package org.rmj.g3appdriver.lib.Account.gConnect.obj;

import android.app.Application;

import org.rmj.g3appdriver.lib.Account.Model.iAuth;

public class ResetPassword implements iAuth {
    private static final String TAG = ResetPassword.class.getSimpleName();

    private final Application instance;

    public ResetPassword(Application instance) {
        this.instance = instance;
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
