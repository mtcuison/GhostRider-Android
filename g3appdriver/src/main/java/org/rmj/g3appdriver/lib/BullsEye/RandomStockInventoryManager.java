package org.rmj.g3appdriver.lib.BullsEye;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RInventoryDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RInventoryMaster;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.ArrayList;
import java.util.List;

public class RandomStockInventoryManager {
    private static final String TAG = RandomStockInventoryManager.class.getSimpleName();

    private final Application instance;

    private String message;

    public RandomStockInventoryManager(Application application) {
        this.instance = application;
    }

    public String getMessage() {
        return message;
    }

    public boolean RequestInventoryItems() {
        try{
            RLogSelfie loSelfie = new RLogSelfie(instance);
            if(loSelfie.getLastEmployeeLog() == null){
                message = "No employee log record found.";
                return false;
            } else {
                ELog_Selfie loEmpLog = loSelfie.getLastEmployeeLog();
                RInventoryMaster loMaster = new RInventoryMaster(instance);
                RInventoryDetail loDetail = new RInventoryDetail(instance);
                WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());

                JSONObject loJson = new JSONObject();
                loJson.put("branchcd", loEmpLog.getBranchCd());
                String lsResponse = WebClient.sendRequest(loApi.getUrlRequestRandomStockInventory(),
                        loJson.toString(),
                        HttpHeaders.getInstance(instance).getHeaders());

                if (lsResponse == null) {
                    message = "Server no response.";
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String lsResult = loResponse.getString("result");
                    if(!lsResult.equalsIgnoreCase("success")){
                        JSONObject loError = loResponse.getJSONObject("error");
                        message = loError.getString("message");
                        return false;
                    } else {
                        JSONObject joMaster = loResponse.getJSONObject("master");
                        EInventoryMaster loMDetlx = new EInventoryMaster();
                        loMDetlx.setTransNox(joMaster.getString("sTransNox"));
                        loMDetlx.setBranchCd(joMaster.getString("sBranchCd"));
                        loMDetlx.setTransact(joMaster.getString("dTransact"));
                        loMDetlx.setRemarksx(joMaster.getString("sRemarksx"));
                        loMDetlx.setEntryNox(Integer.parseInt(joMaster.getString("nEntryNox")));
                        loMDetlx.setVerifyBy(joMaster.getString("sVerified"));
                        loMDetlx.setDateVrfy(joMaster.getString("dVerified"));
                        loMDetlx.setApprveBy(joMaster.getString("sApproved"));
                        loMDetlx.setDateAppv(joMaster.getString("dApproved"));
                        loMDetlx.setTranStat(joMaster.getString("cTranStat"));
                        loMaster.insertInventoryMaster(loMDetlx);

                        JSONArray joDetail = loResponse.getJSONArray("detail");
                        List<EInventoryDetail> inventoryDetails = new ArrayList<>();
                        for (int x = 0; x < joDetail.length(); x++) {
                            JSONObject loData = joDetail.getJSONObject(x);
                            EInventoryDetail loDetlx = new EInventoryDetail();
                            loDetlx.setTransNox(loData.getString("sTransNox"));
                            loDetlx.setEntryNox(Integer.parseInt(loData.getString("nEntryNox")));
                            loDetlx.setBarrCode(loData.getString("sBarrCode"));
                            loDetlx.setDescript(loData.getString("sDescript"));
                            loDetlx.setWHouseNm(loData.getString("sWHouseNm"));
                            loDetlx.setWHouseID(loData.getString("sWHouseID"));
                            loDetlx.setSectnNme(loData.getString("sSectnNme"));
                            loDetlx.setBinNamex(loData.getString("sBinNamex"));
                            loDetlx.setQtyOnHnd(Integer.parseInt(loData.getString("nQtyOnHnd")));
                            loDetlx.setActCtr01(Integer.parseInt(loData.getString("nActCtr01")));
                            loDetlx.setActCtr02(Integer.parseInt(loData.getString("nActCtr02")));
                            loDetlx.setActCtr03(Integer.parseInt(loData.getString("nActCtr03")));
                            loDetlx.setRemarksx(loData.getString("sRemarksx"));
                            loDetlx.setPartsIDx(loData.getString("sPartsIDx"));
                            loDetlx.setWHouseID(loData.getString("sWHouseID"));
                            loDetlx.setSectnIDx(loData.getString("sSectnIDx"));
                            loDetlx.setBinIDxxx(loData.getString("sBinIDxxx"));
                            loDetlx.setLedgerNo(loData.getString("nLedgerNo"));
                            loDetlx.setBegQtyxx(loData.getString("nBegQtyxx"));
                            inventoryDetails.add(loDetlx);
                        }
                        loDetail.insertInventoryDetail(inventoryDetails);

                        return true;
                    }
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public List<EInventoryDetail> getInventoryDetailForLastLog(){
        return new RInventoryDetail(instance).getInventoryDetailForLastLog();
    }

    public boolean UpdateInventoryItem(String TransNox, String BarCode, String PartID, String ActualQty, String Remarks){
        try{
            new RInventoryDetail(instance).UpdateInventoryItem(TransNox, BarCode, PartID, ActualQty, Remarks);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean PostInventory(String fstransNo, String Remarksx){
        try{
            RInventoryMaster loMaster = new RInventoryMaster(instance);
            RInventoryDetail loDetail = new RInventoryDetail(instance);
            WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());

            int lnUncount = loDetail.getUncountedInventoryItems(fstransNo);
            if(lnUncount > 0){
                message = "Please finish your inventory before posting.";
                return false;
            } else {
                loMaster.UpdateInventoryMasterRemarks(fstransNo, Remarksx);
                List<EInventoryDetail> laDetail = loDetail.getInventoryDetailForPosting(fstransNo);
                EInventoryMaster loMDetlx = loMaster.getInventoryMasterForPosting(fstransNo);
                JSONObject master = new JSONObject();
                JSONObject joMaster = new JSONObject();
                joMaster.put("sTransNox", loMDetlx.getTransNox());
                joMaster.put("sRemarksx", loMDetlx.getRemarksx());
                JSONArray jaDetail = new JSONArray();
                for (int x = 0; x < laDetail.size(); x++) {
                    JSONObject params = new JSONObject();
                    params.put("sTransNox", laDetail.get(x).getTransNox());
                    params.put("nEntryNox", laDetail.get(x).getEntryNox());
                    params.put("sPartsIDx", laDetail.get(x).getPartsIDx());
                    params.put("nActCtr01", laDetail.get(x).getActCtr01());
                    params.put("sRemarksx", laDetail.get(x).getRemarksx());
                    jaDetail.put(params);
                }
                master.put("master", joMaster);
                master.put("detail", jaDetail);
                String lsResponse = WebClient.sendRequest(loApi.getUrlSubmitRandomStockInventory(),
                        master.toString(),
                        HttpHeaders.getInstance(instance).getHeaders());
                if(lsResponse == null){
                    message = "Server no response";
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String result = loResponse.getString("result");
                    if(!result.equalsIgnoreCase("success")){
                        JSONObject loError = loResponse.getJSONObject("error");
                        message = loError.getString("message");
                        return false;
                    } else {
                        loDetail.UpdateInventoryItemPostedStatus(loMDetlx.getTransNox());
                        loMaster.UpdateInventoryMasterPostedStatus(loMDetlx.getTransNox());
                    }
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
