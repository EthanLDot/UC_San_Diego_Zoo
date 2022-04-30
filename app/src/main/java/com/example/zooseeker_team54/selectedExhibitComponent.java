package com.example.zooseeker_team54;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class selectedExhibitComponent {

    List<Pair<String, String>> plans;
    Activity activity;

    //assuming that a list of ids will be passed
    public selectedExhibitComponent(List<Pair<String, String>> plans, Activity activity)
    {
        this.plans = plans;
        this.activity = activity;
    }

    public void display()
    {
        int size = plans.size();
        TextView plan_size =(TextView) activity.findViewById(R.id.planSize);
        List<String> planNames = new ArrayList<>();
        plan_size.setText("" + size);
        for (Pair<String, String> plan: plans)
        {
            planNames.add(plan.second);
        }

        selectionListAdapter adapter = new selectionListAdapter();
        adapter.setHasStableIds(true);
        RecyclerView recyclerView = activity.findViewById(R.id.selection_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        adapter.setSelections(planNames);

    }
}
