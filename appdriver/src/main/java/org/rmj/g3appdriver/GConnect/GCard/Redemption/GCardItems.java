package org.rmj.g3appdriver.GConnect.GCard.Redemption;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DRedeemablesInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ERedeemablesInfo;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;

import java.util.ArrayList;
import java.util.List;

public class GCardItems {
    private static final String TAG = GCardItems.class.getSimpleName();

    private final Application instance;
    private final DRedeemablesInfo poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;

    private String message;

    public GCardItems(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).redeemablesDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean ImportRedeemable(){
        try{
            JSONObject params = new JSONObject();
            String lsResponse = WebClient.sendRequest(
                    poApi.getImportRedeemItemsAPI(),
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

            JSONArray laDetail = loResponse.getJSONArray("detail");
            List<ERedeemablesInfo> loItems = new ArrayList<>();
            for(int x = 0; x < laDetail.length(); x++){
                JSONObject loJson = laDetail.getJSONObject(x);
                ERedeemablesInfo info = new ERedeemablesInfo();
                info.setTransNox(loJson.getString("sTransNox"));
                info.setPromoCde(loJson.getString("sPromCode"));
                info.setPromoDsc(loJson.getString("sPromDesc"));
                info.setPointsxx(Double.parseDouble(loJson.getString("nPointsxx")));
                info.setImageUrl(loJson.getString("sImageURL"));
                info.setDateFrom(loJson.getString("dDateFrom"));
                info.setDateThru(loJson.getString("dDateThru"));
                info.setPreOrder(loJson.getString("cPreOrder"));
                loItems.add(info);
            }
            poDao.insertBulkData(loItems);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
