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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.List;

public class VMEmployeeApplications extends AndroidViewModel {
    private static final String TAG = VMEmployeeApplications.class.getSimpleName();

    private final Application instance;
    private final REmployeeLeave poLeave;
    private final REmployeeBusinessTrip poBuss;
    private final RBranch poBranch;

    private final MutableLiveData<Integer> pnLeave = new MutableLiveData<>();

    public interface OnDownloadApplicationListener {
        void OnDownload(String Title, String message);
        void OnDownloadSuccess();
        void OnDownloadFailed(String message);
    }

    public VMEmployeeApplications(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poLeave = new REmployeeLeave(instance);
        this.poBranch = new RBranch(instance);
        this.poBuss = new REmployeeBusinessTrip(instance);
        this.pnLeave.setValue(0);
    }

    public void setApplicationType(int fnVal){
        pnLeave.setValue(fnVal);
    }

    public LiveData<Integer> GetApplicationType(){
        return pnLeave;
    }

    public LiveData<List<EEmployeeLeave>> getApproveLeaveList(){
        return poLeave.getApproveLeaveList();
    }

    public LiveData<List<EEmployeeBusinessTrip>> GetApproveBusTrip(){
        return poBuss.GetApproveBusTrip();
    }

    public LiveData<List<EEmployeeLeave>> getEmployeeLeaveForApprovalList(){
        return poLeave.getEmployeeLeaveForApprovalList();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public void  DownloadLeaveForApproval(OnDownloadApplicationListener listener){
        new DownloadLeaveTask(instance, listener).execute();
    }

    private static class DownloadLeaveTask extends AsyncTask<Void, Void, String>{

        private final REmployeeLeave loLeave;
        private final ConnectionUtil loConn;
        private final HttpHeaders loHeaders;
        private final REmployeeBusinessTrip poBusTrip;
        private final WebApi poApi;
        private final OnDownloadApplicationListener mListener;
        private final AppConfigPreference loConfig;

        public DownloadLeaveTask(Application instance, OnDownloadApplicationListener listener){
            this.loLeave = new REmployeeLeave(instance);
            this.loConn = new ConnectionUtil(instance);
            this.loHeaders = HttpHeaders.getInstance(instance);
            this.poBusTrip = new REmployeeBusinessTrip(instance);
            this.loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
            this.mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mListener.OnDownload("PET Manager","Downloading leave applications. Please wait...");
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(Void... voids) {
            String response;
            JSONObject loResponse;
            String message = null;
            try{
                if (!loConn.isDeviceConnected()) {
                    response = AppConstants.NO_INTERNET();
                } else{
                    String lvResponse = WebClient.httpsPostJSon(poApi.getUrlGetLeaveApplication(loConfig.isBackUpServer()), new JSONObject().toString(), loHeaders.getHeaders());
//                    String lvResponse = AppConstants.LOCAL_EXCEPTION_ERROR("unknown error occurred");
                    if(lvResponse == null){
                        message = AppConstants.LOCAL_EXCEPTION_ERROR("Server no response while downloading leave applications");
                    } else {
                        loResponse = new JSONObject(lvResponse);
                        String lsResult = loResponse.getString("result");
                        if (lsResult.equalsIgnoreCase("success")) {
                            JSONArray jsonA = loResponse.getJSONArray("payload");
                            for (int x = 0; x < jsonA.length(); x++) {
                                JSONObject loJson = jsonA.getJSONObject(x);
                                EEmployeeLeave leave = new EEmployeeLeave();
                                leave.setTransNox(loJson.getString("sTransNox"));
                                leave.setTransact(loJson.getString("dTransact"));
                                leave.setEmployID(loJson.getString("xEmployee"));
                                leave.setEntryByx(loJson.getString("sEmployID"));
                                leave.setBranchNm(loJson.getString("sBranchNm"));
                                leave.setDeptName(loJson.getString("sDeptName"));
                                leave.setPositnNm(loJson.getString("sPositnNm"));
                                leave.setAppldFrx(loJson.getString("dAppldFrx"));
                                leave.setAppldTox(loJson.getString("dAppldTox"));
                                leave.setNoDaysxx(loJson.getString("nNoDaysxx"));
                                leave.setPurposex(loJson.getString("sPurposex"));
                                leave.setLeaveTyp(loJson.getString("cLeaveTyp"));
                                leave.setLveCredt(loJson.getString("nLveCredt"));
                                leave.setTranStat(loJson.getString("cTranStat"));
                                String lsTransNox = loJson.getString("sTransNox");
                                String lsTranStat = loJson.getString("cTranStat");
                                if (loLeave.getTransnoxIfExist(lsTransNox, lsTranStat).size() < 1) {
                                    loLeave.insertApplication(leave);
                                }
                            }
                        } else {
                            JSONObject loError = loResponse.getJSONObject("error");
                            message = "Fail to retrieve leave applications. Reason: " + loError.getString("message");
                        }
                    }

                    Thread.sleep(1000);

                    String obResponse = WebClient.httpsPostJSon(poApi.getUrlGetObApplication(loConfig.isBackUpServer()), new JSONObject().toString(), loHeaders.getHeaders());
                    if (obResponse == null) {
                        if(message != null){
                            message = message + "\n" + "Server no response while downloading business trip applications";
                        } else {
                            message = AppConstants.LOCAL_EXCEPTION_ERROR("Server no response while downloading business trip applications");
                        }
                    } else {
                        loResponse = new JSONObject(obResponse);
                        String result = loResponse.getString("result");
                        if (result.equalsIgnoreCase("success")) {
                            JSONArray jsonA = loResponse.getJSONArray("payload");
                            for(int x = 0; x < jsonA.length(); x++) {
                                JSONObject loJson = jsonA.getJSONObject(x);
                                if (poBusTrip.getOBIfExist(loJson.getString("sTransNox")).size() > 0) {
                                    Log.d(TAG, "OB application already exist.");
                                } else {
                                    EEmployeeBusinessTrip loOB = new EEmployeeBusinessTrip();
                                    loOB.setTransNox(loJson.getString("sTransNox"));
                                    loOB.setTransact(loJson.getString("dTransact"));
                                    loOB.setEmployee(loJson.getString("sEmployID"));
                                    loOB.setFullName(loJson.getString("sCompnyNm"));
                                    loOB.setBranchNm(loJson.getString("sBranchNm"));
                                    loOB.setDeptName(loJson.getString("sDeptName"));
                                    loOB.setDateFrom(loJson.getString("dDateFrom"));
                                    loOB.setDateThru(loJson.getString("dDateThru"));
                                    loOB.setRemarksx(loJson.getString("sRemarksx"));
                                    loOB.setTranStat(loJson.getString("cTranStat"));
                                    poBusTrip.insert(loOB);
                                }
                            }
                        } else {
                            JSONObject loError = loResponse.getJSONObject("error");
                            if(message != null){
                                message = message + "\n" + "Fail to retrieve business trip applications. Reason: " + loError.getString("message");
                            } else {
                                message = "Fail to retrieve business trip applications. Reason: " + loError.getString("message");
                            }
                        }
                    }

                    if(message == null){
                        response = AppConstants.APPROVAL_CODE_GENERATED("");
                    } else {
                        response = AppConstants.LOCAL_EXCEPTION_ERROR(message);
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


