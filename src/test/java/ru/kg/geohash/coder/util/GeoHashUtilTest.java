package ru.kg.geohash.coder.util;

import org.geojson.FeatureCollection;
import org.junit.jupiter.api.Test;
import ru.kg.io.GeoJsonFileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeoHashUtilTest {

    @Test
    void geoJsonToGeoHashTest1() throws IOException {
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map1.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map1Expected.txt");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();

            List<String> actualGeohashList = GeoHashUtil.featureCollectionToGeoHash(inputFeatureCollection, 2);

            List<String> expectedGeohashList = new BufferedReader(new InputStreamReader(inputStreamExpectedMapFile)).lines()
                    .flatMap(line -> Arrays.stream(line.split(","))).map(String::trim).toList();
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoHashUtil.compareGeoHashStringLists(actualGeohashList, expectedGeohashList);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }

    @Test
    void geoJsonToGeoHashTest2() throws IOException {
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map2.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map2Expected.txt");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();

            List<String> actualGeohashList = GeoHashUtil.featureCollectionToGeoHash(inputFeatureCollection, 2);

            List<String> expectedGeohashList = new BufferedReader(new InputStreamReader(inputStreamExpectedMapFile)).lines()
                    .flatMap(line -> Arrays.stream(line.split(","))).map(String::trim).toList();
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoHashUtil.compareGeoHashStringLists(actualGeohashList, expectedGeohashList);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }

    @Test
    void geoJsonToGeoHashTest3() throws IOException {
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map3.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map3Expected.txt");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();

            List<String> actualGeohashList = GeoHashUtil.featureCollectionToGeoHash(inputFeatureCollection, 2);

            List<String> expectedGeohashList = new BufferedReader(new InputStreamReader(inputStreamExpectedMapFile)).lines()
                    .flatMap(line -> Arrays.stream(line.split(","))).map(String::trim).toList();
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoHashUtil.compareGeoHashStringLists(actualGeohashList, expectedGeohashList);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }

    @Test
    void geoJsonToGeoHashTest4() throws IOException {
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map4.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map4Expected.txt");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();

            List<String> actualGeohashList = GeoHashUtil.featureCollectionToGeoHash(inputFeatureCollection, 2);

            List<String> expectedGeohashList = new BufferedReader(new InputStreamReader(inputStreamExpectedMapFile)).lines()
                    .flatMap(line -> Arrays.stream(line.split(","))).map(String::trim).toList();
            inputStreamExpectedMapFile.close();

            boolean isEquals = GeoHashUtil.compareGeoHashStringLists(actualGeohashList, expectedGeohashList);
            assertTrue(isEquals, "Feature collections are not the same!");
        }
        else{
            throw new IllegalArgumentException("File not found");
        }
    }
}
