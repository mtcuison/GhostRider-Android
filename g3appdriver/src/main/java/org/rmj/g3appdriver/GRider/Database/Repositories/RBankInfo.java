package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBankInfo;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EBankInfo;

import java.sql.ResultSet;
import java.util.List;

public class RBankInfo {
    private static final String TAG = RBankInfo.class.getSimpleName();
    private final DBankInfo bankDao;

    private Application instance;

    public RBankInfo(Application application){
        this.instance = application;
        AppDatabase db = AppDatabase.getInstance(instance);
        bankDao = db.BankInfoDao();
    }

    public LiveData<List<EBankInfo>> getBankInfoList(){
        return bankDao.getBankInfoList();
    }

    public LiveData<String[]> getBankNameList(){
        return bankDao.getBankNameList();
    }

    public boolean insertBankInfo(JSONArray faJson) throws Exception{
        GConnection loConn = DbConnection.doConnect(instance);
        boolean result = true;

        if(loConn == null){
            Log.e(TAG, "Connection was not initialized.");
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
            loRS = loConn.executeQuery(lsSQL);

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
                            "," + SQLUtil.toSQL(loJson.getString("cRecdStat")) + ")";
                }

            } else {

                //record already exists

                lsSQL = "UPDATE Bank_Info SET" +
                        " sBankName = " + SQLUtil.toSQL(loJson.getString("sBankName")) +
                        ",  cRecdStat = " + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                        " WHERE sBankIDxx = " + SQLUtil.toSQL(loJson.getString("sBankIDxx"));
            }

            if (!lsSQL.isEmpty()){
                //Log.d(TAG, lsSQL);
                if (loConn.executeUpdate(lsSQL) <= 0) {
                    Log.e(TAG, loConn.getMessage());
                    result = false;
                } else {
                    Log.d(TAG, "Branch info saved successfully.");
                }
            } else {
                Log.d(TAG, "No record to update. Branch info maybe on its latest on local database.");
            }
        }
        Log.e(TAG, "Branch info has been save to local.");

        //terminate object connection
        loConn = null;
        return result;
    }
}
