package ru.kg.geohash.coder.util;

import ch.hsr.geohash.GeoHash;

import java.util.LinkedHashSet;

class FastForwardUtil {

    public static LinkedHashSet<String> fastForwardEast(LinkedHashSet<GeoHash> geoHashesSetPerimeter, GeoHash geoHashToFastForward){
        LinkedHashSet<String> geoHashesNewSet = new LinkedHashSet<>();

        while (!geoHashesSetPerimeter.contains(geoHashToFastForward.getEasternNeighbour())){
            geoHashesNewSet.add(geoHashToFastForward.getEasternNeighbour().toBase32());

            geoHashToFastForward = geoHashToFastForward.getEasternNeighbour();
        }

        return geoHashesNewSet;
    }

    public static LinkedHashSet<String> fastForwardNorth(LinkedHashSet<GeoHash> geoHashesSetPerimeter, GeoHash geoHashToFastForward){
        LinkedHashSet<String> geoHashesNewSet = new LinkedHashSet<>();

        while (!geoHashesSetPerimeter.contains(geoHashToFastForward.getNorthernNeighbour())){
            geoHashesNewSet.add(geoHashToFastForward.getNorthernNeighbour().toBase32());

            geoHashToFastForward = geoHashToFastForward.getNorthernNeighbour();
        }

        return geoHashesNewSet;
    }

    public static LinkedHashSet<String> fastForwardSouth(LinkedHashSet<GeoHash> geoHashesSetPerimeter, GeoHash geoHashToFastForward){
        LinkedHashSet<String> geoHashesNewSet = new LinkedHashSet<>();

        while (!geoHashesSetPerimeter.contains(geoHashToFastForward.getSouthernNeighbour())){
            geoHashesNewSet.add(geoHashToFastForward.getSouthernNeighbour().toBase32());

            geoHashToFastForward = geoHashToFastForward.getSouthernNeighbour();
        }

        return geoHashesNewSet;
    }

    public static LinkedHashSet<String> fastForwardWest(LinkedHashSet<GeoHash> geoHashesSetPerimeter, GeoHash geoHashToFastForward){
        LinkedHashSet<String> geoHashesNewSet = new LinkedHashSet<>();

        while (!geoHashesSetPerimeter.contains(geoHashToFastForward.getWesternNeighbour())){
            geoHashesNewSet.add(geoHashToFastForward.getWesternNeighbour().toBase32());

            geoHashToFastForward = geoHashToFastForward.getWesternNeighbour();
        }

        return geoHashesNewSet;
    }
}
