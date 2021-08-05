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

package org.rmj.g3appdriver.lib.account;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.GRider.Http.WebClient;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.rmj.g3appdriver.etc.SQLiteHandler.TAG;

public class Forgot_Password {
    private String dialog_result = "";

    private String message = "";
    private String imei;
    private String clientid;
    private String productid;
    private String token;
    Context ctx;

    public Forgot_Password(Context sctx, String simei, String sclientid, String sproductid, String stoken){
        this.ctx = sctx;
        this.imei = simei;
        this.clientid = sclientid;
        this.productid = sproductid;
        this.token = stoken;

        Log.d(TAG,"Headers: IMEI-" + imei +" CLIENTID-"+clientid+" PRODUCTID-"+productid+" TOKEN-"+token);
    }

    //result for dialog
    public String getResult(){
        return dialog_result;
    }

    //getting message
    public String getMessage(){
        return message;
    }

    public void forgot_password(final String email){

        String sURL = "https://restgk.guanzongroup.com.ph/security/forgotpswd.php";
        Calendar calendar = Calendar.getInstance();
        Map<String, String> headers =new HashMap<String, String>();

        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");

        headers.put("g-api-id", productid);
        headers.put("g-api-imei", imei);

        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();

        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-client", clientid);
        headers.put("g-api-token", token);

        Log.d(TAG, "API-HASH: " + headers.get("g-api-hash"));

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("email", email);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;

        String response = null;
        try {
            Log.d(TAG, " URL ACTIVATION: " + sURL);
            response = WebClient.sendRequest(sURL, param.toString(), (HashMap<String, String>) headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
            if(System.getProperty("store.error.info").equals("404")){
                message = "Authorization Error " + System.getProperty("store.error.info") + ": Please make sure internet is stable. ";
                dialog_result = "error";
            }else{
                message = "Authorization Error " +  System.getProperty("store.error.info") + ": Please make sure the internet connection is stable.";
                dialog_result = "error";
            }
        }else{
            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                if(result.equals("success")){
                    message = "Success";
                    //DO SOMETHING

                }else{
                    Log.d(TAG, "Else: Json Result : " + response);
                    message = "Else Error";
                }
            } catch (JSONException e) {
                Log.d(TAG, "Catch: Json Result : " + response);
                message = "Catch Error";

            }
        }
    }
}
