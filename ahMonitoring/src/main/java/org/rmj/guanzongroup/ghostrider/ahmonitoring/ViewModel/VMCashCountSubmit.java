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

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECashCount;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCashCount;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.CashCountInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RequestNamesInfoModel;

import java.util.ArrayList;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_KWIKSEARCH;

public class VMCashCountSubmit extends AndroidViewModel {

    private static final String TAG = VMCashCountSubmit.class.getSimpleName();

    private final Application instance;
    private final REmployee poEmploye;
    private final RBranch poBranch;
    private final ECashCount eCashCount;
    private final RCashCount poCashCount;
    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final List<RequestNamesInfoModel> infoList;
    public VMCashCountSubmit(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poEmploye = new REmployee(application);
        this.poBranch = new RBranch(application);
        this.poCashCount = new RCashCount(application);
        this.eCashCount = new ECashCount();
        this.infoList = new ArrayList<>();
        this.psTransNox.setValue(poCashCount.getCashCountNextCode());
    }
    public interface OnKwikSearchCallBack{
        void onStartKwikSearch();
        void onSuccessKwikSearch(List<RequestNamesInfoModel> infoList);
        void onKwikSearchFailed(String message);
    }
    public interface OnSaveCashCountCallBack{
        void onStartSaveCashCount();
        void onSuccessSaveCashCount();
        void onSaveCashCountFailed(String message);
    }
    public LiveData<EEmployeeInfo> getEmplopyeInfo(){
        return this.poEmploye.getEmployeeInfo();
    }
    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }
    public LiveData<String> getTransNox(){
        return psTransNox;
    }

    public void importRequestNames(String names,OnKwikSearchCallBack callBack){

        JSONObject loJson = new JSONObject();
        try {
            loJson.put("reqstdnm", names);
            loJson.put("bsearch", true);

            new ImportRequestNames(instance, callBack).execute(loJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private class ImportRequestNames extends AsyncTask<JSONObject, Void, String> {
        private final HttpHeaders headers;
        private final RBranchLoanApplication brnRepo;
        private final ConnectionUtil conn;
        private final WebApi webApi;
        private final OnKwikSearchCallBack callback;
        public ImportRequestNames(Application instance,  OnKwikSearchCallBack callback) {
            this.headers = HttpHeaders.getInstance(instance);
            this.brnRepo = new RBranchLoanApplication(instance);
            this.conn = new ConnectionUtil(instance);
            this.webApi = new WebApi(instance);
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onStartKwikSearch();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... strings) {
            String response = "";
            try{
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(URL_KWIKSEARCH, strings[0].toString(), headers.getHeaders());
                    JSONObject jsonResponse = new JSONObject(response);
                    String lsResult = jsonResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        parseData(jsonResponse);
                    }
                } else {
                    response = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (NullPointerException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.onSuccessKwikSearch(infoList);
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.onKwikSearchFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onKwikSearchFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.onKwikSearchFailed(e.getMessage());
            }
        }
    }
    private void parseData(JSONObject obj){
        try{
            infoList.clear();
            JSONArray jsonA = obj.getJSONArray("detail");
            for (int x = 0;  x < jsonA.length(); x++) {
                JSONObject jsonDetail = jsonA.getJSONObject(x);
                RequestNamesInfoModel infoDetails = new RequestNamesInfoModel(jsonDetail.getString("reqstdnm"),jsonDetail.getString("reqstdid"),jsonDetail.getString("department"));
                infoList.add(infoDetails);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public boolean saveCashCount(CashCountInfoModel infoModel, JSONObject jsonObject, OnSaveCashCountCallBack callback) {
        try {

            new UpdateTask(poCashCount, infoModel, jsonObject,callback).execute();
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
//            callback.OnFailedResult(e.getMessage());
            callback.onSaveCashCountFailed("NullPointerException error");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            callback.onSaveCashCountFailed("Exception error");
            return false;
        }
    }

    //Added by Jonathan 2021/06/11
    //Need AsyncTask for background threading..
    //RoomDatabase requires background task in order to manipulate Tables...
    private  class UpdateTask extends AsyncTask<Void, Void, String> {
        private final RCashCount pocashcount;
        private final CashCountInfoModel infoModel;
        private final OnSaveCashCountCallBack callback;
        private JSONObject jsonObject;
        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final Telephony poDevID;
        private final SessionManager poUser;
        private final AppConfigPreference poConfig;
        public UpdateTask(RCashCount poDcp, CashCountInfoModel infoModel, JSONObject jsonObject, OnSaveCashCountCallBack callback) {
            this.pocashcount = poDcp;
            this.infoModel = infoModel;
            this.callback = callback;
            this.jsonObject = jsonObject;
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poDevID = new Telephony(instance);
            this.poUser = new SessionManager(instance);
            this.poConfig = AppConfigPreference.getInstance(instance);
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(Void... voids) {
            String lsResponse = "";
            try {

                if (!infoModel.isDataValid()) {
                    lsResponse = infoModel.getMessage();

                } else {
                    eCashCount.setTransNox(jsonObject.getString("sTransNox"));
                    eCashCount.setTransact(new AppConstants().CURRENT_DATE);
                    eCashCount.setCn0005cx(jsonObject.getString("nCn0005cx"));
                    eCashCount.setCn0010cx(jsonObject.getString("nCn0010cx"));
                    eCashCount.setCn0025cx(jsonObject.getString("nCn0025cx"));
                    eCashCount.setCn0050cx(jsonObject.getString("nCn0050cx"));
                    eCashCount.setCn0001px(jsonObject.getString("nCn0001px"));
                    eCashCount.setCn0005px(jsonObject.getString("nCn0005px"));
                    eCashCount.setCn0010px(jsonObject.getString("nCn0010px"));
                    eCashCount.setNte0020p(jsonObject.getString("nNte0020p"));
                    eCashCount.setNte0050p(jsonObject.getString("nNte0050p"));
                    eCashCount.setNte0100p(jsonObject.getString("nNte0100p"));
                    eCashCount.setNte0200p(jsonObject.getString("nNte0200p"));
                    eCashCount.setNte0500p(jsonObject.getString("nNte0500p"));
                    eCashCount.setNte1000p(jsonObject.getString("nNte1000p"));
                    eCashCount.setORNoxxxx(jsonObject.getString("sORNoxxxx"));
                    eCashCount.setSINoxxxx(jsonObject.getString("sSINoxxxx"));
                    eCashCount.setPRNoxxxx(jsonObject.getString("sPRNoxxxx"));
                    eCashCount.setCRNoxxxx(jsonObject.getString("sCRNoxxxx"));
                    eCashCount.setEntryDte(jsonObject.getString("EntryTime"));
                    eCashCount.setReqstdBy(jsonObject.getString("sReqstdBy"));
                    eCashCount.setModified(jsonObject.getString("EntryTime"));
                    eCashCount.setSendStat("0");
                    pocashcount.insertNewCashCount(eCashCount);
                    if(!poConn.isDeviceConnected()) {
                        lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR("Connection error.");
                    } else {
                        lsResponse = WebClient.httpsPostJSon(WebApi.URL_DCP_SUBMIT, jsonObject.toString(), poHeaders.getHeaders());

                        if(lsResponse == null){
                            lsResponse = AppConstants.SERVER_NO_RESPONSE();
                        } else {
                            JSONObject loResponse = new JSONObject(lsResponse);
                            if(loResponse.getString("result").equalsIgnoreCase("success")){
                                pocashcount.UpdateByTransNox(eCashCount.getTransNox());
                            }
                        }
                    }
                }
                return lsResponse;
            } catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.onSuccessSaveCashCount();
            } else {
                callback.onSaveCashCountFailed(s);
            }
        }
    }


}
