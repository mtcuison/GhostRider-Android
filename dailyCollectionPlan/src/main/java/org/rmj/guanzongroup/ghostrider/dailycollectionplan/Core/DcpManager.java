package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction.CustomerNotAround;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction.OthTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction.Paid;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction.PromiseToPay;

import java.util.ArrayList;
import java.util.List;

public class DcpManager {
    private static final String TAG = DcpManager.class.getSimpleName();

    private final Application instance;

    private final RDailyCollectionPlan poDcp;
    private final RBranch poBranch;
    private final AppConfigPreference poConfig;
    private final RCollectionUpdate poUpdate;
    private final REmployee poEmploye;
    private final DcpAPIs poApis;
    private final HttpHeaders poHeaders;
    private final SessionManager poUser;
    private final RImageInfo poImage;
    private final Telephony poTlphny;
    private final RClientUpdate poClient;
    private final RCollectionUpdate poDcpUpdte;

    public interface OnActionCallback{
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public DcpManager(Application application) {
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(instance);
        this.poBranch = new RBranch(instance);
        this.poUpdate = new RCollectionUpdate(instance);
        this.poEmploye = new REmployee(instance);
        this.poApis = new DcpAPIs(true);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poUser = new SessionManager(instance);
        this.poImage = new RImageInfo(instance);
        this.poTlphny = new Telephony(instance);
        this.poClient = new RClientUpdate(instance);
        this.poDcpUpdte = new RCollectionUpdate(instance);
    }

