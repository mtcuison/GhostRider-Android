/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchPerformance;

import java.util.List;

public class VMBranchMonitor extends AndroidViewModel {
    public static final String TAG = VMBranchMonitor.class.getSimpleName();

    private final RBranchPerformance poDatabse;
    private final RBranch poBranch;
    private final MutableLiveData<String> psType = new MutableLiveData<>();

    public VMBranchMonitor(@NonNull Application application) {
        super(application);
        poDatabse = new RBranchPerformance(application);
        poBranch = new RBranch(application);
        psType.setValue("MC");
    }

    public LiveData<List<EBranchPerformance>>  getAllBranchPerformanceInfoByBranch(String branchCD){
        return poDatabse.getAllBranchPerformanceInfoByBranch(branchCD);
    }
    public LiveData<String> getBranchName(String brnCD){
        return poBranch.getBranchName(brnCD);
    }

    public void setType(String value){
        psType.setValue(value);
    }

    public LiveData<String> getType(){
        return psType;
    }

}