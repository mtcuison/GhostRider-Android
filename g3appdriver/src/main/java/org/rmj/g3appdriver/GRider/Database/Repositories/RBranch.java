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

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchInfo;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RBranch {
    private static final String TAG = RBranch.class.getSimpleName();

    private final DBranchInfo poDao;
    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RBranch(Application instance){
        this.poDao = GGC_GriderDB.getInstance(instance).BranchDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public String getLatestDataTime(){
        return poDao.getLatestDataTime();
    }

    public String getBranchNameForNotification(String BranchCd){
        return poDao.getBranchNameForNotification(BranchCd);
    }

    public LiveData<String> getBranchAreaCode(String fsBranchCd) {
        return poDao.getBranchAreaCode(fsBranchCd);
    }

    public LiveData<List<EBranchInfo>> getAreaBranchList(){
        return poDao.getAreaBranchList();
    }

    public List<EBranchInfo> getBranchList(){
        return poDao.getBranchList();
    }
    public List<EBranchInfo> getAreaBranchesList(){
        return poDao.getAreaBranchesList();
    }

    public LiveData<List<EBranchInfo>> getAllMcBranchInfo() {
        return poDao.getAllMcBranchInfo();
    }

    public LiveData<String[]> getAllMcBranchNames(){
        return poDao.getMCBranchNames();
    }

    public LiveData<String[]> getAllBranchNames(){
        return poDao.getAllBranchNames();
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return poDao.getAllBranchInfo();
    }

    public LiveData<String> getPromoDivision(){
        return poDao.getPromoDivision();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poDao.getUserBranchInfo();
    }

    public LiveData<String> getBranchName(String BranchCde) {
        return poDao.getBranchName(BranchCde);
    }

    public LiveData<EBranchInfo> getSelfieLogBranchInfo(){
        return poDao.getSelfieLogBranchInfo();
    }

    public LiveData<EBranchInfo> getBranchInfo(String BranchCD){
        return poDao.getBranchInfo(BranchCD);
    }

    public boolean ImportBranches(){
        try{
            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("descript", "All");

            EBranchInfo loObj = poDao.GetLatestBranchInfo();
            if(loObj != null){
                params.put("dTimeStmp", loObj.getTimeStmp());
            }

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlImportBranches(poConfig.isBackUpServer()),
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
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EBranchInfo loDetail = poDao.GetBranchInfo(loJson.getString("sBranchCd"));

                if(loDetail == null){

                    if(loJson.getString("cRecdStat").equalsIgnoreCase("1")){
                        EBranchInfo loBranch = new EBranchInfo();
                        loBranch.setBranchCd(loJson.getString("sBranchCd"));
                        loBranch.setBranchNm(loJson.getString("sBranchNm"));
                        loBranch.setDescript(loJson.getString("sDescript"));
                        loBranch.setAddressx(loJson.getString("sAddressx"));
                        loBranch.setTownIDxx(loJson.getString("sTownIDxx"));
                        loBranch.setAreaCode(loJson.getString("sAreaCode"));
                        loBranch.setDivision(loJson.getString("cDivision"));
                        loBranch.setPromoDiv(loJson.getString("cPromoDiv"));
                        loBranch.setRecdStat(loJson.getString("cRecdStat"));
                        loBranch.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.SaveBranchInfo(loBranch);
                        Log.d(TAG, "Branch info has been saved.");
                    }

                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setBranchCd(loJson.getString("sBranchCd"));
                        loDetail.setBranchNm(loJson.getString("sBranchNm"));
                        loDetail.setDescript(loJson.getString("sDescript"));
                        loDetail.setAddressx(loJson.getString("sAddressx"));
                        loDetail.setTownIDxx(loJson.getString("sTownIDxx"));
                        loDetail.setAreaCode(loJson.getString("sAreaCode"));
                        loDetail.setDivision(loJson.getString("cDivision"));
                        loDetail.setPromoDiv(loJson.getString("cPromoDiv"));
                        loDetail.setRecdStat(loJson.getString("cRecdStat"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.UpdateBranchInfo(loDetail);
                        Log.d(TAG, "Branch info has been updated.");
                    }
                }
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
