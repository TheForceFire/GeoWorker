package ru.kg.geohash.coder.util;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import org.geojson.LngLatAlt;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

class DirectionMoverUtil {

    public static GeoHash moveLatSame(LongitudeDirection longitudeDirection, GeoHash currentGeoHash){
        GeoHash geoHashToReturn;

        if(longitudeDirection == LongitudeDirection.West){
            geoHashToReturn = currentGeoHash.getWesternNeighbour();
        }
        else{
            geoHashToReturn = currentGeoHash.getEasternNeighbour();
        }

        return geoHashToReturn;
    }

    public static GeoHash moveLonSame(LatitudeDirection latitudeDirection, GeoHash currentGeoHash){
        GeoHash geoHashToReturn;

        if(latitudeDirection == LatitudeDirection.South){
            geoHashToReturn = currentGeoHash.getSouthernNeighbour();
        }
        else{
            geoHashToReturn = currentGeoHash.getNorthernNeighbour();
        }

        return geoHashToReturn;
    }

    public static GeoHash moveNorthEast(LinkedHashSet<GeoHash> geoHashPerimeter, GeoHash currentGeoHash, org.locationtech.jts.geom.Polygon geometryPolygon){
        GeoHash geoHashToReturn;

        if(!geoHashPerimeter.contains(currentGeoHash.getNorthernNeighbour()) &&
                isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getNorthernNeighbour())){
            geoHashToReturn = currentGeoHash.getNorthernNeighbour();
        }
        else if(!geoHashPerimeter.contains(currentGeoHash.getEasternNeighbour()) &&
                isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getEasternNeighbour())){
            geoHashToReturn = currentGeoHash.getEasternNeighbour();
        }
        else if(isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getNorthernNeighbour().getEasternNeighbour())){
            geoHashToReturn = currentGeoHash.getNorthernNeighbour().getEasternNeighbour();
        }
        else{
            geoHashToReturn = currentGeoHash.getSouthernNeighbour().getEasternNeighbour();
        }

        return geoHashToReturn;
    }

    public static GeoHash moveNorthWest(LinkedHashSet<GeoHash> geoHashPerimeter, GeoHash currentGeoHash, org.locationtech.jts.geom.Polygon geometryPolygon){
        GeoHash geoHashToReturn;

        if(!geoHashPerimeter.contains(currentGeoHash.getNorthernNeighbour()) &&
                isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getNorthernNeighbour())){
            geoHashToReturn = currentGeoHash.getNorthernNeighbour();
        }
        else if(!geoHashPerimeter.contains(currentGeoHash.getWesternNeighbour()) &&
                isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getWesternNeighbour())){
            geoHashToReturn = currentGeoHash.getWesternNeighbour();
        }
        else if(isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getNorthernNeighbour().getWesternNeighbour())){
            geoHashToReturn = currentGeoHash.getNorthernNeighbour().getWesternNeighbour();
        }
        else{
            geoHashToReturn = currentGeoHash.getSouthernNeighbour().getWesternNeighbour();
        }

        return geoHashToReturn;
    }

    public static GeoHash moveSouthEast(LinkedHashSet<GeoHash> geoHashPerimeter, GeoHash currentGeoHash, org.locationtech.jts.geom.Polygon geometryPolygon){
        GeoHash geoHashToReturn;

        if(!geoHashPerimeter.contains(currentGeoHash.getSouthernNeighbour()) &&
                isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getSouthernNeighbour())){
            geoHashToReturn = currentGeoHash.getSouthernNeighbour();
        }
        else if(!geoHashPerimeter.contains(currentGeoHash.getEasternNeighbour()) &&
                isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getEasternNeighbour())){
            geoHashToReturn = currentGeoHash.getEasternNeighbour();
        }
        else if(isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getSouthernNeighbour().getEasternNeighbour())){
            geoHashToReturn = currentGeoHash.getSouthernNeighbour().getEasternNeighbour();
        }
        else{
            geoHashToReturn = currentGeoHash.getNorthernNeighbour().getEasternNeighbour();
        }

        return geoHashToReturn;
    }

    public static GeoHash moveSouthWest(LinkedHashSet<GeoHash> geoHashPerimeter, GeoHash currentGeoHash, org.locationtech.jts.geom.Polygon geometryPolygon){
        GeoHash geoHashToReturn;

        if(!geoHashPerimeter.contains(currentGeoHash.getSouthernNeighbour()) &&
                isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getSouthernNeighbour())){
            geoHashToReturn = currentGeoHash.getSouthernNeighbour();
        }
        else if(!geoHashPerimeter.contains(currentGeoHash.getWesternNeighbour()) &&
                isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getWesternNeighbour())){
            geoHashToReturn = currentGeoHash.getWesternNeighbour();
        }
        else if(isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getSouthernNeighbour().getWesternNeighbour())){
            geoHashToReturn = currentGeoHash.getSouthernNeighbour().getWesternNeighbour();
        }
        else{
            geoHashToReturn = currentGeoHash.getNorthernNeighbour().getWesternNeighbour();
        }

        return geoHashToReturn;
    }


    private static boolean isGeoHashBordersWithPolygon(org.locationtech.jts.geom.Polygon geometryPolygon, GeoHash geoHash){
        boolean isBorder = false;

        BoundingBox boundingBox = geoHash.getBoundingBox();

        List<LngLatAlt> boundingPoints = new ArrayList<>();
        boundingPoints.add(new LngLatAlt(boundingBox.getWestLongitude(), boundingBox.getNorthLatitude()));
        boundingPoints.add(new LngLatAlt(boundingBox.getWestLongitude(), boundingBox.getSouthLatitude()));
        boundingPoints.add(new LngLatAlt(boundingBox.getEastLongitude(), boundingBox.getSouthLatitude()));
        boundingPoints.add(new LngLatAlt(boundingBox.getEastLongitude(), boundingBox.getNorthLatitude()));
        boundingPoints.add(new LngLatAlt(boundingBox.getWestLongitude(), boundingBox.getNorthLatitude()));

        org.locationtech.jts.geom.Polygon geoHashPolygon = GeometryConvertorUtil.lngLatAltListToGeometryPolygon(boundingPoints);

        if(!geometryPolygon.contains(geoHashPolygon) && geometryPolygon.intersects(geoHashPolygon)){
            isBorder = true;
        }

        return isBorder;
    }
}
