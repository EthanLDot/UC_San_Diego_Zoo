package com.example.zooseeker_team54;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Collections;
import java.util.List;

/**
 *  Class to represent the functionality of RouteDirectionActivity, which has been deleted.
 */
public class ShowDirectionActivity extends AppCompatActivity {

    public RecyclerViewPresenter<LocEdge> routeDirectionPresenter;

    private ViewModel viewModel;

    private Button nextBtn;
    private Button backBtn;
    private Button settingsBtn;

    private RouteInfo routeInfo;
    private EditText mockRouteInput;
    private Button mockStep;
  
    public LocationTracker locationTracker;

    /**
     * Create the activity from a given savedInstanceState and initialize everything
     * @param savedInstanceState saved instance from before
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        Intent intent = getIntent();

        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        routeInfo = (RouteInfo) intent.getSerializableExtra("routeInfo");

        // Create an adapter for the RecyclerView of route direction
        routeDirectionPresenter = new RecyclerViewPresenterBuilder<LocEdge>()
                .setAdapter(new ShowDirectionAdapter())
                .setRecyclerView(findViewById(R.id.route_direction))
                .getRecyclerViewPresenter();

        List<LocEdge> directions;
        if (routeInfo == null) directions = Collections.emptyList();
        else directions = Utilities.findDirections(routeInfo, viewModel.getLocItemById(routeInfo.getCurrentTarget()), getIsBrief());
        routeDirectionPresenter.setItems(directions);

        // Initialize the next button
        nextBtn = findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(this::onNextBtnClicked);

        if (routeInfo == null) updateNextBtn(null, null);
        else updateNextBtn(routeInfo.getCurrentTarget(), routeInfo.getNextTarget());

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

        // Initialize Location Tracker
        locationTracker = new LocationTracker(this, false);
    }

    /**
     *
     * @param view
     */
    public void onNextBtnClicked(View view) {

        viewModel.addVisitedLoc(viewModel.getLocItemById(routeInfo.getCurrentTarget()));

        // update database
        routeInfo.arriveCurrentTarget();

        // update nextButton
        String currTarget = routeInfo.getCurrentTarget();
        String nextTarget = routeInfo.getNextTarget();
        updateNextBtn(currTarget, nextTarget);

        // Update directions
        List<LocEdge> newDirections = Utilities.findDirections(routeInfo, viewModel.getLocItemById(currTarget), getIsBrief());
        routeDirectionPresenter.setItems(newDirections);
    }

    /**
     *
     * @param currTarget
     * @param nextTarget
     */
    public void updateNextBtn(String currTarget, String nextTarget) {
        String buttonText;
        LocItem nextLocItem = viewModel.getLocItemById(nextTarget);

        if (nextLocItem == null || !nextLocItem.planned) {
            buttonText = "NEXT\n------\n" + "No Exhibits Left!";
            nextBtn.setClickable(false);
            nextBtn.setEnabled(false);
        }
        else {
            buttonText = "NEXT\n------\n" + nextLocItem.name + ", " + (routeInfo.getDistance(nextTarget).intValue() - routeInfo.getDistance(currTarget).intValue());
            nextBtn.setEnabled(true);
        }
        nextBtn.setText(buttonText);
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
     *
     * @return
     */
    private boolean getIsBrief() {
        return getPreferences(MODE_PRIVATE).getBoolean("isBrief", true);
    }

    /**
     * Mocks the next location in the route by calling locationtracker
     * @param view
     */
    private void onMockStepClicked(View view) {
        String [] nextLocation = mockRouteInput.getText().toString().split(",");
        if(nextLocation.length == 2) {
            try {
                Coord locationCoord = new Coord(Double.parseDouble(nextLocation[0]), Double.parseDouble(nextLocation[1]));
                // Log.d("NEXT Coord: ", String.valueOf(locationCoord));
                locationTracker.mockLocation(locationCoord);
                Toast.makeText(this, "Coordinates mocked!",
                        Toast.LENGTH_LONG).show();
            } catch (NumberFormatException nfe) {
                Toast.makeText(this, "Invalid Coords!",
                        Toast.LENGTH_LONG).show();
                return;
            }

        }
        else
        {
            Toast.makeText(this, "Invalid Coords!",
                    Toast.LENGTH_LONG).show();
        }
    }

}