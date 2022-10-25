package org.rmj.g3appdriver.lib.integsys.CreditApp.ViewModel;

import android.content.Intent;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.OnSaveInfoListener;

public interface CreditAppUI {
    void InitializeApplication(Intent params);
    LiveData<ECreditApplicantInfo> GetApplication();
    void ParseData(ECreditApplicantInfo args, OnParseListener listener);
    void SaveData(Object args, OnSaveInfoListener listener);
}
