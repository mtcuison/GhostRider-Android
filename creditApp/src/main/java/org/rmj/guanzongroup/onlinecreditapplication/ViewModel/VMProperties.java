package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.CoMakerResidence;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Properties;

public class VMProperties extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMProperties.class.getSimpleName();

    private final CreditApp poApp;
    private final Properties poModel;

    private String TransNox;
    private String message;

    public VMProperties(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Properties_Info);
        this.poModel = new Properties();
    }

    public Properties getModel() {
        return poModel;
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
    public void Validate(Object args) {

    }

    @Override
    public void SaveData(OnSaveInfoListener listener) {
        new SaveDetailTask(listener).execute(poModel);
    }

    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, Properties> {

        private final OnParseListener listener;

        public ParseDataTask(OnParseListener listener) {
            this.listener = listener;
        }

        @Override
        protected Properties doInBackground(ECreditApplicantInfo... app) {
            try {
                Properties loDetail = (Properties) poApp.Parse(app[0]);
                if (loDetail == null) {
                    message = poApp.getMessage();
                    return null;
                }
                return loDetail;
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Properties result) {
            super.onPostExecute(result);
            if (result == null) {
                Log.e(TAG, message);
            } else {
                listener.OnParse(result);
            }
        }
    }

    private class SaveDetailTask extends AsyncTask<Properties,Void,Boolean>{

        private final OnSaveInfoListener listener;

        public SaveDetailTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Properties... info) {
            int lnResult = poApp.Validate(info[0]);

            if (lnResult != 1){
                message = poApp.getMessage();
                return false;
            }

            if (!poApp.Save(info[0])) {
                message = poApp.getMessage();
                return false;
            }

            TransNox = info[0].getTransNox();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if (!isSuccess) {
                listener.OnFailed(message);
            } else {
                listener.OnSave(TransNox);
            }
        }
    }
}
