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

package org.rmj.g3appdriver.lib.Etc;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;

import java.util.Date;
import java.util.List;

public class Country {
    private static final String TAG = Country.class.getSimpleName();
    private final DCountryInfo poDao;

    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public Country(Application instance){
        this.poDao = GGC_GCircleDB.getInstance(instance).CountryDao();
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<List<ECountryInfo>> getAllCountryInfo() {
        return poDao.getAllCountryInfo();
    }

    public void insertBulkData(List<ECountryInfo> countryInfos){
        poDao.insertBulkData(countryInfos);
    }

    public String getLatestDataTime(){
        return poDao.getLatestDataTime();
    }

    public ECountryInfo getCountryInfo(String ID){
        return poDao.getCountryInfo(ID);
    }

    public boolean ImportCountry(){
        try {
            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("descript", "All");

            ECountryInfo loObj = poDao.GetLatestCountryInfo();
            if(loObj != null){
                params.put("timestamp", loObj.getTimeStmp());
            }

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlImportCountry(),
                    params.toString(),
                    poHeaders.getHeaders());

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

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                ECountryInfo loDetail = poDao.GetCountryInfo(loJson.getString("sCntryCde"));

                if(loDetail == null){

                    if(loJson.getString("cRecdStat").equalsIgnoreCase("1")){
                        ECountryInfo loCountry = new ECountryInfo();
                        loCountry.setCntryCde(loJson.getString("sCntryCde"));
                        loCountry.setCntryNme(loJson.getString("sCntryNme"));
                        loCountry.setNational(loJson.getString("sNational"));
                        loCountry.setRecdStat(loJson.getString("cRecdStat"));
                        loCountry.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.insert(loCountry);
                        Log.d(TAG, "Country info has been saved.");
                    }

                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setCntryCde(loJson.getString("sCntryCde"));
                        loDetail.setCntryNme(loJson.getString("sCntryNme"));
                        loDetail.setNational(loJson.getString("sNational"));
                        loDetail.setRecdStat(loJson.getString("cRecdStat"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.update(loDetail);
                        Log.d(TAG, "Country info has been updated.");
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
