package ru.kg.geohash.coder.util;

import ch.hsr.geohash.GeoHash;

import java.util.LinkedHashSet;

class FastForwardUtil {

    public static LinkedHashSet<GeoHash> fastForwardEast(LinkedHashSet<GeoHash> geoHashesSetPerimeter, GeoHash geoHashToFastForward){
        LinkedHashSet<GeoHash> geoHashesNewSet = new LinkedHashSet<>();

        while (!geoHashesSetPerimeter.contains(geoHashToFastForward.getEasternNeighbour())){
            geoHashesNewSet.add(geoHashToFastForward.getEasternNeighbour());

            geoHashToFastForward = geoHashToFastForward.getEasternNeighbour();
        }

        return geoHashesNewSet;
    }

    public static LinkedHashSet<GeoHash> fastForwardNorth(LinkedHashSet<GeoHash> geoHashesSetPerimeter, GeoHash geoHashToFastForward){
        LinkedHashSet<GeoHash> geoHashesNewSet = new LinkedHashSet<>();

        while (!geoHashesSetPerimeter.contains(geoHashToFastForward.getNorthernNeighbour())){
            geoHashesNewSet.add(geoHashToFastForward.getNorthernNeighbour());

            geoHashToFastForward = geoHashToFastForward.getNorthernNeighbour();
        }

        return geoHashesNewSet;
    }

    public static LinkedHashSet<GeoHash> fastForwardSouth(LinkedHashSet<GeoHash> geoHashesSetPerimeter, GeoHash geoHashToFastForward){
        LinkedHashSet<GeoHash> geoHashesNewSet = new LinkedHashSet<>();

        while (!geoHashesSetPerimeter.contains(geoHashToFastForward.getSouthernNeighbour())){
            geoHashesNewSet.add(geoHashToFastForward.getSouthernNeighbour());

            geoHashToFastForward = geoHashToFastForward.getSouthernNeighbour();
        }

        return geoHashesNewSet;
    }

    public static LinkedHashSet<GeoHash> fastForwardWest(LinkedHashSet<GeoHash> geoHashesSetPerimeter, GeoHash geoHashToFastForward){
        LinkedHashSet<GeoHash> geoHashesNewSet = new LinkedHashSet<>();

        while (!geoHashesSetPerimeter.contains(geoHashToFastForward.getWesternNeighbour())){
            geoHashesNewSet.add(geoHashToFastForward.getWesternNeighbour());

            geoHashToFastForward = geoHashToFastForward.getWesternNeighbour();
        }

        return geoHashesNewSet;
    }
}
