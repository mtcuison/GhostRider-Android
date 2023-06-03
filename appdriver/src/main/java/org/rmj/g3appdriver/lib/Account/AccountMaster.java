package org.rmj.g3appdriver.lib.Account;

import android.app.Application;
import android.util.Log;

import org.rmj.g3appdriver.lib.Account.Model.iAccount;
import org.rmj.g3appdriver.lib.Account.gCircle.gCircleAuth;
import org.rmj.g3appdriver.lib.Account.gConnect.gConnectAuth;
import org.rmj.g3appdriver.etc.AppConfigPreference;

public class AccountMaster {
    private static final String TAG = AccountMaster.class.getSimpleName();

    private final Application instance;
    private final AppConfigPreference poConfig;

    public AccountMaster(Application instance) {
        this.instance = instance;
        this.poConfig = AppConfigPreference.getInstance(instance);
    }

    public iAccount initGuanzonApp(){
        String lsProdctID = poConfig.ProducID();
        if ("gRider".equals(lsProdctID)) {
            Log.d(TAG, "Initialize employee authentication");
            return new gCircleAuth(instance);
        }
        Log.d(TAG, "Initialize client account");
        return new gConnectAuth(instance);
    }
}
