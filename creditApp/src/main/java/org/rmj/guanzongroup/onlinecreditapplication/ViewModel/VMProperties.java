package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;

public class VMProperties extends AndroidViewModel implements CreditAppUI {

    public VMProperties(@NonNull Application application) {
        super(application);
    }

    @Override
    public void InitializeApplication(Intent params) {

    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication() {
        return null;
    }

    @Override
    public void ParseData(ECreditApplicantInfo args, OnParseListener listener) {

    }

    @Override
    public void Validate(Object args) {

    }

    @Override
    public void SaveData(OnSaveInfoListener listener) {

    }
}
