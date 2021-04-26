/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GRider.Constants;

import android.icu.util.TimeZone;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppConstants {

    public static final String APP_PUBLIC_FOLDER = "/org.rmj.guanzongroup.ghostrider.epacss";
    public static final String SUB_FOLDER_DCP = "/DCP";
    public static final String SUB_FOLDER_CREDIT_APP = "/CreditApp";
    public static final String SUB_FOLDER_SELFIE_LOG = "/SelfieLog";
    public static final String SUB_FOLDER_CREDIT_APP_DOCUMENTS = "/COAD";
    public static final String SUB_FOLDER_EXPORTS = "/Exported Files";

    public static String ALL_DATA_SENT() throws Exception{
        JSONObject loJson = new JSONObject();
        loJson.put("result", "success");
        return loJson.toString();
    }

    public static String NO_INTERNET() throws Exception{
        JSONObject loJson = new JSONObject();
        JSONObject loError = new JSONObject();
        loJson.put("result", "error");
        loError.put("code", "503");
        loError.put("message", "Not connected to internet");
        loJson.put("error", loError);
        return loJson.toString();
    }

    public static String LOCAL_EXCEPTION_ERROR(String message) {
        JSONObject loJson = new JSONObject();
        JSONObject loError = new JSONObject();
        try {
            loJson.put("result", "error");
            loError.put("code", "202");
            loError.put("message", message);
            loJson.put("error", loError);
        } catch (Exception e){
            e.printStackTrace();
        }
        return loJson.toString();
    }

    public static String ERROR_SAVING_TO_LOCAL() throws Exception{
        JSONObject loJson = new JSONObject();
        JSONObject loError = new JSONObject();
        loJson.put("result", "error");
        loError.put("code", "503");
        loError.put("message", "Unable to save data to local. ");
        loJson.put("error", loError);
        return loJson.toString();
    }

    public static String SERVER_NO_RESPONSE() throws Exception{
        JSONObject loJson = new JSONObject();
        JSONObject loError = new JSONObject();
        loJson.put("result", "error");
        loError.put("code", "404");
        loError.put("message", "No response receive from server.\n Please check your internet connection \n and try again later.");
        loJson.put("error", loError);
        return loJson.toString();
    }

    public static String APPROVAL_CODE_EMPTY(String fsMessage) throws Exception{
        JSONObject loJson = new JSONObject();
        JSONObject loError = new JSONObject();
        loJson.put("result", "error");
        loError.put("code", "404");
        loError.put("message", fsMessage);
        loJson.put("error", loError);
        return loJson.toString();
    }

    public static String APPROVAL_CODE_GENERATED(String fsMessage) throws Exception{
        JSONObject loJson = new JSONObject();
        loJson.put("result", "success");
        loJson.put("code", fsMessage);
        return loJson.toString();
    }

    public static final int LOCATION_REQUEST = 1000;
    public static final int CAMERA_REQUEST = 999;
    public static final int CONTACT_REQUEST = 998;
    public static final int STORAGE_REQUEST = 997;
    public static final int GPS_REQUEST = 1001;

    public static int LOGIN_ACTIVITY_REQUEST_CODE = 143;

    public static int INVENTORY_REQUEST_CODE = 129;

    public static int PERMISION_REQUEST_CODE = 102;

    public static int JOB_ID = 213;

    public static int INTENT_OB_APPLICATION = 101;

    public static int INTENT_LEAVE_APPLICATION = 102;

    public static int INTENT_SELFIE_LOGIN = 103;

    public static String ACTIVATE_REQUEST = "DEVICES_APPROVE";
    public static String GET_PIN_REQUEST = "DEVICES_GETPIN";
    public static String UNLOCK_REQUEST = "DEVICES_UNLOCK";
    public static String OFFLINE_PIN_REQUEST = "DEVICES_OFFLINE_PIN";
    public static String GET_DEVICE_LOG_REQUEST = "DEVICES_GETDEVICELOG";

    public static String[] LEAVE_TYPE = {
            "Vacation",
            "Sick",
            "Others",
            "Birthday",
            "Paternity",
            "Solo Parent",
            "Saturday"};

    public static String DATE_MODIFIED = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

    public static String CURRENT_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    public static String CURRENT_TIME = String.valueOf(new Timestamp(new Date().getTime()));


}
