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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.appdriver.mob.lib.RequestApproval;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.dev.Database.Repositories.RApprovalCode;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;
import org.rmj.guanzongroup.ghostrider.approvalcode.Model.ApprovalEntry;

import java.util.List;

public class VMApprovalEntry extends AndroidViewModel {
    private static final String TAG = VMApprovalEntry.class.getSimpleName();
    private final Application instance;
    private final RBranch poBranchx;
    private final RApprovalCode poAppCdeR;
    private final SessionManager poSession;
    private final ConnectionUtil poConnect;
    private final HttpHeaders poHeaders;
    private final String psPackage;
    private final MutableLiveData<ECodeApproval> mCodeApproval = new MutableLiveData<>();

    public VMApprovalEntry(@NonNull Application application) {
        super(application);
        this.instance = application;
        poBranchx = new RBranch(application);
        this.poAppCdeR = new RApprovalCode(application);
        this.poSession = new SessionManager(application);
        this.poConnect = new ConnectionUtil(application);
        this.poHeaders = HttpHeaders.getInstance(application);
        this.psPackage = application.getPackageName();
    }

    public LiveData<String> getApprovalDesc(String AppCode){
        return poAppCdeR.getApprovalDesc(AppCode);
    }

    public LiveData<String[]> getBranchNameList(){
        return poBranchx.getAllBranchNames();
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return poBranchx.getAllBranchInfo();
    }

    public void CreateApprovalCode(ApprovalEntry foEntry, CodeApprovalCreatedListener callback){
        new CreateCodeTask(instance, psPackage, callback).execute(foEntry);
    }

    private static class CreateCodeTask extends AsyncTask<ApprovalEntry, Void, String>{
        private final SessionManager loSession;
        private final String lsPackage;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final RApprovalCode poApproval;
        private final WebApi poApi;
        private final AppConfigPreference loConfig;
        private final CodeApprovalCreatedListener listener;

        public CreateCodeTask(Application instance, String lsPackage, CodeApprovalCreatedListener listener) {
            this.loSession = new SessionManager(instance);
            this.lsPackage = lsPackage;
            this.listener = listener;
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poApproval = new RApprovalCode(instance);
            this.loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
        }

        @Override
        protected String doInBackground(ApprovalEntry... approvalEntries) {
            String lsResponse = "";
            try{
                if(approvalEntries[0].isDataValid()) {
                    RequestApproval loApprovl = new RequestApproval(lsPackage);
                    loApprovl.setDeptIDxx(loSession.getDeptID());
                    loApprovl.setEmpLevID(loSession.getEmployeeLevel());
                    loApprovl.setEmployID(loSession.getEmployeeID());
                    loApprovl.setRemarks(approvalEntries[0].getRemarks());

                    String lsAppCode = loApprovl.Generate(
                            approvalEntries[0].getBranchCd(),
                            approvalEntries[0].getReqDatex(),
                            approvalEntries[0].getSystemCd(),
                            approvalEntries[0].getMiscInfo());

                    if (lsAppCode.isEmpty()) {
                        lsResponse = AppConstants.APPROVAL_CODE_EMPTY(loApprovl.getMessage());
                    } else {
                        lsResponse = AppConstants.APPROVAL_CODE_GENERATED(lsAppCode);

                        if(!poConn.isDeviceConnected()){
                            Log.d(TAG, "No internet.");
                        } else {
                            List<ECodeApproval> laForPost = poApproval.getSystemApprovalForUploading();
                            for (int x = 0; x < laForPost.size(); x++) {
                                ECodeApproval detail = laForPost.get(x);
                                JSONObject param = new JSONObject();
                                param.put("sTransNox", detail.getTransNox());
                                param.put("dTransact", detail.getTransact());
                                param.put("sSystemCD", detail.getSystemCD());
                                param.put("sReqstdBy", detail.getReqstdBy());
                                param.put("dReqstdxx", detail.getReqstdxx());
                                param.put("cIssuedBy", detail.getIssuedBy());
                                param.put("sMiscInfo", detail.getMiscInfo());
                                param.put("sRemarks1", detail.getRemarks1());
                                param.put("sRemarks2", detail.getApprCode() == null ? "" : detail.getRemarks2());
                                param.put("sApprCode", detail.getApprCode());
                                param.put("sEntryByx", detail.getEntryByx());
                                param.put("sApprvByx", detail.getApprvByx());
                                param.put("sReasonxx", detail.getReasonxx() == null ? "" : detail.getReasonxx());
                                param.put("sReqstdTo", detail.getReqstdTo() == null ? "" : detail.getReqstdTo());
                                param.put("cTranStat", detail.getTranStat());

                                String response = WebClient.httpsPostJSon(poApi.getUrlSaveApproval(loConfig.isBackUpServer()), param.toString(), poHeaders.getHeaders());
                                if (response == null) {
                                    Log.d(TAG, "Server no response");
                                } else {
                                    JSONObject loResponse = new JSONObject(response);
                                    String result = loResponse.getString("result");
                                    if (result.equalsIgnoreCase("success")) {
                                        String TransNox = loResponse.getString("sTransNox");
                                        poApproval.updateUploaded(detail.getTransNox(), TransNox);
                                        Log.d(TAG, "Approval Code has been uploaded to server");
                                    } else {
                                        JSONObject loError = loResponse.getJSONObject("error");
                                        String message = loError.getString("message");
                                        Log.d(TAG, "Failed to upload approval code. " + message);
                                    }
                                }

                                Thread.sleep(1000);
                            }

                            int unposted = poApproval.getUnpostedApprovalCode();
                            if (unposted > 0) {
                                Log.d(TAG, "Approval Code has been uploaded to server");
                            } else {
                                Log.d(TAG, "Approval Code has been uploaded to server");
                            }
                        }
                    }
                } else {
                    lsResponse = AppConstants.APPROVAL_CODE_EMPTY(approvalEntries[0].getMessage());
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

    public interface CodeApprovalCreatedListener{
        void OnCreate(String args);
        void OnCreateFailed(String message);
    }
}
