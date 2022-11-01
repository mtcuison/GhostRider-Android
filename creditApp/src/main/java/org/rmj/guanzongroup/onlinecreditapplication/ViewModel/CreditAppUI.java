package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.content.Intent;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;

import java.util.List;

public interface CreditAppUI {
    void InitializeApplication(Intent params);
    LiveData<ECreditApplicantInfo> GetApplication();
    void ParseData(ECreditApplicantInfo args, OnParseListener listener);
    void Validate(Object args);
    void SaveData(Object args, OnSaveInfoListener listener);
}
