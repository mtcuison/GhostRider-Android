//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.rmj.g3appdriver.lib.account;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.etc.SQLiteHandler;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.GRider.Http.WebClient;

public class SignIn {
    private final String TABLE_NAME = "Client_Info_Master";
    private String dialog_result = "";
    private String message = "";
    private String imei;
    private String productid;
    private String userid;
    private String token;
    private String mobileno;
    private String mblmodel;
    Context ctx;

    public SignIn(Context sctx, String simei, String sproductid, String suserid, String stoken, String mobileno, String mblmodel) {
        this.ctx = sctx;
        this.imei = simei;
        this.productid = sproductid;
        this.userid = suserid;
        this.token = stoken;
        this.mobileno = mobileno;
        this.mblmodel = mblmodel;
        Log.d(SQLiteHandler.TAG, "Sign In Headers: IMEI-" + this.imei + " PRODUCTID-" + this.productid + " USERID-" + this.userid + " TOKEN-" + this.token + " MOBILENO-" + this.mobileno + "MODEL-" + this.mblmodel);
    }

    public String getResult() {
        return this.dialog_result;
    }

    public String getMessage() {
        return this.message;
    }

    public void checkLogin(String user, String pswd) {
        String sURL = "https://restgk.guanzongroup.com.ph/security/signin.php";
        Calendar calendar = Calendar.getInstance();
        Map<String, String> headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", this.productid);
        headers.put("g-api-imei", this.imei);
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();
        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-user", this.userid);
        headers.put("g-api-token", this.token);
        headers.put("g-api-mobile", this.mobileno);
        headers.put("g-api-model", this.mblmodel);
        JSONObject param = new JSONObject();

        try {
            param.put("user", user);
            param.put("pswd", pswd);
        } catch (JSONException var14) {
            var14.printStackTrace();
        }

        JSONObject json_obj = null;
        String response = null;

        try {
            Log.d(SQLiteHandler.TAG, " URL ACTIVATION: " + sURL);
            response = WebClient.httpsPostJSon(sURL, param.toString(), (HashMap)headers);
        } catch (IOException var13) {
            var13.printStackTrace();
        }

        if (response == null) {
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
            this.message = "Http Error Detected!";
        } else {
            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");
                if (result.equals("success")) {
                    this.message = "Success";
                    this.insertClientInfo(jObj);
                    Log.d(SQLiteHandler.TAG, "Returned Value: " + response);
                } else {
                    Log.d(SQLiteHandler.TAG, "Else: Json Result : " + response);
                    this.message = "Invalid Credentials";
                }
            } catch (JSONException var12) {
                Log.d(SQLiteHandler.TAG, "Catch: Json Result : " + response);
                this.message = "Catch Error";
            }
        }
    }

    public boolean insertClientInfo(JSONObject foValue) {
        AppData loAppData = AppData.getInstance(ctx);

        try {
            String sUserIDxx = foValue.getString("sUserIDxx");
            String sEmailAdd = foValue.getString("sEmailAdd");
            String sUserName = foValue.getString("sUserName");
            String dLoginxxx = (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())).format(new Date());
            Cursor loCursor = loAppData.getReadableDb().rawQuery("SELECT * FROM Client_Info_Master", null);
            int lnRow = loCursor.getCount();
            String lsSQL;
            loAppData.getWritableDb().beginTransaction();
            if (lnRow <= 0) {
                lsSQL = "INSERT INTO Client_Info_Master (   sUserIDxx, sEmailAdd, sUserName, dLoginxxx)  VALUES (" + SQLUtil.toSQL(sUserIDxx) + ", " + SQLUtil.toSQL(sEmailAdd) + ", " + SQLUtil.toSQL(sUserName) + ", " + SQLUtil.toSQL(dLoginxxx) + ")";
                loAppData.getWritableDb().execSQL(lsSQL);
                Log.d(SQLiteHandler.TAG, " Action Created: Insert");
            } else {
                lsSQL = "UPDATE Client_Info_Master SET sUserIDxx = " + SQLUtil.toSQL(sUserIDxx) + ", sEmailAdd = " + SQLUtil.toSQL(sEmailAdd) + ", sUserName = " + SQLUtil.toSQL(sUserName) + ", dLoginxxx = " + SQLUtil.toSQL(dLoginxxx);
                loAppData.getWritableDb().execSQL(lsSQL);
                Log.d(SQLiteHandler.TAG, " Action Created: Update");
            }
            loAppData.getWritableDb().setTransactionSuccessful();

            return true;
        } catch (JSONException var10) {
            var10.printStackTrace();
            Log.d(SQLiteHandler.TAG, "Catch Error: " + var10.getMessage());
            this.message = "Error 102: Login Credentials Invalid";
            return false;
        } finally {
            loAppData.getWritableDb().endTransaction();
        }
    }
}
