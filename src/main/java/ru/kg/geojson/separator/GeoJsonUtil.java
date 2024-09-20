package ru.kg.geojson.separator;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeoJsonUtil {
    public static void calculateAndWriteSeparatedGeoJson(FeatureCollection originFeatureCollection, String pathToSave) throws IOException
    {
        FeatureCollection finalFeatureCollection = separateGeoJson(originFeatureCollection);
        GeoJsonFileManager.writeGeoJsonFile(pathToSave, finalFeatureCollection);
    }


    private static FeatureCollection separateGeoJson(FeatureCollection originFeatureCollection) {
        FeatureCollection finalFeatureCollection;

        FeatureCollection equatorFeatureCollection = new FeatureCollection();
        for(int i = 0; i < originFeatureCollection.getFeatures().size(); i++){
            FeatureCollection temp = separateEquator(originFeatureCollection, i);
            for(int j = 0; j < temp.getFeatures().size(); j++) {
                equatorFeatureCollection.add(temp.getFeatures().get(j));
            }
        }

        FeatureCollection primeMeridianFeatureCollection = new FeatureCollection();
        for(int i = 0; i < equatorFeatureCollection.getFeatures().size(); i++){
            FeatureCollection temp = separatePrimeMeridian(equatorFeatureCollection, i);
            for(int j = 0; j < temp.getFeatures().size(); j++) {
                primeMeridianFeatureCollection.add(temp.getFeatures().get(j));
            }
        }

        finalFeatureCollection = primeMeridianFeatureCollection;
        return finalFeatureCollection;
    }
    private static FeatureCollection separateEquator(FeatureCollection featureCollection, int featureIndex) {
        FeatureCollection equatorFeatureCollection = new FeatureCollection();

        List<LngLatAlt> plusList = new ArrayList<>();
        List<LngLatAlt> minusList = new ArrayList<>();
        setEquatorNewLists(plusList, minusList, featureCollection, featureIndex);
        addEquatorToFeatureCollection(plusList, minusList, equatorFeatureCollection);

        return equatorFeatureCollection;
    }
    private static FeatureCollection separatePrimeMeridian(FeatureCollection featureCollection, int featureIndex){
        FeatureCollection primeMeridianFeatureCollection = new FeatureCollection();

        List<LngLatAlt> plusList = new ArrayList<>();
        List<LngLatAlt> minusList = new ArrayList<>();
        setPrimeMeridianNewLists(plusList, minusList, featureCollection, featureIndex);
        addPrimeMeridianToFeatureCollection(plusList, minusList, primeMeridianFeatureCollection);

        return primeMeridianFeatureCollection;
    }

    private static void setPrimeMeridianNewLists(List<LngLatAlt> plusList, List<LngLatAlt> minusList, FeatureCollection featureCollection, int featureIndex){
        List<LngLatAlt> separatedPoints = getPrimeMeridianSeparatedPoints(featureCollection, featureIndex);
        for(int i = 0; i < separatedPoints.size(); i++){
            double sign;

            sign = Math.signum(separatedPoints.get(i).getLongitude());

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
    }
    private static List<LngLatAlt> getPrimeMeridianSeparatedPoints(FeatureCollection featureCollection, int featureIndex){
        List<LngLatAlt> separatedPoints = new ArrayList<>();
        double sign;
        Polygon originPolygon = (Polygon) featureCollection.getFeatures().get(featureIndex).getGeometry();
        List<LngLatAlt> originPoints = originPolygon.getExteriorRing();

        sign = Math.signum(originPoints.get(0).getLongitude());
        separatedPoints.add(originPoints.get(0));
        for(int i = 1; i < originPoints.size(); i++){
            if(sign != Math.signum(originPoints.get(i).getLongitude())){
                double latitude = medianLatitude(originPoints.get(i).getLatitude(), originPoints.get(i).getLongitude(),
                        originPoints.get(i - 1).getLatitude(), originPoints.get(i - 1).getLongitude());
                LngLatAlt separator = new LngLatAlt(0, latitude);

                separatedPoints.add(separator);
                sign = Math.signum(originPoints.get(i).getLongitude());
            }
            separatedPoints.add(originPoints.get(i));
        }

        return separatedPoints;
    }
    private static double medianLatitude(double latitudeDegrees1, double longitudeDegrees1, double latitudeDegrees2, double longitudeDegrees2){
        double finalLatitude;
        double sumLong = Math.abs(longitudeDegrees1) + Math.abs(longitudeDegrees2);
        double longFragment;
        if(sumLong != 0){
            longFragment = 100 / sumLong;
        }
        else{
            longFragment = 0;
        }
        double long1Frag = Math.abs(longitudeDegrees1) * longFragment / 100;
        double long2Frag = Math.abs(longitudeDegrees2) * longFragment / 100;

        finalLatitude = (latitudeDegrees1 * long2Frag + latitudeDegrees2 * long1Frag);
        return finalLatitude;
    }
    private static void addPrimeMeridianToFeatureCollection(List<LngLatAlt> plusList, List<LngLatAlt> minusList, FeatureCollection primeMeridianFeatureCollection){
        if(plusList.size() != 0){
            if(!plusList.get(0).equals(plusList.get(plusList.size() - 1))) {
                plusList.add(plusList.get(0));
            }
            Polygon plusPolygon = new Polygon();
            plusPolygon.setExteriorRing(plusList);
            Feature plusFeature = new Feature();
            plusFeature.setGeometry(plusPolygon);
            primeMeridianFeatureCollection.add(plusFeature);
        }
        if(minusList.size() != 0){
            if(!minusList.get(0).equals(minusList.get(minusList.size() - 1))) {
                minusList.add(minusList.get(0));
            }
            Polygon minusPolygon = new Polygon();
            minusPolygon.setExteriorRing(minusList);
            Feature minusFeature = new Feature();
            minusFeature.setGeometry(minusPolygon);
            primeMeridianFeatureCollection.add(minusFeature);
        }
    }



    private static void setEquatorNewLists(List<LngLatAlt> plusList, List<LngLatAlt> minusList, FeatureCollection featureCollection, int featureIndex){
        List<LngLatAlt> separatedPoints = getEquatorSeparatedPoints(featureCollection, featureIndex);
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
    }
    private static List<LngLatAlt> getEquatorSeparatedPoints(FeatureCollection featureCollection, int featureIndex){
        List<LngLatAlt> separatedPoints = new ArrayList<>();
        double sign;
        Polygon originPolygon = (Polygon) featureCollection.getFeatures().get(featureIndex).getGeometry();
        List<LngLatAlt> originPoints = originPolygon.getExteriorRing();

        sign = Math.signum(originPoints.get(0).getLatitude());
        separatedPoints.add(originPoints.get(0));
        for(int i = 1; i < originPoints.size(); i++){
            if(sign != Math.signum(originPoints.get(i).getLatitude())){
                double longitude = medianLongitude(originPoints.get(i).getLatitude(), originPoints.get(i).getLongitude(),
                        originPoints.get(i - 1).getLatitude(), originPoints.get(i - 1).getLongitude());
                LngLatAlt separator = new LngLatAlt(longitude, 0);

                separatedPoints.add(separator);
                sign = Math.signum(originPoints.get(i).getLatitude());
            }
            separatedPoints.add(originPoints.get(i));
        }

        return separatedPoints;
    }
    private static double medianLongitude(double latitudeDegrees1, double longitudeDegrees1, double latitudeDegrees2, double longitudeDegrees2){
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
    private static void addEquatorToFeatureCollection(List<LngLatAlt> plusList, List<LngLatAlt> minusList, FeatureCollection equatorFeatureCollection){
        if(plusList.size() != 0){
            if(!plusList.get(0).equals(plusList.get(plusList.size() - 1))) {
                plusList.add(plusList.get(0));
            }
            Polygon plusPolygon = new Polygon();
            plusPolygon.setExteriorRing(plusList);
            Feature plusFeature = new Feature();
            plusFeature.setGeometry(plusPolygon);
            equatorFeatureCollection.add(plusFeature);
        }
        if(minusList.size() != 0){
            if(!minusList.get(0).equals(minusList.get(minusList.size() - 1))) {
                minusList.add(minusList.get(0));
            }
            Polygon minusPolygon = new Polygon();
            minusPolygon.setExteriorRing(minusList);
            Feature minusFeature = new Feature();
            minusFeature.setGeometry(minusPolygon);
            equatorFeatureCollection.add(minusFeature);
        }
    }
}
