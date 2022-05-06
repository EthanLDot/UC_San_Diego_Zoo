package com.example.zooseeker_team54;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class RouteDirectionActivity extends AppCompatActivity {
    public RecyclerView showDirectionView;

    List<LocEdge> directions;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Intent intent = getIntent();
        directions = (List<LocEdge>) intent.getSerializableExtra("directions");

        System.out.println(directions);
    }

    public void onBackToPlanBtnClicked(View view){ finish(); }

}
