package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowRouteActivity extends AppCompatActivity {
    public RecyclerView showRouteView;

    private ViewModel viewModel;
    private ShowRouteAdapter showRouteAdapter;

    private HashMap<String, List<LocEdge>> route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);
        Intent intent = getIntent();
        route = (HashMap<String, List<LocEdge>>) intent.getSerializableExtra("route");

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        showRouteAdapter = new ShowRouteAdapter();
        showRouteAdapter.setHasStableIds(true);

        showRouteView = this.findViewById(R.id.planned_route);
        showRouteView.setLayoutManager(new LinearLayoutManager(this));
        showRouteView.setAdapter(showRouteAdapter);

        viewModel.getAllPlannedUnvisitedLive()
                .observe(this, showRouteAdapter::setLocItems);

    }
    public void onBackButtonClicked (View view) {
        finish();
    }

    public void onDirectionBtnClicked (View view) {
        Intent intent = new Intent(this, RouteDirectionActivity.class);
        List<LocEdge> directions = route.get(viewModel.getNextUnvisitedExhibit().id);
        intent.putExtra("directions", (ArrayList<LocEdge>) directions);
        startActivity(intent);
    }
}

