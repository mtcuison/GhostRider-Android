package org.guanzongroup.com.creditevaluation.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;

import java.util.List;

public class VMEvaluation extends AndroidViewModel {
    private final Application instance;
    private final MutableLiveData<String> sCredInvxx = new MutableLiveData<>();
    private final MutableLiveData<String> sTransNox = new MutableLiveData<>();
    private final SessionManager poSession;
    private final REmployee poEmploye;
    public VMEvaluation(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSession = new SessionManager(application);
        this.poEmploye = new REmployee(application);
    }
    public LiveData<EEmployeeInfo> getEmplopyeInfo(){
        return this.poEmploye.getEmployeeInfo();
    }
    public void setEmployeeID(String empID){
        this.sCredInvxx.setValue(empID);
    }

}
