package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class RoutePlanActivity extends AppCompatActivity {
    public RecyclerView plannedRouteView;

    private PlannedLocsAdapter plannedRouteAdapter;

    private List<LocItem> route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);

        Intent intent = getIntent();
        route = (List<LocItem>) intent.getSerializableExtra("route");

        plannedRouteAdapter = new PlannedLocsAdapter();
        plannedRouteAdapter.setHasStableIds(true);
        plannedRouteAdapter.setLocItems(route);

        plannedRouteView = this.findViewById(R.id.planned_route);
        plannedRouteView.setLayoutManager(new LinearLayoutManager(this));
        plannedRouteView.setAdapter(plannedRouteAdapter);

    }
    public void onBackButtonClicked (View view) {
        finish();
    }
}

