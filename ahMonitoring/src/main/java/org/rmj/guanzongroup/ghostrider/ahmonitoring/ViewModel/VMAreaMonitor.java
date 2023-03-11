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

import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.lib.BullsEye.obj.AreaPerformance;

import java.util.List;

public class VMAreaMonitor extends AndroidViewModel {
    public static final String TAG = VMAreaMonitor.class.getSimpleName();

    private final RAreaPerformance poDatabse;
    private final AreaPerformance poSys;

    public VMAreaMonitor(@NonNull Application application) {
        super(application);
        poDatabse = new RAreaPerformance(application);
        this.poSys = new AreaPerformance(application);
    }

    public LiveData<List<EAreaPerformance>> getAreaPerformanceInfoList(){
        return poDatabse.getAreaPerformanceInfoList();
    }

    public LiveData<List<EAreaPerformance>> getTopBranchList(){
        return poDatabse.getAreaPerformanceInfoList();
    }
    public LiveData<List<EBranchPerformance>> GetTopBranchPerformerForJobOrder() {
        return poSys.GetTopBranchPerformerForJobOrder();
    }
    public LiveData<List<EBranchPerformance>> GetTopBranchPerformerForSPSales() {
        return poSys.GetTopBranchPerformerForSPSales();
    }
    public LiveData<List<EBranchPerformance>> GetTopBranchPerformerForMCSales() {
        return poSys.GetTopBranchPerformerForMCSales();
    }
    public LiveData<String> GetCurrentMCSalesPerformance(){
        return poSys.GetCurrentMCSalesPerformance();
    }

    public LiveData<String> GetCurentSPSalesPerformance() {
        return poSys.GetCurentSPSalesPerformance();
    }
    public LiveData<String> GetJobOrderPerformance() {

        return poSys.GetJobOrderPerformance();
    }
}