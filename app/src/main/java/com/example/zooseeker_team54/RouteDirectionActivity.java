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

    private RouteDirectionAdapter routeDirectionAdapter;

    List<LocEdge> directions;
    HashMap<String, List<LocEdge>> route;
    List<LocEdge> nextDirections;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Intent intent = getIntent();
        directions = (List<LocEdge>) intent.getSerializableExtra("directions");
        route = (HashMap<String, List<LocEdge>>) intent.getSerializableExtra("route");

        routeDirectionAdapter = new RouteDirectionAdapter();
        routeDirectionAdapter.setHasStableIds(true);
        routeDirectionAdapter.setLocEdges(directions);

        nextButton = this.findViewById(R.id.next_btn);

        //getting next set of directions in advance, if they exist
        ViewModel viewModel = new ViewModelProvider(this).get(ViewModel.class);
        nextDirections = route.get(viewModel.getNextUnvisitedExhibit().id);
        //nextButton.setClickable(false);
        nextButton.setAlpha(.5f);
        nextButton.setText(nextButton.getText() + "\n------\ntest");

        routeDirectionView = this.findViewById(R.id.route_direction);
        routeDirectionView.setLayoutManager(new LinearLayoutManager(this));
        routeDirectionView.setAdapter(routeDirectionAdapter);

    }

    public void onBackToPlanBtnClicked(View view){ finish(); }

    public void onNextBtnClicked(View view) {
        Intent intent = new Intent(this, RouteDirectionActivity.class);
        intent.putExtra("directions", (ArrayList<LocEdge>) nextDirections);
        intent.putExtra("route", route);
        //startActivity(intent);
    }
}
