import org.geojson.FeatureCollection;
import org.junit.jupiter.api.Test;
import ru.kg.geojson.separator.GeoJsonFileManager;
import ru.kg.geojson.separator.GeoJsonUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeoJsonSeparatorTest {

    @Test
    void startTest1() throws IOException{
        System.out.println("Test number 1");

        String pathToFile = "src/test/resources/Map1.txt";
        String pathToResultFile = "src/test/resources/Map1Result.txt";


        FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToFile);
        FeatureCollection outputFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

        FeatureCollection resultFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToResultFile);

        boolean isEquals = GeoJsonUtil.compareFeatures(outputFeatureCollection, resultFeatureCollection);
        assertTrue(isEquals, "Feature collections are not the same!");

        System.out.println("Success");
    }

    @Test
    void startTest2() throws IOException{
        System.out.println("Test number 2");

        String pathToFile = "src/test/resources/Map2.txt";
        String pathToResultFile = "src/test/resources/Map2Result.txt";


        FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToFile);
        FeatureCollection outputFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

        FeatureCollection resultFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToResultFile);

        boolean isEquals = GeoJsonUtil.compareFeatures(outputFeatureCollection, resultFeatureCollection);
        assertTrue(isEquals, "Feature collections are not the same!");

        System.out.println("Success");
    }

    @Test
    void startTest3() throws IOException{
        System.out.println("Test number 3");

        String pathToFile = "src/test/resources/Map3.txt";
        String pathToResultFile = "src/test/resources/Map3Result.txt";


        FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToFile);
        FeatureCollection outputFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

        FeatureCollection resultFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToResultFile);

        boolean isEquals = GeoJsonUtil.compareFeatures(outputFeatureCollection, resultFeatureCollection);
        assertTrue(isEquals, "Feature collections are not the same!");

        System.out.println("Success");
    }

    @Test
    void startTest4() throws IOException{
        System.out.println("Test number 4");

        String pathToFile = "src/test/resources/Map4.txt";
        String pathToResultFile = "src/test/resources/Map4Result.txt";


        FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToFile);
        FeatureCollection outputFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

        FeatureCollection resultFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToResultFile);

        boolean isEquals = GeoJsonUtil.compareFeatures(outputFeatureCollection, resultFeatureCollection);
        assertTrue(isEquals, "Feature collections are not the same!");

        System.out.println("Success");
    }

    @Test
    void startTest5() throws IOException{
        System.out.println("Test number 5");

        String pathToFile = "src/test/resources/Map5.txt";
        String pathToResultFile = "src/test/resources/Map5Result.txt";


        FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToFile);
        FeatureCollection outputFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

        FeatureCollection resultFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToResultFile);

        boolean isEquals = GeoJsonUtil.compareFeatures(outputFeatureCollection, resultFeatureCollection);
        assertTrue(isEquals, "Feature collections are not the same!");

        System.out.println("Success");
    }

    @Test
    void startTest6() throws IOException{
        System.out.println("Test number 6");

        String pathToFile = "src/test/resources/Map6.txt";
        String pathToResultFile = "src/test/resources/Map6Result.txt";


        FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToFile);
        FeatureCollection outputFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

        FeatureCollection resultFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToResultFile);

        boolean isEquals = GeoJsonUtil.compareFeatures(outputFeatureCollection, resultFeatureCollection);
        assertTrue(isEquals, "Feature collections are not the same!");

        System.out.println("Success");
    }

    @Test
    void startTest7() throws IOException{
        System.out.println("Test number 7");

        String pathToFile = "src/test/resources/Map7.txt";
        String pathToResultFile = "src/test/resources/Map7Result.txt";


        FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToFile);
        FeatureCollection outputFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

        FeatureCollection resultFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToResultFile);

        boolean isEquals = GeoJsonUtil.compareFeatures(outputFeatureCollection, resultFeatureCollection);
        assertTrue(isEquals, "Feature collections are not the same!");

        System.out.println("Success");
    }

    @Test
    void startTest8() throws IOException{
        System.out.println("Test number 8");

        String pathToFile = "src/test/resources/Map8.txt";
        String pathToResultFile = "src/test/resources/Map8Result.txt";


        FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToFile);
        FeatureCollection outputFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

        FeatureCollection resultFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToResultFile);

        boolean isEquals = GeoJsonUtil.compareFeatures(outputFeatureCollection, resultFeatureCollection);
        assertTrue(isEquals, "Feature collections are not the same!");

        System.out.println("Success");
    }
}
