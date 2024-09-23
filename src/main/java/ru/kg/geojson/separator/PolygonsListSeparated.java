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

    public void removeEmptyPolygons(){
        //Empty polygon is a polygon that have only two closure coordinated

        for(int i = 0; i < polyLists.size(); i++){
            if(polyLists.get(i).size() == 2 && polyLists.get(i).get(0).equals(polyLists.get(i).get(1))){
                polyLists.remove(i);
                i--;
            }
        }
    }

    public void addPolygonsListSeparated(PolygonsListSeparated polygonsListSeparated){
        for(int i = 0; i < polygonsListSeparated.size(); i++){
            addPolygonList(polygonsListSeparated.getPolygonList(i));
        }
    }
}
