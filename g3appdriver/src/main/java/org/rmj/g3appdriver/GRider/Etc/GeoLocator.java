package org.rmj.g3appdriver.GRider.Etc;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoLocator {


    private static double lattitude ,longitude;
    private static final int REQUEST_LOCATION = 1;
    private final Context context;
    private final Activity activity;
    private String address,city,state,country,postalCode,knownName;



    public GeoLocator(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        GetLocation();

    }

    public void GetLocation(){


        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);


        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


            showSettingsAlert();
           Toast.makeText(context,"Permission Denied", Toast.LENGTH_SHORT).show();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){


            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            } else {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

                if (location != null) {
                    double latti = location.getLatitude();
                    double longi = location.getLongitude();
                    lattitude = latti;
                    longitude = longi;



                } else  if (location1 != null) {
                    double latti = location1.getLatitude();
                    double longi = location1.getLongitude();
                    lattitude = latti;
                    longitude = longi;




                } else  if (location2 != null) {
                    double latti = location2.getLatitude();
                    double longi = location2.getLongitude();
                    lattitude = latti;
                    longitude = longi;


                }else{

                    Toast.makeText(context,"Unble to Trace your location", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }



    public   double getLattitude() {
        return lattitude;
    }

    public  double getLongitude() {
        return longitude;
    }

    public void geoAddress(){


        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lattitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
              city = addresses.get(0).getLocality();
              state = addresses.get(0).getAdminArea();
              country = addresses.get(0).getCountryName();
              postalCode = addresses.get(0).getPostalCode();
              knownName = addresses.get(0).getFeatureName();


        } catch (IOException e) {
            e.printStackTrace();
            GToast.CreateMessage(context, e.getMessage(), GToast.INFORMATION).show();
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
        MessageBox poMessage = new MessageBox(context);
        poMessage.setTitle("Location");
        poMessage.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        poMessage.setPositiveButton("Settings", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        poMessage.setNegativeButton("Cancel", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                dialog.dismiss();
            }
        });
        poMessage.show();
    }
}
