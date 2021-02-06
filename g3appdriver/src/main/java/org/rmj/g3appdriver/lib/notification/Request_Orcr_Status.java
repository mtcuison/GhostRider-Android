package org.rmj.g3appdriver.lib.notification;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
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
 * Created by Erika Jane on 1/30/2019.
 */

public class Request_Orcr_Status {
    private final String TABLE_NAME = "MC_Serial_Registration";

    private String message = "";
    private String imei;
    private String productid;
    private String userid;
    private String token;
    private Context ctx;

    public Request_Orcr_Status(Context sctx, String simei, String sproductid, String suserid, String stoken){
        this.ctx = sctx;
        this.imei = simei;
        this.productid = sproductid;
        this.userid = suserid;
        this.token = stoken;

        Log.d(TAG,"Headers: IMEI-" + imei +" PRODUCTID-"+productid+" USERID-"+userid+" TOKEN-"+token);
    }

    public void reqst_orcr_status(final String card_no){

        String sURL = "https://restgk.guanzongroup.com.ph/integsys/registration/reqst_orcr_status.php";
        Calendar calendar = Calendar.getInstance();

        Map<String, String> headers =new HashMap<String, String>();

        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", productid);
        headers.put("g-api-imei", imei);
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();
        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-user", userid);
        headers.put("g-api-token", token);

        //Create the parameters needed by the API
        String card_id = SecUtil.md5Hex(headers.get("g-api-imei") + card_no);
        card_id = card_id.toLowerCase();
        Log.d(TAG, "Card ID: " + card_id);

        JSONObject param = new JSONObject();
        try {
            param.put("card_no", card_no);
            param.put("card_id", card_id);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;

        String response = null;
        try {
            Log.d(TAG, " URL ACTIVATION: " + sURL);
            response = WebClient.httpsPostJSon(sURL, param.toString(), (HashMap<String, String>) headers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null){
            System.out.println("HTTP Error detected: " + System.getProperty("store.error.info"));
            if(System.getProperty("store.error.info").equals("404")){
                message = "Authorization Error " + System.getProperty("store.error.info") + ": Please make sure internet is stable. ";
            }else{
                message = "Authorization Error " +  System.getProperty("store.error.info") + ": Please make sure the internet connection is stable.";
            }
        }else{
            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                if(result.equals("success")){
                    message = "Success";
                    Log.d(TAG, "Returned Value: " + response);

                    //DO SOMTHING HERE...
                    insertOrcrStatus(jObj);

                }else{
                    Log.d(TAG, "Else: Json Result : " + response);
                    message = "Else Error";
                }
            } catch (JSONException e) {
                Log.d(TAG, "Catch: Json Result : " + response);
                message = "Catch Error";

            }
        }
    }

    public boolean insertOrcrStatus(JSONObject foValue){
        AppData loAppData = AppData.getInstance(ctx);

        String lsSQL;

        try{
            JSONArray storesArray = foValue.getJSONArray("detail");
            Log.d(TAG, "Detail Array: " + storesArray);
            Log.d(TAG, "Size of Json: " + storesArray.length());

            for(int i = 0; i < storesArray.length(); i++){
                JSONObject store = storesArray.getJSONObject(i);

                String engine = store.getString("engine");
                String frame = store.getString("frame");
                String location = store.getString("location");
                String status = store.getString("status");
                String original = store.getString("original");
                String lstupdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                lsSQL = "INSERT INTO " + TABLE_NAME +" ( " +
                        "  sEngineNo" +
                        ", sFrameNox" +
                        ", sLocation" +
                        ", sStatusxx" +
                        ", isOriginl" +
                        ", dLastUpdt)" +
                        "  VALUES (" +
                        SQLUtil.toSQL(engine) +
                        ", " + SQLUtil.toSQL(frame) +
                        ", " + SQLUtil.toSQL(location) +
                        ", " + SQLUtil.toSQL(status) +
                        ", " + SQLUtil.toSQL(original) +
                        ", " + SQLUtil.toSQL(lstupdate) +
                        ")";
                loAppData.getWritableDb().beginTransaction();
                loAppData.getWritableDb().execSQL(lsSQL);
                Log.d(TAG, " Action Created: Insert");
                loAppData.getWritableDb().setTransactionSuccessful();

            }
        }catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        } finally {
            loAppData.getWritableDb().endTransaction();
        }
        return true;
    }
}
