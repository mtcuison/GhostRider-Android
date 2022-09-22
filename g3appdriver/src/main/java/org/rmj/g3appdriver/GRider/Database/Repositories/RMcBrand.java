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
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcBrand;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcBrand;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RMcBrand {
    private static final String TAG = RMcBrand.class.getSimpleName();

    private final DMcBrand poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RMcBrand(Application instance){
        poDao = GGC_GriderDB.getInstance(instance).McBrandDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public void insertBulkData(List<EMcBrand> brandList){
        poDao.insertBulkData(brandList);
    }

    public String getLatestDataTime(){
        return poDao.getLatestDataTime();
    }

    public EMcBrand getMcBrandInfo(String BrandID){
        return poDao.getMcBrandInfo(BrandID);
    }

    public LiveData<List<EMcBrand>> getAllBrandInfo(){
        return poDao.getAllMcBrand();
    }

    public LiveData<String[]> getAllBrandNames(){
        return poDao.getAllBrandName();
    }

    public boolean ImportMCBrands(){
        try{
            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("descript", "All");

            EMcBrand loObj = poDao.GetLatestBrandInfo();
            if(loObj != null){
                params.put("dTimeStmp", loObj.getTimeStmp());
            }

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlImportBrand(poConfig.isBackUpServer()),
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
            for(int x = 0 ; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EMcBrand loDetail = poDao.getMcBrandInfo(loJson.getString("sBrandIDx"));

                if(loDetail == null){

                    if(loJson.getString("cRecdStat").equalsIgnoreCase("1")){
                        EMcBrand loBrand = new EMcBrand();
                        loBrand.setBrandIDx(loJson.getString("sBrandIDx"));
                        loBrand.setBrandNme(loJson.getString("sBrandNme"));
                        loBrand.setRecdStat(loJson.getString("cRecdStat"));
                        loBrand.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.insert(loBrand);
                        Log.d(TAG, "MC brand info has been saved.");
                    }

                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setBrandIDx(loJson.getString("sBrandIDx"));
                        loDetail.setBrandNme(loJson.getString("sBrandNme"));
                        loDetail.setRecdStat(loJson.getString("cRecdStat"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.update(loDetail);
                        Log.d(TAG, "MC brand info has been updated.");
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
