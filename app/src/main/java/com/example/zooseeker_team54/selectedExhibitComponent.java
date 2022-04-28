package com.example.zooseeker_team54;

import java.util.HashSet;

public class selectedExhibitComponent {

    HashSet<String> plan;

    //assuming that a hashset of ids will be passed
    public selectedExhibitComponent(HashSet<String> plan)
    {
        this.plan = plan;
    }

    public void display()
    {
        for(String s: plan)
        {
            System.out.println(s);
        }
    }
}
