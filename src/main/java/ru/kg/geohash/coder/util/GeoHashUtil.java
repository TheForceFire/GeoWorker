package ru.kg.geohash.coder.util;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import org.geojson.*;
import org.geojson.MultiPolygon;
import org.geojson.Polygon;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.simplify.TopologyPreservingSimplifier;


import java.util.*;


public class GeoHashUtil {

    public static List<String> featureCollectionToGeoHash(FeatureCollection featureCollection, int precision){
        Set<String> geoHashesSet = new HashSet<>();
        FeatureCollection featureCollectionPolygons = transformFeatureCollectionMultiPolygonsToPolygons(featureCollection);

        for(int i = 0; i < featureCollectionPolygons.getFeatures().size(); i++){
            Polygon polygon = (Polygon) featureCollectionPolygons.getFeatures().get(i).getGeometry();
            List<LngLatAlt> listPolygon = polygon.getExteriorRing();



            geoHashesSet.addAll(calculateGeoHashList(listPolygon, precision));
        }

        List<String> geoHashes = new ArrayList<>(geoHashesSet);
        return geoHashes;
    }

    private static FeatureCollection transformFeatureCollectionMultiPolygonsToPolygons(FeatureCollection originFeatureCollection){
        FeatureCollection finalFeatureCollection = new FeatureCollection();

        for(int i = 0; i < originFeatureCollection.getFeatures().size(); i++) {
            GeoJsonObject featureGeoJsonObject = originFeatureCollection.getFeatures().get(i).getGeometry();
            Class<? extends GeoJsonObject> objectType = featureGeoJsonObject.getClass();

            if (objectType.getSimpleName().equals("Polygon")) {
                finalFeatureCollection.add(originFeatureCollection.getFeatures().get(i));
            }
            else if (objectType.getSimpleName().equals("MultiPolygon")) {
                org.geojson.MultiPolygon originMultiPolygon = (MultiPolygon) originFeatureCollection.getFeatures().get(i).getGeometry();
                List<List<List<LngLatAlt>>> originPointsList = originMultiPolygon.getCoordinates();

                for(int j = 0; j < originPointsList.size(); j++){
                    Feature featureToAdd = new Feature();
                    Polygon polygonToAdd = new Polygon();
                    polygonToAdd.setExteriorRing(originPointsList.get(j).get(0));
                    featureToAdd.setGeometry(polygonToAdd);

                    finalFeatureCollection.add(featureToAdd);
                }

            }
        }

        return finalFeatureCollection;
    }

    private static Set<String> calculateGeoHashList(List<LngLatAlt> listPolygon, int precision){
        Set<String> geoHashesStringList = new HashSet<>();

        if(listPolygon.size() > 0) {
            org.locationtech.jts.geom.Polygon geometryPolygon = GeometryConvertorUtil.lngLatAltListToGeometryPolygon(listPolygon);

            double area = geometryPolygon.getArea();
            double simplifyFactor = getSimplifyFactor(area);

            org.locationtech.jts.geom.Geometry geometry = TopologyPreservingSimplifier.simplify(geometryPolygon, simplifyFactor);
            if(!geometry.isEmpty()) {
                geometryPolygon = (org.locationtech.jts.geom.Polygon) geometry;

                Coordinate[] coordinates = geometryPolygon.getCoordinates();
                List<LngLatAlt> listPolygonSimple = new ArrayList<>();
                for (int j = 0; j < coordinates.length; j++) {
                    listPolygonSimple.add(GeometryConvertorUtil.coordinateToLngLatAlt(coordinates[j]));
                }


                LinkedHashSet<GeoHash> geoHashSetPerimeter = findPerimeter(listPolygonSimple, geometryPolygon, precision);

                LinkedHashSet<String> geoHashSetInside = new LinkedHashSet<>();
                for (GeoHash geoHashToCheck : geoHashSetPerimeter) {
                    GeoHash geoHashEastern = geoHashToCheck.getEasternNeighbour();
                    if (!geoHashSetPerimeter.contains(geoHashEastern) && isGeoHashInLandArea(geometryPolygon, geoHashEastern)) {
                        geoHashSetInside.add(geoHashEastern.toBase32());
                        geoHashSetInside.addAll(FastForwardUtil.fastForwardEast(geoHashSetPerimeter, geoHashEastern));
                    }

                    GeoHash geoHashNorthern = geoHashToCheck.getNorthernNeighbour();
                    if (!geoHashSetPerimeter.contains(geoHashNorthern) && isGeoHashInLandArea(geometryPolygon, geoHashNorthern)) {
                        geoHashSetInside.add(geoHashNorthern.toBase32());
                        geoHashSetInside.addAll(FastForwardUtil.fastForwardNorth(geoHashSetPerimeter, geoHashNorthern));
                    }

                    GeoHash geoHashSouthern = geoHashToCheck.getSouthernNeighbour();
                    if (!geoHashSetPerimeter.contains(geoHashSouthern) && isGeoHashInLandArea(geometryPolygon, geoHashSouthern)) {
                        geoHashSetInside.add(geoHashSouthern.toBase32());
                        geoHashSetInside.addAll(FastForwardUtil.fastForwardSouth(geoHashSetPerimeter, geoHashSouthern));
                    }

                    GeoHash geoHashWestern = geoHashToCheck.getWesternNeighbour();
                    if (!geoHashSetPerimeter.contains(geoHashWestern) && isGeoHashInLandArea(geometryPolygon, geoHashWestern)) {
                        geoHashSetInside.add(geoHashWestern.toBase32());
                        geoHashSetInside.addAll(FastForwardUtil.fastForwardWest(geoHashSetPerimeter, geoHashWestern));
                    }
                }

                LinkedHashSet<String> geoHashSetUnited = new LinkedHashSet<>();
                geoHashSetUnited.addAll(geoHashSetToGeoHashStringSet(geoHashSetPerimeter));
                geoHashSetUnited.addAll(geoHashSetInside);
                geoHashesStringList = geoHashSetUnited;
            }
        }

        return geoHashesStringList;
    }

