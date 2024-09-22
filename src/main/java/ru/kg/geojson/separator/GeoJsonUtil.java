package ru.kg.geojson.separator;

import org.geojson.FeatureCollection;

import java.io.IOException;


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
