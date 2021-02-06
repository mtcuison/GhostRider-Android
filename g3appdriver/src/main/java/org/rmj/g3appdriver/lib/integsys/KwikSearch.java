package org.rmj.g3appdriver.lib.integsys;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.GRider.Http.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.rmj.g3appdriver.etc.SQLiteHandler.TAG;

public class KwikSearch {
    String message = "";

    //constructor
    private String url;
    private String imei;
    private String clientid;
    private String productid;
    private String userid;
    private String logno;
    private String token;
    Context ctx;

    private ArrayList<String> parameter1 = new ArrayList<String>();
    private ArrayList<String> parameter2 = new ArrayList<String>();
    private ArrayList<String> parameter3 = new ArrayList<String>();
    private ArrayList<String> parameter4 = new ArrayList<String>();
    private ArrayList<String> parameter5 = new ArrayList<String>();
    private ArrayList<String> parameter6 = new ArrayList<String>();
    private ArrayList<String> parameter7 = new ArrayList<String>();
    private ArrayList<String> parameter8 = new ArrayList<String>();
    private ArrayList<String> parameter9 = new ArrayList<String>();
    private ArrayList<String> parameter10 = new ArrayList<String>();

    private String json_index1;
    private String json_index2;
    private String json_index3;
    private String json_index4;
    private String json_index5;
    private String json_index6;
    private String json_index7;
    private String json_index8;
    private String json_index9;
    private String json_index10;

    private ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

    public KwikSearch(Context sctx, String surl, String simei, String sclientid, String sproductid, String suserid, String slogno, String stoken){
        this.ctx = sctx;
        this.url = surl;
        this.imei = simei;
        this.clientid = sclientid;
        this.productid = sproductid;
        this.userid = suserid;
        this.logno = slogno;
        this.token = stoken;
    }

    //getting message
    public String getMessage(){
        return message;
    }

