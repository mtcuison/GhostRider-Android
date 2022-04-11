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

import org.guanzongroup.com.creditevaluation.Core.EvaluatorManager;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMEvaluationCIHistoryInfo extends AndroidViewModel {

    private final ConnectionUtil poConnect;
    private final EvaluatorManager poHistory;

    public VMEvaluationCIHistoryInfo(@NonNull Application application) {
        super(application);
        this.poConnect = new ConnectionUtil(application);
        this.poHistory = new EvaluatorManager(application);
    }

    public LiveData<List<ECreditOnlineApplicationCI>> getForPreviewResultList() {
        return poHistory.getForPreviewResultList();
    }

    public LiveData<ECreditOnlineApplicationCI> RetrieveApplicationData(String TransNox) {
        return poHistory.RetrieveApplicationData(TransNox);
    }

    public void PostBHApproval(String fsTransNo, String fsResultx, String fsRemarks, OnTransactionCallBack foCallBck) {
        new PostBHApprovalTask(poConnect, poHistory, fsResultx, fsRemarks, foCallBck).execute(fsTransNo);
    }

    private static class PostBHApprovalTask extends AsyncTask<String, Void, String> {

        private final ConnectionUtil loConnect;
        private final EvaluatorManager loHistory;
        private final String lsResultx;
        private final String lsRemarks;
        private final OnTransactionCallBack loCallBck;
        private boolean isSuccess = false;

        private PostBHApprovalTask(ConnectionUtil foConnect, EvaluatorManager foHistory, String fsResultx, String fsRemarks, OnTransactionCallBack foCallBck) {
            this.loConnect = foConnect;
            this.loHistory = foHistory;
            this.lsResultx = fsResultx;
            this.lsRemarks = fsRemarks;
            this.loCallBck = foCallBck;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loCallBck.onLoad();
        }

        @Override
        protected String doInBackground(String... strings) {
            String lsTransNo = strings[0];
            final String[] lsResultx = {""};

            try {
                if(loConnect.isDeviceConnected()) {
                    loHistory.SaveBHApproval(lsTransNo, this.lsResultx, lsRemarks, new EvaluatorManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {

                        }

                        @Override
                        public void OnFailed(String message) {
                            lsResultx[0] = message;
                        }
                    });
                    loHistory.PostBHApproval(lsTransNo, new EvaluatorManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            lsResultx[0] = args;
                            isSuccess = true;
                        }

                        @Override
                        public void OnFailed(String message) {
                            lsResultx[0] = message;
                        }
                    });
                } else {
                    lsResultx[0] = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResultx[0] = e.getMessage();
            }

            return lsResultx[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(isSuccess) {
                loCallBck.onSuccess(s);
            } else {
                loCallBck.onFailed(s);
            }
        }

    }

    public interface OnTransactionCallBack {
        void onSuccess(String fsMessage);
        void onFailed(String fsMessage);
        void onLoad();
    }

}
