package ru.kg.geohash.coder.util;

import ch.hsr.geohash.GeoHash;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;


import java.util.ArrayList;
import java.util.List;



class GeohashConvertor {
    private static final int MAX_DEPTH = 10000;
    private static final int LATITUDE_MAX_VALUE = 90;
    private static final int LONGITUDE_MAX_VALUE = 180;

    public static List<String> featureCollectionToGeohash(FeatureCollection featureCollection, int precision){
        List<String> geoHashes = new ArrayList<>();

        for(int i = 0; i < featureCollection.getFeatures().size(); i++){
            Polygon polygon = (Polygon) featureCollection.getFeatures().get(i).getGeometry();
            List<LngLatAlt> listPolygon = polygon.getExteriorRing();

            geoHashes.addAll(calculateGeohashList(listPolygon, precision));
        }

        return geoHashes;
    }

    private static List<String> calculateGeohashList(List<LngLatAlt> listPolygon, int precision){
        double latitudeError = getLatitudeError(precision);
        double longitudeError = getLongitudeError(precision);

        org.locationtech.jts.geom.Polygon geometryPolygon = listToGeometryPolygon(listPolygon);

        int i = 0;
        List<String> geoHashes = new ArrayList<>();

        while(i < listPolygon.size() && geoHashes.size() == 0){
            LngLatAlt baitLngLatAlt = new LngLatAlt();

            if(listPolygon.get(i).getLatitude() + latitudeError <= LATITUDE_MAX_VALUE) {
                baitLngLatAlt.setLatitude(listPolygon.get(i).getLatitude() + latitudeError);
            }
            else{
                baitLngLatAlt.setLatitude(listPolygon.get(i).getLatitude() - latitudeError);
            }
            if(listPolygon.get(i).getLongitude() + longitudeError <= LONGITUDE_MAX_VALUE) {
                baitLngLatAlt.setLongitude(listPolygon.get(i).getLongitude() + longitudeError);
            }
            else{
                baitLngLatAlt.setLongitude(listPolygon.get(i).getLongitude() - longitudeError);
            }

            String baitGeoHash = GeoHash.geoHashStringWithCharacterPrecision(baitLngLatAlt.getLatitude(), baitLngLatAlt.getLongitude(), precision);

            geoHashes = findAllGeohashesRecursion(new ArrayList<>(), geometryPolygon, GeoHash.fromGeohashString(baitGeoHash), 0);

            i++;
        }

        return geoHashes;
    }

    private static List<String> findAllGeohashesRecursion(List<String> geoHashesString, org.locationtech.jts.geom.Polygon geometryPolygon,
                                                          GeoHash currentGeohash, int currentDepth){

        if(currentDepth < MAX_DEPTH){
            LngLatAlt currentLngLatAlt = new LngLatAlt();
            currentLngLatAlt.setLatitude(currentGeohash.getOriginatingPoint().getLatitude());
            currentLngLatAlt.setLongitude(currentGeohash.getOriginatingPoint().getLongitude());

            Point currentPoint = lngLatAltToPoint(currentLngLatAlt);
            boolean isInPolygon = geometryPolygon.contains(currentPoint);

            if(isInPolygon){
                if(!geoHashesString.contains(currentGeohash.toBase32())){
                    geoHashesString.add(currentGeohash.toBase32());

                    List<String> geoHashesStringBeforeFindings = new ArrayList<>(geoHashesString);

                    geoHashesString = findAllGeohashesRecursion(geoHashesString, geometryPolygon, currentGeohash.getEasternNeighbour(), currentDepth + 1);

                    geoHashesString = findAllGeohashesRecursion(geoHashesString, geometryPolygon, currentGeohash.getNorthernNeighbour(), currentDepth + 1);

                    geoHashesString = findAllGeohashesRecursion(geoHashesString, geometryPolygon, currentGeohash.getWesternNeighbour(), currentDepth + 1);

                    geoHashesString = findAllGeohashesRecursion(geoHashesString, geometryPolygon, currentGeohash.getSouthernNeighbour(), currentDepth + 1);



                    if(geoHashesString.equals(geoHashesStringBeforeFindings)) {

                        geoHashesString = findAllGeohashesRecursion(geoHashesString, geometryPolygon, currentGeohash.getEasternNeighbour().getSouthernNeighbour(), currentDepth + 1);

                        geoHashesString = findAllGeohashesRecursion(geoHashesString, geometryPolygon, currentGeohash.getNorthernNeighbour().getEasternNeighbour(), currentDepth + 1);

                        geoHashesString = findAllGeohashesRecursion(geoHashesString, geometryPolygon, currentGeohash.getWesternNeighbour().getNorthernNeighbour(), currentDepth + 1);

                        geoHashesString = findAllGeohashesRecursion(geoHashesString, geometryPolygon, currentGeohash.getSouthernNeighbour().getWesternNeighbour(), currentDepth + 1);
                    }
                }
            }
        }
        else{
            throw new StackOverflowError();
        }

        return geoHashesString;
    }

    private static org.locationtech.jts.geom.Polygon listToGeometryPolygon(List<LngLatAlt> listPolygon){
        Coordinate[] coordinates = new Coordinate[listPolygon.size()];

        for(int i = 0; i < listPolygon.size(); i++){
            coordinates[i] = lngLatAltToCoordinate(listPolygon.get(i));
        }

        org.locationtech.jts.geom.Polygon geometryPolygon = new GeometryFactory().createPolygon(coordinates);
        return geometryPolygon;
    }

    private static Point lngLatAltToPoint(LngLatAlt lngLatAlt){
        double latitudeRadians = lngLatAlt.getLatitude() * Math.PI / 180;
        double longitudeRadians = lngLatAlt.getLongitude() * Math.PI / 180;
        double earthRadius = 6378;
        Coordinate coordinate = new Coordinate();
        coordinate.setX(earthRadius * latitudeRadians);
        coordinate.setY(earthRadius * longitudeRadians * Math.cos(latitudeRadians));

        Point point = new GeometryFactory().createPoint(coordinate);
        return point;
    }
    private static Coordinate lngLatAltToCoordinate(LngLatAlt lngLatAlt){
        double latitudeRadians = lngLatAlt.getLatitude() * Math.PI / 180;
        double longitudeRadians = lngLatAlt.getLongitude() * Math.PI / 180;
        double earthRadius = 6378;
        Coordinate coordinate = new Coordinate();
        coordinate.setX(earthRadius * latitudeRadians);
        coordinate.setY(earthRadius * longitudeRadians * Math.cos(latitudeRadians));

        return coordinate;
    }

    private static double getLatitudeError(int precision){
        double errorValue = switch (precision) {
            case 1 -> 23;
            case 2 -> 2.8;
            case 3 -> 0.7;
            case 4 -> 0.087;
            case 5 -> 0.022;
            case 6 -> 0.0027;
            case 7 -> 0.00068;
            case 8 -> 0.000085;
            case 9 -> 0.000021;
            case 10 -> 0.0000026;
            case 11 -> 0.00000066;
            default -> throw new IllegalArgumentException();
        };
        return errorValue;
    }
    private static double getLongitudeError(int precision){
        double errorValue = switch (precision) {
            case 1 -> 23;
            case 2 -> 5.6;
            case 3 -> 0.7;
            case 4 -> 0.18;
            case 5 -> 0.022;
            case 6 -> 0.0055;
            case 7 -> 0.00068;
            case 8 -> 0.00017;
            case 9 -> 0.000021;
            case 10 -> 0.0000053;
            case 11 -> 0.00000066;
            default -> throw new IllegalArgumentException();
        };
        return errorValue;
    }

}