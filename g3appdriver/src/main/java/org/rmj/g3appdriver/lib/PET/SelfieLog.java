package org.rmj.g3appdriver.lib.PET;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RInventoryDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RInventoryMaster;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.ArrayList;
import java.util.List;

public class SelfieLog {
    private static final String TAG = SelfieLog.class.getSimpleName();

    private final Application instance;

    private final RImageInfo poImage;
    private final RLogSelfie poLog;
    private final SessionManager poSession;
    private final AppConfigPreference poConfig;
    private final WebApi poApis;
    private final HttpHeaders poHeaders;

    private EImageInfo poImgInfo;
    private ELog_Selfie poSelfiex;

    public interface OnActionCallback{
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public SelfieLog(Application application) {
        this.instance = application;
        this.poImage = new RImageInfo(instance);
        this.poLog = new RLogSelfie(instance);
        this.poSession = new SessionManager(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApis = new WebApi(poConfig.isTesting_Phase());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public void setImageInfo(EImageInfo foImgInfo) {
        this.poImgInfo = foImgInfo;
        poImage.saveSelfieLogImage(poImgInfo);
    }

    public void setSelfieLogInfo(ELog_Selfie foSelfiex) {
        this.poSelfiex = foSelfiex;
        this.poSelfiex.setTransNox(poLog.getLogNextCode());
        poLog.insertSelfieLog(poSelfiex);
    }

    public void PostSelfieInfo(OnActionCallback callback){
        try {
            if(poImgInfo == null){
                callback.OnFailed("Unable to save selfie log. Image info is not set");
            } else if (poSelfiex == null){
                callback.OnFailed("Unable to save selfie log. Selfie info is not set");
            } else {
                JSONObject loJson = new JSONObject();
                loJson.put("sEmployID", poSelfiex.getEmployID());
                loJson.put("dLogTimex", poSelfiex.getLogTimex());
                loJson.put("nLatitude", poSelfiex.getLatitude());
                loJson.put("nLongitud", poSelfiex.getLongitud());
                loJson.put("sBranchCD", poSelfiex.getBranchCd());

                if(!poConfig.getTestStatus()) {
                    String lsClient = WebFileServer.RequestClientToken(poConfig.ProducID(),
                            poSession.getClientId(),
                            poSession.getUserID());
                    String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                    if (lsClient.isEmpty() || lsAccess.isEmpty()) {
                        callback.OnFailed("Failed to request generated Client or Access token.");
                    } else {
                        org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                                poImgInfo.getFileLoct(),
                                lsAccess,
                                poImgInfo.getFileCode(),
                                poImgInfo.getDtlSrcNo(),
                                poImgInfo.getImageNme(),
                                poSession.getBranchCode(),
                                poImgInfo.getSourceCD(),
                                poImgInfo.getTransNox(),
                                "");

                        if (loUpload != null) {
                            String lsImgResult = (String) loUpload.get("result");

                            if (lsImgResult.equalsIgnoreCase("success")) {
                                String lsTransnox = (String) loUpload.get("sTransNox");
                                poImage.updateImageInfo(lsTransnox, poImgInfo.getTransNox());
                                Log.d(TAG, "Selfie log image has been uploaded successfully.");
                            }
                        } else {
                            Log.e(TAG, "Unable to upload selfie image. Server no response.");
                        }
                    }
                }

                Thread.sleep(1000);

                String lsResponse = WebClient.sendRequest(poApis.getUrlPostSelfielog(), loJson.toString(), poHeaders.getHeaders());

                if(lsResponse == null){
                    callback.OnFailed("Unable to save selfie log. Server no response.");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String lsResult = loResponse.getString("result");
                    if(lsResult.equalsIgnoreCase("success")){
                        String TransNox = loResponse.getString("sTransNox");
                        String OldTrans = poSelfiex.getTransNox();
                        poLog.updateEmployeeLogStatus(TransNox, OldTrans);
                        callback.OnSuccess("Selfie log has been posted");
                    } else {
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = loError.getString("message");
                        callback.OnFailed(lsMessage);
                    }
                }
                callback.OnSuccess("Selfie image info has been save.");
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("SaveTransaction " + e.getMessage());
        }
    }

    public void RequestInventoryStocks(String fsBranch, OnActionCallback callback){
        try {
            RInventoryMaster loMastrx = new RInventoryMaster(instance);
            RInventoryDetail loDtailx = new RInventoryDetail(instance);
            if (poSession.getEmployeeLevel().equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))) {
                JSONObject params = new JSONObject();
                if(fsBranch.isEmpty()){
                    params.put("branchcd", poSelfiex.getBranchCd());
                } else {
                    params.put("branchcd", fsBranch);
                }
                String lsResponse = WebClient.sendRequest(poApis.getUrlRequestRandomStockInventory(), params.toString(), poHeaders.getHeaders());
                if (lsResponse == null) {
                    callback.OnFailed("Server no response");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String lsResult = loResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
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
                        loMastrx.insertInventoryMaster(loMaster);

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
                        loDtailx.insertInventoryDetail(inventoryDetails);
                        poConfig.setInventoryCount(true);
                    } else {
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = loError.getString("message");
                        poConfig.setInventoryCount(false);
                        if(lsMessage.equalsIgnoreCase("No stocks to count.")){
                            callback.OnFailed(lsMessage);
                        } else {
                            callback.OnFailed(lsMessage);
                        }
                    }
                }
            } else {
                callback.OnFailed("Employee is not authorize to request inventory.");
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("RequestInventoryStocks" + e.getMessage());
        }
    }
}
