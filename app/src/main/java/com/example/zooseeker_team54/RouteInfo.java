package com.example.zooseeker_team54;

import android.util.Log;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RouteInfo implements Serializable {
    private Map<String, Double> distances;
    private Map<String, List<LocEdge>> directions;

    public RouteInfo() {
        directions = new HashMap<>();
        distances = new HashMap<>();
    }

    public Map<String, Double> getDistances() {
        return distances;
    }

    public Map<String, List<LocEdge>> getDirections() {
        return directions;
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

    public List<LocEdge> getReversedDirection(String location) {
        if (!directions.containsKey(location)) return null;
        return Utilities.getReversedDirections(directions.get(location));
    }

    public Double getDistance(String location) {
        return distances.get(location);
    }

    public List<String> getLocations() {
        List<String> locations = new ArrayList<>();
        Map<String, Double> sortedMap = sortByValue(distances);
        for (String location : sortedMap.keySet()) {
            locations.add(location);
        }
        return locations;
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

    public String getPreviousLocation() {
        String currentLocation = getCurrentLocation();
        return getPreviousLocationOf(currentLocation);
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
        return getNextLocationOf(currentLocation);
    }

    public String getNextTarget() {
        String currentTarget = getCurrentTarget();
        return getNextLocationOf(currentTarget);
    }

    public String getNextLocationOf(String targetLocation) {
        for (Entry<String, List<LocEdge>> entry: directions.entrySet()) {
            String location = entry.getKey();
            List<LocEdge> path = entry.getValue();

            if (path.size() > 0 && path.get(0).source_id.equals(targetLocation) && distances.get(location) > 0) {
                return location;
            }
        }
        return null;
    }

    public String getPreviousLocationOf(String targetLocation) {
        if (!distances.containsKey(targetLocation))
            return null;

        return directions.get(targetLocation).get(0).source_id;
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

    public void removeLocation(String removalTarget) {
        if (!distances.containsKey(removalTarget))
            return;
        Pair<List<LocEdge>, Double> pair = Utilities.findShortestPathBetween(getCurrentLocation(), getNextTarget());

        List<LocEdge> newDirection = pair.first;
        Double newDistance = pair.second;
        Double oldDistance = distances.get(getNextTarget());
        Double diff = oldDistance - newDistance;

        addDirection(getNextTarget(), newDirection);
        for(Entry<String, Double> entry: distances.entrySet()){
            if(entry.getValue() > 0){
                distances.put(entry.getKey(), entry.getValue()- diff);
            }
        }
        distances.remove(removalTarget);
        directions.remove(removalTarget);
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

    public void arrivePreviousLocation() {
        String previousLocation = getPreviousLocation();
        Double previousDistance = distances.get(previousLocation);

        for (Entry<String, Double> entry : distances.entrySet()) {
            String location = entry.getKey();
            Double originalDistance = entry.getValue();
            distances.put(location, originalDistance - previousDistance);
        }
    }
}
