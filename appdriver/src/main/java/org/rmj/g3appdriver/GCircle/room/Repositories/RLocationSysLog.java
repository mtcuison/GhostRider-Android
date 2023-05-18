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
package org.rmj.g3appdriver.GCircle.room.Repositories;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DLocatorSysLog;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;

import java.util.List;

public class RLocationSysLog {
    private static final String TAG = RLocationSysLog.class.getSimpleName();
    private final DLocatorSysLog poDao;
    private final HttpHeaders poHeaders;
    private final GCircleApi poApi;

    private String message;

    public RLocationSysLog(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).locatorSysLogDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GCircleApi(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean saveCurrentLocation(EGLocatorSysLog sysLog){
        try {
            EEmployeeInfo loUser = poDao.GetUserInfo();

            if (loUser == null) {
                Log.d(TAG, "Unable to save location no user detected.");
                return false;
            }

            sysLog.setTimeStmp(AppConstants.DATE_MODIFIED());
            sysLog.setTransact(AppConstants.DATE_MODIFIED());
            sysLog.setUserIDxx(loUser.getUserIDxx());
            poDao.insertLocation(sysLog);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean uploadUnsentLocationTracks(){
        try{
            List<EGLocatorSysLog> loLocations = poDao.GetTrackingLocations();

            if(loLocations == null){
                message = "No records for location to upload";
                return false;
            }

            if(loLocations.size() == 0){
                message = "No records for location to upload";
                return false;
            }

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

            String lsResponse = WebClient.sendRequest(poApi.getUrlSubmitLocationTrack(),
                    loJson.toString(), poHeaders.getHeaders());
            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            for(int x = 0; x < loLocations.size(); x++){
                poDao.updateSysLogStatus(AppConstants.DATE_MODIFIED(), loLocations.get(x).getTransact());
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
