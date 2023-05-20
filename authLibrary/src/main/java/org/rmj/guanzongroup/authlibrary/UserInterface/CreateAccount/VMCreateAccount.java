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

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.io.IOException;
import java.util.HashMap;

public class VMCreateAccount extends AndroidViewModel{
    public static final String TAG = VMCreateAccount.class.getSimpleName();
    private final Application instance;

    public VMCreateAccount(@NonNull Application application) {
        super(application);
        this.instance = application;
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
            new CreateAccountTask(instance, callBack).execute(params);
        } else {
            callBack.OnFailedRegistration(accountInfo.getMessage());
        }
    }

   /* private static class CreateAccountTask extends AsyncTask<JSONObject, Void, String>{
        private final Application instance;
        private GCircleApi webApi;
        private HttpHeaders headers;
        private ConnectionUtil poConn;
        private CreateAccountCallBack callBack;

        public CreateAccountTask(Application instance, CreateAccountCallBack callBack){
            this.instance = instance;
            this.callBack = callBack;
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected void onPreExecute() {
            callBack.OnAccountLoad("Guanzon Circle", "Sending account info. Please wait...");
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                AppConfigPreference poConfig = AppConfigPreference.getInstance(instance);
                webApi = new GCircleApi(instance);
                headers = HttpHeaders.getInstance(instance);
                if (poConn.isDeviceConnected()) {
                    response = WebClient.sendRequest(webApi.getUrlCreateAccount(), jsonObjects[0].toString(), (HashMap<String, String>) headers.getHeaders());
                    Log.e(TAG, response);
                } else {
                    response = AppConstants.LOCAL_EXCEPTION_ERROR("Unable to connect. Please check your internet connection.");
                }
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
                    String lsMessage = getErrorMessage(loError);
                    callBack.OnFailedRegistration(lsMessage);
                    Log.e(TAG, s);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(true);
        }
    }*/
   private static class CreateAccountTask{
       private final Application instance;
       private GCircleApi webApi;
       private HttpHeaders headers;
       private ConnectionUtil poConn;
       private CreateAccountCallBack callBack;

       public CreateAccountTask(Application instance, CreateAccountCallBack callBack){
           this.instance = instance;
           this.callBack = callBack;
           this.poConn = new ConnectionUtil(instance);
       }

       public void execute(JSONObject jsonObjects){
           TaskExecutor.Execute(jsonObjects, new OnTaskExecuteListener() {
               @Override
               public void OnPreExecute() {
                   callBack.OnAccountLoad("GhostRider Android", "Sending account info. Please wait...");
               }

               @Override
               public Object DoInBackground(Object args) {
                   String response = "";
                   try {
                       AppConfigPreference poConfig = AppConfigPreference.getInstance(instance);
                       webApi = new GCircleApi(instance);
                       headers = HttpHeaders.getInstance(instance);
                       if (poConn.isDeviceConnected()) {
                           response = WebClient.sendRequest(webApi.getUrlCreateAccount(), args.toString(), (HashMap<String, String>) headers.getHeaders());
                           Log.e(TAG, response);
                       } else {
                           response = AppConstants.LOCAL_EXCEPTION_ERROR("Unable to connect. Please check your internet connection.");
                       }
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   return response;
               }

               @Override
               public void OnPostExecute(Object object) {
                   try {
                       JSONObject loResponse = new JSONObject(object.toString());
                       String lsResult = loResponse.getString("result");
                       if(lsResult.equalsIgnoreCase("success")){
                           callBack.OnSuccessRegistration();
                       } else {
                           JSONObject loError = loResponse.getJSONObject("error");
                           String lsMessage = getErrorMessage(loError);
                           callBack.OnFailedRegistration(lsMessage);
                           Log.e(TAG, object.toString());
                       }
                   } catch (Exception e){
                       e.printStackTrace();
                   }
               }
           });
       }
   }
}