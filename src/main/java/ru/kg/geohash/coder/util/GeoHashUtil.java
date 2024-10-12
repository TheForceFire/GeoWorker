package ru.kg.geohash.coder.util;

import ch.hsr.geohash.GeoHash;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;
import org.locationtech.jts.geom.*;


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
            List<GeoHash> geoHashList = new ArrayList<>();
            org.locationtech.jts.geom.Polygon geometryPolygon = GeometryConvertorUtil.lngLatAltListToGeometryPolygon(listPolygon);

            int j = 0;
            do {
                String baitGeoHashString = GeoHash.geoHashStringWithCharacterPrecision(listPolygon.get(j).getLatitude(), listPolygon.get(j).getLongitude(), precision);
                GeoHash baitGeoHash = GeoHash.fromGeohashString(baitGeoHashString);

                if (isGeoHashInLandArea(geometryPolygon, baitGeoHash)) {
                    geoHashList.add(baitGeoHash);
                }
                if (isGeoHashInLandArea(geometryPolygon, baitGeoHash.getEasternNeighbour())) {
                    geoHashList.add(baitGeoHash.getEasternNeighbour());
                }
                if (isGeoHashInLandArea(geometryPolygon, baitGeoHash.getNorthernNeighbour())) {
                    geoHashList.add(baitGeoHash.getNorthernNeighbour());
                }
                if (isGeoHashInLandArea(geometryPolygon, baitGeoHash.getSouthernNeighbour())) {
                    geoHashList.add(baitGeoHash.getSouthernNeighbour());
                }
                if (isGeoHashInLandArea(geometryPolygon, baitGeoHash.getWesternNeighbour())) {
                    geoHashList.add(baitGeoHash.getWesternNeighbour());
                }

                j++;
            }
            while(geoHashList.size() == 0 && j < listPolygon.size());


            int i = 0;
            while (i < geoHashList.size()) {
                if (!geoHashList.contains(geoHashList.get(i).getEasternNeighbour()) && isGeoHashInLandArea(geometryPolygon, geoHashList.get(i).getEasternNeighbour())) {
                    geoHashList.add(geoHashList.get(i).getEasternNeighbour());

                    geoHashList.addAll(FastForwardUtil.fastForwardEast(geoHashList, geometryPolygon));
                }
                if (!geoHashList.contains(geoHashList.get(i).getNorthernNeighbour()) && isGeoHashInLandArea(geometryPolygon, geoHashList.get(i).getNorthernNeighbour())) {
                    geoHashList.add(geoHashList.get(i).getNorthernNeighbour());

                    geoHashList.addAll(FastForwardUtil.fastForwardNorth(geoHashList, geometryPolygon));
                }
                if (!geoHashList.contains(geoHashList.get(i).getSouthernNeighbour()) && isGeoHashInLandArea(geometryPolygon, geoHashList.get(i).getSouthernNeighbour())) {
                    geoHashList.add(geoHashList.get(i).getSouthernNeighbour());

                    geoHashList.addAll(FastForwardUtil.fastForwardSouth(geoHashList, geometryPolygon));
                }
                if (!geoHashList.contains(geoHashList.get(i).getWesternNeighbour()) && isGeoHashInLandArea(geometryPolygon, geoHashList.get(i).getWesternNeighbour())) {
                    geoHashList.add(geoHashList.get(i).getWesternNeighbour());

                    geoHashList.addAll(FastForwardUtil.fastForwardWest(geoHashList, geometryPolygon));
                }

                i++;
            }

            geoHashesStringList = geoHashListToGeoHashStringList(geoHashList);
        }

        return geoHashesStringList;
    }

    private static List<String> geoHashListToGeoHashStringList(List<GeoHash> geoHashList){
        List<String> geoHashStringList = new ArrayList<>();

        for (int j = 0; j < geoHashList.size(); j++) {
            geoHashStringList.add(geoHashList.get(j).toBase32());
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