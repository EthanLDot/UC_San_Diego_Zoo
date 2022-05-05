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
    private static final Graph<String, IdentifiedWeightedEdge> g
            = ZooData.loadZooGraphJSON("sample_zoo_graph.json");
    private static final Map<String, ZooData.VertexInfo> vInfo
            = ZooData.loadVertexInfoJSON("sample_node_info.json");
    private static final Map<String, ZooData.EdgeInfo> eInfo
            = ZooData.loadEdgeInfoJSON("sample_edge_info.json");

    public static Pair<List<LocEdge>, Double> findShortestPathBetween(String start, String goal) {

        List<LocEdge> shortestPath = new ArrayList<>();
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, start, goal);

        int i = 1;
        double sumWeight = 0;
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {
            double weight = g.getEdgeWeight(e);
            String id = e.getId();
            String street = eInfo.get(id).street;
            String source = vInfo.get(g.getEdgeSource(e)).name;
            String target = vInfo.get(g.getEdgeTarget(e)).name;

            LocEdge locEdge = new LocEdge(id, weight, street, source, target);
            shortestPath.add(locEdge);

            sumWeight += weight;
            i++;
        }

        return new Pair<>(shortestPath, sumWeight);
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
