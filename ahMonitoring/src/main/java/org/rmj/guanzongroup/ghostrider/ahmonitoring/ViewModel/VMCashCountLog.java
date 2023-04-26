/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/10/21 1:40 PM
 * project file last modified : 6/10/21 1:40 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DCashCount;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECashCount;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RCashCount;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RequestNamesInfoModel;

import java.util.ArrayList;
import java.util.List;

public class VMCashCountLog extends AndroidViewModel {
    private static final String TAG = VMCashCountLog.class.getSimpleName();

    private final Application instance;
    private final EmployeeMaster poEmploye;
    private final RBranch poBranch;
    private final ECashCount eCashCount;
    private final RCashCount poCashCount;
    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final List<RequestNamesInfoModel> infoList;

    public VMCashCountLog(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poEmploye = new EmployeeMaster(application);
        this.poBranch = new RBranch(application);
        this.poCashCount = new RCashCount(application);
        this.eCashCount = new ECashCount();
        this.infoList = new ArrayList<>();
    }
    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }


    public LiveData<List<DCashCount.CashCountLog>> getCashCountLog(){
        return poCashCount.getCashCountLog();
    }
}
