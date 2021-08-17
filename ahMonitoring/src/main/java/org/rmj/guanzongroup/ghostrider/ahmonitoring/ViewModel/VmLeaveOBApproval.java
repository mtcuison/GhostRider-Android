/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 8/16/21 8:50 AM
 * project file last modified : 8/16/21 8:21 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.OBApplication;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RequestLeaveObInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RequestNamesInfoModel;

import java.util.ArrayList;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_GET_LEAVE_APPLICATION;
import static org.rmj.g3appdriver.utils.WebApi.URL_GET_OB_APPLICATION;
import static org.rmj.g3appdriver.utils.WebApi.URL_KWIKSEARCH;

public class VmLeaveOBApproval extends AndroidViewModel {

    public static final String TAG = VmLeaveOBApproval.class.getSimpleName();
    private final Application instance;
    private final RBranch pobranch;
    private final REmployee poUser;
    private final REmployeeLeave poLeave;

    private final List<RequestLeaveObInfoModel> infoList;
    public VmLeaveOBApproval(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.pobranch = new RBranch(instance);
        this.poUser = new REmployee(instance);
        this.poLeave = new REmployeeLeave(instance);
        this.infoList = new ArrayList<>();
    }
    public interface OnKwikSearchCallBack{
        void onStartKwikSearch();
        void onSuccessKwikSearch(List<RequestLeaveObInfoModel> infoList);
        void onKwikSearchFailed(String message);
    }

    public interface OnConfirmOBLeaveListener{
        void onConfirm();
        void onSuccess();
        void onFailed(String message);
    }
    public LiveData<List<DEmployeeLeave.LeaveOBApplication>> getAllLeaveOBApplication(){
        return poLeave.getAllLeaveOBApplication();
    }


    public void importRequestLeaveApplication(String names, OnKwikSearchCallBack callBack){

        JSONObject loJson = new JSONObject();
        try {
            loJson.put("reqstdnm", names);
            loJson.put("bsearch", true);

            new importRequestLeaveApplication(instance, callBack).execute(loJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void importRequestBusinessTrip(String names, OnKwikSearchCallBack callBack){

        JSONObject loJson = new JSONObject();
        try {
            loJson.put("reqstdnm", names);
            loJson.put("bsearch", true);

            new importRequestBusinessTrip(instance, callBack).execute(loJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void confirmLeaveApplication(RequestLeaveObInfoModel infoModel, OnKwikSearchCallBack callBack){

    }
    private class importRequestLeaveApplication extends AsyncTask<JSONObject, Void, String> {
        private final HttpHeaders headers;
        private final RBranchLoanApplication brnRepo;
        private final ConnectionUtil conn;
        private final WebApi webApi;
        private final OnKwikSearchCallBack callback;
        public importRequestLeaveApplication(Application instance,  OnKwikSearchCallBack callback) {
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
                    response = WebClient.sendRequest(URL_GET_LEAVE_APPLICATION, strings[0].toString(), headers.getHeaders());
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

    private class importRequestBusinessTrip extends AsyncTask<JSONObject, Void, String> {
        private final HttpHeaders headers;
        private final RBranchLoanApplication brnRepo;
        private final ConnectionUtil conn;
        private final WebApi webApi;
        private final OnKwikSearchCallBack callback;
        public importRequestBusinessTrip(Application instance,  OnKwikSearchCallBack callback) {
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
                    response = WebClient.sendRequest(URL_GET_OB_APPLICATION, strings[0].toString(), headers.getHeaders());
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
//                RequestLeaveObInfoModel infoDetails = new RequestLeaveObInfoModel(jsonDetail.getString(""),jsonDetail.getString("reqstdid"),jsonDetail.getString("department"));
//                infoList.add(infoDetails);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void saveObLeave(RequestLeaveObInfoModel infoModel, String url, OnConfirmOBLeaveListener callback){
        new ConfirmObLeaveTask(infoModel, url,instance, callback).execute();
    }

    private static class ConfirmObLeaveTask extends AsyncTask<Void, Void, String> {

        private final RequestLeaveObInfoModel infoModel;
        private final OnConfirmOBLeaveListener callback;

        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final Telephony poDevID;
        private final SessionManager poUser;
        private final AppConfigPreference poConfig;
        private final REmployeeBusinessTrip poOBLeave;
        private final String url;
        public ConfirmObLeaveTask(RequestLeaveObInfoModel infoModel, String url,  Application instance, OnConfirmOBLeaveListener callback) {

            this.infoModel = infoModel;
            this.callback = callback;
            this.poOBLeave = new REmployeeBusinessTrip(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poDevID = new Telephony(instance);
            this.poUser = new SessionManager(instance);
            this.poConfig = AppConfigPreference.getInstance(instance);
            this.url = url;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            String lsResponse = "";
            try{
                JSONObject loJson = new JSONObject();
                loJson.put("sTransNox", infoModel.getsTransNox());
                loJson.put("dTransact", AppConstants.CURRENT_DATE);
                loJson.put("dAppldFrx", infoModel.getdAppldFrx());
                loJson.put("dAppldTox", infoModel.getdAppldTox());
                loJson.put("cTranStat", infoModel.getsTranStat());
                loJson.put("dApproved", AppConstants.CURRENT_DATE);

                if(poConn.isDeviceConnected()) {
                    lsResponse = WebClient.sendRequest(url, loJson.toString(), poHeaders.getHeaders());
                    JSONObject jsonResponse = new JSONObject(lsResponse);
                    String lsResult = jsonResponse.getString("result");
                    Log.e(TAG, lsResponse);
                } else {
                    lsResponse = AppConstants.SERVER_NO_RESPONSE();
                    Log.e(TAG, "else " + lsResponse);
                }

            } catch (NullPointerException e){
                e.printStackTrace();
                lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }catch (Exception e){
                e.printStackTrace();
                lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.onSuccess();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.onFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.onFailed(e.getMessage());
            }
        }
    }
}