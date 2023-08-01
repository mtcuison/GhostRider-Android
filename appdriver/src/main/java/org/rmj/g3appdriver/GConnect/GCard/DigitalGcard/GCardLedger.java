package org.rmj.g3appdriver.GConnect.GCard.DigitalGcard;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Account.ClientSession;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DGCardTransactionLedger;
import org.rmj.g3appdriver.GConnect.room.Entities.EGCardLedger;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.encryp.CodeGenerator;
import org.rmj.g3appdriver.etc.AppConfigPreference;

import java.util.List;

public class GCardLedger {
    private static final String TAG = GCardLedger.class.getSimpleName();

    private final DGCardTransactionLedger poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;
    private final ClientSession poSession;
    private final AppConfigPreference poConfig;

    private String message;

    public GCardLedger(Application instance) {
        this.poDao = GGC_GConnectDB.getInstance(instance).gcardLedgerDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
        this.poSession = ClientSession.getInstance(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<List<EGCardLedger>> GetAllTransactions(){
        return poDao.GetAllTransactionsList();
    }

    public LiveData<List<EGCardLedger>> GetAllPointsEntryTransactions(){
        return poDao.GetPointsEntryTransactionsList();
    }

    public LiveData<List<EGCardLedger>> GetRedemptionTransactions(){
        return poDao.GetRedemptionTransactionsList();
    }

    public boolean ImportOnlineTransactions(){
        try {
            String lsGCardNo = poDao.getCardNox();

            JSONObject params = new JSONObject();
            params.put("secureno", CodeGenerator.generateSecureNo(lsGCardNo));

            String lsResponse = WebClient.sendRequest(
                    poApi.getImportOnlineTransAPI(),
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

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EGCardLedger info = new EGCardLedger();
                info.setGCardNox(loJson.getString("sGCardNox"));
                info.setTransact(loJson.getString("dTransact"));
                info.setSourceDs("ONLINE");
                info.setReferNox(loJson.getString("sReferNox"));
                info.setTranType(loJson.getString("sTranType"));
                info.setSourceNo(loJson.getString("sSourceNo"));
                info.setPointsxx(Double.parseDouble(loJson.getString("nPointsxx")));
                poDao.Save(info);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean ImportOfflineTransactions(){
        try {
            String lsGCardNo = poDao.getCardNox();

            JSONObject params = new JSONObject();
            params.put("secureno", CodeGenerator.generateSecureNo(lsGCardNo));

            String lsResponse = WebClient.sendRequest(
                    poApi.getImportOfflineTransAPI(),
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

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EGCardLedger info = new EGCardLedger();
                info.setGCardNox(loJson.getString("sGCardNox"));
                info.setTransact(loJson.getString("dTransact"));
                info.setSourceDs("OFFLINE");
                info.setReferNox(loJson.getString("sReferNox"));
                info.setTranType(loJson.getString("sTranType"));
                info.setSourceNo(loJson.getString("sSourceNo"));
                info.setPointsxx(Double.parseDouble(loJson.getString("nPointsxx")));
                poDao.Save(info);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean ImportPreOrderTransactions(){
        try {
            String lsGCardNo = poDao.getCardNox();

            JSONObject params = new JSONObject();
            params.put("secureno", CodeGenerator.generateSecureNo(lsGCardNo));

            String lsResponse = WebClient.sendRequest(
                    poApi.getImportPreOrderAPI(),
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

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EGCardLedger info = new EGCardLedger();
                info.setGCardNox(loJson.getString("sGCardNox"));
                info.setTransact(loJson.getString("dTransact"));
                info.setSourceDs("PREORDER");
                info.setReferNox(loJson.getString("sReferNox"));
                info.setTranType(loJson.getString("sTranType"));
                info.setSourceNo(loJson.getString("sSourceNo"));
                info.setPointsxx(Double.parseDouble(loJson.getString("nPointsxx")));
                poDao.Save(info);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean ImportRedemptionTransaction(){
        try {
            String lsGCardNo = poDao.getCardNox();

            JSONObject params = new JSONObject();
            params.put("secureno", CodeGenerator.generateSecureNo(lsGCardNo));

            String lsResponse = WebClient.sendRequest(
                    poApi.getImportReedemptionsAPI(),
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

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EGCardLedger info = new EGCardLedger();
                info.setGCardNox(loJson.getString("sGCardNox"));
                info.setTransact(loJson.getString("dTransact"));
                info.setSourceDs("REDEMPTION");
                info.setReferNox(loJson.getString("sReferNox"));
                info.setTranType(loJson.getString("sTranType"));
                info.setSourceNo(loJson.getString("sSourceNo"));
                info.setPointsxx(Double.parseDouble(loJson.getString("nPointsxx")));
                poDao.Save(info);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
