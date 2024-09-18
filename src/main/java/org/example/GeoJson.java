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


        finalFeatureCollection = equatorFeatureCollection;
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
        for(int i = 1; i < originPoints.size() - 1; i++){
            if(sign != getSign(originPoints.get(i).getLatitude())){
                double longitude = (originPoints.get(i).getLongitude() + originPoints.get(i - 1).getLongitude()) / 2;
                LngLatAlt separator = new LngLatAlt(longitude, 0);

                separatedPoints.add(separator);
                sign = getSign(originPoints.get(i).getLatitude());
            }
            separatedPoints.add(originPoints.get(i));
        }

        return separatedPoints;
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
