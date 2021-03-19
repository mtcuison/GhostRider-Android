package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class RBranchLoanApplication {
    private static final String TAG = RBranchLoanApplication.class.getSimpleName();
    private final DBranchLoanApplication poLoan;

    private final Application application;

    public RBranchLoanApplication(Application application){
        this.application = application;
        this.poLoan = AppDatabase.getInstance(application).CreditAppDocsDao();
    }

    public LiveData<List<EBranchLoanApplication>> getBranchLoanApplications(){
        return poLoan.getBranchLoanApplications();
    }

    public boolean insertApplicationInfos(JSONArray faJson) throws Exception {
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
            lsSQL = "SELECT dTimeStmp FROM Credit_Online_Application_List" +
                    " WHERE sTransNox = " + SQLUtil.toSQL((String) loJson.get("sTransNox"));
            loRS = loConn.executeQuery(lsSQL);

            lsSQL = "";
            //record does not exists
            if (!loRS.next()){
                //create insert statement
                lsSQL = "INSERT INTO Credit_Online_Application_List" +
                        "(sTransNox" +
                        ",dTransact" +
                        ",sCredInvx" +
                        ",sCompnyNm" +
                        ",sSpouseNm" +
                        ",sAddressx" +
                        ",sMobileNo" +
                        ",sQMAppCde" +
                        ",sModelNme" +
                        ",nDownPaym" +
                        ",nAcctTerm" +
                        ",cTranStat" +
                        ",dTimeStmp)" +
                        " VALUES" +
                        "(" + SQLUtil.toSQL(loJson.getString("sTransNox")) +
                        "," + SQLUtil.toSQL(loJson.getString("dTransact")) +
                        "," + SQLUtil.toSQL(loJson.getString("sCredInvx")) +
                        "," + SQLUtil.toSQL(loJson.getString("sCompnyNm")) +
                        "," + SQLUtil.toSQL(loJson.getString("sSpouseNm")) +
                        "," + SQLUtil.toSQL(loJson.getString("sAddressx")) +
                        "," + SQLUtil.toSQL(loJson.getString("sMobileNo")) +
                        "," + SQLUtil.toSQL(loJson.getString("sQMAppCde")) +
                        "," + SQLUtil.toSQL(loJson.getString("sModelNme")) +
                        "," + SQLUtil.toSQL(loJson.getString("nDownPaym")) +
                        "," + SQLUtil.toSQL(loJson.getString("nAcctTerm")) +
                        "," + SQLUtil.toSQL(loJson.getString("cTranStat")) +
                        "," + SQLUtil.toSQL(loJson.getString("dTimeStmp")) + ")";
            } else {
                Log.e(TAG, "Record already exist");
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
