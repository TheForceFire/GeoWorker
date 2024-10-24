package ru.kg.geohash.coder.util;

import org.geojson.LngLatAlt;
import org.locationtech.jts.geom.*;

import java.util.List;

class GeometryConvertorUtil {
    private static final double EarthRadius = 6378.1;

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
        CoordinateXY coordinate = new CoordinateXY();
        coordinate.setX(EarthRadius * latitudeRadians);
        coordinate.setY(EarthRadius * longitudeRadians * Math.cos(latitudeRadians));

        Point point = new GeometryFactory().createPoint(coordinate);
        return point;
    }

    public static CoordinateXY lngLatAltToCoordinate(LngLatAlt lngLatAlt){
        double latitudeRadians = lngLatAlt.getLatitude() * Math.PI / 180;
        double longitudeRadians = lngLatAlt.getLongitude() * Math.PI / 180;
        CoordinateXY coordinate = new CoordinateXY();
        coordinate.setX(EarthRadius * latitudeRadians);
        coordinate.setY(EarthRadius * longitudeRadians * Math.cos(latitudeRadians));

        return coordinate;
    }

    public static LngLatAlt coordinateToLngLatAlt(Coordinate coordinate){
        double x = coordinate.getX();
        double y = coordinate.getY();

        double latitudeRadians = x / EarthRadius;
        double latitude = latitudeRadians * 180 / Math.PI;
        if(latitude < -90) latitude = -90;
        else if(latitude > 90) latitude = 90;

        double longitudeRadians = y / (EarthRadius * Math.cos(latitudeRadians));
        double longitude = longitudeRadians * 180 / Math.PI;
        if(longitude < -180) longitude = -180;
        else if(longitude > 180) longitude = 180;

        LngLatAlt lngLatAlt = new LngLatAlt(longitude, latitude);
        return lngLatAlt;
    }
}
