package org.rmj.g3appdriver.GCircle.Apps.knox.model;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.Apps.knox.pojo.KnoxParams;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;

public class SamsungKnox {
    private static final String TAG = SamsungKnox.class.getSimpleName();

    private final Application instance;
    private final HttpHeaders headers;
    private final GCircleApi poApi;
    private String message;

    public SamsungKnox(Application instance) {
        this.instance = instance;
        this.poApi = new GCircleApi(instance);
        headers = HttpHeaders.getInstance(instance);
    }

    public JSONObject GetResult(Object args){
        try{
            return new JSONObject();
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }
}
