package ru.kg.geojson.separator;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

import java.util.ArrayList;
import java.util.List;

public class GeoJsonEquatorSeparator {

    public static FeatureCollection separateEquator(FeatureCollection featureCollection, int featureIndex) {
        PolygonsListSeparated separatedLists = setPolygonsListSeparated(featureCollection, featureIndex);
        FeatureCollection equatorFeatureCollection = getPolygonsToFeatureCollection(separatedLists);
        return equatorFeatureCollection;
    }

    private static PolygonsListSeparated setPolygonsListSeparated(FeatureCollection featureCollection, int featureIndex){
        List<LngLatAlt> separatedPoints = calculateZeroCoordinates(featureCollection, featureIndex);
        PolygonPlusMinusLists polygonPlusMinusLists = divideListBySign(separatedPoints);

        PolygonsListSeparated separatedLists = new PolygonsListSeparated();
        separatedLists.addPolygonsListSeparated(findAndSeparateIntersections(polygonPlusMinusLists.getPlusList()));
        separatedLists.addPolygonsListSeparated(findAndSeparateIntersections(polygonPlusMinusLists.getMinusList()));

        return separatedLists;
    }
    private static PolygonPlusMinusLists divideListBySign(List<LngLatAlt> separatedPoints){
        List<LngLatAlt> plusList = new ArrayList<>();
        List<LngLatAlt> minusList = new ArrayList<>();

        for(int i = 0; i < separatedPoints.size(); i++){
            double sign;
            sign = Math.signum(separatedPoints.get(i).getLatitude());

            if(sign == 1){
                plusList.add(separatedPoints.get(i));
            }
            else if(sign == -1){
                minusList.add(separatedPoints.get(i));
            }
            else{
                plusList.add(separatedPoints.get(i));
                minusList.add(separatedPoints.get(i));
            }
        }

        PolygonPlusMinusLists polygonPlusMinusLists = new PolygonPlusMinusLists(plusList, minusList);
        return polygonPlusMinusLists;
    }

    private static PolygonsListSeparated findAndSeparateIntersections(List<LngLatAlt> list){
        PolygonsListSeparated polygonsListSeparatedToReturn = new PolygonsListSeparated();
        if(list.size() == 0) return polygonsListSeparatedToReturn;

        if(!list.get(0).equals(list.get(list.size() - 1))) {
            list.add(list.get(0));
        }

        polygonsListSeparatedToReturn.addPolygonList(list);
        int i = 0;
        int depth = 0;

        while(i < polygonsListSeparatedToReturn.size() && depth < 10){

            List<LngLatAlt> polygonList = polygonsListSeparatedToReturn.getPolygonList(i);


            List<LineWithIndex> linesWithZeroDecrease = findAllLinesWithZeroDecrease(polygonList);

            if (linesWithZeroDecrease.size() > 1) {

                int j = 1;
                boolean isFoundIntersection = false;

                int line1Index = linesWithZeroDecrease.get(0).getIndex();
                while (j < linesWithZeroDecrease.size() && !isFoundIntersection) {
                    int line2Index = linesWithZeroDecrease.get(j).getIndex();

                    boolean isIntersected = checkForIntersectionTwoLines(
                            polygonList.get(line1Index).getLongitude(), polygonList.get(line1Index + 1).getLongitude(),
                            polygonList.get(line2Index).getLongitude(), polygonList.get(line2Index + 1).getLongitude()
                    );
                    if (isIntersected) {
                        polygonList = separateIntersection(polygonList, linesWithZeroDecrease.get(0).getIndex(), linesWithZeroDecrease.get(j).getIndex());

                        polygonsListSeparatedToReturn.removePolygonList(i);
                        polygonsListSeparatedToReturn.addPolygonsListSeparated(separatePolygonsList(polygonList));

                        isFoundIntersection = true;
                        i = -1;
                        depth++;
                    }
                    j++;
                }
            }
            i++;
        }

        return polygonsListSeparatedToReturn;
    }

    private static List<LineWithIndex> findAllLinesWithZeroDecrease(List<LngLatAlt> list){
        List<LineWithIndex> lineWithIndexes = new ArrayList<>();

        for(int i = 0; i < list.size() - 1; i++){
            if(list.get(i).getLatitude() == 0 && list.get(i + 1).getLatitude() == 0){
                double lineLenght = lineLength(list.get(i).getLongitude(), list.get(i + 1).getLongitude());
                LineWithIndex line = new LineWithIndex(lineLenght, i);
                lineWithIndexes.add(line);
            }
        }

        if(lineWithIndexes.size() > 1){
            LineWithIndex.sort(lineWithIndexes);
        }
        return lineWithIndexes;
    }

    private static double lineLength(double lineDot1, double lineDot2){
        double lineStart = Math.min(lineDot1, lineDot2);
        double lineEnd = Math.max(lineDot1, lineDot2);

        return lineEnd - lineStart;
    }

