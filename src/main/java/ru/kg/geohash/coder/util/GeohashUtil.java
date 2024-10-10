package ru.kg.geohash.coder.util;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;
import ru.kg.geojson.separator.util.GeoJsonUtil;

import java.util.ArrayList;
import java.util.List;

public class GeohashUtil {
    public static List<String> separateFeatureCollectionAndToSingleGeohashList(FeatureCollection originFeatureCollection, int precision){
        FeatureCollection separatedFeatureCollection = GeoJsonUtil.separateGeoJson(originFeatureCollection);
        List<String> geohashes = GeohashConvertor.featureCollectionToGeohash(separatedFeatureCollection, precision);

        return geohashes;
    }

    public static List<List<String>> separateFeatureCollectionAndToMultipleGeohashLists(FeatureCollection originFeatureCollection, int precision){
        FeatureCollection separatedFeatureCollection = GeoJsonUtil.separateGeoJson(originFeatureCollection);

        List<List<String>> geohashesList = new ArrayList<>();

        for(int i = 0; i < separatedFeatureCollection.getFeatures().size(); i++){
            FeatureCollection tempFeatureCollection = new FeatureCollection();
            tempFeatureCollection.add(separatedFeatureCollection.getFeatures().get(i));

            geohashesList.add(GeohashConvertor.featureCollectionToGeohash(tempFeatureCollection, precision));
        }

        return geohashesList;
    }

    public static FeatureCollection separateFeatureCollectionAndToGeohashBoundingFeatureCollection(FeatureCollection originFeatureCollection, int precision){
        FeatureCollection separatedFeatureCollection = GeoJsonUtil.separateGeoJson(originFeatureCollection);
        List<String> geohashesString = GeohashConvertor.featureCollectionToGeohash(separatedFeatureCollection, precision);

        List<GeoHash> geoHashes = new ArrayList<>();
        for(int i = 0; i < geohashesString.size(); i++){
            geoHashes.add(GeoHash.fromGeohashString(geohashesString.get(i)));
        }

        FeatureCollection boundingFeatureCollection = new FeatureCollection();

        for(int i = 0; i < geoHashes.size(); i++){
            BoundingBox boundingBox = geoHashes.get(i).getBoundingBox();

            List<LngLatAlt> boundingPoints = new ArrayList<>();
            boundingPoints.add(new LngLatAlt(boundingBox.getWestLongitude(), boundingBox.getNorthLatitude()));
            boundingPoints.add(new LngLatAlt(boundingBox.getWestLongitude(), boundingBox.getSouthLatitude()));
            boundingPoints.add(new LngLatAlt(boundingBox.getEastLongitude(), boundingBox.getSouthLatitude()));
            boundingPoints.add(new LngLatAlt(boundingBox.getEastLongitude(), boundingBox.getNorthLatitude()));
            boundingPoints.add(new LngLatAlt(boundingBox.getWestLongitude(), boundingBox.getNorthLatitude()));

            Polygon boundingPolygon = new Polygon();
            boundingPolygon.setExteriorRing(boundingPoints);

            Feature boundingFeature = new Feature();
            boundingFeature.setGeometry(boundingPolygon);

            boundingFeatureCollection.add(boundingFeature);
        }

        return boundingFeatureCollection;
    }

    public static boolean compareGeohashStringLists(List<String> geohashStringList1, List<String> geohashStringList2){
        boolean isEquals = true;

        if(geohashStringList1.size() != geohashStringList2.size()){
            isEquals = false;
        }
        if(isEquals){
            int i = 0;

            while (i < geohashStringList1.size() && isEquals){
                if(!geohashStringList1.contains(geohashStringList2.get(i))){
                    isEquals = false;
                }

                i++;
            }
        }

        return isEquals;
    }
}
