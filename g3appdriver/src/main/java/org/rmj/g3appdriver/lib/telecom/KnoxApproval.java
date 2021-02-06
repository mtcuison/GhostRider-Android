package org.rmj.g3appdriver.lib.telecom;

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


public class KnoxApproval {
    private String dialog_result = "";

    private String message = "";
    private String imei;
    private String clientid;
    private String productid;
    private String userid;
    private String logno;
    private String token;
    Context ctx;
    private KnoxResult knoxResult;

    public KnoxApproval(Context sctx, String simei, String sclientid, String sproductid, String suserid, String slogno, String stoken){
        this.ctx = sctx;
        this.imei = simei;
        this.clientid = sclientid;
        this.productid = sproductid;
        this.userid = suserid;
        this.logno = slogno;
        this.token = stoken;
        this.knoxResult = new KnoxResult();


        Log.d(TAG,"Headers: IMEI-" + imei +" CLIENTID-"+clientid+" PRODUCTID-"+productid+" USERID-"+userid+" LOGNO-"+logno+" TOKEN-"+token);

    }

    //result for dialog
    public String getResult(){
        return dialog_result;
    }

    //getting message
    public String getMessage(){
        return message;
    }
    //imei Authorization

    //old version (not in use)
    public void isAuthorized(final String device_imei, final String deviceUID, final String approveComment){

        String sURL = "https://restgk.guanzongroup.com.ph/security/knoxauthactivate.php";
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
        headers.put("g-api-user", userid);
        headers.put("g-api-log", logno);
        headers.put("g-api-token", token);

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("imei", device_imei);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;

        String response = null;
        try {
            Log.d(TAG, " URL ACTIVATION: " + sURL);
            response = WebClient.httpsPostJSon(sURL, param.toString(), (HashMap<String, String>) headers);
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
                    //message = result;
                    String knox_base = jObj.getString("knox_base");
                    String knox_url = jObj.getString("knox_url");
                    String knox_api = jObj.getString("knox_api");

                    String url_activation =knox_base + knox_url;
                    String sUrl  =  url_activation.replace("\\", "");
                    message = sUrl;
                    deviceApproved(sUrl, knox_api, deviceUID, approveComment);

                }else{
                    //message = "This device is not authorized.";
                    Log.d(TAG, "Else Error: " + response);

                    message = "Your device: " + device_imei +" is not authorized. ";
                    dialog_result = "unauthorize";
                }
            } catch (JSONException e) {
                message = "Please make sure the internet connection is stable.";
                dialog_result = "error";

            }
        }
    }
    //old version (not in use)
    public void deviceApproved(final String url_activation, final String knox_api, final String deviceUid, final String approveComment){

        String sURL = url_activation;
        String transactionId = "M00118000001";

        Map<String, String> headers =new HashMap<String, String>();

        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("x-knox-apikey", knox_api);
        headers.put("x-knox-transactionId",transactionId);

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("deviceUid", deviceUid);
            param.put("approveComment", approveComment);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;

        String response = null;
        try {

            response = WebClient.httpsPostJSon(sURL, param.toString(), (HashMap<String, String>) headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));

            if(System.getProperty("store.error.info").equals("404")){
                message = "Error in activating this IMEI: " + deviceUid + ". This device seems to be not uploaded yet. Please contact Mr. Jeff Yambao in this number : 09177013632 for further assistance,";
                dialog_result = "error";

            }else if(System.getProperty("store.error.info").equals("400")){
                message = "This IMEI " + deviceUid + " could be activated already. Please contact Mr. Jeff Yambao in this number : 09177013632 for further assistance," ;
                dialog_result = "error";
            }
            else{
                message = "Activation Error " + System.getProperty("store.error.info") + ": Please contact Mr. Jeff Yambao in this number : 09177013632 for further assistance,";
                dialog_result = "error";
            }

        }else{
            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                if(result.equalsIgnoreCase("success")){
                    //message = response.toString();
                    message = "This IMEI: " + deviceUid + " was succesfully activated.";
                    dialog_result = "success";

                }else{
                    //message = "This device is not authorized.";
                    //message = response.toString();
                    message = "This IMEI: " + deviceUid + " was not activated.";
                    dialog_result = "error";
                }
            } catch (JSONException e) {
                message = "Please make sure the internet connection is stable.";
                dialog_result = "error";
            }
        }
    }

    //new version of activation
    public void knoxApproval(final String request, final JSONObject parameters){

        Log.d(TAG, "Passed Values:  Request: " + request + " Json Parameters: " + parameters);

        String sURL = "https://restgk.guanzongroup.com.ph/samsung/knox.php";
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
        headers.put("g-api-user", userid);
        headers.put("g-api-log", logno);
        headers.put("g-api-token", token);

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("request", request);
            param.put("param", parameters.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Knox Approval Params: " + param);


        JSONObject json_obj = null;

        String response = null;
        try {
            //Log.d(TAG, " URL ACTIVATION: " + sURL);
            response = WebClient.httpsPostJSon(sURL, param.toString(), (HashMap<String, String>) headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
            if(System.getProperty("store.error.info").equals("404")){
                message = "Authorization Error " + System.getProperty("store.error.info");
                dialog_result = "error";
            }else{
                message = "Authorization Error " +  System.getProperty("store.error.info");
                dialog_result = "error";
            }
        }else{
            Log.d(TAG, "Response From Activation: " + response);

            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                if(result.equalsIgnoreCase("success")){
                    //message = response.toString();
                    message = "Imei is succesfully activated.";
                    dialog_result = "success";

                }else{
                    JSONObject jObj_err =  new JSONObject(jObj.getString("error"));
                    String err_code = jObj_err.getString("code");
                    Log.d(TAG, "Else Error: " + response);

                    knoxResult.getErrorMessage(err_code);
                    //message = err_code;
                }
            } catch (JSONException e) {
                message = response;
                dialog_result = "error";
            }

        }
    }
}
