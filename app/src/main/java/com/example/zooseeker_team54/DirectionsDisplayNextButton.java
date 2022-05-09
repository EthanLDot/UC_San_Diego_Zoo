package com.example.zooseeker_team54;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class DirectionsDisplayNextButton {
    public Button nextButton;
    private ViewModel viewModel;
    private DirectionsDisplayRecyclerView rview;
    LocItem newTarget;

    Activity context;

    public DirectionsDisplayNextButton (Activity context, DirectionsDisplayRecyclerView rview) {
        this.context = context;
        this.rview = rview;
    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void initializeButton() {
        nextButton = context.findViewById(R.id.next_btn);
        newTarget = viewModel.getNextTarget();
        configureButton(newTarget);
        nextButton.setOnClickListener(this::onNextBtnClicked);
    }

    public void onNextBtnClicked(View view) {
        List<LocEdge> newDirections = rview.getRoute().get(newTarget.id);
        rview.getAdapter().setLocEdges(newDirections);
        nextButton = context.findViewById(R.id.next_btn);
        newTarget = getNewTarget();
        configureButton(newTarget);
    }

    void configureButton(LocItem newTarget) {
        if (newTarget == null || !newTarget.kind.equals("exhibit") || !newTarget.planned) {
            String buttonText = "NEXT\n------\n" + "No Exhibits Left!";
            nextButton.setText(buttonText);
            nextButton.setClickable(false);
            nextButton.setEnabled(false);
        }
        else {
            nextButton.setEnabled(true);
            String newText = "NEXT\n------\n" + newTarget.name + ", " + (int)newTarget.currDist;
            nextButton.setText(newText);
        }
    }

    LocItem getNewTarget()
    {
        // Step 1: recalculate currDist for all unvisited LocItems
        LocItem currentTarget = viewModel.getNextUnvisitedExhibit();
        if(currentTarget == null)
        {
            nextButton.setText("NEXT");
            nextButton.setClickable(false);
            nextButton.setEnabled(false);
            return null;
        }
        Double currentTargetDist = currentTarget.currDist;

        List<LocItem> unvisited = viewModel.getAllPlannedUnvisited();
        for (LocItem locItem : unvisited) {
            viewModel.updateLocCurrentDist(locItem, locItem.currDist - currentTargetDist);
        }
        viewModel.addVisitedLoc(currentTarget);

        //Step 2: choose next exhibit to travel to
        newTarget = viewModel.getNextUnvisitedExhibit();
        return newTarget;
    }
}
