package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public RecyclerView searchResultView;
    public RecyclerView plannedLocsView;

    public SearchResultAdapter searchResultAdapter;
    public PlannedLocsAdapter plannedLocsAdapter;


    private EditText searchBarText;
    private Button clearBtn;
    private TextView planSizeText;

    private ViewModel viewModel;
    private Utilities utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        utils = new Utilities(getApplication().getApplicationContext());

        // Get search bar EditText and bind a text watcher to it
        searchBarText = this.findViewById(R.id.search_bar);
        searchBarText.addTextChangedListener(searchBarTextWatcher);

        // Create an adapter for the RecyclerView of search results
        searchResultAdapter = new SearchResultAdapter();
        searchResultAdapter.setHasStableIds(true);
        searchResultAdapter.setOnSearchResultClicked(this::addPlannedLoc);

        // Set the adapter for the actual RecyclerView
        searchResultView = this.findViewById(R.id.search_results);
        searchResultView.setLayoutManager(new LinearLayoutManager(this));
        searchResultView.setAdapter(searchResultAdapter);

        // Create an adapter for the RecyclerView of search results
        plannedLocsAdapter = new PlannedLocsAdapter();
        plannedLocsAdapter.setOnDeleteClicked(this::removePlannedLoc);
        plannedLocsAdapter.setHasStableIds(true);

        // Set the adapter for the actual RecyclerView
        plannedLocsView = this.findViewById(R.id.planned_locs);
        plannedLocsView.setLayoutManager(new LinearLayoutManager(this));
        plannedLocsView.setAdapter(plannedLocsAdapter);

        //
        viewModel.getPlannedLocs()
                .observe(this, plannedLocsAdapter::setLocItems);

        // Show the size of the plan
        planSizeText = this.findViewById(R.id.plan_size);
        updatePlanSizeText();

        // Set up clear button for planned locs
        this.clearBtn = this.findViewById(R.id.clear_btn);
        clearBtn.setOnClickListener(this::onClearBtnClicked);

    }

    public List<LocEdge> findRoute(List<LocItem> plannedLocItems) {

        // the final route to return
        List<LocEdge> route = new ArrayList<>();

        // set up a list unvisited locations
        List<String> unvisited = new ArrayList<>();
        plannedLocItems.forEach((word) -> unvisited.add(word.id));

        // start at the entrance of the zoo
        String current = "entrance_exit_gate";

        // while there are still unvisited locations
        while (unvisited.size() > 0) {

            // initialize index, distance, and the path to the shortest planned locations
            int minIndex = 0;
            double minDist = Double.MAX_VALUE;
            String closest = "";
            List<LocEdge> minPath = new ArrayList<>();

            // loop through each other planned locations
            for (int i = 0; i < unvisited.size(); i++) {
                String target = unvisited.get(i);
                Pair<List<LocEdge>, Double> pair = Utilities.findShortestPathBetween(current, target);

                // if the distance is shorter than current min distance, update
                if (pair.second < minDist) {
                    minPath = pair.first;
                    minDist = pair.second;
                    minIndex = i;
                    closest = target;
                }
            }

            current = closest;
            route.addAll(minPath);
            unvisited.remove(minIndex);
        }

        //commented out for now
        /*String target = "entrance_exit_gate";
        Pair<List<LocEdge>, Double> pair = Utilities.findShortestPathBetween(current, target);
        route.addAll(pair.first);*/
        return route;
    }

    private void removePlannedLoc(LocItem locItem) {
        viewModel.removePlannedLoc(locItem);
        updatePlanSizeText();
    }

    private void addPlannedLoc(LocItem locItem) {
        viewModel.addPlannedLoc(locItem);
        updatePlanSizeText();
    }
    
    private void updatePlanSizeText() {
        planSizeText.setText(Integer.toString(viewModel.countPlannedExhibits()));
    }

    // Text Watcher for search bar textview
    private TextWatcher searchBarTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {
            showSearchResult(editable.toString());
        }
    };

    private void showSearchResult(String query) {

        // Display nothing when query is empty
        if (query.length() == 0) {
            searchResultAdapter.setLocItems(Collections.emptyList());
            return;
        }

        List<LocItem> allLocs = viewModel.getAll();
        List<LocItem> searchResults = new ArrayList<>();

        for(LocItem locItem : allLocs) {
            if (locItem.name.toLowerCase().contains(query.toLowerCase())
                    && !locItem.planned && locItem.kind.equals("exhibit")) {
                searchResults.add(locItem);
            }
        }
        searchResultAdapter.setLocItems(searchResults);
    }

    private void onClearBtnClicked(View view) {
        viewModel.clearPlannedLocs();
        planSizeText.setText("0");
    }

    public void onPlanButtonClicked(View view) {
        // should create plan on database to display on routePlanActivity and take us there

        // get number of exhibits in plan from the TextView
        String planSizeString = Integer.toString(plannedLocsAdapter.getItemCount());
        int planSize = Integer.parseInt(planSizeString);

        // show an alert if plan size is 0
        if (planSize == 0) {
            Utilities.showAlert(this, "Plan list is empty, can't create plan!");
            return;
        }

        List<LocEdge> route = findRoute(plannedLocsAdapter.getLocItems());
        System.out.println(route.size());

        Intent intent = new Intent(this, ShowRouteActivity.class);
        intent.putExtra("route", (ArrayList<LocEdge>) route);
        startActivity(intent);
    }
}
