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

import android.app.Application;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.lib.Account.AccountMaster;
import org.rmj.g3appdriver.lib.Account.Model.Auth;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.util.List;
import java.util.concurrent.Future;

public class VMForgotPassword extends AndroidViewModel {
    public static final String TAG = VMForgotPassword.class.getSimpleName();
    private List<Future<String>> futureList;

    private final iAuth poSys;
    private ConnectionUtil conn;
    private Application instance;
    private String message;
    //    private GCircleApi webApi;
    //    private HttpHeaders headers;

    public VMForgotPassword(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new AccountMaster(instance).initGuanzonApp().getInstance(Auth.FORGOT_PASSWORD);

        conn = new ConnectionUtil(application);

        //        this.webApi = new GCircleApi(application);
        //        headers = HttpHeaders.getInstance(application);
    }

    /*public void RequestPassword(String Email, RequestPasswordCallback callback){

//        if(Email.trim().isEmpty()){
//            callback.OnFailedRequest("Please enter your email");
//        } else if(TextUtils.isEmpty(Email) &&
//                Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
//            callback.OnFailedRequest("Please enter valid email");
//        } else if(!conn.isDeviceConnected()){
//            callback.OnFailedRequest("Unable to connect. Please check your internet connection.");
//        } else {
//            try{
//                JSONObject params = new JSONObject();
//                params.put("email", Email);
//                new RequestPasswordTask(instance, callback).execute(params);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }*/

    //    public static class RequestPasswordTask extends AsyncTask<JSONObject, Void, String>{
//        private GCircleApi webApi;
//        private HttpHeaders headers;
//        private final AppConfigPreference loConfig;
//        private RequestPasswordCallback callBack;
//
//        public RequestPasswordTask(Application instance, RequestPasswordCallback callBack){
//            this.loConfig = AppConfigPreference.getInstance(instance);
//            this.webApi = new GCircleApi(instance);
//            this.headers = HttpHeaders.getInstance(instance);
//            this.callBack = callBack;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            callBack.OnSendRequest("Forgot Password", "Send email request. Please wait...");
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        protected String doInBackground(JSONObject... jsonObjects) {
//            String response = "";
//            try {
//                response = WebClient.sendRequest(webApi.getUrlForgotPassword(), jsonObjects[0].toString(), headers.getHeaders());
//                Log.e(TAG, response);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            try {
//                JSONObject loResponse = new JSONObject(s);
//                String lsResult = loResponse.getString("result");
//                if(lsResult.equalsIgnoreCase("success")){
//                    callBack.OnSuccessRequest();
//                } else {
//                    JSONObject loError = loResponse.getJSONObject("error");
//                    String lsMessage = getErrorMessage(loError);
//                    callBack.OnFailedRequest(lsMessage);
//                    Log.e(TAG, s);
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//            this.cancel(true);
//        }
//    }

    public boolean isEmailValid(String Email){
        return TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }

    public void RequestPassword(String Email, RequestPasswordCallback mListener){
        TaskExecutor taskExecutor = new TaskExecutor();
        taskExecutor.Execute(Email, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                mListener.OnSendRequest("Send Request", "Sending Request . . .");
            }

            @Override
            public Object DoInBackground(Object args) {
                if (!conn.isDeviceConnected()){
                    message = conn.getMessage();
                    return false;
                }

                if (poSys.DoAction((String) args) == 0){
                    message = poSys.getMessage();
                    return false;
                }else {
                    message = poSys.getMessage();
                    return true;
                }
            }

            @Override
            public void OnPostExecute(Object object) {
                Boolean result = (Boolean) object;

                if (result.equals(false)){
                    mListener.OnFailedRequest(message);
                }else {
                    mListener.OnSuccessRequest();
                }
            }
        });
    }

    public interface RequestPasswordCallback{
        void OnSendRequest(String title, String message);
        void OnSuccessRequest();
        void OnFailedRequest(String message);
    }
}