package ru.kg.util;

import org.geojson.FeatureCollection;
import org.junit.jupiter.api.Test;
import ru.kg.geohash.coder.util.GeoHashUtil;
import ru.kg.io.GeoJsonFileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeoWorkerTest {

    @Test
    void geoJsonToSeparatedToGeohashTest1() throws IOException {
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geoWorkerUtilTest/Map1.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geoWorkerUtilTest/Map1Expected.txt");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();

            List<String> actualGeohashList = GeoWorkerUtil.separateFeatureCollectionAndToSingleGeoHashList(inputFeatureCollection, 3);

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
    void geoJsonToSeparatedToGeohashTest2() throws IOException {
        InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geoWorkerUtilTest/Map2SamObl.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geoWorkerUtilTest/Map2SamOblExpected.txt");

        if(inputStreamMapFile != null && inputStreamExpectedMapFile != null) {

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            inputStreamMapFile.close();

            List<String> actualGeohashList = GeoWorkerUtil.separateFeatureCollectionAndToSingleGeoHashList(inputFeatureCollection, 4);

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
