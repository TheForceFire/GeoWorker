package ru.kg.geojson.separator;


import java.io.IOException;


public class Main {
    public static void main(String[] args) {

        try {
            GeoJsonUtil.calculateAndWriteSeparatedGeoJson(GeoJsonFileManager.loadGeoJsonFile("src/main/resources/GeoJsonMap1"),
                    "src/main/resources/GeoJsonMap_modified");
            System.out.println("Separation is done, check _modified version of file in same directory as you provided");
        }
        catch (IOException e){
            System.err.println("Error while working with files:\n" + e.getMessage());
        }
    }
}