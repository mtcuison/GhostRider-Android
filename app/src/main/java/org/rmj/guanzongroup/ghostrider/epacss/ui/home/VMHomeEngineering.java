package org.rmj.guanzongroup.ghostrider.epacss.ui.home;

import android.app.Application;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchOpeningMonitor;
import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.dev.Device.Telephony;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.Account.SessionManager;
import org.rmj.g3appdriver.lib.Notifications.Obj.BranchOpeningMonitor;

import java.util.List;

public class VMHomeEngineering extends AndroidViewModel {
    private final SessionManager poSession;
    private final EmployeeMaster poEmploye;

    private MutableLiveData<String> psEmailxx = new MutableLiveData<>();
    private MutableLiveData<String> psUserNme = new MutableLiveData<>();
    private MutableLiveData<String> psBranchx = new MutableLiveData<>();
    private MutableLiveData<String> psDeptNme = new MutableLiveData<>();
    private MutableLiveData<String> psMobleNo = new MutableLiveData<>();
    private final MutableLiveData<Integer> cv_ahMonitoring = new MutableLiveData<>();
    private final MutableLiveData<Integer> userLvl = new MutableLiveData<>();
//    private MutableLiveData<String> psMobleNo = new MutableLiveData<>();
//    private final MutableLiveData<Integer> cv_ahMonitoring = new MutableLiveData<>();
//    private final MutableLiveData<Integer> userLvl = new MutableLiveData<>();
    private final RAreaPerformance poDatabse;
    private final RNotificationInfo poNotification;
    private final BranchOpeningMonitor poOpening;
    private final RBranch poBrInfo;
    private final RBranchPerformance poBranch;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public VMHomeEngineering(@NonNull Application application) {
        super(application);
        poSession = new SessionManager(application);
        poEmploye = new EmployeeMaster(application);
        psMobleNo.setValue(new Telephony(application).getMobilNumbers());
        poDatabse = new RAreaPerformance(application);
        this.poNotification = new RNotificationInfo(application);
        this.cv_ahMonitoring.setValue(View.GONE);
        this.poOpening = new BranchOpeningMonitor(application);
        this.poBrInfo = new RBranch(application);
        this.poBranch = new RBranchPerformance(application);
    }
    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmploye.GetEmployeeInfo();
    }

    public LiveData<String> getMobileNo() {
        return psMobleNo;
    }

    public LiveData<List<EAreaPerformance>> getAreaPerformanceInfoList(){
        return poDatabse.getAreaPerformanceInfoList();
    }

    public LiveData<String> getUserAreaCodeForDashboard(){
        return poEmploye.getUserAreaCodeForDashboard();
    }

    public LiveData<List<EBranchPerformance>> getBranchPerformance(){
        return poBranch.getBranchPerformanceForDashBoard();
    }

    public LiveData<List<EAreaPerformance>> getAreaPerformanceDashboard(){
        return poDatabse.getAreaPerformanceDashboard();
    }
    // TODO: Implement the ViewModel
    public LiveData<EBranchInfo> GetUserBranchInfo(){
        return poBrInfo.getUserBranchInfo();
    }

    public void setIntUserLvl(int userLvl){
        try {
            if(userLvl == 4){
                this.cv_ahMonitoring.setValue(View.VISIBLE);
            } else {
                this.cv_ahMonitoring.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.userLvl.setValue(userLvl);
    }
    public LiveData<Integer> getIntUserLvl(){
        return this.userLvl;
    }
    public LiveData<Integer> getCv_ahMonitoring(){
        return cv_ahMonitoring;
    }

    public LiveData<Integer> getUnreadMessagesCount(){
        return poNotification.getUnreadMessagesCount();
    }

    public LiveData<Integer> getUnreadNotificationsCount(){
        return poNotification.getUnreadNotificationsCount();
    }

    public LiveData<List<DBranchOpeningMonitor.BranchOpeningInfo>> getBranchOpeningInfoForDashBoard(){
        return poOpening.getBranchOpeningInfoForDashBoard(AppConstants.CURRENT_DATE);
    }

    public LiveData<String> getBranchAreaCode(String fsBranchCd) {
        return poBrInfo.getBranchAreaCode(fsBranchCd);
    }
}