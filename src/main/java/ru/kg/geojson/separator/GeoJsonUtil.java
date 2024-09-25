package ru.kg.geojson.separator;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

import java.util.List;


public class GeoJsonUtil {

    public static FeatureCollection separateGeoJson(FeatureCollection originFeatureCollection){
        FeatureCollection finalFeatureCollection = calculateSeparatedGeoJson(originFeatureCollection);
        return finalFeatureCollection;
    }

    private static FeatureCollection calculateSeparatedGeoJson(FeatureCollection originFeatureCollection) {
        FeatureCollection finalFeatureCollection;

        FeatureCollection equatorFeatureCollection = new FeatureCollection();
        for(int i = 0; i < originFeatureCollection.getFeatures().size(); i++){
            FeatureCollection temp = GeoJsonEquatorSeparator.separateEquator(originFeatureCollection, i);
            for(int j = 0; j < temp.getFeatures().size(); j++) {
                equatorFeatureCollection.add(temp.getFeatures().get(j));
            }
        }

        FeatureCollection primeMeridianFeatureCollection = new FeatureCollection();
        for(int i = 0; i < equatorFeatureCollection.getFeatures().size(); i++){
            FeatureCollection temp = GeoJsonPrimeMeridianSeparator.separatePrimeMeridian(equatorFeatureCollection, i);
            for(int j = 0; j < temp.getFeatures().size(); j++) {
                primeMeridianFeatureCollection.add(temp.getFeatures().get(j));
            }
        }

        finalFeatureCollection = primeMeridianFeatureCollection;
        return finalFeatureCollection;
    }

    public static boolean compareFeatures(FeatureCollection featureCollection1, FeatureCollection featureCollection2){
        boolean isEqual = true;

        List<Feature> featureList1 = featureCollection1.getFeatures();
        List<Feature> featureList2 = featureCollection2.getFeatures();

        if(featureList1.size() != featureList2.size()){
            isEqual = false;
        }
        if(isEqual) {
            int i = 0;

            while(i < featureList1.size() && isEqual){
                Polygon polyTemp1 = (Polygon) featureList1.get(i).getGeometry();
                Polygon polyTemp2 = (Polygon) featureList2.get(i).getGeometry();

                List<LngLatAlt> polyList1 = polyTemp1.getExteriorRing();
                List<LngLatAlt> polyList2 = polyTemp2.getExteriorRing();

                if(polyList1.size() != polyList2.size()){
                    isEqual = false;
                }

                if(isEqual){
                    polyList1.remove(polyList1.size() - 1);
                    polyList2.remove(polyList2.size() - 1);

                    for(int k = 0; k < polyList1.size(); k++){
                        polyList1.get(k).setLatitude( roundDouble(polyList1.get(k).getLatitude(), 3) );
                        polyList1.get(k).setLongitude( roundDouble(polyList1.get(k).getLongitude(), 3) );

                        polyList2.get(k).setLatitude( roundDouble(polyList2.get(k).getLatitude(), 3) );
                        polyList2.get(k).setLongitude( roundDouble(polyList2.get(k).getLongitude(), 3) );
                    }

                    int j = 0;
                    while(j < polyList1.size() && isEqual){
                        if(!polyList1.contains(polyList2.get(j))){
                            isEqual = false;
                        }
                        j++;
                    }
                }
                i++;
            }
        }

        return isEqual;
    }

    private static double roundDouble(double value, int decimals){
        int valueToDivide = 1;

        for(int i = 0; i < decimals; i++){
            valueToDivide *= 10;
        }

        double valueToReturn = ((double) Math.round(value * valueToDivide) / valueToDivide);
        return valueToReturn;
    }
}
