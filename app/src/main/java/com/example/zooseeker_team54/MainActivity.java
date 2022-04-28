package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Place> todos = Place.loadJSON(this, "sample_node_info.json");
        System.out.println(todos.toString());
        System.out.println("oncreate complete!");
    }
}