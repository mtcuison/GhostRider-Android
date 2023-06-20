package org.rmj.g3appdriver.GCircle.Apps.CreditInvestigator.Obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GCircle.Apps.CreditInvestigator.pojo.BarangayRecord;
import org.rmj.g3appdriver.GCircle.Apps.CreditInvestigator.pojo.CIImage;
import org.rmj.g3appdriver.GCircle.Apps.CreditInvestigator.pojo.oChildFndg;
import org.rmj.g3appdriver.GCircle.Apps.CreditInvestigator.pojo.oParentFndg;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditOnlineApplicationCI;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GCircle.room.Entities.EImageInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.GCircle.room.Repositories.RImageInfo;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CITagging {
    private static final String TAG = CITagging.class.getSimpleName();

    private final DCreditOnlineApplicationCI poDao;

    private final EmployeeMaster poUser;

    private final HttpHeaders poHeaders;
    private final EmployeeSession poSession;
    private final RImageInfo poImage;
    private final GCircleApi poApis;

    private String message;

    public interface OnRetrieveDataCallback{
        void OnRetrieve(HashMap<oParentFndg, List<oChildFndg>> foEvaluate, ECreditOnlineApplicationCI foData);
        void OnFailed(String message);
    }

    public CITagging(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).creditEvaluationDao();
        this.poUser = new EmployeeMaster(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poSession = EmployeeSession.getInstance(instance);
        this.poImage = new RImageInfo(instance);
        this.poApis = new GCircleApi(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public boolean DownloadForCIApplications(){
        try{
            JSONObject params = new JSONObject();
            params.put("sEmployID", poSession.getEmployeeID());
            String lsResponse = WebClient.sendRequest(
                    poApis.getUrlDownloadCIApplications(),
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

            JSONArray loDetail = loResponse.getJSONArray("detail");
            for(int x= 0 ; x < loDetail.length(); x++){
                JSONObject loJson = loDetail.getJSONObject(x);

                ECreditOnlineApplicationCI loExist = poDao.GetApplication(loJson.getString("sTransNox"));

                if(loExist == null){
                    ECreditOnlineApplicationCI loApp = new ECreditOnlineApplicationCI();

                    loApp.setTransNox(loJson.getString("sTransNox"));
                    loApp.setCredInvx(loJson.getString("sCredInvx"));
                    loApp.setManagerx(loJson.getString("xMgrUsrID"));
                    loApp.setAddressx(loJson.getString("sAddressx"));
                    loApp.setAddrFndg(loJson.getString("sAddrFndg"));
                    loApp.setAssetsxx(loJson.getString("sAssetsxx"));
                    loApp.setAsstFndg(loJson.getString("sAsstFndg"));
                    loApp.setIncomexx(loJson.getString("sIncomexx"));
                    loApp.setIncmFndg(loJson.getString("sIncmFndg"));
                    loApp.setRcmdRcd1(AppConstants.DATE_MODIFIED());
                    loApp.setTimeStmp(loJson.getString("dTimeStmp"));
//                    if(loJson.getString("dRcmdRcd1").equalsIgnoreCase("null")){
//                    } else if(loJson.getString("dRcmdRcd2").equalsIgnoreCase("null")) {
//                        loApp.setHasRecrd(loJson.getString("cHasRecrd"));
//                        loApp.setRecrdRem(loJson.getString("sRecrdRem"));
//                        loApp.setPrsnBrgy(loJson.getString("sPrsnBrgy"));
//                        loApp.setPrsnPstn(loJson.getString("sPrsnPstn"));
//                        loApp.setPrsnNmbr(loJson.getString("sPrsnNmbr"));
//                        loApp.setNeighBr1(loJson.getString("sNeighBr1"));
//                        loApp.setNeighBr2(loJson.getString("sNeighBr2"));
//                        loApp.setNeighBr3(loJson.getString("sNeighBr3"));
//                        loApp.setRcmdRcd1(loJson.getString("dRcmdRcd1"));
//                        loApp.setRcmdtnx1(loJson.getString("dRcmdtnx1"));
//                        loApp.setRcmdtnc1(loJson.getString("cRcmdtnx1"));
//                        loApp.setRcmdtns1(loJson.getString("sRcmdtnx1"));
//                        loApp.setRcmdRcd2(AppConstants.DATE_MODIFIED());
//                        loApp.setRcmdtnx2(AppConstants.DATE_MODIFIED());
//                        loApp.setTranStat(loJson.getString("cTranStat"));
//                    } else {
//                        loApp.setHasRecrd(loJson.getString("cHasRecrd"));
//                        loApp.setRecrdRem(loJson.getString("sRecrdRem"));
//                        loApp.setPrsnBrgy(loJson.getString("sPrsnBrgy"));
//                        loApp.setPrsnPstn(loJson.getString("sPrsnPstn"));
//                        loApp.setPrsnNmbr(loJson.getString("sPrsnNmbr"));
//                        loApp.setNeighBr1(loJson.getString("sNeighBr1"));
//                        loApp.setNeighBr2(loJson.getString("sNeighBr2"));
//                        loApp.setNeighBr3(loJson.getString("sNeighBr3"));
//                        loApp.setRcmdRcd1(loJson.getString("dRcmdRcd1"));
//                        loApp.setRcmdtnx1(loJson.getString("dRcmdtnx1"));
//                        loApp.setRcmdtnc1(loJson.getString("cRcmdtnx1"));
//                        loApp.setRcmdtns1(loJson.getString("sRcmdtnx1"));
//                        loApp.setRcmdRcd2(loJson.getString("dRcmdRcd2"));
//                        loApp.setRcmdtnx2(loJson.getString("dRcmdtnx2"));
//                        loApp.setRcmdtnc2(loJson.getString("cRcmdtnx2"));
//                        loApp.setRcmdtns2(loJson.getString("sRcmdtnx2"));
//                        loApp.setTranStat(loJson.getString("cTranStat"));
//                        loApp.setUploaded("1");
//                        loApp.setSendStat("1");
//                    }

                    poDao.SaveApplicationInfo(loApp);
                    Log.d(TAG, "CI Record has been saved.");
                } else {
                    if(loJson.getString("cTranStat").equalsIgnoreCase("1")) {
                        Date ldDate1 = SQLUtil.toDate(loExist.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                        Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                        if (ldDate1.compareTo(ldDate2) == -1) {
                            loExist.setTransNox(loJson.getString("sTransNox"));
                            loExist.setCredInvx(loJson.getString("sCredInvx"));
                            loExist.setManagerx(loJson.getString("xMgrUsrID"));
                            loExist.setAddressx(loJson.getString("sAddressx"));
                            loExist.setAddrFndg(loJson.getString("sAddrFndg"));
                            loExist.setAssetsxx(loJson.getString("sAssetsxx"));
                            loExist.setAsstFndg(loJson.getString("sAsstFndg"));
                            loExist.setIncomexx(loJson.getString("sIncomexx"));
                            loExist.setIncmFndg(loJson.getString("sIncmFndg"));
                            loExist.setHasRecrd(loJson.getString("cHasRecrd"));
                            loExist.setRecrdRem(loJson.getString("sRecrdRem"));
                            loExist.setPrsnBrgy(loJson.getString("sPrsnBrgy"));
                            loExist.setPrsnPstn(loJson.getString("sPrsnPstn"));
                            loExist.setPrsnNmbr(loJson.getString("sPrsnNmbr"));
                            loExist.setNeighBr1(loJson.getString("sNeighBr1"));
                            loExist.setNeighBr2(loJson.getString("sNeighBr2"));
                            loExist.setNeighBr3(loJson.getString("sNeighBr3"));
                            loExist.setRcmdRcd1(loJson.getString("dRcmdRcd1"));
                            loExist.setRcmdtnx1(loJson.getString("dRcmdtnx1"));
                            loExist.setRcmdtnc1(loJson.getString("cRcmdtnx1"));
                            loExist.setRcmdtns1(loJson.getString("sRcmdtnx1"));
                            loExist.setRcmdRcd2(loJson.getString("dRcmdRcd2"));
                            loExist.setRcmdtnx2(loJson.getString("dRcmdtnx2"));
                            loExist.setRcmdtnc2(loJson.getString("cRcmdtnx2"));
                            loExist.setRcmdtns2(loJson.getString("sRcmdtnx2"));
                            loExist.setTranStat(loJson.getString("cTranStat"));
                            loExist.setTimeStmp(loJson.getString("dTimeStmp"));
                            loExist.setUploaded("1");
                            loExist.setSendStat("1");
                            poDao.UpdateApplication(loExist);
                            Log.d(TAG, "CI Record has been updated.");
                        }
                    }
                }

                Thread.sleep(1000);
                Log.d(TAG, "Downloading application. Transaction number: " + loJson.getString("sTransNox"));
                if(DownloadForCIApplicationDetails(loJson.getString("sTransNox"))){
                    Log.d(TAG, "Credit app info successfully downloaded.");
                } else {
                    Log.d(TAG, "Failed to download credit app. " + message);
                }
                Thread.sleep(1000);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    private boolean DownloadForCIApplicationDetails(String TransNox) {
        try{
            JSONObject params = new JSONObject();
            params.put("sTransNox", TransNox);
            String lsAddress = poApis.getUrlDownloadCreditAppForCI();
            String lsResponse = WebClient.sendRequest(
                    lsAddress,
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            JSONObject loJson = loResponse.getJSONObject("detail");
            EBranchLoanApplication loApp = poDao.CheckIFExist(loJson.getString("sTransNox"));
            if(loApp == null){
                EBranchLoanApplication loDetail = new EBranchLoanApplication();
                loDetail.setTransNox(loJson.getString("sTransNox"));
                loDetail.setBranchCD(loJson.getString("sBranchCd"));
                loDetail.setTransact(loJson.getString("dTransact"));
                loDetail.setCredInvx(loJson.getString("sCredInvx"));
                loDetail.setCompnyNm(loJson.getString("sCompnyNm"));
                loDetail.setSpouseNm(loJson.getString("sSpouseNm"));
                loDetail.setAddressx(loJson.getString("sAddressx"));
                loDetail.setMobileNo(loJson.getString("sMobileNo"));
                loDetail.setQMAppCde(loJson.getString("sQMAppCde"));
                loDetail.setModelNme(loJson.getString("sModelNme"));
                loDetail.setDownPaym(loJson.getString("nDownPaym"));
                loDetail.setAcctTerm(loJson.getString("nAcctTerm"));
                loDetail.setCreatedX(loJson.getString("sCreatedx"));
                loDetail.setTranStat(loJson.getString("cTranStat"));
                loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                poDao.SaveNewRecord(loDetail);
                Log.d(TAG, "New record saved.");
            } else {
                Date ldDate1 = SQLUtil.toDate(loApp.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                if (!ldDate1.equals(ldDate2)){
                    loApp.setTransNox(loJson.getString("sTransNox"));
                    loApp.setBranchCD(loJson.getString("sBranchCd"));
                    loApp.setTransact(loJson.getString("dTransact"));
                    loApp.setCredInvx(loJson.getString("sCredInvx"));
                    loApp.setCompnyNm(loJson.getString("sCompnyNm"));
                    loApp.setSpouseNm(loJson.getString("sSpouseNm"));
                    loApp.setAddressx(loJson.getString("sAddressx"));
                    loApp.setMobileNo(loJson.getString("sMobileNo"));
                    loApp.setQMAppCde(loJson.getString("sQMAppCde"));
                    loApp.setModelNme(loJson.getString("sModelNme"));
                    loApp.setDownPaym(loJson.getString("nDownPaym"));
                    loApp.setAcctTerm(loJson.getString("nAcctTerm"));
                    loApp.setCreatedX(loJson.getString("sCreatedx"));
                    loApp.setTranStat(loJson.getString("cTranStat"));
                    loApp.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.UpdateExistingRecord(loApp);
                    Log.d(TAG, "New record has been updated.");
                }
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean AddApplication(String TransNox){
        try{
            JSONObject params = new JSONObject();
            params.put("sTransNox", TransNox);
            String lsResponse = WebClient.sendRequest(
                    poApis.getUrlDownloadCIApplications(),
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

            JSONArray loDetail = loResponse.getJSONArray("detail");
            for(int x= 0 ; x < loDetail.length(); x++){
                JSONObject loJson = loDetail.getJSONObject(x);
                ECreditOnlineApplicationCI loApp = new ECreditOnlineApplicationCI();
                loApp.setTransNox(loJson.getString("sTransNox"));
                loApp.setCredInvx(loJson.getString("sCredInvx"));
                loApp.setManagerx(loJson.getString("xMgrUsrID"));
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
                loApp.setTranStat(loJson.getString("cTranStat"));

                if(poSession.getEmployeeID().equalsIgnoreCase(loApp.getCredInvx())){
                    loApp.setTransfer("0");
                } else {
                    loApp.setTransfer("1");
                }

                poDao.SaveApplicationInfo(loApp);

                Thread.sleep(1000);
                Log.d(TAG, "Downloading application. Transaction number: " + loJson.getString("sTransNox"));
                if(DownloadForCIApplicationDetails(loJson.getString("sTransNox"))){
                    Log.d(TAG, "Credit app info successfully downloaded.");
                } else {
                    Log.d(TAG, "Failed to download credit app. " + message);
                }
                Thread.sleep(1000);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public LiveData<List<DCreditOnlineApplicationCI.oDataEvaluationInfo>> GetForEvaluationListData(){
        return poDao.getForEvaluationListData();
    }

    public LiveData<List<DCreditOnlineApplicationCI.oDataEvaluationInfo>> GetForEvaluationListDataPreview(){
        return poDao.getForEvaluationListDataPreview();
    }

    public LiveData<DCreditOnlineApplicationCI.oDataEvaluationInfo> getForEvaluationInfo(String TransNox) {
        return poDao.getForEvaluateInfo(TransNox);
    }

    public LiveData<ECreditOnlineApplicationCI> GetApplicationDetail(String TransNox) {
        return poDao.getApplications(TransNox);
    }

    public LiveData<ECreditOnlineApplicationCI> RetrieveApplicationData(String TransNox){
        return poDao.RetrieveApplicationData(TransNox);
    }

    public boolean UpdateBarangayRecord(BarangayRecord foVal){
        try{
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return false;
            }

            String lsTransNo = foVal.getsTransNox();
            ECreditOnlineApplicationCI loDetail = poDao.GetApplication(lsTransNo);

            if(loDetail == null){
                message = "Unable to find record to update. Please restart the app and try again.";
                return false;
            }

            loDetail.setHasRecrd(foVal.getcHasRecrd());
            loDetail.setRecrdRem(foVal.getsRecrdRem());
            loDetail.setPrsnBrgy(foVal.getsBrgyPrsn());
            loDetail.setPrsnPstn(foVal.getsBrgyPstn());
            loDetail.setPrsnNmbr(foVal.getsPrsnNmbr());
            poDao.UpdateApplication(loDetail);
            Log.d(TAG, "Barangay record save!");

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public void UpdateNeighbor1(String TransNox, String val){
        poDao.UpdateNeighbor1(TransNox, val);
    }

    public void UpdateNeighbor2(String TransNox, String val){
        poDao.UpdateNeighbor2(TransNox, val);
    }

    public void UpdateNeighbor3(String TransNox, String val){
        poDao.UpdateNeighbor3(TransNox, val);
    }

    public boolean ValidateTagging(String TransNox, String fsKeyxx, List<String> foList){
        try{
            String lsEmployID = poDao.GetEmployeeID();
            ECreditOnlineApplicationCI loCiApp = poDao.GetApplication(TransNox);

            if(!lsEmployID.equalsIgnoreCase(loCiApp.getCredInvx())){

                for(int x = 0;  x < foList.size(); x++){
                    if(fsKeyxx.equalsIgnoreCase(foList.get(x))){
                        message = "Unable to update already tag details.";
                        return false;
                    }
                }

            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean SaveCIResult(String TransNox, String fsParnt, String fsKEyxx, String fsValue){
        try{
            String lsFndng;
            JSONObject loJson;
            JSONObject loDetail;

            if(fsParnt.isEmpty()){
                lsFndng = poDao.getAssetsForEvaluation(TransNox);
                loJson = new JSONObject(lsFndng);
                loJson.put(fsKEyxx, fsValue);
                poDao.updateAssetEvaluation(TransNox, loJson.toString());
            } else {
                switch (fsParnt) {
                    case "present_address":
                    case "primary_address":
                        lsFndng = poDao.getAddressForEvaluation(TransNox);
                        loJson = new JSONObject(lsFndng);
                        loDetail = loJson.getJSONObject(fsParnt);
                        loDetail.put(fsKEyxx, fsValue);
                        loJson.put(fsParnt, loDetail);
                        poDao.updateAddressEvaluation(TransNox, loJson.toString());
                        break;
                    case "employed":
                    case "self_employed":
                    case "financed":
                    case "pensioner":
                        lsFndng = poDao.getIncomeForEvaluation(TransNox);
                        loJson = new JSONObject(lsFndng);
                        loDetail = loJson.getJSONObject(fsParnt);
                        loDetail.put(fsKEyxx, fsValue);
                        loJson.put(fsParnt, loDetail);
                        poDao.updateIncomeEvaluation(TransNox, loJson.toString());
                        break;
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean SaveCIApproval(String TransNox, String fsResult, String fsRemarks){
        try{
            String lsDate = AppConstants.DATE_MODIFIED();
            Log.d(TAG, lsDate);
            poDao.SaveCIApproval(TransNox, fsResult, fsRemarks, lsDate);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean SaveBHApproval(String TransNox, String fsResult, String fsRemarks){
        try{
            poDao.SaveBHApproval(TransNox, fsResult, fsRemarks);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean SaveImageInfo(CIImage foVal){
        try {
            String lsTransNo = foVal.getTransNox();
            String lsFileNme = foVal.getFileName();
            String lsFilePth = foVal.getFilePath();
            String lnLatitde = foVal.getLatitude();
            String lnLongtde = foVal.getLongtude();

            String lsResult = poImage.SaveCIImage(lsTransNo, lsFileNme, lsFilePth, lnLatitde, lnLongtde);

            if(lsResult == null){
                message = poImage.getMessage();
                return false;
            }

            //this method updates the tagging of address
            String lsFindings = poDao.getAddressForEvaluation(lsTransNo);
            JSONObject loFndng = new JSONObject(lsFindings);
            JSONObject loChild;
            boolean isPrimary = foVal.isPrimaryAddress();

            if(isPrimary) {
                loChild = loFndng.getJSONObject("present_address");
            } else {
                loChild = loFndng.getJSONObject("primary_address");
            }
            loChild.put("nLatitude", foVal.getLatitude());
            loChild.put("nLongitud", foVal.getLongtude());

            if(isPrimary) {
                loFndng.put("present_address", loChild);
            } else {
                loFndng.put("primary_address", loChild);
            }

            poDao.updateAddressEvaluation(lsTransNo, loFndng.toString());

            String lsParentx = foVal.getsParentxx();

            String lsFndng = poDao.getAddressForEvaluation(lsTransNo);
            JSONObject loJson = new JSONObject(lsFndng);
            JSONObject loDetail = loJson.getJSONObject(lsParentx);
            loDetail.put("nLatitude", lnLatitde);
            loDetail.put("nLongitud", lnLongtde);
            loJson.put(lsParentx, loDetail);
            Log.d(TAG, loJson.toString());
            poDao.updateAddressEvaluation(lsTransNo, loJson.toString());

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean UploadEvaluationResult(String Transnox){
        try{
            ECreditOnlineApplicationCI loDetail = poDao.getApplication(Transnox);
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

            String lsResponse = WebClient.sendRequest(
                    poApis.getUrlSubmitCIResult(),
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

            poDao.UpdateUploadedResult(loDetail.getTransNox());

            List<EImageInfo> loImages = poDao.GetCIImagesForPosting(loDetail.getTransNox(), loDetail.getTransNox());

            if(loImages == null){
                 message = "No ci image found";
                 Log.e(TAG, message);
                 return true;
            } else if(loImages.size() == 0){
                message = "No ci image found";
                Log.e(TAG, message);
                return true;
            } else if(poImage.CheckTokenAvailable() == null){
                message = "CI result uploaded successfully. But images are not uploaded. " + poImage.getMessage();
            } else {
                for(int x = 0; x < loImages.size(); x++){
                    String lsImageID = loImages.get(x).getTransNox();

                    lsImageID = poImage.UploadImage(lsImageID);
                    if(lsImageID == null){
                        message = poImage.getMessage();
                        Log.e(TAG, message);
                        Thread.sleep(1000);
                        continue;
                    }

                    Thread.sleep(1000);
                }
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean PostCIApproval(String TransNox){
        try{
            ECreditOnlineApplicationCI loDetail = poDao.getApplication(TransNox);
            if(loDetail.getUploaded().equalsIgnoreCase("0")){
                message = "Please upload CI result before posting recommendation";
                return false;
            }

            JSONObject params = new JSONObject();
            params.put("sTransNox", loDetail.getTransNox());
            params.put("dRcmdRcv1", loDetail.getRcmdRcd1());
            params.put("dRcmdtnx1", loDetail.getRcmdtnx1());
            params.put("cRcmdtnx1", loDetail.getRcmdtnc1());
            params.put("sRcmdtnx1", loDetail.getRcmdtns1());
            params.put("sApproved", loDetail.getApproved());
            params.put("dApproved", loDetail.getDapprovd());

            String lsResponse = WebClient.sendRequest(
                    poApis.getUrlPostCiApproval(),
                    params.toString(),
                    poHeaders.getHeaders());

            if (lsResponse == null) {
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            poDao.UpdateTransactionSendStat(TransNox);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean DownloadApplicationsForBHApproval(){
        try{
            JSONObject params = new JSONObject();
            params.put("sEmployID", poSession.getEmployeeID());
            String lsResponse = WebClient.sendRequest(poApis.getUrlDownloadBhPreview(),
                    params.toString(), poHeaders.getHeaders());
            if(lsResponse == null){
                message = "Sever no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

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
                loApp.setRcmdRcd2(AppConstants.DATE_MODIFIED());
                loApp.setRcmdtnx2(AppConstants.DATE_MODIFIED());
                loApp.setTranStat(loJson.getString("cTranStat"));
                poDao.SaveApplicationInfo(loApp);

                Thread.sleep(1000);
                if(DownloadForCIApplicationDetails(loJson.getString("sTransNox"))){
                    Log.d(TAG, "Credit app info successfully downloaded.");
                } else {
                    Log.d(TAG, "Failed to download credit app. " + message);
                }
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean PostBHApproval(String TransNox){
        try{
            ECreditOnlineApplicationCI loDetail = poDao.getApplication(TransNox);
            JSONObject params = new JSONObject();
            params.put("sTransNox", loDetail.getTransNox());
            params.put("dRcmdRcv2", loDetail.getRcmdRcd2());
            params.put("dRcmdtnx2", loDetail.getRcmdRcd2());
            params.put("cRcmdtnx2", loDetail.getRcmdtnc2());
            params.put("sRcmdtnx2", loDetail.getRcmdtns2());
            String lsResponse = WebClient.sendRequest(poApis.getUrlPostBhApproval(), params.toString(), poHeaders.getHeaders());
            if (lsResponse == null) {
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

            poDao.UpdateTransactionSendStat(TransNox);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean UploadCIRecommendations(){
        try{
            List<ECreditOnlineApplicationCI> loList = poDao.GetCIRecommendationsForUpload();

            if(loList == null){
                message = "No record to upload.";
                return false;
            }

            if(loList.size() == 0){
                message = "No record to upload.";
                return false;
            }

            for(int x = 0; x < loList.size(); x++){
                ECreditOnlineApplicationCI loDetail = loList.get(x);
                String TransNox = loDetail.getTransNox();

                JSONObject params = new JSONObject();
                params.put("sTransNox", loDetail.getTransNox());
                params.put("dRcmdRcv1", loDetail.getRcmdRcd1());
                params.put("dRcmdtnx1", loDetail.getRcmdtnx1());
                params.put("cRcmdtnx1", loDetail.getRcmdtnc1());
                params.put("sRcmdtnx1", loDetail.getRcmdtns1());
                params.put("sApproved", loDetail.getApproved());
                params.put("dApproved", loDetail.getDapprovd());

                String lsResponse = WebClient.sendRequest(
                        poApis.getUrlPostCiApproval(),
                        params.toString(),
                        poHeaders.getHeaders());

                if (lsResponse == null) {
                    message = SERVER_NO_RESPONSE;
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if (lsResult.equalsIgnoreCase("error")) {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = getErrorMessage(loError);
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                poDao.UpdateTransactionSendStat(TransNox);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean UploadBHRecommendations(){
        try{
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
