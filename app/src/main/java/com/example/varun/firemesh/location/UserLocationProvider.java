package com.example.varun.firemesh.location;

import android.app.Service;
import android.content.Context;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by Max Yang on 4/1/2017.
 */

public class UserLocationProvider extends Service implements LocationListener {
    private Context _context;
    private LocationManager _locMan;

    private GpsPoint _userLoc;
    public UserLocationProvider(Context context) {
        _context = context;
        getLocInit();
    }

    private void getLocInit() {
        try {
            _locMan = (LocationManager) _context.getSystemService(LOCATION_SERVICE);
            boolean gpsEnabled = _locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean cellEnabled = _locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (gpsEnabled || cellEnabled) {
                _locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60, 1, this);
                if (_locMan != null) {
                    Location currLoc = _locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (currLoc != null) {
                        _userLoc = new GpsPoint(currLoc.getLatitude(), currLoc.getLongitude(), currLoc.getAltitude());
                    }
                }
            }
        } catch (SecurityException se) {
            //user hasn't enabled gps
        }
    }

    public GpsPoint getCurrLoc() {
        return _userLoc;
    }

    public boolean gpsLocationExists() {
        return _locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location location) {
        _userLoc = new GpsPoint(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
