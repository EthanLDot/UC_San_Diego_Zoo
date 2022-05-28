package com.example.zooseeker_team54;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/**
 *  Class to represent the functionality of RouteDirectionActivity
 */
public class RouteDirectionActivity extends AppCompatActivity {

    public RecyclerViewPresenter<LocEdge> routeDirectionPresenter;

    private ViewModel viewModel;

    private Button nextBtn;
    private Button backBtn;
    private Button settingsBtn;

    private EditText mockRouteInput;
    private Button mockStep;

    private HashMap<String, List<LocEdge>> route;

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

        route = (HashMap<String, List<LocEdge>>) intent.getSerializableExtra("route");

        // Create an adapter for the RecyclerView of route direction
        routeDirectionPresenter = new RecyclerViewPresenterBuilder<LocEdge>()
                .setAdapter(new RouteDirectionAdapter())
                .setRecyclerView(findViewById(R.id.route_direction))
                .getRecyclerViewPresenter();

        List<LocEdge> directions = Utilities.findDirections(route, viewModel.getCurrTarget(), getIsBrief());
        routeDirectionPresenter.setItems(directions);

        // Initialize the next button
        nextBtn = findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(this::onNextBtnClicked);
        updateNextBtn(viewModel.getCurrTarget(), viewModel.getNextTarget());

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

        // update database
        viewModel.arriveCurrentTarget();

        // update nextButton
        LocItem currTarget = viewModel.getCurrTarget();
        LocItem nextTarget = viewModel.getNextTarget();
        updateNextBtn(currTarget, nextTarget);

        // Update directions
        List<LocEdge> newDirections = Utilities.findDirections(route, currTarget, getIsBrief());
        routeDirectionPresenter.setItems(newDirections);
    }

    /**
     *
     * @param currTarget
     * @param nextTarget
     */
    public void updateNextBtn(LocItem currTarget, LocItem nextTarget) {
        String buttonText;
        if (nextTarget == null || !nextTarget.planned) {
            buttonText = "NEXT\n------\n" + "No Exhibits Left!";
            nextBtn.setClickable(false);
            nextBtn.setEnabled(false);
        }
        else {
            buttonText = "NEXT\n------\n" + nextTarget.name + ", " + (int) (nextTarget.currDist - currTarget.currDist);
            nextBtn.setEnabled(true);
        }
        nextBtn.setText(buttonText);
    }

    /**
     * Finishes the activity and goes back to the previous activity
     * @param view
     */
    private void onBackToPlanBtnClicked(View view){ finish(); }

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
        Coord locationCoord = new Coord (Double.parseDouble(nextLocation[0]), Double.parseDouble(nextLocation[1]));
        // Log.d("NEXT Coord: ", String.valueOf(locationCoord));
        locationTracker.mockLocation(locationCoord);
    }

}