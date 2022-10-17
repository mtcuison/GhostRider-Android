package org.rmj.g3appdriver.lib.integsys.CreditApp;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;

public interface CreditApp {
    LiveData<ECreditApplication> GetApplication();
    Object Parse(ECreditApplication args);
    int Validate();
    boolean Save();
    String getMessage();
}
