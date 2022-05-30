package com.example.zooseeker_team54;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.util.Log;
import android.util.Pair;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;


/**
 * Utilities class for the findShortestPathBetween function and to create Alerts
 */
public class Utilities {
    private static Graph<String, IdentifiedWeightedEdge> g;
    private static Map<String, ZooData.VertexInfo> vInfo;
    private static Map<String, ZooData.EdgeInfo> eInfo;

    /**
     * Used to load new Zoo JSON information for use
     * @param context activity to be called from
     */
    public static void loadNewZooJson(Context context) {
        g = ZooData.loadZooGraphJSON("zoo_graph.json", context);
        vInfo = ZooData.loadVertexInfoJSON("exhibit_info.json", context);
        eInfo = ZooData.loadEdgeInfoJSON("trail_info.json", context);
    }

    /**
     * Used to get the old Zoo JSON info for use on initial creation of MainActivity
     * @param context activity to be called from
     */
    public static void loadOldZooJson(Context context) {
        g = ZooData.loadZooGraphJSON("sample_zoo_graph.json", context);
        vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", context);
        eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json", context);
    }


    /**
     * Find search results from a given search bar query
     * @param query String obtained from user input
     * @param allLocations List of all LocItems within zoo
     * @return List of LocItems to be displayed in the search results RecyclerView
     */
    public static List<LocItem> findSearchResult(String query, List<LocItem> allLocations) {
        if (query.length() == 0)
            return Collections.emptyList();

        List<LocItem> searchResults = new ArrayList<>();
        for (LocItem locItem : allLocations) {
            if (((locItem.name.toLowerCase().contains(query.toLowerCase()) || locItem.tags.contains(query))
                    && !locItem.planned && locItem.kind.equals("exhibit"))) {
                searchResults.add(locItem);
            }
        }
        return searchResults;
    }

    /**
     * Finds shortest path between two edges
     * @param start vertex we're starting from
     * @param goal  vertex we're trying to reach
     * @return      a pair that consists of the path and the total weight of that path
     */
    public static Pair<List<LocEdge>, Double> findShortestPathBetween(String start, String goal) {

        String current = start;
        ZooData.VertexInfo temp;
        List<LocEdge> shortestPath = new LinkedList<>();
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, start, goal);

        double sumWeight = 0;

        // iterate through the list of edges
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {
            double weight = g.getEdgeWeight(e);
            String id = e.getId();
            String street = eInfo.get(id).street;
            ZooData.VertexInfo source = vInfo.get(g.getEdgeSource(e));
            ZooData.VertexInfo target = vInfo.get(g.getEdgeTarget(e));

            // we need to swap source or target if the edge we are going is opposite
            if (!current.equals(g.getEdgeSource(e))) {
                temp = source;
                source = target;
                target = temp;
                current = g.getEdgeSource(e);
            }
            else {
                current = g.getEdgeTarget(e);
            }

            shortestPath.add(new LocEdge(id, weight, street, source.name, source.id, target.name, target.id));
            sumWeight += weight;
        }

