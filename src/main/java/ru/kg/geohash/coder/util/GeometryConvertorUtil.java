package ru.kg.geohash.coder.util;

import org.geojson.LngLatAlt;
import org.locationtech.jts.geom.*;

import java.util.List;

class GeometryConvertorUtil {
    public static org.locationtech.jts.geom.Polygon lngLatAltListToGeometryPolygon(List<LngLatAlt> listPolygon){
        CoordinateXY[] coordinates = new CoordinateXY[listPolygon.size()];

        for(int i = 0; i < listPolygon.size(); i++){
            coordinates[i] = lngLatAltToCoordinate(listPolygon.get(i));
        }

        org.locationtech.jts.geom.Polygon geometryPolygon = new GeometryFactory().createPolygon(coordinates);
        return geometryPolygon;
    }

    public static Point lngLatAltToPoint(LngLatAlt lngLatAlt){
        double latitudeRadians = lngLatAlt.getLatitude() * Math.PI / 180;
        double longitudeRadians = lngLatAlt.getLongitude() * Math.PI / 180;
        double earthRadius = 6378;
        CoordinateXY coordinate = new CoordinateXY();
        coordinate.setX(earthRadius * latitudeRadians);
        coordinate.setY(earthRadius * longitudeRadians * Math.cos(latitudeRadians));

        Point point = new GeometryFactory().createPoint(coordinate);
        return point;
    }

    public static CoordinateXY lngLatAltToCoordinate(LngLatAlt lngLatAlt){
        double latitudeRadians = lngLatAlt.getLatitude() * Math.PI / 180;
        double longitudeRadians = lngLatAlt.getLongitude() * Math.PI / 180;
        double earthRadius = 6378;
        CoordinateXY coordinate = new CoordinateXY();
        coordinate.setX(earthRadius * latitudeRadians);
        coordinate.setY(earthRadius * longitudeRadians * Math.cos(latitudeRadians));

        return coordinate;
    }
}
