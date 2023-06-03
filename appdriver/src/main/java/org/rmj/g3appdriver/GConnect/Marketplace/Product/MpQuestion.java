package org.rmj.g3appdriver.GConnect.Marketplace.Product;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import org.rmj.g3appdriver.GConnect.Account.ClientSession;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.Marketplace.Product.pojo.ProductInquiry;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DProduct;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class MpQuestion {
    private static final String TAG = MpQuestion.class.getSimpleName();

    private final Application instance;
    private final DProduct poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;
    private final ClientSession poSession;

    private String message;

    public MpQuestion(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).prodctDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
        this.poSession = ClientSession.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public List<ProductInquiry> GetProductInquiries(String fsVal){
        try{
            JSONObject params = new JSONObject();
            params.put("sListIDxx", fsVal);

            String lsResponse = WebClient.sendRequest(
                    poApi.getQuestionsAndAnswersAPI(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            List<ProductInquiry> loInquiries = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++) {
                JSONObject loJson = laJson.getJSONObject(x);
                loInquiries.add(new ProductInquiry(
                        loJson.getString("sUserName"),
                        loJson.getString("dCreatedx"),
                        loJson.getString("sQuestion"),
                        loJson.getString("sReplyxxx")));
            }

            return loInquiries;
        }catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public boolean SendProductInquiry(String ListID, String Question){
        try{
            JSONObject params = new JSONObject();
            params.put("sListngID", ListID);
            params.put("sQuestion", Question);
            params.put("sCreatedx", poSession.getUserID());
            params.put("dCreatedx", AppConstants.DATE_MODIFIED());

            String lsResponse = WebClient.sendRequest(
                    poApi.getSubmitInquiryAPI(),
                    params.toString(),
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

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