    private static double getSimplifyFactor(double area) {
        double minSimplifyFactor = 1.45;
        double maxSimplifyFactor = 11.126;
        double threshold = 10000000.0;

        if (area >= threshold) {
            return maxSimplifyFactor;
        }

        double simplifyFactor = ((area / threshold) * (maxSimplifyFactor - minSimplifyFactor)) + minSimplifyFactor;
        return simplifyFactor;
    }

    private static LinkedHashSet<GeoHash> findPerimeter(List<LngLatAlt> listPolygon, org.locationtech.jts.geom.Polygon geometryPolygon, int precision){
        LinkedHashSet<GeoHash> geoHashPerimeter = new LinkedHashSet<>();

        String currentGeoHashString = GeoHash.geoHashStringWithCharacterPrecision(listPolygon.get(0).getLatitude(), listPolygon.get(0).getLongitude(), precision);
        GeoHash currentGeoHash = GeoHash.fromGeohashString(currentGeoHashString);
        geoHashPerimeter.add(currentGeoHash);

        for(int i = 1; i < listPolygon.size(); i++){
            String nextGeoHashString = GeoHash.geoHashStringWithCharacterPrecision(listPolygon.get(i).getLatitude(), listPolygon.get(i).getLongitude(), precision);
            GeoHash nextGeoHash = GeoHash.fromGeohashString(nextGeoHashString);

            geoHashPerimeter.addAll(fillGeoHashGap(currentGeoHash, nextGeoHash, geometryPolygon, geoHashPerimeter));
            currentGeoHash = nextGeoHash;
        }

        return geoHashPerimeter;
    }

    private static Set<GeoHash> fillGeoHashGap(GeoHash currentGeoHash, GeoHash nextGeoHash, org.locationtech.jts.geom.Polygon geometryPolygon, LinkedHashSet<GeoHash> geoHashPerimeter){
        LinkedHashSet<GeoHash> geoHashSet = new LinkedHashSet<>();

        if(!currentGeoHash.equals(nextGeoHash)) {
            GeoHash[] neighboursCurrentGeoHash = currentGeoHash.getAdjacent();
            if (isGeoHashArrayContainsGeoHash(neighboursCurrentGeoHash, nextGeoHash)) {
                geoHashSet.add(nextGeoHash);
            } else {
                WGS84Point currentGeoHashPoint = currentGeoHash.getBoundingBoxCenter();
                WGS84Point nextGeoHashPoint = nextGeoHash.getBoundingBoxCenter();

                LatitudeDirection latitudeDirection = setLatitudeDirection(currentGeoHashPoint, nextGeoHashPoint);
                LongitudeDirection longitudeDirection = setLongitudeDirection(currentGeoHashPoint, nextGeoHashPoint);

                do {
                    List<GeoHash> geoHashesToAdd = moveGeoHashesByDirections(currentGeoHash, geometryPolygon, latitudeDirection, longitudeDirection, geoHashPerimeter);


                    double latDelta = Math.abs(currentGeoHashPoint.getLatitude() - nextGeoHashPoint.getLatitude());
                    double lonDelta = Math.abs(currentGeoHashPoint.getLongitude() - nextGeoHashPoint.getLongitude()) * Math.cos(Math.toRadians(currentGeoHashPoint.getLatitude()));


                    if(geoHashesToAdd.size() == 2){
                        if(latDelta > lonDelta) {
                            currentGeoHash = geoHashesToAdd.get(1);
                        }
                        else{
                            currentGeoHash = geoHashesToAdd.get(0);
                        }
                    }
                    else{
                        currentGeoHash = geoHashesToAdd.get(0);
                    }


                    currentGeoHashPoint = currentGeoHash.getBoundingBoxCenter();
                    latitudeDirection = setLatitudeDirection(currentGeoHashPoint, nextGeoHashPoint);
                    longitudeDirection = setLongitudeDirection(currentGeoHashPoint, nextGeoHashPoint);


                    neighboursCurrentGeoHash = currentGeoHash.getAdjacent();
                    geoHashSet.addAll(geoHashesToAdd);
                }
                while (!isGeoHashArrayContainsGeoHash(neighboursCurrentGeoHash, nextGeoHash));

                geoHashSet.add(nextGeoHash);
            }
        }

        return geoHashSet;
    }

