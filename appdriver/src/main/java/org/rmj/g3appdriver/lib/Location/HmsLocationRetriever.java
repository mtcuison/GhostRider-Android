package org.rmj.g3appdriver.lib.Location;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;


import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;

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

    @Override
    public void StartLocationTracking(Context instance, LocationRetriever.BackgroundLocationTrackingListener listener){
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(instance);
        fusedLocationClient.requestLocationUpdates(GetHuaweiLocationRequest(), new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                locationResult.getLastLocation();
                double latitude = locationResult.getLastLocation().getLatitude();
                double longitude = locationResult.getLastLocation().getLongitude();
                listener.OnRetrieve(latitude, longitude);
            }
        }, Looper.myLooper());
    }

    private LocationRequest GetHuaweiLocationRequest(){
        LocationRequest loRequest = LocationRequest.create();

        loRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        loRequest.setInterval(300000); //5mins
        loRequest.setFastestInterval(600000); //10mins
        return loRequest;
    }
}
