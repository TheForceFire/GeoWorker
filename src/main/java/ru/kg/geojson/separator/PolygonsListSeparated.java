package ru.kg.geojson.separator;

import org.geojson.LngLatAlt;

import java.util.ArrayList;
import java.util.List;

public class PolygonsListSeparated {
    private List<List<LngLatAlt>> polyLists;
    public PolygonsListSeparated(){
        polyLists = new ArrayList<>();
    }


    public List<LngLatAlt> getPolygonList(int index){
        if(index < polyLists.size()){
            return polyLists.get(index);
        }
        else{
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }
    public void addPolygonList(List<LngLatAlt> polyList){
        if(polyList.size() == 0) return;

        if(!polyList.get(0).equals(polyList.get(polyList.size() - 1))){
            polyList.add(polyList.get(0));
        }

        polyLists.add(polyList);
    }
    public int size(){
        return polyLists.size();
    }

    public void addPolygonsListSeparated(PolygonsListSeparated polygonsListSeparated){
        for(int i = 0; i < polygonsListSeparated.size(); i++){
            addPolygonList(polygonsListSeparated.getPolygonList(i));
        }
    }
}
