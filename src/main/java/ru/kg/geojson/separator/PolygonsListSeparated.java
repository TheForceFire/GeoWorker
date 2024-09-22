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
    public void removePolygonList(int index){
        if(index < size()){
            polyLists.remove(index);
        }
    }
    public void addPolygonList(List<LngLatAlt> polyList){
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
