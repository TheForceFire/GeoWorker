package ru.kg.geohash.coder.util;

import ch.hsr.geohash.GeoHash;
import org.geojson.LngLatAlt;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

import java.util.LinkedHashSet;

class FastForwardUtil {

    public static LinkedHashSet<GeoHash> fastForwardEast(GeoHash geoHashToFastForward, org.locationtech.jts.geom.Polygon geometryPolygon){
        int geoHashesToAdd = 1;

        LinkedHashSet<GeoHash> geoHashesNewSet = new LinkedHashSet<>();

        GeoHash firstGeoHash = geoHashToFastForward.getEasternNeighbour();
        GeoHash lastGeoHash;

        do{
            for(int i = 0; i < geoHashesToAdd; i++){
                geoHashesNewSet.add(geoHashToFastForward.getEasternNeighbour());

                geoHashToFastForward = geoHashToFastForward.getEasternNeighbour();
            }
            lastGeoHash = geoHashToFastForward;
            geoHashesToAdd++;
        }
        while(isGeoHashLineInLandArea(geometryPolygon, firstGeoHash, lastGeoHash));


        if(geoHashesNewSet.size() == 1){
            geoHashesNewSet = new LinkedHashSet<>();
        }
        else {
            while (geoHashesNewSet.size() > 0 && !isGeoHashLineInLandArea(geometryPolygon, firstGeoHash, lastGeoHash)) {
                geoHashesNewSet.remove(lastGeoHash);

                lastGeoHash = lastGeoHash.getWesternNeighbour();
            }
        }

        return geoHashesNewSet;
    }

    public static LinkedHashSet<GeoHash> fastForwardNorth(GeoHash geoHashToFastForward, org.locationtech.jts.geom.Polygon geometryPolygon){
        int geoHashesToAdd = 1;

        LinkedHashSet<GeoHash> geoHashesNewSet = new LinkedHashSet<>();

        GeoHash firstGeoHash = geoHashToFastForward.getNorthernNeighbour();
        GeoHash lastGeoHash;

        do{
            for(int i = 0; i < geoHashesToAdd; i++){
                geoHashesNewSet.add(geoHashToFastForward.getNorthernNeighbour());

                geoHashToFastForward = geoHashToFastForward.getNorthernNeighbour();
            }
            lastGeoHash = geoHashToFastForward;
            geoHashesToAdd++;
        }
        while(isGeoHashLineInLandArea(geometryPolygon, firstGeoHash, lastGeoHash));


        if(geoHashesNewSet.size() == 1){
            geoHashesNewSet = new LinkedHashSet<>();
        }
        else {
            while (geoHashesNewSet.size() > 0 && !isGeoHashLineInLandArea(geometryPolygon, firstGeoHash, lastGeoHash)) {
                geoHashesNewSet.remove(lastGeoHash);

                lastGeoHash = lastGeoHash.getSouthernNeighbour();
            }
        }

        return geoHashesNewSet;
    }

    public static LinkedHashSet<GeoHash> fastForwardSouth(GeoHash geoHashToFastForward, org.locationtech.jts.geom.Polygon geometryPolygon){
        int geoHashesToAdd = 1;

        LinkedHashSet<GeoHash> geoHashesNewSet = new LinkedHashSet<>();

        GeoHash firstGeoHash = geoHashToFastForward.getSouthernNeighbour();
        GeoHash lastGeoHash;

        do{
            for(int i = 0; i < geoHashesToAdd; i++){
                geoHashesNewSet.add(geoHashToFastForward.getSouthernNeighbour());

                geoHashToFastForward = geoHashToFastForward.getSouthernNeighbour();
            }
            lastGeoHash = geoHashToFastForward;
            geoHashesToAdd++;
        }
        while(isGeoHashLineInLandArea(geometryPolygon, firstGeoHash, lastGeoHash));


        if(geoHashesNewSet.size() == 1){
            geoHashesNewSet = new LinkedHashSet<>();
        }
        else {
            while (geoHashesNewSet.size() > 0 && !isGeoHashLineInLandArea(geometryPolygon, firstGeoHash, lastGeoHash)) {
                geoHashesNewSet.remove(lastGeoHash);

                lastGeoHash = lastGeoHash.getNorthernNeighbour();
            }
        }

        return geoHashesNewSet;
    }

    public static LinkedHashSet<GeoHash> fastForwardWest(GeoHash geoHashToFastForward, org.locationtech.jts.geom.Polygon geometryPolygon){
        int geoHashesToAdd = 1;

        LinkedHashSet<GeoHash> geoHashesNewSet = new LinkedHashSet<>();

        GeoHash firstGeoHash = geoHashToFastForward.getWesternNeighbour();
        GeoHash lastGeoHash;

        do{
            for(int i = 0; i < geoHashesToAdd; i++){
                geoHashesNewSet.add(geoHashToFastForward.getWesternNeighbour());

                geoHashToFastForward = geoHashToFastForward.getWesternNeighbour();
            }
            lastGeoHash = geoHashToFastForward;
            geoHashesToAdd++;
        }
        while(isGeoHashLineInLandArea(geometryPolygon, firstGeoHash, lastGeoHash));


        if(geoHashesNewSet.size() == 1){
            geoHashesNewSet = new LinkedHashSet<>();
        }
        else {
            while (geoHashesNewSet.size() > 0 && !isGeoHashLineInLandArea(geometryPolygon, firstGeoHash, lastGeoHash)) {
                geoHashesNewSet.remove(lastGeoHash);

                lastGeoHash = lastGeoHash.getEasternNeighbour();
            }
        }

        return geoHashesNewSet;
    }

    private static boolean isGeoHashLineInLandArea(org.locationtech.jts.geom.Polygon geometryPolygon, GeoHash firstGeoHash, GeoHash lastGeoHash){
        boolean isInPolygon;

        if(firstGeoHash.equals(lastGeoHash)){
            isInPolygon = isGeoHashInLandArea(geometryPolygon, firstGeoHash);
        }
        else{
            isInPolygon = isGeoHashesInLandArea(geometryPolygon, firstGeoHash, lastGeoHash);
        }

        return isInPolygon;
    }

    private static boolean isGeoHashesInLandArea(org.locationtech.jts.geom.Polygon geometryPolygon, GeoHash firstGeoHash, GeoHash lastGeoHash){
        LineString lineString = GeometryConvertorUtil.geohashLineToLineString(firstGeoHash, lastGeoHash);

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
