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
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.GRider.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.GRider.Database.Entities.ESCA_Request;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.Date;
import java.util.List;

public class RApprovalCode {
    private static final String TAG = RApprovalCode.class.getSimpleName();

    private final DApprovalCode poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RApprovalCode(Application instance){
        this.poDao = GGC_GriderDB.getInstance(instance).ApprovalDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
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

    public LiveData<List<ESCA_Request>> getAuthorizedFeatures(SupportSQLiteQuery sqLiteQuery){
        return poDao.getAuthorizedFeatures(sqLiteQuery);
    }

    public LiveData<String> getApprovalDesc(String appCode){
        return poDao.getApprovalDesc(appCode);
    }

    public List<ECodeApproval> getSystemApprovalForUploading(){
        return poDao.getSystemApprovalForUploading();
    }

    public void updateUploaded(String TransNox, String NTransNo){
        poDao.updateUploaded(TransNox, NTransNo);
    }

    public Integer getUnpostedApprovalCode(){
        return poDao.getUnpostedApprovalCode();
    }

    public void uploadApprovalCodes(){
        try{
            List<ECodeApproval> laForPost = poDao.getSystemApprovalForUploading();
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

                String response = WebClient.httpsPostJSon(
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
                        poDao.updateUploaded(detail.getTransNox(), TransNox);
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

    public boolean ImportSCARequest(){
        try{
            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("id", "All");

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlScaRequest(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");

            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for (int x = 0; x < laJson.length(); x++) {
                JSONObject loJson = laJson.getJSONObject(x);
                ESCA_Request loDetail = poDao.GetApprovalCode(loJson.getString("sSCACodex"));

                if(loDetail == null){
                    if(loJson.getString("cRecdStat").equalsIgnoreCase("1")) {
                        ESCA_Request info = new ESCA_Request();
                        info.setSCACodex(loJson.getString("sSCACodex"));
                        info.setSCATitle(loJson.getString("sSCATitle"));
                        info.setSCADescx(loJson.getString("sSCADescx"));
                        info.setSCATypex(loJson.getString("cSCATypex"));
                        info.setAreaHead(loJson.getString("cAreaHead"));
                        info.setHCMDeptx(loJson.getString("cHCMDeptx"));
                        info.setCSSDeptx(loJson.getString("cCSSDeptx"));
                        info.setComplnce(loJson.getString("cComplnce"));
                        info.setMktgDept(loJson.getString("cMktgDept"));
                        info.setASMDeptx(loJson.getString("cASMDeptx"));
                        info.setTLMDeptx(loJson.getString("cTLMDeptx"));
                        info.setSCMDeptx(loJson.getString("cSCMDeptx"));
                        info.setRecdStat(loJson.getString("cRecdStat"));
                        info.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.SaveSCARequest(info);
                        Log.d(TAG, "SCA Request has been saved.");
                    }

                }
//                else {
//                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
//                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
//                    if (!ldDate1.equals(ldDate2)) {
//                        loDetail.setSCACodex(loJson.getString("sSCACodex"));
//                        loDetail.setSCATitle(loJson.getString("sSCATitle"));
//                        loDetail.setSCADescx(loJson.getString("sSCADescx"));
//                        loDetail.setSCATypex(loJson.getString("cSCATypex"));
//                        loDetail.setAreaHead(loJson.getString("cAreaHead"));
//                        loDetail.setHCMDeptx(loJson.getString("cHCMDeptx"));
//                        loDetail.setCSSDeptx(loJson.getString("cCSSDeptx"));
//                        loDetail.setComplnce(loJson.getString("cComplnce"));
//                        loDetail.setMktgDept(loJson.getString("cMktgDept"));
//                        loDetail.setASMDeptx(loJson.getString("cASMDeptx"));
//                        loDetail.setTLMDeptx(loJson.getString("cTLMDeptx"));
//                        loDetail.setSCMDeptx(loJson.getString("cSCMDeptx"));
//                        loDetail.setRecdStat(loJson.getString("cRecdStat"));
//                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
//                        poDao.SaveSCARequest(loDetail);
//                        Log.d(TAG, "SCA Request has been updated.");
//                    }
//                }
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
