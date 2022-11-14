package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientResidence;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Personal;

import java.util.List;

public class VMResidenceInfo extends AndroidViewModel implements CreditAppUI{
    private static final String TAG = VMResidenceInfo.class.getSimpleName();

    private final CreditApp poApp;
    private final ClientResidence poModel;

    private String TransNox;
    private String message;


    public VMResidenceInfo(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Residence_Info);
        this.poModel = new ClientResidence();
    }

    public ClientResidence getModel() {
        return poModel;
    }



    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList(){
        return poApp.GetTownProvinceList();
    }

    public LiveData<List<EBarangayInfo>> GetBarangayList(String args){
        return poApp.GetBarangayList(args);
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

    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, ClientResidence>{

        private final OnParseListener listener;

        public ParseDataTask(OnParseListener listener) {
            this.listener = listener;
        }

        @Override
        protected ClientResidence doInBackground(ECreditApplicantInfo... app) {
            try {
                ClientResidence loDetail = (ClientResidence) poApp.Parse(app[0]);

                if(loDetail == null) {
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
        protected void onPostExecute(ClientResidence result) {
            super.onPostExecute(result);
            if(result == null){
                Log.e(TAG, message);
            } else {
                listener.OnParse(result);
            }
        }
    }

    private class SaveDetailTask extends AsyncTask<ClientResidence, Void, Boolean>{

        private final OnSaveInfoListener listener;

        public SaveDetailTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(ClientResidence... info) {
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
