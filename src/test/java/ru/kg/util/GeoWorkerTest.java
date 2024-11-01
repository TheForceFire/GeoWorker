package ru.kg.util;

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

public class GeoWorkerTest {

    @Test
    void geoJsonToSeparatedToGeoHashTest1() throws IOException {
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geoWorkerUtilTest/Map1.json");
            InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geoWorkerUtilTest/Map1Expected.txt");
            BufferedReader bufferedReaderExpectedMapFile = new BufferedReader(new InputStreamReader(inputStreamExpectedMapFile))){

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            List<String> actualGeohashList = GeoWorkerUtil.separateFeatureCollectionAndToSingleGeoHashList(inputFeatureCollection, 3);
            List<String> expectedGeohashList = bufferedReaderExpectedMapFile.lines().flatMap(line -> Arrays.stream(line.split(","))).map(String::trim).toList();

            assertThat(actualGeohashList).containsExactlyInAnyOrderElementsOf(expectedGeohashList);
        }
    }

    @Test
    void geoJsonToSeparatedToGeoHashTest2() throws IOException {
        try(InputStream inputStreamMapFile = getClass().getClassLoader().getResourceAsStream("geoWorkerUtilTest/Map2SamObl.json");
        InputStream inputStreamExpectedMapFile = getClass().getClassLoader().getResourceAsStream("geoWorkerUtilTest/Map2SamOblExpected.txt");
        BufferedReader bufferedReaderExpectedMapFile = new BufferedReader(new InputStreamReader(inputStreamExpectedMapFile))){

            FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(inputStreamMapFile);
            List<String> actualGeohashList = GeoWorkerUtil.separateFeatureCollectionAndToSingleGeoHashList(inputFeatureCollection, 4);
            List<String> expectedGeohashList = bufferedReaderExpectedMapFile.lines().flatMap(line -> Arrays.stream(line.split(","))).map(String::trim).toList();

            assertThat(actualGeohashList).containsExactlyInAnyOrderElementsOf(expectedGeohashList);
        }
    }
}
