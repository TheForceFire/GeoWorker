package org.example;

import org.geojson.LngLatAlt;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<LngLatAlt> geoJsonArray;

        try {
            geoJsonArray = GeoJsonManager.loadGeoJson("src/main/resources/GeoJsonMap1");
        }
        catch (IOException e){
            
        }
    }
}