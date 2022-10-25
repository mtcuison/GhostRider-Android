package org.rmj.g3appdriver.lib.integsys.CreditApp.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientInfo;

public class VMPersonalInfo extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMPersonalInfo.class.getSimpleName();

    private final CreditApp poApp;

    private String TransNox;

    private String message;

    public VMPersonalInfo(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Client_Info);
    }

    @Override
    public void InitializeApplication(Intent params) {
        TransNox = params.getStringExtra("sTransNox");
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
    public void SaveData(Object args, OnSaveInfoListener listener) {
        new SaveDetailTask(listener).execute((ClientInfo) args);
    }

    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, ClientInfo>{

        private final OnParseListener listener;

        public ParseDataTask(OnParseListener listener) {
            this.listener = listener;
        }

        @Override
        protected ClientInfo doInBackground(ECreditApplicantInfo... app) {
            try {
                return (ClientInfo) poApp.Parse(app[0]);
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ClientInfo result) {
            super.onPostExecute(result);
            if(result == null){
                Log.e(TAG, message);
            } else {
                listener.OnParse(result);
            }
        }
    }

    private class SaveDetailTask extends AsyncTask<ClientInfo, Void, Boolean>{

        private final OnSaveInfoListener listener;

        public SaveDetailTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(ClientInfo... info) {
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
                Intent loIntent = new Intent();
                loIntent.putExtra("sTransNox", TransNox);
                listener.OnSave(loIntent);
            }
        }
    }
}
