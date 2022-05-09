package com.example.zooseeker_team54;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Displays the directions to get to a specific exhibit in the route o planned exhibits
 */
public class RouteDirectionActivity extends AppCompatActivity {
    private ViewModel viewModel;
    DirectionsDisplayRecyclerView display;
    DirectionsDisplayNextButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Intent intent = getIntent();
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        // Initialize Recyclerview
        display = new DirectionsDisplayRecyclerView((List<LocEdge>) intent.getSerializableExtra("directions"),
                (HashMap<String, List<LocEdge>>) intent.getSerializableExtra("route"));
        display.setContext(this);
        display.initializeRecyclerView();

        // Initialize New Button
        button = new DirectionsDisplayNextButton(this, display);
        button.setViewModel(this.viewModel);
        button.initializeButton();
    }

    public void onBackToPlanBtnClicked(View view){ finish(); }

    public DirectionsDisplayRecyclerView getDisplayView() {
        return this.display;
    }

    public DirectionsDisplayNextButton getButton () {
        return this.button;
    }
}