package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core;

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.EClientUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GCircle.room.Entities.EImageInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GCircle.room.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GCircle.room.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GCircle.room.Repositories.RImageInfo;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Device.Telephony;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.g3appdriver.dev.Api.WebFileServer;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.obj.RClientUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction.CustomerNotAround;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction.OthTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction.Paid;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction.PromiseToPay;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Service.GLocatorService;

import java.util.List;

public class DcpManager {
    private static final String TAG = DcpManager.class.getSimpleName();

    private final Application instance;

    private final RDailyCollectionPlan poDcp;
    private final AppConfigPreference poConfig;
    private final RCollectionUpdate poUpdate;
    private final GCircleApi poApis;
    private final HttpHeaders poHeaders;
    private final EmployeeSession poUser;
    private final RImageInfo poImage;
    private final Telephony poTlphny;
    private final RClientUpdate poClient;
    private final RCollectionUpdate poDcpUpdte;

    public interface OnActionCallback{
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public interface OnValidateCallback{
        void OnSuccess(boolean hasNV, String args);
        void OnFailed(String message);
    }

    public interface OnSearchCallback{
        void OnSuccess(List<EDCPCollectionDetail> foDetail);
        void OnFailed(String message);
    }

    public DcpManager(Application application) {
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(instance);
        this.poUpdate = new RCollectionUpdate(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poUser = EmployeeSession.getInstance(instance);
        this.poImage = new RImageInfo(instance);
        this.poTlphny = new Telephony(instance);
        this.poClient = new RClientUpdate(instance);
        this.poDcpUpdte = new RCollectionUpdate(instance);
        this.poApis = new GCircleApi(instance);
    }

//    public void ImportDcpMaster(String EmployID, String ReferDte, OnActionCallback callback) {
//        try{
//            String lsEmployID;
//            String lsReferDte;
//            if(poConfig.getTestStatus()) {
//                lsEmployID = EmployID;
//                lsReferDte = ReferDte;
//            } else {
//                lsEmployID = poUser.getEmployeeID();
//                lsReferDte = AppConstants.CURRENT_DATE;
//            }
//            if(lsEmployID == null || lsEmployID.equalsIgnoreCase("")){
//                callback.OnFailed("Unable to download DCP. Please re-login your account and try again.");
//            } else {
//                JSONObject loJson = new JSONObject();
//                loJson.put("sEmployID", lsEmployID);
//                loJson.put("dTransact", lsReferDte);
//                loJson.put("cDCPTypex", "1");
//
//                Log.d(TAG, loJson.toString());
//
//                String lsResponse = WebClient.sendRequest(poApis.getUrlDownloadDcp(), loJson.toString(), poHeaders.getHeaders());
//                if(lsResponse == null){
//                    callback.OnSuccess("Server no response");
//                } else {
//                    JSONObject loResponse = new JSONObject(lsResponse);
//                    String lsResult = loResponse.getString("result");
//                    if (!lsResult.equalsIgnoreCase("success")){
//                        JSONObject loError = loResponse.getJSONObject("error");
//                        String lsMessage = loError.getString("message");
//                        callback.OnFailed(lsMessage);
//                    } else {
//                        JSONObject loMaster = loResponse.getJSONObject("master");
//                        String lsTransNox = loMaster.getString("sTransNox");
//
//                        if(poDcp.getCollectionMasterIfExist(lsTransNox).size() >= 1){
//                            callback.OnFailed("Record already exist on local data.");
//                        } else {
//                            EDCPCollectionMaster collectionMaster = new EDCPCollectionMaster();
//                            collectionMaster.setTransNox(loMaster.getString("sTransNox"));
//                            collectionMaster.setTransact(loMaster.getString("dTransact"));
//                            collectionMaster.setReferNox(loMaster.getString("sReferNox"));
//                            collectionMaster.setCollName(loMaster.getString("xCollName"));
//                            collectionMaster.setRouteNme(loMaster.getString("sRouteNme"));
//                            collectionMaster.setReferDte(loMaster.getString("dReferDte"));
//                            collectionMaster.setTranStat(loMaster.getString("cTranStat"));
//                            collectionMaster.setDCPTypex(loMaster.getString("cDCPTypex"));
//                            collectionMaster.setEntryNox(loMaster.getString("nEntryNox"));
//                            collectionMaster.setBranchNm(loMaster.getString("sBranchNm"));
//                            collectionMaster.setCollctID(loMaster.getString("sCollctID"));
//                            poDcp.insertMasterData(collectionMaster);
//
//                            JSONArray laJson = loResponse.getJSONArray("detail");
//                            List<EDCPCollectionDetail> collectionDetails = new ArrayList<>();
//                            for(int x = 0; x < laJson.length(); x++){
//                                JSONObject loDetail = laJson.getJSONObject(x);
//                                EDCPCollectionDetail collectionDetail = new EDCPCollectionDetail();
//                                collectionDetail.setTransNox(loMaster.getString("sTransNox"));
//                                collectionDetail.setEntryNox(Integer.parseInt(loDetail.getString("nEntryNox")));
//                                collectionDetail.setAcctNmbr(loDetail.getString("sAcctNmbr"));
//                                collectionDetail.setFullName(loDetail.getString("xFullName"));
//                                collectionDetail.setIsDCPxxx(loDetail.getString("cIsDCPxxx"));
//                                collectionDetail.setMobileNo(loDetail.getString("sMobileNo"));
//                                collectionDetail.setHouseNox(loDetail.getString("sHouseNox"));
//                                collectionDetail.setAddressx(loDetail.getString("sAddressx"));
//                                collectionDetail.setBrgyName(loDetail.getString("sBrgyName"));
//                                collectionDetail.setTownName(loDetail.getString("sTownName"));
//                                collectionDetail.setPurchase(loDetail.getString("dPurchase"));
//                                collectionDetail.setAmtDuexx(loDetail.getString("nAmtDuexx"));
//                                collectionDetail.setApntUnit(loDetail.getString("cApntUnit"));
//                                collectionDetail.setDueDatex(loDetail.getString("dDueDatex"));
//                                collectionDetail.setLongitud(loDetail.getString("nLongitud"));
//                                collectionDetail.setLatitude(loDetail.getString("nLatitude"));
//                                collectionDetail.setClientID(loDetail.getString("sClientID"));
//                                collectionDetail.setSerialID(loDetail.getString("sSerialID"));
//                                collectionDetail.setSerialNo(loDetail.getString("sSerialNo"));
//                                collectionDetail.setLastPaym(loDetail.getString("nLastPaym"));
//                                collectionDetail.setLastPaid(loDetail.getString("dLastPaym"));
//                                collectionDetail.setABalance(loDetail.getString("nABalance"));
//                                collectionDetail.setDelayAvg(loDetail.getString("nDelayAvg"));
//                                collectionDetail.setMonAmort(loDetail.getString("nMonAmort"));
//                                collectionDetails.add(collectionDetail);
//                            }
//                            poDcp.insertDetailBulkData(collectionDetails);
//
//                            callback.OnSuccess("Collection for today has been imported successfully");
//                        }
//                    }
//                }
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//            callback.OnFailed("ImportDcpMaster : " + e.getMessage());
//            Log.d(TAG, "ImportDcpMaster : " + e.getMessage());
//        }
//    }
//    public void getSearchList(String fsClientNm, OnSearchCallback callback){
//        List<EDCPCollectionDetail> loSearch = new ArrayList<>();
//        try{
//            EDCPCollectionMaster loMaster = poDcp.CheckIfHasCollection();
//            if(loMaster == null){
//                callback.OnFailed("Unable to add collection. No DCP has been created for today.");
//            } else if("1".equalsIgnoreCase(loMaster.getSendStat())){
//                callback.OnFailed("Unable to add collection. DCP is already posted.");
//            } else {
//                JSONObject loJson = new JSONObject();
//                loJson.put("value", fsClientNm);
//                loJson.put("bycode", false);
//
//                int lnEntryNox = 1;
//                if(poDcp.getDetailCollection(loMaster.getTransNox()) != null){
//                    List<EDCPCollectionDetail> loDcp = poDcp.getDetailCollection(loMaster.getTransNox());
//                    if(loDcp.size() > 0) {
//                        int lnEntry = loDcp.get(0).getEntryNox();
//                        lnEntryNox = lnEntry + 1;
//                    }
//                }
//
//                String lsResponse = WebClient.sendRequest(poApis.getUrlGetArClient(), loJson.toString(), poHeaders.getHeaders());
//                if(lsResponse == null){
//                    callback.OnFailed("Server no response");
//                } else {
//                    JSONObject loResponse = new JSONObject(lsResponse);
//                    String lsResult = loResponse.getString("result");
//                    if (!lsResult.equalsIgnoreCase("success")){
//                        JSONObject loError = loResponse.getJSONObject("error");
//                        String lsMessage = loError.getString("message");
//                        callback.OnFailed(lsMessage);
//                    } else {
//                        JSONArray laJson = loResponse.getJSONArray("data");
//                        for(int x = 0; x < laJson.length(); x++) {
//                            JSONObject loDetail = laJson.getJSONObject(x);
//                            EDCPCollectionDetail collectionDetail = new EDCPCollectionDetail();
//                            collectionDetail.setTransNox(loMaster.getTransNox());
//                            collectionDetail.setEntryNox(lnEntryNox);
//                            try {
//                                collectionDetail.setAcctNmbr(loDetail.getString("sAcctNmbr"));
//                            } catch (Exception e){
//                                e.printStackTrace();
//                            }
//                            collectionDetail.setFullName(loDetail.getString("xFullName"));
//                            collectionDetail.setIsDCPxxx("0");
//                            collectionDetail.setMobileNo(loDetail.getString("sMobileNo"));
//                            collectionDetail.setHouseNox(loDetail.getString("sHouseNox"));
//                            collectionDetail.setAddressx(loDetail.getString("sAddressx"));
//                            collectionDetail.setBrgyName(loDetail.getString("sBrgyName"));
//                            collectionDetail.setTownName(loDetail.getString("sTownName"));
//                            collectionDetail.setClientID(loDetail.getString("sClientID"));
//                            collectionDetail.setSerialID(loDetail.getString("sSerialID"));
//                            collectionDetail.setSerialNo(loDetail.getString("sSerialNo"));
//                            collectionDetail.setLongitud(loDetail.getString("nLongitud"));
//                            collectionDetail.setLatitude(loDetail.getString("nLatitude"));
//                            collectionDetail.setDueDatex(loDetail.getString("dDueDatex"));
//                            collectionDetail.setMonAmort(loDetail.getString("nMonAmort"));
//                            collectionDetail.setLastPaym(loDetail.getString("nLastPaym"));
//                            collectionDetail.setLastPaid(loDetail.getString("dLastPaym"));
//                            collectionDetail.setAmtDuexx(loDetail.getString("nAmtDuexx"));
//                            collectionDetail.setABalance(loDetail.getString("nABalance"));
//                            collectionDetail.setDelayAvg(loDetail.getString("nDelayAvg"));
//                            loSearch.add(collectionDetail);
//                        }
//                        callback.OnSuccess(loSearch);
//                    }
//                }
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//            callback.OnFailed(e.getMessage());
//        }
//    }

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

    public void PostLRDCPTransaction(String fsTransNo, String fsAccount, OnActionCallback callback){
        try{
            String lsProdtID = poConfig.ProducID();
            String lsClntIDx = poUser.getClientId();
            String lsUserIDx = poUser.getUserID();

            EDCPCollectionDetail loDcp = poDcp.getCollectionDetail(fsTransNo, fsAccount);
            JSONObject loData = new JSONObject();
            JSONObject loJson = new JSONObject();
            String lsRemCode = loDcp.getRemCodex();

            String lsTransNo = loDcp.getTransNox();
            String lsAccntNo = loDcp.getAcctNmbr();

            EImageInfo loImage;
            switch (lsRemCode){
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

                    loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);

                    if(loImage != null || loImage.getImageNme() != null) {
                        if(!poConfig.getTestStatus()) {
                        loData.put("sImageNme", loImage.getImageNme());
                        loData.put("sSourceCD", loImage.getSourceCD());
                        loData.put("nLongitud", loImage.getLongitud());
                        loData.put("nLatitude", loImage.getLatitude());
                        }
                    }
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

                    if(loImage != null || loImage.getImageNme() != null) {
                        if(!poConfig.getTestStatus()) {
                        loData.put("sImageNme", loImage.getImageNme());
                        loData.put("sSourceCD", loImage.getSourceCD());
                        loData.put("nLongitud", loImage.getLongitud());
                        loData.put("nLatitude", loImage.getLatitude());
                        loJson.put("sRemCodex", loDcp.getRemCodex());
                        loJson.put("dModified", loDcp.getModified());
                        }
                    }
                    break;

                case "CNA":
                    JSONObject paramAddress = new JSONObject();
                    JSONObject paramMobile = new JSONObject();
                    loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);

                    if(loImage != null || loImage.getImageNme() != null) {
                        if(!poConfig.getTestStatus()) {
                        paramAddress.put("nLatitude", Double.parseDouble(loImage.getLatitude()));
                        paramAddress.put("nLongitud", Double.parseDouble(loImage.getLongitud()));
                        paramAddress.put("sImageNme", loImage.getImageNme());
                        paramMobile.put("sImageNme", loImage.getImageNme());
                        }
                    } else {
                        paramAddress.put("nLatitude", 0.0);
                        paramAddress.put("nLongitud", 0.0);
                        paramAddress.put("sImageNme", "testCase");
                        paramMobile.put("sImageNme", "testCase");
                    }
                    String lsClientID = loDcp.getClientID();

                    if(poUpdate.getAddressUpdateInfoForPosting(lsClientID) != null){
                        EAddressUpdate loAddress = poUpdate.getAddressUpdateInfoForPosting(lsClientID);
                        paramAddress.put("cReqstCDe", loAddress.getReqstCDe());
                        paramAddress.put("cAddrssTp", loAddress.getAddrssTp());
                        paramAddress.put("sHouseNox", loAddress.getHouseNox());
                        paramAddress.put("sAddressx", loAddress.getAddressx());
                        paramAddress.put("sTownIDxx", loAddress.getTownIDxx());
                        paramAddress.put("sBrgyIDxx", loAddress.getBrgyIDxx());
                        paramAddress.put("cPrimaryx", loAddress.getPrimaryx());
                        paramAddress.put("sRemarksx", loAddress.getRemarksx());
                        loData.put("Address", paramAddress);
                    }

                    if(poUpdate.getMobileUpdateInfoForPosting(lsClientID) != null){
                        EMobileUpdate loMobile = poUpdate.getMobileUpdateInfoForPosting(lsClientID);
                        paramMobile.put("cReqstCDe", loMobile.getReqstCDe());
                        paramMobile.put("sMobileNo", loMobile.getMobileNo());
                        paramMobile.put("cPrimaryx", loMobile.getPrimaryx());
                        paramMobile.put("sRemarksx", loMobile.getRemarksx());
                        loData.put("Mobile", paramMobile);
                    }
                    break;

                default:
                    loData.put("sRemarksx", loDcp.getRemarksx());
                    loJson.put("sRemCodex", "NV");
                    loJson.put("dModified", new AppConstants().DATE_MODIFIED);
                    loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);

                    if(loImage != null || loImage.getImageNme() != null) {
                        if(!poConfig.getTestStatus()) {
                        loData.put("sImageNme", loImage.getImageNme());
                        loData.put("sSourceCD", loImage.getSourceCD());
                        loData.put("nLongitud", loImage.getLongitud());
                        loData.put("nLatitude", loImage.getLatitude());
                        }
                    }
            }

