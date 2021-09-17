/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/9/21, 11:53 AM
 * project file last modified : 9/9/21, 11:51 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.List;

public class VMEmployeeApplications extends AndroidViewModel {
    private static final String TAG = VMEmployeeApplications.class.getSimpleName();

    private final Application instance;
    private final REmployeeLeave poLeave;
    private final RBranch poBranch;

    public interface OnDownloadLeaveListListener{
        void OnDownload(String message);
        void OnDownloadSuccess();
        void OnDownloadFailed(String message);
    }

    public VMEmployeeApplications(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poLeave = new REmployeeLeave(instance);
        this.poBranch = new RBranch(instance);
    }

    public LiveData<List<EEmployeeLeave>> getEmployeeLeaveForApprovalList(){
        return poLeave.getEmployeeLeaveForApprovalList();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public void DownloadLeaveForApproval(OnDownloadLeaveListListener listener){
        new DownloadLeaveTask(instance, listener).execute();
    }

    private static class DownloadLeaveTask extends AsyncTask<Void, Void, String>{

        private final REmployeeLeave loLeave;
        private final ConnectionUtil loConn;
        private final HttpHeaders loHeaders;
        private final OnDownloadLeaveListListener mListener;

        public DownloadLeaveTask(Application instance, OnDownloadLeaveListListener listener){
            this.loLeave = new REmployeeLeave(instance);
            this.loConn = new ConnectionUtil(instance);
            this.loHeaders = HttpHeaders.getInstance(instance);
            this.mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mListener.OnDownload("Downloading leave applications. Please wait...");
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(Void... voids) {
            String response;
            try{
                response = WebClient.httpsPostJSon(WebApi.URL_GET_LEAVE_APPLICATION, new JSONObject().toString(), loHeaders.getHeaders());
                JSONObject loResponse = new JSONObject(response);
                String lsResult = loResponse.getString("result");
                if (lsResult.equalsIgnoreCase("success")) {
                    JSONArray jsonA = loResponse.getJSONArray("payload");
                    for(int x = 0; x < jsonA.length(); x++) {
                        JSONObject loJson = jsonA.getJSONObject(x);
                        if (loLeave.getTransnoxIfExist(loJson.getString("sTransNox")).size() > 0) {
                            Log.d(TAG, "Leave application already exist.");
                        } else {
                            EEmployeeLeave leave = new EEmployeeLeave();
                            leave.setTransNox("sTransNox");
                            leave.setTransact("dTransact");
                            leave.setEmployID("xEmployee");
                            leave.setBranchNm("sBranchNm");
                            leave.setDeptName("sDeptName");
                            leave.setPositnNm("sPositnNm");
                            leave.setAppldFrx("dAppldFrx");
                            leave.setAppldTox("dAppldTox");
                            leave.setNoDaysxx("nNoDaysxx");
                            leave.setPurposex("sPurposex");
                            leave.setLeaveTyp("cLeaveTyp");
                            leave.setLveCredt("nLveCredt");
                            leave.setTranStat("cTranStat");
                            loLeave.insertApplication(leave);
                        }
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
                response = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try{
                JSONObject loJson = new JSONObject(response);
                if(loJson.getString("result").equalsIgnoreCase("success")){
                    mListener.OnDownloadSuccess();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    mListener.OnDownloadFailed(loError.getString("message"));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}