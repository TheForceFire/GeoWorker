package ru.kg.geohash.coder.util;

import ch.hsr.geohash.GeoHash;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;
import org.locationtech.jts.geom.*;


import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.List;


public class GeoHashUtil {

    public static List<String> featureCollectionToGeoHash(FeatureCollection featureCollection, int precision){
        List<String> geoHashes = new ArrayList<>();

        for(int i = 0; i < featureCollection.getFeatures().size(); i++){
            Polygon polygon = (Polygon) featureCollection.getFeatures().get(i).getGeometry();
            List<LngLatAlt> listPolygon = polygon.getExteriorRing();

            geoHashes.addAll(calculateGeoHashList(listPolygon, precision));
        }

        return geoHashes;
    }

    private static List<String> calculateGeoHashList(List<LngLatAlt> listPolygon, int precision){
        List<String> geoHashesStringList = new ArrayList<>();

        if(listPolygon.size() > 0) {
            org.locationtech.jts.geom.Polygon geometryPolygon = GeometryConvertorUtil.lngLatAltListToGeometryPolygon(listPolygon);

            LinkedHashSet<GeoHash> geoHashSet = new LinkedHashSet<>(findFirstEntry(listPolygon, geometryPolygon, precision));

            int indexesToSkip = 0;
            boolean hasNewGeoHashes = true;
            while (hasNewGeoHashes) {
                hasNewGeoHashes = false;
                int setSize = geoHashSet.size();

                int i = 0;
                for (GeoHash geoHashToCheck : new LinkedHashSet<>(geoHashSet)) {
                    if(i < indexesToSkip){
                        i++;
                    }
                    else {
                        if (!geoHashSet.contains(geoHashToCheck.getEasternNeighbour()) && isGeoHashInLandArea(geometryPolygon, geoHashToCheck.getEasternNeighbour())) {
                            geoHashSet.add(geoHashToCheck.getEasternNeighbour());
                            geoHashSet.addAll(FastForwardUtil.fastForwardEast(geoHashToCheck.getEasternNeighbour(), geometryPolygon));
                            hasNewGeoHashes = true;
                        }
                        if (!geoHashSet.contains(geoHashToCheck.getNorthernNeighbour()) && isGeoHashInLandArea(geometryPolygon, geoHashToCheck.getNorthernNeighbour())) {
                            geoHashSet.add(geoHashToCheck.getNorthernNeighbour());
                            geoHashSet.addAll(FastForwardUtil.fastForwardNorth(geoHashToCheck.getNorthernNeighbour(), geometryPolygon));
                            hasNewGeoHashes = true;
                        }
                        if (!geoHashSet.contains(geoHashToCheck.getSouthernNeighbour()) && isGeoHashInLandArea(geometryPolygon, geoHashToCheck.getSouthernNeighbour())) {
                            geoHashSet.add(geoHashToCheck.getSouthernNeighbour());
                            geoHashSet.addAll(FastForwardUtil.fastForwardSouth(geoHashToCheck.getSouthernNeighbour(), geometryPolygon));
                            hasNewGeoHashes = true;
                        }
                        if (!geoHashSet.contains(geoHashToCheck.getWesternNeighbour()) && isGeoHashInLandArea(geometryPolygon, geoHashToCheck.getWesternNeighbour())) {
                            geoHashSet.add(geoHashToCheck.getWesternNeighbour());
                            geoHashSet.addAll(FastForwardUtil.fastForwardWest(geoHashToCheck.getWesternNeighbour(), geometryPolygon));
                            hasNewGeoHashes = true;
                        }
                    }
                }

                indexesToSkip = setSize;
            }

            geoHashesStringList = geoHashSetToGeoHashStringList(geoHashSet);
        }

        return geoHashesStringList;
    }

    private static LinkedHashSet<GeoHash> findFirstEntry(List<LngLatAlt> listPolygon, org.locationtech.jts.geom.Polygon geometryPolygon, int precision){
        LinkedHashSet<GeoHash> geoHashSet = new LinkedHashSet<>();

        int j = 0;
        do {
            String baitGeoHashString = GeoHash.geoHashStringWithCharacterPrecision(listPolygon.get(j).getLatitude(), listPolygon.get(j).getLongitude(), precision);
            GeoHash baitGeoHash = GeoHash.fromGeohashString(baitGeoHashString);

            if (isGeoHashInLandArea(geometryPolygon, baitGeoHash)) {
                geoHashSet.add(baitGeoHash);
            }
            if (isGeoHashInLandArea(geometryPolygon, baitGeoHash.getEasternNeighbour())) {
                geoHashSet.add(baitGeoHash.getEasternNeighbour());
                geoHashSet.addAll(FastForwardUtil.fastForwardEast(baitGeoHash.getEasternNeighbour(), geometryPolygon));
            }
            if (isGeoHashInLandArea(geometryPolygon, baitGeoHash.getNorthernNeighbour())) {
                geoHashSet.add(baitGeoHash.getNorthernNeighbour());
                geoHashSet.addAll(FastForwardUtil.fastForwardNorth(baitGeoHash.getNorthernNeighbour(), geometryPolygon));
            }
            if (isGeoHashInLandArea(geometryPolygon, baitGeoHash.getSouthernNeighbour())) {
                geoHashSet.add(baitGeoHash.getSouthernNeighbour());
                geoHashSet.addAll(FastForwardUtil.fastForwardSouth(baitGeoHash.getSouthernNeighbour(), geometryPolygon));
            }
            if (isGeoHashInLandArea(geometryPolygon, baitGeoHash.getWesternNeighbour())) {
                geoHashSet.add(baitGeoHash.getWesternNeighbour());
                geoHashSet.addAll(FastForwardUtil.fastForwardWest(baitGeoHash.getWesternNeighbour(), geometryPolygon));
            }

            j++;
        }
        while(geoHashSet.size() == 0 && j < listPolygon.size());

        return geoHashSet;
    }

    private static List<String> geoHashSetToGeoHashStringList(LinkedHashSet<GeoHash> geoHashSet){
        List<String> geoHashStringList = new ArrayList<>();

        for (GeoHash geoHashToConvert : geoHashSet){
            geoHashStringList.add(geoHashToConvert.toBase32());
        }

        return geoHashStringList;
    }

    private static boolean isGeoHashInLandArea(org.locationtech.jts.geom.Polygon geometryPolygon, GeoHash geoHash){
        LngLatAlt currentLngLatAlt = new LngLatAlt();
        currentLngLatAlt.setLatitude(geoHash.getOriginatingPoint().getLatitude());
        currentLngLatAlt.setLongitude(geoHash.getOriginatingPoint().getLongitude());

        Point currentPoint = GeometryConvertorUtil.lngLatAltToPoint(currentLngLatAlt);
        boolean isInPolygon = geometryPolygon.contains(currentPoint);
        return isInPolygon;
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