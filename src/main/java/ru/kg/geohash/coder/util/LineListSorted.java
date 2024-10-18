package ru.kg.geohash.coder.util;

import org.geojson.LngLatAlt;

import java.util.Collections;
import java.util.List;

class LineListSorted {
    private List<LngLatAlt> polygonList;

    public LineListSorted(List<LngLatAlt> polygonList){
        this.polygonList = polygonList;
        sort();
    }

    private void sort(){
        double sumX = 0;
        double sumY = 0;
        for (int i = 0; i < polygonList.size(); i++) {
            sumX += polygonList.get(i).getLongitude();
            sumY += polygonList.get(i).getLatitude();
        }

        double centerX = sumX / polygonList.size();
        double centerY = sumY / polygonList.size();
        Collections.sort(polygonList, (p1, p2) -> {
            double angle1 = Math.atan2(p1.getLatitude() - centerY, p1.getLongitude() - centerX);
            double angle2 = Math.atan2(p2.getLatitude() - centerY, p2.getLongitude() - centerX);
            return Double.compare(angle1, angle2);
        });
    }

    public List<LngLatAlt> getPolygonList()
    {
        return polygonList;
    }
}
