    package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.util.Log;

    import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class RTown {
    private static final String TAG = "DB_Town_Repository";
    private final DTownInfo townDao;
    private LiveData<List<ETownInfo>> allTownInfo;
    private LiveData<List<ETownInfo>> townInfoFromProvince;
    private final Application application;

    public RTown(Application application){
        this.application = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        townDao = GGCGriderDB.TownDao();
        allTownInfo = townDao.getAllTownInfo();
    }

    public LiveData<List<ETownInfo>> getTownInfoFromProvince(String ProvinceID){
        return townDao.getAllTownInfoFromProvince(ProvinceID);
    }

    public LiveData<String[]> getTownNamesFromProvince(String ProvinceID){
        return townDao.getTownNamesFromProvince(ProvinceID);
    }

    public void insertBulkData(List<ETownInfo> townInfoList){
        townDao.insertBulkData(townInfoList);
    }

    public String getLatestDataTime(){
        return townDao.getLatestDataTime();
    }

    public LiveData<ETownInfo> getTownNameAndProvID(String fsID){
        return townDao.getTownNameAndProvID(fsID);
    }
    public LiveData<DTownInfo.BrgyTownProvinceInfo> getBrgyTownProvinceInfo(String fsID){
        return townDao.getBrgyTownProvinceInfo(fsID);
    }
    public LiveData<DTownInfo.BrgyTownProvinceInfo> getTownProvinceInfo(String fsID){
        return townDao.getTownProvinceInfo(fsID);
    }
    public LiveData<List<DTownInfo.TownProvinceInfo>> getTownProvinceInfo(){
        return townDao.getTownProvinceInfo();
    }

    public DTownInfo.TownProvinceName getTownProvinceName(String TownID){
        return townDao.getTownProvinceNames(TownID);
    }

    public void saveTownInfo(JSONArray faJson) throws Exception {
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
            lsSQL = "SELECT dTimeStmp FROM Town_Info" +
                    " WHERE sTownIDxx = " + SQLUtil.toSQL((String) loJson.get("sTownIDxx"));
            loRS = loConn.executeQuery(lsSQL);

            lsSQL = "";
            //record does not exists
            if (!loRS.next()){
                //check if the record is active
                if ("1".equalsIgnoreCase(loJson.getString("cRecdStat"))){
                    //create insert statement
                    lsSQL = "INSERT INTO Town_Info" +
                            "(sTownIDxx" +
                            ",sTownName" +
                            ",sZippCode" +
                            ",sProvIDxx" +
                            ",sMuncplCd" +
                            ",cHasRoute" +
                            ",cBlackLst" +
                            ",cRecdStat" +
                            ",dTimeStmp)" +
                            " VALUES" +
                            "(" + SQLUtil.toSQL(loJson.getString("sTownIDxx")) +
                            "," + SQLUtil.toSQL(loJson.getString("sTownName")) +
                            "," + SQLUtil.toSQL(loJson.getString("sZippCode")) +
                            "," + SQLUtil.toSQL(loJson.getString("sProvIDxx")) +
                            "," + SQLUtil.toSQL(loJson.getString("sMuncplCd")) +
                            "," + SQLUtil.toSQL(loJson.getString("cHasRoute")) +
                            "," + SQLUtil.toSQL(loJson.getString("cBlackLst")) +
                            "," + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            "," + SQLUtil.toSQL(loJson.getString("dTimeStmp")) + ")";
                }
            } else { //record already exists
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                //compare date if the record from API is newer than the database record
                if (!ldDate1.equals(ldDate2)){
                    //create update statement
                    lsSQL = "UPDATE Town_Info SET" +
                            "   sTownName = " + SQLUtil.toSQL(loJson.getString("sTownName")) +
                            ",  sZippCode = " + SQLUtil.toSQL(loJson.getString("sZippCode")) +
                            ",  sProvIDxx = " + SQLUtil.toSQL(loJson.getString("sProvIDxx")) +
                            ",  sMuncplCd = " + SQLUtil.toSQL(loJson.getString("sMuncplCd")) +
                            ",  cHasRoute = " + SQLUtil.toSQL(loJson.getString("cHasRoute")) +
                            ",  cBlackLst = " + SQLUtil.toSQL(loJson.getString("cBlackLst")) +
                            ",  cRecdStat = " + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                            ",  dTimeStmp = " + SQLUtil.toSQL(loJson.getString("dTimeStmp")) +
                            " WHERE sTownIDxx = " + SQLUtil.toSQL(loJson.getString("sTownIDxx"));
                }
            }

            if (!lsSQL.isEmpty()){
                Log.d(TAG, lsSQL);
                if(loConn.executeUpdate(lsSQL) <= 0){
                    Log.e(TAG, loConn.getMessage());
                } else
                    Log.d(TAG, "Town info saved successfully.");
            } else {
                Log.d(TAG, "No record to update. Town info maybe on its latest on local database.");
            }
        }
        Log.e(TAG, "Town info has been save to local.");

        //terminate object connection
        loConn = null;
    }
}
