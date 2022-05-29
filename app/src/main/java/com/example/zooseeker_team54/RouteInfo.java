package com.example.zooseeker_team54;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RouteInfo implements Serializable {
    private Map<String, List<LocEdge>> directions;
    private Map<String, Double> distances;

    public RouteInfo() {
        directions = new HashMap<>();
        distances = new HashMap<>();
    }

    public void addDirection(String location, List<LocEdge> direction) {
        directions.put(location, direction);
    }

    public void addDistance(String location, Double distance) {
        distances.put(location, distance);
    }

    public List<LocEdge> getPath(String location) {
        return directions.get(location);
    }

    public Double getDistance(String location) {
        return distances.get(location);
    }

    public Map<String, List<LocEdge>> getDirections() {
        return directions;
    }

    public Map<String, Double> getDistances() {
        return distances;
    }

    public List<String> getLocations() {
        List<String> locations = new ArrayList<>();
        Map<String, Double> sortedMap = sortByValue(distances);
        for (String location : sortedMap.keySet()) {
            locations.add(location);
        }
        return locations;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new HashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
