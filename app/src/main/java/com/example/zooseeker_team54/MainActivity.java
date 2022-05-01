package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // "source" and "sink" are graph terms for the start and end
        String start = "entrance_exit_gate";
        String goal = "elephant_odyssey";

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("assets/sample_zoo_graph.json");
        GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, start, goal);
        // 2. Load the information about our nodes and edges...
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("assets/sample_node_info.json");
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("assets/sample_edge_info.json");

        System.out.printf("The shortest path from '%s' to '%s' is:\n", start, goal);

        int i = 1;
        for (IdentifiedWeightedEdge e : path.getEdgeList()) {
            System.out.printf("  %d. Walk %.0f meters along %s from '%s' to '%s'.\n",
                    i,
                    g.getEdgeWeight(e),
                    eInfo.get(e.getId()).street,
                    vInfo.get(g.getEdgeSource(e).toString()).name,
                    vInfo.get(g.getEdgeTarget(e).toString()).name);
            i++;
        }

        // Set up listener for the search bar
        EditText searchBarField = this.findViewById(R.id.search_exhibits);
        searchBarComponent searchBar = new searchBarComponent(searchBarField);

        searchBarField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchBar.setQuery(searchBarField.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                // Stores search results as a list of strings.
                //Pair: id, followed by name
                List<Pair<String, String>> results = searchBar.searchQuery(vInfo);

                // FIXME: Should be implemented differently when the results display class is ready.
                if (results.size() == 0) {
                    Log.d("Results", "No Results");
                }
                else {
                    for (Pair<String, String> str: results) {
                        Log.d("Results", "Exhibit: " + str.second);
                    }
                    Log.d("Break", "——————————————————————————————————");
                }
            }
        });

        //testing code for the earlier class: will move to the test folder later
        /*List<Pair<String, String>> exhibits = new ArrayList<Pair<String, String>>();
        for(Map.Entry<String, ZooData.VertexInfo> entry : vInfo.entrySet())
        {
           if(("" + entry.getValue().kind).equals("EXHIBIT"))
           {
               exhibits.add(new Pair<String, String>(entry.getValue().id, entry.getValue().name));
           }
        }
        selectedExhibitComponent sec = new selectedExhibitComponent(exhibits, this);
        sec.display();*/
    }
}