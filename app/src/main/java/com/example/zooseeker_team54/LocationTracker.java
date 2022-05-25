package com.example.zooseeker_team54;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LocationTracker {
    private static final String TAG = "FOOBAR";

    private boolean useLocationService;
    private LocationModel model;

    /**
     *
     * @param activity
     * @param useLocationService
     */
    public LocationTracker(ComponentActivity activity, boolean useLocationService) {
        this.useLocationService = useLocationService;
        this.model = new ViewModelProvider(activity).get(LocationModel.class);

        // If GPS is enabled, then update the model from the Location service.
        if (this.useLocationService) {
            var permissionChecker = new LocationPermissionChecker(activity);
            permissionChecker.ensurePermissions();

            var locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            var provider = LocationManager.GPS_PROVIDER;
            model.addLocationProviderSource(locationManager, provider);
        }

        model.getLastKnownCoords().observe(activity, (coord) -> {
            Log.i(TAG, String.format("Observing location model update to %s", coord));
        });
    }

    public void startMockingRoute(List<Coord> route) {
        if (!this.useLocationService) {
            model.mockRoute(route, 500, TimeUnit.MILLISECONDS);
        }
    }

    /**
     *
     * @return
     */
    public LiveData<Coord> getUserCoordLive() {
        return model.getLastKnownCoords();
    }

    @VisibleForTesting
    public void mockLocation(Coord coord) {
        model.mockLocation(coord);
    }

    @VisibleForTesting
    public Future<?> mockRoute(List<Coord> route, long delay, TimeUnit unit) {
        return model.mockRoute(route, delay, unit);
    }

}
