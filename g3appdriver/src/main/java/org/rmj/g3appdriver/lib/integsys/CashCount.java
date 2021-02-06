/*
    SYSTEM FLOW
        1. user will submit cash count using "createCashCount" method
            a. system will save the cash count to local database
            b. system will send the data to main office using "submitCashCount" method
        2. submitCashCount
            a. data will be sent to main office
                on success;
                    system will update the local database
                        sTransNox = returned transno by the server
                        dReceived = returned recieved date and time by the server
                on failure;
                    received date field will remain empty, and will be sent to main office
                    when the server is online or the user has connection;
                    call "checkCashCountUpload" method to upload the unposted records
        3. checkCashCountUpload
            a. will search for records with empty received date and send it to main office.
 */

package org.rmj.g3appdriver.lib.integsys;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.GRider.Http.WebClient;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.rmj.g3appdriver.etc.SQLiteHandler.TAG;

public class CashCount {
    private final String TABLE_NAME = "Cash_Count_Master";

    private String imei;
    private String clientid;
    private String productid;
    private String userid;
    private String logno;
    private String token;

    private String message = "";
    String empty_received = "";

    //external Class
    private AppData appData;

    public CashCount(Context sctx,
                     String simei,
                     String sclientid,
                     String sproductid,
                     String suserid,
                     String slogno,
                     String stoken){
        //headers
        this.imei = simei;
        this.clientid = sclientid;
        this.productid = sproductid;
        this.userid = suserid;
        this.logno = slogno;
        this.token = stoken;

        appData = AppData.getInstance(sctx);

        Log.d(TAG,"Headers: IMEI-" + imei +" CLIENTID-"+clientid+" PRODUCTID-"+productid+" USERID-"+userid+" LOGNO-"+logno+" TOKEN-"+token);
    }

    public String getMessage(){
        return message;
    }

    /**
     * Create cash count
     *
     * @param trandate trasaction date
     * @param cn0001cx coins 1 cent quantity
     * @param cn0005cx coins 5 cents quantity
     * @param cn0025cx coins 25 cents quantity
     * @param cn0001px coins 1 peso quantity
     * @param cn0005px coins 5 pesos quantity
     * @param cn0010px coins 10 pesos quantity
     * @param nte0020p paper bill 20 pesos quantity
     * @param nte0050p paper bill 50 pesos quantity
     * @param nte0100p paper bill 100 pesos quantity
     * @param nte0200p paper bill 200 pesos quantity
     * @param nte0500p paper bill 500 pesos quantity
     * @param nte1000p paper bill 1000 pesos quantity
     * @param ornoxxxx last OR number used for the day
     * @param sinoxxxx last SI number used for the day
     * @param prnoxxxx last PR number used for the day
     * @param crnoxxxx last CR number used for the day
     * @param entrytme entry time
     * @param reqstdid requesting officer employee id
     */
    public void createCashCount(String trandate, String cn0001cx, String cn0005cx, String cn0025cx, String cn0001px,
                                 String cn0005px, String cn0010px, String nte0020p, String nte0050p, String nte0100p,
                                 String nte0200p, String nte0500p, String nte1000p, String ornoxxxx, String sinoxxxx,
                                 String prnoxxxx, String crnoxxxx, String entrytme, String  received, String reqstdid){

        //the user must be pass the value for the next code since it is in the database;
        //  generate the next code
        String transnox = appData.GetNextCode(appData.getBranchCode(), TABLE_NAME, "sTransNox");

        //save the cash count to local database
        saveCashCount(transnox, trandate, cn0001cx, cn0005cx, cn0025cx,
                            cn0001px, cn0005px, cn0010px, nte0020p, nte0050p,
                            nte0100p, nte0200p, nte0500p, nte1000p, ornoxxxx,
                            sinoxxxx, prnoxxxx, crnoxxxx, entrytme, received,
                            reqstdid);

        //send the cash count to main office
        //submitCashCount(transnox, trandate, cn0001cx, cn0005cx, cn0025cx, cn0001px, cn0005px,
        //                cn0010px, nte0020p, nte0050p, nte0100p, nte0200p, nte0500p, nte1000p,
        //                ornoxxxx, sinoxxxx, prnoxxxx, crnoxxxx, entrytme, reqstdid);
    }


