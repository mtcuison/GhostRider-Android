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

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRelation;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ERelation;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RRelation {
    private static final String TAG = RRelation.class.getSimpleName();
    private final DRelation relDao;
    private final Application app;
    private final LiveData<String[]> allRelatnDs;
    private final LiveData<List<ERelation>> allRelatns;
    public RRelation(Application application){
        this.app = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        relDao = GGCGriderDB.RelDao();
        allRelatnDs = relDao.getRelatnDs();
        allRelatns = relDao.getRelation();
    }
    public String getRelationFromId(String fsRelatId) {
        return relDao.getRelationFromId(fsRelatId);
    }

    public LiveData<List<ERelation>> getRelation(){
        return allRelatns;
    }

    public LiveData<String[]> getAllRelatnDs(){
        return allRelatnDs;
    }

    public boolean insertBulkRelation(JSONArray faJson) throws Exception {
        try {
            List<ERelation> relations = new ArrayList<>();
            for (int x = 0; x < faJson.length(); x++) {
                JSONObject loJson = faJson.getJSONObject(x);
                ERelation loanInfo = new ERelation();
                loanInfo.setRelatnID(loJson.getString("sRelatnID"));
                loanInfo.setRelatnDs(loJson.getString("sRelatnDs"));
                loanInfo.setRecdStats(loJson.getString("cRecdStat"));;
                loanInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                relations.add(loanInfo);
            }
            relDao.insertBulkData(relations);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
