package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.content.Intent;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;

public interface CreditAppUI {
    void InitializeApplication(Intent params);
    LiveData<ECreditApplicantInfo> GetApplication();
    void ParseData(ECreditApplicantInfo args, OnParseListener listener);
    void Validate(Object args);
    void SaveData(OnSaveInfoListener listener);
}
