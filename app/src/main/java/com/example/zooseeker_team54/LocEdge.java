package com.example.zooseeker_team54;

public class LocEdge {
    private double weight;
    private String source, target, description;

    public LocEdge(double weight, String source, String target, String description) {
        this.weight = weight;
        this.source = source;
        this.target = target;
        this.description = description;
    }
}
