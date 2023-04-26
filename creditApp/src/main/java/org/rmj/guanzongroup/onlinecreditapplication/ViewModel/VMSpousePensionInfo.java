package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.SpousePension;


public class VMSpousePensionInfo extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMPensionInfo.class.getSimpleName();

    private final CreditApp poApp;
    private final SpousePension poModel;

    private String TransNox;

    private String message;

    public VMSpousePensionInfo(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Spouse_Pension_Info);
        this.poModel = new SpousePension();
    }

    public SpousePension getModel() {
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
        new ParseDataTask(listener).execute(args);
    }

    @Override
    public void Validate(Object args) {

    }

    @Override
    public void SaveData(OnSaveInfoListener listener) {
        new SaveDetailTask(listener).execute(poModel);
    }

    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, SpousePension>{

        private final OnParseListener listener;

        public ParseDataTask(OnParseListener listener) {
            this.listener = listener;
        }

        @Override
        protected SpousePension doInBackground(ECreditApplicantInfo... app) {
            try {
                SpousePension loDetail = (SpousePension) poApp.Parse(app[0]);
                if(loDetail == null){
                    message = poApp.getMessage();
                    return null;
                }
                return loDetail;
            } catch (NullPointerException e){
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
        protected void onPostExecute(SpousePension result) {
            super.onPostExecute(result);
            if(result == null){
                Log.e(TAG, message);
            } else {
                listener.OnParse(result);
            }
        }
    }

    private class SaveDetailTask extends AsyncTask<SpousePension, Void, Boolean>{

        private final OnSaveInfoListener listener;

        public SaveDetailTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(SpousePension... info) {
            int lnResult = poApp.Validate(info[0]);

            if(lnResult != 1){
                message = poApp.getMessage();
                return false;
            }

            String lsResult = poApp.Save(info[0]);
            if(lsResult == null){
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
