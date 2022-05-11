/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryMaster;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RInventoryDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RInventoryMaster;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RandomItem;

import java.util.ArrayList;
import java.util.List;

public class VMInventory extends AndroidViewModel {
    private static final String TAG = VMInventory.class.getSimpleName();

    private final Application instance;

    private final MutableLiveData<List<EInventoryDetail>> psRandom = new MutableLiveData<>();
    private final MutableLiveData<String> psBranchCd = new MutableLiveData<>();

    private final RBranch poBranch;
    private final RInventoryDetail poDetail;
    private final RInventoryMaster poMaster;

    public interface OnRequestInventoryCallback{
        void OnRequest(String title, String message);
        void OnSuccessResult(String message);
        void OnNoStockRetrieve(String message);
        void OnFaileResult(String message);
    }

    public VMInventory(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poBranch = new RBranch(application);
        this.poDetail = new RInventoryDetail(application);
        this.poMaster = new RInventoryMaster(application);
        List<RandomItem> randomItems = new ArrayList<>();
        psBranchCd.setValue("");
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<String> getBranchCode(){
        return psBranchCd;
    }

    public LiveData<String> getBranchName(String BranchCd){
        return poBranch.getBranchName(BranchCd);
    }

    public void setBranchCde(String value){
        this.psBranchCd.setValue(value);
    }

    public LiveData<List<EBranchInfo>> getAreaBranchList(){
        return poBranch.getAreaBranchList();
    }

    public LiveData<List<EInventoryDetail>> getInventoryDetailForBranch(String TransNox){
        return poDetail.getInventoryDetailForBranch(TransNox);
    }

    public LiveData<EInventoryMaster> getInventoryMasterForBranch(String BranchCd){
        return poMaster.getInventoryMasterForBranch(AppConstants.CURRENT_DATE, BranchCd);
    }

    public LiveData<String> getInventoryCountForBranch(String TransNox){
        return poDetail.getInventoryCountForBranch(TransNox);
    }

    public void RequestRandomStockInventory(String BranchCd, OnRequestInventoryCallback callback){
        new RequestInventoryTask(instance, callback).execute(BranchCd);
    }

    public LiveData<EBranchInfo> getSelfieLogBranchInfo(){
        return poBranch.getSelfieLogBranchInfo();
    }

    private static class RequestInventoryTask extends AsyncTask<String, Void, String>{
        private final Application instance;

        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final RInventoryDetail poDetail;
        private final RInventoryMaster poMaster;
        private final WebApi poApi;
        private final OnRequestInventoryCallback poCallback;

        public RequestInventoryTask(Application instance, OnRequestInventoryCallback poCallback) {
            this.instance = instance;
            this.poCallback = poCallback;
            this.poConn = new ConnectionUtil(instance);
            this.poDetail = new RInventoryDetail(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poMaster = new RInventoryMaster(instance);
            this.poApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poCallback.OnRequest("Random Stock Inventory", "Downloading inventory details. Please wait...");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String lsResult= "";
            try{
                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.NO_INTERNET();
                } else {
                    JSONObject loJson = new JSONObject();
                    loJson.put("branchcd", strings[0]);
                    lsResult = WebClient.httpsPostJSon(poApi.getUrlRequestRandomStockInventory(), loJson.toString(), poHeaders.getHeaders());
                    if(lsResult == null){
                        lsResult = AppConstants.SERVER_NO_RESPONSE();
                    } else {
                        JSONObject loResponse = new JSONObject(lsResult);
                        lsResult = loResponse.getString("result");
                        if(lsResult.equalsIgnoreCase("success")){
                            JSONObject joMaster = loResponse.getJSONObject("master");
                            EInventoryMaster loMaster = new EInventoryMaster();
                            loMaster.setTransNox(joMaster.getString("sTransNox"));
                            loMaster.setBranchCd(joMaster.getString("sBranchCd"));
                            loMaster.setTransact(joMaster.getString("dTransact"));
                            loMaster.setRemarksx(joMaster.getString("sRemarksx"));
                            loMaster.setEntryNox(Integer.parseInt(joMaster.getString("nEntryNox")));
                            loMaster.setRqstdByx(joMaster.getString("sRqstdByx"));
                            loMaster.setVerifyBy(joMaster.getString("sVerified"));
                            loMaster.setDateVrfy(joMaster.getString("dVerified"));
                            loMaster.setApprveBy(joMaster.getString("sApproved"));
                            loMaster.setDateAppv(joMaster.getString("dApproved"));
                            loMaster.setTranStat(joMaster.getString("cTranStat"));
                            poMaster.insertInventoryMaster(loMaster);

                            JSONArray joDetail = loResponse.getJSONArray("detail");
                            List<EInventoryDetail> inventoryDetails = new ArrayList<>();
                            for (int x = 0; x < joDetail.length(); x++) {
                                JSONObject loData = joDetail.getJSONObject(x);
                                EInventoryDetail loDetail = new EInventoryDetail();
                                loDetail.setTransNox(loData.getString("sTransNox"));
                                loDetail.setEntryNox(Integer.parseInt(loData.getString("nEntryNox")));
                                loDetail.setBarrCode(loData.getString("sBarrCode"));
                                loDetail.setDescript(loData.getString("sDescript"));
                                loDetail.setWHouseNm(loData.getString("sWHouseNm"));
                                loDetail.setWHouseID(loData.getString("sWHouseID"));
                                loDetail.setSectnNme(loData.getString("sSectnNme"));
                                loDetail.setBinNamex(loData.getString("sBinNamex"));
                                loDetail.setQtyOnHnd(Integer.parseInt(loData.getString("nQtyOnHnd")));
                                loDetail.setActCtr01(Integer.parseInt(loData.getString("nActCtr01")));
                                loDetail.setActCtr02(Integer.parseInt(loData.getString("nActCtr02")));
                                loDetail.setActCtr03(Integer.parseInt(loData.getString("nActCtr03")));
                                loDetail.setRemarksx(loData.getString("sRemarksx"));
                                loDetail.setPartsIDx(loData.getString("sPartsIDx"));
                                loDetail.setWHouseID(loData.getString("sWHouseID"));
                                loDetail.setSectnIDx(loData.getString("sSectnIDx"));
                                loDetail.setBinIDxxx(loData.getString("sBinIDxxx"));
                                loDetail.setLedgerNo(loData.getString("nLedgerNo"));
                                loDetail.setBegQtyxx(loData.getString("nBegQtyxx"));
                                inventoryDetails.add(loDetail);
                            }
                            poDetail.insertInventoryDetail(inventoryDetails);
                            lsResult = loResponse.toString();
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("A local error occurred while downloading inventory items. " + e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    poCallback.OnSuccessResult("Random inventory items has been imported successfully");
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    if(lsMessage.equalsIgnoreCase("No record found.")){
                        poCallback.OnNoStockRetrieve("No inventory items available.");
                    } else {
                        poCallback.OnFaileResult(loError.getString("message"));
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void PostInventory(String TransNox, String Remarks, OnRequestInventoryCallback callback){
        new PostInventoryTask(instance, Remarks, callback).execute(TransNox);
    }

    private static class PostInventoryTask extends AsyncTask<String, Void, String>{

        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final RInventoryDetail poDetail;
        private final RInventoryMaster poMaster;
        private final String Remarksx;
        private final WebApi poApi;
        private final OnRequestInventoryCallback callback;

        public PostInventoryTask(Application instance, String Remarks, OnRequestInventoryCallback callback){
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.Remarksx = Remarks;
            this.callback = callback;
            this.poApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
            this.poDetail = new RInventoryDetail(instance);
            this.poMaster = new RInventoryMaster(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnRequest("Random Stock Inventory", "Posting inventory details. Please wait...");
        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String lsResult = "";
            try{
                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.NO_INTERNET();
                } else {
                    String lsTransNox = strings[0];
                    int lnUncount = poDetail.getUncountedInventoryItems(lsTransNox);
                    if(lnUncount > 0){
                        lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Please finish your inventory before posting.");
                    } else {
                        poMaster.UpdateInventoryMasterRemarks(lsTransNox, Remarksx);
                        List<EInventoryDetail> loDetail = poDetail.getInventoryDetailForPosting(lsTransNox);
                        EInventoryMaster loMaster = poMaster.getInventoryMasterForPosting(lsTransNox);
                        JSONObject master = new JSONObject();
                        JSONObject joMaster = new JSONObject();
                        joMaster.put("sTransNox", loMaster.getTransNox());
                        joMaster.put("sRemarksx", loMaster.getRemarksx());
                        JSONArray jaDetail = new JSONArray();
                        for (int x = 0; x < loDetail.size(); x++) {
                            JSONObject params = new JSONObject();
                            params.put("sTransNox", loDetail.get(x).getTransNox());
                            params.put("nEntryNox", loDetail.get(x).getEntryNox());
                            params.put("sPartsIDx", loDetail.get(x).getPartsIDx());
                            params.put("nActCtr01", loDetail.get(x).getActCtr01());
                            params.put("sRemarksx", loDetail.get(x).getRemarksx());
                            jaDetail.put(params);
                        }
                        master.put("master", joMaster);
                        master.put("detail", jaDetail);
                        String response = WebClient.httpsPostJSon(poApi.getUrlSubmitRandomStockInventory(), master.toString(), poHeaders.getHeaders());
                        if(response == null){
                            lsResult = AppConstants.SERVER_NO_RESPONSE();
                        } else {
                            JSONObject loResponse = new JSONObject(response);
                            String result = loResponse.getString("result");
                            if(result.equalsIgnoreCase("success")){
                                for (int x = 0; x < loDetail.size(); x++) {
                                    String lsPartID = loDetail.get(x).getPartsIDx();
                                    poDetail.UpdateInventoryItemPostedStatus(lsTransNox, lsPartID);
                                }
                                poMaster.UpdateInventoryMasterPostedStatus(lsTransNox);
                            }
                            lsResult = response;
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
                String result = loJson.getString("result");
                if(result.equalsIgnoreCase("success")){
                    callback.OnSuccessResult("Random stock inventory has been posted.");
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFaileResult(message);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}