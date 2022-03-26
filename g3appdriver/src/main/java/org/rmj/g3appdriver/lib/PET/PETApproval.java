package org.rmj.g3appdriver.lib.PET;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;

public class PETApproval {
    private static final String TAG = PETApproval.class.getSimpleName();

    private final Application instance;

    public interface OnActionCallback{
        void OnSuccess(String message);
        void OnFailed(String message);
    }

    public PETApproval(Application application) {
        this.instance = application;
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
                    JSONObject jsonResponse = new JSONObject(lsResponse);
                    String lsResult = jsonResponse.getString("result");
                    if (!lsResult.equalsIgnoreCase("success")) {
                        loLeave.updateLeaveApproval(infoModel.getTranStat(), infoModel.getTransNox(), new AppConstants().DATE_MODIFIED);
                    } else {

                    }
                    Log.e(TAG, lsResponse);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }
}
