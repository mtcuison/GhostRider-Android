/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.GRider.Etc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.Locale;

public class GeoLocator {
    private static double lattitude ,longitude;
    private static final int REQUEST_LOCATION = 1;
    private final Context context;
    private final Activity activity;
    private String address,city,state,country,postalCode,knownName;

    private String message;

    public GeoLocator(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        HasLocation();
    }

    public String getMessage(){
        return message;
    }

    public boolean HasLocation(){
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                message = "Location service is not enabled.";
                return false;
            } else {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                    message = "Location permissions are not enabled.";
                    return false;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);


                if (location != null) {
                    double latti = location.getLatitude();
                    double longi = location.getLongitude();
                    lattitude = latti;
                    longitude = longi;
                    return geoAddress();
                } else if (location1 != null) {
                    double latti = location1.getLatitude();
                    double longi = location1.getLongitude();
                    lattitude = latti;
                    longitude = longi;
                    return geoAddress();
                } else {
                    double latti = location2.getLatitude();
                    double longi = location2.getLongitude();
                    lattitude = latti;
                    longitude = longi;
                    return geoAddress();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public double getLatitude() {
        return lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean geoAddress(){
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());

            if(!Geocoder.isPresent()) {
                 message = "Location service is no longer present.";
                 return false;
            }
            addresses = geocoder.getFromLocation(lattitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = "Unable to retrieve current location. " + e.getMessage() + ". Restart the app and try again.";
            return false;
        }
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getKnownName() {
        return knownName;
    }

    public void showSettingsAlert(){
        MessageBox poMessage = new MessageBox(activity);
        poMessage.initDialog();
        poMessage.setTitle("Location");
        poMessage.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        poMessage.setPositiveButton("Settings", (view, dialog) -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);
        });
        poMessage.setNegativeButton("Cancel", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }
}
