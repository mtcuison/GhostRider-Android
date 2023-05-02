package org.rmj.g3appdriver.lib.Account.gConnect;

import android.app.Application;

import org.rmj.g3appdriver.dev.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Device.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.Model.Auth;
import org.rmj.g3appdriver.lib.Account.Model.iAccount;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Account.gConnect.obj.ResetPassword;
import org.rmj.g3appdriver.lib.Account.gConnect.obj.SignIn;
import org.rmj.g3appdriver.lib.Account.gConnect.obj.SignUp;
import org.rmj.g3appdriver.lib.Account.gConnect.obj.UpdatePassword;

public class gConnectAuth implements iAccount {
    private static final String TAG = gConnectAuth.class.getSimpleName();

    private final Application instance;
    private final GConnectApi poApi;
    private final HttpHeaders poHeaders;
    private final AppConfigPreference poConfig;
    private final Telephony poDevID;

    private String message;

    public gConnectAuth(Application instance) {
        this.instance = instance;
        this.poApi = new GConnectApi(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poDevID = new Telephony(instance);
    }

    @Override
    public iAuth getInstance(Auth params) {
        switch (params){
            case AUTHENTICATE:
                return new SignIn(instance);
            case CREATE_ACCOUNT:
                return new SignUp();
            case CHANGE_PASSWORD:
                return new UpdatePassword();
            default:
                return new ResetPassword();
        }
    }
}
