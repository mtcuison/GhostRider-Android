package org.rmj.g3appdriver.lib.account;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.GRider.Http.WebClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.rmj.g3appdriver.etc.SQLiteHandler.TAG;

/**
 * Created by Erika Jane on 12/12/2018.
 */

public class Security {

    //security
    private String message;
    private String imei;
    private String clientid;
    private String productid;
    private String token;

    //database
    private AppData db;
    private UserContactInfo mobileNo;
    private String result = "";

    public Security(Context sctx, String simei, String sclientid, String sproductid, String stoken){
        this.imei = simei;
        this.clientid = sclientid;
        this.productid = sproductid;
        this.token = stoken;
        this.mobileNo = new UserContactInfo(sctx);
        this.db = AppData.getInstance(sctx);

        Log.d(TAG,"Headers: IMEI-" + imei +" CLIENTID-"+clientid+" PRODUCTID-"+productid+" TOKEN-"+token);
    }

    public String getMessage(){
        return message;
    }

    public void checkLogin(final String user, final String pswd){
        String sURL = "https://restgk.guanzongroup.com.ph/security/mlogin.php";
        Calendar calendar = Calendar.getInstance();
        Map<String, String> headers =new HashMap<String, String>();

        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");

        headers.put("g-api-id", productid);
        headers.put("g-api-imei", imei);
        headers.put("g-api-model", Build.MODEL);
        headers.put("g-api-mobile", mobileNo.getMobileNumber());

        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();

        headers.put("g-api-hash", hash_toLower);
        //CHANGA THIS BEFORE UPLOADING
        headers.put("g-api-client", clientid);
        //headers.put("g-api-client", "GTC_BC001");
        headers.put("g-api-user", "");
        headers.put("g-api-log", "");
        headers.put("g-api-token", token);

        Log.d(TAG, "API-HASH: " + headers.get("g-api-hash"));

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("user", user);
            param.put("pswd", pswd);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;

        String response = null;
        try {
            response = WebClient.httpsPostJSon(sURL, param.toString(), (HashMap<String, String>) headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
            System.exit(1);
        }else{
            try {
                Log.d(TAG, "Return Value from login : " + response);

                JSONObject jObj = new JSONObject(response);

                String json_result = jObj.getString("result");

                if(json_result.equals("success")){
                    insertClientInfo(jObj);
                    message = json_result;
                    Log.d(TAG, "Return From Login: " + response);
                }else{
                    JSONObject jObj_err =  new JSONObject(jObj.getString("error"));
                    String err_msg = jObj_err.getString("message");
                    message = err_msg;
                    Log.d(TAG, "Else Error: " + response);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "Catch Error: " + response);
                message = "Error 102: " + "Login Credentials Invalid";

            }
        }
    }

    private void insertClientInfo(JSONObject foValue){

        try{
            String sClientID = foValue.getString("sClientID");
            String sBranchCD = foValue.getString("sBranchCD");
            String sBranchNm = foValue.getString("sBranchNm");
            String sLogNoxxx = foValue.getString("sLogNoxxx");
            String sUserIDxx = foValue.getString("sUserIDxx");
            String sEmailAdd = foValue.getString("sEmailAdd");
            String sUserName = foValue.getString("sUserName");
            String nUserLevl = foValue.getString("nUserLevl");
            String sDeptIDxx = foValue.getString("sDeptIDxx");
            String sPositnID = foValue.getString("sPositnID");
            String sEmpLevID = foValue.getString("sEmpLevID");
            String cAllowUpd = foValue.getString("cAllowUpd");
            String dLoginxxx = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            String TABLE_NAME = "User_Info_Master";
            Cursor loCursor = db.getReadableDb().rawQuery("SELECT * FROM " + TABLE_NAME, null);
            int lnRow = loCursor.getCount();
            String lsSQL;

            db.getWritableDb().beginTransaction();
            if(lnRow <= 0){
                lsSQL = "INSERT INTO " + TABLE_NAME +" ( " +
                            "  sClientID" +
                            ", sBranchCD" +
                            ", sBranchNm" +
                            ", sLogNoxxx" +
                            ", sUserIDxx" +
                            ", sEmailAdd" +
                            ", sUserName" +
                            ", nUserLevl" +
                            ", sDeptIDxx" +
                            ", sPositnID" +
                            ", sEmpLevID" +
                            ", cAllowUpd" +
                            ", dLoginxxx" +
                            ", sMobileNo)" +
                        "  VALUES (" +
                               SQLUtil.toSQL(sClientID) +
                            ", " + SQLUtil.toSQL(sBranchCD) +
                            ", " + SQLUtil.toSQL(sBranchNm) +
                            ", " + SQLUtil.toSQL(sLogNoxxx) +
                            ", " + SQLUtil.toSQL(sUserIDxx) +
                            ", " + SQLUtil.toSQL(sEmailAdd) +
                            ", " + SQLUtil.toSQL(sUserName) +
                            ", " + SQLUtil.toSQL(nUserLevl) +
                            ", " + SQLUtil.toSQL(sDeptIDxx) +
                            ", " + SQLUtil.toSQL(sPositnID) +
                            ", " + SQLUtil.toSQL(sEmpLevID) +
                            ", " + SQLUtil.toSQL(cAllowUpd) +
                            ", " + SQLUtil.toSQL(dLoginxxx) +
                            ", '"+ mobileNo+"')";

                db.getWritableDb().execSQL(lsSQL);
                db.getWritableDb().setTransactionSuccessful();
                Log.d(TAG, " Action Created: Insert");
            } else{
                lsSQL = "UPDATE " + TABLE_NAME + " SET" +
                        "  sClientID = " + SQLUtil.toSQL(sClientID) +
                        ", sBranchCD = " + SQLUtil.toSQL(sBranchCD) +
                        ", sBranchNm = " + SQLUtil.toSQL(sBranchNm) +
                        ", sLogNoxxx = " + SQLUtil.toSQL(sLogNoxxx) +
                        ", sUserIDxx = " + SQLUtil.toSQL(sUserIDxx) +
                        ", sEmailAdd = " + SQLUtil.toSQL(sEmailAdd) +
                        ", sUserName = " + SQLUtil.toSQL(sUserName) +
                        ", nUserLevl = " + SQLUtil.toSQL(nUserLevl) +
                        ", sDeptIDxx = " + SQLUtil.toSQL(sDeptIDxx) +
                        ", sPositnID = " + SQLUtil.toSQL(sPositnID) +
                        ", sEmpLevID = " + SQLUtil.toSQL(sEmpLevID) +
                        ", cAllowUpd = " + SQLUtil.toSQL(cAllowUpd) +
                        ", dLoginxxx = " + SQLUtil.toSQL(dLoginxxx);

                db.getWritableDb().execSQL(lsSQL);
                db.getWritableDb().setTransactionSuccessful();
                Log.d(TAG, " Action Created: Update");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Catch Error: " + e.getMessage());
            message = "Error 102: " + "Login Credentials Invalid";
        } finally {
            db.getWritableDb().endTransaction();
        }
    }
}
