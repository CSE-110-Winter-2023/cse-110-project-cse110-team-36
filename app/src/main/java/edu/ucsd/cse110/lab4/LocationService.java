package edu.ucsd.cse110.lab4;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class LocationService implements LocationListener {

    private static final int PERMISSIONS_REQUEST_LOCATION = 100;
    public static LocationService instance;
    Activity activity;

    MutableLiveData<Pair<Double, Double>> locationValue;
    Location realLocation;

    final LocationManager locationManager;


    public LocationService singleton(Activity activity) {
        if (instance == null) {
            instance = new LocationService(activity);
        }
        return instance;
    }


    /**
     * Constructor for LocationService
     *
     * @param activity Context needed to initiate LocationManager
     */
    public LocationService(Activity activity) {
        this.locationValue = new MutableLiveData<>();
        this.activity = activity;
        this.locationManager =
                (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        this.registerLocationListener();
    }

    public float getBearing(float latitude, float longitude) {
        Location toLocation = new Location(LocationManager.GPS_PROVIDER);
        toLocation.setLatitude(latitude);
        toLocation.setLongitude(longitude);
        return realLocation.bearingTo(toLocation);
    }

    public float getDistance(float latitude, float longitude) {
        Location toLocation = new Location(LocationManager.GPS_PROVIDER);
        toLocation.setLatitude(latitude);
        toLocation.setLongitude(longitude);
        return realLocation.distanceTo(toLocation);
    }

    private void registerLocationListener() {
        if ((ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            throw new IllegalStateException("App needs location permission to get latest location.");
        }

        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, this);

        // get the last location
        Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc_Current != null) {
            double cur_lat = loc_Current.getLatitude();
            double cur_lon = loc_Current.getLongitude();
            this.locationValue.postValue(new Pair<Double, Double>(cur_lat, cur_lon));
            this.realLocation = loc_Current;
        }
    }

    private void unregisterLocationListener() {locationManager.removeUpdates(this);}

    public LiveData<Pair<Double, Double>> getLocation() {return this.locationValue;}

    public void setMockOrientationSource(MutableLiveData<Pair<Double, Double>> mockDataSource) {
        unregisterLocationListener();
        this.locationValue = mockDataSource;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, register location listener
                registerLocationListener();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.locationValue.postValue(new Pair<Double, Double>(location.getLatitude(),
                location.getLongitude()));
        this.realLocation = location;
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}