/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.authLibrary
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.authlibrary.UserInterface.CreateAccount;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.io.IOException;
import java.util.HashMap;

public class VMCreateAccount extends AndroidViewModel{
    public static final String TAG = VMCreateAccount.class.getSimpleName();
    private WebApi webApi;
    private HttpHeaders headers;
    private ConnectionUtil conn;

    public VMCreateAccount(@NonNull Application application) {
        super(application);
        AppConfigPreference loConfig = AppConfigPreference.getInstance(application);
        this.webApi = new WebApi(loConfig.getTestStatus());
        headers = HttpHeaders.getInstance(application);
        conn = new ConnectionUtil(application);
    }

    public void SubmitInfo(AccountInfo accountInfo, CreateAccountCallBack callBack){
        if(accountInfo.isAccountInfoValid()){
            JSONObject params = new JSONObject();
            try{
                params.put("name", accountInfo.getFullName());
                params.put("mail", accountInfo.getEmail());
                params.put("pswd", accountInfo.getPassword());
                params.put("mobile", accountInfo.getMobileNo());
            } catch (Exception e){
                e.printStackTrace();
            }

            if (conn.isDeviceConnected()) {
                new CreateAccountTask(webApi, headers, callBack).execute(params);
            } else {
                callBack.OnFailedRegistration("Unable to connect. Please check your internet connection.");
            }
        } else {
            callBack.OnFailedRegistration(accountInfo.getMessage());
        }
    }

    private static class CreateAccountTask extends AsyncTask<JSONObject, Void, String>{
        private WebApi webApi;
        private HttpHeaders headers;
        private CreateAccountCallBack callBack;

        public CreateAccountTask(WebApi webApi, HttpHeaders headers, CreateAccountCallBack callBack){
            this.webApi = webApi;
            this.headers = headers;
            this.callBack = callBack;
        }

        @Override
        protected void onPreExecute() {
            callBack.OnAccountLoad("GhostRider Android", "Sending account info. Please wait...");
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                response = WebClient.httpsPostJSon(webApi.getUrlCreateAccount(), jsonObjects[0].toString(), (HashMap<String, String>) headers.getHeaders());
                Log.e(TAG, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loResponse = new JSONObject(s);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callBack.OnSuccessRegistration();
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callBack.OnFailedRegistration(lsMessage);
                    Log.e(TAG, s);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(true);
        }
    }
}