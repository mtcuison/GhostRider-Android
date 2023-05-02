package org.rmj.g3appdriver.lib.Account.gConnect.obj;

import android.app.Application;

import org.rmj.g3appdriver.dev.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;

public class SignIn implements iAuth {
    private static final String TAG = SignIn.class.getSimpleName();

    private final Application instance;
    private final GConnectApi poApi;
    private final HttpHeaders poHeaders;

    public SignIn(Application instance) {
        this.instance = instance;
        this.poApi = new GConnectApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    @Override
    public int DoAction(Object params) {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
