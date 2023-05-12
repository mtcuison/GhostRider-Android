/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 3/11/22, 11:19 AM
 * project file last modified : 3/11/22, 11:19 AM
 */

package org.guanzongroup.com.creditevaluation.ViewModel;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditOnlineApplicationCI;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditInvestigator.Obj.CITagging;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMEvaluationHistory extends AndroidViewModel {
    private static final String TAG = VMEvaluationHistory.class.getSimpleName();

    private final CITagging poSys;
    private final ConnectionUtil poConn;

    public VMEvaluationHistory(@NonNull Application application) {
        super(application);
        this.poConn = new ConnectionUtil(application);
        this.poSys = new CITagging(application);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poSys.GetUserInfo();
    }

    public LiveData<List<DCreditOnlineApplicationCI.oDataEvaluationInfo>> getForEvaluationListDataPreview() {
        return poSys.GetForEvaluationListDataPreview();
    }

    public void DownloadApplicationsForBHApproval(OnTransactionCallback callback) {
        new DownloadApplicationsForBHApproval(callback).execute();
    }

    private class DownloadApplicationsForBHApproval extends AsyncTask<Void, Void, Boolean> {

        private final OnTransactionCallback loCallBck;

        private String message;

        private DownloadApplicationsForBHApproval(OnTransactionCallback foCallBck) {
            this.loCallBck = foCallBck;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loCallBck.onLoad();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if(!poConn.isDeviceConnected()) {
                    message = poConn.getMessage();
                    return false;
                }

                if(!poSys.DownloadApplicationsForBHApproval()){
                    message = poSys.getMessage();
                    return false;
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                message = getLocalMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess) {
                loCallBck.onFailed(message);
            } else {
                loCallBck.onSuccess("");
            }
        }

    }

    public interface OnTransactionCallback {
        void onSuccess(String message);
        void onFailed(String message);
        void onLoad();
    }

}
