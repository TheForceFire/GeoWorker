package ru.kg.geojson.separator.util;

import java.util.Comparator;
import java.util.List;

class LineWithIndex {
    private double length;
    private int index;

    public LineWithIndex(double length, int index) {
        this.length = length;
        this.index = index;
    }

    public double getLength() {
        return length;
    }

    public int getIndex() {
        return index;
    }


    public static void sort(List<LineWithIndex> lines) {
        lines.sort(Comparator.comparingDouble(LineWithIndex::getLength).reversed());
    }
}
