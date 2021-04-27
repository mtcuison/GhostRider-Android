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
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchInfo;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class RBranch {
    private static final String TAG = "DB_Branch_Repository";
    private final DBranchInfo branchInfoDao;
    private final LiveData<List<EBranchInfo>> allMcBranchInfo;
    private final LiveData<List<EBranchInfo>> allBranchInfo;
    private final LiveData<String[]> allMcBranchNames;
    private final LiveData<String[]> allBranchNames;

    private final Application application;

    public RBranch(Application application){
        this.application = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        branchInfoDao = GGCGriderDB.BranchDao();
        allMcBranchNames = branchInfoDao.getMCBranchNames();
        allMcBranchInfo = branchInfoDao.getAllMcBranchInfo();
        allBranchNames = branchInfoDao.getAllBranchNames();
        allBranchInfo = branchInfoDao.getAllBranchInfo();
    }

    public String getLatestDataTime(){
        return branchInfoDao.getLatestDataTime();
    }

    public boolean insertBranchInfos(JSONArray faJson) throws Exception {
        GConnection loConn = DbConnection.doConnect(application);
        boolean result = true;
        if (loConn == null){
            result = false;
        }

        JSONObject loJson;
        String lsSQL;
        ResultSet loRS;

        for(int x = 0; x < faJson.length(); x++){
            loJson = new JSONObject(faJson.getString(x));

            //check if record already exists on database
            lsSQL = "SELECT dTimeStmp FROM Branch_Info" +
                    " WHERE sBranchCd = " + SQLUtil.toSQL((String) loJson.get("sBranchCd"));
            loRS = loConn.executeQuery(lsSQL);

            lsSQL = "";
            //record does not exists
            if (!loRS.next()){
                //check if the record is active
                if ("1".equals((String) loJson.get("cRecdStat"))){
                    //create insert statement
                    lsSQL = "INSERT INTO Branch_Info" +
                            "(sBranchCd" +
                            ",sBranchNm" +
                            ",sDescript" +
                            ",sAddressx" +
                            ",sTownIDxx" +
                            ",sAreaCode" +
                            ",cDivision" +
                            ",cPromoDiv" +
                            ",cRecdStat" +
                            ",dTimeStmp)" +
                            " VALUES" +
                            "(" + SQLUtil.toSQL(loJson.getString("sBranchCd")) +
                            "," + SQLUtil.toSQL(loJson.getString("sBranchNm")) +
                            "," + SQLUtil.toSQL(loJson.getString("sDescript")) +
                            "," + SQLUtil.toSQL(loJson.getString("sAddressx")) +
                            "," + SQLUtil.toSQL(loJson.getString("sTownIDxx")) +
                            "," + SQLUtil.toSQL(loJson.getString("sAreaCode")) +
                            "," + SQLUtil.toSQL(loJson.getString("cDivision")) +
                            "," + SQLUtil.toSQL(loJson.getString("cPromoDiv")) +
                            "," + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            "," + SQLUtil.toSQL(loJson.getString("dTimeStmp")) + ")";
                }
            } else { //record already exists
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                //compare date if the record from API is newer than the database record
                if (!ldDate1.equals(ldDate2)){
                    //create update statement
                    lsSQL = "UPDATE Branch_Info SET" +
                            "   sBranchNm = " + SQLUtil.toSQL(loJson.getString("sBranchNm")) +
                            ",  sDescript = " + SQLUtil.toSQL(loJson.getString("sDescript")) +
                            ",  sAddressx = " + SQLUtil.toSQL(loJson.getString("sAddressx")) +
                            ",  sTownIDxx = " + SQLUtil.toSQL(loJson.getString("sTownIDxx")) +
                            ",  sAreaCode = " + SQLUtil.toSQL(loJson.getString("sAreaCode")) +
                            ",  cDivision = " + SQLUtil.toSQL(loJson.getString("cDivision")) +
                            ",  cPromoDiv = " + SQLUtil.toSQL(loJson.getString("cPromoDiv")) +
                            ",  cRecdStat = " + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            ",  dTimeStmp = " + SQLUtil.toSQL(loJson.getString("dTimeStmp")) +
                            " WHERE sBranchCd = " + SQLUtil.toSQL(loJson.getString("sBranchCd"));
                }
            }

            if (!lsSQL.isEmpty()){
                //Log.d(TAG, lsSQL);
                if (loConn.executeUpdate(lsSQL) <= 0) {
                    Log.e(TAG, loConn.getMessage());
                    result = false;
                }
            }
        }
        //.e(TAG, "Branch info has been save to local.");

        //terminate object connection
        loConn = null;
        return result;
    }

    public LiveData<List<EBranchInfo>> getAllMcBranchInfo() {
        return allMcBranchInfo;
    }

    public LiveData<String[]> getAllMcBranchNames(){
        return allMcBranchNames;
    }

    public LiveData<String[]> getAllBranchNames(){
        return allBranchNames;
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return allBranchInfo;
    }

    public LiveData<String> getPromoDivision(){
        return branchInfoDao.getPromoDivision();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return branchInfoDao.getUserBranchInfo();
    }

    public LiveData<String> getBranchName(String BranchCde) {
        return branchInfoDao.getBranchName(BranchCde);
    }

}
