package org.guanzongroup.com.creditevaluation.Core;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;

import java.util.HashMap;
import java.util.List;

public class EvaluatorManager {
    private static final String TAG = EvaluatorManager.class.getSimpleName();

    private final Application instance;

    private final HttpHeaders poHeaders;
    private final SessionManager poSession;
    private final RCreditOnlineApplicationCI poCI;
    private final CIAPIs poApis;

    public interface OnActionCallback {
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public interface OnRetrieveDataCallback{
        void OnRetrieve(HashMap<oParentFndg, List<oChildFndg>> foEvaluate, ECreditOnlineApplicationCI foData);
        void OnFailed(String message);
    }

    public EvaluatorManager(Application application) {
        this.instance = application;
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poSession = new SessionManager(instance);
        this.poCI = new RCreditOnlineApplicationCI(instance);
        this.poApis = new CIAPIs(true);
    }

    public void DownloadCreditApplications(OnActionCallback callback){
        try{
            JSONObject params = new JSONObject();
            String lsResponse = WebClient.sendRequest(poApis.getUrlDownloadApplications(), params.toString(), poHeaders.getHeaders());
            if(lsResponse == null){
                callback.OnFailed("Server no response.");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccess(loResponse.toString());
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailed(lsMessage);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void AddApplication(String TransNox, OnActionCallback callback){
        try{
            JSONObject params = new JSONObject();
            String lsResponse = WebClient.sendRequest(poApis.getUrlDownloadApplications(), params.toString(), poHeaders.getHeaders());
            if(lsResponse == null){
                callback.OnFailed("Server no response.");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccess(loResponse.toString());
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailed(lsMessage);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public boolean SaveApplication(JSONObject foJson) {
        try{
            JSONArray loDetail = foJson.getJSONArray("detail");
            for(int x= 0 ; x < loDetail.length(); x++){
                JSONObject loJson = loDetail.getJSONObject(x);
                ECreditOnlineApplicationCI loApp = new ECreditOnlineApplicationCI();
                loApp.setTransNox(loJson.getString("sTransNox"));
                loApp.setCredInvx(loJson.getString("sCredInvx"));
                loApp.setAddressx(loJson.getString("sAddressx"));
                loApp.setAddrFndg(loJson.getString("sAddrFndg"));
                loApp.setAssetsxx(loJson.getString("sAssetsxx"));
                loApp.setAsstFndg(loJson.getString("sAsstFndg"));
                loApp.setIncomexx(loJson.getString("sIncomexx"));
                loApp.setIncmFndg(loJson.getString("sIncmFndg"));
                loApp.setHasRecrd(loJson.getString("cHasRecrd"));
                loApp.setRecrdRem(loJson.getString("sRecrdRem"));
                loApp.setPrsnBrgy(loJson.getString("sPrsnBrgy"));
                loApp.setPrsnPstn(loJson.getString("sPrsnPstn"));
                loApp.setPrsnNmbr(loJson.getString("sPrsnNmbr"));
                loApp.setNeighBr1(loJson.getString("sNeighBr1"));
                loApp.setNeighBr2(loJson.getString("sNeighBr2"));
                loApp.setNeighBr3(loJson.getString("sNeighBr3"));
                loApp.setRcmdRcd1(loJson.getString("dRcmdRcd1"));
                loApp.setRcmdtnx1(loJson.getString("dRcmdtnx1"));
                loApp.setRcmdtnc1(loJson.getString("cRcmdtnx1"));
                loApp.setRcmdtns1(loJson.getString("sRcmdtnx1"));
                loApp.setRcmdRcd2(loJson.getString("dRcmdRcd2"));
                loApp.setRcmdtnx2(loJson.getString("dRcmdtnx2"));
                loApp.setRcmdtnc2(loJson.getString("cRcmdtnx2"));
                loApp.setRcmdtns2(loJson.getString("sRcmdtnx2"));
                loApp.setTranStat(loJson.getString("cTranStat"));
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public LiveData<List<ECreditOnlineApplicationCI>> getForEvaluationList(){
        return poCI.getForEvaluationList();
    }

    public void RetrieveApplicationData(String TransNox, OnRetrieveDataCallback callback){
        try{
            HashMap<oParentFndg, List<oChildFndg>> loForEval = new HashMap<>();
            ECreditOnlineApplicationCI loApp = poCI.getApplication(TransNox);
            if(loApp == null){
                callback.OnFailed("No application record found.");
            } else {
                loForEval.putAll(FindingsParser.getForEvaluation(oChildFndg.FIELDS.ADDRESS, loApp.getAddressx(), loApp.getAddrFndg()));
                loForEval.putAll(FindingsParser.getForEvaluation(oChildFndg.FIELDS.MEANS, loApp.getIncomexx(), loApp.getIncmFndg()));
                loForEval.putAll(FindingsParser.getForEvaluation(oChildFndg.FIELDS.ASSETS, loApp.getAssetsxx(), loApp.getAsstFndg()));

                callback.OnRetrieve(loForEval, loApp);
            }

        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            callback.OnFailed(e.getMessage());
        }
    }

    public void PostEvaluation(OnActionCallback callback){
        try{
            JSONObject params = new JSONObject();
            String lsResponse = WebClient.sendRequest(poApis.getUrlSubmitResult(), params.toString(), poHeaders.getHeaders());
            if(lsResponse == null){
                callback.OnFailed("Server no response.");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccess("Credit evaluation has been posted successfully");
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailed(lsMessage);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean UpdatePostedEvaluation(String TransNox){
        try {
            poCI.UpdateTransaction(TransNox);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void UpdateCIResult(String TransNox, oParentFndg foParent, oChildFndg foChild, OnActionCallback callback) {
        try{
            String lsFindings = "";
            String lsResult = "";
            String lsField = foParent.getField();
            switch (foParent.getField()){
                case oChildFndg.FIELDS.ADDRESS:
                    lsFindings = poCI.getAddressForEvaluation(TransNox);
                    lsResult = CIResultHandler.toStringResult(lsField, lsFindings, foParent, foChild);
                    poCI.updateAddressEvaluation(TransNox, lsResult);
                    break;

                case oChildFndg.FIELDS.ASSETS:
                    lsFindings = poCI.getAssetsForEvaluation(TransNox);
                    lsResult = CIResultHandler.toStringResult(lsField, lsFindings, foParent, foChild);
                    poCI.updateAssetEvaluation(TransNox, lsResult);
                    break;

                case oChildFndg.FIELDS.MEANS:
                    lsFindings = poCI.getIncomeForEvaluation(TransNox);
                    lsResult = CIResultHandler.toStringResult(lsField, lsFindings, foParent, foChild);
                    poCI.updateIncomeEvaluation(TransNox, lsResult);
                    break;

            }
            callback.OnSuccess("Updated successfully");
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            callback.OnFailed("UpdateCIResult " + e.getMessage());
        }
    }
}
