package org.rmj.g3appdriver.lib.Account.Model;

import android.app.Application;

import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.gCircle.gCircleAuth;
import org.rmj.g3appdriver.lib.Account.gConnect.gConnectAuth;

public class AccountMaster {
    private static final String TAG = AccountMaster.class.getSimpleName();

    private final Application instance;
    private final AppConfigPreference poConfig;

    public AccountMaster(Application instance) {
        this.instance = instance;
        this.poConfig = AppConfigPreference.getInstance(instance);
    }

    public iAccount getInstance(){
        String lsProdctID = poConfig.ProducID();
        if ("gRider".equals(lsProdctID)) {
            return new gCircleAuth(instance);
        }
        return new gConnectAuth(instance);
    }
}
