package ru.kg.geojson.separator;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

import java.util.ArrayList;
import java.util.List;

public class GeoJsonEquatorSeparator {

    public static FeatureCollection separateEquator(FeatureCollection featureCollection, int featureIndex) {
        PolygonsListSeparated separatedLists = setPolygonLists(featureCollection, featureIndex);
        FeatureCollection equatorFeatureCollection = getPolygonsToFeatureCollection(separatedLists);

        return equatorFeatureCollection;
    }

    private static PolygonsListSeparated setPolygonLists(FeatureCollection featureCollection, int featureIndex){
        List<LngLatAlt> separatedPoints = getSeparatedPoints(featureCollection, featureIndex);

        List<LngLatAlt> plusList = new ArrayList<>();
        List<LngLatAlt> minusList = new ArrayList<>();

        for(int i = 0; i < separatedPoints.size(); i++){
            double sign;
            sign = Math.signum(separatedPoints.get(i).getLatitude());

            if(sign == 1){
                plusList.add(separatedPoints.get(i));
            }
            else if(sign == -1){
                minusList.add(separatedPoints.get(i));
            }
            else{
                plusList.add(separatedPoints.get(i));
                minusList.add(separatedPoints.get(i));
            }
        }

        PolygonsListSeparated separatedLists = divideFigures(plusList, minusList);

        return separatedLists;
    }
    private static PolygonsListSeparated divideFigures(List<LngLatAlt> plusList, List<LngLatAlt> minusList){
        PolygonsListSeparated separatedLists = new PolygonsListSeparated();

        separatedLists.addPolygonsListSeparated(divideFigure(plusList));
        separatedLists.addPolygonsListSeparated(divideFigure(minusList));

        return separatedLists;
    }
    private static PolygonsListSeparated divideFigure(List<LngLatAlt> list){
        PolygonsListSeparated polyLists = new PolygonsListSeparated();

        if(list.get(0).equals(list.get(list.size() - 1))) {
            list.remove(list.size() - 1);
        }

        boolean isFigureConvex = isFigureConvex(list);

        if(!isFigureConvex){
            polyLists.addPolygonList(list);
        }
        else{
            polyLists = divideConvexList(list);
        }

        return polyLists;
    }
    private static PolygonsListSeparated divideConvexList(List<LngLatAlt> list){
        PolygonsListSeparated separatedLists = new PolygonsListSeparated();

        List<LngLatAlt> firstList = new ArrayList<>();
        int j = 0;
        while(j < list.size() && list.get(j).getLatitude() != 0){
            firstList.add(list.get(j));

            j++;
        }
        if(firstList.size() != 0) {
            firstList.add(list.get(j));
            j++;
        }

        List<LngLatAlt> nextList = new ArrayList<>();
        for(int i = j; i < list.size(); i++){
            nextList.add(list.get(i));

            if(list.get(i).getLatitude() == 0 && nextList.size() > 1){
                separatedLists.addPolygonList(nextList);
                nextList = new ArrayList<>();
            }

        }

        if(firstList.size() != 0) {
            nextList.addAll(firstList);
            separatedLists.addPolygonList(nextList);
        }

        return separatedLists;
    }
    private static boolean isFigureConvex(List<LngLatAlt> list){
        boolean isConvex = false;

        double longestZeroLatitudeLineSize = 0;
        LngLatAlt farLeftCoord = null;
        LngLatAlt farRightCoord = null;

        double sumZeroLatitudeLinesSize = 0;
        LngLatAlt prevLngLatAlt = null;

        for(int i = 0; i < list.size(); i++){
            LngLatAlt currentCoord = list.get(i);

            if(currentCoord.getLatitude() == 0) {
                if (farLeftCoord == null) {
                    farLeftCoord = currentCoord;
                }
                if (farRightCoord == null) {
                    farRightCoord = currentCoord;
                }
                if (farLeftCoord.getLongitude() > currentCoord.getLongitude()) {
                    farLeftCoord = currentCoord;
                }
                if (farRightCoord.getLongitude() < currentCoord.getLongitude()) {
                    farRightCoord = currentCoord;
                }
            }


            if(prevLngLatAlt != null){
                if(prevLngLatAlt.getLatitude() == 0 && list.get(i).getLatitude() == 0){
                    sumZeroLatitudeLinesSize += Math.abs(Math.abs(prevLngLatAlt.getLongitude()) - Math.abs(list.get(i).getLongitude()));
                }
            }
            prevLngLatAlt = list.get(i);
        }


        if(farRightCoord != null && farLeftCoord != null) {
            longestZeroLatitudeLineSize = Math.abs(Math.abs(farRightCoord.getLongitude()) - Math.abs(farLeftCoord.getLongitude()));
        }


        if(list.get(0).getLatitude() == 0 && list.get(list.size() - 1).getLatitude() == 0){
            sumZeroLatitudeLinesSize += Math.abs(Math.abs(list.get(0).getLongitude()) - Math.abs(list.get(list.size() - 1).getLongitude()));
        }



        if(sumZeroLatitudeLinesSize > longestZeroLatitudeLineSize){
            isConvex = true;
        }
        return isConvex;
    }


    private static List<LngLatAlt> getSeparatedPoints(FeatureCollection featureCollection, int featureIndex){
        List<LngLatAlt> separatedPoints = new ArrayList<>();
        double sign;
        Polygon originPolygon = (Polygon) featureCollection.getFeatures().get(featureIndex).getGeometry();
        List<LngLatAlt> originPoints = originPolygon.getExteriorRing();

        sign = Math.signum(originPoints.get(0).getLatitude());
        separatedPoints.add(originPoints.get(0));
        for(int i = 1; i < originPoints.size(); i++){
            if(sign != Math.signum(originPoints.get(i).getLatitude())){
                double longitude = getMedianLongitude(originPoints.get(i).getLatitude(), originPoints.get(i).getLongitude(),
                        originPoints.get(i - 1).getLatitude(), originPoints.get(i - 1).getLongitude());
                LngLatAlt separator = new LngLatAlt(longitude, 0);

                separatedPoints.add(separator);
                sign = Math.signum(originPoints.get(i).getLatitude());
            }
            separatedPoints.add(originPoints.get(i));
        }

        return separatedPoints;
    }
    private static double getMedianLongitude(double latitudeDegrees1, double longitudeDegrees1, double latitudeDegrees2, double longitudeDegrees2){
        double finalLongitude;

        double sumLat = Math.abs(latitudeDegrees1) + Math.abs(latitudeDegrees2);
        double latFragment;
        if(sumLat != 0){
            latFragment = 100 / sumLat;
        }
        else{
            latFragment = 0;
        }
        double lat1Frag = Math.abs(latitudeDegrees1) * latFragment / 100;
        double lat2Frag = Math.abs(latitudeDegrees2) * latFragment / 100;

        finalLongitude = (longitudeDegrees1 * lat2Frag + longitudeDegrees2 * lat1Frag);

        return finalLongitude;
    }
    private static FeatureCollection getPolygonsToFeatureCollection(PolygonsListSeparated separatedLists){
        FeatureCollection equatorFeatureCollection = new FeatureCollection();

        for(int i = 0; i < separatedLists.size(); i++){
            Polygon polygon = new Polygon();
            polygon.setExteriorRing(separatedLists.getPolygonList(i));
            Feature feature = new Feature();
            feature.setGeometry(polygon);
            equatorFeatureCollection.add(feature);
        }

        return equatorFeatureCollection;
    }

}
