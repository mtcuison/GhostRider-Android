package org.rmj.g3appdriver.lib.Account.gCircle.obj;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Account.pojo.AccountInfo;

public class Register implements iAuth {
    private static final String TAG = Register.class.getSimpleName();

    private final Application instance;
    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public Register(Application instance) {
        this.instance = instance;
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }
    @Override
    public int DoAction(Object args) {
        try{
            AccountInfo loInfo = (AccountInfo) args;
            if(!loInfo.isAccountInfoValid()){
                message = loInfo.getMessage();
                return 0;
            }

            JSONObject params = new JSONObject();
            params.put("name", loInfo.getFullName());
            params.put("mail", loInfo.getEmail());
            params.put("pswd", loInfo.getPassword());
            params.put("mobile", loInfo.getMobileNo());

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlCreateAccount(),
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

            message = "An email has been sent to activate your account";
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return 0;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
