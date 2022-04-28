package com.example.zooseeker_team54;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Place> places = Place.loadJSON(this, "sample_node_info.json");
        List<String> exhibits = new ArrayList<>();
        for(Place place: places)
        {
            if(place.kind.equals("exhibit"))
            {
                exhibits.add(place.id);
            }
        }
        selectedExhibitComponent sec = new selectedExhibitComponent(exhibits, this);
        sec.display();
    }
}