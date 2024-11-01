package ru.kg.geojson.separator.util;

import org.geojson.*;

import java.util.List;


public class GeoJsonUtil {

    public static FeatureCollection separateGeoJson(FeatureCollection originFeatureCollection){
        FeatureCollection featureCollectionPolygons = transformFeatureCollectionMultiPolygonsToPolygons(originFeatureCollection);
        FeatureCollection finalFeatureCollection = calculateSeparatedGeoJson(featureCollectionPolygons);
        return finalFeatureCollection;
    }

    private static FeatureCollection transformFeatureCollectionMultiPolygonsToPolygons(FeatureCollection originFeatureCollection){
        FeatureCollection finalFeatureCollection = new FeatureCollection();

        for(int i = 0; i < originFeatureCollection.getFeatures().size(); i++) {
            GeoJsonObject featureGeoJsonObject = originFeatureCollection.getFeatures().get(i).getGeometry();
            Class<? extends GeoJsonObject> objectType = featureGeoJsonObject.getClass();

            if (objectType.getSimpleName().equals("Polygon")) {
                finalFeatureCollection.add(originFeatureCollection.getFeatures().get(i));
            }
            else if (objectType.getSimpleName().equals("MultiPolygon")) {
                org.geojson.MultiPolygon originMultiPolygon = (MultiPolygon) originFeatureCollection.getFeatures().get(i).getGeometry();
                List<List<List<LngLatAlt>>> originPointsList = originMultiPolygon.getCoordinates();

                for(int j = 0; j < originPointsList.size(); j++){
                    Feature featureToAdd = new Feature();
                    Polygon polygonToAdd = new Polygon();
                    polygonToAdd.setExteriorRing(originPointsList.get(j).get(0));
                    featureToAdd.setGeometry(polygonToAdd);

                    finalFeatureCollection.add(featureToAdd);
                }

            }
        }

        return finalFeatureCollection;
    }

    private static FeatureCollection calculateSeparatedGeoJson(FeatureCollection originFeatureCollection) {
        FeatureCollection finalFeatureCollection;

        FeatureCollection equatorFeatureCollection = new FeatureCollection();
        for(int i = 0; i < originFeatureCollection.getFeatures().size(); i++){
            FeatureCollection temp = GeoJsonEquatorSeparator.separateEquator(originFeatureCollection, i);
            for(int j = 0; j < temp.getFeatures().size(); j++) {
                equatorFeatureCollection.add(temp.getFeatures().get(j));
            }
        }

        FeatureCollection primeMeridianFeatureCollection = new FeatureCollection();
        for(int i = 0; i < equatorFeatureCollection.getFeatures().size(); i++){
            FeatureCollection temp = GeoJsonPrimeMeridianSeparator.separatePrimeMeridian(equatorFeatureCollection, i);
            for(int j = 0; j < temp.getFeatures().size(); j++) {
                primeMeridianFeatureCollection.add(temp.getFeatures().get(j));
            }
        }

        finalFeatureCollection = primeMeridianFeatureCollection;
        return finalFeatureCollection;
    }
}
