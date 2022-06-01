package com.example.zooseeker_team54;

import android.location.Location;
import android.location.LocationManager;
import android.os.SystemClock;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class representing the functionality of the LatLng object. Used to represent coordinates
 */
public class LatLngs {
    // LatLngs of UCSD and ZOO
    public static final LatLng UCSD_LATLNG = new LatLng(32.8801, -117.2340);
    public static final LatLng ZOO_LATLNG = new LatLng(32.7353, -117.1490);

    /**
     * Method to create a LatLng based on a given location
     *
     * @param location location to get coordinates of
     * @return coordinates of the given location as a LatLng
     */
    public static LatLng fromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    /**
     * Method to get the Location from a given LatLng
     *
     * @param latLng LatLng to be converted to a Location
     * @return Location of the given LatLng
     */
    public static Location toGPSLocation(LatLng latLng) {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(latLng.latitude);
        location.setLongitude(latLng.longitude);
        location.setAccuracy(100f);
        location.setTime(System.currentTimeMillis());
        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        return location;
    }

    /**
     * Method to calculate the midpoint between two LatLngs
     *
     * @param l1    Start LatLng
     * @param l2    End Latlng
     * @return      LatLng of the midpoint between the two given LatLngs
     */
    public static LatLng midpoint(LatLng l1, LatLng l2) {
        return new LatLng(
                (l1.latitude + l2.latitude) / 2,
                (l1.longitude + l2.longitude) / 2
        );
    }

    /**
     * Method to interpolate points between two LatLngs
     *
     * @param l1    Start LatLng
     * @param l2    End LatLng
     * @param n     Number of points to interpolate in between
     * @return      List of LatLngs that are evenly spaced in between l1 and l2
     */
    public static List<LatLng> pointsBetween(LatLng l1, LatLng l2, int n) {
        var dLat = Math.abs(l1.latitude - l2.latitude);
        var dLng = Math.abs(l1.longitude - l2.longitude);

        return IntStream.range(0, n)
                // Map from i={0, 1, ... n} to t={0.0, 0.1, ..., 1.0} with n divisions.
                .mapToDouble(i -> (double) i / (double) n)
                // Linear interpolate between l1 and l2 using t.
                .mapToObj(t -> {
                    // p(t) = p0 + (p1 - p0) * t
                    return new LatLng(
                            l1.latitude + (l2.latitude - l1.latitude) * t,
                            l1.longitude + (l2.longitude - l1.longitude) * t
                    );
                })
                .collect(Collectors.toList());
    }
}