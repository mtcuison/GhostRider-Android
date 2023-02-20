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

package org.rmj.g3appdriver.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;

import org.rmj.g3appdriver.etc.AppConfigPreference;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ConnectionUtil {
    private static final String TAG = ConnectionUtil.class.getSimpleName();

    private final Context context;
    private String message;

    private static final String LOCAL = "http://192.168.10.15/";
    private static final String PRIMARY_LIVE = "https://restgk.guanzongroup.com.ph/";
    private static final String SECONDARY_LIVE = "https://restgk1.guanzongroup.com.ph/";

    public ConnectionUtil(Context context){
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public boolean isDeviceConnected(){
        try {
            if (!deviceConnected()) {
                message = "Please enable wifi or data to connect.";
                return false;
            } else {
                String lsAddress;
                AppConfigPreference loConfig = AppConfigPreference.getInstance(context);
                boolean isTestCase = loConfig.getTestStatus();
                if (isTestCase) {
                    lsAddress = LOCAL;
                    if (!isReachable(lsAddress)) {
                        message = "Unable to reach local server.";
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    boolean isBackUp = loConfig.isBackUpServer();
                    if (isBackUp) {
                        lsAddress = SECONDARY_LIVE;
                        if (!isReachable(lsAddress)) {
                            Log.e(TAG, "Unable to connect to secondary server.");
                            lsAddress = PRIMARY_LIVE;
                            if (isReachable(lsAddress)) {
                                Log.d(TAG, "Primary server is reachable.");
                                loConfig.setIfBackUpServer(false);
                                return true;
                            } else {
                                message = "Unable to connect to our servers.";
                                return false;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        lsAddress = PRIMARY_LIVE;
                        if (!isReachable(lsAddress)) {
                            Log.e(TAG, "Unable to connect to primary server.");
                            lsAddress = SECONDARY_LIVE;
                            if (isReachable(lsAddress)) {
                                Log.d(TAG, "Secondary server is reachable.");
                                loConfig.setIfBackUpServer(true);
                                return true;
                            } else {
                                message = "Unable to connect to our servers.";
                                return false;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    private boolean deviceConnected(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private boolean isReachable(String lsAddress)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        trustAllCertificates();

        try
        {
            HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(
                    lsAddress).
                    openConnection();
            httpUrlConnection.setRequestProperty("Connection", "close");
            httpUrlConnection.setRequestMethod("HEAD");
            httpUrlConnection.setConnectTimeout(7000);
            int responseCode = httpUrlConnection.getResponseCode();

            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isWifiConnected(){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }
}
