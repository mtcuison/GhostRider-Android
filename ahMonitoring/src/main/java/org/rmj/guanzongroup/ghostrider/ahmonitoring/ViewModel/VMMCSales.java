/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/14/21 11:00 AM
 * project file last modified : 6/14/21 10:57 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchPerformance;

import java.util.List;

public class VMMCSales extends AndroidViewModel {
    public static final String TAG = VMMCSales.class.getSimpleName();

    private final RBranchPerformance poDatabse;
    private final RAreaPerformance poArea;

    public VMMCSales(@NonNull Application application) {
        super(application);
        poDatabse = new RBranchPerformance(application);
        this.poArea = new RAreaPerformance(application);
    }

    public LiveData<List<EBranchPerformance>>  getAllBranchPerformanceInfoByBranch(String branchCD){
        return poDatabse.getAllBranchPerformanceInfoByBranch(branchCD);
    }

    public LiveData<List<EAreaPerformance>> getAreaPerformanceInfoList() {
        return poArea.getAreaPerformanceInfoList();
    }
}