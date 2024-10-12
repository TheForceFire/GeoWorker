package ru.kg.geohash.coder.util;

import ch.hsr.geohash.GeoHash;
import org.geojson.LngLatAlt;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

class FastForwardUtil {

    public static List<GeoHash> fastForwardEast(List<GeoHash> geoHashList, org.locationtech.jts.geom.Polygon geometryPolygon){
        int geoHashesToAdd = 1;
        int geoHashesAdded;

        List<GeoHash> geoHashesNewList = new ArrayList<>();
        geoHashesNewList.add(geoHashList.get(geoHashList.size() - 1));

        do{
            geoHashesAdded = 0;

            for(int i = 0; i < geoHashesToAdd; i++){
                if(!geoHashList.contains(geoHashesNewList.get(geoHashesNewList.size() - 1).getEasternNeighbour())) {
                    geoHashesNewList.add(geoHashesNewList.get(geoHashesNewList.size() - 1).getEasternNeighbour());
                    geoHashesAdded++;
                }
            }
            geoHashesToAdd++;
        }
        while(isGeoHashListInLandArea(geometryPolygon, geoHashesNewList) && geoHashesAdded != 0);


        geoHashesNewList.remove(0);
        while(geoHashesNewList.size() > 0 && !isGeoHashListInLandArea(geometryPolygon, geoHashesNewList)){
            geoHashesNewList.remove(geoHashesNewList.size() - 1);
        }

        return geoHashesNewList;
    }

    public static List<GeoHash> fastForwardNorth(List<GeoHash> geoHashList, org.locationtech.jts.geom.Polygon geometryPolygon){
        int geoHashesToAdd = 1;
        int geoHashesAdded;

        List<GeoHash> geoHashesNewList = new ArrayList<>();
        geoHashesNewList.add(geoHashList.get(geoHashList.size() - 1));

        do{
            geoHashesAdded = 0;

            for(int i = 0; i < geoHashesToAdd; i++){
                if(!geoHashList.contains(geoHashesNewList.get(geoHashesNewList.size() - 1).getNorthernNeighbour())) {
                    geoHashesNewList.add(geoHashesNewList.get(geoHashesNewList.size() - 1).getNorthernNeighbour());
                    geoHashesAdded++;
                }
            }
            geoHashesToAdd++;
        }
        while(isGeoHashListInLandArea(geometryPolygon, geoHashesNewList) && geoHashesAdded != 0);


        geoHashesNewList.remove(0);
        while(geoHashesNewList.size() > 0 && !isGeoHashListInLandArea(geometryPolygon, geoHashesNewList)){
            geoHashesNewList.remove(geoHashesNewList.size() - 1);
        }

        return geoHashesNewList;
    }

    public static List<GeoHash> fastForwardSouth(List<GeoHash> geoHashList, org.locationtech.jts.geom.Polygon geometryPolygon){
        int geoHashesToAdd = 1;
        int geoHashesAdded;

        List<GeoHash> geoHashesNewList = new ArrayList<>();
        geoHashesNewList.add(geoHashList.get(geoHashList.size() - 1));

        do{
            geoHashesAdded = 0;

            for(int i = 0; i < geoHashesToAdd; i++){
                if(!geoHashList.contains(geoHashesNewList.get(geoHashesNewList.size() - 1).getSouthernNeighbour())) {
                    geoHashesNewList.add(geoHashesNewList.get(geoHashesNewList.size() - 1).getSouthernNeighbour());
                    geoHashesAdded++;
                }
            }
            geoHashesToAdd++;
        }
        while(isGeoHashListInLandArea(geometryPolygon, geoHashesNewList) && geoHashesAdded != 0);


        geoHashesNewList.remove(0);
        while(geoHashesNewList.size() > 0 && !isGeoHashListInLandArea(geometryPolygon, geoHashesNewList)){
            geoHashesNewList.remove(geoHashesNewList.size() - 1);
        }

        return geoHashesNewList;
    }

    public static List<GeoHash> fastForwardWest(List<GeoHash> geoHashList, org.locationtech.jts.geom.Polygon geometryPolygon){
        int geoHashesToAdd = 1;
        int geoHashesAdded;

        List<GeoHash> geoHashesNewList = new ArrayList<>();
        geoHashesNewList.add(geoHashList.get(geoHashList.size() - 1));

        do{
            geoHashesAdded = 0;

            for(int i = 0; i < geoHashesToAdd; i++){
                if(!geoHashList.contains(geoHashesNewList.get(geoHashesNewList.size() - 1).getWesternNeighbour())) {
                    geoHashesNewList.add(geoHashesNewList.get(geoHashesNewList.size() - 1).getWesternNeighbour());
                    geoHashesAdded++;
                }
            }
            geoHashesToAdd++;
        }
        while(isGeoHashListInLandArea(geometryPolygon, geoHashesNewList) && geoHashesAdded != 0);


        geoHashesNewList.remove(0);
        while(geoHashesNewList.size() > 0 && !isGeoHashListInLandArea(geometryPolygon, geoHashesNewList)){
            geoHashesNewList.remove(geoHashesNewList.size() - 1);
        }

        return geoHashesNewList;
    }

    private static boolean isGeoHashListInLandArea(org.locationtech.jts.geom.Polygon geometryPolygon, List<GeoHash> geoHashes){
        boolean isInPolygon;

        if(geoHashes.size() == 1){
            isInPolygon = isGeoHashInLandArea(geometryPolygon, geoHashes.get(0));
        }
        else{
            isInPolygon = isGeoHashesInLandArea(geometryPolygon, geoHashes);
        }

        return isInPolygon;
    }

    private static boolean isGeoHashesInLandArea(org.locationtech.jts.geom.Polygon geometryPolygon, List<GeoHash> geoHashes){
        LineString lineString = GeometryConvertorUtil.geohashListToLineString(geoHashes);

        boolean isInPolygon = geometryPolygon.contains(lineString);
        return isInPolygon;
    }

    private static boolean isGeoHashInLandArea(org.locationtech.jts.geom.Polygon geometryPolygon, GeoHash geoHash){
        LngLatAlt currentLngLatAlt = new LngLatAlt();
        currentLngLatAlt.setLatitude(geoHash.getOriginatingPoint().getLatitude());
        currentLngLatAlt.setLongitude(geoHash.getOriginatingPoint().getLongitude());

        Point currentPoint = GeometryConvertorUtil.lngLatAltToPoint(currentLngLatAlt);
        boolean isInPolygon = geometryPolygon.contains(currentPoint);
        return isInPolygon;
    }
}
