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
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcModelPrice;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcModelPrice;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class RMcModelPrice {
    private static final String TAG = "DB_Price_Repository";
    private final Application application;
    private final DMcModelPrice mcModelPriceDao;
    private LiveData<List<EMcModelPrice>> allMcModelPrice;

    public RMcModelPrice(Application application) {
        this.application = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        mcModelPriceDao = GGCGriderDB.McModelPriceDao();
    }

    public void insertBulkData(List<EMcModelPrice> modelPrices) {
        mcModelPriceDao.insertBulkdData(modelPrices);
    }

    public String getLatestDataTime() {
        return mcModelPriceDao.getLatestDataTime();
    }

    public void saveMcModelPrice(JSONArray faJson) throws Exception {
        GConnection loConn = DbConnection.doConnect(application);

        if (loConn == null) {
            //Log.e(TAG, "Connection was not initialized.");
            return;
        }

        JSONObject loJson;
        String lsSQL;
        ResultSet loRS;

        for (int x = 0; x < faJson.length(); x++) {
            loJson = new JSONObject(faJson.getString(x));

            //check if record already exists on database
            lsSQL = "SELECT dTimeStmp FROM Mc_Model_Price" +
                    " WHERE sModelIDx = " + SQLUtil.toSQL((String) loJson.get("sModelIDx"));
            loRS = loConn.executeQuery(lsSQL);

            lsSQL = "";
            //record does not exists
            if (!loRS.next()) {
                //check if the record is active
                if ("1".equalsIgnoreCase(loJson.getString("cRecdStat"))) {
                    //create insert statement
                    lsSQL = "INSERT INTO Mc_Model_Price" +
                            "(sModelIDx " +
                            ",nSelPrice " +
                            ",nLastPrce " +
                            ",nDealrPrc " +
                            ",nMinDownx " +
                            ",sMCCatIDx " +
                            ",dPricexxx " +
                            ",dInsPrice " +
                            ",cRecdStat " +
                            ",dTimeStmp)" +
                            " VALUES" +
                            "(" + SQLUtil.toSQL(loJson.getString("sModelIDx")) +
                            "," + SQLUtil.toSQL(loJson.getString("nSelPrice")) +
                            "," + SQLUtil.toSQL(loJson.getString("nLastPrce")) +
                            "," + SQLUtil.toSQL(loJson.getString("nDealrPrc")) +
                            "," + SQLUtil.toSQL(loJson.getString("nMinDownx")) +
                            "," + SQLUtil.toSQL(loJson.getString("sMCCatIDx")) +
                            "," + SQLUtil.toSQL(loJson.getString("dPricexxx")) +
                            "," + SQLUtil.toSQL(loJson.getString("dInsPrice")) +
                            "," + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            "," + SQLUtil.toSQL(loJson.getString("dTimeStmp")) + ")";
                }
            } else { //record already exists
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                //compare date if the record from API is newer than the database record
                if (!ldDate1.equals(ldDate2)) {
                    //create update statement
                    lsSQL = "UPDATE Mc_Model_Price SET" +
                            " nSelPrice = " + SQLUtil.toSQL(loJson.getString("nSelPrice")) +
                            ", nLastPrce = " + SQLUtil.toSQL(loJson.getString("nLastPrce")) +
                            ", nDealrPrc = " + SQLUtil.toSQL(loJson.getString("nDealrPrc")) +
                            ", nMinDownx = " + SQLUtil.toSQL(loJson.getString("nMinDownx")) +
                            ", sMCCatIDx = " + SQLUtil.toSQL(loJson.getString("sMCCatIDx")) +
                            ", dPricexxx = " + SQLUtil.toSQL(loJson.getString("dPricexxx")) +
                            ", dInsPrice = " + SQLUtil.toSQL(loJson.getString("dInsPrice")) +
                            ", cRecdStat = " + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            ", dTimeStmp = " + SQLUtil.toSQL(loJson.getString("dTimeStmp")) +
                            " WHERE sModelIDx = " + SQLUtil.toSQL(loJson.getString("sModelIDx"));
                }
            }

            if (!lsSQL.isEmpty()) {
                if (loConn.executeUpdate(lsSQL) <= 0) {
                    //Log.e(TAG, loConn.getMessage());
                }
            }
        }

        //terminate object connection
        loConn = null;
    }
}