            loJson.put("sTransNox", loDcp.getTransNox());
            loJson.put("nEntryNox", loDcp.getEntryNox());
            loJson.put("sAcctNmbr", loDcp.getAcctNmbr());

            loJson.put("sJsonData", loData);
            loJson.put("dReceived", "");
            loJson.put("sUserIDxx", poUser.getUserID());
            loJson.put("sDeviceID", poTlphny.getDeviceID());
            Log.d(TAG, "DCP posting data: " + loJson.toString());

            String lsResponse = WebClient.sendRequest(poApis.getUrlDcpSubmit(), loJson.toString(), poHeaders.getHeaders());

            if(lsResponse == null) {
                Log.e(TAG, "Posting collection Image with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Failed!");
                Log.e(TAG, "Error : Server no response.");
                callback.OnFailed("Server no response.");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    Log.d(TAG, "Posting collection Image with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Success!");
                    poDcp.updateCollectionDetailStatus(loDcp.getTransNox(), loDcp.getEntryNox());
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = getErrorMessage(loError);
                    callback.OnFailed(lsMessage);
                    Log.e(TAG, "Posting collection Image with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Failed!");
                    Log.e(TAG, "Error : " + lsMessage);
                }
            }

            if(!poConfig.getTestStatus()) {
                if(!loDcp.getRemCodex().equalsIgnoreCase("PAY")) {
                    String lsClient = WebFileServer.RequestClientToken(lsProdtID, lsClntIDx, lsUserIDx);
                    String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                    loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);

                    if (loImage != null) {
//                        org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
//                                loImage.getFileLoct(),
//                                lsAccess,
//                                loImage.getFileCode(),
//                                loDcp.getAcctNmbr(),
//                                loImage.getImageNme(),
//                                poUser.getBranchCode(),
//                                loImage.getSourceCD(),
//                                loDcp.getTransNox(),
//                                "");
//
//                        String lsResult = (String) loUpload.get("result");
//                        if (lsResult == null) {
//                            Log.e(TAG, "Posting collection Image with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Failed!");
//                            Log.d(TAG, "Error : SERVER_NO_RESPONSE);
//                        } else {
//                            if (lsResult.equalsIgnoreCase("success")) {
//                                String lsImageID = (String) loUpload.get("sTransNox");
//                                poImage.updateImageInfo(lsImageID, loImage.getTransNox());
//                                Log.d(TAG, "Posting collection Image with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Success!");
//                            } else {
//                                JSONObject loError = new JSONObject((String) loUpload.get("error"));
//                                String lsMessage = loError.getString("message");
//                                Log.d(TAG, "Posting collection Image with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Failed!");
//                                Log.d(TAG, "Error : " + lsMessage);
//                            }
//                        }
                    }else {
                        Log.d(TAG, "No DCP image found for remarks code: " + lsRemCode);
                    }
                }
            }
            callback.OnSuccess("Collection posted successfully");
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("PostLRDCPTransaction " + e.getMessage());
        }
    }

    public void PostLRDCPCollection(OnActionCallback callback){
        try{
            List<EDCPCollectionDetail> loDcpList = poDcp.getLRDCPCollectionForPosting();

            String lsProdtID = poConfig.ProducID();
            String lsClntIDx = poUser.getClientId();
            String lsUserIDx = poUser.getUserID();

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
                    Log.d(TAG, "DCP transaction number: " + lsTransNo);
                    Log.d(TAG, "DCP client account number: " + lsAccntNo);
                    Log.d(TAG, "DCP transaction remarks code: " + lsRemCode);

                    EImageInfo loImage;
                    switch (lsRemCode){
                        case "PAY":
                            loData.put("sPRNoxxxx", loDcp.getPRNoxxxx());
                            loData.put("nTranAmtx", loDcp.getTranAmtx());
                            loData.put("nDiscount", loDcp.getDiscount());
                            loData.put("nOthersxx", loDcp.getOthersxx());
                            loData.put("cTranType", loDcp.getTranType());
                            loData.put("nTranTotl", loDcp.getTranTotl());
                            break;
                        case "PTP":
                            //Required parameters for Promise to pay..
                            loData.put("cApntUnit", loDcp.getApntUnit());
                            loData.put("sBranchCd", loDcp.getBranchCd());
                            loData.put("dPromised", loDcp.getPromised());
                            loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);

                            if(loImage != null || loImage.getImageNme() != null) {
                                if(!poConfig.getTestStatus()) {
                                    loData.put("sImageNme", loImage.getImageNme());
                                    loData.put("sSourceCD", loImage.getSourceCD());
                                    loData.put("nLongitud", loImage.getLongitud());
                                    loData.put("nLatitude", loImage.getLatitude());
                                }
                            }
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
                            if(loImage != null || loImage.getImageNme() != null) {
                                if(!poConfig.getTestStatus()) {
                                    loData.put("sImageNme", loImage.getImageNme());
                                    loData.put("sSourceCD", loImage.getSourceCD());
                                    loData.put("nLongitud", loImage.getLongitud());
                                    loData.put("nLatitude", loImage.getLatitude());
                                }
                            }
                            break;

                        case "CNA":
                            JSONObject paramAddress = new JSONObject();
                            JSONObject paramMobile = new JSONObject();
                            if(!poConfig.getTestStatus()) {
                                loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);
                                if(loImage != null || loImage.getImageNme() != null) {
                                    if(!poConfig.getTestStatus()) {
                                        loData.put("sImageNme", loImage.getImageNme());
                                        loData.put("sSourceCD", loImage.getSourceCD());
                                        loData.put("nLongitud", loImage.getLongitud());
                                        loData.put("nLatitude", loImage.getLatitude());
                                    }
                                }
                            } else {
                                paramAddress.put("nLatitude", 0.0);
                                paramAddress.put("nLongitud", 0.0);
                                paramAddress.put("sImageNme", "testCase");
                                paramMobile.put("sImageNme", "testCase");
                            }
                            String lsClientID = loDcp.getClientID();

                            if(poUpdate.getAddressUpdateInfoForPosting(lsClientID) != null){
                                EAddressUpdate loAddress = poUpdate.getAddressUpdateInfoForPosting(lsClientID);
                                paramAddress.put("cReqstCDe", loAddress.getReqstCDe());
                                paramAddress.put("cAddrssTp", loAddress.getAddrssTp());
                                paramAddress.put("sHouseNox", loAddress.getHouseNox());
                                paramAddress.put("sAddressx", loAddress.getAddressx());
                                paramAddress.put("sTownIDxx", loAddress.getTownIDxx());
                                paramAddress.put("sBrgyIDxx", loAddress.getBrgyIDxx());
                                paramAddress.put("cPrimaryx", loAddress.getPrimaryx());
                                paramAddress.put("sRemarksx", loAddress.getRemarksx());
                                loData.put("Address", paramAddress);
                            }

                            if(poUpdate.getMobileUpdateInfoForPosting(lsClientID) != null){
                                EMobileUpdate loMobile = poUpdate.getMobileUpdateInfoForPosting(lsClientID);
                                paramMobile.put("cReqstCDe", loMobile.getReqstCDe());
                                paramMobile.put("sMobileNo", loMobile.getMobileNo());
                                paramMobile.put("cPrimaryx", loMobile.getPrimaryx());
                                paramMobile.put("sRemarksx", loMobile.getRemarksx());
                                loData.put("Mobile", paramMobile);
                            }

                            break;
                    }
                    loData.put("sRemarksx", loDcp.getRemarksx());
                    loJson.put("sRemCodex", loDcp.getRemCodex());
                    loJson.put("dModified", loDcp.getModified());

                    loJson.put("sTransNox", loDcp.getTransNox());
                    loJson.put("nEntryNox", loDcp.getEntryNox());
                    loJson.put("sAcctNmbr", loDcp.getAcctNmbr());

                    loJson.put("sJsonData", loData);
                    loJson.put("dReceived", "");
                    loJson.put("sUserIDxx", poUser.getUserID());
                    loJson.put("sDeviceID", poTlphny.getDeviceID());

                    String lsResponse = WebClient.sendRequest(poApis.getUrlDcpSubmit(), loJson.toString(), poHeaders.getHeaders());

                    if(lsResponse == null) {
                        Log.e(TAG, "Posting collection Image with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Failed!");
                        Log.e(TAG, "Error : Server no response.");
                    } else {
                        JSONObject loResponse = new JSONObject(lsResponse);
                        String lsResult = loResponse.getString("result");
                        if(lsResult.equalsIgnoreCase("success")){
                            Log.d(TAG, "Posting collection with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Success!");
                            poDcp.updateCollectionDetailStatus(loDcp.getTransNox(), loDcp.getEntryNox());
                        } else {
                            JSONObject loError = loResponse.getJSONObject("error");
                            String lsMessage = getErrorMessage(loError);
                            Log.e(TAG, "Posting collection with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Failed!");
                            Log.e(TAG, "Error : " + lsMessage);
                        }
                    }

                    if(!poConfig.getTestStatus()) {
                        String lsClient = WebFileServer.RequestClientToken(lsProdtID, lsClntIDx, lsUserIDx);
                        String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                        loImage = poImage.getDCPImageInfoForPosting(lsTransNo, lsAccntNo);

//                        org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
//                                loImage.getFileLoct(),
//                                lsAccess,
//                                loImage.getFileCode(),
//                                loDcp.getAcctNmbr(),
//                                loImage.getImageNme(),
//                                poUser.getBranchCode(),
//                                loImage.getSourceCD(),
//                                loDcp.getTransNox(),
//                                "");

//                        String lsResult = (String) loUpload.get("result");
//                        if (lsResult == null) {
//                            Log.e(TAG, "Posting collection Image with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Failed!");
//                            Log.d(TAG, "Error : SERVER_NO_RESPONSE);
//                        } else {
//                            if (lsResult.equalsIgnoreCase("success")) {
//                                String lsImageID = (String) loUpload.get("sTransNox");
//                                poImage.updateImageInfo(lsImageID, loImage.getTransNox());
//                                Log.d(TAG, "Posting collection Image with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Success!");
//                            } else {
//                                JSONObject loError = new JSONObject((String) loUpload.get("error"));
//                                String lsMessage = loError.getString("message");
//                                Log.d(TAG, "Posting collection Image with account no. :" + loDcp.getAcctNmbr() + ", " + loDcp.getRemCodex() + "Failed!");
//                                Log.d(TAG, "Error : " + lsMessage);
//                            }
//                        }
                    }

                    Thread.sleep(1000);
                }
                callback.OnSuccess("DCP Transactions Posted Successfully.");
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
                String lsResponse = WebClient.sendRequest(poApis.getUrlPostDcpMaster(), loJson.toString(), poHeaders.getHeaders());
                if (lsResponse == null) {
                    callback.OnFailed("Posting dcp master failed. Server no response");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String result = loResponse.getString("result");
                    if (result.equalsIgnoreCase("success")) {
                        poDcp.updateSentPostedDCPMaster(lsTransNox);
                        callback.OnSuccess("Dcp for today has been posted.");
                        instance.stopService(new Intent(instance, GLocatorService.class));
                    } else {
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = getErrorMessage(loError);
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
