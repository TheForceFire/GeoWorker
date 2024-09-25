import org.geojson.FeatureCollection;
import org.junit.jupiter.api.Test;
import ru.kg.geojson.separator.GeoJsonFileManager;
import ru.kg.geojson.separator.GeoJsonUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeoJsonSeparatorTest {

    @Test
    void startTest() throws IOException{
        File mapFile;
        int counter = 1;
        String pathToFile;
        String pathToResultFile;

        do{
            pathToFile = "src/test/resources/Map" + counter + ".txt";
            pathToResultFile = "src/test/resources/Map" + counter + "Result.txt";
            mapFile = new File(pathToFile);



            if (mapFile.exists()) {
                System.out.println("*********************************");
                System.out.println("Test number " + counter);

                FeatureCollection inputFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToFile);
                FeatureCollection outputFeatureCollection = GeoJsonUtil.separateGeoJson(inputFeatureCollection);

                FeatureCollection resultFeatureCollection = GeoJsonFileManager.loadGeoJsonFile(pathToResultFile);

                boolean isEquals = GeoJsonUtil.compareFeatures(outputFeatureCollection, resultFeatureCollection);
                assertTrue(isEquals, "Feature collections are not the same!");

                System.out.println("Success");
            }

            counter++;
        }
        while(mapFile.exists());
        System.out.println("*********************************");

    }
}
