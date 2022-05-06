package com.example.zooseeker_team54;

import java.io.Serializable;

public class LocEdge implements Serializable {
    public double weight;
    public String id, source, target, street;

    public LocEdge(String id, double weight, String street, String source, String target) {
        this.id = id;
        this.weight = weight;
        this.street = street;
        this.source = source;
        this.target = target;
    }

    @Override
    public String toString() {
        return String.format("Walk %.0f meters along '%s' from '%s' to '%s'.\n",
                weight, street, source, target);
    }

}
