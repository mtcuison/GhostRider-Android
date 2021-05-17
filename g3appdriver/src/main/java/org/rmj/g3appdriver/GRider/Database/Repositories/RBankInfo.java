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
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBankInfo;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EBankInfo;

import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

public class RBankInfo {
    private static final String TAG = RBankInfo.class.getSimpleName();
    private final DBankInfo bankDao;

    private Application instance;

    public RBankInfo(Application application){
        this.instance = application;
        GGC_GriderDB db = GGC_GriderDB.getInstance(instance);
        bankDao = db.BankInfoDao();
    }

    public LiveData<List<EBankInfo>> getBankInfoList(){
        return bankDao.getBankInfoList();
    }

    public LiveData<String[]> getBankNameList(){
        return bankDao.getBankNameList();
    }

    @SuppressLint("NewApi")
    public boolean insertBankInfo(JSONArray faJson) throws Exception{
        GConnection loConn = DbConnection.doConnect(instance);
        boolean result = true;

        if(loConn == null){
            result = false;
        }

        JSONObject loJson;
        String lsSQL;
        ResultSet loRS;

        for(int x = 0; x < faJson.length(); x++){
            loJson = new JSONObject(faJson.getString(x));

            //check if record already exists on database
            lsSQL = "SELECT dTimeStmp FROM Bank_Info" +
                    " WHERE sBankIDxx = " + SQLUtil.toSQL((String) loJson.get("sBankIDxx"));
            loRS = Objects.requireNonNull(loConn).executeQuery(lsSQL);

            lsSQL = "";

            //record does not exists
            if (!loRS.next()){

                //check if the record is active
                if ("1".equals((String) loJson.get("cRecdStat"))){

                    //create insert statement
                    lsSQL = "INSERT INTO Bank_Info" +
                            "(sBankIDxx" +
                            ",sBankName" +
                            ",cRecdStat)" +
                            " VALUES" +
                            "(" + SQLUtil.toSQL(loJson.getString("sBankIDxx")) +
                            "," + SQLUtil.toSQL(loJson.getString("sBankName")) +
                            "," + SQLUtil.toSQL(loJson.getString("cRecdStat")) +")";
                }

            } else {

                //record already exists
                lsSQL = "UPDATE Bank_Info SET" +
                        " sBankName = " + SQLUtil.toSQL(loJson.getString("sBankName")) +
                        ", cRecdStat = " + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                        " WHERE sBankIDxx = " + SQLUtil.toSQL(loJson.getString("sBankIDxx"));
            }

            if (!lsSQL.isEmpty()){
                //Log.d(TAG, lsSQL);
                if (loConn.executeUpdate(lsSQL) <= 0) {
                    //Log.e(TAG, loConn.getMessage());
                    result = false;
                }
            }
        }

        //terminate object connection
        loConn = null;
        return result;
    }
}
