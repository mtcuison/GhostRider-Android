package org.rmj.g3appdriver.lib.Notification;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;

public class NMMRequest {
    private static final String TAG = NMMRequest.class.getSimpleName();

    private final Application instance;

    private String message;

    public NMMRequest(Application instance) {
        this.instance = instance;
    }

    public String getMessage() {
        return message;
    }

    public boolean RequestPreviousNotifications(int fnIndex){
        try{
            WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
            JSONObject params = new JSONObject();
            params.put("nLimitxxx", fnIndex);
            String lsResponse = WebClient.sendRequest(
                    loApi.getUrlRequestPreviousNotifications(),
                    params.toString(),
                    HttpHeaders.getInstance(instance).getHeaders());

            if(lsResponse == null){
                message = "Server no response";
            } else {
                JSONObject loResponse = new JSONObject();
                JSONArray laMaster = loResponse.getJSONArray("master");
                JSONArray laRcpntx = loResponse.getJSONArray("recipient");

                for(int x = 0; x < laMaster.length(); x++){

                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
