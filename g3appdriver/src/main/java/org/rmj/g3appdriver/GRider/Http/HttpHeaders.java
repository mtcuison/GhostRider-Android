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

package org.rmj.g3appdriver.GRider.Http;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private final Telephony poTlphony;
    private final AppConfigPreference poConfigx;
    private final SessionManager poSession;

    private HttpHeaders(Application application){
        this.poTlphony = new Telephony(application);
        this.poConfigx = AppConfigPreference.getInstance(application);
        this.poSession = new SessionManager(application);
    }

    private static HttpHeaders mHeaders;

    public static HttpHeaders getInstance(Application application){
        if(mHeaders == null){
            mHeaders = new HttpHeaders(application);
        }
        return mHeaders;
    }

    private Map<String, String> initHttpHeaders() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Calendar calendar = Calendar.getInstance();


        //TODO: Change Values on user testing and production
        String lsUserIDx = poSession.getUserID();
        String lsClientx = poSession.getClientId();
        String lsLogNoxx = poSession.getLogNumber();
        String lsTokenxx = poConfigx.getAppToken();
        String lsProduct = poConfigx.ProducID();
        String lsDevcIDx = poTlphony.getDeviceID();
        String lsDateTme = SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss");
        String lsDevcMdl = Build.MODEL;
        String lsMobileN = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            lsMobileN = poTlphony.getMobilNumbers();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", lsProduct);
        headers.put("g-api-client", lsClientx);
        headers.put("g-api-imei", lsDevcIDx);
        headers.put("g-api-model", lsDevcMdl);
        headers.put("g-api-mobile", lsMobileN);
        headers.put("g-api-token", lsTokenxx);
        headers.put("g-api-user", lsUserIDx);
        headers.put("g-api-key", lsDateTme);
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();
        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-log", lsLogNoxx);
        return headers;
    }

    public HashMap<String, String> getHeaders(){
        return (HashMap<String, String>) initHttpHeaders();
    }
}
