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

package org.rmj.g3appdriver.GCircle.room.Repositories;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.GCircle.room.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.GCircle.Apps.BullsEye.PerformancePeriod;
import org.rmj.g3appdriver.GCircle.Etc.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;

import java.util.ArrayList;
import java.util.List;

public class RAreaPerformance {
    private static final String TAG = RAreaPerformance.class.getSimpleName();

    private final DAreaPerformance poDao;

    private final AppConfigPreference poConfig;
    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RAreaPerformance(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).AreaPerformanceDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new GCircleApi(instance);
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

            ArrayList<String> lsPeriod = PerformancePeriod.getList();
            for(int i = 0; i < lsPeriod.size(); i++){
                JSONObject params = new JSONObject();
                params.put("period", lsPeriod.get(i));
                params.put("areacd", lsAreaCode);

                String lsRespones = WebClient.sendRequest(
                        poApi.getImportAreaPerformance(),
                        params.toString(),
                        poHeaders.getHeaders());

                if(lsRespones == null){
                    message = SERVER_NO_RESPONSE;
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                JSONObject loResponse = new JSONObject(lsRespones);
                String lsResult = loResponse.getString("result");
                if (lsResult.equalsIgnoreCase("error")) {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = getErrorMessage(loError);
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
            message = getLocalMessage(e);
            return false;
        }
    }
}
