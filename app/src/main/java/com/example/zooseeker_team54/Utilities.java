package com.example.zooseeker_team54;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

public class Utilities {
    private static Graph<String, IdentifiedWeightedEdge> g;
    private static Map<String, ZooData.VertexInfo> vInfo;
    private static Map<String, ZooData.EdgeInfo> eInfo;

    public Utilities(Context context) {
        g = ZooData.loadZooGraphJSON("sample_zoo_graph.json", context);
        vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", context);
        eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json", context);
    }

    //simply accesses the vinfo to find all exhibits and returns their names as a list.
    public static List<String> getExhibitList()
    {
        List<String> list = new ArrayList<>();
        vInfo.forEach((k,v) -> {
            if(("" + v.kind).equals("EXHIBIT"))
            {
                list.add(v.name);
            }
        });
        return list;
    }


    public static Pair<List<LocEdge>, Double> findShortestPathBetween(String start, String goal) {

        String current = start, temp;
        List<LocEdge> shortestPath = new ArrayList<>();
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, start, goal);

        double sumWeight = 0;
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {
            double weight = g.getEdgeWeight(e);
            String id = e.getId();
            String street = eInfo.get(id).street;
            String source = vInfo.get(g.getEdgeSource(e)).name;
            String target = vInfo.get(g.getEdgeTarget(e)).name;

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

            shortestPath.add(new LocEdge(id, weight, street, source, target));
            sumWeight += weight;
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
