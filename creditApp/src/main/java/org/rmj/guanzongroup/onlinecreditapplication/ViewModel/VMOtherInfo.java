package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.OtherInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.ReviewLoanInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Employment;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.OtherReference;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Reference;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ReviewAppDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMOtherInfo extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMReviewLoanApp.class.getSimpleName();

    private final CreditApp poApp;
    private final OtherReference poModel;

    private String TransNox;

    private String message;
    private MutableLiveData<List<Reference>> loList = new MutableLiveData<>();
    public VMOtherInfo(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Other_Info);
        this.poModel = new OtherReference();
        this.loList.setValue(new ArrayList<>());
    }

    public OtherReference getModel() {
        return poModel;
    }

    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList(){
        return poApp.GetTownProvinceList();
    }
    public void setListOfReference(List<Reference> foList){
        loList.getValue().clear();
        loList.setValue(foList);
//        Objects.requireNonNull(loList).getValue().clear();
//        Objects.requireNonNull(loList).setValue(foList);
    }
    public LiveData<List<Reference>> getReferenceList(){
        return loList;
    }
    public boolean addReference(Reference poInfo, AddPersonalInfoListener listener){
        try{
            if(poInfo.isDataValid()){
                List<Reference> refList = loList.getValue();
                refList.add(poInfo);
                loList.setValue(refList);
                listener.OnSuccess();
                return true;
            } else {
                listener.onFailed(poInfo.getMessage());
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            listener.onFailed(e.getMessage());
            return false;
        }
    }

    public boolean removeReference(int position) {
        try {
            loList.getValue().remove(position);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public interface AddPersonalInfoListener{
        void OnSuccess();
        void onFailed(String message);
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

    @SuppressLint("StaticFieldLeak")
    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, OtherReference> {

        private final OnParseListener listener;

        public ParseDataTask(OnParseListener listener) {
            this.listener = listener;
        }

        @Override
        protected OtherReference doInBackground(ECreditApplicantInfo... app) {
            try{
                OtherReference loDetail = (OtherReference) poApp.Parse(app[0]);

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
        protected void onPostExecute(OtherReference result) {
            super.onPostExecute(result);
            if(result == null){
                Log.e(TAG, message);
            } else {
                listener.OnParse(result);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SaveDetailTask extends AsyncTask<OtherReference, Void, Boolean>{

        private final OnSaveInfoListener listener;

        public SaveDetailTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(OtherReference... info) {
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
            if(!isSuccess){
                listener.OnFailed(message);
            } else {
                listener.OnSave(TransNox);
            }
        }
    }
}
