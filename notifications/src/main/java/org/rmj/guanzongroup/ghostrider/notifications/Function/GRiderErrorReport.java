/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 10/7/21, 2:59 PM
 * project file last modified : 10/7/21, 2:59 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Function;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.WebClient;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;

public class GRiderErrorReport {
    private static final String TAG = GRiderErrorReport.class.getSimpleName();

    private final Application instance;

    private final HttpHeaders poHeaders;
    private final WebApi poApi;
    private final AppConfigPreference loConfig;

    private String Message;

    public GRiderErrorReport(Application application) {
        this.instance = application;
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.loConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(loConfig.getTestStatus());
    }

    public String getMessage() {
        return Message;
    }

    public boolean SendErrorReport(String Title, String Data) {
        try {
            JSONArray rcpts = new JSONArray();
            JSONObject rcpt = new JSONObject();
            rcpt.put("app", "gRider"); //Product ID of Recepient
            rcpt.put("user", "GAP021002961"); //Garcia, Michael
            rcpts.put(rcpt);

            //Create the parameters needed by the API
            JSONObject param = new JSONObject();
            param.put("type", "00000"); //Message Type
            /**
             * Types of message:
             * 00000        Regular Message
             * 00001	ACCOUNT LOGIN ON NEW DEVICE
             * 00002	GCARD REGISTERED ON DIFFERENT ACCOUNTS
             * 00003	CASH COUNT REQUEST
             * 00004	REG OR/CR PROCESS
             * 00005	GCARD PREORDER
             */

            param.put("parent", null); //Message ID of first notification
            param.put("title", Title);
            param.put("message", Data);
            param.put("rcpt", rcpts);

            JSONObject json_obj = null;

            String response = WebClient.sendHTTP(poApi.getUrlSendRequest(loConfig.isBackUpServer()), param.toString(), poHeaders.getHeaders());
            if(response == null) {
                Message = "No server response";
                return false;
            } else {
                JSONObject loResponse = new JSONObject(response);
                String lsResult =  loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    return true;
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    Message = loError.getString("message");
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            Message = "Something went wrong while sending error report. " + e.getMessage();
            return false;
        }
    }
}
