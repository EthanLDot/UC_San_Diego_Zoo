package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RoutePlanActivity extends AppCompatActivity {

    public RecyclerView planView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);

        planView = this.findViewById(R.id.planSelected);
        planView.setLayoutManager(new LinearLayoutManager(this));

    }
    public void onBackButtonClicked (View view) {
        finish();
    }
}

