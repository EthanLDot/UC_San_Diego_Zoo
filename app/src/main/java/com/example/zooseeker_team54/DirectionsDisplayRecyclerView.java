package com.example.zooseeker_team54;

import android.app.Activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DirectionsDisplayRecyclerView {
    List<LocEdge> directions;
    RouteDirectionAdapter routeDirectionAdapter;
    Activity context;
    RecyclerView routeDirectionView;

    public DirectionsDisplayRecyclerView(List<LocEdge> directions) {
        this.directions = directions;
        initializeAdapter();
    }

    public void setContext(Activity activity) {
        this.context = activity;
    }

    public void initializeAdapter() {
        routeDirectionAdapter = new RouteDirectionAdapter();
        routeDirectionAdapter.setHasStableIds(true);
        routeDirectionAdapter.setLocEdges(directions);
    }

    public void initializeRecyclerView() {
        routeDirectionView = context.findViewById(R.id.route_direction);
        routeDirectionView.setLayoutManager(new LinearLayoutManager(context));
        routeDirectionView.setAdapter(routeDirectionAdapter);
    }

    public void setDirections(List<LocEdge> directions) {
        this.directions = directions;
    }

    public List<LocEdge> getDirections() {
        return this.directions;
    }

    public RouteDirectionAdapter getAdapter() {
        return this.routeDirectionAdapter;
    }
}
