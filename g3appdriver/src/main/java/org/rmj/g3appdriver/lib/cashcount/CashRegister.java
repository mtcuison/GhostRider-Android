package org.rmj.g3appdriver.lib.cashcount;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.GRider.Http.HttpRequestUtil;
import org.rmj.g3appdriver.GRider.Http.RequestHeaders;

import java.util.HashMap;

public class CashRegister extends HttpRequestUtil {
    private static final String TAG = CashRegister.class.getSimpleName();
    private static final String URL_CASH_REGISTER = "https://restgk.guanzongroup.com.ph/integsys/cashcount/submit_cash_count.php";

    /**
     * Submit cashcount manager...
     * this provides the front classes to easily manipulate
     * this library class.*/
    public interface g3CashRegistrationManager{
        JSONObject setParameter();
        void onSendSuccessResult();
        void onSendErrorResult(String ErrorMessage);
    }

    private RequestHeaders headers;
    private g3CashRegistrationManager manager;
    private AppData appData;
    private JSONObject params;

    public void submitCashCount(Context context, g3CashRegistrationManager ParameterManager){
        this.headers = new RequestHeaders(context);
        ConnectionUtil connectionUtil = new ConnectionUtil(context);
        this.manager = ParameterManager;
        this.appData = AppData.getInstance(context);
        this.params = manager.setParameter();

        sendRequest(URL_CASH_REGISTER, new onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    Log.e(TAG, "Cash count headers has been set.");
                    return (HashMap) headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    Log.e(TAG, "Cash count parameters has been set.");
                    saveCashCountDetail();
                    return manager.setParameter();
                }

                @Override
                public void onResponse(JSONObject obj) {
                    try {
                        updateLocalCashCount(obj);
                        manager.onSendSuccessResult();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String message) {
                    manager.onSendErrorResult(message);
                }
            });
    }

    private void saveCashCountDetail(){
        try{
            appData.getWritableDb().beginTransaction();
            appData.getWritableDb().execSQL("INSERT INTO Cash_Count_Master ( " +
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
                            "sReqstdBy, " +
                            "dModified) " +
                            " VALUES (" +
                            " '" + params.getString("transnox") + "', " +
                            " '"+ params.getString("trandate") + "', " +
                            " '"+ params.getString("cn0001cx") + "', " +
                            " '"+ params.getString("cn0005cx") + "', " +
                            " '"+ params.getString("cn0025cx") + "', " +
                            " '"+ params.getString("cn0001px") + "', " +
                            " '"+ params.getString("cn0005px") + "', " +
                            " '"+ params.getString("cn0010px") + "', " +
                            " '"+ params.getString("nte0020p") + "', " +
                            " '"+ params.getString("nte0050p") + "', " +
                            " '"+ params.getString("nte0100p") + "', " +
                            " '"+ params.getString("nte0200p") + "', " +
                            " '"+ params.getString("nte0500p") + "', " +
                            " '"+ params.getString("nte1000p") + "', " +
                            " '"+ params.getString("ornoxxxx") + "', " +
                            " '"+ params.getString("sinoxxxx") + "', " +
                            " '"+ params.getString("prnoxxxx") + "', " +
                            " '"+ params.getString("crnoxxxx") + "', " +
                            " '"+ params.getString("entrytme") + "', " +
                            " '"+ params.getString("reqstdid") + "', " +
                            " '"+ params.getString("entrytme") + "')");
            appData.getWritableDb().setTransactionSuccessful();
        }catch (SQLiteException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            appData.getWritableDb().endTransaction();
        }
    }

    private void updateLocalCashCount(JSONObject obj) throws JSONException{
        try {
            String localTransnox = manager.setParameter().getString("transnox");
            String sql = "UPDATE Cash_Count_Master SET sTransNox = '" + obj.getString("realnox") + "', " +
                    "dReceived = '" + obj.getString("received") + "' WHERE sTransNox = '" + localTransnox + "'";
            appData.getWritableDb().beginTransaction();
            appData.getWritableDb().execSQL(sql);
            appData.getWritableDb().setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            appData.getWritableDb().endTransaction();
        }
    }
}
