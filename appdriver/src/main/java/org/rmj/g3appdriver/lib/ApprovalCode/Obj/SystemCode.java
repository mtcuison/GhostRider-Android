package org.rmj.g3appdriver.lib.ApprovalCode.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.appdriver.mob.lib.RequestApproval;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.ApprovalCode.SCA;
import org.rmj.g3appdriver.lib.ApprovalCode.model.AppCodeParams;
import org.rmj.g3appdriver.lib.ApprovalCode.model.CreditAppInfo;
import org.rmj.g3appdriver.dev.Api.WebApi;

import java.util.List;

public class SystemCode implements SCA {

    private final DApprovalCode poDao;

    private final RBranch poBranch;
    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public SystemCode(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).ApprovalDao();
        this.poBranch = new RBranch(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    @Override
    public LiveData<List<EBranchInfo>> GetBranchList() {
        return poBranch.getAllBranchInfo();
    }

    @Override
    public LiveData<String> GetApprovalCodeDescription(String args) {
        return poDao.getApprovalDesc(args);
    }

    @Override
    public String GenerateCode(Object foArgs) {
        try{
            AppCodeParams foVal = (AppCodeParams) foArgs;

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
            loApp.setEmpLevID(String.valueOf(loUser.getEmpLevID()));
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

    @Override
    public CreditAppInfo LoadApplication(String args, String args1, String args2) {
        return null;
    }

    @Override
    public boolean Upload(String args) {
        try {
            ECodeApproval loCode = poDao.GetCodeApproval(args);

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

            String lsResponse = WebClient.sendRequest(
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

    @Override
    public String getMessage() {
        return message;
    }
}
