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

import android.annotation.SuppressLint;
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
import java.util.Objects;

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

    public String getBranchNameForNotification(String BranchCd){
        return branchInfoDao.getBranchNameForNotification(BranchCd);
    }

    public LiveData<String> getBranchAreaCode(String fsBranchCd) {
        return branchInfoDao.getBranchAreaCode(fsBranchCd);
    }

    public LiveData<List<EBranchInfo>> getAreaBranchList(){
        return branchInfoDao.getAreaBranchList();
    }

    @SuppressLint("NewApi")
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
            loRS = Objects.requireNonNull(loConn).executeQuery(lsSQL);

            lsSQL = "";
            //record does not exists
            if (!loRS.next()){
                //check if the record is active
                if ("1".equals((String) loJson.get("cRecdStat"))){
                    //create insert statement
                    EBranchInfo loBranch = new EBranchInfo();
                    loBranch.setBranchCd(loJson.getString("sBranchCd"));
                    loBranch.setBranchNm(loJson.getString("sBranchNm"));
                    loBranch.setDescript(loJson.getString("sDescript"));
                    loBranch.setAddressx(loJson.getString("sAddressx"));
                    loBranch.setTownIDxx(loJson.getString("sTownIDxx"));
                    loBranch.setAreaCode(loJson.getString("sAreaCode"));
                    loBranch.setDivision(loJson.getString("cDivision"));
                    loBranch.setPromoDiv(loJson.getString("cPromoDiv"));
                    loBranch.setRecdStat(loJson.getString("cRecdStat"));
                    loBranch.setTimeStmp(loJson.getString("dTimeStmp"));
                    branchInfoDao.insertBranchInfo(loBranch);
                }
            } else { //record already exists
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                //compare date if the record from API is newer than the database record
                if (!ldDate1.equals(ldDate2)){
                    //create update statement
                    branchInfoDao.updateBranchInfo(loJson.getString("sBranchCd"),
                            loJson.getString("sBranchNm"),
                            loJson.getString("sDescript"),
                            loJson.getString("sAddressx"),
                            loJson.getString("sTownIDxx"),
                            loJson.getString("sAreaCode"),
                            loJson.getString("cDivision"),
                            loJson.getString("cPromoDiv"),
                            loJson.getString("cRecdStat"),
                            loJson.getString("dTimeStmp"));
                }
            }
        }

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
