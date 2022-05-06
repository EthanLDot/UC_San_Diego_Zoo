package com.example.zooseeker_team54;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class RouteDirectionActivity extends AppCompatActivity {
    public RecyclerView showDirectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
    }

    public void onBackToPlanBtnClicked(View view){ finish(); }

}
