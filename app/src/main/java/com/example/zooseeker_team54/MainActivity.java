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



        // Set up listener for the search bar
        EditText searchBarField = this.findViewById(R.id.search_exhibits);
        SearchBarComponent searchBar = new SearchBarComponent(searchBarField);
        Activity myActivity = this;
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
                    //list of stuff, activity, id for the recycler view, display count or not (if so, pass id), clickable or not
                    ExhibitListComponent sec = new ExhibitListComponent(results, myActivity, R.id.search_results, -1, true);
                    sec.display();
                    Log.d("Results", "No Results");
                }
                else {
                    //printOutput(results);
                    for (Pair<String, String> str: results) {
                        Log.d("Results", "Exhibit: " + str.second);
                    }
                    Log.d("Break", "——————————————————————————————————");
                    ExhibitListComponent sec = new ExhibitListComponent(results, myActivity, R.id.search_results, -1, true);
                    sec.display();
                }
            }
        });
        //testing code for the earlier class: will move to the test folder later
        List<Pair<String, String>> exhibits = new ArrayList<Pair<String, String>>();
        for(Map.Entry<String, ZooData.VertexInfo> entry : vInfo.entrySet())
        {
           if(("" + entry.getValue().kind).equals("EXHIBIT"))
           {
               exhibits.add(new Pair<String, String>(entry.getValue().id, entry.getValue().name));
           }
        }
        ExhibitListComponent sec = new ExhibitListComponent(exhibits, this, R.id.selection_items, R.id.planSize, false);
        sec.display();
    }
}