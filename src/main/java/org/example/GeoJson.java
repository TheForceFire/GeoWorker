package org.example;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

enum Signs{
    SIGN_PLUS,
    SIGN_MINUS,
    SIGN_ZERO
}

public class GeoJson {
    private FeatureCollection finalFeatureCollection;
    private FeatureCollection originFeatureCollection;
    private String path;

    public GeoJson(String path) throws IOException {
        finalFeatureCollection = new FeatureCollection();
        originFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(path);

        this.path = path;
    }

    public void separateGeoJson() throws IOException {
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
        writeGeoJson(path + "_modified");
    }
    private FeatureCollection separateEquator(FeatureCollection featureCollection, int featureIndex) {
        FeatureCollection equatorFeatureCollection = new FeatureCollection();

        List<LngLatAlt> plusList = new ArrayList<>();
        List<LngLatAlt> minusList = new ArrayList<>();
        setEquatorNewLists(plusList, minusList, featureCollection, featureIndex);
        addEquatorToFeatureCollection(plusList, minusList, equatorFeatureCollection);

        return equatorFeatureCollection;
    }
    private FeatureCollection separatePrimeMeridian(FeatureCollection featureCollection, int featureIndex){
        FeatureCollection primeMeridianFeatureCollection = new FeatureCollection();

        List<LngLatAlt> plusList = new ArrayList<>();
        List<LngLatAlt> minusList = new ArrayList<>();
        setPrimeMeridianNewLists(plusList, minusList, featureCollection, featureIndex);
        addPrimeMeridianToFeatureCollection(plusList, minusList, primeMeridianFeatureCollection);

        return primeMeridianFeatureCollection;
    }

