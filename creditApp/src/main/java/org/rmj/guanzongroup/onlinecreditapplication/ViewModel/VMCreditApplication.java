/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

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
