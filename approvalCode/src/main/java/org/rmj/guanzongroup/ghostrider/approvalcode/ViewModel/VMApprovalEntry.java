/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.approvalCode
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.approvalcode.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.lib.ApprovalCode.ApprovalCode;
import org.rmj.g3appdriver.lib.ApprovalCode.SCA;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.lib.ApprovalCode.model.AppCodeParams;

import java.util.List;

public class VMApprovalEntry extends AndroidViewModel {
    private static final String TAG = VMApprovalEntry.class.getSimpleName();
    private final SCA poSys;
    private final ConnectionUtil poConn;

    public interface OnGenerateApprovalCodeListener{
        void OnGenerate(String title, String message);
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public VMApprovalEntry(@NonNull Application application) {
        super(application);
        this.poSys = new ApprovalCode(application).getInstance(ApprovalCode.eSCA.SYSTEM_CODE);
        this.poConn = new ConnectionUtil(application);
    }

    public LiveData<String> GetApprovalCodeDescription(String AppCode){
        return poSys.GetApprovalCodeDescription(AppCode);
    }

    public LiveData<List<EBranchInfo>> GetAllBranchInfo(){
        return poSys.GetBranchList();
    }

    public void GenerateCode(AppCodeParams foVal, OnGenerateApprovalCodeListener listener){
        new GenerateCodeTask(listener).execute(foVal);
    }

    private class GenerateCodeTask extends AsyncTask<AppCodeParams, Void, String>{

        private final OnGenerateApprovalCodeListener listener;

        private String message;

        public GenerateCodeTask(OnGenerateApprovalCodeListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnGenerate("Approval Code", "Generating approval code. Please wait...");
        }

        @Override
        protected String doInBackground(AppCodeParams... params) {
            try{
                if(!params[0].isDataValid()){
                    message = params[0].getMessage();
                    return null;
                }

                String lsCode = poSys.GenerateCode(params[0]);

                if(lsCode == null){
                    message = poSys.getMessage();
                    return null;
                }

                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage();
                    Log.e(TAG, message);
                }

                if(!poSys.Upload(lsCode)){
                    message = poSys.getMessage();
                    Log.e(TAG, message);
                }

                return lsCode;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result == null){
                listener.OnFailed(message);
            } else {
                listener.OnSuccess(result);
            }
        }
    }
}
