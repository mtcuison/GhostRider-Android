package org.rmj.g3appdriver.lib.Account.gCircle.obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Account.pojo.VerificationInfo;

import java.util.Random;

public class SendVerification {
    private static final String TAG = SendVerification.class.getSimpleName();

    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public SendVerification(Application instance) {
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String GetMessage(){
        return message;
    }

    public Object VerifyAccount(String fsParam){

        try{
            if(fsParam == null || fsParam.isEmpty()){
                message = "Please enter your email or mobile number";
                return false;
            }

            Random random = new Random();
            int lsOTP = 100000 + random.nextInt(900000);

            JSONObject params = new JSONObject();
            params.put("id", fsParam);
            params.put("otp", lsOTP);

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlSendVerification(),
                    params.toString(),
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }
            Log.d("Verify Account", lsResponse);

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            message = "We've sent a verification code to your entered account.";
            return new VerificationInfo(
                    loResponse.getString("userid"),
                    loResponse.getString("otp")
            );

        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }

    }
}
