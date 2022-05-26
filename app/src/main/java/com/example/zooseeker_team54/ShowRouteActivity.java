package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Activity to show the route of exhibits on our plan. Launched when "Plan" is clicked
 * on MainActivity
 */
public class ShowRouteActivity extends AppCompatActivity {

    private ViewModel viewModel;
    public RecyclerViewPresenter<LocItem> showRoutePresenter;

    private HashMap<String, List<LocEdge>> route;

    /**
     * Create the activity from a given savedInstanceState and initialize everything
     *
     * @param savedInstanceState the saved instance from before
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);
        Intent intent = getIntent();
        route = (HashMap<String, List<LocEdge>>) intent.getSerializableExtra("route");

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        showRoutePresenter = new RecyclerViewPresenterBuilder<LocItem>()
                .setAdapter(new ShowRouteAdapter())
                .setRecyclerView(this.findViewById(R.id.planned_route))
                .getRecyclerViewPresenter();

        viewModel.getAllPlannedUnvisitedLive()
                .observe(this, showRoutePresenter::setItems);

    }

    /**
     * Back out of the activity when the "Back" button is clicked
     *
     * @param view View that's passed in
     */
    public void onBackButtonClicked(View view) {
        finish();
    }

    /**
     * Function for when the Direction button is clicked, and launches the RouteDirectionActivity
     *
     * @param view
     */
    public void onDirectionBtnClicked(View view) {

        // user selection of brief display vs detailed display
        LocItem target = viewModel.getNextUnvisitedExhibit();
        Intent intent = new Intent(this, RouteDirectionActivity.class);

        // show an alert if target doesn't exist or is null
        if (target == null) {
            String alertMessage = "All exhibits visited! " + "" +
                    "Please clear all selections on the previous page " +
                    "with the CLEAR button " +
                    "and select more exhibits to visit.";
            Utilities.showAlert(this, alertMessage);
            return;
        }

        //
        intent.putExtra("route", route);
        startActivity(intent);
    }

    public boolean getIsBrief() {
        return getPreferences(MODE_PRIVATE).getBoolean("isBrief", true);
    }
}

