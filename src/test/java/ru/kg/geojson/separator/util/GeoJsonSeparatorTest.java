package ru.kg.geojson.separator.util;

import org.geojson.FeatureCollection;
import org.junit.jupiter.api.Test;
import ru.kg.io.GeoJsonFileManager;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class GeoJsonSeparatorTest {

    @Test
    void separationTest1() throws IOException{
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map1.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map1Expected.json")) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);
            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);

            assertThat(actualFeatureCollection).containsExactlyInAnyOrderElementsOf(expectedFeatureCollection);
        }
    }

    @Test
    void separationTest2() throws IOException{
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map2.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map2Expected.json")) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);
            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);

            assertThat(actualFeatureCollection).containsExactlyInAnyOrderElementsOf(expectedFeatureCollection);
        }
    }

    @Test
    void separationTest3() throws IOException{
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map3.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map3Expected.json")) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);
            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);

            assertThat(actualFeatureCollection).containsExactlyInAnyOrderElementsOf(expectedFeatureCollection);
        }
    }

    @Test
    void separationTest4() throws IOException{
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map4.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map4Expected.json")) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);
            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);

            assertThat(actualFeatureCollection).containsExactlyInAnyOrderElementsOf(expectedFeatureCollection);
        }
    }

    @Test
    void separationTest5() throws IOException{
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map5.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map5Expected.json")) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);
            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);

            assertThat(actualFeatureCollection).containsExactlyInAnyOrderElementsOf(expectedFeatureCollection);
        }
    }

    @Test
    void separationTest6() throws IOException{
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map6.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map6Expected.json")) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);
            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);

            assertThat(actualFeatureCollection).containsExactlyInAnyOrderElementsOf(expectedFeatureCollection);
        }
    }

    @Test
    void separationTest7() throws IOException{
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map7.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map7Expected.json")) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);
            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);

            assertThat(actualFeatureCollection).containsExactlyInAnyOrderElementsOf(expectedFeatureCollection);
        }
    }

    @Test
    void separationTest8() throws IOException{
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map8.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("separationTest/Map8Expected.json")) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            FeatureCollection actualFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);
            FeatureCollection expectedFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamExpectedMapFile);

            assertThat(actualFeatureCollection).containsExactlyInAnyOrderElementsOf(expectedFeatureCollection);
        }
    }
}