    private static LatitudeDirection setLatitudeDirection(WGS84Point currentGeoHashPoint, WGS84Point nextGeoHashPoint){
        LatitudeDirection latitudeDirection;
        if (currentGeoHashPoint.getLatitude() > nextGeoHashPoint.getLatitude()) {
            latitudeDirection = LatitudeDirection.South;
        }
        else if (currentGeoHashPoint.getLatitude() < nextGeoHashPoint.getLatitude()) {
            latitudeDirection = LatitudeDirection.North;
        }
        else{
            latitudeDirection = LatitudeDirection.Same;
        }

        return latitudeDirection;
    }

    private static LongitudeDirection setLongitudeDirection(WGS84Point currentGeoHashPoint, WGS84Point nextGeoHashPoint){
        LongitudeDirection longitudeDirection;
        if (currentGeoHashPoint.getLongitude() > nextGeoHashPoint.getLongitude()) {
            longitudeDirection = LongitudeDirection.West;
        }
        else if (currentGeoHashPoint.getLongitude() < nextGeoHashPoint.getLongitude()){
            longitudeDirection = LongitudeDirection.East;
        }
        else{
            longitudeDirection = LongitudeDirection.Same;
        }

        return longitudeDirection;
    }


    private static List<GeoHash> moveGeoHashesByDirections(GeoHash currentGeoHash, org.locationtech.jts.geom.Polygon geometryPolygon, LatitudeDirection latitudeDirection,
                                                           LongitudeDirection longitudeDirection, LinkedHashSet<GeoHash> geoHashPerimeter){
        List<GeoHash> geoHashToReturn;

        if(latitudeDirection == LatitudeDirection.Same){
            geoHashToReturn = DirectionMoverUtil.moveLatSame(longitudeDirection, currentGeoHash);
        }
        else if(longitudeDirection == LongitudeDirection.Same){
            geoHashToReturn = DirectionMoverUtil.moveLonSame(latitudeDirection, currentGeoHash);
        }
        else{

            if(latitudeDirection == LatitudeDirection.North && longitudeDirection == LongitudeDirection.East){
                geoHashToReturn = DirectionMoverUtil.moveNorthEast(geoHashPerimeter, currentGeoHash, geometryPolygon);
            }
            else if(latitudeDirection == LatitudeDirection.North && longitudeDirection == LongitudeDirection.West){
                geoHashToReturn = DirectionMoverUtil.moveNorthWest(geoHashPerimeter, currentGeoHash, geometryPolygon);
            }

            else if(latitudeDirection == LatitudeDirection.South && longitudeDirection == LongitudeDirection.East){
                geoHashToReturn = DirectionMoverUtil.moveSouthEast(geoHashPerimeter, currentGeoHash, geometryPolygon);
            }
            else{
                geoHashToReturn = DirectionMoverUtil.moveSouthWest(geoHashPerimeter, currentGeoHash, geometryPolygon);
            }

        }

        return geoHashToReturn;
    }

    private static boolean isGeoHashInLandArea(org.locationtech.jts.geom.Polygon geometryPolygon, GeoHash geoHash){
        LngLatAlt currentLngLatAlt = new LngLatAlt();
        currentLngLatAlt.setLatitude(geoHash.getOriginatingPoint().getLatitude());
        currentLngLatAlt.setLongitude(geoHash.getOriginatingPoint().getLongitude());

        Point currentPoint = GeometryConvertorUtil.lngLatAltToPoint(currentLngLatAlt);
        boolean isInPolygon = geometryPolygon.contains(currentPoint);
        return isInPolygon;
    }

    private static boolean isGeoHashArrayContainsGeoHash(GeoHash[] geoHashArray, GeoHash geoHash){
        boolean isContains = false;

        int i = 0;
        while(i < geoHashArray.length && !isContains){
            if(i % 2 == 0 && geoHashArray[i].equals(geoHash)){
                isContains = true;
            }

            i++;
        }

        return isContains;
    }

    private static Set<String> geoHashSetToGeoHashStringSet(LinkedHashSet<GeoHash> geoHashSet){
        Set<String> geoHashStringSet = new HashSet<>();

        for (GeoHash geoHashToConvert : geoHashSet){
            geoHashStringSet.add(geoHashToConvert.toBase32());
        }

        return geoHashStringSet;
    }

    public static boolean compareGeoHashStringLists(List<String> geoHashStringList1, List<String> geoHashStringList2){
        boolean isEquals = true;

        if(geoHashStringList1.size() != geoHashStringList2.size()){
            isEquals = false;
        }
        if(isEquals){
            int i = 0;

            while (i < geoHashStringList1.size() && isEquals){
                if(!geoHashStringList1.contains(geoHashStringList2.get(i))){
                    isEquals = false;
                }

                i++;
            }
        }

        return isEquals;
    }
}