package org.guanzongroup.com.creditevaluation.Core;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EvaluatorManager {
    private static final String TAG = EvaluatorManager.class.getSimpleName();

    private final Application instance;

    private final HttpHeaders poHeaders;
    private final SessionManager poSession;
    private final RCreditOnlineApplicationCI poCI;
    private final RImageInfo poImage;
    private final WebApi poApis;
    private final AppConfigPreference poConfig;

    private String message;

    public interface OnActionCallback {
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public interface OnRetrieveDataCallback{
        void OnRetrieve(HashMap<oParentFndg, List<oChildFndg>> foEvaluate, ECreditOnlineApplicationCI foData);
        void OnFailed(String message);
    }

    public EvaluatorManager(Application application) {
        this.instance = application;
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poSession = new SessionManager(instance);
        this.poCI = new RCreditOnlineApplicationCI(instance);
        this.poImage = new RImageInfo(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApis = new WebApi(poConfig.getTestStatus());
    }

    public void DownloadCreditApplications(OnActionCallback callback){
        try{
            JSONObject params = new JSONObject();
//            params.put("sEmployID", poSession.getEmployeeID());
            params.put("sEmployID", "M00115000623");
            String lsResponse = WebClient.sendRequest(poApis.getUrlDownloadCIApplications(), params.toString(), poHeaders.getHeaders());
            if(lsResponse == null){
                callback.OnFailed("Server no response.");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    JSONArray loDetail = loResponse.getJSONArray("detail");
                    for(int x= 0 ; x < loDetail.length(); x++){
                        JSONObject loJson = loDetail.getJSONObject(x);
                        ECreditOnlineApplicationCI loApp = new ECreditOnlineApplicationCI();
                        loApp.setTransNox(loJson.getString("sTransNox"));
                        loApp.setCredInvx(loJson.getString("sCredInvx"));
                        loApp.setCredInvx(loJson.getString("xMgrUsrID"));
                        loApp.setAddressx(loJson.getString("sAddressx"));
                        loApp.setAddrFndg(loJson.getString("sAddrFndg"));
                        loApp.setAssetsxx(loJson.getString("sAssetsxx"));
                        loApp.setAsstFndg(loJson.getString("sAsstFndg"));
                        loApp.setIncomexx(loJson.getString("sIncomexx"));
                        loApp.setIncmFndg(loJson.getString("sIncmFndg"));
                        loApp.setRcmdRcd1(new AppConstants().DATE_MODIFIED);
                        poCI.SaveApplicationInfo(loApp);
                    }

                    Thread.sleep(1000);
                    if(DownloadBranchApplications()){
                        callback.OnSuccess("success");
                    } else {
                        callback.OnFailed(message);
                    }
//                    callback.OnSuccess("success");
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailed(lsMessage);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    private boolean DownloadBranchApplications() {
        try{
            JSONObject params = new JSONObject();

            String brnCD = poSession.getBranchCode();
            params.put("bycode", true);
            params.put("value", brnCD);

            String lsAddress = poApis.getUrlBranchLoanApp();
            String lsResponse = WebClient.sendRequest(lsAddress,
                    params.toString(),
                    poHeaders.getHeaders());

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("success")) {
                JSONArray laJson = loResponse.getJSONArray("detail");
                return new RBranchLoanApplication(instance).insertBranchApplicationInfos(laJson);
            } else {
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public void AddApplication(String TransNox, OnActionCallback callback){
        try{
            JSONObject params = new JSONObject();
            params.put("sTransNox", TransNox);
            String lsResponse = WebClient.sendRequest(poApis.getUrlAddForEvaluation(), params.toString(), poHeaders.getHeaders());
            if(lsResponse == null){
                callback.OnFailed("Server no response.");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    JSONArray loDetail = loResponse.getJSONArray("detail");
                    for(int x= 0 ; x < loDetail.length(); x++){
                        JSONObject loJson = loDetail.getJSONObject(x);
                        ECreditOnlineApplicationCI loApp = new ECreditOnlineApplicationCI();
                        loApp.setTransNox(loJson.getString("sTransNox"));
                        loApp.setCredInvx(loJson.getString("sCredInvx"));
                        loApp.setCredInvx(loJson.getString("xMgrUsrID"));
                        loApp.setAddressx(loJson.getString("sAddressx"));
                        loApp.setAddrFndg(loJson.getString("sAddrFndg"));
                        loApp.setAssetsxx(loJson.getString("sAssetsxx"));
                        loApp.setAsstFndg(loJson.getString("sAsstFndg"));
                        loApp.setIncomexx(loJson.getString("sIncomexx"));
                        loApp.setIncmFndg(loJson.getString("sIncmFndg"));
                        loApp.setRcmdRcd1(new AppConstants().DATE_MODIFIED);
                        poCI.SaveApplicationInfo(loApp);
                    }
                    List<ECreditApplication> loLoanApp = new ArrayList<>();
                    JSONArray loCredxx = loResponse.getJSONArray("credit_online");
                    for(int x = 0; x < loCredxx.length(); x++) {
                        JSONObject loJson = loCredxx.getJSONObject(x);
                        ECreditApplication loApp = new ECreditApplication();
                        loApp.setTransNox(loJson.getString("sTransNox"));
                        loApp.setBranchCd(loJson.getString("sBranchCd"));
                        loApp.setTransact(loJson.getString("dTransact"));
                        loApp.setClientNm(loJson.getString("sClientNm"));
                        loApp.setGOCASNox(loJson.getString("sGOCASNox"));
                        //TODO : Unit Applied is default to 0 cuz empty String is return from Server upon request
                        // this must be update when credit is available in Telecom
                        loApp.setUnitAppl("0");
                        loApp.setSourceCD(loJson.getString("sSourceCD"));
                        loApp.setDetlInfo(loJson.getString("sDetlInfo"));
                        loApp.setQMatchNo(loJson.getString("sQMatchNo"));
                        loApp.setWithCIxx(loJson.getString("cWithCIxx"));
                        loApp.setDownPaym(Double.parseDouble(loJson.getString("nDownPaym")));
                        loApp.setRemarksx(loJson.getString("sRemarksx"));
                        loApp.setCreatedx(loJson.getString("sCreatedx"));
                        loApp.setDateCreatedx(loJson.getString("dCreatedx"));
                        loApp.setSendStat("1");
                        loApp.setVerified(loJson.getString("sVerified"));
                        loApp.setDateVerified(loJson.getString("dVerified"));
                        loApp.setTranStat(loJson.getString("cTranStat"));
                        loApp.setDivision(loJson.getString("cDivision"));
                        loApp.setReceived(loJson.getString("dReceived"));
                        loApp.setCaptured("0");
                        loLoanApp.add(loApp);
                    }
                    new RCreditApplication(instance).insertBulkData(loLoanApp);
                    callback.OnSuccess("success");
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailed(lsMessage);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public LiveData<List<ECreditOnlineApplicationCI>> getForEvaluationList(){
        return poCI.getForEvaluationList();
    }

    public LiveData<List<DCreditOnlineApplicationCI.oDataEvaluationInfo>> getForEvaluationListData(){
        return poCI.getForEvaluationListData();
    }

    public LiveData<List<DCreditOnlineApplicationCI.oDataEvaluationInfo>> getForEvaluationListDataPreview(){
        return poCI.getForEvaluationListDataPreview();
    }

    public LiveData<DCreditOnlineApplicationCI.oDataEvaluationInfo> getForEvaluationInfo(String TransNox) {
        return poCI.getForEvaluateInfo(TransNox);
    }

    public LiveData<ECreditOnlineApplicationCI> getApplications(String TransNox) {
        return poCI.getApplications(TransNox);
    }

    public LiveData<List<ECreditOnlineApplicationCI>> getForPreviewResultList(){
        return poCI.getForPreviewResultList();
    }


    public HashMap<oParentFndg, List<oChildFndg>> parseToEvaluationData(ECreditOnlineApplicationCI foDetail) throws Exception{
        HashMap<oParentFndg, List<oChildFndg>> loForEval = new HashMap<>();

        loForEval.putAll(FindingsParser.getForEvaluation(oChildFndg.FIELDS.ADDRESS, foDetail.getAddressx(), foDetail.getAddrFndg()));
        loForEval.putAll(FindingsParser.getForEvaluation(oChildFndg.FIELDS.MEANS, foDetail.getIncomexx(), foDetail.getIncmFndg()));
        loForEval.putAll(FindingsParser.getForEvaluation(oChildFndg.FIELDS.ASSETS, foDetail.getAssetsxx(), foDetail.getAsstFndg()));

        return loForEval;
    }

    public HashMap<oParentFndg, List<oChildFndg>> parseToEvaluationPreviewData(ECreditOnlineApplicationCI foDetail) throws Exception{
        HashMap<oParentFndg, List<oChildFndg>> loForEval = new HashMap<>();

//        loForEval.putAll(PreviewParser.getForPreviewResult(oChildFndg.FIELDS.ADDRESS, foDetail.getAddressx(), foDetail.getAddrFndg()));
//        loForEval.putAll(PreviewParser.getForPreviewResult(oChildFndg.FIELDS.MEANS, foDetail.getIncomexx(), foDetail.getIncmFndg()));
//        loForEval.putAll(PreviewParser.getForPreviewResult(oChildFndg.FIELDS.ASSETS, foDetail.getAssetsxx(), foDetail.getAsstFndg()));

        return loForEval;
    }

    public LiveData<ECreditOnlineApplicationCI> RetrieveApplicationData(String TransNox){
        return poCI.RetrieveApplicationData(TransNox);
    }

    public void UpdateRecordInfo(String TransNox, String val){
        poCI.UpdateRecordInfo(TransNox, val);
    }

    public void UpdateRecordRemarks(String TransNox, String val){
        poCI.UpdateRecordRemarks(TransNox, val);
    }

    public void UpdatePresentBarangay(String TransNox, String val){
        poCI.UpdatePresentBarangay(TransNox, val);
    }

    public void UpdatePosition(String TransNox, String val){
        poCI.UpdatePosition(TransNox, val);
    }

    public void UpdateContact(String TransNox, String val){
        poCI.UpdateContact(TransNox, val);
    }

    public void UpdateNeighbor1(String TransNox, String val){
        poCI.UpdateNeighbor1(TransNox, val);
    }

    public void UpdateNeighbor2(String TransNox, String val){
        poCI.UpdateNeighbor2(TransNox, val);
    }

    public void UpdateNeighbor3(String TransNox, String val){
        poCI.UpdateNeighbor3(TransNox, val);
    }

    public void UpdateConfirmInfos(String TransNox, oParentFndg foParent, oChildFndg foChild, OnActionCallback callback) {
        try{
            String lsFindings = "";
            String lsResult = "";
            String lsField = foParent.getField();
            switch (foParent.getField()){
                case oChildFndg.FIELDS.ADDRESS:
                    lsFindings = poCI.getAddressForEvaluation(TransNox);
                    lsResult = CIResultHandler.toStringResult(lsField, lsFindings, foParent, foChild);
                    poCI.updateAddressEvaluation(TransNox, lsResult);
                    break;

                case oChildFndg.FIELDS.ASSETS:
                    lsFindings = poCI.getAssetsForEvaluation(TransNox);
                    lsResult = CIResultHandler.toStringResult(lsField, lsFindings, foParent, foChild);
                    poCI.updateAssetEvaluation(TransNox, lsResult);
                    break;

                case oChildFndg.FIELDS.MEANS:
                    lsFindings = poCI.getIncomeForEvaluation(TransNox);
                    lsResult = CIResultHandler.toStringResult(lsField, lsFindings, foParent, foChild);
                    poCI.updateIncomeEvaluation(TransNox, lsResult);
                    break;

            }
            callback.OnSuccess("Updated successfully");
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            callback.OnFailed("UpdateCIResult " + e.getMessage());
        }
    }

    public void SaveCIApproval(String TransNox, String fsResult, String fsRemarks, OnActionCallback callback){
        try{
            poCI.SaveCIApproval(TransNox, fsResult, fsRemarks, new AppConstants().DATE_MODIFIED);
            callback.OnSuccess("");
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void SaveBHApproval(String TransNox, String fsResult, String fsRemarks, OnActionCallback callback){
        try{
            poCI.SaveBHApproval(TransNox, fsResult, fsRemarks);
            callback.OnSuccess("");
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void SaveImageInfo(String TransNox, EImageInfo foImage, boolean isPrimary, OnActionCallback callback){
        try {
            if(poImage.CheckImageForCIExist(foImage.getSourceNo(), foImage.getDtlSrcNo()) == null) {
                foImage.setTransNox(poImage.getImageNextCode());
                foImage.setCaptured(new AppConstants().DATE_MODIFIED);
                foImage.setSourceCD("COAD");
                foImage.setFileCode("CI001");
                foImage.setDtlSrcNo(TransNox);
                foImage.setMD5Hashx(WebFileServer.createMD5Hash(foImage.getFileLoct()));
                poImage.insertImageInfo(foImage);
            } else {
                EImageInfo loImage = poImage.CheckImageForCIExist(foImage.getSourceNo(), foImage.getDtlSrcNo());
                poImage.UpdateImageInfoForCI(loImage);
            }

            String lsFindings = poCI.getAddressForEvaluation(TransNox);
            JSONObject loFndng = new JSONObject(lsFindings);
            JSONObject loChild;
            if(isPrimary) {
                loChild = loFndng.getJSONObject("present_address");
            } else {
                loChild = loFndng.getJSONObject("primary_address");
            }
            loChild.put("nLatitude", foImage.getLatitude());
            loChild.put("nLongitud", foImage.getLongitud());
            if(isPrimary) {
                loFndng.put("present_address", loChild);
            } else {
                loFndng.put("primary_address", loChild);
            }

            poCI.updateAddressEvaluation(TransNox, loFndng.toString());

            callback.OnSuccess("success");
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void UploadEvaluationResult(String Transnox, OnActionCallback callback){
        try{
            ECreditOnlineApplicationCI loDetail = poCI.getApplication(Transnox);
            JSONObject params = new JSONObject();
            params.put("sTransNox", loDetail.getTransNox());
            params.put("sAddressx", loDetail.getAddressx());
            params.put("sAddrFndg", new JSONObject(loDetail.getAddrFndg()));
            params.put("sAsstFndg", new JSONObject(loDetail.getAsstFndg()));
            params.put("sIncmFndg", new JSONObject(loDetail.getIncmFndg()));
            params.put("cHasRecrd", loDetail.getHasRecrd());
            params.put("sRecrdRem", loDetail.getRecrdRem());
            params.put("sPrsnBrgy", loDetail.getPrsnBrgy());
            params.put("sPrsnPstn", loDetail.getPrsnPstn());
            params.put("sPrsnNmbr", loDetail.getPrsnNmbr());
            params.put("sNeighBr1", loDetail.getNeighBr1());
            params.put("sNeighBr2", loDetail.getNeighBr2());
            params.put("sNeighBr3", loDetail.getNeighBr3());
            String lsResponse = WebClient.sendRequest(poApis.getUrlSubmitCIResult(), params.toString(), poHeaders.getHeaders());
            if(lsResponse == null){
                callback.OnFailed("Server no response.");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccess("Credit evaluation has been posted successfully");
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailed(lsMessage);
                }
            }

            String lsClient = "";
            String lsAccess = "";

            if(!poConfig.getTestStatus()){
                if(poImage.getCIImageForPosting(loDetail.getTransNox()) == null){
                    Log.e(TAG, "Unable to upload CI selfie image. No image found.");
                } else if(lsAccess.isEmpty()){
                    Log.e(TAG, "Unable to upload CI selfie image. Access token na generated.");
                } else {

                    String ProdctID, ClientID,UserIDxx;
                    ProdctID = poConfig.ProducID();
                    ClientID = poSession.getClientId();
                    UserIDxx = poSession.getUserID();

                    lsClient = WebFileServer.RequestClientToken(ProdctID, ClientID, UserIDxx);
                    lsAccess = WebFileServer.RequestAccessToken(lsClient);

                    EImageInfo loImage = poImage.getCIImageForPosting(loDetail.getTransNox());

                    org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                            loImage.getFileLoct(),
                            lsAccess,
                            loImage.getFileCode(),
                            loImage.getDtlSrcNo(),
                            loImage.getImageNme(),
                            poSession.getBranchCode(),
                            loImage.getSourceCD(),
                            loImage.getTransNox(),
                            "");

                    if (loUpload == null) {
                        Log.e(TAG, "Unable to upload CI selfie image. Access token na generated.");
                    } else {
                        String lsImgResult = (String) loUpload.get("result");

                        if (lsImgResult.equalsIgnoreCase("success")) {
                            String lsTransnox = (String) loUpload.get("sTransNox");
                            poImage.updateImageInfo(lsTransnox, loImage.getTransNox());
                            Log.d(TAG, "Selfie log image has been uploaded successfully.");
                        }
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void PostCIApproval(String TransNox, OnActionCallback callback){
        try{
            ECreditOnlineApplicationCI loDetail = poCI.getApplication(TransNox);
            JSONObject params = new JSONObject();
            params.put("sTransNox", loDetail.getTransNox());
            params.put("dRcmdRcv1", loDetail.getRcmdRcd1());
            params.put("dRcmdtnx1", loDetail.getRcmdtnx1());
            params.put("cRcmdtnx1", loDetail.getRcmdtnc1());
            params.put("sRcmdtnx1", loDetail.getRcmdtns1());
            params.put("sApproved", loDetail.getApproved());
            params.put("dApproved", loDetail.getDapprovd());
            Log.d("params", String.valueOf(params));
            String lsResponse = WebClient.sendRequest(poApis.getUrlPostCiApproval(), params.toString(), poHeaders.getHeaders());
            Log.d("response", lsResponse);
            if (lsResponse == null) {
                callback.OnFailed("Server no response.");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    poCI.UpdateTransactionSendStat(TransNox);
                    callback.OnSuccess("Approval sent successfully.");
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailed(lsMessage);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void DownloadApplicationsForBHApproval(OnActionCallback callback){
        try{
            JSONObject params = new JSONObject();
            params.put("sEmployID", "M00117000702");

            String lsResponse = WebClient.sendRequest(poApis.getUrlDownloadBhPreview(),
                    params.toString(), poHeaders.getHeaders());
            if(lsResponse == null){
                callback.OnFailed("Sever no response.");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    JSONArray loDetail = loResponse.getJSONArray("detail");
                    for(int x= 0 ; x < loDetail.length(); x++){
                        JSONObject loJson = loDetail.getJSONObject(x);
                        ECreditOnlineApplicationCI loApp = new ECreditOnlineApplicationCI();
                        loApp.setTransNox(loJson.getString("sTransNox"));
                        loApp.setCredInvx(loJson.getString("sCredInvx"));
                        loApp.setAddressx(loJson.getString("sAddressx"));
                        loApp.setAddrFndg(loJson.getString("sAddrFndg"));
                        loApp.setAssetsxx(loJson.getString("sAssetsxx"));
                        loApp.setAsstFndg(loJson.getString("sAsstFndg"));
                        loApp.setIncomexx(loJson.getString("sIncomexx"));
                        loApp.setIncmFndg(loJson.getString("sIncmFndg"));
                        loApp.setHasRecrd(loJson.getString("cHasRecrd"));
                        loApp.setRecrdRem(loJson.getString("sRecrdRem"));
                        loApp.setPrsnBrgy(loJson.getString("sPrsnBrgy"));
                        loApp.setPrsnPstn(loJson.getString("sPrsnPstn"));
                        loApp.setPrsnNmbr(loJson.getString("sPrsnNmbr"));
                        loApp.setNeighBr1(loJson.getString("sNeighBr1"));
                        loApp.setNeighBr2(loJson.getString("sNeighBr2"));
                        loApp.setNeighBr3(loJson.getString("sNeighBr3"));
                        loApp.setRcmdRcd1(loJson.getString("dRcmdRcd1"));
                        loApp.setRcmdtnx1(loJson.getString("dRcmdtnx1"));
                        loApp.setRcmdtnc1(loJson.getString("cRcmdtnx1"));
                        loApp.setRcmdtns1(loJson.getString("sRcmdtnx1"));
                        loApp.setRcmdRcd2(new AppConstants().DATE_MODIFIED);
                        loApp.setRcmdtnx2(new AppConstants().DATE_MODIFIED);
                        loApp.setTranStat(loJson.getString("cTranStat"));
                        poCI.SaveApplicationInfo(loApp);
                    }
                    callback.OnSuccess(loResponse.toString());
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailed(lsMessage);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void PostBHApproval(String TransNox, OnActionCallback callback){
        try{
            ECreditOnlineApplicationCI loDetail = poCI.getApplication(TransNox);
            JSONObject params = new JSONObject();
            params.put("sTransNox", loDetail.getTransNox());
            params.put("dRcmdRcv2", loDetail.getRcmdRcd2());
            params.put("dRcmdtnx2", loDetail.getRcmdRcd2());
            params.put("cRcmdtnx2", loDetail.getRcmdtnc2());
            params.put("sRcmdtnx2", loDetail.getRcmdtns2());
            String lsResponse = WebClient.sendRequest(poApis.getUrlPostBhApproval(), params.toString(), poHeaders.getHeaders());
            if (lsResponse == null) {
                callback.OnFailed("Server no response.");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    poCI.UpdateTransactionSendStat(TransNox);
                    callback.OnSuccess("Approval sent.");
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailed(lsMessage);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }
}