        return new Pair<>(shortestPath, sumWeight);
    }

    /**
     * Method to make showing alerts easier
     * @param activity Activity to display the alert in
     * @param message message to be displayed in the alert
     */
    public static void showAlert(Activity activity, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert!")
                .setMessage(message)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.cancel();
                })
                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    /**
     * From a given list of LocItems, find the most optimal route through the graph
     * using our findShortestPathBetween function
     * @param unvisitedLocItems List of LocItems within plan to find a route for
     * @return route from the planned exhibits as a HashMap of edges
     */
    public static RouteInfo findRoute(List<LocItem> unvisitedLocItems, Coord coord, boolean startFromEntrance) {

        // the final route to return
        RouteInfo routeInfo = new RouteInfo();

        // set up a list unvisited locations
        List<String> unvisited = new ArrayList<>();
        unvisitedLocItems.forEach((word) -> unvisited.add(word.id));

        // start at the entrance of the zoo
        double currDist = 0;
        String current = startFromEntrance ? "entrance_exit_gate" : findClosestExhibitId(unvisitedLocItems, coord);

        // while there are still unvisited locations, find the closest to the last added one, and add it to the route
        while (unvisited.size() > 0) {

            // initialize index, distance, and the path to the shortest planned locations
            int minIndex = 0;
            String closest = "", target = "";
            double minDist = Double.MAX_VALUE;
            List<LocEdge> minPath = new LinkedList<>();

            // loop through each other planned locations
            for (int i = 0; i < unvisited.size(); i++) {
                target = unvisited.get(i);
                Pair<List<LocEdge>, Double> pair = findShortestPathBetween(current, target);

                // if the distance is shorter than current min distance, update
                if (pair.second < minDist) {
                    minPath = pair.first;
                    minDist = pair.second;
                    minIndex = i;
                    closest = target;
                }
            }

            // add minimum distance to the current distance
            currDist += minDist;

            // set closest to be current and remove the top element from unvisited
            current = closest;
            unvisited.remove(minIndex);

            // add the next path to paths and add the next distance to distances
            routeInfo.addDirection(closest, minPath);
            routeInfo.addDistance(closest, currDist);
        }

        // find the path from the last added exhibit and add it to the route
        String target = "entrance_exit_gate";
        Pair<List<LocEdge>, Double> pair = Utilities.findShortestPathBetween(current, target);
        routeInfo.addDirection(target, pair.first);

        currDist += pair.second;
        routeInfo.addDistance(target, currDist);

        return routeInfo;
    }

    // TODO: implement this
    /**
     *
     * @param unvisitedLocItems
     * @param latLng
     * @return
     */
    public static String findClosestExhibitId(List<LocItem> unvisitedLocItems, Coord coord) {
        return unvisitedLocItems
                .stream()
                .reduce((locItem1, locItem2) ->
                        Coord.distanceBetweenTwoCoords(locItem1.getCoord(), coord) < Coord.distanceBetweenTwoCoords(locItem2.getCoord(), coord) ?
                        locItem1 : locItem2)
                .get().id;
    }

    /**
     *
     * @param routeInfo
     * @param target
     * @param isBrief
     * @return
     */
    public static List<LocEdge> findDirections(RouteInfo routeInfo, String target, boolean isBrief) {
        if (target == null) { return Collections.emptyList(); }
        List<LocEdge> directions = routeInfo.getDirection(target);
        if (!isBrief) { return directions; }
        return getBriefDirections(directions);
    }

    /**
     *
     * @param routeInfo
     * @param target
     * @param isBrief
     * @return
     */
    public static List<LocEdge> findReversedDirections(RouteInfo routeInfo, String target, boolean isBrief) {
        if (target == null) { return Collections.emptyList(); }
        List<LocEdge> directions = routeInfo.getReversedDirection(target);
        if (!isBrief) { return directions; }
        return getBriefDirections(directions);
    }

    /**
     *
     * @param directions
     * @return
     */
    public static List<LocEdge> getBriefDirections(List<LocEdge> directions) {
        List<LocEdge> briefDirections = new ArrayList<>();

        // initialize the data
        LocEdge firstPath = directions.get(0);
        String currStreet = firstPath.street;
        String source = firstPath.source;
        String source_id = firstPath.source_id;
        String sink = firstPath.target;
        String sink_id = firstPath.target_id;
        double streetWeight = 0;

        // loop through the directions and create brief direction
        for (LocEdge edge : directions) {
            if (currStreet.equals(edge.street)) {
                streetWeight += edge.weight;
            } else {
                // add brief data into the new list
                briefDirections.add(new LocEdge("", streetWeight, currStreet, source, source_id, edge.source, edge.source_id));
                currStreet = edge.street;
                source = edge.source;
                source_id = edge.source_id;
                streetWeight = edge.weight;
                sink = edge.target;
                sink_id = edge.target_id;
            }
        }

        // for loop will not take care of the last item, thus we are adding it here
        briefDirections.add(new LocEdge("", streetWeight, currStreet, source, source_id, sink, sink_id));
        return briefDirections;
    }

    /**
     *
     * @param direction
     * @return
     */
    public static List<LocEdge> getReversedDirections(List<LocEdge> direction) {
        LinkedList<LocEdge> reversedDirections = new LinkedList<>();
        for (LocEdge edge : direction) { reversedDirections.addFirst(LocEdge.getReversedLocEdge(edge)); }
        return reversedDirections;
    }

    /**
     *
     * @param context
     * @return
     */
    public static String getTextFromBoard(Context context){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData textData = clipboard.getPrimaryClip();
        return textData.toString();
    }

    /**
     *
     * @return
     */
    public static List<Coord> getMockRoute(String [] locations) {
        // TODO: find a way to prompt the user and paste the JSON text or an URL
        // return Coords
        //      .interpolate(Coords.UCSD, Coords.ZOO, 12)
        //      .collect(Collectors.toList());

        List<Coord> coords = new ArrayList<>();

        for (int i = 0; i < locations.length; i++) {

        }

        return coords;
    }


}
