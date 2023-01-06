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

package org.rmj.g3appdriver.dev.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.BranchPerformancePeriod;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

public class RAreaPerformance {
    private static final String TAG = RAreaPerformance.class.getSimpleName();

    private final DAreaPerformance poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RAreaPerformance(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).AreaPerformanceDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public void insertBulkData(List<EAreaPerformance> areaPerformances){
        poDao.insertBulkData(areaPerformances);
    }

    public LiveData<List<EAreaPerformance>> getAreaPerformanceInfoList(){
        return poDao.getAllAreaPerformanceInfo();
    }

    public LiveData<List<EAreaPerformance>> getAreaPerformanceDashboard(){
        return poDao.getAreaPerformanceDashboard();
    }

    public LiveData<String> getAreaDescription(){
        return poDao.getAreaDescription();
    }

    public LiveData<String> getAreaNameFromCode(String fsAreaCde){
        return poDao.getAreaNameFromCode();
    }

    public boolean ImportData(){
        try{
            int lsUserLvl = poDao.GetUserLevel();
            String lsDeptIDx = poDao.GetUserDepartment();

            if(lsDeptIDx.equalsIgnoreCase(DeptCode.SALES)){
                message = "Your department code is not authorize to download branch performance info";
                return false;
            }

            if(lsUserLvl != DeptCode.LEVEL_AREA_MANAGER ||
                lsUserLvl != DeptCode.LEVEL_BRANCH_HEAD){
                message = "User is not authorize to download area/branch performance";
                return false;
            }

            String lsAreaCode = poDao.GetAreaCode();

            if(lsAreaCode == null){
                message = "Unable to retrieve area code. Please re-login account.";
                return false;
            }

            ArrayList<String> lsPeriod = BranchPerformancePeriod.getList();
            for(int i = 0; i < lsPeriod.size(); i++){
                JSONObject params = new JSONObject();
                params.put("period", lsPeriod.get(i));
                params.put("areacd", lsAreaCode);

                String lsRespones = WebClient.httpsPostJSon(
                        poApi.getImportAreaPerformance(poConfig.isBackUpServer()),
                        params.toString(),
                        poHeaders.getHeaders());

                if(lsRespones == null){
                    message = "Server no response.";
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                JSONObject loResponse = new JSONObject(lsRespones);
                String lsResult = loResponse.getString("result");
                if (lsResult.equalsIgnoreCase("error")) {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    Thread.sleep(1000);
                    continue;
                }

                JSONArray laJson = loResponse.getJSONArray("detail");
                for(int x = 0; x < laJson.length(); x++){
                    JSONObject loJson = laJson.getJSONObject(x);
                    EAreaPerformance loDetail = poDao.GetAreaPerformance(
                            loJson.getString("sPeriodxx"),
                            loJson.getString("sAreaCode"));

                    if(loDetail == null){

                        EAreaPerformance loArea = new EAreaPerformance();
                        loArea.setPeriodxx(loJson.getString("sPeriodxx"));
                        loArea.setAreaCode(loJson.getString("sAreaCode"));
                        loArea.setAreaDesc(loJson.getString("sAreaDesc"));
                        loArea.setMCGoalxx(Integer.parseInt(loJson.getString("nMCGoalxx")));
                        loArea.setSPGoalxx(Float.parseFloat(loJson.getString("nSPGoalxx")));
                        loArea.setJOGoalxx(Integer.parseInt(loJson.getString("nJOGoalxx")));
                        loArea.setLRGoalxx(Float.parseFloat(loJson.getString("nLRGoalxx")));
                        loArea.setMCActual(Integer.parseInt(loJson.getString("nMCActual")));
                        loArea.setSPActual(Float.parseFloat(loJson.getString("nSPActual")));
                        loArea.setLRActual(Float.parseFloat(loJson.getString("nLRActual")));
                        poDao.insert(loArea);
                        Log.d(TAG, "Area performance for " + lsAreaCode + " has been saved.");

                    } else {

                    }
                }

                Thread.sleep(1000);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
