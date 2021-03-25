package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;

public class VMCreditApplication extends AndroidViewModel {
    private static final String TAG = VMCreditApplication.class.getSimpleName();

    private final RCreditApplicant poCreditApp;

    public VMCreditApplication(@NonNull Application application) {
        super(application);
        this.poCreditApp = new RCreditApplicant(application);
    }

    public LiveData<ECreditApplicantInfo> getCurrentApplicantInfo(String TransNox){
         return poCreditApp.getCreditApplicantInfoLiveData(TransNox);
    }

    public void updateGOCasApplication(GOCASApplication gocas, String TransNox){
        poCreditApp.updateCurrentGOCasData(gocas.toJSONString(), TransNox);
    }
}
