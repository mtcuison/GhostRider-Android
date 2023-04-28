package org.rmj.g3appdriver.lib.Account.gCircle.obj;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;

public class ForgotPassword implements iAuth {
    private static final String TAG = ForgotPassword.class.getSimpleName();

    private final Application instance;
    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public ForgotPassword(Application instance) {
        this.instance = instance;
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    @Override
    public int DoAction(Object args) {
        try{
            if(args == null){
                message = "Please enter your email";
                return 0;
            }

            String lsEmail = (String) args;
            if(lsEmail.trim().isEmpty()){
                message = "Please enter your email";
                return 0;
            }

            if(TextUtils.isEmpty(lsEmail) &&
                    Patterns.EMAIL_ADDRESS.matcher(lsEmail).matches()){
                message = "Please enter valid email";
                return 0;
            }

            JSONObject params = new JSONObject();
            params.put("email", lsEmail);

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlForgotPassword(),
                    params.toString(),
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = "No server response.";
                return 0;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                String lsMessage = loError.getString("message");
                Log.e(TAG, lsMessage);
                message = lsMessage;
                return 0;
            }

            message = "Please check your email for password retrieval.";
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return 0;
        }
    }

    @Override
    public String getString() {
        return message;
    }
}
