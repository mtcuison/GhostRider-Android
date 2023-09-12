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

package org.rmj.guanzongroup.petmanager.ViewModel;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Apps.PetManager.Obj.EmployeeLeave;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.pojo.LeaveApplication;
import org.rmj.g3appdriver.lib.Branch.Branch;
import org.rmj.g3appdriver.utils.ConnectionUtil;


public class VMLeaveApplication extends AndroidViewModel {

    private final Application instance;
    private final Branch pobranch;
    private final EmployeeLeave poSys;
    private final ConnectionUtil poConn;

    public VMLeaveApplication(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.pobranch = new Branch(instance);
        this.poSys = new EmployeeLeave(instance);
        this.poConn = new ConnectionUtil(instance);
    }

    public interface LeaveApplicationCallback {
        void OnSave(String Title, String message);
        void OnSuccess(String message);
        void OnFailed(String message);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poSys.GetUserInfo();
    }

    public void SaveApplication(LeaveApplication application, LeaveApplicationCallback callback){
        new SaveLeaveApplication(callback).execute(application);
    }

    private class SaveLeaveApplication extends AsyncTask<LeaveApplication, Void, Boolean>{
        private final LeaveApplicationCallback callback;

        private String message;

        public SaveLeaveApplication(LeaveApplicationCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnSave("Pet Manager", "Saving your leave application. Please wait...");
        }

        @Override
        protected Boolean doInBackground(LeaveApplication... leaveApplications) {
            LeaveApplication loLeave = leaveApplications[0];
            try {
                String lsTransNo = poSys.SaveApplication(loLeave);
                if (lsTransNo == null) {
                    message = poSys.getMessage();
                    return false;
                }

                if (!poConn.isDeviceConnected()) {
                    message = poConn.getMessage();
                    return true;
                }

                if(!poSys.UploadApplication(lsTransNo)) {
                    message = poSys.getMessage();
                    return false;
                }

                message = poSys.getMessage();
                return true;

            } catch (Exception e){
                e.printStackTrace();
                message = getLocalMessage(e);
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