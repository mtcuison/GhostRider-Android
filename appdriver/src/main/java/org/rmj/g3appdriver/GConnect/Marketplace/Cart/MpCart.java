package org.rmj.g3appdriver.GConnect.Marketplace.Cart;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GConnect.Account.ClientSession;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DItemCart;
import org.rmj.g3appdriver.GConnect.room.Entities.EItemCart;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConstants;

import java.util.Date;

public class MpCart {
    private static final String TAG = MpCart.class.getSimpleName();

    private final Application instance;
    private final DItemCart poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;
    private final ClientSession poSession;

    private String message;

    public MpCart(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).itemCartDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
        this.poSession = ClientSession.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean ImportMarketPlaceItemCart(){
        try{
            JSONObject params = new JSONObject();
            if(poDao.CheckIfCartHasRecord() > 0){
                Log.d(TAG, "Has local record...");
                params.put("dTimeStmp", poDao.GetLatestCartTimeStamp());
            }

            String lsResponse = WebClient.sendRequest(
                    poApi.getImportCartItems(),
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

            JSONArray jaDetail = loResponse.getJSONArray("detail");
            for(int x = 0; x < jaDetail.length(); x++){
                JSONObject loJson = jaDetail.getJSONObject(x);

                EItemCart loDetail = poDao.GetItemCart(loJson.getString("sListngID"));

                if(loDetail == null){
                    EItemCart loInfo = new EItemCart();
                    loInfo.setUserIDxx(loJson.getString("sUserIDxx"));
                    loInfo.setListIDxx(loJson.getString("sListngID"));
                    loInfo.setQuantity(loJson.getInt("nQuantity"));
                    loInfo.setAvlQtyxx(loJson.getInt("nAvlQtyxx"));
                    loInfo.setCreatedx(loJson.getString("dCreatedx"));
                    loInfo.setTranStat(loJson.getString("cTranStat"));
                    loInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.SaveItemInfo(loInfo);
                } else {

                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setUserIDxx(loJson.getString("sUserIDxx"));
                        loDetail.setListIDxx(loJson.getString("sListngID"));
                        loDetail.setQuantity(loJson.getInt("nQuantity"));
                        loDetail.setAvlQtyxx(loJson.getInt("nAvlQtyxx"));
                        loDetail.setCreatedx(loJson.getString("dCreatedx"));
                        loDetail.setTranStat(loJson.getString("cTranStat"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.UpdateItemInfo(loDetail);
                    }
                }
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean AddToCart(String ListngID, int Quantity){
        try{
            if(Quantity <= 0){
                message = "Invalid Quantity. Please select a quantity greater than zero to proceed.";
                return false;
            }

            if(poSession.getUserID().isEmpty()){
                message = "Account Login Required. Please log in to your account to continue.";
                return false;
            }

//            if(poSession.getVerificationStatus() == 0){
//                message = "Incomplete Account Setup. Please complete your account setup to proceed.";
//                return false;
//            }

            Log.d(TAG, "Validating quantity...");
            if(!ValidateItemQuantity(ListngID, Quantity)) {
                return false;
            }

            Thread.sleep(1000);

            EItemCart loDetail = poDao.CheckIFItemExist(ListngID);
            if(loDetail != null){
                int lnQty = poDao.CheckIFItemExist(ListngID).getQuantity();
                Quantity = lnQty + Quantity;
            }

            JSONObject params = new JSONObject();
            params.put("sListngID", ListngID);
            params.put("nQuantity", Quantity);

            String lsResponse = WebClient.sendRequest(
                    poApi.getAddToCartAPI(),
                    params.toString(),
                    poHeaders.getHeaders());

            if (lsResponse == null) {
                message = SERVER_NO_RESPONSE;
                return false;
            }

            Log.d(TAG, "Add to cart result: " + lsResponse);
            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                Log.e(TAG, message);
                return false;
            }

            EItemCart loItem = new EItemCart();
            loItem.setUserIDxx(poSession.getUserID());
            loItem.setListIDxx(ListngID);
            loItem.setQuantity(Quantity);
            loItem.setTranStat("0");
            loItem.setCreatedx(AppConstants.DATE_MODIFIED());
            loItem.setTimeStmp(AppConstants.DATE_MODIFIED());
            if(poDao.CheckIFItemExist(ListngID) == null){
                poDao.SaveItemInfo(loItem);
            } else {
                poDao.UpdateItem(ListngID, Quantity);
            }

            Log.d(TAG, "Added to cart");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    private boolean ValidateItemQuantity(String fsLstngID, int fnQuantity) throws Exception{

        JSONObject params = new JSONObject();
        params.put("bsearch", false);
        params.put("id", fsLstngID);

        String lsResponse = WebClient.sendRequest(
                poApi.getImportProducts(),
                params.toString(),
                poHeaders.getHeaders());

        if(lsResponse == null){
            message = SERVER_NO_RESPONSE;
            return false;
        }

        Log.d(TAG, "Quantity validation result: " + lsResponse);
        JSONObject loResponse = new JSONObject(lsResponse);
        String lsResult = loResponse.getString("result");
        if(lsResult.equalsIgnoreCase("error")){
            JSONObject loError = loResponse.getJSONObject("error");
            message = getErrorMessage(loError);
            return false;
        }

        JSONArray laDetail = loResponse.getJSONArray("detail");
        JSONObject loDetail = laDetail.getJSONObject(0);
        int lnQuantity = loDetail.getInt("nQtyOnHnd");
        String nTotalQty = loDetail.getString("nTotalQty");
        String nQtyOnHnd = loDetail.getString("nQtyOnHnd");
        String nResvOrdr = loDetail.getString("nResvOrdr");
        String nSoldQtyx = loDetail.getString("nSoldQtyx");
        String nUnitPrce = loDetail.getString("nUnitPrce");
        String sListngID = loDetail.getString("sListngID");
        poDao.UpdateProdcutQuantity(sListngID, nTotalQty, nQtyOnHnd, nResvOrdr, nSoldQtyx, nUnitPrce);
        if(lnQuantity >= fnQuantity){
            return true;
        } else {
            message = "Insufficient Quantity Available. The order quantity exceeds the available quantity on hand.";
            Log.e(TAG, message);
            return false;
        }
    }

    public boolean UpdateCartQuantity(String ListngID, int Quantity){
        try {
            if(Quantity <= 0){
                message = "Invalid Quantity. Please select a quantity greater than zero to proceed.";
                return false;
            }

            if(poSession.getUserID().isEmpty()){
                message = "Account Login Required. Please log in to your account to continue.";
                return false;
            }

            if(poSession.getVerificationStatus() == 0){
                message = "Incomplete Account Setup. Please complete your account setup to proceed.";
                return true;
            }

            if(!ValidateItemQuantity(ListngID, Quantity)) {
                return false;
            }

            Thread.sleep(1000);

            JSONObject params = new JSONObject();
            params.put("sListngID", ListngID);
            params.put("nQuantity", Quantity);

            String lsResponse = WebClient.sendRequest(
                    poApi.getAddToCartAPI(),
                    params.toString(),
                    poHeaders.getHeaders());

            if (lsResponse == null) {
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            EItemCart loItem = new EItemCart();
            loItem.setUserIDxx(poSession.getUserID());
            loItem.setListIDxx(ListngID);
            loItem.setQuantity(Quantity);
            loItem.setTranStat("0");
            loItem.setCreatedx(AppConstants.DATE_MODIFIED());
            loItem.setTimeStmp(AppConstants.DATE_MODIFIED());
            if(poDao.CheckIFItemExist(ListngID) == null){
                poDao.SaveItemInfo(loItem);
            } else {
                poDao.UpdateItem(ListngID, Quantity);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean RemoveCartItem(String fsLstngID){
        try{
            JSONObject params = new JSONObject();
            params.put("sListngID", fsLstngID);

            String lsResponse = WebClient.sendRequest(
                    poApi.getRemoveCartItemAPI(),
                    params.toString(),
                    poHeaders.getHeaders());

            if (lsResponse == null) {
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            poDao.DeleteCartItem(fsLstngID);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean ForCheckOut(String fsLstngID){
        try {
            poDao.UpdateForCheckOut(fsLstngID);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean RemoveForCheckOut(String fsLstngID){
        try {
            poDao.RemoveForCheckOut(fsLstngID);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean CheckCartItemsForCheckOut(){
        try{
            int lnCountxx = poDao.CheckCartItemsForOrder();
            if(lnCountxx > 0){
                return true;
            } else {
                message = "No Items Selected. Please select items to proceed with the checkout.";
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public LiveData<Integer> GetCartItemCount(){
        return poDao.GetCartItemCount();
    }

    public LiveData<Integer> GetMartketplaceCartItemCount(){
        return poDao.GetMartketplaceCartItemCount();
    }
}
