package org.rmj.g3appdriver.GCircle.Apps.Inventory;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DInventoryDao;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EInventoryDetail;
import org.rmj.g3appdriver.GCircle.room.Entities.EInventoryMaster;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;

import java.util.List;

public class RandomStockInventory {
    private static final String TAG = RandomStockInventory.class.getSimpleName();

    private final DInventoryDao poDao;

    private final EmployeeMaster poUser;

    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RandomStockInventory(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).InventoryDao();
        this.poUser = new EmployeeMaster(instance);
        this.poApi = new GCircleApi(instance);
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
            int lnLogsxx = poDao.CheckIfHasSelfieLog(AppConstants.CURRENT_DATE());
            if(lnLogsxx == 0){
                message = "No selfie log record found for this day.";
                return null;
            }

            List<EBranchInfo> loList = poDao.GetBranchesForInventory(AppConstants.CURRENT_DATE());
            if(loList.size() == 0){
                message = "All branches on selfie log has inventory record.";
                return null;
            }

            return loList;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public LiveData<EBranchInfo> GetBranchInfo(String args){
        return poDao.GetBranchInfo(args);
    }

    public Boolean CheckInventoryRecord(String fsVal){
        try{
            EInventoryMaster loMaster = poDao.GetMasterIfExists(fsVal, AppConstants.CURRENT_DATE());

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
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean ImportInventory(String fsVal){
        try{
            EInventoryMaster loMaster = poDao.GetMasterIfExists(fsVal, AppConstants.CURRENT_DATE());

            if(loMaster != null){
                if(loMaster.getTranStat().equalsIgnoreCase("2")) {
                    message = "Inventory is already uploaded to server.";
                    return false;
                }
                message = "Inventory already exist in your local device.";
                return false;
            }

            JSONObject params = new JSONObject();
            params.put("branchcd", fsVal);
            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlRequestRandomStockInventory(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            JSONObject joMaster = loResponse.getJSONObject("master");
            EInventoryMaster objMaster = poDao.GetMaster(joMaster.getString("sTransNox"));
            if(objMaster == null) {
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
                SaveDetail(joDetail);
            } else {
                objMaster.setTransNox(joMaster.getString("sTransNox"));
                objMaster.setBranchCd(joMaster.getString("sBranchCd"));
                objMaster.setTransact(joMaster.getString("dTransact"));
                objMaster.setRemarksx(joMaster.getString("sRemarksx"));
                objMaster.setEntryNox(Integer.parseInt(joMaster.getString("nEntryNox")));
                objMaster.setRqstdByx(joMaster.getString("sRqstdByx"));
                objMaster.setVerifyBy(joMaster.getString("sVerified"));
                objMaster.setDateVrfy(joMaster.getString("dVerified"));
                objMaster.setApprveBy(joMaster.getString("sApproved"));
                objMaster.setDateAppv(joMaster.getString("dApproved"));
                objMaster.setTranStat(joMaster.getString("cTranStat"));
                poDao.UpdateMaster(objMaster);
                Log.d(TAG, "Inventory master has been updated.");

                JSONArray joDetail = loResponse.getJSONArray("detail");
                SaveDetail(joDetail);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    private void SaveDetail(JSONArray laDetail){
        try{
            for (int x = 0; x < laDetail.length(); x++) {
                JSONObject loData = laDetail.getJSONObject(x);

                String lsTransNo = loData.getString("sTransNox");
                int lnEntryNo = loData.getInt("nEntryNox");
                String lsBarCode = loData.getString("sBarrCode");

                EInventoryDetail objDetail = poDao.GetDetail(lsTransNo, lnEntryNo, lsBarCode);
                if(objDetail == null) {
                    EInventoryDetail loDetail = new EInventoryDetail();
                    loDetail.setTransNox(loData.getString("sTransNox"));
                    loDetail.setEntryNox(loData.getInt("nEntryNox"));
                    loDetail.setBarrCode(loData.getString("sBarrCode"));
                    loDetail.setDescript(loData.getString("sDescript"));
                    loDetail.setWHouseNm(loData.getString("sWHouseNm"));
                    loDetail.setWHouseID(loData.getString("sWHouseID"));
                    loDetail.setSectnNme(loData.getString("sSectnNme"));
                    loDetail.setBinNamex(loData.getString("sBinNamex"));
                    loDetail.setQtyOnHnd(loData.getInt("nQtyOnHnd"));
                    loDetail.setActCtr01(loData.getInt("nActCtr01"));
                    loDetail.setActCtr02(loData.getInt("nActCtr02"));
                    loDetail.setActCtr03(loData.getInt("nActCtr03"));
                    loDetail.setRemarksx(loData.getString("sRemarksx"));
                    loDetail.setPartsIDx(loData.getString("sPartsIDx"));
                    loDetail.setWHouseID(loData.getString("sWHouseID"));
                    loDetail.setSectnIDx(loData.getString("sSectnIDx"));
                    loDetail.setBinIDxxx(loData.getString("sBinIDxxx"));
                    loDetail.setLedgerNo(loData.getString("nLedgerNo"));
                    loDetail.setBegQtyxx(loData.getString("nBegQtyxx"));
                    poDao.SaveDetail(loDetail);
                    Log.d(TAG, "Inventory detail has been saved.");
                } else {
                    objDetail.setTransNox(loData.getString("sTransNox"));
                    objDetail.setEntryNox(loData.getInt("nEntryNox"));
                    objDetail.setBarrCode(loData.getString("sBarrCode"));
                    objDetail.setDescript(loData.getString("sDescript"));
                    objDetail.setWHouseNm(loData.getString("sWHouseNm"));
                    objDetail.setWHouseID(loData.getString("sWHouseID"));
                    objDetail.setSectnNme(loData.getString("sSectnNme"));
                    objDetail.setBinNamex(loData.getString("sBinNamex"));
                    objDetail.setQtyOnHnd(loData.getInt("nQtyOnHnd"));
                    objDetail.setActCtr01(loData.getInt("nActCtr01"));
                    objDetail.setActCtr02(loData.getInt("nActCtr02"));
                    objDetail.setActCtr03(loData.getInt("nActCtr03"));
                    objDetail.setRemarksx(loData.getString("sRemarksx"));
                    objDetail.setPartsIDx(loData.getString("sPartsIDx"));
                    objDetail.setWHouseID(loData.getString("sWHouseID"));
                    objDetail.setSectnIDx(loData.getString("sSectnIDx"));
                    objDetail.setBinIDxxx(loData.getString("sBinIDxxx"));
                    objDetail.setLedgerNo(loData.getString("nLedgerNo"));
                    objDetail.setBegQtyxx(loData.getString("nBegQtyxx"));
                    poDao.UpdateDetail(objDetail);
                    Log.d(TAG, "Inventory detail has been updated.");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<EInventoryMaster> GetInventoryMaster(String BranchCd){
        return poDao.GetInventoryMaster(BranchCd, AppConstants.CURRENT_DATE());
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

            EInventoryDetail loDetail = poDao.GetDetail(TransNox, Integer.parseInt(PartIDxx), BarCodex);

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
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean UploadInventory(String fsVal){
        try{
            EInventoryMaster loMaster = poDao.GetMasterIfExists(fsVal, AppConstants.CURRENT_DATE());

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
                    poApi.getUrlSubmitRandomStockInventory(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            poDao.UpdateUploadedDetails(lsTransNo);
            poDao.UpdateUploadedMaster(lsTransNo);
            Log.d(TAG, "Inventory has been uploaded successfully.");


            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public String SaveMasterForPosting(String fsVal, String Remarks){
        try{
            EInventoryMaster loMaster = poDao.GetMasterIfExists(fsVal, AppConstants.CURRENT_DATE());

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
            message = getLocalMessage(e);
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
                        poApi.getUrlSubmitRandomStockInventory(),
                        params.toString(),
                        poHeaders.getHeaders());

                if(lsResponse == null){
                    message = SERVER_NO_RESPONSE;
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("error")){
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = getErrorMessage(loError);
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
            message = getLocalMessage(e);
            return false;
        }
    }
}
