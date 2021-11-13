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

    public interface OnRequestInventoryCallback{
        void OnRequest(String title, String message);
        void OnSuccessResult(String message);
        void OnFaileResult(String message);
    }

    public VMInventory(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poBranch = new RBranch(application);
        this.poDetail = new RInventoryDetail(application);
        List<RandomItem> randomItems = new ArrayList<>();
        psBranchCd.setValue("");
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<String> getBranchCode(){
        return psBranchCd;
    }

    public void setBranchCde(String value){
        this.psBranchCd.setValue(value);
    }

    public LiveData<List<EBranchInfo>> getAreaBranchList(){
        return poBranch.getAreaBranchList();
    }

    public LiveData<List<EInventoryDetail>> getInventoryDetailForBranch(String BranchCd){
        return poDetail.getInventoryDetailForBranch(BranchCd);
    }

    public void RequestRandomStockInventory(String BranchCd, OnRequestInventoryCallback callback){
        new RequestInventoryTask(instance, callback).execute(BranchCd);
    }

    private static class RequestInventoryTask extends AsyncTask<String, Void, String>{
        private final Application instance;

        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final RInventoryDetail poDetail;
        private final RInventoryMaster poMaster;
        private final OnRequestInventoryCallback poCallback;

        public RequestInventoryTask(Application instance, OnRequestInventoryCallback poCallback) {
            this.instance = instance;
            this.poCallback = poCallback;
            this.poConn = new ConnectionUtil(instance);
            this.poDetail = new RInventoryDetail(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poMaster = new RInventoryMaster(instance);
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
                    lsResult = WebClient.httpsPostJSon(WebApi.URL_REQUEST_RANDOM_STOCK_INVENTORY, loJson.toString(), poHeaders.getHeaders());
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
        }
    }
}