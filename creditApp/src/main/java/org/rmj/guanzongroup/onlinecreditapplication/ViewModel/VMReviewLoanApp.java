/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.model.ReviewAppDetail;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMReviewLoanApp  extends AndroidViewModel {
    private static final String TAG = VMReviewLoanApp.class.getSimpleName();

    private final CreditApp poApp;
    private final CreditOnlineApplication poCredt;
    private final ConnectionUtil poConn;

    private ECreditApplicantInfo poInfo;

    private String TransNox;

    private String message;

    public interface OnSaveCreditAppListener{
        void OnSave();
        void OnSuccess(String args);
        void OnSaveLocal(String message);
        void OnFailed(String message);
    }

    public VMReviewLoanApp(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.ReviewLoanInfo);
        this.poCredt = new CreditOnlineApplication(application);
        this.poConn = new ConnectionUtil(application);
    }

    public void setInfo(ECreditApplicantInfo poInfo) {
        this.poInfo = poInfo;
    }

    public void InitializeApplication(Intent params) {
        this.TransNox = params.getStringExtra("sTransNox");
    }

    public LiveData<ECreditApplicantInfo> GetApplication() {
        return poApp.GetApplication(TransNox);
    }

    public void ParseData(ECreditApplicantInfo args, OnParseListener listener) {
        new ParseDataTask(listener).execute(args);
    }

    public void Validate(Object args) {

    }

    public void SaveData(OnSaveCreditAppListener listener) {
        new SaveDetailTask(listener).execute(poInfo);
    }

    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, List<ReviewAppDetail>>{

        private final OnParseListener listener;

        public ParseDataTask(OnParseListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<ReviewAppDetail> doInBackground(ECreditApplicantInfo... app) {
            try {
                List<ReviewAppDetail> loDetail =  (List<ReviewAppDetail>) poApp.Parse(app[0]);
                if(loDetail == null){
                    message = poApp.getMessage();
                    return null;
                }
                return loDetail;
            }  catch (NullPointerException e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ReviewAppDetail>  result) {
            super.onPostExecute(result);
            if(result == null){
                Log.e(TAG, message);
            } else {
                listener.OnParse(result);
            }
        }
    }

    private class SaveDetailTask extends AsyncTask<ECreditApplicantInfo, Void, Integer>{

        private final OnSaveCreditAppListener listener;

        private String message;

        public SaveDetailTask(OnSaveCreditAppListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnSave();
        }

        @Override
        protected Integer doInBackground(ECreditApplicantInfo... eCreditApplicantInfos) {
            String lsResult = poApp.Save(eCreditApplicantInfos[0]);
            if(lsResult == null){
                message = poApp.getMessage();
                return 0;
            }

            if(!poConn.isDeviceConnected()){
                message = "Credit application has been save to local.";
                return 2;
            }

            if(!poCredt.UploadApplication(lsResult)){
                message = poCredt.getMessage();
                return 0;
            }
            message = "Credit application saved!";
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            switch (result){
                case 1:
                    listener.OnSuccess(message);
                    break;
                case 2:
                    listener.OnSaveLocal(message);
                    break;
                default:
                    listener.OnFailed(message);
                    break;
            }
        }
    }

}