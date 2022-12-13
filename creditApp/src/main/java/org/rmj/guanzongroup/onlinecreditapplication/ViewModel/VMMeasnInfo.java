package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.MeansSelectionInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Means;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Pension;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Personal;

import java.util.ArrayList;
import java.util.List;

public class VMMeasnInfo extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMPersonalInfo.class.getSimpleName();

    private final CreditApp poApp;
    private final Means poModel;

    private String TransNox;

    private String message;
    private int lnIndex;

    public VMMeasnInfo(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Means_Info);
        this.poModel = new Means();

    }

    public Means getModel(){
        return poModel;
    }
    @Override
    public void InitializeApplication(Intent params) {
        TransNox = params.getStringExtra("sTransNox");
        poModel.setTransNox(TransNox);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication() {
        return poApp.GetApplication(TransNox);
    }

    @Override
    public void ParseData(ECreditApplicantInfo args, OnParseListener listener) {
        new VMMeasnInfo.ParseDataTask(listener).execute(args);
    }

    @Override
    public void Validate(Object args) {

    }

    @Override
    public void SaveData(OnSaveInfoListener listener) {
        new VMMeasnInfo.SaveDetailTask(listener).execute(poModel);
    }

    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, Means>{

        private final OnParseListener listener;

        public ParseDataTask(OnParseListener listener) {
            this.listener = listener;
        }

        @Override
        protected Means doInBackground(ECreditApplicantInfo... app) {
            try {
                Means loDetail = (Means) poApp.Parse(app[0]);
                if(loDetail == null){
                    message = poApp.getMessage();
                    return null;
                }
                return loDetail;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Means result) {
            super.onPostExecute(result);
            if(result == null){
                Log.e(TAG, message);
            } else {
                listener.OnParse(result);
            }
        }
    }

    private class SaveDetailTask extends AsyncTask<Means, Void, Boolean>{

        private final OnSaveInfoListener listener;

        public SaveDetailTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Means... info) {
            int lnResult = poApp.Validate(info[0]);

            if(lnResult != 1){
                message = poApp.getMessage();
                return false;
            }

            if(!poApp.Save(info[0])){
                message = poApp.getMessage();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                listener.OnSave(TransNox);
            } else {
                listener.OnFailed(message);
            }
        }
    }
}