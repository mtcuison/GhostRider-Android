package org.rmj.g3appdriver.GConnect.Marketplace.Payment;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Account.ClientSession;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;

public class MpPayment {
    private static final String TAG = MpPayment.class.getSimpleName();

    private final Application instance;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;
    private final ClientSession poSession;

    private String message;

    public MpPayment(Application instance) {
        this.instance = instance;
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
        this.poSession = ClientSession.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean CashOnDelivery(String TransNo){
        try{
            JSONObject param = new JSONObject();
            param.put("sTransNox", TransNo);
            param.put("sTermCode", "COD");
            param.put("sReferNox", "");

            String lsResponse = WebClient.sendRequest(
                    poApi.getOrderPaymentAPI(),
                    param.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            Thread.sleep(1000);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean GCashPayment(String TransNo, String ReferNo){
        try{
            JSONObject param = new JSONObject();
            param.put("sTransNox", TransNo);
            param.put("sTermCode", "GCASH");
            param.put("sReferNox", ReferNo);

            String lsResponse = WebClient.sendRequest(
                    poApi.getOrderPaymentAPI(),
                    param.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            Thread.sleep(1000);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean MayaPayment(String TransNo, String ReferNo){
        try{
            JSONObject param = new JSONObject();
            param.put("sTransNox", TransNo);
            param.put("sTermCode", "MAYA");
            param.put("sReferNox", ReferNo);

            String lsResponse = WebClient.sendRequest(
                    poApi.getOrderPaymentAPI(),
                    param.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            Thread.sleep(1000);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
