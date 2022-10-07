package org.rmj.guanzongroup.ghostrider.epacss.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranchPerformance;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;

import java.util.List;

public class VMBHDashboard extends AndroidViewModel {

    private final RBranchPerformance poPerFormance;
    private final RBranch poBranch;
    private final EmployeeMaster poUser;

    private final MutableLiveData<String> psSales = new MutableLiveData<>();

    public static final String MC_SALES = "MC";
    public static final String SP_SALES = "SP";

    public VMBHDashboard(@NonNull Application application) {
        super(application);
        psSales.setValue("MC");
        this.poPerFormance = new RBranchPerformance(application);
        this.poBranch = new RBranch(application);
        this.poUser = new EmployeeMaster(application);
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public void setSalesType(String type){
        this.psSales.setValue(type);
    }

    public LiveData<String> getSales(){
        return psSales;
    }

    public LiveData<EBranchPerformance> getCurrentPeriodPerformance(){
        return poPerFormance.getCurrentPeriodPerformance();
    }

    public LiveData<DBranchPerformance.PeriodRange> getPeriodRange(){
        return poPerFormance.getPeriodRange();
    }

    public LiveData<DBranchPerformance.ActualGoal> getMCBranchPerformance(){
        return poPerFormance.getMCBranchPerformance();
    }
    public LiveData<DBranchPerformance.ActualGoal> getSPBranchPerformance(){
        return poPerFormance.getSPBranchPerformance();
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> getMCBranchPeriodicalPerformance(){
        return poPerFormance.getMCBranchPeriodicalPerformance();
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> getSPBranchPeriodicalPerformance(){
        return poPerFormance.getSPBranchPeriodicalPerformance();
    }

}