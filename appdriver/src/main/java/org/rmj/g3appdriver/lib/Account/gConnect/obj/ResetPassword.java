package org.rmj.g3appdriver.lib.Account.gConnect.obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Account.pojo.PasswordUpdate;

public class ResetPassword implements iAuth {
    private static final String TAG = ResetPassword.class.getSimpleName();

    private final Application instance;
    private final GConnectApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public ResetPassword(Application instance) {
        this.instance = instance;
        this.poApi = new GConnectApi(instance);
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
                    poApi.getRetrievePasswordAPI(),
                    params.toString(),
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return 0;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                Log.e(TAG, message);
                return 0;
            }

            message = "We've sent an email to your registered address with instructions for password recovery.";
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return 0;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
