package org.rmj.g3appdriver.GConnect.Marketplace.Order;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Account.ClientSession;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DOrder;
import org.rmj.g3appdriver.GConnect.room.Entities.EItemCart;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConstants;

public class MpOrder {
    private static final String TAG = MpOrder.class.getSimpleName();

    private final Application instance;
    private final DOrder poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;
    private final ClientSession poSession;

    private String message;

    public MpOrder(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).ordersDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
        this.poSession = ClientSession.getInstance(instance);
    }

    public String getMessage(){
        return message;
    }

    public boolean BuyNow(String fsLstngID, int fnQuantity){
        try {
            poDao.RemoveItemFromCart();
            EItemCart loItem = new EItemCart();
            loItem.setUserIDxx(poSession.getUserID());
            loItem.setListIDxx(fsLstngID);
            loItem.setQuantity(fnQuantity);
            loItem.setBuyNowxx("1");
            loItem.setCheckOut("1");
            loItem.setCreatedx(AppConstants.DATE_MODIFIED());
            loItem.setTimeStmp(AppConstants.DATE_MODIFIED());
            poDao.SaveItemInfo(loItem);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean PlaceOrderBuyNow(){
        try{
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

//    public boolean PlaceOrder(){
//        try{
//            JSONArray jaDetail = new JSONArray();
////            for(int x = 0; x < foItemLst.size(); x++){
////                JSONObject joDetail = new JSONObject();
////                joDetail.put("sListngID", foItemLst.get(x).sListIDxx);
////                joDetail.put("nQuantity", foItemLst.get(x).nQuantity);
////                jaDetail.put(joDetail);
////            }
//
//            JSONObject params = new JSONObject();
//            params.put("cCartItem", 1); //0 - direct place order; 1 - place order of cart item
//            params.put("nFreightx", 100.00); //Freight charge
//
//            params.put("detail", jaDetail);
//
//            String lsResponse = WebClient.sendRequest(
//                    poApi.getMarketPlaceOrderAPI(),
//                    params.toString(),
//                    poHeaders.getHeaders());
//
//            if(lsResponse == null){
//                message = SERVER_NO_RESPONSE;
//                return false;
//            }
//
//            JSONObject loResponse = new JSONObject(lsResponse);
//            String lsResult = loResponse.getString("result");
//            if(lsResult.equalsIgnoreCase("error")){
//                JSONObject loError = loResponse.getJSONObject("error");
//                message = getErrorMessage(loError);
//                return false;
//            }
//
//            for(int x = 0; x < foItemLst.size(); x++){
//                poCartDao.DeleteCartItem(foItemLst.get(x).sListIDxx);
//            }
//
//            return loResponse.getString("sTransNox");
//        } catch (Exception e){
//            e.printStackTrace();
//            message = getLocalMessage(e);
//            return false;
//        }
//    }
}
