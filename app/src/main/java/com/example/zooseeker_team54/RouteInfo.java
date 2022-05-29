package com.example.zooseeker_team54;

import android.util.Log;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

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

    public List<LocEdge> getDirection(String location) {
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

    public String getCurrentLocation() {
        for (Entry<String, Double> entry : distances.entrySet()) {
            if (entry.getValue() == 0.0)
                return entry.getKey();
        }

        Log.d("FOOBAR", "No next unvisited exhibit");
        return "entrance_exit_gate";
    }

    public String getCurrentTarget() {
        String currentLocation = getCurrentLocation();

        for (Entry<String, List<LocEdge>> entry : directions.entrySet()) {
            String location = entry.getKey();
            List<LocEdge> path = entry.getValue();

            if (path.size() > 0 && Objects.equals(currentLocation, path.get(0).source_id) && distances.get(location) > 0) {
                return location;
            }
        }

        Log.d("FOOBAR", "No next unvisited exhibit");
        return null;
    }

    public String getNextTarget() {
        String currentLocation = getCurrentTarget();

        for (Entry<String, List<LocEdge>> entry : directions.entrySet()) {
            String location = entry.getKey();
            List<LocEdge> path = entry.getValue();

            if (path.size() > 0 && Objects.equals(currentLocation, path.get(0).source_id) && distances.get(location) > 0) {
                return location;
            }
        }

        Log.d("FOOBAR", "No next unvisited exhibit");
        return null;
    }

    public void arriveCurrentTarget() {
        String nextLocation = getCurrentTarget();
        Double previousDistance = distances.get(nextLocation);

        for (Entry<String, Double> entry : distances.entrySet()) {
            String location = entry.getKey();
            Double originalDistance = entry.getValue();
            distances.put(location, originalDistance - previousDistance);
        }
    }

    public List<String> getSortedLocations(List<String> unsortedLocations) {

        Map<String, Double> unsortedDistances = new HashMap<>();
        for (String location : unsortedLocations) {
            unsortedDistances.put(location, distances.get(location));
        }

        List<Entry<String, Double>> sortedDistances = new ArrayList<>(unsortedDistances.entrySet());
        sortedDistances.sort(Entry.comparingByValue());

        List<String> sortedLocations = new ArrayList<>();
        for (Entry<String, Double> entry: sortedDistances) {
            sortedLocations.add(entry.getKey());
        }

        return sortedLocations;
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

    public void removeLocation(String removalTarget) {
        if (!distances.containsKey(removalTarget))
            return;
        Pair<List<LocEdge>, Double> pair = Utilities.findShortestPathBetween(getCurrentLocation(), getNextTarget());

        List<LocEdge> newDirection = pair.first;
        Double newDistance = pair.second;
        Double oldDistance = distances.get(getNextTarget());
        Double diff = oldDistance - newDistance;

        addDirection(getCurrentTarget(), newDirection);
        for(Entry<String, Double> entry: distances.entrySet()){
            if(entry.getValue() > 0){
                distances.put(entry.getKey(), entry.getValue()- diff);
            }
        }
        distances.remove(removalTarget);
        directions.remove(removalTarget);
    }
}
