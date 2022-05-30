package com.example.zooseeker_team54;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Collections;
import java.util.List;

/**
 *  Class to represent the functionality of RouteDirectionActivity
 */
public class ShowDirectionActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private RouteInfo routeInfo;
    private LocationTracker locationTracker;
    private RecyclerViewPresenter<LocEdge> routeDirectionPresenter;

    private Button nextBtn;
    private Button previousBtn;
    private Button backBtn;
    private Button settingsBtn;
    private Button skipBtn;
    private Button mockStep;
    private EditText mockRouteInput;

    /**
     * Create the activity from a given savedInstanceState and initialize everything
     * @param savedInstanceState saved instance from before
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        Intent intent = getIntent();

        // Initialize major fields
        {
            viewModel = new ViewModelProvider(this).get(ViewModel.class);
            routeInfo = (RouteInfo) intent.getSerializableExtra("routeInfo");
            locationTracker = new LocationTracker(this, false);
            routeDirectionPresenter = new RecyclerViewPresenterBuilder<LocEdge>()
                    .setAdapter(new ShowDirectionAdapter())
                    .setRecyclerView(findViewById(R.id.route_direction))
                    .getRecyclerViewPresenter();
        }

        // set direction to forward if current location is entrance and direction is backward
        {
            if (getDirection().equals("backward") && routeInfo.getCurrentLocation().equals("entrance_exit_gate"))
                setDirection("forward");
        }

        // set recycler view presenter
        {
            List<LocEdge> directions;
            if (routeInfo == null) {
                directions = Collections.emptyList();
            }
            else if (getDirection().equals("forward")) {
                directions = Utilities.findDirections(routeInfo, routeInfo.getCurrentTarget(), getIsBrief());
            }
            else {
                directions = Utilities.findReversedDirections(routeInfo, routeInfo.getCurrentLocation(), getIsBrief());
            }
            routeDirectionPresenter.setItems(directions);
        }

        // Initialize nextBtn
        {
            nextBtn = findViewById(R.id.next_btn);
            nextBtn.setOnClickListener(this::onNextBtnClicked);

            if (routeInfo == null) {
                updateNextBtn(null, null);
            }
            else if (getDirection().equals("forward")) {
                updateNextBtn(routeInfo.getCurrentTarget(), routeInfo.getNextTarget());
            }
            else {
                updateNextBtn(routeInfo.getCurrentLocation(), routeInfo.getCurrentTarget());
            }
        }

        // Initialize the back button
        {
            previousBtn = this.findViewById(R.id.previous_btn);
            previousBtn.setOnClickListener(this::onPreviousBtnClicked);

            if (routeInfo == null) updatePreviousBtn(null);
            else updatePreviousBtn(routeInfo.getCurrentLocation());
        }

        // Initialize other components
        {
            // Initialize the back button
            backBtn = this.findViewById(R.id.back_to_plan);
            backBtn.setOnClickListener(this::onBackToPlanBtnClicked);

            // Initialize the settings button
            settingsBtn = this.findViewById(R.id.settings_button);
            settingsBtn.setOnClickListener(this::onSettingsClicked);

            // Initialize the mock route input
            mockRouteInput = this.findViewById(R.id.mock_route_input);

            // Initialize the start mock button
            mockStep = this.findViewById(R.id.start_mock);
            mockStep.setOnClickListener(this::onMockStepClicked);

            //Initialize the skip button
            skipBtn = this.findViewById(R.id.skip_btn);
            skipBtn.setOnClickListener(this::onSkipBtnClicked);
        }

    }

    /**
     *
     * @return
     */
    public RecyclerViewPresenter<LocEdge> getRouteDirectionPresenter() {
        return routeDirectionPresenter;
    }

    /**
     *
     * @param view
     */
    private void onNextBtnClicked(View view) {

        if (!getDirection().equals("forward")) {
            setDirection("forward");
        } else {
            viewModel.addVisitedLoc(viewModel.getLocItemById(routeInfo.getCurrentTarget()));
            routeInfo.arriveCurrentTarget();
        }

        String currentTarget = routeInfo.getCurrentTarget();
        List<LocEdge> directions = Utilities.findDirections(routeInfo, currentTarget, getIsBrief());
        routeDirectionPresenter.setItems(directions);

        // update buttons
        updateNextBtn(routeInfo.getCurrentTarget(), routeInfo.getNextTarget());
        updatePreviousBtn(routeInfo.getCurrentLocation());
    }

    /**
     *
     * @param view
     */
    private void onPreviousBtnClicked(View view) {
        if (!getDirection().equals("backward")) {
            setDirection("backward");
        }
        else {
            viewModel.removeVisitedLoc(viewModel.getLocItemById(routeInfo.getCurrentLocation()));
            routeInfo.arrivePreviousLocation();
        }

        String currentLocation = routeInfo.getCurrentLocation();
        List<LocEdge> directions = Utilities.findReversedDirections(routeInfo, currentLocation, getIsBrief());
        routeDirectionPresenter.setItems(directions);

        // update buttons
        updateNextBtn(routeInfo.getCurrentLocation(), routeInfo.getCurrentTarget());
        updatePreviousBtn(routeInfo.getCurrentLocation());
    }

    /**
     *
     * @param currTarget
     * @param nextTarget
     */
    private void updateNextBtn(String currTarget, String nextTarget) {
        String buttonText;
        LocItem nextLocItem = viewModel.getLocItemById(nextTarget);

        if (nextLocItem == null || !nextLocItem.planned) {
            buttonText = "NEXT\n------\n" + "No Exhibits Left!";
            nextBtn.setClickable(false);
            nextBtn.setEnabled(false);
        }
        else {
            buttonText = "NEXT\n------\n" + nextLocItem.name + ", " + (routeInfo.getDistance(nextTarget).intValue() - routeInfo.getDistance(currTarget).intValue());
            nextBtn.setClickable(true);
            nextBtn.setEnabled(true);
        }
        nextBtn.setText(buttonText);
    }

    /**
     *
     * @param currentLocation
     */
    private void updatePreviousBtn(String currentLocation) {
        if (currentLocation == null || currentLocation.equals("entrance_exit_gate") ||
                (currentLocation.equals(routeInfo.getLocations().get(0)) &&
                getDirection().equals("backward"))) {
            previousBtn.setClickable(false);
            previousBtn.setEnabled(false);
        }
        else {
            previousBtn.setClickable(true);
            previousBtn.setEnabled(true);
        }
    }

    /**
     * Finishes the activity and goes back to the previous activity
     * @param view
     */
    private void onBackToPlanBtnClicked(View view){
        Intent data = new Intent();
        data.putExtra("routeInfo", routeInfo);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }

    /**
     *
     * @param view
     */
    private void onSettingsClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * Mocks the next location in the route by calling locationtracker
     * @param view
     */
    private void onMockStepClicked(View view) {
        String [] nextLocation = mockRouteInput.getText().toString().split(",");
        Coord locationCoord = new Coord (Double.parseDouble(nextLocation[0]), Double.parseDouble(nextLocation[1]));
        // Log.d("NEXT Coord: ", String.valueOf(locationCoord));
        locationTracker.mockLocation(locationCoord);
    }

    /**
     *
     * @param view
     */
    private void onSkipBtnClicked(View view){
        String target = routeInfo.getCurrentTarget();
        viewModel.removePlannedLoc(viewModel.getLocItemById(target));
        routeInfo.removeLocation(target);
        routeDirectionPresenter.setItems(routeInfo.getDirection(routeInfo.getCurrentTarget()));
        updateNextBtn(routeInfo.getCurrentTarget(), routeInfo.getNextTarget());
    }

    /**
     *
     * @return
     */
    private boolean getIsBrief() {
        return getPreferences(MODE_PRIVATE).getBoolean("isBrief", true);
    }

    /**
     *
     * @return
     */
    private String getDirection() {
        return getPreferences(MODE_PRIVATE).getString("direction", "forward");
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
}