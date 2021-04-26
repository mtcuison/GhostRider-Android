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

package org.rmj.g3appdriver.lib.integsys;


import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.GRider.Http.WebClient;

import static org.rmj.g3appdriver.etc.SQLiteHandler.TAG;

public class ApprovalCode {
    private String dialog_result = "";

    private String message = "";
    private String imei;
    private String clientid;
    private String productid;
    private String userid;
    private String logno;
    Context ctx;

    public ApprovalCode(Context sctx, String simei, String sclientid, String sproductid, String suserid, String slogno){
        this.ctx = sctx;
        this.imei = simei;
        this.clientid = sclientid;
        this.productid = sproductid;
        this.userid = suserid;
        this.logno = slogno;

    }

    //result for dialog
    public String getResult(){
        return dialog_result;
    }

    //getting message
    public String getMessage(){
        return message;
    }

    public String getAuthority(String fsSCATypex,
                               String fsEmpLevID,
                               String fsDeptIDxx,
                               String fsPositnID){

        String sURL = "https://restgk.guanzongroup.com.ph/integsys/paramqry/sca_approval.php";

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
        headers.put("g-api-token", "");

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("scatypex", fsSCATypex);
            param.put("emplevid", fsEmpLevID);
            param.put("deptidxx", fsDeptIDxx);
            param.put("positnid", fsPositnID);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;
        String response = null;

        try {
            Log.d(TAG, " URL LOAD REQUEST: " + sURL);
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
            return "";
        }else{
            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                if(result.equals("success")){
                    message = "Record Loaded Successfully.";
                    dialog_result = jObj.getString("detail");

                    return dialog_result;
                }else{
                    JSONObject jError = new JSONObject(jObj.getString("error"));
                    message = "Unable to Load Record";
                    dialog_result = jError.getString("message");

                    return "";
                }
            } catch (JSONException e) {
                message = "Please make sure the internet connection is stable.";
                dialog_result = e.getMessage();
                return "";
            }
        }
    }

    public boolean loadRequest(String fsSystemCd,
                               String fsBranchCd,
                               String fsTransNox){

        String sURL = "https://restgk.guanzongroup.com.ph/integsys/codeapproval/code_load.php";

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
        headers.put("g-api-token", "");

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("systemcd", fsSystemCd);
            param.put("branchcd", fsBranchCd);
            param.put("transnox", fsTransNox);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;
        String response = null;

        try {
            Log.d(TAG, " URL LOAD REQUEST: " + sURL);
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
            return false;
        }else{
            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                if(result.equals("success")){
                    message = "Transaction Loaded Successfully.";
                    dialog_result = jObj.getString("detail");
                    return true;
                }else{
                    JSONObject jError = new JSONObject(jObj.getString("error"));
                    message = "Unable to Load Transaction";
                    dialog_result = jError.getString("message");
                    return false;
                }
            } catch (JSONException e) {
                message = "Please make sure the internet connection is stable.";
                dialog_result = e.getMessage();
                return  false;
            }
        }
    }

    public boolean approveRequest(String fsTransNox,
                                  String fsReasonxx,
                                  String fsApproved){

        if (fsReasonxx.equals("")){
            message = "Unable to process empty reason.";
            dialog_result = "";
            return false;
        }

        String sURL = "https://restgk.guanzongroup.com.ph/integsys/codeapproval/code_decide.php";

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
        headers.put("g-api-token", "");

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("transnox", fsTransNox);
            param.put("reasonxx", fsReasonxx);
            param.put("approved", fsApproved);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;

        String response = null;

        try {
            Log.d(TAG, " URL LOAD REQUEST: " + sURL);
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
            return false;
        }else{
            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                if(result.equals("success")){
                    if (fsApproved.equalsIgnoreCase("yes")){
                        message = "Transaction Approved Successfully.";
                    } else {
                        message = "Transaction Disapproved Successfully.";
                    }

                    dialog_result = jObj.getString("apprcode");
                    return true;
                }else{
                    JSONObject jError = new JSONObject(jObj.getString("error"));
                    if (fsApproved.equalsIgnoreCase("yes")){
                        message = "Unable to approved transaction.";
                    } else {
                        message = "Unable to disapproved transaction.";
                    }
                    dialog_result = jError.getString("message");
                    return false;
                }
            } catch (JSONException e) {
                message = "Please make sure the internet connection is stable.";
                dialog_result = "error";
                return  false;
            }
        }
    }
}
