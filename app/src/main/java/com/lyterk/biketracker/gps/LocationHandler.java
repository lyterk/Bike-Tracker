package com.lyterk.biketracker.gps;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationHandler
        implements LocationListener,
        OnConnectionFailedListener,
        ConnectionCallbacks {

    private static final String TAG = "locationhandling";
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private static final String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    private static final String LOCATION_KEY = "location-key";
    private static final String LAST_UPDATE_TIME_STRING_KEY = "last-update-time-string-key";

    public GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLocation;
    private LocationListener ll;

    private boolean mRequestingLocationUpdates = false;

    public boolean isRequestingLocationUpdates() {
        return mRequestingLocationUpdates;
    }

    protected synchronized void buildGoogleApiClient(Context ctx) {
        mGoogleApiClient = new GoogleApiClient.Builder(ctx)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        ll.onLocationChanged(mLocation);
    }

    public void startUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    public void stopUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mLocation == null) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Log.v(TAG, "onConnected problems");
//            ll.onLocationChanged(mLocation);
        }
        if (mRequestingLocationUpdates) {
            startUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d(TAG, "Connection failed");
    }    
}
