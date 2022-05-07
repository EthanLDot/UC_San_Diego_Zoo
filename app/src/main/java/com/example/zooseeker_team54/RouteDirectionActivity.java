package com.example.zooseeker_team54;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

/**
 * Displays the directions to get to a specific exhibit in the route o planned exhibits
 */
public class RouteDirectionActivity extends AppCompatActivity {
    DirectionsDisplayRecyclerView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Intent intent = getIntent();
        display = new DirectionsDisplayRecyclerView((List<LocEdge>) intent.getSerializableExtra("directions"));
        display.setContext(this);
        display.initializeRecyclerView();
    }

    public void onBackToPlanBtnClicked(View view){ finish(); }
    public DirectionsDisplayRecyclerView getDisplayView() {
        return this.display;
    }
}
