package org.rmj.g3appdriver.dev;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.StringHelper;

import java.util.Calendar;

public class AppData extends SQLiteOpenHelper {
    private static final String TAG = AppData.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static AppData instance;

    private static final String DATABASE_NAME = "GGC_ISysDBF.db";

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private SQLiteDatabase db;

    public static synchronized AppData getInstance(Context context){
        mContext = context;
        if(instance == null){
            instance = new AppData(context);
        }
        return instance;
    }

    private AppData(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS App_Token_Info (sAppToken varchar)");

        db.execSQL("CREATE TABLE IF NOT EXISTS Client_Info_Master(" +
                " sUserIDxx varchar" +
                ", sEmailAdd varchar" +
                ", sUserName varchar" +
                ", dLoginxxx varchar" +
                ", sMobileNo varchar" +
                ", dDateMmbr varchar)");

        db.execSQL("CREATE TABLE IF NOT EXISTS User_Info_Master(" +
                "  sClientID varchar" +
                ", sBranchCD varchar" +
                ", sBranchNm varchar" +
                ", sLogNoxxx varchar" +
                ", sUserIDxx varchar" +
                ", sEmailAdd varchar" +
                ", sUserName varchar" +
                ", nUserLevl varchar" +
                ", sDeptIDxx varchar" +
                ", sPositnID varchar" +
                ", sEmpLevID varchar" +
                ", cAllowUpd varchar" +
                ", dLoginxxx varchar" +
                ", sMobileNo varchar" +
                ", dSessionx datetime)");
        Log.d(TAG, "Local database tables has been created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS App_Token_Info");
        db.execSQL("DROP TABLE IF EXISTS Client_Info_Master");
        db.execSQL("DROP TABLE IF EXISTS User_Info_Master");
        Log.e(TAG, "Local database tables has been updated.");
    }

