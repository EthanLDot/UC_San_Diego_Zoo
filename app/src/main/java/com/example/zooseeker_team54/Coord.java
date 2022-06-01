package com.example.zooseeker_team54;

import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.maps.model.LatLng;

/**
 * Class representing the functionality of the Coord object. Used to represent coordinates
 */
public class Coord {

    /**
     * Constructor method for Coord
     *
     * @param lat Latitude as a double
     * @param lng Longitude as a double
     */
    public Coord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    // member variables latitude and longitude
    public final double lat;
    public final double lng;

    /**
     * Method to create new coordinates from passed in doubles
     *
     * @param lat Latitude
     * @param lng Longitude
     * @return new Coord object from given coordinates
     */
    public static Coord of(double lat, double lng) {
        return new Coord(lat, lng);
    }

    /**
     * Method to create new coordinates from a LatLng object
     *
     * @param latLng LatLng to be converted to a Coord
     * @return new Coord object from given coordinates
     */
    public static Coord fromLatLng(LatLng latLng) {
        return Coord.of(latLng.latitude, latLng.longitude);
    }

    /**
     * Convert a Coord to a LatLng object
     *
     * @return new LatLng object
     */
    public LatLng toLatLng() {
        return new LatLng(lat, lng);
    }

    /**
     * Converts a location to a Coord
     *
     * @param location location to be made into a Coord
     * @return new Coord object from given location
     */
    public static Coord fromLocation(Location location) {
        return Coord.of(location.getLatitude(), location.getLongitude());
    }

    /**
     * Method to calculate the distance between two given coordinates
     *
     * source: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
     *
     * @param coordOne Coordinates of first location
     * @param coordTwo Coordinates of second location
     * @return distance between the two coordinates as a Double
     */
    public static Double distanceBetweenTwoCoords(Coord coordOne, Coord coordTwo) {
        // The Latitude would be the y value
        // The Longitude would be the x value.
        // Basic Formula = sqrt((x2 - x1)^2 + (y2 - y1)^2)

        double latDiff = Math.pow(coordOne.lat - coordTwo.lat, 2);
        double longDiff = Math.pow(coordOne.lng - coordTwo.lng, 2);

        return Math.pow(latDiff + longDiff, 0.5);
    }

    /**
     * Method override to compare the called Coord and a passed in Coord
     *
     * @param o Given Coord to be compared
     * @return boolean if the passed in Coord and called Coord are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return Double.compare(coord.lat, lat) == 0 && Double.compare(coord.lng, lng) == 0;
    }

    /**
     * Getter method for the hashcode of a coordinate
     *
     * @return hashcode as an int
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(lat, lng);
    }

    /**
     * Method to convert the Coord to a String
     *
     * @return String representation of the called upon Coord
     */
    @NonNull
    @Override
    public String toString() {
        return String.format("Coord{lat=%s, lng=%s}", lat, lng);
    }
}