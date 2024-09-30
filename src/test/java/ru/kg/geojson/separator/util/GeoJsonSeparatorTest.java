package ru.kg.geojson.separator.util;

import org.geojson.FeatureCollection;
import org.junit.jupiter.api.Test;
import ru.kg.geojson.separator.io.GeoJsonFileManager;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeoJsonSeparatorTest {

    @Test
    void separationTest1() throws IOException{
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("Map1.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("Map1Expected.json");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoJsonUtil.compareFeatures(actualFeatureCollection, expectedFeatureCollection);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }

    @Test
    void separationTest2() throws IOException{
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("Map2.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("Map2Expected.json");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoJsonUtil.compareFeatures(actualFeatureCollection, expectedFeatureCollection);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }

    @Test
    void separationTest3() throws IOException{
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("Map3.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("Map3Expected.json");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoJsonUtil.compareFeatures(actualFeatureCollection, expectedFeatureCollection);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }

    @Test
    void separationTest4() throws IOException{
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("Map4.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("Map4Expected.json");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoJsonUtil.compareFeatures(actualFeatureCollection, expectedFeatureCollection);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }

    @Test
    void separationTest5() throws IOException{
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("Map5.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("Map5Expected.json");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoJsonUtil.compareFeatures(actualFeatureCollection, expectedFeatureCollection);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }

    @Test
    void separationTest6() throws IOException{
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("Map6.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("Map6Expected.json");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoJsonUtil.compareFeatures(actualFeatureCollection, expectedFeatureCollection);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }

    @Test
    void separationTest7() throws IOException{
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("Map7.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("Map7Expected.json");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoJsonUtil.compareFeatures(actualFeatureCollection, expectedFeatureCollection);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }

    @Test
    void separationTest8() throws IOException{
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("Map8.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("Map8Expected.json");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoJsonUtil.compareFeatures(actualFeatureCollection, expectedFeatureCollection);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }
}