    public void ImportDcpMaster(OnActionCallback callback) {
        try{
//            String lsEmployID = poEmploye.getEmployeeID();
            String lsEmployID = "M00121000570";
            if(lsEmployID == null ||
                    lsEmployID.equalsIgnoreCase("")){
                callback.OnFailed("Unable to download DCP. Please relogin your account and try again.");
            } else {
                JSONObject loJson = new JSONObject();
                loJson.put("sEmployID", lsEmployID);
//                loJson.put("dTransact", AppConstants.CURRENT_DATE);
                loJson.put("dTransact", "2022-02-22");
                loJson.put("cDCPTypex", "1");

                String lsResponse = WebClient.sendRequest(poApis.getUrlDownloadDcp(), loJson.toString(), poHeaders.getHeaders());
                if(lsResponse == null){
                    callback.OnSuccess("Server no response");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String lsResult = loResponse.getString("result");
                    if (!lsResult.equalsIgnoreCase("success")){
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = loError.getString("message");
                        callback.OnFailed(lsMessage);
                    } else {
                        JSONObject loMaster = loResponse.getJSONObject("master");
                        String lsTransNox = loMaster.getString("sTransNox");

                        if(poDcp.getCollectionMasterIfExist(lsTransNox).size() > 0){
                            callback.OnFailed("Record already exist on local data.");
                        } else {
                            EDCPCollectionMaster collectionMaster = new EDCPCollectionMaster();
                            collectionMaster.setTransNox(loMaster.getString("sTransNox"));
                            collectionMaster.setTransact(loMaster.getString("dTransact"));
                            collectionMaster.setReferNox(loMaster.getString("sReferNox"));
                            collectionMaster.setCollName(loMaster.getString("xCollName"));
                            collectionMaster.setRouteNme(loMaster.getString("sRouteNme"));
                            collectionMaster.setReferDte(loMaster.getString("dReferDte"));
                            collectionMaster.setTranStat(loMaster.getString("cTranStat"));
                            collectionMaster.setDCPTypex(loMaster.getString("cDCPTypex"));
                            collectionMaster.setEntryNox(loMaster.getString("nEntryNox"));
                            collectionMaster.setBranchNm(loMaster.getString("sBranchNm"));
                            collectionMaster.setCollctID(loMaster.getString("sCollctID"));
                            poDcp.insertMasterData(collectionMaster);
                        }

                        if(poDcp.getCollectionMasterIfExist(lsTransNox).size() > 0){
                            callback.OnFailed("Record already exist on local data.");
                        } else {
                            JSONArray laJson = loResponse.getJSONArray("detail");
                            List<EDCPCollectionDetail> collectionDetails = new ArrayList<>();
                            for(int x = 0; x < laJson.length(); x++){
                                JSONObject loDetail = laJson.getJSONObject(x);
                                EDCPCollectionDetail collectionDetail = new EDCPCollectionDetail();
                                collectionDetail.setTransNox(loMaster.getString("sTransNox"));
                                collectionDetail.setEntryNox(Integer.parseInt(loDetail.getString("nEntryNox")));
                                collectionDetail.setAcctNmbr(loDetail.getString("sAcctNmbr"));
                                collectionDetail.setFullName(loDetail.getString("xFullName"));
                                collectionDetail.setIsDCPxxx(loDetail.getString("cIsDCPxxx"));
                                collectionDetail.setMobileNo(loDetail.getString("sMobileNo"));
                                collectionDetail.setHouseNox(loDetail.getString("sHouseNox"));
                                collectionDetail.setAddressx(loDetail.getString("sAddressx"));
                                collectionDetail.setBrgyName(loDetail.getString("sBrgyName"));
                                collectionDetail.setTownName(loDetail.getString("sTownName"));
                                collectionDetail.setPurchase(loDetail.getString("dPurchase"));
                                collectionDetail.setAmtDuexx(loDetail.getString("nAmtDuexx"));
                                collectionDetail.setApntUnit(loDetail.getString("cApntUnit"));
                                collectionDetail.setDueDatex(loDetail.getString("dDueDatex"));
                                collectionDetail.setLongitud(loDetail.getString("nLongitud"));
                                collectionDetail.setLatitude(loDetail.getString("nLatitude"));
                                collectionDetail.setClientID(loDetail.getString("sClientID"));
                                collectionDetail.setSerialID(loDetail.getString("sSerialID"));
                                collectionDetail.setSerialNo(loDetail.getString("sSerialNo"));
                                collectionDetail.setLastPaym(loDetail.getString("nLastPaym"));
                                collectionDetail.setLastPaid(loDetail.getString("dLastPaym"));
                                collectionDetail.setABalance(loDetail.getString("nABalance"));
                                collectionDetail.setDelayAvg(loDetail.getString("nDelayAvg"));
                                collectionDetail.setMonAmort(loDetail.getString("nMonAmort"));
                                collectionDetails.add(collectionDetail);
                            }
                            poDcp.insertDetailBulkData(collectionDetails);
                        }
                        callback.OnSuccess("");
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("ImportDcpMaster : " + e.getMessage());
            Log.d(TAG, "ImportDcpMaster : " + e.getMessage());
        }
    }

    public iDCPTransaction getTransaction(String fsRemCode){
        switch (fsRemCode) {
            case "PAY":
                return new Paid(instance);
            case "PTP":
                return new PromiseToPay(instance);
            case "CNA":
                return new CustomerNotAround(instance);
            default:
                return new OthTransaction(instance);
        }
    }

    public void PostLRDCPTransaction(String fsRemarks, String fsTransNo, String fsAccount, OnActionCallback callback){
        try{
            String lsProdtID = poConfig.ProducID();
            String lsClntIDx = poUser.getClientId();
            String lsUserIDx = poUser.getUserID();

            EDCPCollectionDetail loDetail = poDcp.getCollectionDetail(fsTransNo, fsAccount);

            String lsClient = WebFileServer.RequestClientToken(lsProdtID, lsClntIDx, lsUserIDx);
            String lsAccess = WebFileServer.RequestAccessToken(lsClient);

            if(loDetail == null){
                callback.OnFailed("No account info found.");
            } else if(loDetail.getRemCodex().isEmpty()){
                JSONObject loData = new JSONObject();
                JSONObject loJson = new JSONObject();
                loData.put("sRemarksx", fsRemarks);
                loJson.put("sTransNox", loDetail.getTransNox());
                loJson.put("nEntryNox", loDetail.getEntryNox());
                loJson.put("sAcctNmbr", loDetail.getAcctNmbr());
                loJson.put("sRemCodex", "NV");
                loJson.put("dModified", new AppConstants().DATE_MODIFIED);
                loJson.put("sJsonData", loData);
                loJson.put("dReceived", "");
                loJson.put("sUserIDxx", poUser.getUserID());
                loJson.put("sDeviceID", poTlphny.getDeviceID());
            } else {
                EImageInfo loImage = poImage.getDCPImageInfoForPosting(fsTransNo, fsAccount);
                if(loImage == null){
                     callback.OnFailed("Unable to upload collection detail image. No image info found");
                } else {
                    org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                            loImage.getFileLoct(),
                            lsAccess,
                            loImage.getFileCode(),
                            loDetail.getAcctNmbr(),
                            loImage.getImageNme(),
                            poUser.getBranchCode(),
                            loImage.getSourceCD(),
                            loDetail.getTransNox(),
                            "");

                    String lsResult = (String) loUpload.get("result");
                    if(lsResult == null){
                        callback.OnFailed("Unable to upload collection detail image. Server has no response.");
                    } else {
                        if(lsResult.equalsIgnoreCase("success")){
                            String lsTransNo = (String) loUpload.get("sTransNox");
                            poImage.updateImageInfo(lsTransNo, loImage.getTransNox());

                        } else {

                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("PostLRDCPTransaction " + e.getMessage());
        }
    }

    public void UpdateNotVisitedCollections(String fsRemarks, OnActionCallback callback){
        String lsTransNox = poDcp.getUnpostedDcpMaster();
        poDcp.updateNotVisitedCollections(fsRemarks, lsTransNox);
    }

    public void PostLRDCPCollection(OnActionCallback callback){
        try{
            List<EDCPCollectionDetail> loDcpList = poDcp.getLRDCPCollectionForPosting();

            String lsProdtID = poConfig.ProducID();
            String lsClntIDx = poUser.getClientId();
            String lsUserIDx = poUser.getUserID();

            String lsClient = WebFileServer.RequestClientToken(lsProdtID, lsClntIDx, lsUserIDx);
            String lsAccess = WebFileServer.RequestAccessToken(lsClient);

            if(loDcpList == null){
                callback.OnFailed("No record for posting");
            } else {
                for(int x = 0; x < loDcpList.size(); x++){
                    EDCPCollectionDetail loDcp = loDcpList.get(x);
                    JSONObject loData = new JSONObject();
                    JSONObject loJson = new JSONObject();
                    String lsRemCode = loDcp.getRemCodex();

                    String lsTransNo = loDcp.getTransNox();
                    String lsAccntNo = loDcp.getAcctNmbr();
                    switch (lsRemCode){
                        case "":
                            loData.put("sRemarksx", loDcp.getRemarksx());
                            loJson.put("sRemCodex", "NV");
                            loJson.put("dModified", new AppConstants().DATE_MODIFIED);
                            break;
                        case "PAY":
                            loData.put("sPRNoxxxx", loDcp.getPRNoxxxx());
                            loData.put("nTranAmtx", loDcp.getTranAmtx());
                            loData.put("nDiscount", loDcp.getDiscount());
                            loData.put("nOthersxx", loDcp.getOthersxx());
                            loData.put("cTranType", loDcp.getTranType());
                            loData.put("nTranTotl", loDcp.getTranTotl());
                            loData.put("sRemarksx", loDcp.getRemarksx());
                            loJson.put("sRemCodex", loDcp.getRemCodex());
                            loJson.put("dModified", loDcp.getModified());
                            break;
                        case "PTP":
                            //Required parameters for Promise to pay..
                            loData.put("cApntUnit", loDcp.getApntUnit());
                            loData.put("sBranchCd", loDcp.getBranchCd());
                            loData.put("dPromised", loDcp.getPromised());
                            loJson.put("sRemCodex", loDcp.getRemCodex());
                            loJson.put("dModified", loDcp.getModified());

                            EImageInfo loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);

                            loData.put("sImageNme", loImage.getImageNme());
                            loData.put("sSourceCD", loImage.getSourceCD());
                            loData.put("nLongitud", loImage.getLongitud());
                            loData.put("nLatitude", loImage.getLatitude());
                            break;
                        case "LUn":
                        case "TA":
                        case "FO":
                            EClientUpdate loClient = poClient.getClientUpdateInfoForPosting(lsTransNo, lsAccntNo);
                            //TODO: replace JSON parameters get the parameters which is being generated by RClientUpdate...
                            loData.put("sLastName", loClient.getLastName());
                            loData.put("sFrstName", loClient.getFrstName());
                            loData.put("sMiddName", loClient.getMiddName());
                            loData.put("sSuffixNm", loClient.getSuffixNm());
                            loData.put("sHouseNox", loClient.getHouseNox());
                            loData.put("sAddressx", loClient.getAddressx());
                            loData.put("sTownIDxx", loClient.getTownIDxx());
                            loData.put("cGenderxx", loClient.getGenderxx());
                            loData.put("cCivlStat", loClient.getCivlStat());
                            loData.put("dBirthDte", loClient.getBirthDte());
                            loData.put("dBirthPlc", loClient.getBirthPlc());
                            loData.put("sLandline", loClient.getLandline());
                            loData.put("sMobileNo", loClient.getMobileNo());
                            loData.put("sEmailAdd", loClient.getEmailAdd());

                            loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);

                            loData.put("sImageNme", loImage.getImageNme());
                            loData.put("sSourceCD", loImage.getSourceCD());
                            loData.put("nLongitud", loImage.getLongitud());
                            loData.put("nLatitude", loImage.getLatitude());
                            loJson.put("sRemCodex", loDcp.getRemCodex());
                            loJson.put("dModified", loDcp.getModified());
                            break;
                        default:
                            loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);
                            loData.put("sImageNme", loImage.getImageNme());
                            loData.put("sSourceCD", loImage.getSourceCD());
                            loData.put("nLongitud", loImage.getLongitud());
                            loData.put("nLatitude", loImage.getLatitude());
                    }

                    loJson.put("sTransNox", loDcp.getTransNox());
                    loJson.put("nEntryNox", loDcp.getEntryNox());
                    loJson.put("sAcctNmbr", loDcp.getAcctNmbr());

                    loJson.put("sJsonData", loData);
                    loJson.put("dReceived", "");
                    loJson.put("sUserIDxx", poUser.getUserID());
                    loJson.put("sDeviceID", poTlphny.getDeviceID());

                    String lsResponse = WebClient.sendRequest(poApis.getUrlDcpSubmit(), loJson.toString(), poHeaders.getHeaders());

                    if(lsResponse == null) {
                        Log.d(TAG, "Posting LR DCP. Server no response.");
                    } else {
                        JSONObject loResponse = new JSONObject(lsResponse);
                        String lsResult = loResponse.getString("result");
                        if(lsResult.equalsIgnoreCase("success")){
                            Log.d(TAG, "Posting LR DCP. Detail has been submitted.");
                            poDcp.updateCollectionDetailStatus(loDcp.getTransNox(), loDcp.getEntryNox());
                        } else {
                            JSONObject loError = loResponse.getJSONObject("error");
                            String lsMessage = loError.getString("message");
                            Log.d(TAG, "Posting LR DCP " + lsMessage);
                        }
                    }

                    EImageInfo loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);

                    org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                            loImage.getFileLoct(),
                            lsAccess,
                            loImage.getFileCode(),
                            loDcp.getAcctNmbr(),
                            loImage.getImageNme(),
                            poUser.getBranchCode(),
                            loImage.getSourceCD(),
                            loDcp.getTransNox(),
                            "");

                    String lsResult = (String) loUpload.get("result");
                    if (lsResult == null) {
                        callback.OnFailed("Unable to upload collection detail image. Server has no response.");
                    } else {
                        if (lsResult.equalsIgnoreCase("success")) {

                        } else {

                        }
                    }

                    Thread.sleep(1000);
                }
                callback.OnSuccess("");
            }

        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("PostLRDCPCollection " + e.getMessage());
        }
    }

    public void PostDcpMaster(OnActionCallback callback){
        try{
            String lsTransNox = poDcp.getUnpostedDcpMaster();
            int lnPostdDcp = poDcp.getUnsentCollectionDetail(lsTransNox);
            String lsStatus = poDcp.getMasterSendStatus(lsTransNox);
            if (lnPostdDcp == 0 &&
                    lsStatus == null){
                JSONObject loJson = new JSONObject();
                loJson.put("sTransNox", lsTransNox);
                String lsResponse = WebClient.sendRequest(poApis.getUrlPostDcp(), loJson.toString(), poHeaders.getHeaders());
                if (lsResponse == null) {
                    callback.OnFailed("Posting dcp master failed. Server no response");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String result = loResponse.getString("result");
                    if (result.equalsIgnoreCase("success")) {
                        poDcp.updateSentPostedDCPMaster(lsTransNox);
                        callback.OnSuccess("Dcp for today has been posted.");
                    } else {
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = loError.getString("message");
                        callback.OnFailed("Posting dcp master failed." + lsMessage);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("Posting dcp master failed. " + e.getMessage());
        }
    }
}
