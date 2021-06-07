/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.approvalCode
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.approvalcode.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.appdriver.mob.lib.RequestApproval;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.GRider.Database.Repositories.RApprovalCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.approvalcode.Etc.ViewModelCallback;
import org.rmj.guanzongroup.ghostrider.approvalcode.Model.ManualLog;

import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_SAVE_APPROVAL;

public class VMManualLog extends AndroidViewModel {
    public static final String TAG = VMManualLog.class.getSimpleName();
    private final RBranch poBranchR;
    private final RApprovalCode poAppCdeR;
    private final SessionManager poSession;
    private final ConnectionUtil poConnect;
    private final HttpHeaders poHeaders;
    private final String psPackage;
    private final MutableLiveData<ECodeApproval> mCodeApproval = new MutableLiveData<>();

    public VMManualLog(@NonNull Application application) {
        super(application);
        this.poBranchR = new RBranch(application);
        this.poAppCdeR = new RApprovalCode(application);
        this.poSession = new SessionManager(application);
        this.poConnect = new ConnectionUtil(application);
        this.poHeaders = HttpHeaders.getInstance(application);
        this.psPackage = application.getPackageName();
    }

    public LiveData<String[]> getBranchNames(){
        return poBranchR.getAllMcBranchNames();
    }

    public LiveData<List<EBranchInfo>> getBranchInfoList(){
        return poBranchR.getAllMcBranchInfo();
    }

    public void creatApprovalCode(ManualLog model, CodeApprovalCreatedListener listener){
        new CreateCodeTask(poSession, psPackage, listener).execute(model);
    }

    public LiveData<ECodeApproval> getCodeApprovalInfo(){
        return poAppCdeR.getCodeApprovalInfo();
    }

    public void setCodeApprovalInfo(ECodeApproval codeApprovalInfo){
        this.mCodeApproval.setValue(codeApprovalInfo);
    }

    public void UploadApprovalCode(ViewModelCallback foCallBck){
        new SubmitApprovalInfoTask(poConnect, poAppCdeR, poHeaders, foCallBck).execute(mCodeApproval.getValue());
    }

    private static class CreateCodeTask extends AsyncTask<ManualLog, Void, String>{
        private final SessionManager loSession;
        private final CodeApprovalCreatedListener listener;
        private final String lsPackage;

        public CreateCodeTask(SessionManager foSession,
                              String fsPackage,
                              CodeApprovalCreatedListener listener) {
            this.loSession = foSession;
            this.lsPackage = fsPackage;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(ManualLog... manualLogs) {
            String lsResponse = "";
            try{
                if(manualLogs[0].isDataValid()) {
                    RequestApproval loApprovl = new RequestApproval(lsPackage);
                    loApprovl.setDeptIDxx(loSession.getDeptID());
                    loApprovl.setEmpLevID(loSession.getEmployeeID());
                    loApprovl.setEmployID(loSession.getClientId());
                    loApprovl.setReason(manualLogs[0].getRemarks());

                    String lsReqDate = manualLogs[0].getReqDatex();

                    long lnVal = Long.valueOf(manualLogs[0].getMiscInfo(), 2);

                    String lsAppCode = loApprovl.Generate(manualLogs[0].getBranchCd(),
                            lsReqDate,
                            manualLogs[0].getSysCode(),
                            String.valueOf(lnVal));

                    if (lsAppCode.isEmpty()) {
                        lsResponse = AppConstants.APPROVAL_CODE_EMPTY(loApprovl.getMessage());
                    } else {
                        lsResponse = AppConstants.APPROVAL_CODE_GENERATED(lsAppCode);
                    }
                } else {
                    lsResponse = AppConstants.APPROVAL_CODE_EMPTY(manualLogs[0].getMessage());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return lsResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loResponse = new JSONObject(s);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    listener.OnCreate(loResponse.getString("code"));
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    listener.OnCreateFailed(lsMessage);
                    Log.e(TAG, s);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(false);
        }
    }

    private static class SubmitApprovalInfoTask extends AsyncTask<ECodeApproval, Void, String>{
        private final ConnectionUtil loConnectx;
        private final RApprovalCode loApprovl;
        private final ViewModelCallback loCallbck;
        private final HttpHeaders loheaders;

        public SubmitApprovalInfoTask(ConnectionUtil foConnectx, RApprovalCode foApprovl, HttpHeaders foHeades, ViewModelCallback foCallbck){
            this.loConnectx = foConnectx;
            this.loApprovl = foApprovl;
            this.loCallbck = foCallbck;
            this.loheaders = foHeades;
        }

        @Override
        protected void onPreExecute() {
            loCallbck.OnLoadData("Approval Code", "Uploading approval code. Please wait...");
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(ECodeApproval... eCodeApprovals) {
            String lsReponse = "";
            try{
                if(loConnectx.isDeviceConnected()) {
                    final JSONObject param = new JSONObject();
                    param.put("sTransNox", eCodeApprovals[0].getTransNox());
                    param.put("dTransact", eCodeApprovals[0].getTransact());
                    param.put("sSystemCD", eCodeApprovals[0].getSystemCD());
                    param.put("sReqstdBy", eCodeApprovals[0].getReqstdBy());
                    param.put("dReqstdxx", eCodeApprovals[0].getReqstdxx());
                    param.put("cIssuedBy", eCodeApprovals[0].getIssuedBy());
                    param.put("sMiscInfo", eCodeApprovals[0].getMiscInfo());
                    param.put("sRemarks1", eCodeApprovals[0].getRemarks1());
                    param.put("sRemarks2", eCodeApprovals[0].getRemarks2());
                    param.put("sApprCode", eCodeApprovals[0].getApprCode());
                    param.put("sEntryByx", eCodeApprovals[0].getEntryByx());
                    param.put("sApprvByx", eCodeApprovals[0].getApprvByx());
                    param.put("sReasonxx", eCodeApprovals[0].getReasonxx());
                    param.put("sReqstdTo", eCodeApprovals[0].getReqstdTo());
                    param.put("cTranStat", eCodeApprovals[0].getTranStat());

                    lsReponse = WebClient.httpsPostJSon(URL_SAVE_APPROVAL, param.toString(), loheaders.getHeaders());
                    JSONObject loJson = new JSONObject(lsReponse);
                    Log.e(TAG, loJson.getString("result"));
                    String lsResult = loJson.getString("result");
                    if(lsResult.equalsIgnoreCase("success")){
                        String lsTransNo = loJson.getString("TransNox");
                        eCodeApprovals[0].setTransNox(lsTransNo);
                        eCodeApprovals[0].setSendxxxx("1");
                        loApprovl.update(eCodeApprovals[0]);
                    }
                } else {
                    lsReponse = AppConstants.NO_INTERNET();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return lsReponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                String lsResultx = loJson.getString("result");
                if (lsResultx.equalsIgnoreCase("success")) {
                    loCallbck.OnSuccessResult("");
                } else {
                    JSONObject loErrorxx = loJson.getJSONObject("error");
                    String lsMessage = loErrorxx.getString("message");
                    loCallbck.OnFailedResult(lsMessage);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public interface CodeApprovalCreatedListener{
        void OnCreate(String args);
        void OnCreateFailed(String message);
    }
}