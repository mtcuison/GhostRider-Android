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

import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.lib.BullsEye.obj.AreaPerformance;

import java.util.List;

public class VMAreaPerfromanceMonitoring extends AndroidViewModel {
    private static final String TAG = VMAreaPerfromanceMonitoring.class.getSimpleName();
    private final AreaPerformance poSys;
    private final MutableLiveData<String> psType = new MutableLiveData<>();

    public VMAreaPerfromanceMonitoring(@NonNull Application application) {
        super(application);
        Log.e(TAG, "Initialized.");
        this.poSys = new AreaPerformance(application);
        psType.setValue("MC");
    }

    public LiveData<String> getAreaNameFromCode() {
        return new MutableLiveData<>();
    }


    public void setType(String value){
        psType.setValue(value);
    }

    public LiveData<String> getType(){
        return psType;
    }

    public LiveData<List<DAreaPerformance.BranchPerformance>> GetMCSalesBranchesPerformance(){
        return poSys.GetMCSalesBranchesPerformance();
    }

    public LiveData<List<DAreaPerformance.BranchPerformance>> GetSPSalesBranchesPerformance(){
        return poSys.GetSPSalesBranchesPerformance();
    }

    public LiveData<List<DAreaPerformance.BranchPerformance>> GetJobOrderBranchesPerformance(){
        return poSys.GetJobOrderBranchesPerformance();
    }
    public LiveData<String> GetCurrentMCSalesPerformance() {
        return poSys.GetCurrentMCSalesPerformance();
    }
    public LiveData<String> GetCurentSPSalesPerformance() {
        return poSys.GetCurentSPSalesPerformance();
    }
    public LiveData<String> GetJobOrderPerformance() {
        return poSys.GetJobOrderPerformance();
    }
    public LiveData<List<DAreaPerformance.PeriodicPerformance>>GetMCSalesPeriodicPerformance(){
        return poSys.GetMCSalesPeriodicPerformance();
    }

    public LiveData<List<DAreaPerformance.PeriodicPerformance>>GetSPSalesPeriodicPerformance(){
        return poSys.GetSPSalesPeriodicPerformance();
    }

    public LiveData<List<DAreaPerformance.PeriodicPerformance>>GetJobOrderPeriodicPerformance(){
        return poSys.GetJobOrderPeriodicPerformance();
    }
    public LiveData<String> getAreaDescription(){
        return poSys.getAreaDescription();
    }

}
