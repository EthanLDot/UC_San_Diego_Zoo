package com.example.zooseeker_team54;

import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.maps.model.LatLng;

public class Coord {
    public Coord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public final double lat;
    public final double lng;

    public static Coord of(double lat, double lng) {
        return new Coord(lat, lng);
    }

    public static Coord fromLatLng(LatLng latLng) {
        return Coord.of(latLng.latitude, latLng.longitude);
    }

    public LatLng toLatLng() {
        return new LatLng(lat, lng);
    }

    public static Coord fromLocation(Location location) {
        return Coord.of(location.getLatitude(), location.getLongitude());
    }

    /**
     * source: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
     * @param coordOne
     * @param coordTwo
     * @return
     */
    public static Double distanceBetweenTwoCoords(Coord coordOne, Coord coordTwo) {
        // The Latitude would be the y value
        // The Longitude would be the x value.
        // Basic Formula = sqrt((x2 - x1)^2 + (y2 - y1)^2)

        double latDiff = Math.pow(coordOne.lat - coordTwo.lat, 2);
        double longDiff = Math.pow(coordOne.lng - coordTwo.lng, 2);

        return Math.pow(latDiff + longDiff, 0.5);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return Double.compare(coord.lat, lat) == 0 && Double.compare(coord.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lat, lng);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Coord{lat=%s, lng=%s}", lat, lng);
    }
}