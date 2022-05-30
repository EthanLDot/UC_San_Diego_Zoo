package com.example.zooseeker_team54;

import android.util.Log;
import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class representing the information for the selected route. Used for
 */
public class RouteInfo implements Serializable {
    private Map<String, Double> distances;
    private Map<String, List<LocEdge>> directions;
    private Map<String, String> groupIds;

    /**
     * Constructor for RouteInfo that instantiates new member variables
     */
    public RouteInfo() {
        directions = new HashMap<>();
        distances = new HashMap<>();
        groupIds = new HashMap<>();
    }

    /**
     * Getter method for the member variable directions
     * @return member variable directions as a Map<String, List<LocEdge>>
     */
    public Map<String, List<LocEdge>> getDirections() {
        return directions;
    }

    /**
     * Getter method for the member variable distances
     * @return member variable distances as a Map<String, Double>
     */
    public Map<String, Double> getDistances() {
        return distances;
    }

    /**
     * Adds a direction into the directions Map
     * @param location Name of location
     * @param direction List of LocEdges representing the directions
     */
    public void addDirection(String location, List<LocEdge> direction) {
        directions.put(location, direction);
    }

    /**
     * Adds a distance to the distances Map
     * @param location Name of the location
     * @param distance The current distance to be added
     */
    public void addDistance(String location, Double distance) {
        distances.put(location, distance);
    }

    /**
     *
     * @param location
     * @param groupId
     */
    public void addGroupId(String location, String groupId) { groupIds.put(location, groupId); }

    /**
     * Getter method for the path from a given location
     * @param location String name of the desired location
     * @return List of LocEdges for the directions to the passed in location
     */
    public List<LocEdge> getDirection(String location) {
        return directions.get(location);
    }

    public List<LocEdge> getReversedDirection(String location) {
        if (!directions.containsKey(location)) return null;
        return Utilities.getReversedDirections(directions.get(location));
    }

    public String getGroupId(String location) { return groupIds.get(location); }

    public List<String> getIdsWithGroupId(String groupId) {
        List<String> ids = new ArrayList<>();

        for (Entry<String, String> pair : groupIds.entrySet()) {
            if (pair.getValue().equals(groupId)) ids.add(pair.getKey());
        }

        return ids;
    }

    /**
     * Getter method for the current distance to a given location
     * @param location String name of the desired location
     * @return Double representation of the current distance
     */
    public Double getDistance(String location) {
        return distances.get(location);
    }

    /**
     * Gets all of the locations within the route
     * @return List of locations on the route as strings
     */
    public List<String> getLocations() {
        // iterate through our sorted map
        return sortLocations(new ArrayList<>(distances.keySet()));
    }

    /**
     * Gets the current location
     * @return String of the current location
     */
    public String getCurrentLocation() {
        for (Entry<String, Double> entry : distances.entrySet()) {
            if (entry.getValue() == 0.0 && directions.get(entry.getKey()).size() > 0)
                return entry.getKey();
        }

        // If we reach here, there isn't a next unvisited exhibit, so we return the entrance/exit
        Log.d("FOOBAR", "No next unvisited exhibit");
        return "entrance_exit_gate";
    }

    /**
     * Gets the current target
     * @return String of the current target
     */
    public String getCurrentTarget() {
        String currentLocation = getCurrentLocation();
        return getNextExhibitOf(currentLocation);
    }

    /**
     * Gets the next target
     * @return String of the next target
     */
    public String getNextTarget() {
        String currentTarget = getCurrentTarget();
        return getNextExhibitOf(currentTarget);
    }

    /**
     * Gets the locations and sorts them into a list by distance
     * @param unsortedLocations List of unsorted locations in plan
     * @return List of sorted locations based on distance
     */
    public List<String> sortLocations(List<String> unsortedLocations) {

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

    /**
     * Method to carry out functionality whenever the user arrives at the current target
     */
    public void arriveCurrentTarget() { arrive(getCurrentTarget()); }

    public void arrivePreviousLocation() { arrive(getPreviousLocation()); }

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

    /**
     * Get the next exhibit in our plan
     * @param targetLocation String of the current location
     * @return String of the next exhibit on the route
     */
    private String getNextExhibitOf(String targetLocation) {

        for (Entry<String, List<LocEdge>> entry: directions.entrySet()) {
            String location = entry.getKey();
            List<LocEdge> path = entry.getValue();

            if (path.size() > 0
                    && (path.get(0).source_id.equals(targetLocation) || path.get(0).source_id.equals(groupIds.get(targetLocation)))
                    && distances.get(location) > 0
            ) return location;
        }

        // Nothing found, return null
        return null;
    }

    private String getPreviousLocationOf(String targetLocation) {
        if (!distances.containsKey(targetLocation))
            return null;

        String source = directions.get(targetLocation).get(0).source_id;
        for (Entry<String, String> groupIds : groupIds.entrySet()) {
            String location = groupIds.getKey();
            String groupId = groupIds.getValue();

            if (source.equals(groupId) && directions.get(location).size() > 0)
                return location;
        }
        return source;
    }

    private void arrive(String location) {
        Double previousDistance = distances.get(location);
        distances.replaceAll((l, d) -> d - previousDistance);
    }

    /**
     * Sorts a given Map of locations by their distances
     * @param map Map of the distances
     * @param <K> String name of the location
     * @param <V> total distance as a Double
     * @return Sorted Map of the locations based on distance
     */
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new HashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