    private static PolygonsListSeparated separatePolygonsList(List<LngLatAlt> list){
        PolygonsListSeparated separatedLists = new PolygonsListSeparated();

        List<LngLatAlt> polygonToAdd = new ArrayList<>();

        for(int i = 0; i < list.size(); i++){
            polygonToAdd.add(list.get(i));

            if(polygonToAdd.size() > 1 && polygonToAdd.get(0).equals(polygonToAdd.get(polygonToAdd.size() - 1))){
                 separatedLists.addPolygonList(polygonToAdd);
                 polygonToAdd = new ArrayList<>();
            }

        }

        return separatedLists;
    }
    private static boolean checkForIntersectionTwoLines(double lineDot11, double lineDot12, double lineDot21, double lineDot22){
        boolean isIntersected = false;
        double lineStart1 = Math.min(lineDot11, lineDot12);
        double lineEnd1 = Math.max(lineDot11, lineDot12);

        double lineStart2 = Math.min(lineDot21, lineDot22);
        double lineEnd2 = Math.max(lineDot21, lineDot22);

        if(lineStart1 < lineStart2 && lineEnd2 < lineEnd1){
            isIntersected = true;
        }

        return isIntersected;
    }
    private static List<LngLatAlt> separateIntersection(List<LngLatAlt> list, int lineIndex, int intersectionIndex){
        List<LngLatAlt> separatedList = new ArrayList<>();

        int lowerSeparateIndex = Math.min(lineIndex, intersectionIndex);
        int higherSeparateIndex = Math.max(lineIndex, intersectionIndex);

        int i = 0;
        while(i != lowerSeparateIndex + 1){
            separatedList.add(list.get(i));
            i++;
        }
        i = higherSeparateIndex + 1;
        while (i != list.size()){
            separatedList.add(list.get(i));
            i++;
        }
        if(!separatedList.get(0).equals(separatedList.get(separatedList.size() - 1))) {
            separatedList.add(separatedList.get(0));
        }

        i = lowerSeparateIndex + 1;
        while (i != higherSeparateIndex + 1){
            separatedList.add(list.get(i));
            i++;
        }
        if(!separatedList.get(separatedList.size() - 1).equals(list.get(lowerSeparateIndex + 1))) {
            separatedList.add(list.get(lowerSeparateIndex + 1));
        }

        return separatedList;
    }


    private static List<LngLatAlt> calculateZeroCoordinates(FeatureCollection featureCollection, int featureIndex){
        List<LngLatAlt> separatedPoints = new ArrayList<>();
        Polygon originPolygon = (Polygon) featureCollection.getFeatures().get(featureIndex).getGeometry();
        List<LngLatAlt> originPoints = originPolygon.getExteriorRing();

        double sign;
        sign = Math.signum(originPoints.get(0).getLatitude());
        separatedPoints.add(originPoints.get(0));
        for(int i = 1; i < originPoints.size(); i++){
            if(sign != Math.signum(originPoints.get(i).getLatitude())){
                double longitude = getMedianLongitude(originPoints.get(i).getLatitude(), originPoints.get(i).getLongitude(),
                        originPoints.get(i - 1).getLatitude(), originPoints.get(i - 1).getLongitude());
                LngLatAlt separator = new LngLatAlt(longitude, 0);

                separatedPoints.add(separator);
                sign = Math.signum(originPoints.get(i).getLatitude());
            }
            separatedPoints.add(originPoints.get(i));
        }

        return separatedPoints;
    }
    private static double getMedianLongitude(double latitudeDegrees1, double longitudeDegrees1, double latitudeDegrees2, double longitudeDegrees2){
        double finalLongitude;

        double sumLat = Math.abs(latitudeDegrees1) + Math.abs(latitudeDegrees2);
        double latFragment;
        if(sumLat != 0){
            latFragment = 100 / sumLat;
        }
        else{
            latFragment = 0;
        }
        double lat1Frag = Math.abs(latitudeDegrees1) * latFragment / 100;
        double lat2Frag = Math.abs(latitudeDegrees2) * latFragment / 100;

        finalLongitude = (longitudeDegrees1 * lat2Frag + longitudeDegrees2 * lat1Frag);

        return finalLongitude;
    }
    private static FeatureCollection getPolygonsToFeatureCollection(PolygonsListSeparated separatedLists){
        FeatureCollection equatorFeatureCollection = new FeatureCollection();

        for(int i = 0; i < separatedLists.size(); i++){
            Polygon polygon = new Polygon();
            polygon.setExteriorRing(separatedLists.getPolygonList(i));
            Feature feature = new Feature();
            feature.setGeometry(polygon);
            equatorFeatureCollection.add(feature);
        }

        return equatorFeatureCollection;
    }

}