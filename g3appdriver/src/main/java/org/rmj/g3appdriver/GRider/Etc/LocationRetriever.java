package org.rmj.g3appdriver.GRider.Etc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationRetriever {
    private static final String TAG = LocationRetriever.class.getSimpleName();

    private final Context instance;
    private final Activity activity;
    private final FusedLocationProviderClient fusedLocationClient;

    private String message;
    private String nLatitude;
    private String nLongitde;
    boolean isSuccess;
    int lnResult = 0;

    public interface LocationRetrieveCallback{
        void OnRetrieve(String message, double latitude, double longitude);
        void OnFailed(String message);
    }

    public LocationRetriever(Context application, Activity activity) {
        this.instance = application;
        this.activity = activity;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(instance);
    }

    public interface iLocationRetriever{
        void GetLocation(Context instance, OnRetrieveLocationListener listener);
    }

    public interface OnRetrieveLocationListener{
        void OnRetrieve(String latitude, String longitude);
        void OnFailed(String message, String latitude, String longitude);
    }

    public String getMessage() {
        return message;
    }

    public String getLatitude() {
        return nLatitude;
    }

    public String getLongitude() {
        return nLongitde;
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
                location.HasLocation();
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

    @SuppressLint("MissingPermission")
    public boolean HasLocation(){
        try {
            String lsCompx = android.os.Build.MANUFACTURER;
            lsCompx = "huawei";
            iLocationRetriever location = null;
            if (!lsCompx.toLowerCase().equalsIgnoreCase("huawei")) {
                location = new GmsLocationRetriever();
            } else {
                location = new HmsLocationRetriever();
            }

            location.GetLocation(instance, new OnRetrieveLocationListener() {
                @Override
                public void OnRetrieve(String latitude, String longitude) {
                    isSuccess = true;
                    nLatitude = latitude;
                    nLongitde = longitude;
                    lnResult = 1;
                }

                @Override
                public void OnFailed(String fsMsg, String latitude, String longitude) {
                    isSuccess = false;
                    nLatitude = latitude;
                    nLongitde = longitude;
                    message = fsMsg;
                    lnResult = 1;
                }
            });
            while(lnResult < 1) {
                Log.d(TAG, "waiting location result...");
                Thread.sleep(1000);
            }

            return isSuccess;
        } catch (Exception e){
            e.printStackTrace();
            message = "Failed retrieving current location. " + e.getMessage();
            return false;
        }
    }
}
