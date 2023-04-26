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

package org.rmj.g3appdriver.dev.Database.GCircle.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECodeApproval;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ESCA_Request;
import org.rmj.g3appdriver.dev.Database.GCircle.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Account.SessionManager;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.dev.Api.WebApi;

import java.util.List;

public class RApprovalCode {
    private static final String TAG = RApprovalCode.class.getSimpleName();

    private final DApprovalCode poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;
    private final SessionManager poSession;

    private String message;

    public RApprovalCode(Application instance){
        this.poDao = GGC_GCircleDB.getInstance(instance).ApprovalDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poSession = new SessionManager(instance);
    }

    public String getMessage() {
        return message;
    }

    public void insert(ECodeApproval codeApproval){
        poDao.insert(codeApproval);
    }

    public void update(ECodeApproval codeApproval){
        poDao.update(codeApproval);
    }

    public void insertBulkData(List<ESCA_Request> requestList){
        poDao.insertBulkData(requestList);
    }

    public LiveData<ECodeApproval> getCodeApprovalInfo(){
        return poDao.getCodeApprovalEntry();
    }

    public LiveData<List<ESCA_Request>> getAuthReferenceList(){
        return poDao.getSCA_AuthReference();
    }

    public LiveData<List<ESCA_Request>> getAuthNameList(){
        return poDao.getSCA_AuthName();
    }

    public LiveData<String> getApprovalDesc(String appCode){
        return poDao.getApprovalDesc(appCode);
    }

    public List<ECodeApproval> getSystemApprovalForUploading(){
        return poDao.GetSystemApprovalForUploading();
    }

    public void updateUploaded(String TransNox, String NTransNo){
        poDao.UpdateUploaded(TransNox, NTransNo);
    }

    public Integer getUnpostedApprovalCode(){
        return poDao.getUnpostedApprovalCode();
    }

    public void uploadApprovalCodes(){
        try{
            List<ECodeApproval> laForPost = poDao.GetSystemApprovalForUploading();
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

                String response = WebClient.sendRequest(
                        poApi.getUrlSaveApproval(poConfig.isBackUpServer()),
                        param.toString(),
                        poHeaders.getHeaders());
                if (response == null) {
                    Log.d(TAG, "Server no response");
                } else {
                    JSONObject loResponse = new JSONObject(response);
                    String result = loResponse.getString("result");
                    if (result.equalsIgnoreCase("success")) {
                        String TransNox = loResponse.getString("sTransNox");
                        poDao.UpdateUploaded(detail.getTransNox(), TransNox);
                        Log.d(TAG, "Approval Code has been uploaded to server");
                    } else {
                        JSONObject loError = loResponse.getJSONObject("error");
                        String message = loError.getString("message");
                        Log.d(TAG, "Failed to upload approval code. " + message);
                    }
                }

                Thread.sleep(1000);
            }

            int unposted = poDao.getUnpostedApprovalCode();
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
