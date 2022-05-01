package com.example.zooseeker_team54;

import android.app.Activity;
import android.util.Pair;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class exhibitListComponent {

    List<Pair<String, String>> plans;
    Activity activity;
    int exhibitId;
    int displayCountId;
    boolean clickable;

    //assuming that a list of ids will be passed
    public exhibitListComponent(List<Pair<String, String>> plans, Activity activity,
                                int exhibitId, int displayCountId, boolean clickable)
    {
        this.plans = plans;
        this.activity = activity;
        this.exhibitId = exhibitId;
        this.displayCountId = displayCountId;
        this.clickable = clickable;
    }

    public void display()
    {
        if(displayCountId >= 0) {
            int size = plans.size();
            TextView plan_size = (TextView) activity.findViewById(displayCountId);
            plan_size.setText("" + size);
        }

        exhibitListAdapter adapter = new exhibitListAdapter();
        adapter.setHasStableIds(true);

        RecyclerView recyclerView = activity.findViewById(exhibitId);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        adapter.setSelections(plans, clickable);

    }
}
