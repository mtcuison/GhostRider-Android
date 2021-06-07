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
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.GRider.Database.Repositories.RApprovalCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.approvalcode.Model.ApprovalEntry;

import java.util.List;

public class VMApprovalEntry extends AndroidViewModel {
    private static final String TAG = VMApprovalEntry.class.getSimpleName();
    private final RBranch poBranchx;
    private final RApprovalCode poAppCdeR;
    private final SessionManager poSession;
    private final ConnectionUtil poConnect;
    private final HttpHeaders poHeaders;
    private final String psPackage;
    private final MutableLiveData<ECodeApproval> mCodeApproval = new MutableLiveData<>();

    public VMApprovalEntry(@NonNull Application application) {
        super(application);
        poBranchx = new RBranch(application);
        this.poAppCdeR = new RApprovalCode(application);
        this.poSession = new SessionManager(application);
        this.poConnect = new ConnectionUtil(application);
        this.poHeaders = HttpHeaders.getInstance(application);
        this.psPackage = application.getPackageName();
    }

    public LiveData<String[]> getBranchNameList(){
        return poBranchx.getAllBranchNames();
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return poBranchx.getAllBranchInfo();
    }

    public void CreateApprovalCode(ApprovalEntry foEntry, CodeApprovalCreatedListener callback){
        new CreateCodeTask(poSession, psPackage, callback).execute(foEntry);
    }

    private static class CreateCodeTask extends AsyncTask<ApprovalEntry, Void, String>{
        private final SessionManager loSession;
        private final String lsPackage;
        private final CodeApprovalCreatedListener listener;

        public CreateCodeTask(SessionManager loSession, String lsPackage, CodeApprovalCreatedListener listener) {
            this.loSession = loSession;
            this.lsPackage = lsPackage;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(ApprovalEntry... approvalEntries) {
            String lsResponse = "";
            try{
                if(approvalEntries[0].isDataValid()) {
                    RequestApproval loApprovl = new RequestApproval(lsPackage);
                    loApprovl.setDeptIDxx(loSession.getDeptID());
                    loApprovl.setEmpLevID(loSession.getEmployeeID());
                    loApprovl.setEmployID(loSession.getClientId());
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