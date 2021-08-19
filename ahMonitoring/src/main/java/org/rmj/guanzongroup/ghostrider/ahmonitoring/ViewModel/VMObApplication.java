/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.OBApplication;

import java.util.List;

public class VMObApplication extends AndroidViewModel {

    public static final String TAG = VMObApplication.class.getSimpleName();
    private final Application instance;
    private final RBranch pobranch;
    private final REmployee poUser;
    private final REmployeeBusinessTrip poOBLeave;
    private final LiveData<String[]> paBranchNm;
    public VMObApplication(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.pobranch = new RBranch(instance);
        this.poUser = new REmployee(instance);
        paBranchNm = pobranch.getAllMcBranchNames();
        this.poOBLeave = new REmployeeBusinessTrip(instance);
    }
    public interface OnSubmitOBLeaveListener{
        void onSuccess();
        void onFailed(String message);
    }
    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }
    public LiveData<String[]> getAllBranchNames(){
        return paBranchNm;
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return pobranch.getAllMcBranchInfo();
    }
    public void saveObLeave(OBApplication infoModel, OnSubmitOBLeaveListener callback){
        new PostObLeaveTask(infoModel, instance, callback).execute();
    }

    private static class PostObLeaveTask extends AsyncTask<Void, Void, String> {

        private final OBApplication infoModel;
        private final OnSubmitOBLeaveListener callback;

        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final Telephony poDevID;
        private final SessionManager poUser;
        private final AppConfigPreference poConfig;
        private final REmployeeBusinessTrip poOBLeave;
        public PostObLeaveTask(OBApplication infoModel, Application instance, OnSubmitOBLeaveListener callback) {

            this.infoModel = infoModel;
            this.callback = callback;
            this.poOBLeave = new REmployeeBusinessTrip(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poDevID = new Telephony(instance);
            this.poUser = new SessionManager(instance);
            this.poConfig = AppConfigPreference.getInstance(instance);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            String lsResponse = "";
            try{
                if(!infoModel.isOBLeaveValid()){
                    lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR(infoModel.getMessage());
                } else {
                    EEmployeeBusinessTrip detail = new EEmployeeBusinessTrip();
                    detail.setTransNox(poOBLeave.getOBLeaveNextCode());
                    detail.setTransact(new AppConstants().DATE_MODIFIED);
                    detail.setEmployee(poUser.getEmployeeID());
                    detail.setBranchNm(infoModel.getsBranchNm());
                    detail.setDeptName(infoModel.getsDeptName());
                    detail.setPositnNm(infoModel.getsPositnNm());
                    detail.setDateFrom(infoModel.getDateFrom());
                    detail.setDateThru(infoModel.getDateThru());
                    detail.setRemarksx(infoModel.getRemarks());
                    detail.setApproved("0");
                    detail.setDapprove(null);
                    detail.setAppldFrx(null);
                    detail.setAppldTox(null);
                    detail.setTranStat("0");
                    detail.setSendStat("0");
                    poOBLeave.insertNewOBLeave(detail);

                    JSONObject loJson = new JSONObject();
                    loJson.put("sTransNox", poOBLeave.getOBLeaveNextCode());
                    loJson.put("dTransact", AppConstants.CURRENT_DATE);
                    loJson.put("sEmployID", poUser.getEmployeeID());
                    loJson.put("dDateFrom", infoModel.getDateFrom());
                    loJson.put("dDateThru", infoModel.getDateThru());
                    loJson.put("nNoDaysxx", infoModel.getNoOfDays());
                    loJson.put("sDestinat", infoModel.getDestination());
                    loJson.put("sRemarks", infoModel.getRemarks());
                    loJson.put("sApproved", "");
                    loJson.put("dApproved", "");
                    loJson.put("dAppldFrx", "");
                    loJson.put("dAppldTox", "");
                    loJson.put("cTranStat", "0");
                    loJson.put("sModified", poUser.getUserID());
                    loJson.put("dModified", AppConstants.CURRENT_DATE);

                    if(poConn.isDeviceConnected()) {
                        lsResponse = WebClient.sendRequest(WebApi.URL_SEND_OB_APPLICATION, loJson.toString(), poHeaders.getHeaders());
                        JSONObject jsonResponse = new JSONObject(lsResponse);
                        String lsResult = jsonResponse.getString("result");
                        Log.e(TAG, lsResponse);
                    } else {
                        lsResponse = AppConstants.SERVER_NO_RESPONSE();
                        Log.e(TAG, "else " + lsResponse);
                    }
                }
            } catch (NullPointerException e){
                e.printStackTrace();
                lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }catch (Exception e){
                e.printStackTrace();
                lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.onSuccess();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.onFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.onFailed(e.getMessage());
            }
        }
    }
}