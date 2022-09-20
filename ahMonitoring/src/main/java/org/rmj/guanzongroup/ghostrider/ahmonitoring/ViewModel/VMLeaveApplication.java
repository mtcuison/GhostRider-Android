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
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.LEAVE_TYPE;

public class VMLeaveApplication extends AndroidViewModel {

    private final Application instance;
    private final RBranch pobranch;
    private final REmployee poUser;

    public VMLeaveApplication(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.pobranch = new RBranch(instance);
        this.poUser = new REmployee(instance);
    }

    public interface LeaveApplicationCallback {
        void OnSave(String Title, String message);
        void OnSuccess(String message);
        void OnFailed(String message);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }

    public LiveData<ArrayAdapter<String>> getLeaveTypeList(){
        MutableLiveData<ArrayAdapter<String>> loList = new MutableLiveData<>();
        loList.setValue(new ArrayAdapter<>(instance, android.R.layout.simple_dropdown_item_1line, LEAVE_TYPE));
        return loList;
    }

    public void SaveApplication(REmployeeLeave.LeaveApplication application, LeaveApplicationCallback callback){
        new SaveLeaveApplication(instance, callback).execute(application);
    }

    private static class SaveLeaveApplication extends AsyncTask<REmployeeLeave.LeaveApplication, Void, Boolean>{
        private final LeaveApplicationCallback callback;

        private final ConnectionUtil poConn;
        private final REmployeeLeave poLeave;

        private String message;

        public SaveLeaveApplication(Application instance, LeaveApplicationCallback callback) {
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poLeave = new REmployeeLeave(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnSave("Pet Manager", "Saving your leave application. Please wait...");
        }

        @Override
        protected Boolean doInBackground(REmployeeLeave.LeaveApplication... leaveApplications) {
            REmployeeLeave.LeaveApplication loLeave = leaveApplications[0];
            try {
                String lsTransNo = poLeave.SaveLeaveApplication(loLeave);
                if (lsTransNo == null) {
                    message = poLeave.getMessage();
                    return false;
                }

                if (!poConn.isDeviceConnected()) {
                    message = poConn.getMessage();
                    return true;
                }

                if(!poLeave.UploadLeaveApplication(poLeave.getTransnox())) {
                    message = poLeave.getMessage();
                    return true;
                } else {
                    message = poLeave.getMessage();
                    return false;
                }

            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                callback.OnSuccess(message);
            } else {
                callback.OnFailed(message);
            }
        }
    }
}