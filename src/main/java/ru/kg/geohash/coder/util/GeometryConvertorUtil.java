package ru.kg.geohash.coder.util;

import ch.hsr.geohash.GeoHash;
import org.geojson.LngLatAlt;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

class GeometryConvertorUtil {
    public static org.locationtech.jts.geom.Polygon lngLatAltListToGeometryPolygon(List<LngLatAlt> listPolygon){
        Coordinate[] coordinates = new Coordinate[listPolygon.size()];

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
        Coordinate coordinate = new Coordinate();
        coordinate.setX(earthRadius * latitudeRadians);
        coordinate.setY(earthRadius * longitudeRadians * Math.cos(latitudeRadians));

        Point point = new GeometryFactory().createPoint(coordinate);
        return point;
    }

    public static Coordinate lngLatAltToCoordinate(LngLatAlt lngLatAlt){
        double latitudeRadians = lngLatAlt.getLatitude() * Math.PI / 180;
        double longitudeRadians = lngLatAlt.getLongitude() * Math.PI / 180;
        double earthRadius = 6378;
        Coordinate coordinate = new Coordinate();
        coordinate.setX(earthRadius * latitudeRadians);
        coordinate.setY(earthRadius * longitudeRadians * Math.cos(latitudeRadians));

        return coordinate;
    }

    public static LineString geohashLineToLineString(GeoHash firstGeoHash, GeoHash lastGeoHash){
        LngLatAlt firstLngLatAlt = new LngLatAlt();
        firstLngLatAlt.setLatitude(firstGeoHash.getOriginatingPoint().getLatitude());
        firstLngLatAlt.setLongitude(firstGeoHash.getOriginatingPoint().getLongitude());

        LngLatAlt secondLngLatAlt = new LngLatAlt();
        secondLngLatAlt.setLatitude(lastGeoHash.getOriginatingPoint().getLatitude());
        secondLngLatAlt.setLongitude(lastGeoHash.getOriginatingPoint().getLongitude());

        List<LngLatAlt> geoHashLineList = new ArrayList<>();
        geoHashLineList.add(firstLngLatAlt);
        geoHashLineList.add(secondLngLatAlt);

        LineListSorted lineListSorted = new LineListSorted(geoHashLineList);
        List<LngLatAlt> sortedPolygonList = lineListSorted.getPolygonList();

        Coordinate[] coordinates = new Coordinate[2];
        coordinates[0] = lngLatAltToCoordinate(sortedPolygonList.get(0));
        coordinates[1] = lngLatAltToCoordinate(sortedPolygonList.get(1));

        LineString lineString = new GeometryFactory().createLineString(coordinates);
        return lineString;
    }
}
