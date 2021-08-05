/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RFileCode;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.GRider.ImportData.Import_CreditAppList;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_BRANCH_LOAN_APP;

public class VMEvaluationList extends AndroidViewModel {
    private static final String TAG = VMEvaluationList.class.getSimpleName();
    private final Application instance;
    private final RFileCode peFileCode;
    private final LiveData<List<EFileCode>> collectionList;
    private final RBranchLoanApplication poCreditApp;
    private final Import_CreditAppList poImport;
    private final SessionManager poSession;
    private final MutableLiveData<String> sCredInvxx = new MutableLiveData<>();


    private final REmployee poEmploye;
    public VMEvaluationList(@NonNull Application application) {
        super(application);
        this.instance = application;
        peFileCode = new RFileCode(application);
        this.collectionList = peFileCode.getAllFileCode();
        this.poCreditApp = new RBranchLoanApplication(application);
        this.poImport = new Import_CreditAppList(application);
        this.poSession = new SessionManager(application);
        this.poEmploye = new REmployee(application);
    }
    public interface OnImportCallBack{
        void onStartImport();
        void onSuccessImport();
        void onImportFailed(String message);
    }
    public LiveData<EEmployeeInfo> getEmplopyeInfo(){
        return this.poEmploye.getEmployeeInfo();
    }
    public void setEmployeeID(String empID){
        this.sCredInvxx.setValue(empID);
    }
    public LiveData<List<EFileCode>> getFileCode(){
        return this.collectionList;
    }

    public LiveData<List<DBranchLoanApplication.CIEvaluationList>> getAllCICreditApplications(){
        return poCreditApp.getAllCICreditApplications();
    }
    public void ImportCIApplications(OnImportCallBack callBack){

        JSONObject loJson = new JSONObject();
        try {
            loJson.put("bycode", false);
            loJson.put("value","M02407000479");
//            loJson.put("value",sCredInvxx.getValue());
            new ImportCIApplications(instance, callBack).execute(loJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void importApplicationInfo(String fsTransno, ViewModelCallback callback) {
        try{
            JSONObject param = new JSONObject();
            param.put("value", fsTransno.trim());
            param.put("bsearch", true);
            new ImportApplicationInfoTask(instance,  WebApi.URL_DOWNLOAD_CREDIT_ONLINE_APP, callback).execute(param);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private class ImportCIApplications extends AsyncTask<JSONObject, Void, String> {
        private final HttpHeaders headers;
        private final RBranchLoanApplication brnRepo;
        private final ConnectionUtil conn;
        private final WebApi webApi;
        private final OnImportCallBack callback;
        private final RBranchLoanApplication poCreditApp;
        public ImportCIApplications(Application instance,  OnImportCallBack callback) {
            this.headers = HttpHeaders.getInstance(instance);
            this.brnRepo = new RBranchLoanApplication(instance);
            this.poCreditApp = new RBranchLoanApplication(instance);
            this.conn = new ConnectionUtil(instance);
            this.webApi = new WebApi(instance);
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onStartImport();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... strings) {
            String response = "";
            try{
                if(conn.isDeviceConnected()) {
                    response = WebClient.sendRequest(URL_BRANCH_LOAN_APP, strings[0].toString(), headers.getHeaders());
                    JSONObject jsonResponse = new JSONObject(response);
                    String lsResult = jsonResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        JSONArray laJson = jsonResponse.getJSONArray("detail");
                        if(!brnRepo.insertBranchApplicationInfos(laJson)){
                            response = AppConstants.ERROR_SAVING_TO_LOCAL();
                        }
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
                    callback.onSuccessImport();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.onImportFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onImportFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.onImportFailed(e.getMessage());
            }
        }
    }

    private static class ImportApplicationInfoTask extends AsyncTask<JSONObject, Void, String> {
        private final HttpHeaders headers;
        private final ConnectionUtil conn;
        private final RBranchLoanApplication poCiEvalx;
        private final ViewModelCallback callback;
        private final String Url;

        public ImportApplicationInfoTask(Application instance, String Url, ViewModelCallback callback) {
            this.headers = HttpHeaders.getInstance(instance);
            this.conn = new ConnectionUtil(instance);
            this.poCiEvalx = new RBranchLoanApplication(instance);
            this.callback = callback;
            this.Url = Url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... strings) {
            String response = "";
            try{
                if(conn.isDeviceConnected()) {
                    response = WebClient.sendRequest(Url, strings[0].toString(), headers.getHeaders());
                    Log.e(TAG+" API Response", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    String lsResult = jsonResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                         JSONArray arDetail = jsonResponse.getJSONArray("detail");
                         JSONObject detailx = arDetail.getJSONObject(0);
                         boolean isInserted = saveDataToLocal(detailx);
                         if(!isInserted) {
                             Log.e("isInserted?", "NO");
                             JSONObject jError = new JSONObject();
                             jError.put("result", "error");
                             JSONObject jMsg = new JSONObject();
                             jMsg.put("message", "Application already exist.");
                             jError.put("error", jMsg);
                             response = jError.toString();
                         }
                        Log.e(TAG+" Before Extract", jsonResponse.toString());
                    }
                } else {
                    response = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccessResult(new String[]{"Client Account info has been saved."});
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    Log.e("Error Result",  loError.getString("message"));
                    callback.OnFailedResult(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.OnFailedResult(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailedResult(e.getMessage());
            }
        }

        boolean saveDataToLocal(JSONObject foData) throws JSONException {
            Log.e(TAG + "saveDataToLocal()", foData.toString());
            EBranchLoanApplication loDetail = new EBranchLoanApplication();
            loDetail.setTransNox(foData.getString("sTransNox"));
            loDetail.setBranchCD(foData.getString("sBranchCd"));
            loDetail.setTransact(foData.getString("dTransact"));
            loDetail.setCredInvx(foData.getString("sCredInvx"));
            loDetail.setCompnyNm(foData.getString("sCompnyNm"));
            loDetail.setQMAppCde(foData.getString("sQMAppCde"));
            loDetail.setDownPaym(foData.getString("nDownPaym"));
            loDetail.setCreatedX(foData.getString("sCreatedx"));
            loDetail.setTranStat(foData.getString("cTranStat"));
            loDetail.setTimeStmp(foData.getString("dTimeStmp"));
            loDetail.setSpouseNm(foData.getString("sSpouseNm"));
            loDetail.setAddressx(foData.getString("sAddressx"));
            loDetail.setModelNme(foData.getString("sModelNme"));
            loDetail.setAcctTerm(foData.getString("nAcctTerm"));
            loDetail.setMobileNo(foData.getString("sMobileNo"));
            return poCiEvalx.insertCiApplication(loDetail);
        }
    }

}
