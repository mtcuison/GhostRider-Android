package org.rmj.g3appdriver.GRider.Etc;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationRetriever {
    private static final String TAG = LocationRetriever.class.getSimpleName();

    private final Context instance;
    private final FusedLocationProviderClient fusedLocationClient;

    public interface LocationRetrieveCallback{
        void OnRetrieve(String message, double latitude, double longitude);
    }

    public LocationRetriever(Context application) {
        this.instance = application;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(instance);
    }

    public void getLocation(LocationRetrieveCallback callback){
        if (ActivityCompat.checkSelfPermission(instance, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(instance, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if(location != null){
                String message = "Last Location \n " +
                        "Altitude: " + location.getLatitude() + "\n" +
                        "Longitude: " + location.getLongitude();
                callback.OnRetrieve(message, location.getLatitude(),
                        location.getLongitude());
            } else {
                callback.OnRetrieve("unable to retrieve location.", 0.0, 0.0);
            }
        });
    }
}
