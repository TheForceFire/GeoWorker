package org.example;


import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        GeoJson geoJson;

        try {
            geoJson = new GeoJson("src/main/resources/GeoJsonMap1");
        }
        catch (IOException e){
            
        }
    }
}