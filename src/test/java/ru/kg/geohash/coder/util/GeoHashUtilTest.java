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

import static org.assertj.core.api.Assertions.assertThat;

public class GeoHashUtilTest {

    @Test
    void geoJsonToGeoHashTest1() throws IOException {
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map1.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map1Expected.txt");
        BufferedReader bufferedReaderExpectedMapFile = new BufferedReader(new InputStreamReader(inputStreamExpectedMapFile))) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            List<String> actualGeohashList = GeoHashUtil.featureCollectionToGeoHash(inputFeatureCollection, 2);
            List<String> expectedGeohashList = bufferedReaderExpectedMapFile.lines().flatMap(line -> Arrays.stream(line.split(","))).map(String::trim).toList();

            assertThat(actualGeohashList).containsExactlyInAnyOrderElementsOf(expectedGeohashList);
        }
    }

    @Test
    void geoJsonToGeoHashTest2() throws IOException {
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map2.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map2Expected.txt");
        BufferedReader bufferedReaderExpectedMapFile = new BufferedReader(new InputStreamReader(inputStreamExpectedMapFile))) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            List<String> actualGeohashList = GeoHashUtil.featureCollectionToGeoHash(inputFeatureCollection, 2);
            List<String> expectedGeohashList = bufferedReaderExpectedMapFile.lines().flatMap(line -> Arrays.stream(line.split(","))).map(String::trim).toList();

            assertThat(actualGeohashList).containsExactlyInAnyOrderElementsOf(expectedGeohashList);
        }
    }

    @Test
    void geoJsonToGeoHashTest3() throws IOException {
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map3.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map3Expected.txt");
        BufferedReader bufferedReaderExpectedMapFile = new BufferedReader(new InputStreamReader(inputStreamExpectedMapFile))) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            List<String> actualGeohashList = GeoHashUtil.featureCollectionToGeoHash(inputFeatureCollection, 2);
            List<String> expectedGeohashList = bufferedReaderExpectedMapFile.lines().flatMap(line -> Arrays.stream(line.split(","))).map(String::trim).toList();

            assertThat(actualGeohashList).containsExactlyInAnyOrderElementsOf(expectedGeohashList);
        }
    }

    @Test
    void geoJsonToGeoHashTest4() throws IOException {
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map4.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geohashUtilTest/Map4Expected.txt");
        BufferedReader bufferedReaderExpectedMapFile = new BufferedReader(new InputStreamReader(inputStreamExpectedMapFile))) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            List<String> actualGeohashList = GeoHashUtil.featureCollectionToGeoHash(inputFeatureCollection, 2);
            List<String> expectedGeohashList = bufferedReaderExpectedMapFile.lines().flatMap(line -> Arrays.stream(line.split(","))).map(String::trim).toList();

            assertThat(actualGeohashList).containsExactlyInAnyOrderElementsOf(expectedGeohashList);
        }
    }
}
