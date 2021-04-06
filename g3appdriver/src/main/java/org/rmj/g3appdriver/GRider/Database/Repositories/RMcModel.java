package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcModel;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcModel;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class RMcModel {
    private static final String TAG = "DB_Model_Repository";
    private final Application application;
    private final DMcModel mcModelDao;
    private LiveData<List<EMcModel>> allMcModelInfo;
    private LiveData<String[]> allMcModelName;

    public RMcModel(Application application){
        this.application = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        mcModelDao = GGCGriderDB.McModelDao();
    }

    public LiveData<List<EMcModel>> getMcModelFromBrand(String BrandID){
        allMcModelInfo = mcModelDao.getAllModeFromBrand(BrandID);
        return allMcModelInfo;
    }

    public LiveData<String[]> getAllMcModelName(String BrandID){
        allMcModelName = mcModelDao.getAllModelName(BrandID);
        return allMcModelName;
    }

    public String getLatestDataTime(){
        return mcModelDao.getLatestDataTime();
    }

    public void insertBulkData(List<EMcModel> modelList){
        mcModelDao.insertBulkData(modelList);
    }

    public EMcModel getModelInfo(String TransNox){
        return mcModelDao.getModelInfo(TransNox);
    }

    public void saveMcModelInfo(JSONArray faJson) throws Exception {
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
            lsSQL = "SELECT dTimeStmp FROM Mc_Model" +
                    " WHERE sModelIDx = " + SQLUtil.toSQL((String) loJson.get("sModelIDx"));
            loRS = loConn.executeQuery(lsSQL);

            lsSQL = "";
            //record does not exists
            if (!loRS.next()){
                //check if the record is active
                if ("1".equals((String) loJson.get("cRecdStat"))){
                    //create insert statement
                    lsSQL = "INSERT INTO Mc_Model" +
                            "(sModelIDx" +
                            ",sModelCde" +
                            ",sModelNme" +
                            ",sBrandIDx" +
                            ",cMotorTyp" +
                            ",cRegisTyp" +
                            ",cEndOfLfe" +
                            ",cEngineTp" +
                            ",cHotItemx" +
                            ",cRecdStat" +
                            ",dTimeStmp)" +
                            " VALUES" +
                            "(" + SQLUtil.toSQL(loJson.getString("sModelIDx")) +
                            "," + SQLUtil.toSQL(loJson.getString("sModelCde")) +
                            "," + SQLUtil.toSQL(loJson.getString("sModelNme")) +
                            "," + SQLUtil.toSQL(loJson.getString("sBrandIDx")) +
                            "," + SQLUtil.toSQL(loJson.getString("cMotorTyp")) +
                            "," + SQLUtil.toSQL(loJson.getString("cRegisTyp")) +
                            "," + SQLUtil.toSQL(loJson.getString("cEndOfLfe")) +
                            "," + SQLUtil.toSQL(loJson.getString("cEngineTp")) +
                            "," + SQLUtil.toSQL(loJson.getString("cHotItemx")) +
                            "," + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            "," + SQLUtil.toSQL(loJson.getString("dTimeStmp")) + ")";
                }
            } else { //record already exists
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                //compare date if the record from API is newer than the database record
                if (!ldDate1.equals(ldDate2)){
                    //create update statement
                    lsSQL = "UPDATE Mc_Model SET" +
                            "   sModelCde = " + SQLUtil.toSQL(loJson.getString("sModelCde")) +
                            ",  sModelNme = " + SQLUtil.toSQL(loJson.getString("sModelNme")) +
                            ",  sBrandIDx = " + SQLUtil.toSQL(loJson.getString("sBrandIDx")) +
                            ",  cMotorTyp = " + SQLUtil.toSQL(loJson.getString("cMotorTyp")) +
                            ",  cRegisTyp = " + SQLUtil.toSQL(loJson.getString("cRegisTyp")) +
                            ",  cEndOfLfe = " + SQLUtil.toSQL(loJson.getString("cEndOfLfe")) +
                            ",  cEngineTp = " + SQLUtil.toSQL(loJson.getString("cEngineTp")) +
                            ",  cHotItemx = " + SQLUtil.toSQL(loJson.getString("cHotItemx")) +
                            ",  cRecdStat = " + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            ",  dTimeStmp = " + SQLUtil.toSQL(loJson.getString("dTimeStmp")) +
                            " WHERE sModelIDx = " + SQLUtil.toSQL(loJson.getString("sModelIDx"));
                }
            }

            if (!lsSQL.isEmpty()){
                Log.d(TAG, lsSQL);
                if(loConn.executeUpdate(lsSQL) <= 0){
                    Log.e(TAG, loConn.getMessage());
                } else
                    Log.d(TAG, "Model info saved successfully.");
            } else {
                Log.d(TAG, "No record to update. Model info maybe on its latest on local database.");
            }
        }
        Log.e(TAG, "Model info has been save to local.");

        //terminate object connection
        loConn = null;
    }
}
