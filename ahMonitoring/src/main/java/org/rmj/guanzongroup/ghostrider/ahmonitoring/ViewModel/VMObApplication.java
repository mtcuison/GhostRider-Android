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

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.lib.PetManager.PetManager;
import org.rmj.g3appdriver.lib.PetManager.iPM;
import org.rmj.g3appdriver.lib.PetManager.model.OBApplication;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMObApplication extends AndroidViewModel {

    public static final String TAG = VMObApplication.class.getSimpleName();
    private final Application instance;
    private final RBranch pobranch;
    private final ConnectionUtil poConn;
    private final iPM poSys;
    private final LiveData<String[]> paBranchNm;

    public VMObApplication(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.pobranch = new RBranch(instance);
        this.poConn = new ConnectionUtil(instance);
        this.poSys = new PetManager(instance).GetInstance(PetManager.ePetManager.BUSINESS_TRIP_APPLICATION);
        paBranchNm = pobranch.getAllMcBranchNames();
    }
    public interface OnSubmitOBLeaveListener{
        void onSuccess();
        void onFailed(String message);
    }
    public LiveData<DEmployeeInfo.EmployeeBranch> getUserInfo(){
        return poSys.GetUserInfo();
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
    public void saveObLeave(OBApplication infoModel, OnSubmitOBLeaveListener callback){
        new PostObLeaveTask(instance, callback).execute(infoModel);
    }

    private class PostObLeaveTask extends AsyncTask<OBApplication, Void, Boolean> {
        private final OnSubmitOBLeaveListener callback;

        private String message;

        public PostObLeaveTask(Application instance, OnSubmitOBLeaveListener callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(OBApplication... obApplications) {
            OBApplication loApp = obApplications[0];
            try{
                String lsTransNox = poSys.SaveApplication(loApp);
                if(lsTransNox == null){
                    message = poSys.getMessage();
                    return false;
                }

                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage() + " Your leave application has been save to local.";
                    return false;
                }

                if(!poSys.UploadApplication(lsTransNox)){
                    message = poSys.getMessage();
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