/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/28/21 8:56 AM
 * project file last modified : 4/28/21 8:56 AM
 */
package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DLocatorSysLog;
import org.rmj.g3appdriver.GRider.Database.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.List;

public class RLocationSysLog {
    private static final String TAG = "LocationSysLog";
    private final Application instance;
    private final DLocatorSysLog poDao;
    private final HttpHeaders poHeaders;
    private final WebApi poApi;
    private final AppConfigPreference poConfig;

    private String message;

    public RLocationSysLog(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).locatorSysLogDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
    }

    public String getMessage() {
        return message;
    }

    public void saveCurrentLocation(EGLocatorSysLog sysLog){
        poDao.insertLocation(sysLog);
    }

    public void updateSysLogStatus(String dTransact){
        poDao.updateSysLogStatus(new AppConstants().DATE_MODIFIED, dTransact);
    }

    public boolean uploadUnsentLocationTracks(){
        try{
            List<EGLocatorSysLog> loLocations = poDao.GetTrackingLocations();
            JSONObject loJson = new JSONObject();
            JSONArray laDetail = new JSONArray();
            for(int x= 0; x < loLocations.size(); x++){
                JSONObject loDetail = new JSONObject();
                loDetail.put("dTransact", loLocations.get(x).getTransact());
                loDetail.put("nLatitude", loLocations.get(x).getLatitude());
                loDetail.put("nLongitud", loLocations.get(x).getLongitud());
                loDetail.put("cGPSEnbld", loLocations.get(x).getGpsEnbld());
                laDetail.put(loDetail);
            }
            loJson.put("detail", laDetail);

            String lsResponse = WebClient.httpPostJSon(poApi.getUrlSubmitLocationTrack(poConfig.isBackUpServer()),
                    loJson.toString(), poHeaders.getHeaders());
            if(lsResponse == null){
                message = "No server response.";
                return false;
            } else {
                Log.d(TAG, lsResponse);
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    for(int x = 0; x < loLocations.size(); x++){
                        poDao.updateSysLogStatus(new AppConstants().DATE_MODIFIED, loLocations.get(x).getTransact());
                    }
                    return true;
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    message = lsMessage;
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
