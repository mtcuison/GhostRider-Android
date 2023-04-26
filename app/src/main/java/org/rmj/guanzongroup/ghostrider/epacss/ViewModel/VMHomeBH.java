package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ERaffleStatus;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.BullsEye.obj.BranchPerformance;
import org.rmj.g3appdriver.lib.Notifications.Obj.Notification;
import org.rmj.g3appdriver.lib.Panalo.Obj.ILOVEMYJOB;
import org.rmj.g3appdriver.lib.PetManager.OnCheckEmployeeApplicationListener;
import org.rmj.g3appdriver.lib.PetManager.PetManager;
import org.rmj.g3appdriver.lib.PetManager.model.iPM;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMHomeBH extends AndroidViewModel {
    private static final String TAG = VMHomeAH.class.getSimpleName();
    private final EmployeeMaster poEmploye;
    private final BranchPerformance poSys;
    private final Notification poNotification;
    private final Application instance;
    private final ConnectionUtil poConn;
    private iPM poApp;
    private final ILOVEMYJOB poPanalo;
    public VMHomeBH(@NonNull Application application) {
        super(application);
        poEmploye = new EmployeeMaster(application);
        this.poSys = new BranchPerformance(application);
        this.poNotification = new Notification(application);
        this.poPanalo = new ILOVEMYJOB(application);
        this.poConn = new ConnectionUtil(application);
        this.instance = application;
    }
    public LiveData<Integer> GetAllUnreadNotificationCount(){
        return poNotification.GetAllUnreadNotificationCount();
    }
    public LiveData<List<EEmployeeLeave>> GetLeaveForApproval(){
        this.poApp = new PetManager(instance).GetInstance(PetManager.ePetManager.LEAVE_APPLICATION);
        return poApp.GetLeaveApplicationsForApproval();
    }
    public LiveData<List<EEmployeeBusinessTrip>> GetOBForApproval(){
        this.poApp = new PetManager(instance).GetInstance(PetManager.ePetManager.BUSINESS_TRIP_APPLICATION);
        return poApp.GetOBApplicationsForApproval();
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
    public LiveData<ERaffleStatus> GetRaffleStatus(){
        return poPanalo.GetRaffleStatus();
    }
    public LiveData<String> GetJobOrderPerformance() {
        return poSys.GetJobOrderPerformance();
    }
    public void CheckApplicationsForApproval(OnCheckEmployeeApplicationListener listener){
        new VMHomeBH.CheckApplicationForApprovalTask(listener).execute();
    }

    private class CheckApplicationForApprovalTask extends AsyncTask<Void, Void, Boolean> {

        private final OnCheckEmployeeApplicationListener mListener;

        private String message;

        public CheckApplicationForApprovalTask(OnCheckEmployeeApplicationListener mListener) {
            this.mListener = mListener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage();
                    return false;
                }

                poApp = new PetManager(instance).GetInstance(PetManager.ePetManager.LEAVE_APPLICATION);
                if(!poApp.ImportApplications()){
                    Log.e(TAG, poApp.getMessage());
                }

                Thread.sleep(1000);

                poApp = new PetManager(instance).GetInstance(PetManager.ePetManager.BUSINESS_TRIP_APPLICATION);
                if(!poApp.ImportApplications()){
                    Log.e(TAG, poApp.getMessage());
                }

                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                mListener.OnFailed(message);
            } else {
                mListener.OnSuccess();
            }
        }
    }
}