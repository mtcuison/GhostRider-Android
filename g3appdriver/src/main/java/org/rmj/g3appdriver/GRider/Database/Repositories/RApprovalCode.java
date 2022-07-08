/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.GRider.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.GRider.Database.Entities.ESCA_Request;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.List;

public class RApprovalCode {
    private static final String TAG = RApprovalCode.class.getSimpleName();
    private final DApprovalCode approvalDao;

    private final Context context;

    public RApprovalCode(Application application){
        this.context = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        approvalDao = GGCGriderDB.ApprovalDao();
    }

    public void insert(ECodeApproval codeApproval){
        approvalDao.insert(codeApproval);
    }

    public void update(ECodeApproval codeApproval){
        approvalDao.update(codeApproval);
    }

    public void insertBulkData(List<ESCA_Request> requestList){
        approvalDao.insertBulkData(requestList);
    }

    public LiveData<ECodeApproval> getCodeApprovalInfo(){
        return approvalDao.getCodeApprovalEntry();
    }

    public LiveData<List<ESCA_Request>> getAuthReferenceList(){
        return approvalDao.getSCA_AuthReference();
    }

    public LiveData<List<ESCA_Request>> getAuthNameList(){
        return approvalDao.getSCA_AuthName();
    }

    public LiveData<List<ESCA_Request>> getAuthorizedFeatures(SupportSQLiteQuery sqLiteQuery){
        return approvalDao.getAuthorizedFeatures(sqLiteQuery);
    }

    public LiveData<String> getApprovalDesc(String appCode){
        return approvalDao.getApprovalDesc(appCode);
    }

    public List<ECodeApproval> getSystemApprovalForUploading(){
        return approvalDao.getSystemApprovalForUploading();
    }

    public void updateUploaded(String TransNox, String NTransNo){
        approvalDao.updateUploaded(TransNox, NTransNo);
    }

    public Integer getUnpostedApprovalCode(){
        return approvalDao.getUnpostedApprovalCode();
    }

    public void uploadApprovalCodes(){
        try{
            List<ECodeApproval> laForPost = approvalDao.getSystemApprovalForUploading();
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

                AppConfigPreference loConfig = AppConfigPreference.getInstance(context);
                WebApi loApi = new WebApi(loConfig.getTestStatus());
                String response = WebClient.httpsPostJSon(loApi.getUrlSaveApproval(loConfig.isBackUpServer()), param.toString(), HttpHeaders.getInstance(context).getHeaders());
                if (response == null) {
                    Log.d(TAG, "Server no response");
                } else {
                    JSONObject loResponse = new JSONObject(response);
                    String result = loResponse.getString("result");
                    if (result.equalsIgnoreCase("success")) {
                        String TransNox = loResponse.getString("sTransNox");
                        approvalDao.updateUploaded(detail.getTransNox(), TransNox);
                        Log.d(TAG, "Approval Code has been uploaded to server");
                    } else {
                        JSONObject loError = loResponse.getJSONObject("error");
                        String message = loError.getString("message");
                        Log.d(TAG, "Failed to upload approval code. " + message);
                    }
                }

                Thread.sleep(1000);
            }

            int unposted = approvalDao.getUnpostedApprovalCode();
            if (unposted > 0) {
                Log.d(TAG, "Approval Code has been uploaded to server");
            } else {
                Log.d(TAG, "Approval Code has been uploaded to server");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
