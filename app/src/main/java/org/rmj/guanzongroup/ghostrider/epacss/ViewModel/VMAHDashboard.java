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

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.SelfieLog.SelfieLog;

import java.util.List;

public class VMAHDashboard extends AndroidViewModel {
    private static final String TAG =  VMAHDashboard.class.getSimpleName();

    private final EmployeeMaster poEmployee;
    private final RBranch pobranch;
    private final SelfieLog poLog;
    private final AppConfigPreference poConfigx;

    private final MutableLiveData<String> psVersion = new MutableLiveData<>();

    public VMAHDashboard(@NonNull Application application) {
        super(application);
        this.poEmployee = new EmployeeMaster(application);
        this.pobranch = new RBranch(application);
        poLog = new SelfieLog(application);
        this.poConfigx = AppConfigPreference.getInstance(application);
        this.psVersion.setValue(poConfigx.getVersionInfo());
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmployee.GetEmployeeInfo();
    }

    public LiveData<String> getVersionInfo(){
        return psVersion;
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }

    public LiveData<List<ESelfieLog>> getCurrentLogTimeIfExist(){
        return poLog.getCurrentLogTimeIfExist(new AppConstants().CURRENT_DATE);
    }
}