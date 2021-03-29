package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcBrand;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcBrand;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class RMcBrand {
    private static final String TAG = "DB_Brand_Repository";
    private final DMcBrand mcBrandDao;
    private final LiveData<List<EMcBrand>> allMcBrand;
    private final LiveData<String[]> allBrandNames;
    private final Application application;

    public RMcBrand(Application application){
        this.application = application;
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mcBrandDao = appDatabase.McBrandDao();
        allBrandNames = mcBrandDao.getAllBrandName();
        allMcBrand = mcBrandDao.getAllMcBrand();
    }

    public void insertBulkData(List<EMcBrand> brandList){
        mcBrandDao.insertBulkData(brandList);
    }

    public String getLatestDataTime(){
        return mcBrandDao.getLatestDataTime();
    }

    public EMcBrand getMcBrandInfo(String BrandID){
        return mcBrandDao.getMcBrandInfo(BrandID);
    }

    public void insertBrandInfo(JSONArray faJson) throws Exception {
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

            lsSQL = "SELECT dTimeStmp FROM MC_Brand " +
                    "WHERE sBrandIDx = "+ SQLUtil.toSQL((String) loJson.get("sBrandIDx"));
            loRS = loConn.executeQuery(lsSQL);

            lsSQL = "";

            if(!loRS.next()){

                if("1".equalsIgnoreCase(loJson.getString("cRecdStat"))){
                    lsSQL = "INSERT INTO MC_Brand" +
                            "(sBrandIDx, " +
                            "sBrandNme, " +
                            "cRecdStat, " +
                            "dTimeStmp) " +
                            "VALUES(" +
                            ""+SQLUtil.toSQL(loJson.getString("sBrandIDx"))+", " +
                            ""+SQLUtil.toSQL(loJson.getString("sBrandNme"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("cRecdStat"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("dTimeStmp"))+")";
                }
            } else {
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                if(!ldDate1.equals(ldDate2)){
                    lsSQL = "UPDATE MC_Brand SET " +
                            "sBrandNme = "+SQLUtil.toSQL(loJson.getString("sBrandNme"))+"," +
                            "cRecdStat = "+SQLUtil.toSQL(loJson.getString("cRecdStat"))+"," +
                            "dTimeStmp = "+SQLUtil.toSQL(loJson.getString("dTimeStmp"))+"";
                }
            }

            if(!lsSQL.isEmpty()){
                Log.d(TAG, lsSQL);
                if(loConn.executeUpdate(lsSQL) <= 0){
                    Log.e(TAG, loConn.getMessage());
                } else {
                    Log.d(TAG, "Brand info save successfully");
                }
            } else {
                Log.d(TAG, "No record to update. Brand maybe on its latest on local database.");
            }
        }
        Log.e(TAG, "Brand info has been save to local.");

        loConn = null;
    }

    public LiveData<List<EMcBrand>> getAllBrandInfo(){
        return allMcBrand;
    }

    public LiveData<String[]> getAllBrandNames(){
        return allBrandNames;
    }
}
