package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.gocas.base.GOCASApplication;

public class PersonalInfo implements CreditApp {
    private static final String TAG = PersonalInfo.class.getSimpleName();

    private final DCreditApplication poDao;

    private final GOCASApplication poGocas;

    private String message;

    public PersonalInfo(Application instance){
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poGocas = new GOCASApplication();
    }

    @Override
    public LiveData<ECreditApplication> GetApplication() {
        return null;
    }

    @Override
    public Object Parse(ECreditApplication args) {
        return null;
    }

    @Override
    public int Validate() {
        return 0;
    }

    @Override
    public boolean Save() {
        return false;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