    public ArrayList<ArrayList<String>> Qsearch(final String jsonToSearch, final Boolean bsearch, final ArrayList<ArrayList<String>> jsonToBeDisplayed){

        String sURL = url;
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

        Log.d(TAG, "API-HASH: " + headers.get("g-api-hash"));

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            if(bsearch.equals(true)){
                param.put("reqstdnm", jsonToSearch);
                param.put("bsearch", bsearch);
            }else{
                param.put("reqstdid", jsonToSearch);
                param.put("bsearch", bsearch);
            }
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

        }else{
            Log.d(TAG, "Json Response: " + response);
            Log.d(TAG, "Passed Value: " + jsonToSearch);
            Log.d(TAG, "Size of Array: " + jsonToBeDisplayed.size());
            Log.d(TAG, "Array Content: " + jsonToBeDisplayed.toString().replace("[", "").replace("]", ""));

            try {
                Log.d(TAG, "Return Value from login : " + response);
                JSONObject jObj = new JSONObject(response);
                String json_result = jObj.getString("result");

                if(json_result.equals("success")){
                    try{
                        JSONArray storesArray = jObj.getJSONArray("detail");
                        Log.d(TAG, "Detail Array: " + storesArray);
                        Log.d(TAG, "Size of Json: " + storesArray.length());
                        clear_parameters();

                        for(int i = 0; i < storesArray.length(); i++){
                            JSONObject store = storesArray.getJSONObject(i);

                            if(jsonToBeDisplayed.size()<=0){
                                message = "Missing Parameters. Please contact MIS Department";
                            }if(jsonToBeDisplayed.size()>10){
                                message = "Parameters exceeded. Please contact MIS Department";
                            }else{
                                if(jsonToBeDisplayed.size()==1){
                                    json_index1  = jsonToBeDisplayed.get(0).toString().replace("[", "").replace("]", "");
                                    parameter1.add(store.getString(json_index1));
                                } if(jsonToBeDisplayed.size()==2){
                                    json_index1  = jsonToBeDisplayed.get(0).toString().replace("[", "").replace("]", "");
                                    json_index2  = jsonToBeDisplayed.get(1).toString().replace("[", "").replace("]", "");
                                    parameter1.add(store.getString(json_index1));
                                    parameter2.add(store.getString(json_index2));
                                }if(jsonToBeDisplayed.size()==3){
                                    json_index1  = jsonToBeDisplayed.get(0).toString().replace("[", "").replace("]", "");
                                    json_index2  = jsonToBeDisplayed.get(1).toString().replace("[", "").replace("]", "");
                                    json_index3  = jsonToBeDisplayed.get(2).toString().replace("[", "").replace("]", "");
                                    parameter1.add(store.getString(json_index1));
                                    parameter2.add(store.getString(json_index2));
                                    parameter3.add(store.getString(json_index3));
                                }if(jsonToBeDisplayed.size()==4){
                                    json_index1  = jsonToBeDisplayed.get(0).toString().replace("[", "").replace("]", "");
                                    json_index2  = jsonToBeDisplayed.get(1).toString().replace("[", "").replace("]", "");
                                    json_index3  = jsonToBeDisplayed.get(2).toString().replace("[", "").replace("]", "");
                                    json_index4  = jsonToBeDisplayed.get(3).toString().replace("[", "").replace("]", "");
                                    parameter1.add(store.getString(json_index1));
                                    parameter2.add(store.getString(json_index2));
                                    parameter3.add(store.getString(json_index3));
                                    parameter4.add(store.getString(json_index4));
                                }if(jsonToBeDisplayed.size()==5){
                                    json_index1  = jsonToBeDisplayed.get(0).toString().replace("[", "").replace("]", "");
                                    json_index2  = jsonToBeDisplayed.get(1).toString().replace("[", "").replace("]", "");
                                    json_index3  = jsonToBeDisplayed.get(2).toString().replace("[", "").replace("]", "");
                                    json_index4  = jsonToBeDisplayed.get(3).toString().replace("[", "").replace("]", "");
                                    json_index5  = jsonToBeDisplayed.get(4).toString().replace("[", "").replace("]", "");
                                    parameter1.add(store.getString(json_index1));
                                    parameter2.add(store.getString(json_index2));
                                    parameter3.add(store.getString(json_index3));
                                    parameter4.add(store.getString(json_index4));
                                    parameter5.add(store.getString(json_index5));
                                }if(jsonToBeDisplayed.size()==6){
                                    json_index1  = jsonToBeDisplayed.get(0).toString().replace("[", "").replace("]", "");
                                    json_index2  = jsonToBeDisplayed.get(1).toString().replace("[", "").replace("]", "");
                                    json_index3  = jsonToBeDisplayed.get(2).toString().replace("[", "").replace("]", "");
                                    json_index4  = jsonToBeDisplayed.get(3).toString().replace("[", "").replace("]", "");
                                    json_index5  = jsonToBeDisplayed.get(4).toString().replace("[", "").replace("]", "");
                                    json_index6  = jsonToBeDisplayed.get(5).toString().replace("[", "").replace("]", "");
                                    parameter1.add(store.getString(json_index1));
                                    parameter2.add(store.getString(json_index2));
                                    parameter3.add(store.getString(json_index3));
                                    parameter4.add(store.getString(json_index4));
                                    parameter5.add(store.getString(json_index5));
                                    parameter6.add(store.getString(json_index6));
                                }if(jsonToBeDisplayed.size()==7){
                                    json_index1  = jsonToBeDisplayed.get(0).toString().replace("[", "").replace("]", "");
                                    json_index2  = jsonToBeDisplayed.get(1).toString().replace("[", "").replace("]", "");
                                    json_index3  = jsonToBeDisplayed.get(2).toString().replace("[", "").replace("]", "");
                                    json_index4  = jsonToBeDisplayed.get(3).toString().replace("[", "").replace("]", "");
                                    json_index5  = jsonToBeDisplayed.get(4).toString().replace("[", "").replace("]", "");
                                    json_index6  = jsonToBeDisplayed.get(5).toString().replace("[", "").replace("]", "");
                                    json_index7  = jsonToBeDisplayed.get(6).toString().replace("[", "").replace("]", "");
                                    parameter1.add(store.getString(json_index1));
                                    parameter2.add(store.getString(json_index2));
                                    parameter3.add(store.getString(json_index3));
                                    parameter4.add(store.getString(json_index4));
                                    parameter5.add(store.getString(json_index5));
                                    parameter6.add(store.getString(json_index6));
                                    parameter7.add(store.getString(json_index7));
                                }if(jsonToBeDisplayed.size()==8){
                                    json_index1  = jsonToBeDisplayed.get(0).toString().replace("[", "").replace("]", "");
                                    json_index2  = jsonToBeDisplayed.get(1).toString().replace("[", "").replace("]", "");
                                    json_index3  = jsonToBeDisplayed.get(2).toString().replace("[", "").replace("]", "");
                                    json_index4  = jsonToBeDisplayed.get(3).toString().replace("[", "").replace("]", "");
                                    json_index5  = jsonToBeDisplayed.get(4).toString().replace("[", "").replace("]", "");
                                    json_index6  = jsonToBeDisplayed.get(5).toString().replace("[", "").replace("]", "");
                                    json_index7  = jsonToBeDisplayed.get(6).toString().replace("[", "").replace("]", "");
                                    json_index8  = jsonToBeDisplayed.get(7).toString().replace("[", "").replace("]", "");
                                    parameter1.add(store.getString(json_index1));
                                    parameter2.add(store.getString(json_index2));
                                    parameter3.add(store.getString(json_index3));
                                    parameter4.add(store.getString(json_index4));
                                    parameter5.add(store.getString(json_index5));
                                    parameter6.add(store.getString(json_index6));
                                    parameter7.add(store.getString(json_index7));
                                    parameter8.add(store.getString(json_index8));
                                }if(jsonToBeDisplayed.size()==9){
                                    json_index1  = jsonToBeDisplayed.get(0).toString().replace("[", "").replace("]", "");
                                    json_index2  = jsonToBeDisplayed.get(1).toString().replace("[", "").replace("]", "");
                                    json_index3  = jsonToBeDisplayed.get(2).toString().replace("[", "").replace("]", "");
                                    json_index4  = jsonToBeDisplayed.get(3).toString().replace("[", "").replace("]", "");
                                    json_index5  = jsonToBeDisplayed.get(4).toString().replace("[", "").replace("]", "");
                                    json_index6  = jsonToBeDisplayed.get(5).toString().replace("[", "").replace("]", "");
                                    json_index7  = jsonToBeDisplayed.get(6).toString().replace("[", "").replace("]", "");
                                    json_index8  = jsonToBeDisplayed.get(7).toString().replace("[", "").replace("]", "");
                                    json_index9  = jsonToBeDisplayed.get(8).toString().replace("[", "").replace("]", "");
                                    parameter1.add(store.getString(json_index1));
                                    parameter2.add(store.getString(json_index2));
                                    parameter3.add(store.getString(json_index3));
                                    parameter4.add(store.getString(json_index4));
                                    parameter5.add(store.getString(json_index5));
                                    parameter6.add(store.getString(json_index6));
                                    parameter7.add(store.getString(json_index7));
                                    parameter8.add(store.getString(json_index8));
                                    parameter9.add(store.getString(json_index9));
                                }if(jsonToBeDisplayed.size()==10){
                                    json_index1  = jsonToBeDisplayed.get(0).toString().replace("[", "").replace("]", "");
                                    json_index2  = jsonToBeDisplayed.get(1).toString().replace("[", "").replace("]", "");
                                    json_index3  = jsonToBeDisplayed.get(2).toString().replace("[", "").replace("]", "");
                                    json_index4  = jsonToBeDisplayed.get(3).toString().replace("[", "").replace("]", "");
                                    json_index5  = jsonToBeDisplayed.get(4).toString().replace("[", "").replace("]", "");
                                    json_index6  = jsonToBeDisplayed.get(5).toString().replace("[", "").replace("]", "");
                                    json_index7  = jsonToBeDisplayed.get(6).toString().replace("[", "").replace("]", "");
                                    json_index8  = jsonToBeDisplayed.get(7).toString().replace("[", "").replace("]", "");
                                    json_index9  = jsonToBeDisplayed.get(8).toString().replace("[", "").replace("]", "");
                                    json_index10  = jsonToBeDisplayed.get(9).toString().replace("[", "").replace("]", "");
                                    parameter1.add(store.getString(json_index1));
                                    parameter2.add(store.getString(json_index2));
                                    parameter3.add(store.getString(json_index3));
                                    parameter4.add(store.getString(json_index4));
                                    parameter5.add(store.getString(json_index5));
                                    parameter6.add(store.getString(json_index6));
                                    parameter7.add(store.getString(json_index7));
                                    parameter8.add(store.getString(json_index8));
                                    parameter9.add(store.getString(json_index9));
                                    parameter10.add(store.getString(json_index10));
                                }
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    message = "Error";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "Catch Error: " + response);
                message = "Error 102: " + "Login Credentials Invalid";
            }
        }
        return_result();
        return result;
    }

    public void clear_parameters(){
        parameter1.clear();
        parameter2.clear();
        parameter3.clear();
        parameter4.clear();
        parameter5.clear();
        parameter6.clear();
        parameter7.clear();
        parameter8.clear();
        parameter9.clear();
        parameter10.clear();
    }

    public void return_result(){
        result.add(parameter1);
        result.add(parameter2);
        result.add(parameter3);
        result.add(parameter4);
        result.add(parameter5);
        result.add(parameter6);
        result.add(parameter7);
        result.add(parameter8);
        result.add(parameter9);
        result.add(parameter10);
    }
}
