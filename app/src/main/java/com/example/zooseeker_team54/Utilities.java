package com.example.zooseeker_team54;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Pair;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

public class Utilities {
    private Graph<String, IdentifiedWeightedEdge> g;
    Map<String, ZooData.VertexInfo> vInfo;
    Map<String, ZooData.EdgeInfo> eInfo;

    public Utilities() {
        g = ZooData.loadZooGraphJSON("sample_zoo_graph.json");
        vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json");
        eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json");
    }

    // TODO: implement this function
    public List<LocItem> findRoute(List<LocItem> plannedLocItems) {
        return plannedLocItems;
    }

    public Pair<List<LocEdge>, Double> findShortestPathBetween(String start, String goal) {

        double sumWeight = 0;
        List<LocEdge> shortestPath = new ArrayList<>();
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, start, goal);

        int i = 1;
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {
            double weight = g.getEdgeWeight(e);
            String source = vInfo.get(g.getEdgeSource(e)).name;
            String target = vInfo.get(g.getEdgeTarget(e)).name;
            String description = String.format("  %d. Walk %.0f meters along %s from '%s' to '%s'.\n",
                    i, weight, eInfo.get(e.getId()).street, source, target);

            i++;
            sumWeight += weight;
            LocEdge locEdge = new LocEdge(weight, source, target, description);
            shortestPath.add(locEdge);
        }

        return new Pair<List<LocEdge>, Double>(shortestPath, sumWeight);
    }

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
}
