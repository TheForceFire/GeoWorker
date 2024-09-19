package org.example;


import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        GeoJson geoJson;

        try {
            geoJson = new GeoJson("src/main/resources/GeoJsonMap2");
            geoJson.separateGeoJson();
            System.out.println("Separation is done, check _modified version of file in same directory as you provided");
        }
        catch (IOException e){
            System.err.println("Error while working with files:\n" + e.getMessage());
        }
    }
}