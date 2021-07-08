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
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DFileCode;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RFileCode implements DFileCode{
    private static final String TAG = RFileCode.class.getSimpleName();
    private LiveData<List<EFileCode>> allFileCode;
    private DFileCode fileCodeDao;
    private Application application;

    public RFileCode(Application application) {
        this.application=application;
        this.fileCodeDao = GGC_GriderDB.getInstance(application).FileCodeDao();
        this.allFileCode = fileCodeDao.selectFileCodeList();
    }

    public LiveData<List<EFileCode>> getAllFileCode() {
        return allFileCode;
    }

    public LiveData<List<EFileCode>> selectFileCodeList() {
        return fileCodeDao.selectFileCodeList();
    }

    public String getLatestDataTime(){
        return fileCodeDao.getLatestDataTime();
    }

    public String getLastUpdate() {
        return fileCodeDao.getLastUpdate();
    }

    @SuppressLint("NewApi")
    public boolean insertFileCodeData(JSONArray faJson) throws Exception{
        GConnection loConn = DbConnection.doConnect(application);
        boolean result = true;
        if (loConn == null){
            Log.e(TAG, "Connection was not initialized.");
            result = false;
        }

        JSONObject loJson;
        String lsSQL;
        ResultSet loRS;

        for(int x = 0; x < faJson.length(); x++){
            loJson = new JSONObject(faJson.getString(x));

            //check if record already exists on database
            lsSQL = "SELECT dTimeStmp FROM EDocSys_File" +
                    " WHERE sFileCode = " + SQLUtil.toSQL((String) loJson.get("sFileCode"));
            loRS = Objects.requireNonNull(loConn).executeQuery(lsSQL);

            lsSQL = "";
            //record does not exists
            if (!loRS.next()){
                //check if the record is active
                if ("1".equals((String) loJson.get("cRecdStat"))){
                    //create insert statement
                    lsSQL = "INSERT INTO EDocSys_File" +
                            "(sFileCode" +
                            ",sBarrcode" +
                            ",sBriefDsc" +
                            ",cRecdStat" +
                            ",nEntryNox" +
                            ",dLstUpdte" +
                            ",dTimeStmp)" +
                            " VALUES" +
                            "(" + SQLUtil.toSQL(loJson.getString("sFileCode")) +
                            "," + SQLUtil.toSQL(loJson.getString("sBarrcode")) +
                            "," + SQLUtil.toSQL(loJson.getString("sBriefDsc")) +
                            "," + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            "," + SQLUtil.toSQL(loJson.getInt("nEntryNox")) +
                            "," + SQLUtil.toSQL(new AppConstants().CURRENT_DATE) +
                            "," + SQLUtil.toSQL(loJson.getString("dTimeStmp")) + ")";
                }
            } else { //record already exists
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                //compare date if the record from API is newer than the database record
                if (!ldDate1.equals(ldDate2)){
                    //create update statement
                    lsSQL = "UPDATE EDocSys_File SET" +
                            "   sFileCode = " + SQLUtil.toSQL(loJson.getString("sFileCode")) +
                            ",  sBarrcode = " + SQLUtil.toSQL(loJson.getString("sBarrcode")) +
                            ",  sBriefDsc = " + SQLUtil.toSQL(loJson.getString("sBriefDsc")) +
                            ",  cRecdStat = " + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            ",  nEntryNox = " + SQLUtil.toSQL(loJson.getInt("nEntryNox")) +
                            ",  dLstUpdte = " + SQLUtil.toSQL(new AppConstants().CURRENT_DATE) +
                            ",  dTimeStmp = " + SQLUtil.toSQL(loJson.getString("dTimeStmp"))  +
                            " WHERE sFileCode = " + SQLUtil.toSQL(loJson.getString("sFileCode"));
                }
            }

            if (!lsSQL.isEmpty()){
                //Log.d(TAG, lsSQL);
                if (loConn.executeUpdate(lsSQL) <= 0) {
                    Log.e(TAG, loConn.getMessage());
                    result = false;
                } else {
                    //Log.d(TAG, "FileCode info saved successfully.");
                }
            } else {
                //Log.d(TAG, "No record to update. FileCode info maybe on its latest on local database.");
            }
        }
        //Log.e(TAG, "FileCode info has been save to local.");

        //terminate object connection
        loConn = null;
        return result;
    }
}
