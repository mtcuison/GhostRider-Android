package org.rmj.g3appdriver.lib.Account.gCircle.obj;

import android.app.Application;

import org.rmj.g3appdriver.dev.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;

public class ForgotPassword implements iAuth {
    private static final String TAG = ForgotPassword.class.getSimpleName();

    private final Application instance;
    private final GCircleApi webApi;
    private final HttpHeaders headers;

    public ForgotPassword(Application instance) {
        this.instance = instance;
        this.webApi = new GCircleApi(instance);
        this.headers = HttpHeaders.getInstance(instance);
    }

    @Override
    public int DoAction(Object params) {
        return 0;
    }

    @Override
    public String getString() {
        return null;
    }
}
