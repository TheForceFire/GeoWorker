package ru.kg.geojson.separator.util;

import org.geojson.FeatureCollection;
import org.junit.jupiter.api.Test;
import ru.kg.io.GeoJsonFileManager;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeoJsonSeparatorTest {

    @Test
    void separationTest1() throws IOException{
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map1.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map1Expected.json");

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
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map2.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map2Expected.json");

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
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map3.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map3Expected.json");

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
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map4.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map4Expected.json");

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
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map5.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map5Expected.json");

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
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map6.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map6Expected.json");

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
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map7.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map7Expected.json");

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
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map8.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map8Expected.json");

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