    private void setPrimeMeridianNewLists(List<LngLatAlt> plusList, List<LngLatAlt> minusList, FeatureCollection featureCollection, int featureIndex){
        Signs sign;
        List<LngLatAlt> separatedPoints = getPrimeMeridianSeparatedPoints(featureCollection, featureIndex);
        for(int i = 0; i < separatedPoints.size(); i++){
            sign = getSign(separatedPoints.get(i).getLongitude());

            if(sign == Signs.SIGN_PLUS){
                plusList.add(separatedPoints.get(i));
            }
            else if(sign == Signs.SIGN_MINUS){
                minusList.add(separatedPoints.get(i));
            }
            else{
                plusList.add(separatedPoints.get(i));
                minusList.add(separatedPoints.get(i));
            }
        }
    }
    private List<LngLatAlt> getPrimeMeridianSeparatedPoints(FeatureCollection featureCollection, int featureIndex){
        List<LngLatAlt> separatedPoints = new ArrayList<>();
        Signs sign;
        Polygon originPolygon = (Polygon) featureCollection.getFeatures().get(featureIndex).getGeometry();
        List<LngLatAlt> originPoints = originPolygon.getExteriorRing();

        sign = getSign(originPoints.get(0).getLongitude());
        separatedPoints.add(originPoints.get(0));
        for(int i = 1; i < originPoints.size(); i++){
            if(sign != getSign(originPoints.get(i).getLongitude())){
                double latitude = medianLatitude(originPoints.get(i).getLatitude(), originPoints.get(i).getLongitude(),
                        originPoints.get(i - 1).getLatitude(), originPoints.get(i - 1).getLongitude());
                LngLatAlt separator = new LngLatAlt(0, latitude);

                separatedPoints.add(separator);
                sign = getSign(originPoints.get(i).getLongitude());
            }
            separatedPoints.add(originPoints.get(i));
        }

        return separatedPoints;
    }
    private double medianLatitude(double latitudeDegrees1, double longitudeDegrees1, double latitudeDegrees2, double longitudeDegrees2){
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
    private void addPrimeMeridianToFeatureCollection(List<LngLatAlt> plusList, List<LngLatAlt> minusList, FeatureCollection primeMeridianFeatureCollection){
        if(plusList.size() != 0){
            plusList.add(plusList.get(0));
            Polygon plusPolygon = new Polygon();
            plusPolygon.setExteriorRing(plusList);
            Feature plusFeature = new Feature();
            plusFeature.setGeometry(plusPolygon);
            primeMeridianFeatureCollection.add(plusFeature);
        }
        if(minusList.size() != 0){
            minusList.add(minusList.get(0));
            Polygon minusPolygon = new Polygon();
            minusPolygon.setExteriorRing(minusList);
            Feature minusFeature = new Feature();
            minusFeature.setGeometry(minusPolygon);
            primeMeridianFeatureCollection.add(minusFeature);
        }
    }



    private void setEquatorNewLists(List<LngLatAlt> plusList, List<LngLatAlt> minusList, FeatureCollection featureCollection, int featureIndex){
        Signs sign;
        List<LngLatAlt> separatedPoints = getEquatorSeparatedPoints(featureCollection, featureIndex);
        for(int i = 0; i < separatedPoints.size(); i++){
            sign = getSign(separatedPoints.get(i).getLatitude());

            if(sign == Signs.SIGN_PLUS){
                plusList.add(separatedPoints.get(i));
            }
            else if(sign == Signs.SIGN_MINUS){
                minusList.add(separatedPoints.get(i));
            }
            else{
                plusList.add(separatedPoints.get(i));
                minusList.add(separatedPoints.get(i));
            }
        }
    }
    private List<LngLatAlt> getEquatorSeparatedPoints(FeatureCollection featureCollection, int featureIndex){
        List<LngLatAlt> separatedPoints = new ArrayList<>();
        Signs sign;
        Polygon originPolygon = (Polygon) featureCollection.getFeatures().get(featureIndex).getGeometry();
        List<LngLatAlt> originPoints = originPolygon.getExteriorRing();

        sign = getSign(originPoints.get(0).getLatitude());
        separatedPoints.add(originPoints.get(0));
        for(int i = 1; i < originPoints.size(); i++){
            if(sign != getSign(originPoints.get(i).getLatitude())){
                double longitude = medianLongitude(originPoints.get(i).getLatitude(), originPoints.get(i).getLongitude(),
                        originPoints.get(i - 1).getLatitude(), originPoints.get(i - 1).getLongitude());
                LngLatAlt separator = new LngLatAlt(longitude, 0);

                separatedPoints.add(separator);
                sign = getSign(originPoints.get(i).getLatitude());
            }
            separatedPoints.add(originPoints.get(i));
        }

        return separatedPoints;
    }
    private double medianLongitude(double latitudeDegrees1, double longitudeDegrees1, double latitudeDegrees2, double longitudeDegrees2){
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
        if(finalLongitude < -180) finalLongitude += 360;
        if(finalLongitude > 180) finalLongitude -= 360;

        return finalLongitude;
    }
    private void addEquatorToFeatureCollection(List<LngLatAlt> plusList, List<LngLatAlt> minusList, FeatureCollection equatorFeatureCollection){
        if(plusList.size() != 0){
            plusList.add(plusList.get(0));
            Polygon plusPolygon = new Polygon();
            plusPolygon.setExteriorRing(plusList);
            Feature plusFeature = new Feature();
            plusFeature.setGeometry(plusPolygon);
            equatorFeatureCollection.add(plusFeature);
        }
        if(minusList.size() != 0){
            minusList.add(minusList.get(0));
            Polygon minusPolygon = new Polygon();
            minusPolygon.setExteriorRing(minusList);
            Feature minusFeature = new Feature();
            minusFeature.setGeometry(minusPolygon);
            equatorFeatureCollection.add(minusFeature);
        }
    }

    private Signs getSign(double value){
        if(value < 0) return Signs.SIGN_MINUS;
        else if(value > 0) return Signs.SIGN_PLUS;
        return Signs.SIGN_ZERO;
    }

    private void writeGeoJson(String path) throws IOException {
        GeoJsonFileManager.writeGeoJsonFile(path, finalFeatureCollection);
    }
}
