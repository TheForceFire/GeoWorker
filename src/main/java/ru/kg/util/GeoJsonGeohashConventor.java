package ru.kg.util;

import org.geojson.FeatureCollection;
import ru.kg.geohash.coder.util.GeohashUtil;
import ru.kg.geojson.separator.util.GeoJsonUtil;

import java.util.ArrayList;
import java.util.List;

public class GeoJsonGeohashConventor {


    public static List<String> separateGeoJsonAndConvertToSingleGeohashList(FeatureCollection originFeatureCollection, int precision){
        FeatureCollection separatedFeatureCollection = GeoJsonUtil.separateGeoJson(originFeatureCollection);
        List<String> geohashes = GeohashUtil.featureCollectionToGeohash(separatedFeatureCollection, precision);

        return geohashes;
    }

    public static List<String>[] separateGeoJsonAndConvertToMultipleGeohashLists(FeatureCollection originFeatureCollection, int precision){
        FeatureCollection separatedFeatureCollection = GeoJsonUtil.separateGeoJson(originFeatureCollection);

        List<String>[] geohashes = new List[separatedFeatureCollection.getFeatures().size()];

        for(int i = 0; i < separatedFeatureCollection.getFeatures().size(); i++){
            FeatureCollection tempFeatureCollection = new FeatureCollection();
            tempFeatureCollection.add(separatedFeatureCollection.getFeatures().get(i));

            geohashes[i] = GeohashUtil.featureCollectionToGeohash(tempFeatureCollection, precision);
        }

        return geohashes;
    }

}
