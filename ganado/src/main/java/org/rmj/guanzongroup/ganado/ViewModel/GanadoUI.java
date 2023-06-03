package org.rmj.guanzongroup.ganado.ViewModel;

import android.content.Intent;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EGanadoOnline;

import java.util.List;

public interface GanadoUI {
    void InitializeApplication(Intent params);
    LiveData<EGanadoOnline> GetApplication();
    void ParseData(EGanadoOnline args, OnParseListener listener);
    void Validate(Object args);
    void SaveData(OnSaveInfoListener listener);
}