    public void submitCashCount(String transnox, String trandate, String cn0001cx, String cn0005cx, String cn0025cx,
                                String cn0001px, String cn0005px, String cn0010px, String nte0020p, String nte0050p,
                                String nte0100p, String nte0200p, String nte0500p, String nte1000p, String ornoxxxx,
                                String sinoxxxx, String prnoxxxx, String crnoxxxx, String entrytme, String reqstdid){
        Log.d(TAG,"PassedValue 1: " + transnox+ "\n" +
                        "PassedValue 2: " + trandate+ "\n" +
                        "PassedValue 3: " + cn0001cx+ "\n" +
                        "PassedValue 4: " + cn0005cx+ "\n" +
                        "PassedValue 5: " + cn0025cx+ "\n" +
                        "PassedValue 6: " + cn0001cx+ "\n" +
                        "PassedValue 7: " + cn0005px + "\n" +
                        "PassedValue 8: " + cn0010px+ "\n" +
                        "PassedValue 9: " + nte0020p+ "\n" +
                        "PassedValue 10: " + nte0050p+ "\n" +
                        "PassedValue 11: " + nte0100p+ "\n" +
                        "PassedValue 12: " + nte0200p+ "\n" +
                        "PassedValue 13: " + nte0500p+ "\n" +
                        "PassedValue 14: " + nte1000p+ "\n" +
                        "PassedValue 15: " + ornoxxxx+ "\n" +
                        "PassedValue 16: " + sinoxxxx+ "\n" +
                        "PassedValue 17: " + prnoxxxx+ "\n" +
                        "PassedValue 18: " + crnoxxxx+ "\n" +
                        "PassedValue 19: " + entrytme+ "\n" +
                        "PassedValue 20: " + reqstdid+ "\n");

        String sURL = "https://restgk.guanzongroup.com.ph/integsys/cashcount/submit_cash_count.php";

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
        headers.put("g-api-client", clientid);
        headers.put("g-api-user", userid);
        headers.put("g-api-log", logno);
        headers.put("g-api-token", token);

        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("transnox", transnox);
            param.put("trandate", trandate);
            param.put("cn0001cx", cn0001cx);
            param.put("cn0005cx", cn0005cx);
            param.put("cn0025cx", cn0025cx);
            param.put("cn0001px", cn0001px);
            param.put("cn0005px", cn0005px);
            param.put("cn0010px", cn0010px);
            param.put("nte0020p", nte0020p);
            param.put("nte0050p", nte0050p);
            param.put("nte0100p", nte0100p);
            param.put("nte0200p", nte0200p);
            param.put("nte0500p", nte0500p);
            param.put("nte1000p", nte1000p);
            param.put("ornoxxxx", ornoxxxx);
            param.put("sinoxxxx", sinoxxxx);
            param.put("prnoxxxx", prnoxxxx);
            param.put("crnoxxxx", crnoxxxx);
            param.put("entrytme", entrytme);
            param.put("reqstdid", reqstdid);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json_obj = null;
        String response = null;

        try {
            Log.d(TAG, " URL: " + sURL);
            response = WebClient.httpsPostJSon(sURL, param.toString(), (HashMap<String, String>) headers);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response == null){
            System.out.println("HTTP Error detected");
            message = "Http Error Detected";

        }else{
            try {
                JSONObject jObj = new JSONObject(response);
                String result = jObj.getString("result");

                message = response;

                if(result.equals("success")){
                    String realnox = jObj.getString("realnox");
                    String transno = jObj.getString("transno");
                    String received = jObj.getString("received");

                    //update the received date on the local database

                  /* String sql = "UPDATE " + TABLE_NAME + " SET" +
                           "  sTransNox = " + SQLUtil.toSQL(transno) +
                           ", dReceived = " + SQLUtil.toSQL(received) +
                           " WHERE sTransNox = " + SQLUtil.toSQL(realnox);*/
                   /* String sql = "UPDATE " + TABLE_NAME + " SET" +
                            "  sTransNox = " + transno +
                            ", dReceived = " + received+
                            " WHERE sTransNox = " + realnox;*/

                    String sql = "Update Cash_Count_Master Set sTransNox = '"+realnox+"', dReceived = '"+received+"'Where sTransNox = '"+transno+"'";
                    appData.getWritableDb().beginTransaction();
                    appData.getWritableDb().execSQL(sql);

                    Log.d(TAG, "Update Statement: " + sql);
                    appData.getWritableDb().setTransactionSuccessful();
                    message = "Transaction Completed";
                }else{
                    //internet enabled but no connection
                    Log.d(TAG, "Else: Json Result : " + response);
                    message = response;
                }
            } catch (JSONException e) {
                //internet disabled
                Log.d(TAG, "Catch: Json Result" + response);
            } finally {
                appData.getWritableDb().setTransactionSuccessful();
            }
        }
    }

