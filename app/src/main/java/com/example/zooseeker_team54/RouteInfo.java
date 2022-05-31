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
    private int index;

    /**
     * Constructor for RouteInfo that instantiates new member variables
     */
    public RouteInfo() {
        directions = new HashMap<>();
        distances = new HashMap<>();
        groupIds = new HashMap<>();
        index = 0;
    }

    /**
     * Getter method for the member variable directions
     *
     * @return member variable directions as a Map<String, List<LocEdge>>
     */
    public Map<String, List<LocEdge>> getDirections() {
        return directions;
    }

    /**
     * Getter method for the member variable distances
     *
     * @return member variable distances as a Map<String, Double>
     */
    public Map<String, Double> getDistances() {
        return distances;
    }

    /**
     * Adds a direction into the directions Map
     *
     * @param location Name of location
     * @param direction List of LocEdges representing the directions
     */
    public void addDirection(String location, List<LocEdge> direction) {
        directions.put(location, direction);
    }

    /**
     * Adds a distance to the distances Map
     *
     * @param location Name of the location
     * @param distance The current distance to be added
     */
    public void addDistance(String location, Double distance) {
        distances.put(location, distance);
    }

    /**
     * Adds a given group ID to a given location
     *
     * @param location String location to be ID'd
     * @param groupId String ID for the given location
     */
    public void addGroupId(String location, String groupId) {
        groupIds.put(location, groupId);
    }

    /**
     * Getter method for the path from a given location
     *
     * @param location String name of the desired location
     * @return List of LocEdges for the directions to the passed in location
     */
    public List<LocEdge> getDirection(String location) {
        return directions.get(location);
    }

    /**
     * Get reversed directions based on location
     *
     * @param location String location to start from
     * @return List of LocEdges representing the reversed directions
     */
    public List<LocEdge> getReversedDirection(String location) {
        if (!directions.containsKey(location)) return null;
        return Utilities.getReversedDirections(directions.get(location));
    }

    /**
     * Retrieves the group ID of a given location
     *
     * @param location String of the location to retrieve its group ID
     * @return Group ID of the given location
     */
    public String getGroupId(String location) {
        return groupIds.get(location);
    }

    /**
     * Get IDs of locations that have the same group ID
     *
     * @param groupId Group ID of locations wanted
     * @return List of IDs as Strings
     */
    public List<String> getIdsWithGroupId(String groupId) {
        List<String> ids = new ArrayList<>();

        for (Entry<String, String> pair : groupIds.entrySet()) {
            if (pair.getValue().equals(groupId)) ids.add(pair.getKey());
        }

        return ids;
    }

    /**
     * Getter method for the current distance to a given location
     *
     * @param location String name of the desired location
     * @return Double representation of the current distance
     */
    public Double getDistance(String location) {
        return distances.get(location);
    }

    /**
     * Gets all of the locations within the route
     *
     * @return List of locations on the route as strings
     */
    public List<String> getLocations() {
        // iterate through our sorted map
        return sortLocations(new ArrayList<>(distances.keySet()));
    }

    /**
     * Gets the current location
     *
     * @return String of the current location
     */
    public String getCurrentLocation() {
        if (index >= getLocations().size() + 1 || index < 0)
            return null;
        else if (index == 0)
            return "entrance_exit_gate";
        return getLocations().get(index - 1);
    }

    /**
     * Gets the current target
     *
     * @return String of the current target
     */
    public String getCurrentTarget() {
        if (index >= getLocations().size() || index < 0)
            return null;
        return getNextExhibitOf(getCurrentLocation());
    }

    /**
     * Gets the next target
     *
     * @return String of the next target
     */
    public String getNextTarget() {
        if (index >= getLocations().size() - 1 || index < 0)
            return null;
        return getNextExhibitOf(getCurrentTarget());
    }

    /**
     * Gets previous location
     *
     * @return Previous location as a String
     */
    public String getPreviousLocation() {
        if (index == 0)
            return null;
        else if (index == 1)
            return "entrance_exit_gate";
        else
            return getPreviousLocationOf(getCurrentLocation());
    }

    /**
     * Gets the locations and sorts them into a list by distance
     *
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

    /**
     * Method to carry out functionality whenever the user arrives at the current target
     */
    public void arriveCurrentTarget() {
        if (index >= getLocations().size() + 1 || index < 0) return;
        String currentTarget = getCurrentTarget();
        index = getLocations().indexOf(currentTarget) + 1;
        arrive(currentTarget);
    }

    /**
     * Used to indicate when the user has arrived at the previous location
     */
    public void arrivePreviousLocation() {
        if (index >= getLocations().size() + 1 || index < 0) return;
        String previousLocation = getPreviousLocation();
        index = getLocations().indexOf(previousLocation) + 1;
        arrive(previousLocation);
    }

    /**
     * Method to remove the current target
     */
    public void removeCurrentTarget() {
        String currentTarget = getCurrentTarget();
        if (currentTarget == null) return;

        if (!groupIds.containsKey(currentTarget)) {
            directions.remove(currentTarget);
            distances.remove(currentTarget);
            groupIds.remove(currentTarget);
            return;
        }

        List<String> ids = getIdsWithGroupId(groupIds.get(currentTarget));
        for (String id : ids) {
            directions.remove(id);
            distances.remove(id);
            groupIds.remove(id);
        }
    }

    /**
     * Method used to update the rest of the route after
     *
     * @param routeForTheRest RouteInfo with the rest of the un-updated locations
     */
    public void updateTheRest(RouteInfo routeForTheRest) {
        String currentLocation = getCurrentLocation();

        // update directions
        for (Entry<String, List<LocEdge>> entry : routeForTheRest.directions.entrySet()) {
            if (!entry.getKey().equals(currentLocation) || currentLocation.equals("entrance_exit_gate"))
                directions.put(entry.getKey(), entry.getValue());
        }

        // update distances
        for (Entry<String, Double> entry : routeForTheRest.distances.entrySet()) {
            if (!entry.getKey().equals(currentLocation) || currentLocation.equals("entrance_exit_gate"))
                distances.put(entry.getKey(), entry.getValue());
        }

        // update groupIds
        for (Entry<String, String> entry : routeForTheRest.groupIds.entrySet()) {
            if (!entry.getKey().equals(currentLocation) || currentLocation.equals("entrance_exit_gate"))
                groupIds.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Get the next exhibit in our plan
     *
     * @param targetLocation String of the current location
     * @return String of the next exhibit on the route
     */
    private String getNextExhibitOf(String targetLocation) {

        List<String> locations = getLocations();
        if (index == 0 && targetLocation.equals("entrance_exit_gate"))
            return locations.size() > 0 ? locations.get(0) : null;

        int position = locations.indexOf(targetLocation);
        for (int i = position + 1; i < locations.size(); i++) {
            String next = locations.get(i);
            List<LocEdge> direction = directions.get(next);
            if (direction.size() > 0) return next;
        }

        // Nothing found, return null
        return null;
    }

    /**
     * Gets the previous location of a given location
     *
     * @param targetLocation Location to get the previous of
     * @return Previous location of a given location
     */
    private String getPreviousLocationOf(String targetLocation) {

        List<String> locations = getLocations();
        if (index == 0) return null;
        if (index == 1) return "entrance_exit_gate";

        int position = locations.indexOf(targetLocation);
        for (int i = position - 1; i >= 0; i--) {
            String previous = locations.get(i);
            List<LocEdge> direction = directions.get(previous);
            if (direction.size() > 0) return previous;
        }

        return "entrance_exit_gate";
    }

    /**
     * Used to indicate when the user has arrived at a location
     *
     * @param location Location arrived at
     */
    private void arrive(String location) {
        Double previousDistance = distances.get(location);
        distances.replaceAll((l, d) -> d - previousDistance);
    }

    /**
     * Sorts a given Map of locations by their distances
     *
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
