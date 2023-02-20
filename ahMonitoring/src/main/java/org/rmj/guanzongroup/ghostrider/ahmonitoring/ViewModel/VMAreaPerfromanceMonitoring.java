/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/14/21, 10:48 AM
 * project file last modified : 10/14/21, 10:48 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranchPerformance;

import java.util.List;

public class VMAreaPerfromanceMonitoring extends AndroidViewModel {
    private static final String TAG = VMAreaPerfromanceMonitoring.class.getSimpleName();
    private final RAreaPerformance poArea;
    private final RBranchPerformance poBranch;
    private final MutableLiveData<String> psType = new MutableLiveData<>();

    public VMAreaPerfromanceMonitoring(@NonNull Application application) {
        super(application);
        Log.e(TAG, "Initialized.");
        this.poArea = new RAreaPerformance(application);
        this.poBranch = new RBranchPerformance(application);
        psType.setValue("MC");
    }

    public LiveData<String> getAreaNameFromCode() {
        return new MutableLiveData<>();
    }

    public LiveData<List<EAreaPerformance>> getAreaPerformanceInfoList() {
        return poArea.getAreaPerformanceInfoList();
    }

    public LiveData<List<EBranchPerformance>> getAreaBranchesSalesPerformance(String fsPeriodx, String fsType) {
        switch(fsType) {
            case "MC": return poBranch.getAreaBranchesMCSalesPerformance(fsPeriodx);
            case "SP": return poBranch.getAreaBranchesSPSalesPerformance(fsPeriodx);
            default: return null;
        }
    }

    public LiveData<DBranchPerformance.MonthlyPieChart> getMonthlyPieChartData(String fsPeriodx) {
        return poBranch.getMonthlyPieChartData(fsPeriodx);
    }

    public LiveData<DBranchPerformance.MonthlyPieChart> get12MonthPieChartData(String fsValue1, String fsValue2) {
        return poBranch.get12MonthPieChartData(fsValue1, fsValue2);
    }

    public void setType(String value){
        psType.setValue(value);
    }

    public LiveData<String> getType(){
        return psType;
    }

}
