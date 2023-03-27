package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.BullsEye.obj.BranchPerformance;

public class VMHomeBH extends AndroidViewModel {

    private final EmployeeMaster poEmploye;
    private final BranchPerformance poSys;

    public VMHomeBH(@NonNull Application application) {
        super(application);
        poEmploye = new EmployeeMaster(application);
        this.poSys = new BranchPerformance(application);
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmploye.GetEmployeeInfo();
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