    private void saveCashCount(String transnox, String trandate, String cn0001cx, String cn0005cx, String cn0025cx,
                              String cn0001px, String cn0005px, String cn0010px, String nte0020p, String nte0050p,
                              String nte0100p, String nte0200p, String nte0500p, String nte1000p, String ornoxxxx,
                              String sinoxxxx, String prnoxxxx, String crnoxxxx, String entrytme, String received,
                              String reqstdid){
        try {
            appData.getWritableDb().beginTransaction();
            appData.getWritableDb().execSQL("INSERT INTO " + TABLE_NAME + " ( " +
                            "sTransNox, " +
                            "dTransact, " +
                            "nCn0001cx, " +
                            "nCn0005cx, " +
                            "nCn0025cx, " +
                            "nCn0001px, " +
                            "nCn0005px, " +
                            "nCn0010px, " +
                            "nNte0020p, " +
                            "nNte0050p, " +
                            "nNte0100p, " +
                            "nNte0200p, " +
                            "nNte0500p, " +
                            "nNte1000p, " +
                            "sORNoxxxx, " +
                            "sSINoxxxx, " +
                            "sPRNoxxxx, " +
                            "sCRNoxxxx, " +
                            "dEntryDte, " +
                            "dReceived, " +
                            "sReqstdBy, " +
                            "sModified, " +
                            "dModified) " +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    new Object[]{
                            transnox,
                            trandate,
                            cn0001cx,
                            cn0005cx,
                            cn0025cx,
                            cn0001px,
                            cn0005px,
                            cn0010px,
                            nte0020p,
                            nte0050p,
                            nte0100p,
                            nte0200p,
                            nte0500p,
                            nte1000p,
                            ornoxxxx,
                            sinoxxxx,
                            prnoxxxx,
                            crnoxxxx,
                            entrytme,
                            received,
                            reqstdid,
                            userid,
                            entrytme //ask sir marlon if current date ang ilalagay or kasama din siya sa ireturn
                    });
            message = "Transaction Complete";
            appData.getWritableDb().setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
        } finally {
            appData.getWritableDb().endTransaction();
        }
    }

    public boolean checkCashCountUpload(){
        Cursor lnRow;
        lnRow = appData.getReadableDb().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE dReceived = '' ORDER BY dTransact", null);

        if (lnRow.getCount() > 0){
            try {
                while (lnRow.moveToNext()) {
                    //send the cash count to main office
                    submitCashCount(lnRow.getString(0), lnRow.getString(1), lnRow.getString(2), lnRow.getString(3),
                                    lnRow.getString(4), lnRow.getString(5), lnRow.getString(6), lnRow.getString(7),
                                    lnRow.getString(8), lnRow.getString(9), lnRow.getString(10), lnRow.getString(11),
                                    lnRow.getString(12), lnRow.getString(13), lnRow.getString(14), lnRow.getString(15),
                                    lnRow.getString(16), lnRow.getString(17), lnRow.getString(18), lnRow.getString(20));
                }
            } finally {
                lnRow.close();
            }
        }
        return true;
    }
}
