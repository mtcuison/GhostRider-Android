package org.rmj.g3appdriver.lib.integsys.Inventory;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DInventoryDao;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EInventoryDetail;
import org.rmj.g3appdriver.dev.Database.Entities.EInventoryMaster;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.dev.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.List;

public class RandomStockInventory {
    private static final String TAG = RandomStockInventory.class.getSimpleName();

    private final DInventoryDao poDao;

    private final EmployeeMaster poUser;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RandomStockInventory(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).InventoryDao();
        this.poUser = new EmployeeMaster(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public List<EBranchInfo> GetBranchesForInventory(){
        try{
            int lnLogsxx = poDao.CheckIfHasSelfieLog(AppConstants.CURRENT_DATE);
            if(lnLogsxx == 0){
                message = "No selfie log record found for this day.";
                return null;
            }

            List<EBranchInfo> loList = poDao.GetBranchesForInventory(AppConstants.CURRENT_DATE);
            if(loList.size() == 0){
                message = "All branches on selfie log has cash count record.";
                return null;
            }

            return loList;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public LiveData<EBranchInfo> GetBranchInfo(String args){
        return poDao.GetBranchInfo(args);
    }

    public Boolean CheckInventoryRecord(String fsVal){
        try{
            EInventoryMaster loMaster = poDao.GetMasterIfExists(fsVal, AppConstants.CURRENT_DATE);

            if(loMaster != null){

                if(loMaster.getTranStat().equalsIgnoreCase("2")){
                    message = "Inventory is already uploaded to server.";
                    return false;
                }

                if(!loMaster.getRemarksx().trim().isEmpty()){
                    message = "You already have entries for this inventory.";
                    return false;
                }

            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean ImportInventory(String fsVal){
        try{
            EInventoryMaster loMaster = poDao.GetMasterIfExists(fsVal, AppConstants.CURRENT_DATE);

            if(loMaster != null){
                message = "Inventory already exist in your local device.";
                return false;
            }

            JSONObject params = new JSONObject();
            params.put("branchcd", fsVal);
            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlRequestRandomStockInventory(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            JSONObject joMaster = loResponse.getJSONObject("master");
            EInventoryMaster master = new EInventoryMaster();
            master.setTransNox(joMaster.getString("sTransNox"));
            master.setBranchCd(joMaster.getString("sBranchCd"));
            master.setTransact(joMaster.getString("dTransact"));
            master.setRemarksx(joMaster.getString("sRemarksx"));
            master.setEntryNox(Integer.parseInt(joMaster.getString("nEntryNox")));
            master.setRqstdByx(joMaster.getString("sRqstdByx"));
            master.setVerifyBy(joMaster.getString("sVerified"));
            master.setDateVrfy(joMaster.getString("dVerified"));
            master.setApprveBy(joMaster.getString("sApproved"));
            master.setDateAppv(joMaster.getString("dApproved"));
            master.setTranStat(joMaster.getString("cTranStat"));
            poDao.SaveMaster(master);
            Log.d(TAG, "Inventory master has been saved.");

            JSONArray joDetail = loResponse.getJSONArray("detail");
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
                poDao.SaveDetail(loDetail);
                Log.d(TAG, "Inventory detail has been saved.");
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public LiveData<EInventoryMaster> GetInventoryMaster(String BranchCd){
        return poDao.GetInventoryMaster(BranchCd, AppConstants.CURRENT_DATE);
    }

    public LiveData<List<EInventoryDetail>> GetInventoryItems(String fsVal){
        return poDao.GetInventoryItems(fsVal);
    }

    public LiveData<EInventoryDetail> GetItemDetail(String TransNox, String PartID, String BarCode){
        return poDao.GetItemDetail(TransNox, PartID, BarCode);
    }

    public boolean UpdateItemDetail(RandomItem foVal){
        try{
            String TransNox = foVal.getTransNox();
            String PartIDxx = foVal.getPartIDxx();
            String BarCodex = foVal.getBarCodex();

            EInventoryDetail loDetail = poDao.GetDetail(TransNox, PartIDxx, BarCodex);

            if(loDetail == null){
                message = "Unable to find item detail.";
                return false;
            }

            loDetail.setActCtr01(Integer.parseInt(foVal.getItemQtyx()));
            loDetail.setRemarksx(foVal.getRemarksx());
            loDetail.setTranStat("1");

            poDao.UpdateDetail(loDetail);

            Log.d(TAG, "Item detail has been updated.");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadInventory(String fsVal){
        try{
            EInventoryMaster loMaster = poDao.GetMasterIfExists(fsVal, AppConstants.CURRENT_DATE);

            if(loMaster == null){
                message = "Unable to find record for uploading.";
                return false;
            }

            String lsTransNo = loMaster.getTransNox();

            List<EInventoryDetail> loDetails = poDao.GetInventoryDetails(lsTransNo);

            if(loDetails == null){
                message = "Unable to find records for details.";
                return false;
            }

            JSONObject params = new JSONObject();
            JSONObject joMaster = new JSONObject();
            joMaster.put("sTransNox", loMaster.getTransNox());
            joMaster.put("sRemarksx", loMaster.getRemarksx());
            JSONArray jaDetail = new JSONArray();
            for (int x = 0; x < loDetails.size(); x++) {
                JSONObject detail = new JSONObject();
                detail.put("sTransNox", loDetails.get(x).getTransNox());
                detail.put("nEntryNox", loDetails.get(x).getEntryNox());
                detail.put("sPartsIDx", loDetails.get(x).getPartsIDx());
                detail.put("nActCtr01", loDetails.get(x).getActCtr01());
                detail.put("sRemarksx", loDetails.get(x).getRemarksx());
                jaDetail.put(detail);
            }

            params.put("master", joMaster);
            params.put("detail", jaDetail);

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlSubmitRandomStockInventory(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            poDao.UpdateUploadedDetails(lsTransNo);
            poDao.UpdateUploadedMaster(lsTransNo);
            Log.d(TAG, "Inventory has been uploaded successfully.");


            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public String SaveMasterForPosting(String fsVal, String Remarks){
        try{
            EInventoryMaster loMaster = poDao.GetMasterIfExists(fsVal, AppConstants.CURRENT_DATE);

            if(loMaster == null){
                message = "Unable to find record for uploading.";
                return null;
            }

            loMaster.setRemarksx(Remarks);
            poDao.UpdateMaster(loMaster);
            Log.d(TAG, "Inventory master has been updated.");

            return loMaster.getTransNox();
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public boolean UploadInventoryDetails(){
        try{
            List<EInventoryMaster> loMasters = poDao.GetMasterInventoryForUpload();

            if(loMasters == null){
                message = "Unable to find record for uploading.";
                return false;
            }

            for(int x = 0; x < loMasters.size(); x++){
                EInventoryMaster loMaster = loMasters.get(x);

                String lsTransNo = loMaster.getTransNox();

                List<EInventoryDetail> loDetails = poDao.GetInventoryDetails(lsTransNo);

                if(loDetails == null){
                    message = "Unable to find records for details.";
                    Log.e(TAG, message);
                    continue;
                }

                JSONObject params = new JSONObject();
                JSONObject joMaster = new JSONObject();
                joMaster.put("sTransNox", loMaster.getTransNox());
                joMaster.put("sRemarksx", loMaster.getRemarksx());
                JSONArray jaDetail = new JSONArray();

                for (int i = 0; i < loDetails.size(); i++) {
                    JSONObject detail = new JSONObject();
                    detail.put("sTransNox", loDetails.get(i).getTransNox());
                    detail.put("nEntryNox", loDetails.get(i).getEntryNox());
                    detail.put("sPartsIDx", loDetails.get(i).getPartsIDx());
                    detail.put("nActCtr01", loDetails.get(i).getActCtr01());
                    detail.put("sRemarksx", loDetails.get(i).getRemarksx());
                    jaDetail.put(detail);
                }

                params.put("master", joMaster);
                params.put("detail", jaDetail);

                String lsResponse = WebClient.sendRequest(
                        poApi.getUrlSubmitRandomStockInventory(poConfig.isBackUpServer()),
                        params.toString(),
                        poHeaders.getHeaders());

                if(lsResponse == null){
                    message = "Server no response.";
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("error")){
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                poDao.UpdateUploadedDetails(lsTransNo);
                poDao.UpdateUploadedMaster(lsTransNo);
                Log.d(TAG, "Inventory has been uploaded successfully.");

                Thread.sleep(1000);
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
