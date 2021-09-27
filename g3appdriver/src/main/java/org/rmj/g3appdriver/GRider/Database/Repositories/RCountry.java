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
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCountryInfo;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class RCountry {
    private static final String TAG = "DB_Country_Repository";
    private final DCountryInfo countryDao;
    private final LiveData<List<ECountryInfo>> allCountryInfo;
    private final LiveData<String[]> allCountryNames;
    private final LiveData<String[]> allCountryCitizenNames;
    private final Application application;

    public RCountry(Application application){
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        this.countryDao = GGCGriderDB.CountryDao();
        this.allCountryInfo = countryDao.getAllCountryInfo();
        this.allCountryNames = countryDao.getAllCountryNames();
        this.allCountryCitizenNames = countryDao.getAllCountryCitizenNames();
        this.application = application;
    }

    public LiveData<List<ECountryInfo>> getAllCountryInfo() {
        return allCountryInfo;
    }

    public LiveData<String[]> getAllCountryNames(){
        return allCountryNames;
    }

    public LiveData<String[]> getAllCountryCitizenName(){
        return allCountryCitizenNames;
    }

    public void insertBulkData(List<ECountryInfo> countryInfos){
        countryDao.insertBulkData(countryInfos);
    }

    public String getLatestDataTime(){
        return countryDao.getLatestDataTime();
    }

    public ECountryInfo getCountryInfo(String ID){
        return countryDao.getCountryInfo(ID);
    }

    public LiveData<String> getClientCitizenship(String CntryCde){
        return countryDao.getClientCitizenship(CntryCde);
    }

    public LiveData<String> getCountryNameFromId(String fsCntryCd) {
        return  countryDao.getCountryNameFromId(fsCntryCd);
    }

    public void insertCountryInfo(JSONArray faJson) throws Exception{
        GConnection loConn = DbConnection.doConnect(application);

        if (loConn == null){
            //Log.e(TAG, "Connection was not initialized");
            return;
        }

        JSONObject loJson;
        String lsSQL;
        ResultSet loRS;

        for (int x = 0; x < faJson.length(); x++){
            loJson = new JSONObject(faJson.getString(x));

            lsSQL = "SELECT dTimeStmp FROM Country_Info " +
                    "WHERE sCntryCde = "+ SQLUtil.toSQL((String) loJson.get("sCntryCde"));

            loRS = loConn.executeQuery(lsSQL);

            lsSQL = "";

            if(!loRS.next()){

                if("1".equalsIgnoreCase(loJson.getString("cRecdStat"))){
                    lsSQL = "INSERT INTO Country_Info" +
                            "(sCntryCde, " +
                            "sCntryNme, " +
                            "sNational, " +
                            "cRecdStat, " +
                            "dTimeStmp) " +
                            "VALUES(" +
                            ""+SQLUtil.toSQL(loJson.getString("sCntryCde"))+", " +
                            ""+SQLUtil.toSQL(loJson.getString("sCntryNme"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("sNational"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("cRecdStat"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("dTimeStmp"))+")";
                }
            } else {
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                if(!ldDate1.equals(ldDate2)){
                    lsSQL = "UPDATE Country_Info SET " +
                            "sCntryNme = "+SQLUtil.toSQL(loJson.getString("sCntryNme"))+"," +
                            "sNational = "+SQLUtil.toSQL(loJson.getString("sNational"))+"," +
                            "cRecdStat = "+SQLUtil.toSQL(loJson.getString("cRecdStat"))+"," +
                            "dTimeStmp = "+SQLUtil.toSQL(loJson.getString("dTimeStmp"))+"";
                }
            }
            if(!lsSQL.isEmpty()){
                if(loConn.executeUpdate(lsSQL) <= 0){
                    //Log.e(TAG, loConn.getMessage());
                } else {
                    //Log.d(TAG, "Country info save successfully");
                }
            } else {
                //Log.d(TAG, "No record to update. Country maybe on its latest on local database.");
            }
        }
        //Log.e(TAG, "Country info has been save to local.");

        loConn = null;
    }
}
