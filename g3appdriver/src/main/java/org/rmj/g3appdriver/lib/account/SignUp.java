package org.rmj.g3appdriver.lib.account;

import android.content.Context;
import android.os.Build;
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

public class SignUp {
    private String dialog_result = "";

    private String message = "";
    private String imei;
    private String productid;
    private String clientid;
    private String token;

    private Context ctx;
    private UserContactInfo telephony;
    private AppData appData;

    public SignUp(Context sctx, String simei, String sproductid, String sclientid, String stoken){
        this.ctx = sctx;
        this.imei = simei;
        this.productid = sproductid;
        this.clientid = sclientid;
        this.token = stoken;
        this.telephony = new UserContactInfo(ctx);
        this.appData = AppData.getInstance(ctx);
    }

    //result for dialog
    public String getResult(){
        return dialog_result;
    }

    //getting message
    public String getMessage(){
        return message;
    }

    public void signup(final String name, final String mail, final String pswd, final String mobile){

        String sURL = "https://restgk.guanzongroup.com.ph/security/signup.php";
        Calendar calendar = Calendar.getInstance();

        Map<String, String> headers =new HashMap<String, String>();

        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("g-api-id", productid);
        headers.put("g-api-imei", imei);
        headers.put("g-api-mobile", telephony.getMobileNumber());
        headers.put("g-api-model", Build.MODEL);
        headers.put("g-api-user", "");
        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
        String hash_toLower = SecUtil.md5Hex(headers.get("g-api-imei") + headers.get("g-api-key"));
        hash_toLower = hash_toLower.toLowerCase();

        headers.put("g-api-hash", hash_toLower);
        headers.put("g-api-client", clientid);
        headers.put("g-api-user", "");
        headers.put("g-api-log", "");
        headers.put("g-api-token", token);

        Log.d(TAG, "API-HASH: " + headers.get("g-api-hash"));


        //Create the parameters needed by the API
        JSONObject param = new JSONObject();
        try {
            param.put("name", name);
            param.put("mail", mail);
            param.put("pswd", pswd);
            param.put("mobile", mobile);
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
                dialog_result = "error";
            }else{
                message = "Authorization Error " +  System.getProperty("store.error.info") + ": Please make sure the internet connection is stable.";
                dialog_result = "error";
            }
        }else{
            try {
                JSONObject jObj = new JSONObject(response);
                Log.d(TAG, "Returned Value: " + response);
                String result = jObj.getString("result");

                if(result.equals("success")){
                    message = "Success";

                    String userid = jObj.getString("userid");
                    Log.d(TAG, "Returned Value: " + userid);
                    //DO SOMTHING HERE...
                    appData.saveMobileNumberToLocal(mobile, mail);
                }else{
                    Log.d(TAG, "Else Error: " + response);
                    JSONObject jObj_err =  new JSONObject(jObj.getString("error"));
                    String err_msg = jObj_err.getString("message");
                    message = err_msg;

                }
            } catch (JSONException e) {
                Log.d(TAG, "Catch: Json Result : " + response);
                message = "Catch Error";

            }
        }
    }
}