    public void setAppToken(String FirebaseToken){
        db = this.getWritableDb();
        try{
            db.beginTransaction();
            String lsSql = "INSERT INTO App_Token_Info(sAppToken) VALUES (?)";
            SQLiteStatement loSql = db.compileStatement(lsSql);
            loSql.bindString(1, FirebaseToken);
            loSql.execute();
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public String getAppToken(){
        db = this.getReadableDb();
        String lsToken = "";
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM App_Token_Info", null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                lsToken = cursor.getString(cursor.getColumnIndex("sAppToken"));
                cursor.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsToken;
    }

    /**
     *
     * @return get the current instance of local database handler
     * to select values from the selected table inside the database.
     */
    public SQLiteDatabase getReadableDb(){
        if(db == null) {
            db = this.getReadableDatabase();
        }
        return db;
    }

    /**
     *
     * @return get the current instance of local database handler
     *  to update data or insert new values to selected tables
     *  surround try catch block or throw exception.
     *  to execute query call beginTransaction and setTransactionSuccessful
     *  and call endTransaction on finally block to end transaction.
     */
    public SQLiteDatabase getWritableDb(){
        if(db == null) {
            db = this.getWritableDatabase();
        }
        return db;
    }

    /**
     *
     * @param FieldName selected column of information inside the table
     *                  base on which application is using this library.
     *                  - GuanzonApp for Clients
     *                  - Telecom/IntegSys for Employees.
     *
     * @return selected information inside the column.
     */
    private String getUserInfo(String FieldName){
        AppConfigPreference loPref = AppConfigPreference.getInstance(mContext);
        if(loPref.ProducID().equalsIgnoreCase("GuanzonApp")){
            return getClientInfo(FieldName);
        } else {
            return getEmployeeInfo(FieldName);
        }
    }

    /**
     *
     * @param sFieldNm selected column of information inside
     *                 the table of User_Info_Master
     *
     * @return selected information inside the column
     */
    private String getEmployeeInfo(String sFieldNm){
        String lsInfo = "";
        try{
            db = this.getReadableDatabase();
            @SuppressLint("Recycle") Cursor loCursor = db.rawQuery("SELECT * FROM User_Info_Master", null);
            if(loCursor.getCount() > 0) {
                loCursor.moveToFirst();
                lsInfo = loCursor.getString(loCursor.getColumnIndex(sFieldNm));
                loCursor.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsInfo;
    }

    /**
     *
     * @param sFieldNm selected column of information inside
     *                 the table of Client_Info_Master
     *
     * @return selected information inside the column
     */
    private String getClientInfo(String sFieldNm){
        String lsInfo = "";
        try{
            db = this.getReadableDatabase();
            Cursor loCursor = db.rawQuery("SELECT * FROM Client_Info_Master", null);
            loCursor.moveToFirst();
            lsInfo = loCursor.getString(loCursor.getColumnIndex(sFieldNm));
            loCursor.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsInfo;
    }

    /**
     *
     * @return current id of the user employee/client.
     */
    public String getClientID(){
        return getUserInfo("sClientID");
    }

    /**
     *
     * @return current branch code of the user
     *
     *          NOTE: For employees only.
     */
    public String getBranchCode(){
        return getUserInfo("sBranchCD");
    }

    /**
     *
     * @return current branch name of the user
     *
     *          NOTE: For employees only.
     */
    public String getBranchName(){
        return getUserInfo("sBranchNm");
    }

    /**
     *
     * @return current branch code of the user
     *
     *          NOTE: For employees only.
     */
    public String getLogNumber(){
        return getUserInfo("sLogNoxxx");
    }

    /**
     *
     * @return current Log number of the user
     *          this info is use for validation on
     *          requesting/sending data to server.
     *
     *          NOTE: For employees only.
     */
    public String getUserID(){
        return getUserInfo("sUserIDxx");
    }

    /**
     *
     * @return email of the user employee/client.
     */
    public String getEmailAddress(){
        return getUserInfo("sEmailAdd");
    }

    /**
     *
     * @return username of the user employee/client.
     */
    public String getUserName(){
        return getUserInfo("sUserName");
    }


    /**
     *
     * @return current user level of the user
     *          this info is use for validation on
     *          requesting/sending data to server.
     *
     *          NOTE: For employees only.
     */
    public String getUserLevel(){
        return getUserInfo("nUserLevl");
    }

    /**
     *
     * @return department ID of the user
     *          this info is use for validation on
     *          requesting/sending data to server.
     *
     *          NOTE: For employees only.
     */
    public String getDepartmentID(){
        return getUserInfo("sDeptIDxx");
    }

    /**
     *
     * @return current position ID of the user
     *          this info is use for validation on
     *          requesting/sending data to server.
     *
     *          NOTE: For employees only.
     */
    public String getPositionID(){
        return getUserInfo("sPositnID");
    }

    /**
     *
     * @return current employee level of the user
     *          this info is use for validation on
     *          requesting/sending data to server.
     *
     *          NOTE: For employees only.
     */
    public String getEmpLevelID(){
        return getUserInfo("sEmpLevID");
    }

    /**
     *
     * @return date login of the user employee/client.
     */
    public String getDateLogin(){
        return getUserInfo("dLoginxxx");
    }



    /**
     * Get Next Code
     *
     * @param fsBranchCd - branch code
     * @param fsTable - table name
     * @param fsField - field name to assign the code
     *
     * @return new generated transaction number from local.
     */
    public String GetNextCode(String fsBranchCd, String fsTable, String fsField){
        db = this.getReadableDatabase();
        String lsSQL;
        Calendar now = Calendar.getInstance();

        lsSQL = "SELECT " + fsField +
                " FROM " + fsTable +
                " ORDER BY " + fsField + " DESC" +
                " LIMIT 1";

        Cursor cQuery = db.rawQuery(lsSQL, null);

        int year = now.get(Calendar.YEAR);

        if (cQuery.getCount() == 0) {
            return fsBranchCd + String.valueOf(year).substring(2) +
                    StringHelper.prepad("1", 6, '0');
        }

        cQuery.moveToFirst();
        String lsCode = cQuery.getString(0);
        lsCode = lsCode.substring(6);

        int lnNextCode = Integer.parseInt(lsCode);
        lnNextCode = lnNextCode + 1;

        return fsBranchCd + String.valueOf(year).substring(2) +
                StringHelper.prepad(String.valueOf(lnNextCode), 6, '0');
    }

    public boolean isContactAndEmailExisted(String Email){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sMobileNo FROM Client_Info_Master WHERE sEmailAdd = '"+Email+"'",null);
        if (cursor.getCount()>0){
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void saveMobileNumberToLocal(String MobileNumber, String Email){
        db = this.getReadableDb();
        try {
            Cursor cursor = db.rawQuery("SELECT sMobileNo FROM Client_Info_Master WHERE sEmailAdd = '" + Email + "'", null);
            if (cursor.getCount() > 0) {
            } else {
                db = this.getWritableDatabase();
                db.beginTransaction();
                String[] bindArgs = {MobileNumber};
                db.execSQL("INSERT INTO Client_Info_Master (sMobileNo) VALUES (?)", bindArgs);
                db.setTransactionSuccessful();
            }
            cursor.close();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void LogoutUser(){
        try{
            db = this.getWritableDatabase();
            db.beginTransaction();
            db.execSQL("DELETE FROM User_Info_Master");
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
