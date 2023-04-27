package org.rmj.g3appdriver.lib.Account.gCircle;

import android.app.Application;

import org.rmj.g3appdriver.lib.Account.Model.iAccount;

public class EmployeeAuthentication implements iAccount {
    private static final String TAG = EmployeeAuthentication.class.getSimpleName();

    private final Application instance;

    private String message;

    public EmployeeAuthentication(Application instance) {
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
