package org.rmj.g3appdriver.lib.Account.gConnect;

import android.app.Application;

import org.rmj.g3appdriver.lib.Account.Model.Auth;
import org.rmj.g3appdriver.lib.Account.Model.iAccount;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;

public class gConnectAuth implements iAccount {
    private static final String TAG = gConnectAuth.class.getSimpleName();

    private final Application instance;

    public gConnectAuth(Application instance) {
        this.instance = instance;
    }

    @Override
    public iAuth getInstance(Auth params) {
        return null;
    }
}
