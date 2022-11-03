package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Disbursement;

public class VMDisbursement extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMDisbursement.class.getSimpleName();

    private final CreditApp poApp;
    private final Disbursement poModel;

    private String TransNox;

    private String message;

    public VMDisbursement(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Disbursement_Info);
        this.poModel = new Disbursement();
    }

    public Disbursement getModel() {
        return poModel;
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

    }

    @Override
    public void Validate(Object args) {

    }

    @Override
    public void SaveData(OnSaveInfoListener listener) {
        new SaveDataTask(listener).execute(poModel);
    }

    private class SaveDataTask extends AsyncTask<Disbursement, Void, Boolean>{

        private final OnSaveInfoListener listener;

        public SaveDataTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Disbursement... info) {
            int lnResult = poApp.Validate(info[0]);

            if(lnResult != 1){
                message = poApp.getMessage();
                return false;
            }

            if(!poApp.Save(info[0])){
                message = poApp.getMessage();
                return false;
            }

            TransNox = info[0].getTransNox();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                listener.OnFailed(message);
            } else {
                listener.OnSave(TransNox);
            }
        }
    }
}
