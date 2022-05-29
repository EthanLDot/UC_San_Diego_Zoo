package com.example.zooseeker_team54;

import java.io.Serializable;

/**
 * Class to represent the Edges between Locations and to be able to store and modify them in
 * containers of our choosing.
 */
public class LocEdge implements Serializable {

    // member variables of LocEdge
    public double weight;
    public String id, source, source_id, target, target_id, street;

    /**
     * Constructor method for LocEdge given its properties
     * @param id        ID for identification of the edge
     * @param weight    weight of the edge as a double
     * @param street    name of the street as a string
     * @param source    name of the source as a string
     * @param target    name of the target as a string
     */
    public LocEdge(String id, double weight, String street, String source, String source_id, String target, String target_id) {
        this.id = id;
        this.weight = weight;
        this.street = street;
        this.source = source;
        this.source_id = source_id;
        this.target = target;
        this.target_id = target_id;
    }

    /**
     * Gets the representation of the edge as a string
     * @return String representation of the LocEdge with its member variables
     */
    @Override
    public String toString() {
        return String.format("Proceed on '%s' %.0f meters towards '%s' from '%s'.\n",
                street, weight, target, source);
    }

    /**
     *
     * @param edge
     * @return
     */
    public static LocEdge getReversedLocEdge(LocEdge edge) {
        return new LocEdge(edge.id, edge.weight, edge.street, edge.target, edge.target_id, edge.source, edge.source_id);
    }
}
