/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/14/21 3:59 PM
 * project file last modified : 5/14/21 3:59 PM
 */

package org.rmj.g3appdriver.dev.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DRelation;
import org.rmj.g3appdriver.dev.Database.Entities.ERelation;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RRelation {
    private static final String TAG = RRelation.class.getSimpleName();

    private final DRelation poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RRelation(Application instance){
        this.poDao = GGC_GriderDB.getInstance(instance).RelDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public String getRelationFromId(String fsRelatId) {
        return poDao.getRelationFromId(fsRelatId);
    }

    public LiveData<List<ERelation>> getRelation(){
        return poDao.getRelation();
    }

    public LiveData<String[]> getAllRelatnDs(){
        return poDao.getRelatnDs();
    }

    public boolean insertBulkRelation(JSONArray faJson) throws Exception {
        try {
            List<ERelation> relations = new ArrayList<>();
            for (int x = 0; x < faJson.length(); x++) {
                JSONObject loJson = faJson.getJSONObject(x);
                if(poDao.GetRelationInfo(loJson.getString("sRelatnID")) == null) {
                    ERelation loanInfo = new ERelation();
                    loanInfo.setRelatnID(loJson.getString("sRelatnID"));
                    loanInfo.setRelatnDs(loJson.getString("sRelatnDs"));
                    loanInfo.setRecdStats(loJson.getString("cRecdStat"));
                    loanInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                    relations.add(loanInfo);
                }
            }
            poDao.insertBulkData(relations);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Integer GetRelationRecordsCount(){
        return poDao.GetRelationRecordsCount();
    }

    public boolean ImportRelations(){
        try{
            JSONObject params = new JSONObject();
            params.put("descript", "All");
            params.put("bsearch", true);

            ERelation loDetail = poDao.GetLatestRelationInfo();
            if(loDetail != null){
                params.put("timestamp", loDetail.getTimeStmp());
            }

            String lsResponse = WebClient.httpsPostJSon(poApi.getUrlDownloadRelation(
                    poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(Objects.requireNonNull(lsResponse));
            String lsResult = loResponse.getString("result");

            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for (int x = 0; x < laJson.length(); x++) {
                JSONObject loJson = laJson.getJSONObject(x);
                ERelation loRel = poDao.GetRelationInfo(loJson.getString("sRelatnID"));
                if(loRel == null){

                    if(loJson.getString("cRecdStat").equalsIgnoreCase("1")) {
                        ERelation loanInfo = new ERelation();
                        loanInfo.setRelatnID(loJson.getString("sRelatnID"));
                        loanInfo.setRelatnDs(loJson.getString("sRelatnDs"));
                        loanInfo.setRecdStats(loJson.getString("cRecdStat"));
                        loanInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.insert(loanInfo);
                        Log.d(TAG, "Relation info has been saved.");
                    }

                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loRel.setRelatnID(loJson.getString("sRelatnID"));
                        loRel.setRelatnDs(loJson.getString("sRelatnDs"));
                        loRel.setRecdStats(loJson.getString("cRecdStat"));
                        loRel.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.update(loRel);
                        Log.d(TAG, "Relation info has been updated.");
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
