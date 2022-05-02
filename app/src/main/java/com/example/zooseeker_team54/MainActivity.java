package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.w3c.dom.Text;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public RecyclerView searchResultView;
    public RecyclerView plannedExhibitsView;

    private EditText searchBarText;

    private ExhibitViewModel exhibitViewModel;
    private SearchResultAdapter searchResultAdapter;
    private PlannedExhibitsAdapter plannedExhibitsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exhibitViewModel = new ViewModelProvider(this).get(ExhibitViewModel.class);

        // Get search bar EditText and bind a text watcher to it
        searchBarText = this.findViewById(R.id.search_bar);
        searchBarText.addTextChangedListener(searchBarTextWatcher);

        // Create an adapter for the RecyclerView of search results
        searchResultAdapter = new SearchResultAdapter();
        searchResultAdapter.setOnSearchResultClicked(exhibitViewModel::addPlannedExhibit);
        searchResultAdapter.setHasStableIds(true);

        // Set the adapter for the actual RecyclerView
        searchResultView = this.findViewById(R.id.search_results);
        searchResultView.setLayoutManager(new LinearLayoutManager(this));
        searchResultView.setAdapter(searchResultAdapter);

        // Create an adapter for the RecyclerView of search results
        plannedExhibitsAdapter = new PlannedExhibitsAdapter();
        plannedExhibitsAdapter.setHasStableIds(true);

        // Set the adapter for the actual RecyclerView
        plannedExhibitsView = this.findViewById(R.id.planned_exhibits);
        plannedExhibitsView.setLayoutManager(new LinearLayoutManager(this));
        plannedExhibitsView.setAdapter(plannedExhibitsAdapter);

        exhibitViewModel.getPlannedExhibits()
                .observe(this, plannedExhibitsAdapter::setExhibitItems);

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

    void showSearchResult(String query) {

        // Display nothing when query is empty
        if (query.length() == 0) {
            searchResultAdapter.setExhibitItems(Collections.emptyList());
            return;
        }

        List<ExhibitItem> allExhibits = exhibitViewModel.getAll();
        List<ExhibitItem> searchResults = new ArrayList<>();

        for(ExhibitItem exhibitItem : allExhibits) {
            if (exhibitItem.name.toLowerCase().contains(query.toLowerCase()) && !exhibitItem.planned) {
                searchResults.add(exhibitItem);
            }
        }
        searchResultAdapter.setExhibitItems(searchResults);
    }
}
