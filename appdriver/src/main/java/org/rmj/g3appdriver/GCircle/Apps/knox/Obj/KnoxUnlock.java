package org.rmj.g3appdriver.GCircle.Apps.knox.Obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Apps.knox.KnoxResult;
import org.rmj.g3appdriver.GCircle.Apps.knox.model.SamsungKnox;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConstants;

public class KnoxUnlock extends SamsungKnox {

    public KnoxUnlock(Application instance) {
        super(instance);
    }

    @Override
    public String GetResult(String DeviceID) {
        try{
            JSONObject loJSon = new JSONObject();
            JSONObject loParam = new JSONObject();
            loJSon.put("deviceUid", DeviceID);
            loParam.put("request", AppConstants.UNLOCK_REQUEST);
            loParam.put("param", loJSon.toString());

            String lsResponse = WebClient.sendRequest(poApi.getUrlKnox(), loParam.toString(), poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("FAIL") ||
                    lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = KnoxResult.getMessage(loError);
                return null;
            }

            return "Device has been successfully unlocked";
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String GetResult(String DeviceID, String Remarks) {
        return null;
    }
}
