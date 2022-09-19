package org.rmj.g3appdriver.GRider.Etc;


import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class HmsLocationRetriever implements LocationRetriever.iLocationRetriever {

    @SuppressLint("MissingPermission")
    @Override
    public void GetLocation(Context instance, LocationRetriever.OnRetrieveLocationListener listener){
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(instance);
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                String nLatitude = String.valueOf(location.getLatitude());
                String nLongitde = String.valueOf(location.getLongitude());
                listener.OnRetrieve(nLatitude, nLongitde);
            } else {
                listener.OnFailed("Failed to retrieve location.", "0.0", "0.0");
            }
        });
    }
}
