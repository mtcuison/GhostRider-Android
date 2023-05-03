package org.rmj.g3appdriver.lib.Account.gConnect;

import android.app.Application;
import android.util.Log;

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
                Log.d(TAG, "Initialize client sign in.");
                return new SignIn(instance);
            case CREATE_ACCOUNT:
                Log.d(TAG, "Initialize client sign up.");
                return new SignUp(instance);
            case CHANGE_PASSWORD:
                Log.d(TAG, "Initialize update password.");
                return new UpdatePassword(instance);
            default:
                Log.d(TAG, "Initialize reset password.");
                return new ResetPassword(instance);
        }
    }
}
