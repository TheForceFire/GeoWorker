package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GeoJson {
    private static final double latitude_MIN_Radians = -(Math.PI / 2);
    private static final double latitude_MAX_Radians = (Math.PI / 2);
    private static final double longitude_MIN_Radians = -Math.PI;
    private static final double longitude_MAX_Radians = Math.PI;
    private static final double latitude_MIN_Degrees = latitude_MIN_Radians * 180 / Math.PI;
    private static final double latitude_MAX_Degrees = latitude_MAX_Radians * 180 / Math.PI;
    private static final double longitude_MIN_Degrees = longitude_MIN_Radians * 180 / Math.PI;
    private static final double longitude_MAX_Degrees = longitude_MAX_Radians * 180 / Math.PI;

    private FeatureCollection featureCollection;
    private List<LngLatAlt> originPoints;

    public GeoJson(String path) throws IOException {
        featureCollection = new FeatureCollection();

        FeatureCollection originFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(path);
        Polygon originPolygon = (Polygon) originFeatureCollection.getFeatures().get(0).getGeometry();
        originPoints = originPolygon.getExteriorRing();

        featureCollection = originFeatureCollection;
        writeGeoJson(path + "123");
    }



    public void writeGeoJson(String path) throws IOException {
        GeoJsonFileManager.writeGeoJsonFile(path, featureCollection);
    }
}
