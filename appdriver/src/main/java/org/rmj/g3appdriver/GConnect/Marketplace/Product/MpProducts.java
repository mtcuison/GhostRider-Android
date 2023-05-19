package org.rmj.g3appdriver.GConnect.Marketplace.Product;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DProduct;
import org.rmj.g3appdriver.GConnect.room.Entities.EProducts;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;

import java.util.Date;

public class MpProducts {
    private static final String TAG = MpProducts.class.getSimpleName();

    private final Application instance;
    private final DProduct poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;

    private String message;

    public MpProducts(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).prodctDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean ImportMPProducts(){
        try{
            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("descript", "");
            String lsTimeStmp = poDao.GetLatestProductStamp();
            if(lsTimeStmp != null){
                params.put("timestamp", lsTimeStmp);
            }

            String lsResponse = WebClient.sendRequest(
                    poApi.getImportProducts(),
                    params.toString(),
                    poHeaders.getHeaders());

            if (lsResponse == null) {
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (!lsResult.equalsIgnoreCase("success")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            JSONArray laDetail = loResponse.getJSONArray("detail");
            for (int x = 0; x < laDetail.length(); x++) {
                JSONObject joDetail = laDetail.getJSONObject(x);
                EProducts loDetail = poDao.GetProductIfExist(joDetail.getString("sListngID"));
                if (loDetail == null) {
                    EProducts loProdct = new EProducts();
                    loProdct.setListngID(joDetail.getString("sListngID"));
                    loProdct.setBriefDsc(joDetail.getString("sBriefDsc"));
                    loProdct.setStockIDx(joDetail.getString("sStockIDx"));
                    loProdct.setDescript(joDetail.getString("sDescript"));
                    loProdct.setRatingxx(joDetail.getString("nRatingxx"));
                    loProdct.setBarCodex(joDetail.getString("xBarCodex"));
                    loProdct.setDescrptx(joDetail.getString("xDescript"));
                    loProdct.setBrandNme(joDetail.getString("xBrandNme"));
                    loProdct.setModelNme(joDetail.getString("xModelNme"));
                    loProdct.setModelIDx(joDetail.getString("sModelIDx"));
                    loProdct.setImagesxx(joDetail.getString("sImagesxx"));
                    loProdct.setColorNme(joDetail.getString("xColorNme"));
                    loProdct.setCategrNm(joDetail.getString("xCategrNm"));
                    loProdct.setTotalQty(joDetail.getString("nTotalQty"));
                    loProdct.setQtyOnHnd(joDetail.getString("nQtyOnHnd"));
                    loProdct.setAllwCrdt(joDetail.getString("cAllwCrdt"));
                    loProdct.setResvOrdr(joDetail.getString("nResvOrdr"));
                    loProdct.setSoldQtyx(joDetail.getString("nSoldQtyx"));
                    loProdct.setUnitPrce(joDetail.getString("nUnitPrce"));
                    loProdct.setListStrt(joDetail.getString("dListStrt"));
                    loProdct.setListEndx(joDetail.getString("dListEndx"));
                    loProdct.setTranStat(joDetail.getString("cTranStat"));
                    loProdct.setTimeStmp(joDetail.getString("dTimeStmp"));
                    poDao.SaveProductInfo(loProdct);
                    Log.d(TAG, "New product listing save!");
                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) joDetail.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setBriefDsc(joDetail.getString("sBriefDsc"));
                        loDetail.setStockIDx(joDetail.getString("sStockIDx"));
                        loDetail.setDescript(joDetail.getString("sDescript"));
                        loDetail.setRatingxx(joDetail.getString("nRatingxx"));
                        loDetail.setBarCodex(joDetail.getString("xBarCodex"));
                        loDetail.setDescrptx(joDetail.getString("xDescript"));
                        loDetail.setBrandNme(joDetail.getString("xBrandNme"));
                        loDetail.setModelNme(joDetail.getString("xModelNme"));
                        loDetail.setModelIDx(joDetail.getString("sModelIDx"));
                        loDetail.setImagesxx(joDetail.getString("sImagesxx"));
                        loDetail.setColorNme(joDetail.getString("xColorNme"));
                        loDetail.setCategrNm(joDetail.getString("xCategrNm"));
                        loDetail.setTotalQty(joDetail.getString("nTotalQty"));
                        loDetail.setQtyOnHnd(joDetail.getString("nQtyOnHnd"));
                        loDetail.setAllwCrdt(joDetail.getString("cAllwCrdt"));
                        loDetail.setResvOrdr(joDetail.getString("nResvOrdr"));
                        loDetail.setSoldQtyx(joDetail.getString("nSoldQtyx"));
                        loDetail.setUnitPrce(joDetail.getString("nUnitPrce"));
                        loDetail.setListStrt(joDetail.getString("dListStrt"));
                        loDetail.setListEndx(joDetail.getString("dListEndx"));
                        loDetail.setTranStat(joDetail.getString("cTranStat"));
                        loDetail.setTimeStmp(joDetail.getString("dTimeStmp"));
                        poDao.UpdateProductInfo(loDetail);
                        Log.d(TAG, "New product listing updated!");
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

    public boolean SearchProduct(String fsVal){
        try {
            AddNewSearch(fsVal);

            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("descript", fsVal);

            String lsResponse = WebClient.sendRequest(
                    poApi.getImportProducts(),
                    params.toString(),
                    poHeaders.getHeaders());

            if (lsResponse == null) {
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (!lsResult.equalsIgnoreCase("success")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            JSONArray laDetail = loResponse.getJSONArray("detail");
            for (int x = 0; x < laDetail.length(); x++) {
                JSONObject joDetail = laDetail.getJSONObject(x);
                if (poDao.GetProductIfExist(joDetail.getString("sListngID")) == null) {
                    EProducts loProdct = new EProducts();
                    loProdct.setListngID(joDetail.getString("sListngID"));
                    loProdct.setBriefDsc(joDetail.getString("sBriefDsc"));
                    loProdct.setDescript(joDetail.getString("sDescript"));
                    loProdct.setBarCodex(joDetail.getString("xBarCodex"));
                    loProdct.setDescrptx(joDetail.getString("xDescript"));
                    loProdct.setRatingxx(joDetail.getString("nRatingxx"));
                    loProdct.setBrandNme(joDetail.getString("xBrandNme"));
                    loProdct.setModelNme(joDetail.getString("xModelNme"));
                    loProdct.setImagesxx(joDetail.getString("sImagesxx"));
                    loProdct.setColorNme(joDetail.getString("xColorNme"));
                    loProdct.setCategrNm(joDetail.getString("xCategrNm"));
                    loProdct.setTotalQty(joDetail.getString("nTotalQty"));
                    loProdct.setQtyOnHnd(joDetail.getString("nQtyOnHnd"));
                    loProdct.setResvOrdr(joDetail.getString("nResvOrdr"));
                    loProdct.setSoldQtyx(joDetail.getString("nSoldQtyx"));
                    loProdct.setUnitPrce(joDetail.getString("nUnitPrce"));
                    loProdct.setListStrt(joDetail.getString("dListStrt"));
                    loProdct.setListEndx(joDetail.getString("dListEndx"));
                    loProdct.setTranStat(joDetail.getString("cTranStat"));
                    loProdct.setTimeStmp(joDetail.getString("dTimeStmp"));
                    poDao.SaveProductInfo(loProdct);
                    Log.d(TAG, "New product listing save!");
                } else {
                    EProducts loProdct = poDao.GetProductIfExist(joDetail.getString("sListngID"));
                    Date ldDate1 = SQLUtil.toDate(loProdct.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) joDetail.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        poDao.UpdateProductListing(joDetail.getString("sListngID"),
                                joDetail.getString("nTotalQty"),
                                joDetail.getString("nQtyOnHnd"),
                                joDetail.getString("nResvOrdr"),
                                joDetail.getString("nSoldQtyx"),
                                joDetail.getString("nUnitPrce"),
                                joDetail.getString("dListStrt"),
                                joDetail.getString("dListEndx"),
                                joDetail.getString("cTranStat"),
                                joDetail.getString("dTimeStmp"));
                        Log.d(TAG, "New product listing updated!");
                    }
                }
            }
            return true;
        } catch(Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean AddNewSearch(String fsArgs){
        try{
//            if(poSearch.CheckIfExist(fsArgs) == null){
//                ESearchLog loSearch = new ESearchLog();
//                int nEntryNox = poSearch.CreateNewEntryNox();
//                loSearch.setEntryNox(nEntryNox);
//                loSearch.setSearchxx(fsArgs);
//                loSearch.setTimeStmp(new AppConstants().DATE_MODIFIED);
//                poSearch.SaveNewSearch(loSearch);
//                return true;
//            } else {
//                ESearchLog loSearch = poSearch.CheckIfExist(fsArgs);
//                poSearch.UpdateSearch(loSearch.getEntryNox(), new AppConstants().DATE_MODIFIED);
                return true;
//            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
