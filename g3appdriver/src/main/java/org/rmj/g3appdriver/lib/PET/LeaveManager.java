package org.rmj.g3appdriver.lib.PET;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;

public class LeaveManager {
    private static final String TAG = LeaveManager.class.getSimpleName();

    private final Application instance;

    public interface OnActionCallback{
        void OnSuccess(String message);
        void OnFailed(String message);
    }

    public LeaveManager(Application application) {
        this.instance = application;
    }

    public void SaveLeaveApplication(EEmployeeLeave foLeave, OnActionCallback callback){
        try{
            if(foLeave.getDateFrom().trim().isEmpty()){
                callback.OnFailed("Please select starting date of leave");
            } else if(foLeave.getDateThru().trim().isEmpty()){
                callback.OnFailed("Please select ending date of leave");
            } else if(foLeave.getLeaveTyp().isEmpty()){
                callback.OnFailed("Please select type of leave");
            } else if(foLeave.getPurposex().trim().isEmpty()){
                callback.OnFailed("Please provide your purpose on remarks area");
            } else {
                REmployeeLeave loLeave = new REmployeeLeave(instance);
                SessionManager loSession = new SessionManager(instance);
                foLeave.setTransNox(loLeave.getNextLeaveCode());
                foLeave.setTransact(AppConstants.CURRENT_DATE);
                foLeave.setEmployID(loSession.getEmployeeID());
                foLeave.setBranchNm(loSession.getBranchCode());
                foLeave.setEntryByx(loSession.getEmployeeID());
                foLeave.setEntryDte(AppConstants.CURRENT_DATE);
                foLeave.setWithOPay("0");
                foLeave.setApproved("0");
                foLeave.setTranStat("0");
                loLeave.insertApplication(foLeave);
                callback.OnSuccess("Employee leave save successfully.");
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void PostLeaveApplication(String fsTransNo, OnActionCallback callback){
        try{
            REmployeeLeave loLeave = new REmployeeLeave(instance);
            if(loLeave.getLeaveForPosting(fsTransNo) == null){
                callback.OnFailed("No leave application to post.");
            } else {
                EEmployeeLeave loDetail = loLeave.getLeaveForPosting(fsTransNo);
                WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
                JSONObject params = new JSONObject();
                params.put("sTransNox", loDetail.getTransNox());
                params.put("dTransact", loDetail.getTransact());
                params.put("sEmployID", loDetail.getEmployID());
                params.put("dDateFrom", loDetail.getDateFrom());
                params.put("dDateThru", loDetail.getDateThru());
                params.put("nNoDaysxx", loDetail.getNoDaysxx());
                params.put("sPurposex", loDetail.getPurposex());
                params.put("cLeaveTyp", loDetail.getLeaveTyp());
                params.put("dAppldFrx", loDetail.getDateFrom());
                params.put("dAppldTox", loDetail.getDateThru());
                params.put("sEntryByx", loDetail.getEmployID());
                params.put("dEntryDte", loDetail.getEntryDte());
                params.put("nWithOPay", "0");
                params.put("nEqualHrs", loDetail.getEqualHrs());
                params.put("sApproved", "0");
                params.put("dApproved", "");
                params.put("dSendDate", new AppConstants().DATE_MODIFIED);
                params.put("cTranStat", "1");
                params.put("sModified", loDetail.getEmployID());

                String lsResponse = WebClient.sendRequest(loApi.getUrlSendLeaveApplication(),
                        params.toString(),
                        HttpHeaders.getInstance(instance).getHeaders());
                if(lsResponse == null){
                    callback.OnFailed("Unable to post leave application. Server no response.");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String result = loResponse.getString("result");
                    if(!result.equalsIgnoreCase("success")){
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = loError.getString("message");
                        callback.OnFailed(lsMessage);
                    } else {
                        loLeave.updateSendStatus(
                                new AppConstants().DATE_MODIFIED,
                                loDetail.getTransNox(),
                                loResponse.getString("sTransNox"));
                        Log.d("Employee Leave", "Leave info updated!");
                        callback.OnSuccess("Leave application posted successfully.");
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

}
