package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcCategory;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcCategory;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class RMcCategory {
    private static final String TAG = "DB_Category_Repository";
    private final Application application;
    private final DMcCategory mcCategoryDao;
    private final LiveData<List<EMcCategory>> allMcCategory;

    public RMcCategory(Application application){
        this.application = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        mcCategoryDao = GGCGriderDB.McCategoryDao();
        allMcCategory = mcCategoryDao.getAllMcCategory();
    }

    public void insertBulkData(List<EMcCategory> categories){
        mcCategoryDao.insetBulkData(categories);
    }

    public String getLatestDataTime(){
        return mcCategoryDao.getLatestDataTime();
    }

    public void saveCategoryInfo(JSONArray faJson) throws Exception {
        GConnection loConn = DbConnection.doConnect(application);

        if (loConn == null){
            Log.e(TAG, "Connection was not initialized.");
            return;
        }

        JSONObject loJson;
        String lsSQL;
        ResultSet loRS;

        for(int x = 0; x < faJson.length(); x++){
            loJson = new JSONObject(faJson.getString(x));

            //check if record already exists on database
            lsSQL = "SELECT dTimeStmp FROM MC_Category" +
                    " WHERE sMcCatIDx = " + SQLUtil.toSQL((String) loJson.get("sMCCatIDx"));
            loRS = loConn.executeQuery(lsSQL);

            lsSQL = "";
            //record does not exists
            if (!loRS.next()){
                //check if the record is active
                if ("1".equalsIgnoreCase(loJson.getString("cRecdStat"))){
                    //create insert statement
                    lsSQL = "INSERT INTO MC_Category" +
                            "(sMcCatIDx" +
                            ",sMcCatNme" +
                            ",nMiscChrg" +
                            ",nRebatesx" +
                            ",nEndMrtgg" +
                            ",cRecdStat" +
                            ",dTimeStmp)" +
                            " VALUES" +
                            "(" + SQLUtil.toSQL(loJson.getString("sMCCatIDx")) +
                            "," + SQLUtil.toSQL(loJson.getString("sMCCatNme")) +
                            "," + SQLUtil.toSQL(loJson.getString("nMiscChrg")) +
                            "," + SQLUtil.toSQL(loJson.getString("nRebatesx")) +
                            "," + SQLUtil.toSQL(loJson.getString("nEndMrtgg")) +
                            "," + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            "," + SQLUtil.toSQL(loJson.getString("dTimeStmp")) + ")";
                }
            } else { //record already exists
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                //compare date if the record from API is newer than the database record
                if (!ldDate1.equals(ldDate2)){
                    //create update statement
                    lsSQL = "UPDATE MC_Category SET" +
                            "   sModelCde = " + SQLUtil.toSQL(loJson.getString("sMCCatIDx")) +
                            ",  sModelNme = " + SQLUtil.toSQL(loJson.getString("sMCCatNme")) +
                            ",  sBrandIDx = " + SQLUtil.toSQL(loJson.getString("nMiscChrg")) +
                            ",  cMotorTyp = " + SQLUtil.toSQL(loJson.getString("nRebatesx")) +
                            ",  cRegisTyp = " + SQLUtil.toSQL(loJson.getString("nEndMrtgg")) +
                            ",  cEndOfLfe = " + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            ",  dTimeStmp = " + SQLUtil.toSQL(loJson.getString("dTimeStmp")) +
                            " WHERE sMcCatIDx = " + SQLUtil.toSQL(loJson.getString("sMCCatIDx"));
                }
            }

            if (!lsSQL.isEmpty()){
                Log.d(TAG, lsSQL);
                if(loConn.executeUpdate(lsSQL) <= 0){
                    Log.e(TAG, loConn.getMessage());
                } else
                    Log.d(TAG, "Category info saved successfully.");
            } else {
                Log.d(TAG, "No record to update. Category info maybe on its latest on local database.");
            }
        }
        Log.e(TAG, "Category info has been save to local.");

        //terminate object connection
        loConn = null;
    }
}
