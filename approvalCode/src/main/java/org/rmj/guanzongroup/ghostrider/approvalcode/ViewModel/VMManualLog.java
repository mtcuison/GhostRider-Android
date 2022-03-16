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
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;
import org.rmj.guanzongroup.ghostrider.approvalcode.Etc.ViewModelCallback;
import org.rmj.guanzongroup.ghostrider.approvalcode.Model.ManualLog;

import java.util.List;

public class VMManualLog extends AndroidViewModel {
    public static final String TAG = VMManualLog.class.getSimpleName();
    private final Application instance;
    private final RBranch poBranchR;
    private final RApprovalCode poAppCdeR;
    private final SessionManager poSession;
    private final ConnectionUtil poConnect;
    private final HttpHeaders poHeaders;
    private final String psPackage;
    private final MutableLiveData<ECodeApproval> mCodeApproval = new MutableLiveData<>();

    public VMManualLog(@NonNull Application application) {
        super(application);
        this.instance = application;
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
        new CreateCodeTask(instance, poSession, psPackage, listener).execute(model);
    }

    public LiveData<ECodeApproval> getCodeApprovalInfo(){
        return poAppCdeR.getCodeApprovalInfo();
    }

    public void setCodeApprovalInfo(ECodeApproval codeApprovalInfo){
        this.mCodeApproval.setValue(codeApprovalInfo);
    }

    private static class CreateCodeTask extends AsyncTask<ManualLog, Void, String>{
        private final SessionManager loSession;
        private final CodeApprovalCreatedListener listener;
        private final String lsPackage;
        private final HttpHeaders loHeaders;
        private final ConnectionUtil poConn;
        private final RApprovalCode loApproval;
        private final WebApi poApi;

        public CreateCodeTask(Application instance,
                              SessionManager foSession,
                              String fsPackage,
                              CodeApprovalCreatedListener listener) {
            this.loSession = foSession;
            this.lsPackage = fsPackage;
            this.listener = listener;
            this.loHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.loApproval = new RApprovalCode(instance);
            this.poApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(ManualLog... manualLogs) {
            String lsResponse = "";
            try{
                if(manualLogs[0].isDataValid()) {
                    RequestApproval loApprovl = new RequestApproval(lsPackage);
                    loApprovl.setDeptIDxx(loSession.getDeptID());
                    loApprovl.setEmpLevID(loSession.getEmployeeLevel());
                    loApprovl.setEmployID(loSession.getEmployeeID());
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

                        if(poConn.isDeviceConnected()) {
                            List<ECodeApproval> laForPost = loApproval.getSystemApprovalForUploading();
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

                                String response = WebClient.httpsPostJSon(poApi.getUrlSaveApproval(), param.toString(), loHeaders.getHeaders());
                                if (response == null) {
                                    Log.d(TAG, "Server no response");
                                } else {
                                    JSONObject loResponse = new JSONObject(response);
                                    String result = loResponse.getString("result");
                                    if (result.equalsIgnoreCase("success")) {
                                        String TransNox = loResponse.getString("sTransNox");
                                        loApproval.updateUploaded(detail.getTransNox(), TransNox);
                                        Log.d(TAG, "Approval Code has been uploaded to server");
                                    } else {
                                        JSONObject loError = loResponse.getJSONObject("error");
                                        String message = loError.getString("message");
                                        Log.d(TAG, "Failed to upload approval code. " + message);
                                    }
                                }

                                Thread.sleep(1000);
                            }

                            int unposted = loApproval.getUnpostedApprovalCode();
                            if (unposted > 0) {
                                Log.d(TAG, "Approval Code has been uploaded to server");
                            } else {
                                Log.d(TAG, "Approval Code has been uploaded to server");
                            }
                        } else {
                            Log.d(TAG, "Approval Code has been uploaded to server");
                        }
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

    public interface CodeApprovalCreatedListener{
        void OnCreate(String args);
        void OnCreateFailed(String message);
    }
}