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

package org.rmj.g3appdriver.dev;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class GLocationManager {
    private static final String TAG = GLocationManager.class.getSimpleName();
    private final Context mContext;

    public static int GLocationResCode = 241;

    private String lattitude = "0.0";
    private String longitude = "0.0";

    public interface OnLocationTrack{
        void OnTrack(String latitude, String longitude);
    }

    public GLocationManager(Context context) {
        this.mContext = context;
    }

    public boolean isLocationEnabled(){
        @SuppressLint("ServiceCast") LocationManager lm = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch(Exception e) {
            e.printStackTrace();
        }

        return gps_enabled && network_enabled;
    }

    @SuppressLint("MissingPermission")
    public void getLocation(OnLocationTrack callback){
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        if (location != null) {
            double latti = location.getLatitude();
            double longi = location.getLongitude();
            lattitude = String.valueOf(latti);
            longitude = String.valueOf(longi);

            callback.OnTrack(lattitude, longitude);
        } else if (location1 != null) {
            double latti = location1.getLatitude();
            double longi = location1.getLongitude();
            lattitude = String.valueOf(latti);
            longitude = String.valueOf(longi);

            callback.OnTrack(lattitude, longitude);
        } else if (location2 != null) {
            double latti = location2.getLatitude();
            double longi = location2.getLongitude();
            lattitude = String.valueOf(latti);
            longitude = String.valueOf(longi);

            callback.OnTrack(lattitude, longitude);
        }else{
            callback.OnTrack(lattitude, longitude);
            Log.e(TAG, "unable to get location coordinates");
        }
    }
}
