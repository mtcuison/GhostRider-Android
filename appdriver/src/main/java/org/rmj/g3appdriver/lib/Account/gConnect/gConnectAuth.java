package org.rmj.g3appdriver.lib.Account.gConnect;

import android.app.Application;

import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
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

    public gConnectAuth(Application instance) {
        this.instance = instance;
    }

    @Override
    public iAuth getInstance(Auth params) {
        switch (params){
            case AUTHENTICATE:
                return new SignIn(instance);
            case CREATE_ACCOUNT:
                return new SignUp(instance);
            case CHANGE_PASSWORD:
                return new UpdatePassword(instance);
            default:
                return new ResetPassword(instance);
        }
    }
}
