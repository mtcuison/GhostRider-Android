package org.rmj.guanzongroup.authlibrary.UserInterface.ForgotPassword;

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
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.io.IOException;
import java.util.HashMap;

public class VMForgotPassword extends AndroidViewModel {
    public static final String TAG = VMForgotPassword.class.getSimpleName();
    private WebApi webApi;
    private HttpHeaders headers;
    private ConnectionUtil conn;

    public VMForgotPassword(@NonNull Application application) {
        super(application);
        webApi = new WebApi(application);
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
                params.put("name", Email);
                new RequestPasswordTask(webApi, headers, callback).execute(params);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean isEmailValid(String Email){
        return TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }

    public static class RequestPasswordTask extends AsyncTask<JSONObject, Void, String>{
        private WebApi webApi;
        private HttpHeaders headers;
        private RequestPasswordCallback callBack;

        public RequestPasswordTask(WebApi webApi, HttpHeaders headers, RequestPasswordCallback callBack){
            this.webApi = webApi;
            this.headers = headers;
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
                response = new WebClient().httpsPostJSon(webApi.URL_REGISTRATION(), jsonObjects[0].toString(), (HashMap<String, String>) headers.getHeaders());
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
                    String lsMessage = loError.getString("message");
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