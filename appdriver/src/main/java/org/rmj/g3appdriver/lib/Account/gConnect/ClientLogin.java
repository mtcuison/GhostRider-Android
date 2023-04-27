package org.rmj.g3appdriver.lib.Account.gConnect;

import android.app.Application;

import org.rmj.g3appdriver.lib.Account.Model.iAccount;

public class ClientLogin implements iAccount {
    private static final String TAG = ClientLogin.class.getSimpleName();

    private final Application instance;

    private String message;

    public ClientLogin(Application instance) {
        this.instance = instance;
    }

    @Override
    public boolean Login(Object args) {
        return false;
    }

    @Override
    public boolean CreateAccount(Object args) {
        return false;
    }

    @Override
    public boolean ForgotPassword(Object args) {
        return false;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
