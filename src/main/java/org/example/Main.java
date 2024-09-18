package org.example;


import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        GeoJson geoJson;

        try {
            geoJson = new GeoJson("src/main/resources/GeoJsonMap1");
            geoJson.separateGeoJson();
        }
        catch (IOException e){
            System.err.println("Error while working with files:\n" + e.getMessage());
        }
    }
}