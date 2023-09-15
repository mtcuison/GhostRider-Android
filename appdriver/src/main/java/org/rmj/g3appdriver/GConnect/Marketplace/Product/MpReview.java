package org.rmj.g3appdriver.GConnect.Marketplace.Product;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Account.ClientSession;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.Marketplace.Product.pojo.OrderReview;
import org.rmj.g3appdriver.GConnect.Marketplace.Product.pojo.ProductReview;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DProduct;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class MpReview {
    private static final String TAG = MpReview.class.getSimpleName();

    private final Application instance;
    private final DProduct poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;
    private final ClientSession poSession;

    private String message;

    public MpReview(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).prodctDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
        this.poSession = ClientSession.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public List<ProductReview> GetProductRatings(String fsVal){
        try{
            JSONObject params = new JSONObject();
            params.put("sListIDxx", fsVal);
            String lsResponse = WebClient.sendRequest(
                    poApi.getImportReviewsAPI(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            Log.d(TAG, lsResponse);
            String lsResult = loResponse.getString("result");
            if(!lsResult.equalsIgnoreCase("success")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            List<ProductReview> loList = new ArrayList<>();
            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                ProductReview loReview = new ProductReview();
                loReview.setnEntryNox(loJson.getString("nEntryNox"));
                loReview.setnRatingxx(loJson.getString("nRatingxx"));
                loReview.setsUserName(loJson.getString("sUserName"));
                loReview.setdCreatedx(loJson.getString("dCreatedx"));
                loReview.setsRemarksx(loJson.getString("sRemarksx"));
                loReview.setsReplyxxx(loJson.getString("sReplyxxx"));
                loList.add(loReview);
            }

            return loList;
        }catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public boolean SendReview(OrderReview foVal){
        try{
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return false;
            }

            JSONObject params = new JSONObject();
            params.put("sTransNox", foVal.getOrderID());
            params.put("sListngID", foVal.getListID());
            params.put("nRatingxx", foVal.getRate());
            params.put("sRemarksx", foVal.getRemarks());
            params.put("sCreatedx", poSession.getUserID());
            params.put("dCreatedx", AppConstants.DATE_MODIFIED());

            String lsResponse = WebClient.sendRequest(
                    poApi.getSubmitReviewAPI(),
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
            message = getLocalMessage(e);
            return false;
        }
    }
}
