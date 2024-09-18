package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.FeatureCollection;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class GeoJsonFileManager {

    public static FeatureCollection loadGeoJsonFile(String path) throws IOException {
        FeatureCollection featureCollection = new ObjectMapper().readValue(new FileInputStream(path), FeatureCollection.class);
        return featureCollection;
    }
    public static void writeGeoJsonFile(String path, FeatureCollection featureCollection) throws IOException {
        String geoJsonString = new ObjectMapper().writeValueAsString(featureCollection);
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(geoJsonString);
        fileWriter.close();
    }
}
