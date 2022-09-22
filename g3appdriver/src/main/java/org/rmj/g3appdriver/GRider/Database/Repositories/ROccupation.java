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

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DOccupationInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.Date;
import java.util.List;

public class ROccupation {
    private static final String TAG = ROccupation.class.getSimpleName();

    private final DOccupationInfo poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public ROccupation(Application instance){
        this.poDao = GGC_GriderDB.getInstance(instance).OccupationDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<String[]> getAllOccupationNameList() {
        return poDao.getOccupationNameList();
    }

    public LiveData<List<EOccupationInfo>> getAllOccupationInfo() {
        return poDao.getAllOccupationInfo();
    }

    public void insertBulkData(List<EOccupationInfo> occupationInfos){
        poDao.insertBulkData(occupationInfos);
    }

    public String getOccupationName(String ID){
        return poDao.getOccupationName(ID);
    }

    public String getLatestDataTime(){
        return poDao.getLatestDataTime();
    }

    public LiveData<String> getLiveOccupationName(String ID) {
        return poDao.getLiveOccupationName(ID);
    }

    public Integer GetOccupationRecordsCount(){
        return poDao.GetOccupationRecordsCount();
    }
    public EOccupationInfo CheckIfExist(String fsVal){
        return poDao.GetOccupationInfo(fsVal);
    }

    public boolean ImportJobTitles(){
        try{
            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("id", "All");

            EOccupationInfo loDetail = poDao.GetLatestDataInfo();
            if (loDetail != null) {
                params.put("dTimeStmp", loDetail.getTimeStmp());
            }

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlImportOccupations(poConfig.isBackUpServer()),
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
                EOccupationInfo loJob = poDao.GetOccupationInfo(loJson.getString("sOccptnID"));
                if(loJob == null){

                    if(loJson.getString("cRecdStat").equalsIgnoreCase("1")) {
                        EOccupationInfo info = new EOccupationInfo();
                        info.setOccptnID(loJson.getString("sOccptnID"));
                        info.setOccptnNm(loJson.getString("sOccptnNm"));
                        info.setRecdStat(loJson.getString("cRecdStat"));
                        info.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.SaveOccupation(info);
                        Log.d(TAG, "Job title info info has been saved.");
                    }

                } else {
                    Date ldDate1 = SQLUtil.toDate(loJob.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loJob.setOccptnID(loJson.getString("sOccptnID"));
                        loJob.setOccptnNm(loJson.getString("sOccptnNm"));
                        loJob.setRecdStat(loJson.getString("cRecdStat"));
                        loJob.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.update(loDetail);
                        Log.d(TAG, "Job title info has been updated.");
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
