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

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DFileCode;
import org.rmj.g3appdriver.GCircle.room.Entities.EFileCode;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;

import java.util.Date;

public class RFileCode{
    private static final String TAG = RFileCode.class.getSimpleName();
    private final DFileCode poDao;

    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RFileCode(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).FileCodeDao();
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public String getLatestDataTime(){
        return poDao.getLatestDataTime();
    }

    public boolean ImportFileCode(){
        try{
            JSONObject params = new JSONObject();
            params.put("descript", "All");
            params.put("deptidxx", "015");
            params.put("bsearch", true);

            EFileCode loObj = poDao.GetLatestFileCode();
            if(loObj != null) {
                params.put("timestamp", loObj.getTimeStmp());
            }

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlImportFileCode(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EFileCode loDetail = poDao.GetFileCode(loJson.getString("sFileCode"));

                if(loDetail == null){

                    if (loJson.getString("cRecdStat").equalsIgnoreCase("1")){
                        EFileCode loFile = new EFileCode();
                        loFile.setFileCode(loJson.getString("sFileCode"));
                        loFile.setBarrcode(loJson.getString("sBarrcode"));
                        loFile.setBriefDsc(loJson.getString("sBriefDsc"));
                        loFile.setRecdStat(loJson.getString("cRecdStat"));
                        loFile.setEntryNox(loJson.getInt("nEntryNox"));
                        loFile.setLstUpdte(AppConstants.DATE_MODIFIED());
                        loFile.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.insert(loFile);
                        Log.d(TAG, "File code info has been saved.");
                    }

                } else {
                    Log.d(TAG, "Record found.");
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setFileCode(loJson.getString("sFileCode"));
                        loDetail.setBarrcode(loJson.getString("sBarrcode"));
                        loDetail.setBriefDsc(loJson.getString("sBriefDsc"));
                        loDetail.setRecdStat(loJson.getString("cRecdStat"));
                        loDetail.setEntryNox(loJson.getInt("nEntryNox"));
                        loDetail.setFileCode(AppConstants.DATE_MODIFIED());
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.update(loDetail);
                        Log.d(TAG, "File code info has been updated.");
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
