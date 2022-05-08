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

public class RouteDirectionActivity extends AppCompatActivity {
    public RecyclerView routeDirectionView;
    public Button nextButton;

    private ViewModel viewModel;
    private RouteDirectionAdapter routeDirectionAdapter;

    List<LocEdge> directions;
    HashMap<String, List<LocEdge>> route;
    LocItem newTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Intent intent = getIntent();
        directions = (List<LocEdge>) intent.getSerializableExtra("directions");
        route = (HashMap<String, List<LocEdge>>) intent.getSerializableExtra("route");

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        routeDirectionAdapter = new RouteDirectionAdapter();
        routeDirectionAdapter.setHasStableIds(true);
        routeDirectionAdapter.setLocEdges(directions);

        nextButton = this.findViewById(R.id.next_btn);
        newTarget = getNewTarget();
        configureButton(newTarget);
        nextButton.setOnClickListener(this::onNextBtnClicked);

        routeDirectionView = this.findViewById(R.id.route_direction);
        routeDirectionView.setLayoutManager(new LinearLayoutManager(this));
        routeDirectionView.setAdapter(routeDirectionAdapter);

    }

    public void onBackToPlanBtnClicked(View view){ finish(); }

    public void onNextBtnClicked(View view) {
        List<LocEdge> newDirections = route.get(newTarget.id);
        routeDirectionAdapter.setLocEdges(newDirections);
        nextButton = this.findViewById(R.id.next_btn);
        newTarget = getNewTarget();
        configureButton(newTarget);
    }

    LocItem getNewTarget()
    {
        // Step 1: recalculate currDist for all unvisited LocItems
        LocItem currentTarget = viewModel.getNextUnvisitedExhibit();
        if(currentTarget == null)
        {
            nextButton.setText("NEXT");
            nextButton.setClickable(false);
            nextButton.setEnabled(false);
            return null;
        }
        Double currentTargetDist = currentTarget.currDist;

        List<LocItem> unvisited = viewModel.getAllPlannedUnvisited();
        for (LocItem locItem : unvisited) {
            viewModel.updateLocCurrentDist(locItem, locItem.currDist - currentTargetDist);
        }
        viewModel.addVisitedLoc(currentTarget);

        //Step 2: choose next exhibit to travel to
        newTarget = viewModel.getNextUnvisitedExhibit();
        return newTarget;
    }

    void configureButton(LocItem newTarget)
    {

        if (newTarget == null || !newTarget.kind.equals("exhibit") || !newTarget.planned) {
            nextButton.setText("NEXT");
            nextButton.setClickable(false);
            nextButton.setEnabled(false);
        }
        else
        {
            nextButton.setEnabled(true);
            String newText = "NEXT\n------\n" + newTarget.name + ", " + (int)newTarget.currDist;
            nextButton.setText(newText);
        }
    }
}