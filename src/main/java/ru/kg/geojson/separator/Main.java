package ru.kg.geojson.separator;


import java.io.IOException;


public class Main {
    public static void main(String[] args) {

        try {
            GeoJsonUtil.calculateAndWriteSeparatedGeoJson(GeoJsonFileManager.loadGeoJsonFile("src/main/resources/GeoJsonMap4"),
                    "src/main/resources/GeoJsonMap_modified");
        }
        catch (IOException e){
            System.err.println("Error while working with files:\n" + e.getMessage());
        }
    }
}