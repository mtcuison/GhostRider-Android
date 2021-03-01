package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.media.Image;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class RDailyCollectionPlan {
    private static final String TAG = "DB_CollectionDetail";
    private final DDCPCollectionDetail detailDao;
    private final DDCPCollectionMaster masterDao;
    private final Application application;

    public RDailyCollectionPlan(Application application){
        this.application = application;
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        detailDao = appDatabase.DcpDetailDao();
        masterDao = appDatabase.DcpMasterDao();
    }

    public void insertDetailBulkData(List<EDCPCollectionDetail> collectionDetails){
        detailDao.insertBulkData(collectionDetails);
    }

    public void insertCollectionDetail(EDCPCollectionDetail collectionDetail){
        detailDao.insert(collectionDetail);
    }

    public void insertMasterData(EDCPCollectionMaster collectionMaster){
        masterDao.insert(collectionMaster);
    }

    public void updateEntryMaster(String nEntryNox){
        masterDao.updateEntryMaster(nEntryNox);
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionDetailList(){
        return detailDao.getCollectionDetailList();
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionDetailLog(){
        return detailDao.getCollectionDetailLog();
    }

    public LiveData<EDCPCollectionMaster> getCollectionMaster(){
        return masterDao.getCollectionMaster();
    }

    public LiveData<EDCPCollectionDetail> getCollectionDetail(String TransNox, String EntryNox){
        return detailDao.getCollectionDetail(TransNox, EntryNox);
    }

    public void updateCollectionDetailInfo(EDCPCollectionDetail collectionDetail){
        detailDao.update(collectionDetail);
    }

    public void updateCollectionDetailImage(String ImageID, String AccntNox){
        detailDao.updateCustomerDetailImage(ImageID, AccntNox);
    }

    public LiveData<EDCPCollectionDetail> getCollectionLastEntry(){
        return detailDao.getCollectionLastEntry();
    }

    public LiveData<EDCPCollectionDetail> getDuplicateAccountEntry(String TransNox, String AccountNo){
        return detailDao.getDuplicateAccountEntry(TransNox, AccountNo);
    }

    public void saveCollectionMaster(JSONObject foJson) throws Exception {

    }

    public String getImageNextCode(){
        String lsNextCode = "";
        try{
            GConnection loConn = DbConnection.doConnect(application);
            lsNextCode = MiscUtil.getNextCode("Image_Information", "sTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsNextCode;
    }


    public String getNextAddressCode(){
        String lsNextCode = "";
        try{
            GConnection loConn = DbConnection.doConnect(application);
            lsNextCode = MiscUtil.getNextCode("Address_Update_Request", "sTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsNextCode;
    }

    public String getNextMobileCode(){
        String lsNextCode = "";
        try{
            GConnection loConn = DbConnection.doConnect(application);
            lsNextCode = MiscUtil.getNextCode("Mobile_Update_Request", "sTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsNextCode;
    }

    public void saveCollectionDetail(JSONArray faJson) throws Exception {
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

            lsSQL = "SELECT dModified FROM LR_DCP_Collection_Detail " +
                    "WHERE sTransNox = "+ SQLUtil.toSQL(loJson.getString("sTransNox"))+" " +
                    "AND nEntryNox = "+ SQLUtil.toSQL(loJson.getString("nEntryNox"));
            loRS = loConn.executeQuery(lsSQL);

            lsSQL = "";

            if(!loRS.next()){

                if("1".equalsIgnoreCase(loJson.getString("cRecdStat"))){
                    lsSQL = "INSERT INTO LR_DCP_Collection_Detail" +
                            "(sTransNox, " +
                            "nEntryNox, " +
                            "sAcctNmbr, " +
                            "xFullName, " +
                            "sPRNoxxxx, " +
                            "nTranAmtx, " +
                            "nDiscount, " +
                            "nOthersxx, " +
                            "sRemarksx, " +
                            "dPromised, " +
                            "sRemCodex, " +
                            "cTranType, " +
                            "nTranTotl, " +
                            "sReferNox, " +
                            "cPaymForm, " +
                            "cIsDCPxxx, " +
                            "sMobileNo, " +
                            "sHouseNox, " +
                            "sAddressx, " +
                            "sBrgyName, " +
                            "sTownName, " +
                            "nAmtDuexx, " +
                            "cApntUnit, " +
                            "dDueDatex, " +
                            "nLongitud, " +
                            "nLatitude, " +
                            "sClientID, " +
                            "sSerialID, " +
                            "sSerialNo, " +
                            "cSendStat, " +
                            "cSendStat, " +
                            "dModified) " +
                            "VALUES(" +
                            ""+SQLUtil.toSQL(loJson.getString("sTransNox"))+", " +
                            ""+SQLUtil.toSQL(loJson.getString("nEntryNox"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("sAcctNmbr"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("xFullName"))+", " +
                            ""+SQLUtil.toSQL(loJson.getString("sPRNoxxxx"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("nTranAmtx"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("nDiscount"))+", " +
                            ""+SQLUtil.toSQL(loJson.getString("nOthersxx"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("sRemarksx"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("dPromised"))+", " +
                            ""+SQLUtil.toSQL(loJson.getString("sRemCodex"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("cTranType"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("nTranTotl"))+", " +
                            ""+SQLUtil.toSQL(loJson.getString("sReferNox"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("cPaymForm"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("cIsDCPxxx"))+", " +
                            ""+SQLUtil.toSQL(loJson.getString("sMobileNo"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("sHouseNox"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("sAddressx"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("sBrgyName"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("sTownName"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("nAmtDuexx"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("cApntUnit"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("dDueDatex"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("nLongitud"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("nLatitude"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("sClientID"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("sSerialID"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("sSerialNo"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("cSendStat"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("cSendStat"))+"," +
                            ""+SQLUtil.toSQL(loJson.getString("dModified"))+")";
                }
            } else {
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                if(!ldDate1.equals(ldDate2)){
                    lsSQL = "UPDATE LR_DCP_Collection_Detail SET " +
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
}
