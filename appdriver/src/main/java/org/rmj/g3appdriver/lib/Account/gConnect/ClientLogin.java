package org.rmj.g3appdriver.lib.Account.gConnect;

import android.app.Application;

import org.rmj.g3appdriver.lib.Account.Model.Auth;
import org.rmj.g3appdriver.lib.Account.Model.iAccount;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;

public class ClientLogin implements iAccount {
    private static final String TAG = ClientLogin.class.getSimpleName();

    private final Application instance;

    private String message;

    public ClientLogin(Application instance) {
        this.instance = instance;
    }

    @Override
    public iAuth getInstance(Auth params) {
        return null;
    }
}
