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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

/**
 *  Class to represent the functionality of RouteDirectionActivity, which has been deleted.
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
            locationTracker.getUserCoordLive().observe(this, this::detectOffRoute);
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
            updateSkipBtn();
        }

    }

    /**
     * Getter method for the location tracker
     * @return the current location tracker
     */
    public LocationTracker getLocationTracker() {
        return locationTracker;
    }

    /**
     * Method to detect when the user is off the given route
     * @param coord Current coordinates of the user
     */
    private void detectOffRoute(Coord coord) {

        // filter exhibits part of group and exhibits that are not off route
        {
            String currentLocation = routeInfo.getCurrentLocation();
            LocItem currentLocItem = viewModel.getLocItemById(currentLocation);

            if (currentLocItem.group_id != null)
                currentLocation = routeInfo.getGroupId(currentLocation);

            if (coord.equals(viewModel.getLocItemById(currentLocation).getCoord())) {
                return;
            }
        }

        String currentTarget = routeInfo.getCurrentTarget();
        LocItem targetLocItem = viewModel.getLocItemById(currentTarget);

        LocItem closestLocItem = targetLocItem;
        double minDifference = Double.MAX_VALUE;
        List<LocItem> unvisitedLocItems = viewModel.getAllPlannedUnvisited();

        for (LocItem locItem : unvisitedLocItems) {

            // if the location is in a group, we want to find the route to its group instead.
            if (locItem.group_id != null) {
                locItem = viewModel.getLocItemById(locItem.group_id);
            }

            if (locItem.id.equals("entrance_exit_gate") && !currentTarget.equals("entrance_exit_gate"))
                continue;

            double difference = Coord.distanceBetweenTwoCoords(coord, locItem.getCoord());
            if (difference < minDifference) {
                closestLocItem = locItem;
                minDifference = difference;
            }
        }

        // if user doesn't want to reroute when there is a new target, return
        if (!closestLocItem.id.equals(targetLocItem.id) && !askForReroute())
            return;

        String startLocation = Utilities.findClosestExhibitId(viewModel.getAllNonGroup(), coord);
        RouteInfo newPlanForUnvisitedLocations = Utilities.findRoute(unvisitedLocItems, viewModel.getLocItemById(startLocation));
        routeInfo.updateTheRest(newPlanForUnvisitedLocations);

        // reset the directions
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
    }

    /**
     * Getter method for the replan boolean to indicate whether to replan or not
     *
     * @return boolean representing user input to replan or not
     */
    public boolean getShouldReplan() {
        return getSharedPreferences("shouldReplan", MODE_PRIVATE).getBoolean("shouldReplan", false);
    }

    /**
     * Method to ask for replan from the replan prompt activity
     *
     * @return Boolean indicating if user wants to replan route
     */
    public boolean askForReroute() {
        //return Utilities.offRouteReplanAlert(this);
        Intent intent = new Intent(this, ReplanPromptActivity.class);
        startActivity(intent);
        return getShouldReplan();
    }

    /**
     * Getter method for the RouteDirectionPresenter
     *
     * @return member variable RouteDirectionPresenter
     */
    public RecyclerViewPresenter<LocEdge> getRouteDirectionPresenter() {
        return routeDirectionPresenter;
    }

    /**
     * Method for when the next button is clicked
     *
     * @param view View to be launched from
     */
    private void onNextBtnClicked(View view) {

        if (!getDirection().equals("forward")) {
            setDirection("forward");
        } else {
            String arrivedLocation = routeInfo.getCurrentTarget();
            LocItem arrivedLocItem = viewModel.getLocItemById(arrivedLocation);
            viewModel.addVisitedLoc(arrivedLocItem);

            if (arrivedLocItem.group_id != null)
                locationTracker.mockLocation(viewModel.getLocItemById(arrivedLocItem.group_id).getCoord());
            else
                locationTracker.mockLocation(viewModel.getLocItemById(arrivedLocation).getCoord());

            String groupId = routeInfo.getGroupId(arrivedLocation);
            if (groupId != null) {
                List<String> ids = routeInfo.getIdsWithGroupId(groupId);
                for (String id : ids) {
                    viewModel.addVisitedLoc(viewModel.getLocItemById(id));
                }
            }

            routeInfo.arriveCurrentTarget();
        }

        String currentTarget = routeInfo.getCurrentTarget();
        List<LocEdge> directions = Utilities.findDirections(routeInfo, currentTarget, getIsBrief());
        routeDirectionPresenter.setItems(directions);

        // update buttons
        updateNextBtn(routeInfo.getCurrentTarget(), routeInfo.getNextTarget());
        updatePreviousBtn(routeInfo.getCurrentLocation());
        updateSkipBtn();
    }

    /**
     * Method for when previous button is clicked
     *
     * @param view View to be launched from
     */
    private void onPreviousBtnClicked(View view) {
        if (!getDirection().equals("backward")) {
            setDirection("backward");
        }
        else {
            String arrivedLocation = routeInfo.getCurrentLocation();
            LocItem arrivedLocItem = viewModel.getLocItemById(arrivedLocation);
            viewModel.removeVisitedLoc(arrivedLocItem);

            if (arrivedLocItem.group_id != null)
                locationTracker.mockLocation(viewModel.getLocItemById(arrivedLocItem.group_id).getCoord());
            else
                locationTracker.mockLocation(viewModel.getLocItemById(arrivedLocation).getCoord());

            String groupId = routeInfo.getGroupId(arrivedLocation);
            if (groupId != null) {
                List<String> ids = routeInfo.getIdsWithGroupId(groupId);
                for (String id : ids) {
                    viewModel.removeVisitedLoc(viewModel.getLocItemById(id));
                }
            }

            routeInfo.arrivePreviousLocation();
        }

        String currentLocation = routeInfo.getCurrentLocation();
        List<LocEdge> directions = Utilities.findReversedDirections(routeInfo, currentLocation, getIsBrief());
        routeDirectionPresenter.setItems(directions);

        // update buttons
        updateNextBtn(routeInfo.getCurrentLocation(), routeInfo.getCurrentTarget());
        updatePreviousBtn(routeInfo.getCurrentLocation());
        updateSkipBtn();
    }

    /**
     * Method for when the next button is clicked
     *
     * @param currTarget the current target location
     * @param nextTarget the next target location
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
     * Method to update the previous button with new information
     *
     * @param currentLocation the current location
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
     * Method to update the skip button with new information
     */
    private void updateSkipBtn() {
        if(routeInfo != null) {
            String currTarget = routeInfo.getCurrentTarget();
            String nextTarget = routeInfo.getNextTarget();
            if ((nextTarget == null || nextTarget.equals("entrance_exit_gate")) && (currTarget == null || currTarget.equals("entrance_exit_gate"))) {
                skipBtn.setClickable(false);
                skipBtn.setEnabled(false);
            } else {
                skipBtn.setClickable(true);
                skipBtn.setEnabled(true);
            }
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
        setCoord(getCoord());
        finish();
    }

    /**
     * Method for when the Settings button is clicked
     * @param view View to be launched from
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
     * Method for when the skip button is clicked
     * @param view View to be launched from
     */
    private void onSkipBtnClicked(View view){
        String target = routeInfo.getCurrentTarget();
        viewModel.removePlannedLoc(viewModel.getLocItemById(target));
        routeInfo.removeCurrentTarget();

        String currentLocation = routeInfo.getCurrentLocation();


        if (currentLocation.equals("entrance_exit_gate")) {
            routeInfo = Utilities.findRoute(viewModel.getAllPlannedUnvisited(), viewModel.getLocItemById("entrance_exit_gate"));
        }
        else {
            RouteInfo newPlanForUnvisitedLocations = Utilities.findRoute(viewModel.getAllPlannedUnvisited(), viewModel.getLocItemById(currentLocation));
            routeInfo.updateTheRest(newPlanForUnvisitedLocations);
        }

        List<LocEdge> directions = routeInfo.getDirection(routeInfo.getCurrentTarget());
        routeDirectionPresenter.setItems(directions);
        updateNextBtn(routeInfo.getCurrentTarget(), routeInfo.getNextTarget());
        updateSkipBtn();
    }

    /**
     * Getter method for the isBrief boolean in SharedPreferences
     * @return the isBrief boolean from SharedPreferences
     */
    public boolean getIsBrief() {
        return getPreferences(MODE_PRIVATE).getBoolean("isBrief", true);
    }

    /**
     * Getter method for the direction from SharedPreferences
     * @return the direction from SharedPreferences
     */
    public String getDirection() {
        return getPreferences(MODE_PRIVATE).getString("direction", "forward");
    }

    /**
     * Setter method for the direction
     * @param direction direction to be set
     */
    public void setDirection(String direction) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("direction", direction);
        editor.apply();
    }

    /**
     * Getter method the current coordinates
     * @return the current coordinates
     */
    public Coord getCoord() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        Coord DEFAULT_COORD = viewModel.getLocItemById("entrance_exit_gate").getCoord();
        String json = preferences.getString("coord", gson.toJson(DEFAULT_COORD));
        Coord coord = gson.fromJson(json, Coord.class);
        return coord;
    }

    /**
     * Setter method for coordinates
     * @param coord Coordinates to be set to
     */
    public void setCoord(Coord coord) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(coord);
        editor.putString("coord", json);
        editor.apply();
    }

    public RouteInfo getRouteInfo() { return routeInfo; }
}
