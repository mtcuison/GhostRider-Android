package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
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
    }

    public void ImportDcpMaster(OnActionCallback callback) {
        try{
            String lsEmployID = poEmploye.getEmployeeID();
            if(lsEmployID == null ||
                    lsEmployID.equalsIgnoreCase("")){
                callback.OnFailed("Unable to download DCP. Please relogin your account and try again.");
            } else {
                JSONObject loJson = new JSONObject();
                loJson.put("sEmployID", lsEmployID);
                loJson.put("dTransact", AppConstants.CURRENT_DATE);
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
                                collectionDetail.setEntryNox(Integer.parseInt(loJson.getString("nEntryNox")));
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
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("ImportDcpMaster : " + e.getMessage());
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

    public void PostLRDCPCollection(String fsRemarks, OnActionCallback callback){
        try{

        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("PostLRDCPCollection " + e.getMessage());
        }
    }
}
