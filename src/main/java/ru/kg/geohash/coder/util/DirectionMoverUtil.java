package ru.kg.geohash.coder.util;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import org.geojson.LngLatAlt;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

class DirectionMoverUtil {

    public static List<GeoHash> moveLatSame(LongitudeDirection longitudeDirection, GeoHash currentGeoHash){
        List<GeoHash> geoHashToReturn = new ArrayList<>();

        if(longitudeDirection == LongitudeDirection.West){
            geoHashToReturn.add(currentGeoHash.getWesternNeighbour());
        }
        else{
            geoHashToReturn.add(currentGeoHash.getEasternNeighbour());
        }

        return geoHashToReturn;
    }

    public static List<GeoHash> moveLonSame(LatitudeDirection latitudeDirection, GeoHash currentGeoHash){
        List<GeoHash> geoHashToReturn = new ArrayList<>();

        if(latitudeDirection == LatitudeDirection.South){
            geoHashToReturn.add(currentGeoHash.getSouthernNeighbour());
        }
        else{
            geoHashToReturn.add(currentGeoHash.getNorthernNeighbour());
        }

        return geoHashToReturn;
    }

    public static List<GeoHash> moveNorthEast(LinkedHashSet<GeoHash> geoHashPerimeter, GeoHash currentGeoHash, org.locationtech.jts.geom.Polygon geometryPolygon){
        List<GeoHash> geoHashToReturn = new ArrayList<>();

        boolean isBorderNorth = isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getNorthernNeighbour());
        boolean isBorderEast = isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getEasternNeighbour());

        if(!geoHashPerimeter.contains(currentGeoHash.getEasternNeighbour()) && isBorderEast){
            geoHashToReturn.add(currentGeoHash.getEasternNeighbour());
        }
        if(!geoHashPerimeter.contains(currentGeoHash.getNorthernNeighbour()) && isBorderNorth){
            geoHashToReturn.add(currentGeoHash.getNorthernNeighbour());
        }

        if(geoHashToReturn.size() == 0){
            if(isBorderEast){
                geoHashToReturn.add(currentGeoHash.getEasternNeighbour());
            }
            if(isBorderNorth){
                geoHashToReturn.add(currentGeoHash.getNorthernNeighbour());
            }
        }

        if(geoHashToReturn.size() == 0){
            geoHashToReturn.add(currentGeoHash.getNorthernNeighbour().getEasternNeighbour());
        }

        return geoHashToReturn;
    }

    public static List<GeoHash> moveNorthWest(LinkedHashSet<GeoHash> geoHashPerimeter, GeoHash currentGeoHash, org.locationtech.jts.geom.Polygon geometryPolygon){
        List<GeoHash> geoHashToReturn = new ArrayList<>();

        boolean isBorderNorth = isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getNorthernNeighbour());
        boolean isBorderWest = isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getWesternNeighbour());

        if(!geoHashPerimeter.contains(currentGeoHash.getWesternNeighbour()) && isBorderWest){
            geoHashToReturn.add(currentGeoHash.getWesternNeighbour());
        }
        if(!geoHashPerimeter.contains(currentGeoHash.getNorthernNeighbour()) && isBorderNorth){
            geoHashToReturn.add(currentGeoHash.getNorthernNeighbour());
        }

        if(geoHashToReturn.size() == 0){
            if(isBorderWest){
                geoHashToReturn.add(currentGeoHash.getWesternNeighbour());
            }
            if(isBorderNorth){
                geoHashToReturn.add(currentGeoHash.getNorthernNeighbour());
            }
        }

        if(geoHashToReturn.size() == 0){
            geoHashToReturn.add(currentGeoHash.getNorthernNeighbour().getWesternNeighbour());
        }

        return geoHashToReturn;
    }

    public static List<GeoHash> moveSouthEast(LinkedHashSet<GeoHash> geoHashPerimeter, GeoHash currentGeoHash, org.locationtech.jts.geom.Polygon geometryPolygon){
        List<GeoHash> geoHashToReturn = new ArrayList<>();

        boolean isBorderSouth = isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getSouthernNeighbour());
        boolean isBorderEast = isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getEasternNeighbour());

        if(!geoHashPerimeter.contains(currentGeoHash.getEasternNeighbour()) && isBorderEast){
            geoHashToReturn.add(currentGeoHash.getEasternNeighbour());
        }
        if(!geoHashPerimeter.contains(currentGeoHash.getSouthernNeighbour()) && isBorderSouth){
            geoHashToReturn.add(currentGeoHash.getSouthernNeighbour());
        }

        if(geoHashToReturn.size() == 0){
            if(isBorderEast){
                geoHashToReturn.add(currentGeoHash.getEasternNeighbour());
            }
            if(isBorderSouth){
                geoHashToReturn.add(currentGeoHash.getSouthernNeighbour());
            }
        }

        if(geoHashToReturn.size() == 0){
            geoHashToReturn.add(currentGeoHash.getSouthernNeighbour().getEasternNeighbour());
        }

        return geoHashToReturn;
    }

    public static List<GeoHash> moveSouthWest(LinkedHashSet<GeoHash> geoHashPerimeter, GeoHash currentGeoHash, org.locationtech.jts.geom.Polygon geometryPolygon){
        List<GeoHash> geoHashToReturn = new ArrayList<>();

        boolean isBorderSouth = isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getSouthernNeighbour());
        boolean isBorderWest = isGeoHashBordersWithPolygon(geometryPolygon, currentGeoHash.getWesternNeighbour());

        if(!geoHashPerimeter.contains(currentGeoHash.getWesternNeighbour()) && isBorderWest){
            geoHashToReturn.add(currentGeoHash.getWesternNeighbour());
        }
        if(!geoHashPerimeter.contains(currentGeoHash.getSouthernNeighbour()) && isBorderSouth){
            geoHashToReturn.add(currentGeoHash.getSouthernNeighbour());
        }

        if(geoHashToReturn.size() == 0){
            if(isBorderWest){
                geoHashToReturn.add(currentGeoHash.getWesternNeighbour());
            }
            if(isBorderSouth){
                geoHashToReturn.add(currentGeoHash.getSouthernNeighbour());
            }
        }

        if(geoHashToReturn.size() == 0){
            geoHashToReturn.add(currentGeoHash.getSouthernNeighbour().getWesternNeighbour());
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
