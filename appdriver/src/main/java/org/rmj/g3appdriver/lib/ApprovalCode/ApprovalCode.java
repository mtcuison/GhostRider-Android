/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.lib.ApprovalCode;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.mob.lib.RequestApproval;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.dev.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ESCA_Request;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.List;

public class ApprovalCode {
    private static final String TAG = ApprovalCode.class.getSimpleName();

    private final DApprovalCode poDao;

    private final EmployeeMaster poUser;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public ApprovalCode(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).ApprovalDao();
        this.poUser = new EmployeeMaster(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<String> GetApprovalCodeDescription(String fsVal){
        return poDao.getApprovalDesc(fsVal);
    }

    public String GenerateApprovalCode(AppCodeParams foVal){
        try{
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return null;
            }

            String lsPackage = poConfig.getPackageName();

            EEmployeeInfo loUser = poDao.GetUserInfo();

            if(loUser == null){
                message = "Invalid user record. Please re-login your account and try again.";
                return null;
            }

            RequestApproval loApp = new RequestApproval(lsPackage);
            loApp.setDeptIDxx(loUser.getDeptIDxx());
            loApp.setEmpLevID(loUser.getEmpLevID());
            loApp.setEmployID(loUser.getEmployID());
            loApp.setRemarks(foVal.getRemarks());

            String lsAppCode = loApp.Generate(
                    foVal.getBranchCd(),
                    foVal.getReqDatex(),
                    foVal.getSystemCd(),
                    foVal.getMiscInfo());

            if(lsAppCode.isEmpty()){
                message = loApp.getMessage();
                return null;
            }

            return lsAppCode;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public String GenerateApprovalCode(ManualLog foVal){
        try{
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return null;
            }

            EEmployeeInfo loUser = poDao.GetUserInfo();

            if(loUser == null){
                message = "Invalid user record. Please re-login your account and try again.";
            }

            String lsPackage = poConfig.getPackageName();

            RequestApproval loApp = new RequestApproval(lsPackage);
            loApp.setDeptIDxx(loUser.getDeptIDxx());
            loApp.setEmpLevID(loUser.getEmpLevID());
            loApp.setEmployID(loUser.getEmployID());
            loApp.setReason(foVal.getRemarks());

            String lsReqDate = foVal.getReqDatex();

            long lnVal = Long.valueOf(foVal.getMiscInfo(), 2);

            String lsAppCode = loApp.Generate(
                    foVal.getBranchCd(),
                    lsReqDate,
                    foVal.getSysCode(),
                    String.valueOf(lnVal));

            if(lsAppCode.isEmpty()){
                message = loApp.getMessage();
                return null;
            }

            return lsAppCode;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public boolean UploadApprovalCode(String fsVal){
        try {
            ECodeApproval loCode = poDao.GetCodeApproval(fsVal);

            if(loCode == null){
                message = "No approval code record found for uploading.";
                return false;
            }

            JSONObject param = new JSONObject();
            param.put("sTransNox", loCode.getTransNox());
            param.put("dTransact", loCode.getTransact());
            param.put("sSystemCD", loCode.getSystemCD());
            param.put("sReqstdBy", loCode.getReqstdBy());
            param.put("dReqstdxx", loCode.getReqstdxx());
            param.put("cIssuedBy", loCode.getIssuedBy());
            param.put("sMiscInfo", loCode.getMiscInfo());
            param.put("sRemarks1", loCode.getRemarks1());
            param.put("sRemarks2", loCode.getApprCode() == null ? "" : loCode.getRemarks2());
            param.put("sApprCode", loCode.getApprCode());
            param.put("sEntryByx", loCode.getEntryByx());
            param.put("sApprvByx", loCode.getApprvByx());
            param.put("sReasonxx", loCode.getReasonxx() == null ? "" : loCode.getReasonxx());
            param.put("sReqstdTo", loCode.getReqstdTo() == null ? "" : loCode.getReqstdTo());
            param.put("cTranStat", loCode.getTranStat());

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlSaveApproval(poConfig.isBackUpServer()),
                    param.toString(),
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

            String lsTransNo = loResponse.getString("sTransNox");
            poDao.UpdateUploaded(loCode.getTransNox(), lsTransNo);

            return true;

        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public CreditAppInfo GetCreditApp(String args, String args1, String args2){
        try{
            JSONObject params = new JSONObject();
            params.put("systemcd", args);
            params.put("branchcd", args1);
            params.put("transnox", args2);

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlLoadApplicationApproval(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return null;
            }

            return new CreditAppInfo(loResponse.getString("rqstinfo"),
                                loResponse.getString("reqstdxx"),
                                loResponse.getString("miscinfo"),
                                loResponse.getString("remarks1"));
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public String ApproveCreditApp(CreditApp.Application foVal){
        try{
            JSONObject params = new JSONObject();
            params.put("transnox", foVal.getTransNox());
            params.put("reasonxx", foVal.getReasonxx());
            params.put("approved", foVal.getApproved());

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlApplicationApprove(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return null;
            }

            String lsAppCode = loResponse.getString("apprcode");
            return lsAppCode;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public boolean UploadApprovalCode(){
        try{
            List<ECodeApproval> loApp = poDao.GetSystemApprovalForUploading();
            if(loApp == null){
                message = "No approval code record found.";
                return false;
            }

            if(loApp.size() == 0){
                message = "No approval code record found.";
                return false;
            }

            for(int x = 0; x < loApp.size(); x++){
                ECodeApproval loCode = loApp.get(x);

                JSONObject param = new JSONObject();
                param.put("sTransNox", loCode.getTransNox());
                param.put("dTransact", loCode.getTransact());
                param.put("sSystemCD", loCode.getSystemCD());
                param.put("sReqstdBy", loCode.getReqstdBy());
                param.put("dReqstdxx", loCode.getReqstdxx());
                param.put("cIssuedBy", loCode.getIssuedBy());
                param.put("sMiscInfo", loCode.getMiscInfo());
                param.put("sRemarks1", loCode.getRemarks1());
                param.put("sRemarks2", loCode.getApprCode() == null ? "" : loCode.getRemarks2());
                param.put("sApprCode", loCode.getApprCode());
                param.put("sEntryByx", loCode.getEntryByx());
                param.put("sApprvByx", loCode.getApprvByx());
                param.put("sReasonxx", loCode.getReasonxx() == null ? "" : loCode.getReasonxx());
                param.put("sReqstdTo", loCode.getReqstdTo() == null ? "" : loCode.getReqstdTo());
                param.put("cTranStat", loCode.getTranStat());

                String lsResponse = WebClient.httpsPostJSon(
                        poApi.getUrlSaveApproval(poConfig.isBackUpServer()),
                        param.toString(),
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

                String lsTransNo = loResponse.getString("sTransNox");
                poDao.UpdateUploaded(loCode.getTransNox(), lsTransNo);
                Log.d(TAG, "System code approval has been save to server.");
                Thread.sleep(1000);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean ImportSCARequest(){
        try{
            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("id", "All");

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlScaRequest(poConfig.isBackUpServer()),
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

            JSONArray laJson = loResponse.getJSONArray("detail");
            for (int x = 0; x < laJson.length(); x++) {
                JSONObject loJson = laJson.getJSONObject(x);
                ESCA_Request loDetail = poDao.GetApprovalCode(loJson.getString("sSCACodex"));

                if(loDetail == null){
                    if(loJson.getString("cRecdStat").equalsIgnoreCase("1")) {
                        ESCA_Request info = new ESCA_Request();
                        info.setSCACodex(loJson.getString("sSCACodex"));
                        info.setSCATitle(loJson.getString("sSCATitle"));
                        info.setSCADescx(loJson.getString("sSCADescx"));
                        info.setSCATypex(loJson.getString("cSCATypex"));
                        info.setAreaHead(loJson.getString("cAreaHead"));
                        info.setHCMDeptx(loJson.getString("cHCMDeptx"));
                        info.setCSSDeptx(loJson.getString("cCSSDeptx"));
                        info.setComplnce(loJson.getString("cComplnce"));
                        info.setMktgDept(loJson.getString("cMktgDept"));
                        info.setASMDeptx(loJson.getString("cASMDeptx"));
                        info.setTLMDeptx(loJson.getString("cTLMDeptx"));
                        info.setSCMDeptx(loJson.getString("cSCMDeptx"));
                        info.setRecdStat(loJson.getString("cRecdStat"));
                        info.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.SaveSCARequest(info);
                        Log.d(TAG, "SCA Request has been saved.");
                    }
                }
//                else {
//                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
//                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
//                    if (!ldDate1.equals(ldDate2)) {
//                        loDetail.setSCACodex(loJson.getString("sSCACodex"));
//                        loDetail.setSCATitle(loJson.getString("sSCATitle"));
//                        loDetail.setSCADescx(loJson.getString("sSCADescx"));
//                        loDetail.setSCATypex(loJson.getString("cSCATypex"));
//                        loDetail.setAreaHead(loJson.getString("cAreaHead"));
//                        loDetail.setHCMDeptx(loJson.getString("cHCMDeptx"));
//                        loDetail.setCSSDeptx(loJson.getString("cCSSDeptx"));
//                        loDetail.setComplnce(loJson.getString("cComplnce"));
//                        loDetail.setMktgDept(loJson.getString("cMktgDept"));
//                        loDetail.setASMDeptx(loJson.getString("cASMDeptx"));
//                        loDetail.setTLMDeptx(loJson.getString("cTLMDeptx"));
//                        loDetail.setSCMDeptx(loJson.getString("cSCMDeptx"));
//                        loDetail.setRecdStat(loJson.getString("cRecdStat"));
//                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
//                        poDao.SaveSCARequest(loDetail);
//                        Log.d(TAG, "SCA Request has been updated.");
//                    }
//                }
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public LiveData<List<ESCA_Request>> getAuthorizedFeatures(String fsVal){

        EEmployeeInfo loUser = poDao.GetUserInfo();

        String lsSqlQryxx = "SELECT *" +
                " FROM xxxSCA_Request" +
                " WHERE cSCATypex = " + SQLUtil.toSQL(fsVal)  +
                " AND cRecdStat = '1'" +
                " ORDER BY sSCATitle";

        String lsCondition = "";
        String lsEmpLvID = loUser.getEmpLevID();
        String lsDeptIDx = loUser.getDeptIDxx();
        String lsPostion = loUser.getPositnID();

        if (lsEmpLvID.equals("4")){
            lsCondition  = "cAreaHead = '1'";
        } else{
            switch (lsDeptIDx){
                case "021": //hcm
                    lsCondition = "cHCMDeptx = '1'"; break;
                case "022": //css
                    lsCondition = "cCSSDeptx = '1'"; break;
                case "034": //cm
                    lsCondition = "cComplnce = '1'"; break;
                case "025": //m&p
                    lsCondition = "cMktgDept = '1'"; break;
                case "027": //asm
                    lsCondition = "cASMDeptx = '1'"; break;
                case "035": //tele
                    lsCondition = "cTLMDeptx = '1'"; break;
                case "024": //scm
                    lsCondition = "cSCMDeptx = '1'"; break;
                case "026": //mis
                    break;
                case "015": //sales
                    if (lsPostion.equals("091")){
                        //field specialist
                        lsCondition = "sSCACodex = 'CA'";
                    } else {
                        lsCondition = "0=1";
                    }
                    break;
                default: lsCondition = "0=1";
            }
        }

        if (!lsCondition.isEmpty()) lsSqlQryxx = MiscUtil.addCondition(lsSqlQryxx, lsCondition);
        return poDao.getAuthorizedFeatures(new SimpleSQLiteQuery(lsSqlQryxx));
    }
}
