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
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.Date;
import java.util.List;
import java.util.Objects;

    public class RTown {
    private static final String TAG = RTown.class.getSimpleName();

    private final DTownInfo poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RTown(Application instance){
        this.poDao = GGC_GriderDB.getInstance(instance).TownDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<List<ETownInfo>> getTownInfoFromProvince(String ProvinceID){
        return poDao.getAllTownInfoFromProvince(ProvinceID);
    }

    public LiveData<String[]> getTownNamesFromProvince(String ProvinceID){
        return poDao.getTownNamesFromProvince(ProvinceID);
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

    public LiveData<DTownInfo.TownProvinceName> getLiveTownProvinceNames(String TownID){
        return poDao.getLiveTownProvinceNames(TownID);
    }
    public LiveData<DTownInfo.TownProvinceInfo> getTownProvinceByTownID(String TownID){
        return poDao.getTownProvinceByTownID(TownID);
    }
    public LiveData<DTownInfo.TownProvinceInfo> getTownProvinceByTownName(String TownNm){
        return poDao.getTownProvinceByTownName(TownNm);
    }
    public LiveData<DTownInfo.BrgyTownProvinceInfoWithID> getBrgyTownProvinceInfoWithID(String BrgyID){
        return poDao.getBrgyTownProvinceInfoWithID(BrgyID);
    }

    public void saveTownInfo(JSONArray faJson) throws Exception {
        for (int x = 0; x < faJson.length(); x++) {
            JSONObject loJson = faJson.getJSONObject(x);
            if(poDao.GetTown(loJson.getString("sTownIDxx")) == null){
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
            }
        }
    }

    public Integer GetTownRecordsCount(){
        return poDao.GetTownRecordsCount();
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

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlImportTown(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(Objects.requireNonNull(lsResponse));
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
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
            message = e.getMessage();
            return false;
        }
    }
}
