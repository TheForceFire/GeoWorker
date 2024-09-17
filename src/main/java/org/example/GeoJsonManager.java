package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class GeoJsonManager {
    public static List<LngLatAlt> loadGeoJson(String path) throws IOException {
        FeatureCollection featureCollection = new ObjectMapper().readValue(new FileInputStream(path), FeatureCollection.class);
        org.geojson.Polygon p = (org.geojson.Polygon) featureCollection.getFeatures().get(0).getGeometry();
        List<LngLatAlt> lngLatAlts = p.getExteriorRing();
        return lngLatAlts;
    }
}
