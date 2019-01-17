package com.example.naray.knowyourgovernment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by naray on 4/3/2017.
 */

public class Locator {

    private static final String TAG = "Locator";
    private MainActivity main;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public Locator(MainActivity activity) {
        main = activity;

        if (checkPermission()) {
            setUpLocationManager();
            determineLocation();
        }

    }

    public void setUpLocationManager() {

        //if (locationManager != null)
           // return;

        if (!checkPermission())
            return;

        locationManager = (LocationManager) main.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //Toast.makeText(main, "Update from " + location.getProvider(), Toast.LENGTH_SHORT).show();
               // main.doLocationWork(location.getLatitude(), location.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        //locationManager.requestLocationUpdates(
               //LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        //locationManager.requestLocationUpdates(
               // LocationManager.PASSIVE_PROVIDER, 1000, 0, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

    }

    public void shutDown() {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(main, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(main,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
            return false;
        }
        return true;
    }

    public void determineLocation() {
        Location loc;
        if (!checkPermission()) {
            return;
        } else {
            /*if (locationManager == null) {
                setUpLocationManager();
            }*/

                loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (loc != null) {
                    //owner.setData(loc.getLatitude(), loc.getLongitude(), LocationManager.NETWORK_PROVIDER);
                    Toast.makeText(main, "Using " + LocationManager.NETWORK_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                    main.doLocationWork(loc.getLatitude(),loc.getLongitude());

                }
                else{
                    loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    if(loc!=null){
                        Toast.makeText(main, "Using " + LocationManager.PASSIVE_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                        main.doLocationWork(loc.getLatitude(),loc.getLongitude());

                    }
                    else{
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(loc!=null){
                            Toast.makeText(main, "Using " + LocationManager.GPS_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                            main.doLocationWork(loc.getLatitude(),loc.getLongitude());

                        }

                    }
                }


            /*if (locationManager != null) {
                Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if (loc != null) {
                    owner.setData(loc.getLatitude(), loc.getLongitude(), LocationManager.PASSIVE_PROVIDER);
                    Toast.makeText(owner, "Using " + LocationManager.PASSIVE_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (locationManager != null) {
                Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (loc != null) {
                    owner.setData(loc.getLatitude(), loc.getLongitude(), LocationManager.GPS_PROVIDER);
                    Toast.makeText(owner, "Using " + LocationManager.GPS_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                    return;
                }
            }*/
        }
    }
}
