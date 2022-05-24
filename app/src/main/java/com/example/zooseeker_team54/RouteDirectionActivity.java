package com.example.zooseeker_team54;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    private ViewModel viewModel;

    private RecyclerView routeDirectionView;
    private GeneralRecyclerAdapter<LocEdge> routeDirectionAdapter;

    private Button nextBtn;
    private Button backBtn;
    private Button settingsBtn;

    private List<LocEdge> directions;
    private HashMap<String, List<LocEdge>> route;

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

        directions = (List<LocEdge>) intent.getSerializableExtra("directions");
        route = (HashMap<String, List<LocEdge>>) intent.getSerializableExtra("route");

        // Create an adapter for the RecyclerView of route direction
        routeDirectionAdapter = new RouteDirectionAdapter();
        routeDirectionAdapter.setHasStableIds(true);
        routeDirectionAdapter.setItems(directions);

        routeDirectionView = findViewById(R.id.route_direction);
        routeDirectionView.setLayoutManager(new LinearLayoutManager(this));
        routeDirectionView.setAdapter(routeDirectionAdapter);

        nextBtn = findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(this::onNextBtnClicked);
        updateNextBtn(viewModel.getCurrTarget(), viewModel.getNextTarget());

        //
        backBtn = this.findViewById(R.id.back_to_plan);
        backBtn.setOnClickListener(this::onBackToPlanBtnClicked);

        //
        settingsBtn = this.findViewById(R.id.settings_button);
        settingsBtn.setOnClickListener(this::onSettingsClicked);
    }

    /**
     *
     * @param view
     */
    public void onNextBtnClicked(View view) {
        viewModel.arriveCurrentTarget();
        LocItem currTarget = viewModel.getCurrTarget();
        LocItem nextTarget = viewModel.getNextTarget();
        updateNextBtn(currTarget, nextTarget);
        routeDirectionAdapter.setItems(route.get(currTarget.id));
    }

    /**
     *
     * @param currTarget
     * @param nextTarget
     */
    private void updateNextBtn(LocItem currTarget, LocItem nextTarget) {
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
}