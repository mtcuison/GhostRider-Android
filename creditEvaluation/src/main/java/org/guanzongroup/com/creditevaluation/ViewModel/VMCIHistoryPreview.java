/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 3/28/22, 8:50 AM
 * project file last modified : 3/28/22, 8:50 AM
 */

package org.guanzongroup.com.creditevaluation.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.guanzongroup.com.creditevaluation.Core.EvaluatorManager;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VMCIHistoryPreview extends AndroidViewModel {
    private static final String TAG = VMEvaluation.class.getSimpleName();
    private final ConnectionUtil poConnect;
    private final Application app;
    private final REmployee poUser;
    private final EvaluatorManager foManager;
    private MutableLiveData<HashMap<oParentFndg, List<oChildFndg>>> foEvaluate = new MutableLiveData<>();

    public VMCIHistoryPreview(@NonNull Application application) {
        super(application);
        this.app = application;
        this.poConnect = new ConnectionUtil(application);
        this.poUser = new REmployee(application);
        this.foManager = new EvaluatorManager(application);
    }
    public LiveData<ECreditOnlineApplicationCI> getCIEvaluation(String transNox){
        return foManager.getApplications(transNox);
    }

    public void parseToEvaluationPreviewData(ECreditOnlineApplicationCI eCI) throws Exception {
        foEvaluate.setValue(foManager.parseToEvaluationPreviewData(eCI));
    }
    public LiveData<HashMap<oParentFndg, List<oChildFndg>>> getParsedEvaluationPreviewData(){
        return foEvaluate;
    }

    public void PostBHApproval(String fsTransNo, String fsResultx, String fsRemarks, VMEvaluationCIHistoryInfo.OnTransactionCallBack foCallBck) {
        new PostBHApprovalTask(poConnect, foManager, fsResultx, fsRemarks, foCallBck).execute(fsTransNo);
    }

    private static class PostBHApprovalTask extends AsyncTask<String, Void, String> {

        private final ConnectionUtil loConnect;
        private final EvaluatorManager loHistory;
        private final String lsResultx;
        private final String lsRemarks;
        private final VMEvaluationCIHistoryInfo.OnTransactionCallBack loCallBck;
        private boolean isSuccess = false;

        private PostBHApprovalTask(ConnectionUtil foConnect, EvaluatorManager foHistory, String fsResultx, String fsRemarks, VMEvaluationCIHistoryInfo.OnTransactionCallBack foCallBck) {
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
