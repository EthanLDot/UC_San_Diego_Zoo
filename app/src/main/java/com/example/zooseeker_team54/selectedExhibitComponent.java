package com.example.zooseeker_team54;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class selectedExhibitComponent {

    List<String> plan;
    Activity activity;

    //assuming that a hashset of ids will be passed
    public selectedExhibitComponent(List<String> plan, Activity activity)
    {
        this.plan = plan;
        this.activity = activity;
    }

    public void display()
    {
        int size = plan.size();
        TextView plan_size =(TextView) activity.findViewById(R.id.planSize);
        plan_size.setText("" + size);

        selectionListAdapter adapter = new selectionListAdapter();
        adapter.setHasStableIds(true);
        RecyclerView recyclerView = activity.findViewById(R.id.selection_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        adapter.setSelections(plan);

    }
}
