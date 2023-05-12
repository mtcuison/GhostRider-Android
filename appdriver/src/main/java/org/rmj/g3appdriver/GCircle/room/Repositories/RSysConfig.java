/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/26/21 4:19 PM
 * project file last modified : 4/26/21 4:19 PM
 */

package org.rmj.g3appdriver.GCircle.room.Repositories;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DSysConfig;
import org.rmj.g3appdriver.GCircle.room.Entities.ESysConfig;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;

import java.util.Date;

public class RSysConfig {
    private static final String TAG = RSysConfig.class.getSimpleName();
    private final DSysConfig poDao;

    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RSysConfig(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).sysConfigDao();
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean ImportSysConfig(){
        try{
            JSONObject params = new JSONObject();
            params.put("descript", "all");
            params.put("bsearch", true);

            ESysConfig loObj = poDao.GetLatestSysConfig();
            if(loObj != null){
                params.put("timestamp", loObj.getTimeStmp());
            }

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlImportSysConfig(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            Log.e(TAG, loResponse.getString("result"));
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = new JSONObject(laJson.getString(x));
                ESysConfig loDetail = poDao.GetSysConfig(loJson.getString("sConfigCd"));

                if(loDetail == null) {
                    ESysConfig info = new ESysConfig();
                    info.setConfigCd(loJson.getString("sConfigCd"));
                    info.setConfigDs(loJson.getString("sConfigDs"));
                    info.setConfigVl(loJson.getString("sConfigVl"));
                    info.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.SaveSysConfig(info);
                    Log.d(TAG, "System config has been saved.");
                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setConfigCd(loJson.getString("sConfigCd"));
                        loDetail.setConfigDs(loJson.getString("sConfigDs"));
                        loDetail.setConfigVl(loJson.getString("sConfigVl"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.UpdateSysConfig(loDetail);
                        Log.d(TAG, "System config has been updated.");
                    }
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
