package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;

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
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VMCollectionList extends AndroidViewModel {
    private static final String TAG = VMCollectionList.class.getSimpleName();
    private final Application instance;
    private final RDailyCollectionPlan poDCPRepo;
    private final RBranch poBranch;
    private final RCollectionUpdate poUpdate;
    private final REmployee poEmploye;
    private final LiveData<List<EDCPCollectionDetail>> collectionList;

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
        this.collectionList = poDCPRepo.getCollectionDetailList();
    }

    @SuppressLint("SimpleDateFormat")
    public void DownloadDcp(String date, OnDownloadCollection callback){
        try{
            @SuppressLint("SimpleDateFormat") Date loDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            String lsDate = new SimpleDateFormat("yyyy-MM-dd").format(Objects.requireNonNull(loDate));
            boolean isExist = false;
            for(int x = 0; x < masterList.getValue().size(); x++){
                if(masterList.getValue().get(x).getTransact().equalsIgnoreCase(lsDate)){
                    isExist = true;
                }
            }

            if(!isExist) {
                JSONObject loJson = new JSONObject();
                loJson.put("sEmployID", "M00110006088");
                loJson.put("dTransact", lsDate);
                loJson.put("cDCPTypex", "1");
                new ImportLRCollection(instance, masterList.getValue(), callback).execute(loJson);
            } else {
                callback.OnDownloadFailed("Record already exist");
            }
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
    public void importDCPFile(String importFileName, ViewModelCallback callback) throws JSONException {
        JSONObject dcpImport = readDCPImportFileContent(importFileName);
        if(dcpImport != null)  {
            extractDCPImportDetails(dcpImport, (collectionDetlList, collectionMaster) -> {
                if (importDCPMasterData(collectionMaster)) {
                    boolean isCollectDetlInserted = importDCPListBulkData(collectionDetlList);
                    if (isCollectDetlInserted) {
                        callback.OnSuccessResult(new String[]{"Collection detail imported successfully."});
                    } else {
                        callback.OnFailedResult("Collection Detail Import Failed: DETAIL");
                    }
                } else {
                    callback.OnFailedResult("Collection Detail Import Failed: MASTER");
                }
            });
        }
    }

    private JSONObject readDCPImportFileContent(String fileName) throws JSONException {
        FileReader fr = null;
        String fileContents;
        String root = Environment.getExternalStorageDirectory().toString();
        String lsPublicFoldx = AppConstants.APP_PUBLIC_FOLDER;
        String lsSubFoldx = AppConstants.SUB_FOLDER_EXPORTS;
        File sd = new File(root + lsPublicFoldx + lsSubFoldx + "/");
        File myExternalFile = new File(sd , fileName + "-out.txt");
        if(!myExternalFile.exists()) {
            GToast.CreateMessage(getApplication(), "Please enter a valid file name.", GToast.WARNING).show();
            return null;
        }
        Log.e("DIRECTORY", myExternalFile.toString());
        StringBuilder sb = new StringBuilder();
        try {
            fr = new FileReader(myExternalFile);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            if(line != null) {
                while(line != null) {
                    sb.append(line).append('\n');
                    line = br.readLine();
                }
            } else {
                GToast.CreateMessage(getApplication(), "File does not contain data.", GToast.ERROR).show();
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            fileContents = sb.toString();
            Log.e("Your String", fileContents);
        }
        return new JSONObject(fileContents);
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
                                    } else if(!loJSON_Master.getString("dTransact").equalsIgnoreCase(AppConstants.CURRENT_DATE)) {
                                        GToast.CreateMessage(getApplication(), "Collection list is not applicable for the current date.", GToast.WARNING).show();
                                    } else {
                                        EDCPCollectionMaster loMaster = new EDCPCollectionMaster();

                                        loMaster.setTransact(loJSON_Master.getString("dTransact"));
                                        loMaster.setBranchNm(loJSON_Master.getString("sBranchNm"));
                                        loMaster.setTransNox(loJSON_Master.getString("sTransNox"));
                                        loMaster.setDCPTypex(loJSON_Master.getString("cDCPTypex").charAt(0));
                                        loMaster.setEntryNox(loJSON_Master.getString("nEntryNox"));
                                        loMaster.setCollName(loJSON_Master.getString("xCollName"));
                                        loMaster.setCollctID(loJSON_Master.getString("sCollctID"));
                                        loMaster.setTranStat(loJSON_Master.getString("cTranStat").charAt(0));
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


    public void importInsuranceInfo(String fsSerialNo, ViewModelCallback callback){
        try{
            boolean lbExist = false;
            List<EDCPCollectionDetail> laDetail = collectionList.getValue();
            if(laDetail.size() > 0) {
                for(int x = 0; x < laDetail.size(); x++){
                    if(fsSerialNo.equalsIgnoreCase(laDetail.get(x).getSerialNo())){
                        lbExist = true;
                    }
                }
                if(!lbExist) {
                    JSONObject loJson = new JSONObject();
                    loJson.put("sSerialNo", fsSerialNo);
                    //new ImportData(instance, psTransNox.getValue(), pnEntryNox.getValue(), WebApi.URL_GET_REG_CLIENT, callback).execute(loJson);
                } else {
                    callback.OnFailedResult("Engine no is already exist in today's collection list.");
                }
            } else {
                callback.OnFailedResult("Please download or import collection detail before adding.");
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

    public void importARClientInfo(String fsAccntNox, OnDownloadClientList callback){
        try{
            boolean lbExist = false;
            List<EDCPCollectionDetail> laDetail = collectionList.getValue();
            if(laDetail.size() > 0) {
                for(int x = 0; x < laDetail.size(); x++){
                    if(fsAccntNox.equalsIgnoreCase(laDetail.get(x).getAcctNmbr())){
                        lbExist = true;
                    }
                }
                if(!lbExist) {
                    JSONObject loJson = new JSONObject();
                    loJson.put("sAcctNmbr", fsAccntNox);
                    new ImportData(instance, psTransNox.getValue(), pnEntryNox.getValue(), WebApi.URL_GET_AR_CLIENT, callback).execute(loJson);
                } else {
                    callback.OnFailedDownload("Account number is already exist in today's collection list.");
                }
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
                    response = WebClient.httpsPostJSon(Url, strings[0].toString(), headers.getHeaders());
                    JSONObject jsonResponse = new JSONObject(response);
                    String lsResult = jsonResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        saveDetailDataToLocal(jsonResponse.getJSONObject("data"));
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
                    //callback.OnSuccessDownload("");
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

        void saveDetailDataToLocal(JSONObject foData) throws JSONException {
            Log.e(TAG, foData.toString());
            EDCPCollectionDetail collectionDetail = new EDCPCollectionDetail();
            collectionDetail.setTransNox(sTransNox);
            collectionDetail.setEntryNox(nEntryNox);
            collectionDetail.setAcctNmbr(foData.getString("sAcctNmbr"));
            collectionDetail.setFullName(foData.getString("xFullName"));
            collectionDetail.setIsDCPxxx("0");
            collectionDetail.setMobileNo(foData.getString("sMobileNo"));
            collectionDetail.setHouseNox(foData.getString("sHouseNox"));
            collectionDetail.setAddressx(foData.getString("sAddressx"));
            collectionDetail.setBrgyName(foData.getString("sBrgyName"));
            collectionDetail.setTownName(foData.getString("sTownName"));
            collectionDetail.setClientID(foData.getString("sClientID"));
            collectionDetail.setSerialID(foData.getString("sSerialID"));
            collectionDetail.setSerialNo(foData.getString("sSerialNo"));
            dcpRepo.insertCollectionDetail(collectionDetail);
            dcpRepo.updateEntryMaster(nEntryNox);
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
            this.webApi = new WebApi(instance);
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
                    response = WebClient.httpsPostJSon(WebApi.URL_DOWNLOAD_DCP, strings[0].toString(), headers.getHeaders());
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
            boolean isExist = true;
            JSONObject loJson = foJson.getJSONObject("master");
            for(int x = 0; x < masterList.size(); x++){
                if(masterList.get(x).getTransNox().equalsIgnoreCase(loJson.getString("sTransNox"))){
                    //return false if transNox already exist...
                    isExist = false;
                }
            }
            EDCPCollectionMaster collectionMaster = new EDCPCollectionMaster();
            collectionMaster.setTransNox(loJson.getString("sTransNox"));
            collectionMaster.setTransact(loJson.getString("dTransact"));
            collectionMaster.setReferNox(loJson.getString("sReferNox"));
            collectionMaster.setCollName(loJson.getString("xCollName"));
            collectionMaster.setRouteNme(loJson.getString("sRouteNme"));
            collectionMaster.setReferDte(loJson.getString("dReferDte"));
            collectionMaster.setTranStat(loJson.getString("cTranStat").charAt(0));
            collectionMaster.setDCPTypex(loJson.getString("cDCPTypex").charAt(0));
            collectionMaster.setEntryNox(loJson.getString("nEntryNox"));
            collectionMaster.setBranchNm(loJson.getString("sBranchNm"));
            collectionMaster.setCollctID(loJson.getString("sCollctID"));
            JSONArray laJson = foJson.getJSONArray("detail");
            dcpRepo.insertMasterData(collectionMaster);
            saveDetailDataToLocal(laJson, loJson);
            return isExist;
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

    public void PostLRCollectionDetail(String Remarks, ViewModelCallback callback){
        new PostLRCollectionDetail(instance, Remarks,  plAddress.getValue(), plMobile.getValue(), callback).execute(plDetail.getValue());
    }

    public static class PostLRCollectionDetail extends AsyncTask<List<DDCPCollectionDetail.CollectionDetail>, Void, String>{
        private final ConnectionUtil poConn;
        private final ViewModelCallback callback;
        private final SessionManager poUser;
        private final RDailyCollectionPlan poDcp;
        private final RImageInfo poImage;
        private final Telephony poTelephony;
        private final HttpHeaders poHeaders;
        private final RCollectionUpdate rCollect;
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
            List<DDCPCollectionDetail.CollectionDetail> laCollDetl = lists[0];
            try {
                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.NO_INTERNET();
                } else {

                    String lsClient = WebFileServer.RequestClientToken("IntegSys", poUser.getClientId(), poUser.getUserID());
                    String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                    if(lsClient.isEmpty() || lsAccess.isEmpty()){
                        lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Failed to request generated Client or Access token.");
                    } else {
                        boolean[] isDataSent = new boolean[laCollDetl.size()];
                        if(laCollDetl.size() > 0) {
                            for (int x = 0; x < laCollDetl.size(); x++) {
                                try {
                                    DDCPCollectionDetail.CollectionDetail loDetail = laCollDetl.get(x);

                                    JSONObject loData = new JSONObject();

                                    if (loDetail.sRemCodex != null) {

                                        org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(loDetail.sFileLoct,
                                                lsAccess,
                                                loDetail.sFileCode,
                                                loDetail.sAcctNmbr,
                                                loDetail.sImageNme,
                                                poUser.getBranchCode(),
                                                loDetail.sSourceCD,
                                                loDetail.sTransNox,
                                                "");

                                        String lsResponse = (String) loUpload.get("result");
                                        Log.e(TAG, "Uploading image result : " + lsResponse);

                                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                                            Log.e(TAG, "Image file of Account No. " + loDetail.sAcctNmbr + ", Entry No. " + loDetail.nEntryNox + "was uploaded successfully");

                                            String lsTransNo = (String) loUpload.get("sTransNox");
                                            poImage.updateImageInfo(lsTransNo, loDetail.sImageIDx);

                                            Thread.sleep(1000);

                                        } else {
                                            isDataSent[x] = false;
                                            Log.e(TAG, "Image file of Account No. " + loDetail.sAcctNmbr + ", Entry No. " + loDetail.nEntryNox + " was not uploaded to server.");
                                            JSONObject loError = new JSONObject(lsResponse);
                                            Log.e(TAG, "Reason : " + loError.getString("message"));
                                        }

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
                                    if (loDetail.sRemCodex == null) {
                                        loJson.put("sRemCodex", "OTH");
                                    } else {
                                        loJson.put("sRemCodex", loDetail.sRemCodex);
                                    }
                                    loJson.put("sJsonData", loData);
                                    loJson.put("dReceived", "");
                                    loJson.put("sUserIDxx", poUser.getUserID());
                                    loJson.put("sDeviceID", poTelephony.getDeviceID());

                                    Log.e(TAG, loJson.toString());
                                    String lsResponse1 = WebClient.httpsPostJSon(WebApi.URL_DCP_SUBMIT, loJson.toString(), poHeaders.getHeaders());
                                    if (lsResponse1 == null) {
                                        Log.e(TAG, "Server no response.");
                                    } else {
                                        JSONObject loResponse = new JSONObject(lsResponse1);

                                        String result = loResponse.getString("result");
                                        if (result.equalsIgnoreCase("success")) {
                                            Log.e(TAG, "Data of Account No. " + loDetail.sAcctNmbr + ", Entry No. " + loDetail.nEntryNox + " was uploaded successfully");
                                            if (loDetail.sRemCodex == null) {
                                                poDcp.updateCollectionDetailStatusWithRemarks(loDetail.sTransNox, loDetail.nEntryNox, sRemarksx);
                                            } else {
                                                poDcp.updateCollectionDetailStatus(loDetail.sTransNox, loDetail.nEntryNox);
                                            }
                                            isDataSent[x] = true;

                                            //call sending CNA details....
                                            sendCNADetails(loDetail.sRemCodex, loDetail.sTransNox);
                                        } else {
                                            JSONObject loError = loResponse.getJSONObject("error");
                                            Log.e(TAG, loError.getString("message"));
                                            isDataSent[x] = false;
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    isDataSent[x] = false;
                                }

                                Thread.sleep(1000);
                            }

                            boolean allDataSent = true;
                            for (boolean b : isDataSent) {
                                if (!b) {
                                    allDataSent = false;
                                    break;
                                }
                            }

                            if (allDataSent) {
                                lsResult = AppConstants.ALL_DATA_SENT();
                            } else {
                                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("An error occurred while posting collection details. Please try again...");
                            }
                        } else {
                            // TODO: Display no details to post
                            lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("No available collection details to post.");
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                if(loJson.getString("result").equalsIgnoreCase("success")){
                    callback.OnSuccessResult(new String[]{"Collection for today has been posted successfully."});
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    callback.OnFailedResult(loError.getString("message"));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(true);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        void sendCNADetails(String fsRemCode, String fsTransno) throws Exception {
            if (fsRemCode != null && fsRemCode.equalsIgnoreCase("CNA")) {
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

                        String lsAddressUpdtResponse = WebClient.httpsPostJSon(WebApi.URL_UPDATE_ADDRESS, param.toString(), poHeaders.getHeaders());

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
                    }
                    Thread.sleep(1000);
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

                        String lsMobileUpdtResponse = WebClient.httpsPostJSon(WebApi.URL_UPDATE_MOBILE, param.toString(), poHeaders.getHeaders());

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
        private JSONArray JSONArrayExport;
        private String sRemarkx;
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
                    if(loDetail.sRemCodex == null){
                        loJson.put("sRemCodex", "OTH");
                    } else {
                        loJson.put("sRemCodex", loDetail.sRemCodex);
                    }
                    loJson.put("sJsonData", loData);
                    JSONArrayExport.put(loJson);
                    Log.e(TAG, loJson.toString());

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
                callBack.OnSuccessResult(new String[]{"Collection list exported successfully."});
            } else {
                callBack.OnFailedResult(("No available collection list to export."));
            }

        }


    }

    private static class CheckImportDataTask extends AsyncTask<String, Void, Boolean>{
        private RDailyCollectionPlan poDCPRepo;
        private CheckImport mListener;
        private EDCPCollectionDetail poDCPEntity;

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
                    poDCPEntity = poDCPRepo.checkCollectionImport(
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
            String lsUserId = poEmploye.getUserNonLiveData().getUserIDxx();
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

    public interface CheckImport {
        void OnCheck(boolean doesExist);
    }

    public interface ImportJSONCallback {
        void OnDataExtract(List<EDCPCollectionDetail> collectionDetlList, EDCPCollectionMaster collectionMaster);
    }
}