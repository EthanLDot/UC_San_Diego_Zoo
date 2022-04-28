package com.example.zooseeker_team54;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class selectedExhibitComponent {

    List<String> plan;
    private static final int PLANSIZE = R.id.planSize;
    Activity activity;

    //assuming that a hashset of ids will be passed
    public selectedExhibitComponent(List<String> plan, Activity activity)
    {
        this.plan = plan;
        this.activity = activity;
    }

    public void display()
    {
        Log.d("selectedExhibitComponent", "" + plan.size());
        for(String s: plan)
        {
            Log.d("selectedExhibitComponent", s);
        }
        int size = plan.size();
        TextView plan_size =(TextView) activity.findViewById(PLANSIZE);
        plan_size.setText("" + size);

    }
}
