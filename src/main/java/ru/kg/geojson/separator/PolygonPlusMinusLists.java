package ru.kg.geojson.separator;

import org.geojson.LngLatAlt;

import java.util.List;

public class PolygonPlusMinusLists {
    private List<LngLatAlt> plusList;
    private List<LngLatAlt> minusList;

    public PolygonPlusMinusLists(List<LngLatAlt> plusList, List<LngLatAlt> minusList){
        this.plusList = plusList;
        this.minusList = minusList;
    }

    public List<LngLatAlt> getPlusList(){
        return plusList;
    }
    public List<LngLatAlt> getMinusList(){
        return minusList;
    }
}
