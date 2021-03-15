package org.rmj.g3appdriver.dev;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;

public class GLocationManager {

    public GLocationManager() {
    }

    public static boolean isLocationEnabled(Context context){
        @SuppressLint("ServiceCast") LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
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

    public static int GLocationResCode = 241;
}
