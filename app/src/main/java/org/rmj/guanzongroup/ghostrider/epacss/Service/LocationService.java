package org.rmj.guanzongroup.ghostrider.epacss.Service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class LocationService extends Service {

    private static final long INTERVAL = 5 * 60 * 1000; // 5 minutes
    private Handler handler;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LocationBinder binder = new LocationBinder();

    public class LocationBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle location updates here
               // String  = location.getLatitude() + ", " + location.getLongitude();
                //showToast("Location Updated: " + location.getLatitude() + ", " + location.getLongitude());
                Log.e("Location Updated: " , location.getLatitude() + ", " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Try to enable location services

            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startLocationUpdates();
        return START_STICKY;
    }

    public void startLocationUpdates() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check location service status
                if (isLocationServiceEnabled()) {
                    // Request location updates
                    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, locationListener);
                    if (ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                } else {
//                    showToast("Location service is disabled");
                    // Try to enable location services
                    enableLocationServices();
                }

                // Schedule the next update
                handler.postDelayed(this, INTERVAL);
            }
        }, 0);
    }

    private void enableLocationServices() {
        // You can customize this method to open the location settings for the user
        // For example, you can show a dialog or open the device settings page
        showToast("Location service is disabled. Please enable it in settings.");

        // Example: Open location settings page
        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }
    public boolean isLocationServiceEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop location updates when the service is destroyed
        locationManager.removeUpdates(locationListener);
    }
}