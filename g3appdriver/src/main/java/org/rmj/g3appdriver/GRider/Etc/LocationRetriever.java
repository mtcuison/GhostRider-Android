package org.rmj.g3appdriver.GRider.Etc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationRetriever {
    private static final String TAG = LocationRetriever.class.getSimpleName();

    private final Context instance;
    private final Activity activity;
    private final FusedLocationProviderClient fusedLocationClient;

    public interface LocationRetrieveCallback{
        void OnRetrieve(String message, double latitude, double longitude);
        void OnFailed(String message);
    }

    public LocationRetriever(Context application, Activity activity) {
        this.instance = application;
        this.activity = activity;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(instance);
    }

    @SuppressLint("MissingPermission")
    public void getLocation(LocationRetrieveCallback callback){
        try {
            String lsCompx = android.os.Build.MANUFACTURER;
            if (!lsCompx.toLowerCase().equalsIgnoreCase("huawei")) {
                fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        String message = "Last Location \n " +
                                "Altitude: " + location.getLatitude() + "\n" +
                                "Longitude: " + location.getLongitude();
                        callback.OnRetrieve(message, location.getLatitude(),
                                location.getLongitude());
                    } else {
                        callback.OnRetrieve("unable to retrieve location.", 0.0, 0.0);
                    }
                });
            } else {
                GeoLocator location = new GeoLocator(instance, activity);
                location.GetLocation();
                double lnLatitude = location.getLatitude();
                double lnLongitude = location.getLongitude();
                String message = "Last Location \n " +
                        "Altitude: " + location.getLatitude() + "\n" +
                        "Longitude: " + location.getLongitude();
                callback.OnRetrieve(message, lnLatitude, lnLongitude);
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("Failed retrieving current location. " + e.getMessage());
        }
    }
}
