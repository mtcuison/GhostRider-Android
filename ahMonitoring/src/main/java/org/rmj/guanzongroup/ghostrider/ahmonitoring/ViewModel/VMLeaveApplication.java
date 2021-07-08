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
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;
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

        public SaveLeaveApplication(Application application, LeaveApplicationCallback callback) {
            this.instance = application;
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poLeave = new REmployeeLeave(instance);
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
                JSONObject param = new JSONObject();
                param.put("dDateFrom", loLeave.getDateFromx());
                param.put("dDateThru", loLeave.getDateThrux());
                param.put("nNoDaysxx", loLeave.getNoOfDaysx());
                param.put("sPurposex", loLeave.getRemarksxx());
                param.put("cLeaveTyp", loLeave.getLeaveType());
                param.put("dAppldFrx", new AppConstants().CURRENT_DATE);

                EEmployeeLeave loApp = new EEmployeeLeave();
                loApp.setTransNox(poLeave.getNextLeaveCode());
                loApp.setDateFrom(loLeave.getDateFromx());
                loApp.setDateThru(loLeave.getDateThrux());
                loApp.setNoDaysxx(String.valueOf(loLeave.getNoOfDaysx()));
                loApp.setPurposex(loLeave.getRemarksxx());
                loApp.setLeaveTyp(loLeave.getLeaveType());
                poLeave.insertApplication(loApp);

                if(poConn.isDeviceConnected()){
                    lsResult = WebClient.httpsPostJSon("", param.toString(), poHeaders.getHeaders());

                    JSONObject loResult = new JSONObject(lsResult);
                    lsResult = loResult.getString("result");
                } else {
                    lsResult = AppConstants.NO_INTERNET();
                }
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