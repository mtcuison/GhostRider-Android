package org.rmj.g3appdriver.lib.integsys;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;

public class CashCount {
    private static final String TAG = CashCount.class.getSimpleName();

    private final Context mContext;
    private final Application instance;
    private final WebApi poApi;

    private final HttpHeaders poHeaders;

    public interface SubmitCashCountCallback {
        void OnSuccess(String args1, String args2);
        void OnFailed(String message);
    }

    public CashCount(Context mContext, Application instance) {
        this.mContext = mContext;
        this.instance = instance;
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void SubmitCashCount(JSONObject foJson, SubmitCashCountCallback callback){
        try {
            String lsResponse = WebClient.httpPostJSon(poApi.getUrlSubmitCashcount(), foJson.toString(), poHeaders.getHeaders());
            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("success")){
                String lsTransNo = loResponse.getString("sTransNox");
                String lsReceive = loResponse.getString("dReceived");
                callback.OnSuccess(lsTransNo, lsReceive);
            } else {
                JSONObject loError = loResponse.getJSONObject("error");
                String lsMessage = loError.getString("message");
                callback.OnFailed(lsMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }
}
