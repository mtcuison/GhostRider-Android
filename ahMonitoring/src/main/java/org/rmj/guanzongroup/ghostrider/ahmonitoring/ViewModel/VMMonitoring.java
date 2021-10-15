/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/15/21, 10:56 AM
 * project file last modified : 10/15/21, 10:56 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;

public class VMMonitoring extends AndroidViewModel {
    private static final String TAG = VMMonitoring.class.getSimpleName();

    private final REmployee poUser;
    private final RAreaPerformance poAreaPrfm;
    private final RBranchPerformance poBranchPrfm;

    public VMMonitoring(@NonNull Application application) {
        super(application);
        this.poUser = new REmployee(application);
        this.poBranchPrfm = new RBranchPerformance(application);
        this.poAreaPrfm = new RAreaPerformance(application);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public LiveData<String> getAreaDesciption(){
        return poAreaPrfm.getAreaDescription();
    }
}
