/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/9/21 9:00 AM
 * project file last modified : 6/9/21 9:00 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.CashCount.CashCount;
import org.rmj.g3appdriver.GCircle.Apps.CashCount.QuickSearchNames;
import org.rmj.g3appdriver.lib.Branch.Branch;
import org.rmj.g3appdriver.lib.Branch.entity.EBranchInfo;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.util.List;

public class VMCashCountSubmit extends AndroidViewModel {
    private static final String TAG = VMCashCountSubmit.class.getSimpleName();

    private final Application instance;
    private final EmployeeMaster poEmploye;
    private final CashCount poSys;
    private final ConnectionUtil poConn;
    private final Branch poBranch;
    private String message;
    public VMCashCountSubmit(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new CashCount(application);
        this.poEmploye = new EmployeeMaster(application);
        this.poBranch = new Branch(application);
        this.poConn = new ConnectionUtil(application);
    }

    public interface OnKwikSearchCallBack{
        void onStartKwikSearch();
        void onSuccessKwikSearch(List<QuickSearchNames> infoList);
        void onKwikSearchFailed(String message);
    }

    public interface OnSaveCashCountCallBack{
        void OnSaving();
        void OnSuccess();
        void OnSaveToLocal(String message);
        void OnFailed(String message);
    }

    public interface OnDeviceConnectionCheck{
        void OnCheck(boolean isDeviceConnected);
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<EBranchInfo> getSelfieLogBranchInfo(){
        return poBranch.getSelfieLogBranchInfo();
    }

    public void GetSearchList(String name, OnKwikSearchCallBack callback){
        TaskExecutor.Execute(name, new OnTaskExecuteListener() {

            @Override
            public void OnPreExecute() {
                callback.onStartKwikSearch();
            }

            @Override
            public Object DoInBackground(Object args) {
                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage();
                    return null;
                }

                String lsName = (String) args;
                List<QuickSearchNames> loList = poSys.GetQuickSearchNames(lsName);
                if(loList == null){
                    message = poSys.getMessage();
                    return null;
                }
                return loList;
            }

            @Override
            public void OnPostExecute(Object object) {
                List<QuickSearchNames> loResult = (List<QuickSearchNames>) object;
                if(loResult == null){
                    callback.onKwikSearchFailed(message);
                    return;
                }

                callback.onSuccessKwikSearch(loResult);
            }
        });
    }

    public void SaveCashCount(JSONObject foVal, OnSaveCashCountCallBack callback) {
        TaskExecutor.Execute(foVal, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callback.OnSaving();
            }

            @Override
            public Object DoInBackground(Object args) {
                JSONObject lsentries = (JSONObject) args;
                String lsResult = poSys.SaveCashCount(lsentries);
                if (lsResult == null){
                    message = poSys.getMessage();
                    return 0;
                }

                if(!poConn.isDeviceConnected()){
                    message = "Cash count entry has been save to local device.";
                    return 2;
                }

                if(!poSys.UploadCashCount(lsResult)){
                    message = poSys.getMessage();
                    return 0;
                }

                return 1;

            }

            @Override
            public void OnPostExecute(Object object) {
                Integer lnResult = (Integer) object;
                switch (lnResult){
                    case 0:
                        callback.OnFailed(message);
                        break;
                    case 1:
                        callback.OnSuccess();
                        break;
                    case 2:
                        callback.OnSaveToLocal(message);
                        break;
                }
            }
        });
    }

//    private class SaveCashCountTask extends AsyncTask<JSONObject, Void, Integer>{
//
//        private final OnSaveCashCountCallBack callBack;
//
//        private String message;
//
//        public SaveCashCountTask(OnSaveCashCountCallBack callBack) {
//            this.callBack = callBack;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            callBack.OnSaving();
//        }
//
//        @Override
//        protected Integer doInBackground(JSONObject... entries) {
//            String lsResult = poSys.SaveCashCount(entries[0]);
//            if (lsResult == null){
//                message = poSys.getMessage();
//                return 0;
//            }
//
//            if(!poConn.isDeviceConnected()){
//                message = "Cash count entry has been save to local device.";
//                return 2;
//            }
//
//            if(!poSys.UploadCashCount(lsResult)){
//                message = poSys.getMessage();
//                return 0;
//            }
//
//            return 1;
//        }
//
//        @Override
//        protected void onPostExecute(Integer result) {
//            super.onPostExecute(result);
//            switch (result){
//                case 0:
//                    callBack.OnFailed(message);
//                    break;
//                case 1:
//                    callBack.OnSuccess();
//                    break;
//                case 2:
//                    callBack.OnSaveToLocal(message);
//                    break;
//            }
//        }
//    }



    //Added by Jonathan 2021/06/11
    //Need AsyncTask for background threading..
    //RoomDatabase requires background task in order to manipulate Tables...
