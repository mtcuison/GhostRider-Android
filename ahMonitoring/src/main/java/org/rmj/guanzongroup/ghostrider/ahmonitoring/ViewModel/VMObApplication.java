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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.PetManager.EmployeeOB;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMObApplication extends AndroidViewModel {

    public static final String TAG = VMObApplication.class.getSimpleName();
    private final Application instance;
    private final RBranch pobranch;
    private final EmployeeMaster poUser;
    private final LiveData<String[]> paBranchNm;
    public VMObApplication(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.pobranch = new RBranch(instance);
        this.poUser = new EmployeeMaster(instance);
        paBranchNm = pobranch.getAllMcBranchNames();
    }
    public interface OnSubmitOBLeaveListener{
        void onSuccess();
        void onFailed(String message);
    }
    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }
    public LiveData<String[]> getAllBranchNames(){
        return paBranchNm;
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return pobranch.getAllMcBranchInfo();
    }
    public void saveObLeave(EmployeeOB.OBApplication infoModel, OnSubmitOBLeaveListener callback){
        new PostObLeaveTask(instance, callback).execute(infoModel);
    }

    private static class PostObLeaveTask extends AsyncTask<EmployeeOB.OBApplication, Void, Boolean> {
        private final OnSubmitOBLeaveListener callback;
        private final EmployeeOB poOBLeave;
        private final ConnectionUtil poConn;

        private String message;

        public PostObLeaveTask(Application instance, OnSubmitOBLeaveListener callback) {
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poOBLeave = new EmployeeOB(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(EmployeeOB.OBApplication... obApplications) {
            EmployeeOB.OBApplication loApp = obApplications[0];
            try{
                String lsTransNox = poOBLeave.SaveOBApplication(loApp);
                if(lsTransNox == null){
                    message = poOBLeave.getMessage();
                    return false;
                }

                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage() + " Your leave application has been save to local.";
                    return false;
                }

                if(!poOBLeave.UploadApplication(lsTransNox)){
                    message = poOBLeave.getMessage();
                    return false;
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
            if(isSuccess){
                callback.onSuccess();
            } else {
                callback.onFailed(message);
            }
        }
    }
}