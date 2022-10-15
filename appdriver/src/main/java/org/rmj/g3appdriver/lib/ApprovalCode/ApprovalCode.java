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

import org.json.JSONObject;
import org.rmj.appdriver.mob.lib.RequestApproval;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.dev.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

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
            loApp.setRemarks(foVal.getsRemarksx());

            String lsAppCode = loApp.Generate(
                    foVal.getsBranchCd(),
                    foVal.getdReqDatex(),
                    foVal.getsSystemCd(),
                    foVal.getsMiscInfo());

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
}
