package org.rmj.g3appdriver.GCircle.Apps.knox.model;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;

public class SamsungKnox {
    private static final String TAG = SamsungKnox.class.getSimpleName();

    protected final Application instance;
    protected final HttpHeaders headers;
    protected final GCircleApi poApi;
    protected String message;

    public SamsungKnox(Application instance) {
        this.instance = instance;
        this.poApi = new GCircleApi(instance);
        headers = HttpHeaders.getInstance(instance);
    }

    public String getMessage(){
        return message;
    }

    public String GetResult(String DeviceID, String Remarks){
        try{
            return "";
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public String GetResult(String DeviceID){
        try{
            return "";
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }
}
