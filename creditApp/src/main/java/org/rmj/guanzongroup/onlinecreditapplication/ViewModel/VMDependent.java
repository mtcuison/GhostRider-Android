package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Dependent;

import java.util.ArrayList;
import java.util.List;

public class VMDependent extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMDependent.class.getSimpleName();

    private final CreditApp poApp;
    private final Dependent poModel;

    private String TransNox;

    private final MutableLiveData<List<Dependent.DependentInfo>> poList = new MutableLiveData<>();

    private String message;

    public interface OnAddDependetListener{
        void OnAdd(String args);
        void OnFailed(String message);
    }

    public VMDependent(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Dependent_Info);
        this.poModel = new Dependent();
        this.poList.setValue(new ArrayList<>());
    }

    public Dependent getModel() {
        return poModel;
    }

    public LiveData<List<Dependent.DependentInfo>> GetDependents(){
        return poList;
    }

    public void setDependent(List<Dependent.DependentInfo> val){
        poList.setValue(val);
    }

    public void addDependent(Dependent.DependentInfo args, OnAddDependetListener listener){
        try{
            if(!args.isDataValid()){
                listener.OnFailed(args.getMessage());
                return;
            }

            List<Dependent.DependentInfo> loList = poList.getValue();
            loList.add(args);
            poList.setValue(loList);
            listener.OnAdd("Dependent Added!");
        } catch (Exception e){
            e.printStackTrace();
            listener.OnFailed(e.getMessage());
        }
    }

    public void removeDependent(int args){
        List<Dependent.DependentInfo> loList = poList.getValue();
        loList.remove(args);
        poList.setValue(loList);
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

    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, Dependent>{

        private final OnParseListener listener;

        public ParseDataTask(OnParseListener listener) {
            this.listener = listener;
        }

        @Override
        protected Dependent doInBackground(ECreditApplicantInfo... app) {
            try {
                Dependent loDetail = (Dependent) poApp.Parse(app[0]);
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
        protected void onPostExecute(Dependent result) {
            super.onPostExecute(result);
            if(result == null){
                Log.e(TAG, message);
            } else {
                listener.OnParse(result);
            }
        }
    }

    private class SaveDataTask extends AsyncTask<Dependent, Void, Boolean>{

        private final OnSaveInfoListener listener;

        public SaveDataTask(OnSaveInfoListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Dependent... info) {
            info[0].setDependentList(poList.getValue());
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

    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvince(){
        return poApp.GetTownProvinceList();
    }
}
