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

package org.rmj.guanzongroup.authlibrary.UserInterface.ForgotPassword;

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.io.IOException;

public class VMForgotPassword extends AndroidViewModel {
    public static final String TAG = VMForgotPassword.class.getSimpleName();
    private GCircleApi webApi;
    private HttpHeaders headers;
    private ConnectionUtil conn;
    private Application instance;

    public VMForgotPassword(@NonNull Application application) {
        super(application);
        this.instance = application;
        AppConfigPreference loConfig = AppConfigPreference.getInstance(application);
        this.webApi = new GCircleApi(application);
        headers = HttpHeaders.getInstance(application);
        conn = new ConnectionUtil(application);
    }

    public void RequestPassword(String Email, RequestPasswordCallback callback){
        if(Email.trim().isEmpty()){
            callback.OnFailedRequest("Please enter your email");
        } else if(TextUtils.isEmpty(Email) &&
                Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            callback.OnFailedRequest("Please enter valid email");
        } else if(!conn.isDeviceConnected()){
            callback.OnFailedRequest("Unable to connect. Please check your internet connection.");
        } else {
            try{
                JSONObject params = new JSONObject();
                params.put("email", Email);
                new RequestPasswordTask(instance, callback).execute(params);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean isEmailValid(String Email){
        return TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }

    public static class RequestPasswordTask extends AsyncTask<JSONObject, Void, String>{
        private GCircleApi webApi;
        private HttpHeaders headers;
        private final AppConfigPreference loConfig;
        private RequestPasswordCallback callBack;

        public RequestPasswordTask(Application instance, RequestPasswordCallback callBack){
            this.loConfig = AppConfigPreference.getInstance(instance);
            this.webApi = new GCircleApi(instance);
            this.headers = HttpHeaders.getInstance(instance);
            this.callBack = callBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callBack.OnSendRequest("Forgot Password", "Send email request. Please wait...");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                response = WebClient.sendRequest(webApi.getUrlForgotPassword(), jsonObjects[0].toString(), headers.getHeaders());
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
                    callBack.OnSuccessRequest();
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = getErrorMessage(loError);
                    callBack.OnFailedRequest(lsMessage);
                    Log.e(TAG, s);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(true);
        }
    }

    public interface RequestPasswordCallback{
        void OnSendRequest(String title, String message);
        void OnSuccessRequest();
        void OnFailedRequest(String message);
    }
}