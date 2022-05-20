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
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.LeaveApplication;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.LEAVE_TYPE;

public class VMLeaveApplication extends AndroidViewModel {

    private final Application instance;
    private final RBranch pobranch;
    private final REmployee poUser;

    public VMLeaveApplication(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.pobranch = new RBranch(instance);
        this.poUser = new REmployee(instance);
    }

    public interface LeaveApplicationCallback {
        void OnSave(String Title, String message);
        void OnSuccess();
        void OnFailed(String message);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }

    public LiveData<ArrayAdapter<String>> getLeaveTypeList(){
        MutableLiveData<ArrayAdapter<String>> loList = new MutableLiveData<>();
        loList.setValue(new ArrayAdapter<>(instance, android.R.layout.simple_dropdown_item_1line, LEAVE_TYPE));
        return loList;
    }

    public void SaveApplication(LeaveApplication application, LeaveApplicationCallback callback){
        if(application.isDataValid()) {
            new SaveLeaveApplication(instance, callback).execute(application);
        } else {
            callback.OnFailed(application.getMessage());
        }
    }

    private static class SaveLeaveApplication extends AsyncTask<LeaveApplication, Void, String>{
        private final Application instance;
        private final LeaveApplicationCallback callback;

        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final REmployeeLeave poLeave;
        private final SessionManager poSession;
        private final WebApi poApi;

        public SaveLeaveApplication(Application application, LeaveApplicationCallback callback) {
            this.instance = application;
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poLeave = new REmployeeLeave(instance);
            this.poSession = new SessionManager(instance);
            AppConfigPreference loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnSave("Pet Manager", "Saving your leave application. Please wait...");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(LeaveApplication... leaveApplications) {
            String lsResult;
            LeaveApplication loLeave = leaveApplications[0];
            try {
                EEmployeeLeave loApp = new EEmployeeLeave();
                loApp.setTransNox(poLeave.getNextLeaveCode());
                loApp.setTransact(AppConstants.CURRENT_DATE);
                loApp.setEmployID(poSession.getEmployeeID());
                loApp.setEmployID(loLeave.getEmploName());
                loApp.setBranchNm(loLeave.getBranchNme());
                loApp.setAppldFrx(loLeave.getDateFromx());
                loApp.setAppldTox(loLeave.getDateThrux());
                loApp.setNoDaysxx(String.valueOf(loLeave.getNoOfDaysx()));
                loApp.setPurposex(loLeave.getRemarksxx());
                loApp.setEqualHrs(String.valueOf(loLeave.getNoOfHours()));
                loApp.setLeaveTyp(loLeave.getLeaveType());
                loApp.setEntryByx(poSession.getEmployeeID());
                loApp.setEntryDte(AppConstants.CURRENT_DATE);
                loApp.setWithOPay("0");
                loApp.setApproved("0");
                loApp.setTranStat("0");
                poLeave.insertApplication(loApp);

                JSONObject param = new JSONObject();
                param.put("sTransNox", poLeave.getNextLeaveCode());
                param.put("dTransact", AppConstants.CURRENT_DATE);
                param.put("sEmployID", poSession.getEmployeeID());
                param.put("dDateFrom", loLeave.getDateFromx());
                param.put("dDateThru", loLeave.getDateThrux());
                param.put("nNoDaysxx", loLeave.getNoOfDaysx());
                param.put("sPurposex", loLeave.getRemarksxx());
                param.put("cLeaveTyp", loLeave.getLeaveType());
                param.put("dAppldFrx", loLeave.getDateFromx());
                param.put("dAppldTox", loLeave.getDateThrux());
                param.put("sEntryByx", poSession.getEmployeeID());
                param.put("dEntryDte", AppConstants.CURRENT_DATE);
                param.put("nWithOPay", "0");
                param.put("nEqualHrs", loApp.getEqualHrs());
                param.put("sApproved", "0");
                param.put("dApproved", "");
                param.put("dSendDate", AppConstants.CURRENT_DATE);
                param.put("cTranStat", "1");
                param.put("sModified", poSession.getEmployeeID());

                if(poConn.isDeviceConnected()){
                    lsResult = WebClient.sendRequest(poApi.getUrlSendLeaveApplication(), param.toString(), poHeaders.getHeaders());
                    if(lsResult == null){
                        lsResult = AppConstants.SERVER_NO_RESPONSE();
                    } else {
                        JSONObject loResult = new JSONObject(lsResult);
                        String result = loResult.getString("result");
                        if(result.equalsIgnoreCase("success")){
                            poLeave.updateSendStatus(
                                    new AppConstants().DATE_MODIFIED,
                                    loApp.getTransNox(),
                                    loResult.getString("sTransNox"));
                            Log.d("Employee Leave", "Leave info updated!");
                        }
                    }
                } else {
                    lsResult = AppConstants.NO_INTERNET();
                }
                Log.e("VMLeaveApplication", lsResult);
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                if(loJson.getString("result").equalsIgnoreCase("success")){
                    callback.OnSuccess();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    callback.OnFailed(loError.getString("message"));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(true);
        }
    }
}