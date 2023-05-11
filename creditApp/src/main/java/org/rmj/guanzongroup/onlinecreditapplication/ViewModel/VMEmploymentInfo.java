package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.model.Employment;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.util.List;

public class VMEmploymentInfo extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMEmploymentInfo.class.getSimpleName();

    private final CreditApp poApp;
    private final Employment poModel;

    private String TransNox;

    private String message;

    public VMEmploymentInfo(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Employed_Info);
        this.poModel = new Employment();
    }

    public Employment getModel() {
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
//        new ParseDataTask(listener).execute(args);
        TaskExecutor.Execute(listener, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {

            }

            @Override
            public Object DoInBackground(Object args) {
                ECreditApplicantInfo lsInfo = (ECreditApplicantInfo) args;
                try {
                    Employment loDetail = (Employment) poApp.Parse(lsInfo);

                    if (loDetail == null) {
                        message = poApp.getMessage();
                        return null;
                    }

                    return loDetail;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    message = e.getMessage();
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    message = e.getMessage();
                    return null;
                }
            }

            @Override
            public void OnPostExecute(Object object) {
                Employment lsResult = (Employment) object;
                if (lsResult == null) {
                    Log.e(TAG, message);
                } else {
                    listener.OnParse(lsResult);
                }
            }
        });
    }

    @Override
    public void Validate(Object args) {

    }

    @Override
    public void SaveData(OnSaveInfoListener listener) {
//        new SaveDetailTask(listener).execute(poModel);
        TaskExecutor.Execute(listener, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {

            }

            @Override
            public Object DoInBackground(Object args) {
                Employment lsInfo = (Employment) args;
                int lnResult = poApp.Validate(lsInfo);

                if (lnResult != 1) {
                    message = poApp.getMessage();
                    return false;
                }

                String lsResult = poApp.Save(lsInfo);
                if (lsResult == null) {
                    message = poApp.getMessage();
                    return false;
                }

                TransNox = lsInfo.getTransNox();
                return true;
            }

            @Override
            public void OnPostExecute(Object object) {
                Boolean lsSuccess = (Boolean) object;
                if (!lsSuccess) {
                    listener.OnFailed(message);
                } else {
                    listener.OnSave(TransNox);
                }
            }
        });
    }

    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList() {
        return poApp.GetTownProvinceList();
    }

    public LiveData<List<ECountryInfo>> GetCountryList() {
        return poApp.GetCountryList();
    }

    public LiveData<List<EOccupationInfo>> GetOccupations() {
        return poApp.GetOccupations();
    }
}

//    private class SaveDetailTask extends AsyncTask<Employment, Void, Boolean>{
//
//        private final OnSaveInfoListener listener;
//
//        public SaveDetailTask(OnSaveInfoListener listener) {
//            this.listener = listener;
//        }
//
//        @Override
//        protected Boolean doInBackground(Employment... info) {
//            int lnResult = poApp.Validate(info[0]);
//
//            if(lnResult != 1){
//                message = poApp.getMessage();
//                return false;
//            }
//
//            String lsResult = poApp.Save(info[0]);
//            if(lsResult == null){
//                message = poApp.getMessage();
//                return false;
//            }
//
//            TransNox = info[0].getTransNox();
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean isSuccess) {
//            super.onPostExecute(isSuccess);
//            if(!isSuccess){
//                listener.OnFailed(message);
//            } else {
//                listener.OnSave(TransNox);
//            }
//        }
//    }
//}
//private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, Employment>{
//
//    private final OnParseListener listener;
//
//    public ParseDataTask(OnParseListener listener) {
//        this.listener = listener;
//    }
//
//    @Override
//    protected Employment doInBackground(ECreditApplicantInfo... app) {
//        try{
//            Employment loDetail = (Employment) poApp.Parse(app[0]);
//
//            if(loDetail == null){
//                message = poApp.getMessage();
//                return null;
//            }
//
//            return loDetail;
//        } catch (NullPointerException e){
//            e.printStackTrace();
//            message = e.getMessage();
//            return null;
//        } catch (Exception e){
//            e.printStackTrace();
//            message = e.getMessage();
//            return null;
//        }
//    }
//
//    @Override
//    protected void onPostExecute(Employment result) {
//        super.onPostExecute(result);
//        if(result == null){
//            Log.e(TAG, message);
//        } else {
//            listener.OnParse(result);
//        }
//    }
//}