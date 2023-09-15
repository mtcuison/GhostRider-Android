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
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ETownInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;

import java.util.Date;
import java.util.List;
import java.util.Objects;

    public class Town {
    private static final String TAG = Town.class.getSimpleName();

    private final DTownInfo poDao;

    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public Town(Application instance){
        this.poDao = GGC_GCircleDB.getInstance(instance).TownDao();
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public void insertBulkData(List<ETownInfo> townInfoList){
        poDao.insertBulkData(townInfoList);
    }

    public String getLatestDataTime(){
        return poDao.getLatestDataTime();
    }

    public LiveData<DTownInfo.BrgyTownProvinceInfo> getBrgyTownProvinceInfo(String fsID){
        return poDao.getBrgyTownProvinceInfo(fsID);
    }
    public LiveData<DTownInfo.BrgyTownProvinceInfo> getTownProvinceInfo(String fsID){
        return poDao.getTownProvinceInfo(fsID);
    }
    public LiveData<List<DTownInfo.TownProvinceInfo>> getTownProvinceInfo(){
        return poDao.getTownProvinceInfo();
    }

    public DTownInfo.TownProvinceName getTownProvinceName(String TownID){
        return poDao.getTownProvinceNames(TownID);
    }

    public boolean ImportTown(){
        try{
            JSONObject params = new JSONObject();

            params.put("bsearch", true);
            params.put("descript", "All");

            ETownInfo loObj = poDao.GetLatestTown();
            if(loObj != null){
                params.put("timestamp", loObj.getTimeStmp());
            }

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlImportTown(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(Objects.requireNonNull(lsResponse));
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                ETownInfo loDetail = poDao.GetTown(loJson.getString("sTownIDxx"));

                if(loDetail == null){

                    if(loJson.getString("cRecdStat").equalsIgnoreCase("1")) {
                        ETownInfo loTown = new ETownInfo();
                        loTown.setTownIDxx(loJson.getString("sTownIDxx"));
                        loTown.setTownName(loJson.getString("sTownName"));
                        loTown.setZippCode(loJson.getString("sZippCode"));
                        loTown.setProvIDxx(loJson.getString("sProvIDxx"));
                        loTown.setMuncplCd(loJson.getString("sMuncplCd"));
                        loTown.setHasRoute(loJson.getString("cHasRoute"));
                        loTown.setBlackLst(loJson.getString("cBlackLst"));
                        loTown.setRecdStat(loJson.getString("cRecdStat"));
                        loTown.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.insert(loTown);
                        Log.d(TAG, "Town info has been saved.");
                    }

                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setTownIDxx(loJson.getString("sTownIDxx"));
                        loDetail.setTownName(loJson.getString("sTownName"));
                        loDetail.setZippCode(loJson.getString("sZippCode"));
                        loDetail.setProvIDxx(loJson.getString("sProvIDxx"));
                        loDetail.setMuncplCd(loJson.getString("sMuncplCd"));
                        loDetail.setHasRoute(loJson.getString("cHasRoute"));
                        loDetail.setBlackLst(loJson.getString("cBlackLst"));
                        loDetail.setRecdStat(loJson.getString("cRecdStat"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.update(loDetail);
                        Log.d(TAG, "Town info has been updated.");
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
