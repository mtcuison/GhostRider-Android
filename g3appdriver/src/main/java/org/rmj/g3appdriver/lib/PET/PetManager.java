package org.rmj.g3appdriver.lib.PET;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.List;

public class PetManager {
    private static final String TAG = PetManager.class.getSimpleName();

    private final Application instance;

    public interface OnActionCallback{
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public PetManager(Application application) {
        this.instance = application;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SaveLeaveApplication(EEmployeeLeave foLeave, OnActionCallback callback){
        try{
            if(foLeave.getDateFrom() == null ||
                    foLeave.getDateThru().trim().isEmpty()){
                callback.OnFailed("Please select starting date of leave");
            } else if(foLeave.getDateFrom() == null ||
                    foLeave.getDateThru().trim().isEmpty()){
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
                callback.OnSuccess(foLeave.getTransNox());
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
                params.put("sEmployID", loDetail.getEmployID());
                params.put("dDateFrom", loDetail.getDateFrom());
                params.put("dDateThru", loDetail.getDateThru());
                params.put("nNoDaysxx", loDetail.getNoDaysxx());
                params.put("nEqualHrs", loDetail.getEqualHrs());
                params.put("sPurposex", loDetail.getPurposex());
                params.put("cLeaveTyp", loDetail.getLeaveTyp());

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

    public void SaveLeaveApproval(String fsTransNo, String fsTranStat, OnActionCallback callback){
        try{
            REmployeeLeave loLeave = new REmployeeLeave(instance);
            loLeave.updateLeaveApproval(fsTranStat, fsTransNo, new AppConstants().DATE_MODIFIED);
            callback.OnSuccess("Leave Approval has been save!");
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void PostLeaveApproval(String fsTransno, OnActionCallback callback){
        try{
            REmployeeLeave loLeave = new REmployeeLeave(instance);
            if(loLeave.getLeaveForApproval(fsTransno) == null){
                callback.OnFailed("No leave application found.");
            } else {
                EEmployeeLeave loDetail = loLeave.getLeaveForApproval(fsTransno);
                JSONObject loJson = new JSONObject();
                loJson.put("sTransNox", loDetail.getTransNox());
                loJson.put("dTransact", AppConstants.CURRENT_DATE);
                loJson.put("dAppldFrx", loDetail.getAppldFrx());
                loJson.put("dAppldTox", loDetail.getAppldTox());
                loJson.put("cTranStat", loDetail.getTranStat());
                loJson.put("nWithPayx", loDetail.getWithPayx());
                loJson.put("nWithOPay", loDetail.getWithOPay());
                loJson.put("sApproved", loDetail.getApproved());
                loJson.put("dApproved", loDetail.getDApproved());

                WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());

                String lsResponse = WebClient.sendRequest(loApi.getUrlConfirmLeaveApplication(),
                        loJson.toString(), HttpHeaders.getInstance(instance).getHeaders());
                if (lsResponse == null) {
                    callback.OnFailed("Unable to approve.");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String lsResult = loResponse.getString("result");
                    if (!lsResult.equalsIgnoreCase("success")) {
                        String lsTransNo = loResponse.getString("sTransNox");
                        loLeave.updateSendStatus(new AppConstants().DATE_MODIFIED, loDetail.getTransNox(), lsTransNo);
                        callback.OnSuccess("Leave application approval posted successfully");
                    } else {
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = loError.getString("message");
                        callback.OnFailed(lsMessage);
                    }
                    Log.e(TAG, lsResponse);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void SaveOBApproval(String fsTransno, String fsTranStat, OnActionCallback callback){
        try{
            REmployeeBusinessTrip poBusTrip = new REmployeeBusinessTrip(instance);
            poBusTrip.updateOBApproval(fsTranStat, fsTransno, new AppConstants().DATE_MODIFIED);
            callback.OnSuccess("OB Approval has been save!");
        }catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void PostOBApproval(String fsTransNo, OnActionCallback callback){
        try {
            REmployeeBusinessTrip loBusTrip = new REmployeeBusinessTrip(instance);
            if(loBusTrip.getBusinessTripForPosting(fsTransNo) == null){
                callback.OnFailed("Business Trip info not found.");
            } else {
                WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
                EEmployeeBusinessTrip loDetail = loBusTrip.getBusinessTripForPosting(fsTransNo);

                JSONObject param = new JSONObject();
                param.put("sTransNox", loDetail.getTransNox());
                param.put("dAppldFrx", loDetail.getAppldFrx());
                param.put("dAppldTox", loDetail.getAppldTox());
                param.put("sApproved", loDetail.getApproved());
                param.put("dApproved", loDetail.getDapprove());
                param.put("cTranStat", loDetail.getTranStat());

                String lsResponse = WebClient.sendRequest(loApi.getUrlConfirmObApplication(), param.toString(),
                        HttpHeaders.getInstance(instance).getHeaders());
                if (lsResponse == null) {
                    callback.OnFailed("Unable to approve.");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String lsResult = loResponse.getString("result");
                    if (!lsResult.equalsIgnoreCase("success")) {
                        loBusTrip.updateOBSentStatus(loDetail.getTransNox());
                        callback.OnSuccess("Leave application approval posted successfully");
                    } else {
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = loError.getString("message");
                        callback.OnFailed(lsMessage);
                    }
                    Log.e(TAG, lsResponse);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void UploadBackGround(OnActionCallback callback){
        try{
            REmployeeBusinessTrip loBusTrip = new REmployeeBusinessTrip(instance);
            WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());

            List<EEmployeeBusinessTrip> loObList = loBusTrip.getOBListForUpload();
            if(loObList == null){
                callback.OnFailed("Business Trip info not found.");
            } else if(loObList.size() == 0) {
                callback.OnFailed("Business Trip info not found.");
            } else {
                for(int x = 0; x < loObList.size(); x++){
                    EEmployeeBusinessTrip loDetail = loObList.get(x);

                    JSONObject param = new JSONObject();
                    param.put("sTransNox", loDetail.getTransNox());
                    param.put("dAppldFrx", loDetail.getAppldFrx());
                    param.put("dAppldTox", loDetail.getAppldTox());
                    param.put("sApproved", loDetail.getApproved());
                    param.put("dApproved", loDetail.getDapprove());
                    param.put("cTranStat", loDetail.getTranStat());

                    String lsResponse = WebClient.sendRequest(loApi.getUrlConfirmObApplication(), param.toString(),
                            HttpHeaders.getInstance(instance).getHeaders());
                    if (lsResponse == null) {
                        callback.OnFailed("Unable to approve.");
                    } else {
                        JSONObject loResponse = new JSONObject(lsResponse);
                        String lsResult = loResponse.getString("result");
                        if (!lsResult.equalsIgnoreCase("success")) {
                            loBusTrip.updateOBSentStatus(loDetail.getTransNox());
                        } else {
                            JSONObject loError = loResponse.getJSONObject("error");
                            String lsMessage = loError.getString("message");
                        }
                    }

                    Thread.sleep(1000);
                    callback.OnSuccess("");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }
}
