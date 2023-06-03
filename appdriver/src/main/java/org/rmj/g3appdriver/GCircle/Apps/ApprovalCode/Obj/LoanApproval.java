package org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.Obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.model.SCA;
import org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.pojo.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.pojo.CreditAppInfo;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECodeApproval;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Etc.Branch;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;

import java.util.List;

public class LoanApproval implements SCA {

    private final DApprovalCode poDao;

    private final Branch poBranch;
    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public LoanApproval(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).ApprovalDao();
        this.poBranch = new Branch(instance);
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
            CreditApp.Application foVal = (CreditApp.Application) foArgs;

            JSONObject params = new JSONObject();
            params.put("transnox", foVal.getTransNox());
            params.put("reasonxx", foVal.getReasonxx());
            params.put("approved", foVal.getApproved());

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlApplicationApprove(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            String lsAppCode = loResponse.getString("apprcode");
            return lsAppCode;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
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

    @Override
    public CreditAppInfo LoadApplication(String args, String args1, String args2) {
        try{
            JSONObject params = new JSONObject();
            params.put("systemcd", args);
            params.put("branchcd", args1);
            params.put("transnox", args2);

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlLoadApplicationApproval(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            return new CreditAppInfo(loResponse.getString("rqstinfo"),
                    loResponse.getString("reqstdxx"),
                    loResponse.getString("miscinfo"),
                    loResponse.getString("remarks1"));
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }
}
