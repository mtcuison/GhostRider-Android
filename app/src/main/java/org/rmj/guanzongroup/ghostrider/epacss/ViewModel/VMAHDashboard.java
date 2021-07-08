/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/30/21 11:47 AM
 * project file last modified : 4/24/21 3:19 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.etc.AppConfigPreference;

import java.util.List;

public class VMAHDashboard extends AndroidViewModel {
    private static final String TAG =  VMAHDashboard.class.getSimpleName();

    private final REmployee poEmployee;
    private final RBranch pobranch;
    private final RLogSelfie poLog;
    private final AppConfigPreference poConfigx;

    private final MutableLiveData<String> psVersion = new MutableLiveData<>();

    public VMAHDashboard(@NonNull Application application) {
        super(application);
        this.poEmployee = new REmployee(application);
        this.pobranch = new RBranch(application);
        poLog = new RLogSelfie(application);
        this.poConfigx = AppConfigPreference.getInstance(application);
        this.psVersion.setValue(poConfigx.getVersionName() + poConfigx.getVersionCode() +" - "+ poConfigx.getDateRelease());
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmployee.getEmployeeInfo();
    }

    public LiveData<String> getVersionInfo(){
        return psVersion;
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }

    public LiveData<List<ELog_Selfie>> getCurrentLogTimeIfExist(){
        return poLog.getCurrentLogTimeIfExist(new AppConstants().CURRENT_DATE);
    }
}