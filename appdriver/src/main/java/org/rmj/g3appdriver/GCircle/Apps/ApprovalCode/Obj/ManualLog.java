package org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.Obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.appdriver.mob.lib.RequestApproval;
import org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.model.SCA;
import org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.pojo.CreditAppInfo;
import org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.pojo.ManualTimeLog;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECodeApproval;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Etc.Branch;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;

import java.util.List;

public class ManualLog implements SCA {

    private final DApprovalCode poDao;

    private final Branch poBranch;
    private final AppConfigPreference poConfig;
    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public ManualLog(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).ApprovalDao();
        this.poBranch = new Branch(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new GCircleApi(instance);
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
            ManualTimeLog foVal = (ManualTimeLog) foArgs;

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
            loApp.setEmpLevID(String.valueOf(loUser.getEmpLevID()));
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
            message = getLocalMessage(e);
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
                    poApi.getUrlSaveApproval(),
                    param.toString(),
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

            String lsTransNo = loResponse.getString("sTransNox");
            poDao.UpdateUploaded(loCode.getTransNox(), lsTransNo);

            return true;

        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
