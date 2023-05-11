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
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.Obj.SpouseResidenceInfo;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.model.SpouseResidence;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.util.List;

public class VMSpouseResidence extends AndroidViewModel implements CreditAppUI {
    private static final String TAG = VMSpouseResidence.class.getSimpleName();

    private final CreditApp poApp;
    private final SpouseResidence poModel;

    private String TransNox;

    private String message;

    public VMSpouseResidence(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application).getInstance(CreditAppInstance.Spouse_Residence_Info);
        this.poModel = new SpouseResidence();
    }

    public SpouseResidence getModel() {
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
                ECreditApplicantInfo lsApp = (ECreditApplicantInfo) args;
                try {
                    SpouseResidenceInfo.oResidence loDetail = (SpouseResidenceInfo.oResidence) poApp.Parse(lsApp);
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
                SpouseResidenceInfo.oResidence lsResult = (SpouseResidenceInfo.oResidence) object;
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
//        new SaveDataTask(listener).execute(poModel);
        TaskExecutor.Execute(listener, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {

            }

            @Override
            public Object DoInBackground(Object args) {
                SpouseResidence lsInfo = (SpouseResidence) args;
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

    public LiveData<List<EBarangayInfo>> GetBarangayList(String args) {
        return poApp.GetBarangayList(args);
    }
}
//    private class ParseDataTask extends AsyncTask<ECreditApplicantInfo, Void, SpouseResidenceInfo.oResidence>{
//
//        private final OnParseListener listener;
//
//        public ParseDataTask(OnParseListener listener) {
//            this.listener = listener;
//        }
//
//        @Override
//        protected SpouseResidenceInfo.oResidence doInBackground(ECreditApplicantInfo... app) {
//            try {
//                SpouseResidenceInfo.oResidence loDetail = (SpouseResidenceInfo.oResidence) poApp.Parse(app[0]);
//                if(loDetail == null){
//                    message = poApp.getMessage();
//                    return null;
//                }
//                return loDetail;
//            } catch (NullPointerException e){
//                e.printStackTrace();
//                message = e.getMessage();
//                return null;
//            }catch (Exception e){
//                e.printStackTrace();
//                message = e.getMessage();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(SpouseResidenceInfo.oResidence result) {
//            super.onPostExecute(result);
//            if(result == null){
//                Log.e(TAG, message);
//            } else {
//                listener.OnParse(result);
//            }
//        }
//    }

//    private class SaveDataTask extends AsyncTask<SpouseResidence, Void, Boolean>{
//
//        private final OnSaveInfoListener listener;
//
//        public SaveDataTask(OnSaveInfoListener listener) {
//            this.listener = listener;
//        }
//
//        @Override
//        protected Boolean doInBackground(SpouseResidence... info) {
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
