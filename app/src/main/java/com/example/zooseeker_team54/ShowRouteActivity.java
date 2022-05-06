package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class ShowRouteActivity extends AppCompatActivity {
    public RecyclerView showRouteView;

    private ShowRouteAdapter showRouteAdapter;

    private List<LocEdge> route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);
        Intent intent = getIntent();
        route = (List<LocEdge>) intent.getSerializableExtra("route");

        showRouteAdapter = new ShowRouteAdapter();
        showRouteAdapter.setHasStableIds(true);
        showRouteAdapter.setLocEdges(route);

        showRouteView = this.findViewById(R.id.planned_route);
        showRouteView.setLayoutManager(new LinearLayoutManager(this));
        showRouteView.setAdapter(showRouteAdapter);

    }
    public void onBackButtonClicked (View view) {
        finish();
    }

    public void onDirectionBtnClicked (View view) {
        Intent intent = new Intent(this, RouteDirectionActivity.class);
        startActivity(intent);
    }
}

