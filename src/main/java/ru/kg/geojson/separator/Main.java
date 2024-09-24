package ru.kg.geojson.separator;


import org.geojson.FeatureCollection;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {

        try {
            FeatureCollection collection = GeoJsonUtil.separateGeoJson(GeoJsonFileManager.loadGeoJsonFile("src/main/resources/GeoJsonMap3"));
            GeoJsonFileManager.writeGeoJsonFile("src/main/resources/GeoJsonMap_modified", collection);
        }
        catch (IOException e){
            System.err.println("Error while working with files:\n" + e.getMessage());
        }
    }
}