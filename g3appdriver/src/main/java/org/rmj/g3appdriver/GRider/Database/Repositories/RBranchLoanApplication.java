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
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class RBranchLoanApplication {
    private static final String TAG = RBranchLoanApplication.class.getSimpleName();
    private final DBranchLoanApplication docsDao;

    private final Application application;
    private LiveData<List<EBranchLoanApplication>> branchCreditApplication;

    public RBranchLoanApplication(Application application){
        this.application = application;
        AppDatabase database = AppDatabase.getInstance(application);
        this.docsDao = AppDatabase.getInstance(application).CreditAppDocsDao();
//        branchCreditApplication = docsDao.getAllBranchCreditApplication();

    }
    public boolean insertBranchApplicationInfos(JSONArray faJson) throws Exception {
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
//            lsSQL = "SELECT dTimeStmp FROM Branch_Info" +
//                    " WHERE sBranchCd = " + SQLUtil.toSQL((String) loJson.get("sBranchCd"));
//            loRS = loConn.executeQuery(lsSQL);
//
//            lsSQL = "";
            //check if record already exists on database
            lsSQL = "SELECT dTimeStmp FROM Credit_Online_Application_List" +
                    " WHERE sTransNox = " + SQLUtil.toSQL((String) loJson.get("sTransNox"));
            loRS = loConn.executeQuery(lsSQL);

            lsSQL = "";

            //record does not exists
            if (!loRS.next()){
                //check if the record is active
                    //create insert statement
                lsSQL = "INSERT INTO Credit_Online_Application_List" +
                        "(sTransNox" +
                        ",sBranchCD" +
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
                        ",sCreatedx" +
                        ",cTranStat" +
                        ",dTimeStmp)" +
                        " VALUES" +
                        "(" + SQLUtil.toSQL(loJson.getString("sTransNox")) +
                        ","+ SQLUtil.toSQL(loJson.getString("sBranchCd")) +
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
                        "," + SQLUtil.toSQL(loJson.getString("sCreatedx")) +
                        "," + SQLUtil.toSQL(loJson.getString("cTranStat")) +
                        "," + SQLUtil.toSQL(loJson.getString("dTimeStmp")) + ")";
            } else { //record already exists
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                //compare date if the record from API is newer than the database record
                if (!ldDate1.equals(ldDate2)){
                    //create update statement
                    lsSQL = "UPDATE Credit_Online_Application_List SET" +
                            "   sBranchCD = " + SQLUtil.toSQL(loJson.getString("sBranchCd")) +
                            ",  dTransact = " + SQLUtil.toSQL(loJson.getString("dTransact")) +
                            ",  sCredInvx = " + SQLUtil.toSQL(loJson.getString("sCredInvx")) +
                            ",  sCompnyNm = " + SQLUtil.toSQL(loJson.getString("sCompnyNm")) +
                            ",  sSpouseNm = " + SQLUtil.toSQL(loJson.getString("sSpouseNm")) +
                            ",  sAddressx = " + SQLUtil.toSQL(loJson.getString("sAddressx")) +
                            ",  sMobileNo = " + SQLUtil.toSQL(loJson.getString("sMobileNo")) +
                            ",  sQMAppCde = " + SQLUtil.toSQL(loJson.getString("sQMAppCde")) +
                            ",  sModelNme = " + SQLUtil.toSQL(loJson.getString("sModelNme")) +
                            ",  nDownPaym = " + SQLUtil.toSQL(loJson.getString("nDownPaym")) +
                            ",  nAcctTerm = " + SQLUtil.toSQL(loJson.getString("nAcctTerm")) +
                            ",  sCreatedx = " + SQLUtil.toSQL(loJson.getString("sCreatedx")) +
                            ",  cTranStat = " + SQLUtil.toSQL(loJson.getString("cTranStat")) +
                            ",  dTimeStmp = " + SQLUtil.toSQL(loJson.getString("dTimeStmp")) +
                            " WHERE sTransNox = " + SQLUtil.toSQL(loJson.getString("sTransNox"));
                }
            }

            if (!lsSQL.isEmpty()){
                //Log.d(TAG, lsSQL);
                if (loConn.executeUpdate(lsSQL) <= 0) {
                    Log.e(TAG, loConn.getMessage() + " RBranch error");
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
    public LiveData<List<EBranchLoanApplication>> getBranchCreditApplication(){
        return docsDao.getAllBranchCreditApplication();
    }
    public LiveData<List<EBranchLoanApplication>> getCICreditApplication(){
        return docsDao.getAllCICreditApplication();
    }
    public LiveData<List<EBranchLoanApplication>> getAllCICreditApplicationLog(){
        return docsDao.getAllCICreditApplicationLog();
    }
    public void insertDetailBulkData(List<EBranchLoanApplication> eBranchLoanApplications){
        new InsertBulkBranchApplicationListAsyncTask(docsDao).execute(eBranchLoanApplications);
    }
    private static class InsertBulkBranchApplicationListAsyncTask extends AsyncTask<List<EBranchLoanApplication>, Void, Void> {
        private DBranchLoanApplication detailDao;

        private InsertBulkBranchApplicationListAsyncTask(DBranchLoanApplication detailDao){
            this.detailDao = detailDao;
        }

        @Override
        protected Void doInBackground(List<EBranchLoanApplication>... lists) {
            detailDao.insertBulkData(lists[0]);
            return null;
        }
    }
}
