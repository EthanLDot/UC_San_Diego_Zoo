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

        double lat1 = coordOne.lat, lat2 = coordTwo.lat, lon1 = coordOne.lng, lon2 = coordTwo.lng;

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        return distance;
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