package org.rmj.g3appdriver.lib.Account.gCircle.obj;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Account.pojo.PasswordUpdate;

public class ChangePassword implements iAuth {
    private static final String TAG = ChangePassword.class.getSimpleName();

    private final Application instance;
    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public ChangePassword(Application instance) {
        this.instance = instance;
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    @Override
    public int DoAction(Object args) {
        try{
            PasswordUpdate loInfo = (PasswordUpdate) args;
            if(!loInfo.isDataValid()){
                message = loInfo.getMessage();
                return 0;
            }

            JSONObject params = new JSONObject();
            params.put("oldpswd", loInfo.getOldPassword());
            params.put("newpswd", loInfo.getNewPassword());

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlChangePassword(),
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

            message = "Password updated successfully.";
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
