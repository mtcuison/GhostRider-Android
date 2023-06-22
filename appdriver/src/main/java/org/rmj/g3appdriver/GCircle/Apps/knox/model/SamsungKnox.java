package org.rmj.g3appdriver.GCircle.Apps.knox.model;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;

public abstract class SamsungKnox {
    private static final String TAG = SamsungKnox.class.getSimpleName();

    protected final Application instance;
    protected final HttpHeaders poHeaders;
    protected final GCircleApi poApi;
    protected String message;

    public SamsungKnox(Application instance) {
        this.instance = instance;
        this.poApi = new GCircleApi(instance);
        poHeaders = HttpHeaders.getInstance(instance);
    }

    public abstract String getMessage();

    public abstract String GetResult(String DeviceID, String Remarks);

    public abstract String GetResult(String DeviceID);
}
