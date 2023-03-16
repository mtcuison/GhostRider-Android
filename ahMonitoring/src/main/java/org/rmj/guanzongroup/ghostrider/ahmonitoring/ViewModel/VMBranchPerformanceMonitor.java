package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.lib.BullsEye.obj.BranchPerformance;

import java.util.List;

public class VMBranchPerformanceMonitor extends AndroidViewModel {
    private static final String TAG = VMBranchPerformanceMonitor.class.getSimpleName();

    private final BranchPerformance poSys;

    public VMBranchPerformanceMonitor(@NonNull Application application) {
        super(application);
        this.poSys = new BranchPerformance(application);
    }

    public LiveData<String> GetCurrentMCSalesPerformance(String args) {
        return poSys.GetCurrentMCSalesPerformance(args);
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> GetMCSalesPeriodicPerformance(String BranchCd){
        return poSys.GetMCSalesPeriodicPerformance(BranchCd);
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> GetSPSalesPeriodicPerformance(String BranchCd){
        return poSys.GetSPSalesPeriodicPerformance(BranchCd);
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> GetJobOrderPeriodicPerformance(String BranchCd){
        return poSys.GetJobOrderPeriodicPerformance(BranchCd);
    }

}
