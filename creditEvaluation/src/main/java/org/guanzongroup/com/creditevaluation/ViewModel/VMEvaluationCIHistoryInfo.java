/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 4/8/22, 11:35 AM
 * project file last modified : 4/8/22, 11:35 AM
 */

package org.guanzongroup.com.creditevaluation.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.ROccupation;
import org.rmj.g3appdriver.lib.integsys.CreditInvestigator.Obj.CITagging;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMEvaluationCIHistoryInfo extends AndroidViewModel {

    private final CITagging poSys;
    private final ROccupation poJob;
    private final ConnectionUtil poConn;

    public VMEvaluationCIHistoryInfo(@NonNull Application application) {
        super(application);
        this.poSys = new CITagging(application);
        this.poJob = new ROccupation(application);
        this.poConn = new ConnectionUtil(application);
    }

    public LiveData<List<EOccupationInfo>> GetOccupationList(){
        return poJob.getAllOccupationInfo();
    }

    public LiveData<ECreditOnlineApplicationCI> RetrieveApplicationData(String TransNox) {
        return poSys.RetrieveApplicationData(TransNox);
    }

    public void PostBHApproval(String fsTransNo, String fsResultx, String fsRemarks, OnTransactionCallBack foCallBck) {
        new PostBHApprovalTask(foCallBck).execute(fsTransNo, fsResultx, fsRemarks);
    }

    private class PostBHApprovalTask extends AsyncTask<String, Void, Boolean> {

        private final OnTransactionCallBack loCallBck;

        private String message;

        private PostBHApprovalTask(OnTransactionCallBack foCallBck) {
            this.loCallBck = foCallBck;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loCallBck.onLoad();
        }

        @Override
        protected Boolean doInBackground(String... args) {
            try {
                String TransNo = args[0];
                String Resultx = args[1];
                String Remarks = args[2];

                if(!poSys.SaveBHApproval(TransNo, Resultx, Remarks)){
                    message = poSys.getMessage();
                    return false;
                }

                if(!poConn.isDeviceConnected()) {
                    message = poConn.getMessage();
                    return false;
                }

                if(!poSys.PostBHApproval(TransNo)){
                    message = poSys.getMessage();
                    return false;
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess) {
                loCallBck.onFailed(message);
            } else {
                loCallBck.onSuccess("Your approval recommendation has been uploaded successfully.");
            }
        }

    }

    public interface OnTransactionCallBack {
        void onSuccess(String fsMessage);
        void onFailed(String fsMessage);
        void onLoad();
    }

}