//    private  class UpdateTask extends AsyncTask<Void, Void, String> {
//        private final RCashCount pocashcount;
//        private final CashCountInfoModel infoModel;
//        private final OnSaveCashCountCallBack callback;
//        private JSONObject jsonObject;
//        private final HttpHeaders poHeaders;
//        private final ConnectionUtil poConn;
//        private final SelfieLog poLog;
//        private final Telephony poDevID;
//        private final SessionManager poUser;
//        private final AppConfigPreference poConfig;
//        private final WebApi poApi;
//
//        public UpdateTask(RCashCount poDcp, CashCountInfoModel infoModel, JSONObject jsonObject, OnSaveCashCountCallBack callback) {
//            this.pocashcount = poDcp;
//            this.infoModel = infoModel;
//            this.callback = callback;
//            this.jsonObject = jsonObject;
//            this.poHeaders = HttpHeaders.getInstance(instance);
//            this.poConn = new ConnectionUtil(instance);
//            this.poDevID = new Telephony(instance);
//            this.poUser = new SessionManager(instance);
//            this.poConfig = AppConfigPreference.getInstance(instance);
//            this.poLog = new SelfieLog(instance);
//            this.poApi = new WebApi(poConfig.getTestStatus());
//        }
//
//        @SuppressLint("NewApi")
//        @Override
//        protected String doInBackground(Void... voids) {
//            String lsResponse = "";
//            try {
//                eCashCount.setTransNox(jsonObject.getString("sTransNox"));
//                eCashCount.setBranchCd(jsonObject.getString("sBranchCd"));
//                eCashCount.setTransact(new AppConstants().CURRENT_DATE);
//                eCashCount.setCn0001cx(jsonObject.getString("nCn0001cx"));
//                eCashCount.setCn0005cx(jsonObject.getString("nCn0005cx"));
//                eCashCount.setCn0010cx(jsonObject.getString("nCn0010cx"));
//                eCashCount.setCn0025cx(jsonObject.getString("nCn0025cx"));
//                eCashCount.setCn0050cx(jsonObject.getString("nCn0050cx"));
//                eCashCount.setCn0001px(jsonObject.getString("nCn0001px"));
//                eCashCount.setCn0005px(jsonObject.getString("nCn0005px"));
//                eCashCount.setCn0010px(jsonObject.getString("nCn0010px"));
//                eCashCount.setNte0020p(jsonObject.getString("nNte0020p"));
//                eCashCount.setNte0050p(jsonObject.getString("nNte0050p"));
//                eCashCount.setNte0100p(jsonObject.getString("nNte0100p"));
//                eCashCount.setNte0200p(jsonObject.getString("nNte0200p"));
//                eCashCount.setNte0500p(jsonObject.getString("nNte0500p"));
//                eCashCount.setNte1000p(jsonObject.getString("nNte1000p"));
//                eCashCount.setORNoxxxx(jsonObject.getString("sORNoxxxx"));
//                eCashCount.setSINoxxxx(jsonObject.getString("sSINoxxxx"));
//                eCashCount.setPRNoxxxx(jsonObject.getString("sPRNoxxxx"));
//                eCashCount.setCRNoxxxx(jsonObject.getString("sCRNoxxxx"));
//                eCashCount.setEntryDte(jsonObject.getString("dEntryDte"));
//                eCashCount.setReqstdBy(jsonObject.getString("sReqstdBy"));
//                eCashCount.setORNoxNPt(jsonObject.getString("sORNoxNPt"));
//                eCashCount.setPRNoxNPt(jsonObject.getString("sPRNoxNPt"));
//                eCashCount.setDRNoxxxx(jsonObject.getString("sDRNoxxxx"));
//                eCashCount.setPettyAmt(jsonObject.getString("nPettyAmt"));
//                eCashCount.setSendStat("0");
//                pocashcount.insertNewCashCount(eCashCount);
//                poLog.UpdateCashCountRequireStatus();
//                if(!poConn.isDeviceConnected()) {
//                    lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR("Cash count entry has been save to local device.");
//                } else {
//                    lsResponse = WebClient.sendRequest(poApi.getUrlSubmitCashcount(poConfig.isBackUpServer()), jsonObject.toString(), poHeaders.getHeaders());
//                    if(lsResponse == null){
//                        lsResponse = AppConstants.SERVER_NO_RESPONSE();
//                    } else {
//                        JSONObject loResponse = new JSONObject(lsResponse);
//                        if(loResponse.getString("result").equalsIgnoreCase("success")){
//                            pocashcount.UpdateByTransNox(eCashCount.getTransNox());
//                        }
//                    }
//                }
//                return lsResponse;
//            } catch (Exception e){
//                e.printStackTrace();
//                lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
//            }
//
//            return lsResponse;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            try{
//                Log.e(TAG, s);
//                JSONObject loJSon = new JSONObject(s);
//                String lsResult = loJSon.getString("result");
//                if(lsResult.equalsIgnoreCase("success")){
//                    callback.OnSuccess();
//                } else {
//                    JSONObject loError = loJSon.getJSONObject("error");
//                    callback.onSaveCashCountFailed(loError.getString("message"));
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

    public void CheckConnectivity(OnDeviceConnectionCheck callback){
        new ConnectionCheckTask(instance, callback).execute();
    }

    private static class ConnectionCheckTask extends AsyncTask<String, Void, Boolean>{

        private final Application instance;
        private final OnDeviceConnectionCheck callback;

        public ConnectionCheckTask(Application instance, OnDeviceConnectionCheck callback) {
            this.instance = instance;
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return new ConnectionUtil(instance).isDeviceConnected();
        }

        @Override
        protected void onPostExecute(Boolean isConnected) {
            super.onPostExecute(isConnected);
            callback.OnCheck(isConnected);
        }
    }
}