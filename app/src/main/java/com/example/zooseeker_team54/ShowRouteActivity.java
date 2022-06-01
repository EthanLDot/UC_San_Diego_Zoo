package com.example.zooseeker_team54;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
    private RouteInfo routeInfo;
    private RecyclerViewPresenter<LocItem> showRoutePresenter;

    private Button directionBtn;
    private Button backBtn;

    private final ActivityResultLauncher<Intent> directionActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if (data.hasExtra("routeInfo")) {
                            routeInfo = (RouteInfo) data.getSerializableExtra("routeInfo");
                            ((ShowRouteAdapter) showRoutePresenter.getAdapter()).setRouteInfo(routeInfo);
                        }
                    }
                }
            });

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

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // initialize major fields of this activity
        {
            viewModel = new ViewModelProvider(this).get(ViewModel.class);
            routeInfo = (RouteInfo) intent.getSerializableExtra("routeInfo");
            showRoutePresenter = new RecyclerViewPresenterBuilder<LocItem>()
                    .setAdapter(new ShowRouteAdapter(routeInfo))
                    .setRecyclerView(this.findViewById(R.id.planned_route))
                    .getRecyclerViewPresenter();
        }

        // add observer to this livedata
        viewModel.getAllPlannedUnvisitedLive()
                .observe(this, this::setItems);

        // initialize other components
        {
            directionBtn = findViewById(R.id.direction_btn);
            directionBtn.setOnClickListener(this::onDirectionBtnClicked);

            backBtn = findViewById(R.id.go_back_btn);
            backBtn.setOnClickListener(this::onBackButtonClicked);
        }
    }

    /**
     * Setter method to set a given list of LocItems for display
     *
     * @param locItems Given locations of the route
     */
    public void setItems(List<LocItem> locItems) {

        if (locItems.size() == 0) return;

        List<String> locations = locItems
                .stream()
                .map((locItem -> locItem.id))
                .collect(Collectors.toList());

        List<LocItem> sortedLocations = routeInfo.sortLocations(locations)
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
        Intent data = new Intent();
        data.putExtra("routeInfo", routeInfo);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }

    /**
     * Function for when the Direction button is clicked, and launches the ShowDirectionActivity
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
        directionActivityResultLauncher.launch(intent);
    }
}

