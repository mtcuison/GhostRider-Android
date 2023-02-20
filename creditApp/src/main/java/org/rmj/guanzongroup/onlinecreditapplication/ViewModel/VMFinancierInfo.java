package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Financier;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Personal;

import java.util.List;

public class VMFinancierInfo extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMFinancierInfo.class.getSimpleName();

    private final CreditApp poApp;
    private final Financier poModel;

    private String TransNox;

    private String message;


    public VMFinancierInfo(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Financier_Info);
        this.poModel = new Financier();
    }

    public Financier getModel(){
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
        new SaveDataTask(listener).execute(poModel);
    }

    public LiveData<List<ECountryInfo>> GetCountryList(){
        return poApp.GetCountryList();
    }

    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, Financier>{

        private final OnParseListener listener;

        public ParseDataTask(OnParseListener listener) {
            this.listener = listener;
        }

        @Override
        protected Financier doInBackground(ECreditApplicantInfo... app) {
            try {
                Financier loDetail = (Financier) poApp.Parse(app[0]);
                if(loDetail == null){
                    message = poApp.getMessage();
                    return null;
                }
                return loDetail;
            } catch (NullPointerException e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Financier result) {
            super.onPostExecute(result);
            if(result == null){
                Log.e(TAG, message);
            } else {
                listener.OnParse(result);
            }
        }
    }

    private class SaveDataTask extends AsyncTask<Financier, Void, Boolean>{

        private final OnSaveInfoListener listener;

        public SaveDataTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Financier... info) {
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
