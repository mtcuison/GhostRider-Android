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
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBarangayInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EMcModel;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.MeansSelectionInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.ReviewLoanInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientResidence;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Personal;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.SpouseBusiness;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ReviewAppDetail;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppConstants;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;

public class VMReviewLoanApp  extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMReviewLoanApp.class.getSimpleName();

    private final CreditApp poApp;
    private final CreditOnlineApplication poCredt;
    private final ConnectionUtil poConn;

    private ECreditApplicantInfo poInfo;

    private String TransNox;

    private String message;

    public VMReviewLoanApp(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.ReviewLoanInfo);
        this.poCredt = new CreditOnlineApplication(application);
        this.poConn = new ConnectionUtil(application);
    }

    public void setInfo(ECreditApplicantInfo poInfo) {
        this.poInfo = poInfo;
    }

    @Override
    public void InitializeApplication(Intent params) {
        this.TransNox = params.getStringExtra("sTransNox");
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication() {
        return poApp.GetApplication(TransNox);
    }

    @Override
    public void ParseData(ECreditApplicantInfo args, OnParseListener listener) {
        new ParseDataTask(listener).execute(args);
    }

    @Override
    public void Validate(Object args) {

    }


    @Override
    public void SaveData(OnSaveInfoListener listener) {
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

    private class SaveDetailTask extends AsyncTask<ECreditApplicantInfo, Void, Boolean>{

        private final OnSaveInfoListener listener;

        private String message;

        public SaveDetailTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(ECreditApplicantInfo... eCreditApplicantInfos) {
            String lsResult = poApp.Save(eCreditApplicantInfos[0]);
            if(lsResult == null){
                message = poApp.getMessage();
                return false;
            }

            if(!poConn.isDeviceConnected()){
                message = "Credit application has been save to local.";
                return true;
            }

            if(!poCredt.UploadApplication(lsResult)){
                message = poCredt.getMessage();
                return false;
            }
            message = "Credit application saved!";
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                listener.OnFailed(message);
            } else {
                listener.OnSave(message);
            }
        }
    }

}