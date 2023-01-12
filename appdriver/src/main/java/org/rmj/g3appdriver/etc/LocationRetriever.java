package org.rmj.g3appdriver.etc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.rmj.g3appdriver.dev.Database.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.dev.Database.Repositories.RLocationSysLog;
import org.rmj.g3appdriver.dev.Telephony;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LocationRetriever {
    private static final String TAG = LocationRetriever.class.getSimpleName();

    private final Application instance;
    private Activity activity;

    private String message;
    private String nLatitude;
    private String nLongitde;
    boolean isSuccess;
    int lnResult = 0;

    public interface LocationRetrieveCallback{
        void OnRetrieve(String message, double latitude, double longitude);
        void OnFailed(String message);
    }

    public LocationRetriever(Application application, Activity activity) {
        this.instance = application;
        this.activity = activity;
    }

    public LocationRetriever(Application application) {
        this.instance = application;
    }

    public interface iLocationRetriever{
        void GetLocation(Context instance, OnRetrieveLocationListener listener);
        void StartLocationTracking(Context instance, BackgroundLocationTrackingListener listener);
    }

    public interface OnRetrieveLocationListener{
        void OnRetrieve(String latitude, String longitude);
        void OnFailed(String message, String latitude, String longitude);
    }

    public interface BackgroundLocationTrackingListener{
        void OnRetrieve(double args, double args1);
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
    public boolean HasLocation(){
        try {
            String lsCompx = android.os.Build.MANUFACTURER;
            iLocationRetriever location;
            if (!lsCompx.equalsIgnoreCase("huawei")) {
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

    public void GetLocationOnBackgroud(){
        try{
            String lsCompx = android.os.Build.MANUFACTURER;

            iLocationRetriever location;

            if (!lsCompx.equalsIgnoreCase("huawei")) {
                Log.d(TAG, "Initialize google services for location tracking.");
                location = new GmsLocationRetriever();
            } else {
                Log.d(TAG, "Initialize huawei services for location tracking.");
                location = new HmsLocationRetriever();
            }

            RLocationSysLog loSys = new RLocationSysLog(instance);
            Telephony loDevID = new Telephony(instance);
            SessionManager loSession = new SessionManager(instance);

            location.StartLocationTracking(instance, (args, args1) -> {
                EGLocatorSysLog loLog = new EGLocatorSysLog();
                loLog.setDeviceID(loDevID.getDeviceID());
                loLog.setLatitude(String.valueOf(args));
                loLog.setLongitud(String.valueOf(args1));
                loLog.setTimeStmp(new AppConstants().DATE_MODIFIED());
                loLog.setTransact(new AppConstants().DATE_MODIFIED());
                loLog.setUserIDxx(loSession.getUserID());
                if(isLocationEnabled(instance)) {
                    loLog.setGpsEnbld("1");
                    loLog.setRemarksx("Location Retrieve.");
                } else {
                    loLog.setGpsEnbld("0");
                    loLog.setRemarksx("Location service is not enabled.");
                }
                loSys.saveCurrentLocation(loLog);
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static boolean isLocationEnabled(Context context) {
        int locationMode;
        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }
}
