package com.example.zooseeker_team54;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Activity to show the route of exhibits on our plan. Launched when "Plan" is clicked
 * on MainActivity
 */
public class ShowRouteActivity extends AppCompatActivity {

    private ViewModel viewModel;
    public RecyclerViewPresenter<LocItem> showRoutePresenter;

    private RouteInfo routeInfo;

    private Button directionBtn;
    private Button backBtn;

    private static final int ACTIVITY_CONSTANT = 1;

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
        routeInfo = (RouteInfo) intent.getSerializableExtra("routeInfo");

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        showRoutePresenter = new RecyclerViewPresenterBuilder<LocItem>()
                .setAdapter(new ShowRouteAdapter(routeInfo))
                .setRecyclerView(this.findViewById(R.id.planned_route))
                .getRecyclerViewPresenter();

        viewModel.getAllPlannedUnvisitedLive()
                .observe(this, this::setItems);

        directionBtn = findViewById(R.id.direction_btn);
        directionBtn.setOnClickListener(this::onDirectionBtnClicked);

        backBtn = findViewById(R.id.go_back_btn);
        backBtn.setOnClickListener(this::onBackButtonClicked);
    }

    /**
     *
     * @param locItems
     */
    public void setItems(List<LocItem> locItems) {
        List<String> locations = locItems
                .stream()
                .map((locItem -> locItem.id))
                .collect(Collectors.toList());

        List<LocItem> sortedLocations = routeInfo.getSortedLocations(locations)
                .stream()
                .map((location) -> viewModel.getLocItemById(location))
                .collect(Collectors.toList());

        showRoutePresenter.setItems(sortedLocations);
    }

    /**
     * Back out of the activity when the "Back" button is clicked
     *
     * @param view View that's passed in
     */
    private void onBackButtonClicked(View view) {
        finish();
    }

    /**
     * Function for when the Direction button is clicked, and launches the RouteDirectionActivity
     *
     * @param view
     */
    private void onDirectionBtnClicked(View view) {

        // user selection of brief display vs detailed display
        String target = routeInfo.getCurrentTarget();
        Intent intent = new Intent(this, ShowDirectionActivity.class);

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
        intent.putExtra("routeInfo", routeInfo);
        setDirection("forward");
        directionActivityResultLauncher.launch(intent);
    }


    /**
     *
     * @param direction
     */
    private void setDirection(String direction) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("direction", direction);
        editor.apply();
    }

    ActivityResultLauncher<Intent> directionActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if (data.hasExtra("routeInfo")) {
                            routeInfo = (RouteInfo) data.getSerializableExtra("routeInfo");
                            ((ShowRouteAdapter) showRoutePresenter.getAdapter()).setRouteInfo(routeInfo);
                            setDirection("forward");
                        }
                    }
                }
            });
}

