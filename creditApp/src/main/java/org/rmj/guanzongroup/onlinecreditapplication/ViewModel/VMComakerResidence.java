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
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.CoMakerResidence;

import java.util.List;

public class VMComakerResidence extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMComakerResidence.class.getSimpleName();

    private final CreditApp poApp;
    private final CoMakerResidence poModel;

    private String TransNox;

    private String message;


    public VMComakerResidence(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.CoMaker_Residence_Info);
        this.poModel = new CoMakerResidence();
    }

    public CoMakerResidence getModel() {
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

    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList() {
        return poApp.GetTownProvinceList();
    }

    public LiveData<List<EBarangayInfo>> GetBarangayList(String args){
        return poApp.GetBarangayList(args);
    }

    public LiveData<List<ECountryInfo>> GetCountryList() {
        return poApp.GetCountryList();
    }

    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, CoMakerResidence> {

        private final OnParseListener listener;

        public ParseDataTask(OnParseListener listener) {
            this.listener = listener;
        }

        @Override
        protected CoMakerResidence doInBackground(ECreditApplicantInfo... app) {
            try {
                CoMakerResidence loDetail = (CoMakerResidence) poApp.Parse(app[0]);
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
        protected void onPostExecute(CoMakerResidence result) {
            super.onPostExecute(result);
            if (result == null) {
                Log.e(TAG, message);
            } else {
                listener.OnParse(result);
            }
        }
    }

    private class SaveDetailTask extends AsyncTask<CoMakerResidence, Void, Boolean> {

        private final OnSaveInfoListener listener;

        public SaveDetailTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(CoMakerResidence... info) {
            int lnResult = poApp.Validate(info[0]);

            if (lnResult != 1) {
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
