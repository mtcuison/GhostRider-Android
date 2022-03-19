/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.net.Uri;
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
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.DcpManager;
import org.rmj.guanzongroup.ghostrider.notifications.Function.GRiderErrorReport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VMCollectionList extends AndroidViewModel {
    private static final String TAG = VMCollectionList.class.getSimpleName();
    private static final String KEY_EMPLOYEE_ID = "sEmployId";
    private static final String KEY_REFER_DATE = "sReferDte";
    private final Application instance;
    private final RDailyCollectionPlan poDCPRepo;
    private final RBranch poBranch;
    private final RCollectionUpdate poUpdate;
    private final REmployee poEmploye;
    private final LiveData<List<EDCPCollectionDetail>> collectionList;
    private final AppConfigPreference poConfig;
    private final WebApi poApi;

    private String psEmployeeID = "";

    private final MutableLiveData<List<DDCPCollectionDetail.CollectionDetail>> plDetail = new MutableLiveData();
    private final MutableLiveData<List<EAddressUpdate>> plAddress = new MutableLiveData<>();
    private final MutableLiveData<List<EMobileUpdate>> plMobile = new MutableLiveData<>();

    private final MutableLiveData<List<EDCPCollectionMaster>> masterList = new MutableLiveData<>();

    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<Integer> pnEntryNox = new MutableLiveData<>();
    public interface OnDownloadCollection{
        void OnDownload();
        void OnSuccessDownload();
        void OnDownloadFailed(String message);
    }

    public interface OnDownloadClientList{
        void OnDownload();
        void OnSuccessDownload(List<EDCPCollectionDetail> collectionDetails);
        void OnFailedDownload(String message);
    }

    public VMCollectionList(@NonNull Application application) {
        super(application);
        this.instance = application;
        poDCPRepo = new RDailyCollectionPlan(application);
        poBranch = new RBranch(application);
        poUpdate = new RCollectionUpdate(application);
        poEmploye = new REmployee(application);
        poConfig = AppConfigPreference.getInstance(application);
        poApi = new WebApi(poConfig.getTestStatus());
        this.collectionList = poDCPRepo.getCollectionDetailList();
    }

    public LiveData<EEmployeeInfo> getEmplopyeInfo(){
        return this.poEmploye.getEmployeeInfo();
    }

    public void setEmployeeID(String EmployeeID){
        this.psEmployeeID = EmployeeID;
    }

    public boolean isDebugMode(){
        return poConfig.isTesting_Phase();
    }

    public void UpdateNotVisitedCollections(String fsRemarks, OnTransactionCallback foCallBck) {
        new UpdateNotVisitedCollectionsTask(instance, foCallBck).execute(fsRemarks);
    }

    public void ImportDcpMaster(String EmployID, String ReferDte, OnTransactionCallback foCallBck) {
        HashMap<String, String> loDataMap = new HashMap<>();
        loDataMap.put(KEY_EMPLOYEE_ID, EmployID);
        loDataMap.put(KEY_REFER_DATE, ReferDte);
        new ImportDcpMasterTask(instance, foCallBck).execute(loDataMap);
    }

    public void getSearchList(String fsClientNm, OnCollectionNameSearch foCallBck) {
        new GetSearchListTask(instance, foCallBck).execute(fsClientNm);
    }

    @SuppressLint("SimpleDateFormat")
    public void DownloadDcp(String date, OnDownloadCollection callback){
        try{
//            @SuppressLint("SimpleDateFormat") Date loDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//            String lsDate = new SimpleDateFormat("yyyy-MM-dd").format(Objects.requireNonNull(loDate));
//            boolean isExist = false;
//            for(int x = 0; x < Objects.requireNonNull(masterList.getValue()).size(); x++){
//                if(masterList.getValue().get(x).getReferDte().equalsIgnoreCase(lsDate)){
//                    isExist = true;
//                }
//            }

            JSONObject loJson = new JSONObject();
            loJson.put("sEmployID", psEmployeeID);
            loJson.put("dTransact", date);
            loJson.put("cDCPTypex", "1");
            new ImportLRCollection(instance, masterList.getValue(), callback).execute(loJson);
//            if(!isExist) {
//
//            } else {
//                callback.OnDownloadFailed("Record already exist. Creating multiple DCP schedule for one day is not allowed.");
//            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<List<EDCPCollectionMaster>> getCollectionMasterList(){
        return poDCPRepo.getCollectioMasterList();
    }

    public void setCollectionMasterList(List<EDCPCollectionMaster> masterList){
        this.masterList.setValue(masterList);
    }

    public LiveData<EDCPCollectionDetail> getCollectionLastEntry(){
        return poDCPRepo.getCollectionLastEntry();
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionList(){
        return collectionList;
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public void setParameter(String fsTransNox, int fnEntryNox){
        try{
            this.psTransNox.setValue(fsTransNox);
            this.pnEntryNox.setValue(fnEntryNox);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<List<EAddressUpdate>> getAddressRequestList(){
        return poUpdate.getAddressList();
    }

    public void setAddressRequestList(List<EAddressUpdate> addressRequestList){
        this.plAddress.setValue(addressRequestList);
    }

    public LiveData<List<EMobileUpdate>> getMobileRequestList(){
        return poUpdate.getMobileList();
    }

    public void setMobileRequestList(List<EMobileUpdate> mobileRequestList){
        this.plMobile.setValue(mobileRequestList);
    }

    // TODO: Import DCP List
    public void importDCPFile(Uri importFileName, ViewModelCallback callback) {
        try {
            JSONObject dcpImport = readDCPImportFileContent(importFileName);
            if (dcpImport != null) {
                extractDCPImportDetails(dcpImport, (collectionDetlList, collectionMaster) -> {
                    if (importDCPMasterData(collectionMaster)) {
                        boolean isCollectDetlInserted = importDCPListBulkData(collectionDetlList);
                        if (isCollectDetlInserted) {
                            callback.OnSuccessResult(new String[]{"Collection detail imported successfully."});
                            poConfig.setExportedDcp(true);
                        } else {
                            callback.OnFailedResult("Collection Detail Import Failed: DETAIL");
                        }
                    } else {
                        callback.OnFailedResult("Collection Detail Import Failed: MASTER");
                    }
                });
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailedResult("Parsing Dcp file error. Please take screenshot and report to MIS" +
                    "\n" +
                    "\n" +
                    "Cause: " + e.getMessage());
        }
    }

    private JSONObject readDCPImportFileContent(Uri fileContents) throws JSONException{
        String lsDcpContent = readTextFile(fileContents);
        return new JSONObject(lsDcpContent);
    }

    private void extractDCPImportDetails(JSONObject dcpImport, ImportJSONCallback callback) {
        try {
            if (dcpImport.has("master")  && dcpImport.has("detail")) {
                new CheckImportDataTask(poDCPRepo, doesExist -> {
                    if(doesExist) {
                        GToast.CreateMessage(getApplication(), "Collection list already exist.", GToast.WARNING).show();
                    } else {
                        try {
                            JSONObject loJSON_Master = dcpImport.getJSONObject("master");

                            new CheckLoggedUserTask(poEmploye, doesExist1 -> {
                                try {
                                    if(!doesExist1) {
                                        GToast.CreateMessage(getApplication(), "Collection list not applicable for the current logged user.", GToast.WARNING).show();
                                    } else if(!loJSON_Master.getString("dReferDte").equalsIgnoreCase(new AppConstants().CURRENT_DATE)) {
                                        GToast.CreateMessage(getApplication(), "Collection list is not applicable for the current date.", GToast.WARNING).show();
                                    } else {
                                        EDCPCollectionMaster loMaster = new EDCPCollectionMaster();

                                        loMaster.setTransact(loJSON_Master.getString("dTransact"));
                                        loMaster.setBranchNm(loJSON_Master.getString("sBranchNm"));
                                        loMaster.setTransNox(loJSON_Master.getString("sTransNox"));
                                        loMaster.setDCPTypex(loJSON_Master.getString("cDCPTypex"));
                                        loMaster.setEntryNox(loJSON_Master.getString("nEntryNox"));
                                        loMaster.setCollName(loJSON_Master.getString("xCollName"));
                                        loMaster.setCollctID(loJSON_Master.getString("sCollctID"));
                                        loMaster.setTranStat(loJSON_Master.getString("cTranStat"));
                                        loMaster.setRouteNme(loJSON_Master.getString("sRouteNme"));
                                        loMaster.setReferDte(loJSON_Master.getString("dReferDte"));
                                        loMaster.setReferNox(loJSON_Master.getString("sReferNox"));

                                        JSONArray loJArray_detail = dcpImport.getJSONArray("detail");
                                        List<EDCPCollectionDetail> loCollectDetlList = new ArrayList<>(); // This is return
                                        for (int x = 0; x < loJArray_detail.length(); x++) {
                                            EDCPCollectionDetail loDetail = new EDCPCollectionDetail();
                                            JSONObject loJson = loJArray_detail.getJSONObject(x);

                                            loDetail.setTransNox(loJSON_Master.getString("sTransNox"));
                                            loDetail.setApntUnit(loJson.getString("cApntUnit"));
                                            loDetail.setLongitud(loJson.getString("nLongitud"));
                                            loDetail.setAddressx(loJson.getString("sAddressx"));
                                            loDetail.setBrgyName(loJson.getString("sBrgyName"));
                                            loDetail.setEntryNox(Integer.parseInt(loJson.getString("nEntryNox")));
                                            loDetail.setClientID(loJson.getString("sClientID"));
                                            loDetail.setTownName(loJson.getString("sTownName"));
//                                            loDetail.setPurchase(loJson.getString("dPurchase"));
                                            loDetail.setIsDCPxxx(loJson.getString("cIsDCPxxx"));
                                            loDetail.setSerialID(loJson.getString("sSerialID"));
                                            loDetail.setFullName(loJson.getString("xFullName"));
                                            loDetail.setDueDatex(loJson.getString("dDueDatex"));
                                            loDetail.setLatitude(loJson.getString("nLatitude"));
                                            loDetail.setMobileNo(loJson.getString("sMobileNo"));
                                            loDetail.setAmtDuexx(loJson.getString("nAmtDuexx"));
                                            loDetail.setHouseNox(loJson.getString("sHouseNox"));
                                            loDetail.setSerialNo(loJson.getString("sSerialNo"));
                                            loDetail.setAcctNmbr(loJson.getString("sAcctNmbr"));
                                            loDetail.setLastPaym(loJson.getString("nLastPaym"));
                                            loDetail.setLastPaid(loJson.getString("dLastPaym"));
                                            loDetail.setABalance(loJson.getString("nABalance"));
                                            loDetail.setDelayAvg(loJson.getString("nDelayAvg"));
                                            loDetail.setMonAmort(loJson.getString("nMonAmort"));
                                            loCollectDetlList.add(loDetail);
                                        }
                                        callback.OnDataExtract(loCollectDetlList, loMaster);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }).execute(loJSON_Master.getString("sCollctID"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).execute(dcpImport.toString());

            } else {
                GToast.CreateMessage(getApplication(),"File contains invalid data format.", GToast.ERROR).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean importDCPMasterData(EDCPCollectionMaster collectionMaster) {
        try {
            poDCPRepo.insertMasterData(collectionMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean importDCPListBulkData(List<EDCPCollectionDetail> collectionDetails) {
        try {
            poDCPRepo.insertDetailBulkData(collectionDetails);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // TODO: Import DCP List --- END


    public void importInsuranceInfo(String clientName, OnDownloadClientList callback){
        try{
            List<EDCPCollectionDetail> laDetail = collectionList.getValue();
            if(laDetail.size() > 0) {
                JSONObject loJson = new JSONObject();
                loJson.put("value", clientName);
                loJson.put("bycode", false);
                new ImportData(instance, psTransNox.getValue(), pnEntryNox.getValue(), poApi.getUrlGetRegClient(), callback).execute(loJson);
            } else {
                callback.OnFailedDownload("Please download or import collection detail before adding.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public JSONObject getExportDataList(String remarks, FileManagerCallBack callBack) {
        ExportDCPFileTask exportAsync = new ExportDCPFileTask(remarks, callBack);
        exportAsync.execute(plDetail.getValue());
        return exportAsync.JSONExport;
    }

    public void insertAddedCollectionDetail(EDCPCollectionDetail foDetail, RDailyCollectionPlan.OnClientAccNoxInserted listener){
        poDCPRepo.insertCollectionDetail(foDetail, listener);
    }

    public void importARClientInfo(String clientName, OnDownloadClientList callback){
        try{
            List<EDCPCollectionDetail> laDetail = collectionList.getValue();
            if(laDetail.size() > 0) {
                /**
                 * validation if client already exist has been remove in this area...
                 * validations will be triggered upon user add the selected client on search list
                 */
                JSONObject loJson = new JSONObject();
                loJson.put("value", clientName);
                loJson.put("bycode", false);
                String lsTransNox = psTransNox.getValue();
                int lnEntryNox = pnEntryNox.getValue();
                new ImportData(instance, lsTransNox, lnEntryNox, poApi.getUrlGetRegClient(), callback).execute(loJson);
            } else {
                callback.OnFailedDownload("Please download or import collection detail before adding.");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportData extends AsyncTask<JSONObject, Void, String> {
        private final HttpHeaders headers;
        private final RDailyCollectionPlan dcpRepo;
        private final ConnectionUtil conn;
        private final OnDownloadClientList callback;
        private final String sTransNox;
        private final int nEntryNox;
        private final String Url;
        private final List<EDCPCollectionDetail> collectionDetails = new ArrayList<>();

        public ImportData(Application instance, String TransNox, int EntryNox, String Url, OnDownloadClientList callback) {
            this.headers = HttpHeaders.getInstance(instance);
            this.dcpRepo = new RDailyCollectionPlan(instance);
            this.conn = new ConnectionUtil(instance);
            this.callback = callback;
            this.sTransNox = TransNox;
            this.nEntryNox = EntryNox;
            this.Url = Url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownload();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... strings) {
            String response = "";
            try{
                if(conn.isDeviceConnected()) {
                    response = WebClient.sendRequest(Url, strings[0].toString(), headers.getHeaders());
                    JSONObject jsonResponse = new JSONObject(response);
                    String lsResult = jsonResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        addSearchListToArrayList(jsonResponse.getJSONArray("data"));
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
                    callback.OnSuccessDownload(collectionDetails);
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailedDownload(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.OnFailedDownload(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailedDownload(e.getMessage());
            }
        }

        void addSearchListToArrayList(JSONArray foData) throws JSONException {
            for(int x = 0; x < foData.length(); x++) {
                JSONObject loJson = foData.getJSONObject(x);
                EDCPCollectionDetail collectionDetail = new EDCPCollectionDetail();
                collectionDetail.setTransNox(sTransNox);
                collectionDetail.setEntryNox(nEntryNox);
                try {
                    collectionDetail.setAcctNmbr(loJson.getString("sAcctNmbr"));
                } catch (Exception e){
                    e.printStackTrace();
                }
                collectionDetail.setFullName(loJson.getString("xFullName"));
                collectionDetail.setIsDCPxxx("0");
                collectionDetail.setMobileNo(loJson.getString("sMobileNo"));
                collectionDetail.setHouseNox(loJson.getString("sHouseNox"));
                collectionDetail.setAddressx(loJson.getString("sAddressx"));
                collectionDetail.setBrgyName(loJson.getString("sBrgyName"));
                collectionDetail.setTownName(loJson.getString("sTownName"));
                collectionDetail.setClientID(loJson.getString("sClientID"));
                collectionDetail.setSerialID(loJson.getString("sSerialID"));
                collectionDetail.setSerialNo(loJson.getString("sSerialNo"));
                collectionDetail.setLongitud(loJson.getString("nLongitud"));
                collectionDetail.setLatitude(loJson.getString("nLatitude"));
                collectionDetail.setDueDatex(loJson.getString("dDueDatex"));
                collectionDetail.setMonAmort(loJson.getString("nMonAmort"));
                collectionDetail.setLastPaym(loJson.getString("nLastPaym"));
                collectionDetail.setLastPaid(loJson.getString("dLastPaym"));
                collectionDetail.setAmtDuexx(loJson.getString("nAmtDuexx"));
                collectionDetail.setABalance(loJson.getString("nABalance"));
                collectionDetail.setDelayAvg(loJson.getString("nDelayAvg"));
                collectionDetails.add(collectionDetail);
            }
        }
    }

    private static class ImportLRCollection extends AsyncTask<JSONObject, Void, String>{
        private final HttpHeaders headers;
        private final RDailyCollectionPlan dcpRepo;
        private final ConnectionUtil conn;
        private final WebApi webApi;
        private final OnDownloadCollection callback;
        private final List<EDCPCollectionMaster> masterList;

        public ImportLRCollection(Application instance, List<EDCPCollectionMaster> masterList, OnDownloadCollection callback) {
            this.headers = HttpHeaders.getInstance(instance);
            this.dcpRepo = new RDailyCollectionPlan(instance);
            this.conn = new ConnectionUtil(instance);
            this.webApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
            this.masterList = masterList;
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownload();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... strings) {
            String response = "";
            try{
                if(conn.isDeviceConnected()) {
                    response = WebClient.sendRequest(webApi.getUrlDownloadDcp(), strings[0].toString(), headers.getHeaders());
                    JSONObject jsonResponse = new JSONObject(response);
                    String lsResult = jsonResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        if(!saveMasterDataToLocal(jsonResponse)){
                            response = AppConstants.LOCAL_EXCEPTION_ERROR("Collection list already exist");
                        }
                    }
                } else {
                    response = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e){
                e.printStackTrace();
                response = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
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
                    callback.OnSuccessDownload();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnDownloadFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.OnDownloadFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnDownloadFailed(e.getMessage());
            }
        }

        boolean saveMasterDataToLocal(JSONObject foJson) throws Exception {
            JSONObject loJson = foJson.getJSONObject("master");
            String lsTransNox = loJson.getString("sTransNox");

            List<EDCPCollectionMaster> laMaster = dcpRepo.getCollectionMasterIfExist(lsTransNox);
            if(laMaster.size() <= 0) {
                EDCPCollectionMaster collectionMaster = new EDCPCollectionMaster();
                collectionMaster.setTransNox(loJson.getString("sTransNox"));
                collectionMaster.setTransact(loJson.getString("dTransact"));
                collectionMaster.setReferNox(loJson.getString("sReferNox"));
                collectionMaster.setCollName(loJson.getString("xCollName"));
                collectionMaster.setRouteNme(loJson.getString("sRouteNme"));
                collectionMaster.setReferDte(loJson.getString("dReferDte"));
                collectionMaster.setTranStat(loJson.getString("cTranStat"));
                collectionMaster.setDCPTypex(loJson.getString("cDCPTypex"));
                collectionMaster.setEntryNox(loJson.getString("nEntryNox"));
                collectionMaster.setBranchNm(loJson.getString("sBranchNm"));
                collectionMaster.setCollctID(loJson.getString("sCollctID"));
                JSONArray laJson = foJson.getJSONArray("detail");
                dcpRepo.insertMasterData(collectionMaster);
                saveDetailDataToLocal(laJson, loJson);
                return true;
            } else {
                return false;
            }
        }

        void saveDetailDataToLocal(JSONArray faJson, JSONObject jsonMaster) throws JSONException {
            List<EDCPCollectionDetail> collectionDetails = new ArrayList<>();
            for(int x = 0; x < faJson.length(); x++){
                JSONObject loJson = faJson.getJSONObject(x);
                EDCPCollectionDetail collectionDetail = new EDCPCollectionDetail();
                collectionDetail.setTransNox(jsonMaster.getString("sTransNox"));
                collectionDetail.setEntryNox(Integer.parseInt(loJson.getString("nEntryNox")));
                collectionDetail.setAcctNmbr(loJson.getString("sAcctNmbr"));
                collectionDetail.setFullName(loJson.getString("xFullName"));
                collectionDetail.setIsDCPxxx(loJson.getString("cIsDCPxxx"));
                collectionDetail.setMobileNo(loJson.getString("sMobileNo"));
                collectionDetail.setHouseNox(loJson.getString("sHouseNox"));
                collectionDetail.setAddressx(loJson.getString("sAddressx"));
                collectionDetail.setBrgyName(loJson.getString("sBrgyName"));
                collectionDetail.setTownName(loJson.getString("sTownName"));
                collectionDetail.setPurchase(loJson.getString("dPurchase"));
                collectionDetail.setAmtDuexx(loJson.getString("nAmtDuexx"));
                collectionDetail.setApntUnit(loJson.getString("cApntUnit"));
                collectionDetail.setDueDatex(loJson.getString("dDueDatex"));
                collectionDetail.setLongitud(loJson.getString("nLongitud"));
                collectionDetail.setLatitude(loJson.getString("nLatitude"));
                collectionDetail.setClientID(loJson.getString("sClientID"));
                collectionDetail.setSerialID(loJson.getString("sSerialID"));
                collectionDetail.setSerialNo(loJson.getString("sSerialNo"));
                collectionDetail.setLastPaym(loJson.getString("nLastPaym"));
                collectionDetail.setLastPaid(loJson.getString("dLastPaym"));
                collectionDetail.setABalance(loJson.getString("nABalance"));
                collectionDetail.setDelayAvg(loJson.getString("nDelayAvg"));
                collectionDetail.setMonAmort(loJson.getString("nMonAmort"));
                collectionDetails.add(collectionDetail);
            }
            dcpRepo.insertDetailBulkData(collectionDetails);
        }
    }

    public void setCollectionListForPosting(List<DDCPCollectionDetail.CollectionDetail> collectionDetails){
        this.plDetail.setValue(collectionDetails);
    }

    public LiveData<List<DDCPCollectionDetail.CollectionDetail>> getCollectionDetailForPosting(){
        return poDCPRepo.getCollectionDetailForPosting();
    }

//    public void PostLRCollectionDetail(String Remarks, ViewModelCallback callback){
//        new PostLRCollectionDetail(instance, Remarks,  plAddress.getValue(), plMobile.getValue(), callback).execute(plDetail.getValue());
//    }

    public class PostLRCollectionDetail extends AsyncTask<List<DDCPCollectionDetail.CollectionDetail>, Void, String>{
        private final ConnectionUtil poConn;
        private final ViewModelCallback callback;
        private final SessionManager poUser;
        private final RDailyCollectionPlan poDcp;
        private final RImageInfo poImage;
        private final Telephony poTelephony;
        private final HttpHeaders poHeaders;
        private final RCollectionUpdate rCollect;
        private final GRiderErrorReport poReport;
        private final List<EAddressUpdate> paAddress;
        private final List<EMobileUpdate> paMobile;
        private final String sRemarksx;

        public PostLRCollectionDetail(Application instance, String Remarksx,List<EAddressUpdate> faAddress, List<EMobileUpdate> faMobile, ViewModelCallback callback){
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new SessionManager(instance);
            this.paAddress = faAddress;
            this.paMobile = faMobile;
            this.callback = callback;
            this.poDcp = new RDailyCollectionPlan(instance);
            this.poImage = new RImageInfo(instance);
            this.poTelephony = new Telephony(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.rCollect = new RCollectionUpdate(instance);
            this.poReport = new GRiderErrorReport(instance);
            this.sRemarksx = Remarksx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }

        @SafeVarargs
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected final String doInBackground(List<DDCPCollectionDetail.CollectionDetail>... lists) {
            String lsResult;
            UnpostedDCP loUnposted;
            String[] params;

            List<DDCPCollectionDetail.CollectionDetail> laCollDetl = lists[0];
            try {
                String lsTransNox;
                if(hasRemittedBeforePosting()) {
                    if (!poConn.isDeviceConnected()) {
                        lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Not connected to internet. Tap 'Okay' to export backup file.");
                    } else {
                        String lsClient = WebFileServer.RequestClientToken(poConfig.ProducID(), poUser.getClientId(), poUser.getUserID());
                        String lsAccess = WebFileServer.RequestAccessToken(lsClient);
                        if (lsClient.isEmpty() || lsAccess.isEmpty()) {
                            lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Failed to request generated Client or Access token.");
                        } else {
                            if (laCollDetl.size() > 0) {
                                params = new String[laCollDetl.size()];
                                lsTransNox = laCollDetl.get(0).sTransNox;
                                for (int x = 0; x < laCollDetl.size(); x++) {
                                    if(lsTransNox == null) {
                                        lsTransNox = laCollDetl.get(x).sTransNox;
                                    }
                                    try {
                                        DDCPCollectionDetail.CollectionDetail loDetail = laCollDetl.get(x);

                                        JSONObject loData = new JSONObject();

                                        if (!loDetail.sRemCodex.isEmpty()) {

                                            org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                                                    loDetail.sFileLoct,
                                                    lsAccess,
                                                    loDetail.sFileCode,
                                                    loDetail.sAcctNmbr,
                                                    loDetail.sImageNme,
                                                    poUser.getBranchCode(),
                                                    loDetail.sSourceCD,
                                                    loDetail.sTransNox,
                                                    "");

                                            String lsResponse = (String) loUpload.get("result");
                                            if(lsResponse == null){
                                                JSONObject imgParams = new JSONObject();
                                                imgParams.put("sFileLoct", loDetail.sFileLoct);
                                                imgParams.put("lsAccess", lsAccess);
                                                imgParams.put("sFileCode", loDetail.sFileCode);
                                                imgParams.put("sAcctNmbr", loDetail.sAcctNmbr);
                                                imgParams.put("sImageNme", loDetail.sImageNme);
                                                imgParams.put("sSourceCD", loDetail.sSourceCD);
                                                imgParams.put("sTransNox", loDetail.sTransNox);
                                                imgParams.put("sBranchCD", poUser.getBranchCode());
                                                loUnposted = new UnpostedDCP(loDetail.sAcctNmbr,
                                                        loDetail.sRemCodex,
                                                        imgParams,
                                                        "Failed to upload collection image. Server no response.");
                                                poReport.SendErrorReport("DCP Error Report", "Unable to post DCP. \n" + loUnposted.getMessage());
                                            } else if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                                                String lsTransNo = (String) loUpload.get("sTransNox");
                                                poImage.updateImageInfo(lsTransNo, loDetail.sImageIDx);

                                                Thread.sleep(1000);

                                            } else {
                                                loUnposted = new UnpostedDCP(loDetail.sAcctNmbr,
                                                        loDetail.sRemCodex,
                                                        new JSONObject(),
                                                        "Server no response while uploading dcp image");
                                                poReport.SendErrorReport("DCP Error Report", "Unable to post DCP. \n" + loUnposted.getMessage());
                                            }

                                            if (loDetail.sRemCodex.equalsIgnoreCase("PAY")) {
                                                loData.put("sPRNoxxxx", loDetail.sPRNoxxxx);
                                                loData.put("nTranAmtx", loDetail.nTranAmtx);
                                                loData.put("nDiscount", loDetail.nDiscount);
                                                loData.put("nOthersxx", loDetail.nOthersxx);
                                                loData.put("cTranType", loDetail.cTranType);
                                                loData.put("nTranTotl", loDetail.nTranTotl);
//                                               Added by Jonathan 07/27/2021
                                                loData.put("sRemarksx", loDetail.sRemarksx);
                                            } else if (loDetail.sRemCodex.equalsIgnoreCase("PTP")) {
                                                //Required parameters for Promise to pay..
                                                loData.put("cApntUnit", loDetail.cApntUnit);
                                                loData.put("sBranchCd", loDetail.sBranchCd);
                                                loData.put("dPromised", loDetail.dPromised);

                                                loData.put("sImageNme", loDetail.sImageNme);
                                                loData.put("sSourceCD", loDetail.sSourceCD);
                                                loData.put("nLongitud", loDetail.nLongitud);
                                                loData.put("nLatitude", loDetail.nLatitude);

                                            } else if (loDetail.sRemCodex.equalsIgnoreCase("LUn") ||
                                                    loDetail.sRemCodex.equalsIgnoreCase("TA") ||
                                                    loDetail.sRemCodex.equalsIgnoreCase("FO")) {

                                                //TODO: replace JSON parameters get the parameters which is being generated by RClientUpdate...
                                                loData.put("sLastName", loDetail.sLastName);
                                                loData.put("sFrstName", loDetail.sFrstName);
                                                loData.put("sMiddName", loDetail.sMiddName);
                                                loData.put("sSuffixNm", loDetail.sSuffixNm);
                                                loData.put("sHouseNox", loDetail.sHouseNox);
                                                loData.put("sAddressx", loDetail.sAddressx);
                                                loData.put("sTownIDxx", loDetail.sTownIDxx);
                                                loData.put("cGenderxx", loDetail.cGenderxx);
                                                loData.put("cCivlStat", loDetail.cCivlStat);
                                                loData.put("dBirthDte", loDetail.dBirthDte);
                                                loData.put("dBirthPlc", loDetail.dBirthPlc);
                                                loData.put("sLandline", loDetail.sLandline);
                                                loData.put("sMobileNo", loDetail.sMobileNo);
                                                loData.put("sEmailAdd", loDetail.sEmailAdd);

                                                loData.put("sImageNme", loDetail.sImageNme);
                                                loData.put("sSourceCD", loDetail.sSourceCD);
                                                loData.put("nLongitud", loDetail.nLongitud);
                                                loData.put("nLatitude", loDetail.nLatitude);
                                            } else {
                                                loData.put("sImageNme", loDetail.sImageNme);
                                                loData.put("sSourceCD", loDetail.sSourceCD);
                                                loData.put("nLongitud", loDetail.nLongitud);
                                                loData.put("nLatitude", loDetail.nLatitude);
                                            }

                                            loData.put("sRemarksx", loDetail.sRemarksx);
                                        } else {
                                            loData.put("sRemarksx", sRemarksx);
                                        }

                                        JSONObject loJson = new JSONObject();
                                        loJson.put("sTransNox", loDetail.sTransNox);
                                        loJson.put("nEntryNox", loDetail.nEntryNox);
                                        loJson.put("sAcctNmbr", loDetail.sAcctNmbr);
                                        if (loDetail.sRemCodex.isEmpty()) {
                                            loJson.put("sRemCodex", "NV");
                                            loJson.put("dModified", new AppConstants().DATE_MODIFIED);
                                        } else {
                                            loJson.put("sRemCodex", loDetail.sRemCodex);
                                            loJson.put("dModified", loDetail.dModified);
                                        }

                                        loJson.put("sJsonData", loData);
                                        loJson.put("dReceived", "");
                                        loJson.put("sUserIDxx", poUser.getUserID());
                                        loJson.put("sDeviceID", poTelephony.getDeviceID());
                                        params[x] =loJson.toString() + " \n";
                                        String lsResponse1 = WebClient.sendRequest(poApi.getUrlDcpSubmit(), loJson.toString(), poHeaders.getHeaders());
                                        if (lsResponse1 == null) {
                                            loUnposted = new UnpostedDCP(loDetail.sAcctNmbr,
                                                    loDetail.sRemCodex,
                                                    loJson,
                                                    "Server no response");
                                            poReport.SendErrorReport("DCP Error Report", "Unable to post DCP. \n" + loUnposted.getMessage());
                                        } else {
                                            JSONObject loResponse = new JSONObject(lsResponse1);

                                            String result = loResponse.getString("result");
                                            if (result.equalsIgnoreCase("success")) {
                                                if (loDetail.sRemCodex.isEmpty()) {
                                                    poDcp.updateCollectionDetailStatusWithRemarks(loDetail.sTransNox, loDetail.nEntryNox, sRemarksx);
                                                } else {
                                                    poDcp.updateCollectionDetailStatus(loDetail.sTransNox, loDetail.nEntryNox);
                                                }
                                            } else {
                                                JSONObject loError = loResponse.getJSONObject("error");
                                                String lsMessage = loError.getString("message");

                                                loUnposted = new UnpostedDCP(loDetail.sAcctNmbr,
                                                        loDetail.sRemCodex,
                                                        loJson,
                                                        lsMessage);
                                                poReport.SendErrorReport("DCP Error Report", "Unable to post DCP. \n" + loUnposted.getMessage());
                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        loUnposted = new UnpostedDCP(laCollDetl.get(x).sAcctNmbr,
                                                laCollDetl.get(x).sRemCodex,
                                                new JSONObject(),
                                                e.getMessage() + "\n " +Arrays.toString(e.getStackTrace()));
                                        poReport.SendErrorReport("DCP Error Report", "Unable to post DCP. \n" + loUnposted.getMessage());
                                    }

                                    Thread.sleep(1000);
                                }

                                //call sending CNA details....
                                sendCNADetails(lsTransNox);
                                int UnPostedDcp = poDcp.getUnsentCollectionDetail(lsTransNox);
                                int UnsentAccnt = poDcp.getAccountNoCount(lsTransNox);

                                Thread.sleep(1000);

                                if(UnsentAccnt == 0){
                                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("No customers account has been collected.");//
                                } else if (UnPostedDcp == 0){
                                    JSONObject loJson = new JSONObject();
                                    loJson.put("sTransNox", lsTransNox);
                                    String lsResponse1 = WebClient.sendRequest(poApi.getUrlPostDcpMaster(), loJson.toString(), poHeaders.getHeaders());
                                    if (lsResponse1 == null) {
                                        lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Server no response on posting DCP master detail. Tap 'Okay' to create dcp file for backup");
                                    } else {
                                        JSONObject loResponse = new JSONObject(lsResponse1);
                                        String result = loResponse.getString("result");
                                        if (result.equalsIgnoreCase("success")) {
                                            poDcp.updateSentPostedDCPMaster(laCollDetl.get(0).sTransNox);
                                            lsResult = loResponse.toString();
                                        } else {
                                            JSONObject loError = loResponse.getJSONObject("error");
                                            lsResult = loError.toString();
                                        }
                                    }
                                } else {
                                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("An error occurred while posting dcp. Please try again.");//
                                }
                            } else {
                                // TODO: Display no details to post
                                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("No available collection details to post.");
                            }
                        }
                    }
                } else {
                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Please remit collection before posting");
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
                poReport.SendErrorReport("DCP Error Report", "Unable to post DCP. \n" + e.getMessage() + " \n " + Arrays.toString(e.getStackTrace()));
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                if(loJson.getString("result").equalsIgnoreCase("success")){
                    callback.OnSuccessResult(new String[]{"Collection for today has been posted successfully. Tap 'Okay' to create dcp file for backup"});
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    callback.OnFailedResult(loError.getString("message"));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(true);
        }

        boolean hasRemittedBeforePosting(){
            DDCPCollectionDetail.DCP_Posting_Validation_Data loPost = poDcp.getValidationData();
            if(loPost.Paid_Collection > 0){
                if(loPost.Remitted_Collection == null){
                    return false;
                } else return loPost.Remitted_Collection.equalsIgnoreCase("0") ||
                        loPost.Remitted_Collection.equalsIgnoreCase("0.0");
            } else {
                return true;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        void sendCNADetails(String fsTransno) throws Exception {
            if (paAddress.size() == 0) {
                Log.e(TAG, "paAddress is Empty");
            } else {
                for (int i = 0; i < paAddress.size(); i++) {
                    EAddressUpdate info = paAddress.get(i);
                    JSONObject param = new JSONObject();
                    param.put("sTransNox", info.getTransNox());
                    param.put("sClientID", info.getClientID());
                    param.put("cReqstCDe", info.getReqstCDe());
                    param.put("cAddrssTp", info.getAddrssTp());
                    param.put("sHouseNox", info.getHouseNox());
                    param.put("sAddressx", info.getAddressx());
                    param.put("sTownIDxx", info.getTownIDxx());
                    param.put("sBrgyIDxx", info.getBrgyIDxx());
                    param.put("cPrimaryx", info.getPrimaryx());
                    Log.e("Latitude", info.getLatitude());
                    param.put("nLatitude", Double.parseDouble(info.getLatitude()));
                    Log.e("Longitude", info.getLongitud());
                    param.put("nLongitud", Double.parseDouble(info.getLongitud()));
                    param.put("sRemarksx", info.getRemarksx());
                    param.put("sSourceCD", "DCPa");
                    param.put("sSourceNo", fsTransno);

                    Log.e("Address JsonParam", param.toString());

                    String lsAddressUpdtResponse = WebClient.sendRequest(poApi.getUrlUpdateAddress(), param.toString(), poHeaders.getHeaders());

                    if (lsAddressUpdtResponse == null) {
                        Log.e("Address Update Result:", "Server no Repsonse");
                    } else {
                        JSONObject loResult = new JSONObject(lsAddressUpdtResponse);
                        String result = loResult.getString("result");
                        Log.e("The Address Result", String.valueOf(loResult));
                        if (result.equalsIgnoreCase("success")) {
                            String newTransNox = loResult.getString("sTransNox");
                            rCollect.updateAddressStatus(newTransNox, info.getTransNox());
                            Log.e("Address Update Result:", result);
                        } else {
                            Log.e("Address Update Result:", "Failed");
                        }
                    }
                    Thread.sleep(1000);
                }
            }

            if (paMobile.size() == 0) {
                Log.e(TAG, "paMobile is Empty.");
            } else {
                for (int y = 0; y < paMobile.size(); y++) {
                    EMobileUpdate info = paMobile.get(y);
                    JSONObject param = new JSONObject();
                    param.put("sTransNox", info.getTransNox());
                    param.put("sClientID", info.getClientID());
                    param.put("cReqstCDe", info.getReqstCDe());
                    param.put("sMobileNo", info.getMobileNo());
                    param.put("cPrimaryx", info.getPrimaryx());
                    param.put("sRemarksx", info.getRemarksx());
                    param.put("sSourceCD", "DCPa");
                    param.put("sSourceNo", fsTransno);

                    Log.e("Mobile JsonParam", param.toString());

                    String lsMobileUpdtResponse = WebClient.sendRequest(poApi.getUrlUpdateMobile(), param.toString(), poHeaders.getHeaders());

                    if (lsMobileUpdtResponse == null) {
                        Log.e("Mobile Update Result:", "Server no Repsonse");
                    } else {
                        JSONObject loMobResult = new JSONObject(lsMobileUpdtResponse);
                        String result = loMobResult.getString("result");
//                                        Log.e("The Mobile Result", String.valueOf(loMobResult));
                        if (result.equalsIgnoreCase("success")) {
                            String newTransNox = loMobResult.getString("sTransNox");
                            rCollect.updateMobileStatus(newTransNox, info.getTransNox());
                            Log.e("Mobile Update Result:", result);
                        } else {
                            Log.e("Mobile Update Result:", "Failed");
                        }
                    }
                    Thread.sleep(1000);
                }
            }
        }
    }

    public interface FileManagerCallBack{
        void OnJSONCreated(JSONObject loJson);
        void OnStartSaving();
        void OnSuccessResult(String[] args);
        void OnFailedResult(String message);
    }

    private static class ExportDCPFileTask extends AsyncTask<List<DDCPCollectionDetail.CollectionDetail>, Void, JSONObject>{
        private JSONObject JSONExport;
        private final JSONArray JSONArrayExport;
        private final String sRemarkx;
        private final FileManagerCallBack callBack;

        public ExportDCPFileTask(String sRemarkx, FileManagerCallBack callBack) {
            this.sRemarkx = sRemarkx;
            this.JSONExport = new JSONObject();
            this.JSONArrayExport = new JSONArray();
            this.callBack = callBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callBack.OnStartSaving();
        }

        @Override
        protected JSONObject doInBackground(List<DDCPCollectionDetail.CollectionDetail>... lists) {
            // TODO: START

            List<DDCPCollectionDetail.CollectionDetail> laCollDetl = lists[0];

            for(int x = 0; x < laCollDetl.size(); x++) {
                try {
                    DDCPCollectionDetail.CollectionDetail loDetail = laCollDetl.get(x);

                    JSONObject loData = new JSONObject();

                    if(loDetail.sRemCodex != null) {

                        if (loDetail.sRemCodex.equalsIgnoreCase("PAY")) {
                            loData.put("sPRNoxxxx", loDetail.sPRNoxxxx);
                            loData.put("nTranAmtx", loDetail.nTranAmtx);
                            loData.put("nDiscount", loDetail.nDiscount);
                            loData.put("nOthersxx", loDetail.nOthersxx);
                            loData.put("cTranType", loDetail.cTranType);
                            loData.put("nTranTotl", loDetail.nTranTotl);
                        } else if (loDetail.sRemCodex.equalsIgnoreCase("PTP")) {
                            //Required parameters for Promise to pay..
                            loData.put("cApntUnit", loDetail.cApntUnit);
                            loData.put("sBranchCd", loDetail.sBranchCd);
                            loData.put("dPromised", loDetail.dPromised);
                            loData.put("sImageNme", loDetail.sImageNme);
                            loData.put("nLongitud", loDetail.nLongitud);
                            loData.put("nLatitude", loDetail.nLatitude);

                        } else if (loDetail.sRemCodex.equalsIgnoreCase("LUn") ||
                                loDetail.sRemCodex.equalsIgnoreCase("TA") ||
                                loDetail.sRemCodex.equalsIgnoreCase("FO")) {

                            //TODO: replace JSON parameters get the parameters which is being generated by RClientUpdate...
                            loData.put("sLastName", loDetail.sLastName);
                            loData.put("sFrstName", loDetail.sFrstName);
                            loData.put("sMiddName", loDetail.sMiddName);
                            loData.put("sSuffixNm", loDetail.sSuffixNm);
                            loData.put("sHouseNox", loDetail.sHouseNox);
                            loData.put("sAddressx", loDetail.sAddressx);
                            loData.put("sTownIDxx", loDetail.sTownIDxx);
                            loData.put("cGenderxx", loDetail.cGenderxx);
                            loData.put("cCivlStat", loDetail.cCivlStat);
                            loData.put("dBirthDte", loDetail.dBirthDte);
                            loData.put("dBirthPlc", loDetail.dBirthPlc);
                            loData.put("sLandline", loDetail.sLandline);
                            loData.put("sMobileNo", loDetail.sMobileNo);
                            loData.put("sEmailAdd", loDetail.sEmailAdd);
                        } else if (loDetail.sRemCodex.equalsIgnoreCase("CNA")) {
                            //Mobile
                            if(loDetail.smReqstCde != null &&
                                    loDetail.smContactNox != null &&
                                    loDetail.smRemarksx !=null) {
                                JSONObject paramMobile = new JSONObject();
                                paramMobile.put("cReqstCDe", loDetail.smReqstCde);
                                paramMobile.put("sMobileNo", loDetail.smContactNox);
                                paramMobile.put("cPrimaryx", loDetail.smPrimaryx);
                                paramMobile.put("sImageNme", loDetail.sImageNme);
                                paramMobile.put("sRemarksx", loDetail.smRemarksx);
                                Log.e("paramMobile", paramMobile.toString());
                                loData.put("Mobile", paramMobile);
                            }
                            // Address
                            else if(loDetail.saReqstCde != null &&
                                    loDetail.saAddrsTp != null &&
                                    loDetail.saHouseNox != null &&
                                    loDetail.saAddress != null &&
                                    loDetail.saTownIDxx != null &&
                                    loDetail.saBrgyIDxx != null &&
                                    loDetail.saRemarksx != null) {
                                JSONObject paramAddress = new JSONObject();
                                paramAddress.put("cReqstCDe", loDetail.saReqstCde);
                                paramAddress.put("cAddrssTp", loDetail.saAddrsTp);
                                paramAddress.put("sHouseNox", loDetail.saHouseNox);
                                paramAddress.put("sAddressx", loDetail.saAddress);
                                paramAddress.put("sTownIDxx", loDetail.saTownIDxx);
                                paramAddress.put("sBrgyIDxx", loDetail.saBrgyIDxx);
                                paramAddress.put("cPrimaryx", loDetail.saPrimaryx);
                                paramAddress.put("sImageNme", loDetail.sImageNme);
                                paramAddress.put("nLatitude", Double.parseDouble(loDetail.saLatitude));
                                paramAddress.put("nLongitud", Double.parseDouble(loDetail.saLongitude));
                                paramAddress.put("sRemarksx", loDetail.saRemarksx);
                                Log.e("paramAddress", paramAddress.toString());
                                loData.put("Address", paramAddress);
                            }

                        } else {
                            loData.put("sImageNme", loDetail.sImageNme);
                            loData.put("nLongitud", loDetail.nLongitud);
                            loData.put("nLatitude", loDetail.nLatitude);
                        }
                        loData.put("sRemarksx", loDetail.sRemarksx);
                    } else {
                        loData.put("sRemarksx", sRemarkx);
                    }

                    JSONObject loJson = new JSONObject();
                    loJson.put("sTransNox", loDetail.sTransNox);
                    loJson.put("nEntryNox", loDetail.nEntryNox);
                    loJson.put("sAcctNmbr", loDetail.sAcctNmbr);
                    if (loDetail.sRemCodex.isEmpty()) {
                        loJson.put("sRemCodex", "NV");
                        loJson.put("dModified", new AppConstants().DATE_MODIFIED);
                    } else {
                        loJson.put("sRemCodex", loDetail.sRemCodex);
                        loJson.put("dModified", loDetail.dModified);
                    }
                    loJson.put("sJsonData", loData);
                    JSONArrayExport.put(loJson);
                } catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }

            try {
                if(JSONArrayExport.length() == 0) { return null; }
                JSONExport.put("android", JSONArrayExport);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            Log.e(TAG, JSONExport.toString());
            return JSONExport;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            JSONExport = jsonObject;
            if(JSONExport != null){
                callBack.OnJSONCreated(jsonObject);
                callBack.OnSuccessResult(new String[]{"Collection list exported successfully. Tap 'Okay' to create dcp file for backup"});
            } else {
                callBack.OnFailedResult(("No available collection list to export. Tap 'Okay' to create dcp file for backup"));
            }
        }
    }

    private static class CheckImportDataTask extends AsyncTask<String, Void, Boolean>{
        private final RDailyCollectionPlan poDCPRepo;
        private final CheckImport mListener;

        public CheckImportDataTask(RDailyCollectionPlan poDCPRepo, CheckImport mListener) {
            this.poDCPRepo = poDCPRepo;
            this.mListener = mListener;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                JSONObject loJson = new JSONObject(strings[0]);
                JSONArray loArrray_detl = loJson.getJSONArray("detail");
                JSONObject loJson_master = loJson.getJSONObject("master");

                for(int i = 0; i < loArrray_detl.length(); i++) {
                    JSONObject loJson_detl = loArrray_detl.getJSONObject(i);
                    EDCPCollectionDetail poDCPEntity = poDCPRepo.checkCollectionImport(
                            loJson_master.getString("sTransNox"),
                            Integer.parseInt(loJson_detl.getString("nEntryNox")));
                    if(poDCPEntity != null) {
                        return true;
                    }
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.OnCheck(aBoolean);
        }
    }

    private static class CheckLoggedUserTask extends AsyncTask<String, Void, Boolean> {

        private final REmployee poEmploye;
        private final CheckImport mListener;

        public CheckLoggedUserTask(REmployee poEmploye, CheckImport mListener) {
            this.mListener = mListener;
            this.poEmploye = poEmploye;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String lsUserId = poEmploye.getUserNonLiveData().getEmployID();
            if(strings[0].equals(lsUserId)) {
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.OnCheck(aBoolean);
        }
    }

    private static class UpdateNotVisitedCollectionsTask extends AsyncTask<String, Void, String> {
        private final ConnectionUtil poConnect;
        private final DcpManager poDcpMngr;
        private final OnTransactionCallback poCallBck;
        private boolean isSuccess = false;

        private UpdateNotVisitedCollectionsTask(Application foApp, OnTransactionCallback foCallBck) {
            this.poConnect = new ConnectionUtil(foApp);
            this.poDcpMngr = new DcpManager(foApp);
            this.poCallBck = foCallBck;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poCallBck.onLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String lsRemarks = strings[0];
            final String[] lsResultx = {""};
            try {
                if(poConnect.isDeviceConnected()) {
                    poDcpMngr.UpdateNotVisitedCollections(lsRemarks, new DcpManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            isSuccess = true;
                            lsResultx[0] = args;
                        }

                        @Override
                        public void OnFailed(String message) {
                            lsResultx[0] = message;
                        }
                    });
                } else {
                    lsResultx[0] = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResultx[0] = e.getMessage();
            }


            return lsResultx[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(isSuccess) {
                poCallBck.onSuccess(s);
            } else {
                poCallBck.onFailed(s);
            }
        }
    }

    private static class ImportDcpMasterTask extends AsyncTask<HashMap<String, String>, Void, String> {
        private final ConnectionUtil poConnect;
        private final DcpManager poDcpMngr;
        private final OnTransactionCallback poCallBck;
        private boolean isSuccess = false;

        private ImportDcpMasterTask(Application foApp, OnTransactionCallback foCallBck) {
            this.poConnect = new ConnectionUtil(foApp);
            this.poDcpMngr = new DcpManager(foApp);
            this.poCallBck = foCallBck;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poCallBck.onLoading();
        }

        @Override
        protected String doInBackground(HashMap<String, String>... hashMaps) {
            HashMap<String, String> loDataMap = hashMaps[0];
            final String[] lsResultx = {""};

            try {
                if(poConnect.isDeviceConnected()) {
                    poDcpMngr.ImportDcpMaster(
                            loDataMap.get(KEY_EMPLOYEE_ID),
                            loDataMap.get(KEY_REFER_DATE),
                            new DcpManager.OnActionCallback() {

                        @Override
                        public void OnSuccess(String args) {
                            isSuccess = true;
                            lsResultx[0] = args;
                        }

                        @Override
                        public void OnFailed(String message) {
                            lsResultx[0] = message;
                        }

                    });
                } else {
                    lsResultx[0] = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResultx[0] = e.getMessage();
            }

            return lsResultx[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(isSuccess) {
                poCallBck.onSuccess(s);
            } else {
                poCallBck.onFailed(s);
            }
        }

    }

    private static class GetSearchListTask extends AsyncTask<String, Void, String> {
        private final ConnectionUtil poConnect;
        private final DcpManager poDcpMngr;
        private final OnCollectionNameSearch poCallBck;
        private List<EDCPCollectionDetail> poDetailx;
        private boolean isSuccess = false;

        private GetSearchListTask(Application foApp, OnCollectionNameSearch foCallBck) {
            this.poConnect = new ConnectionUtil(foApp);
            this.poDcpMngr = new DcpManager(foApp);
            this.poCallBck = foCallBck;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poCallBck.onLoading();
        }

        @Override
        protected String doInBackground(String... strings) {
            String lsClientN = strings[0];
            final String[] lsResultx = {""};

            try {
                if(poConnect.isDeviceConnected()) {
                    poDcpMngr.getSearchList(lsClientN, new DcpManager.OnSearchCallback() {
                        @Override
                        public void OnSuccess(List<EDCPCollectionDetail> foDetail) {
                            isSuccess = true;
                            poDetailx = foDetail;
                        }

                        @Override
                        public void OnFailed(String message) {
                            lsResultx[0] = message;
                        }
                    });
                } else {
                    lsResultx[0] = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResultx[0] = e.getMessage();
            }

            return lsResultx[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(isSuccess) {
                poCallBck.onSuccess(poDetailx);
            } else {
                poCallBck.onFailed(s);
            }
        }

    }

    public interface CheckImport {
        void OnCheck(boolean doesExist);
    }

    public interface ImportJSONCallback {
        void OnDataExtract(List<EDCPCollectionDetail> collectionDetlList, EDCPCollectionMaster collectionMaster);
    }

    private String readTextFile(Uri uri)
    {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try
        {
            reader = new BufferedReader(new InputStreamReader(instance.getContentResolver().openInputStream(uri)));

            String line = "";
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
            reader.close();
        }
        catch (IOException e) {e.printStackTrace();}
        return builder.toString();
    }

    class UnpostedDCP{
        String AcctNox;
        String RemCode;
        JSONObject data;
        String Message;

        public UnpostedDCP(String acctNox, String remCode, JSONObject data, String message) {
            AcctNox = acctNox;
            RemCode = remCode;
            this.data = data;
            Message = message;
        }

        public String getMessage(){
            return "Account No : " + AcctNox +"\n" +
                    "Remarks Code : " + RemCode + "\n" +
                    "Error Message : " + Message +
                    "\n" +
                    "Parameters : " + data.toString() + "\n";
        }
    }

    public void setExportedDCP(boolean val){
        poConfig.setExportedDcp(val);
    }

    public boolean isExportedDCP(){
        return poConfig.isExportedDcp();
    }

    public interface OnTransactionCallback {
        void onLoading();
        void onSuccess(String fsMessage);
        void onFailed(String fsMessage);
    }

    public interface OnCollectionNameSearch {
        void onLoading();
        void onSuccess(List<EDCPCollectionDetail> foDetail);
        void onFailed(String fsMessage);
    }
}