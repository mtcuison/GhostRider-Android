package org.rmj.g3appdriver.lib.Panalo.Obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DPanalo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.encryp.CodeGenerator;
import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;

import java.util.ArrayList;
import java.util.List;

public class GPanalo {
    private static final String TAG = GPanalo.class.getSimpleName();

    private final Context mContext;

    private final DPanalo poDao;

    private final GCircleApi poApis;
    private final HttpHeaders poHeaders;

    private String message;

    public GPanalo(Application instance) {
        this.mContext = instance;
        this.poDao = GGC_GCircleDB.getInstance(mContext).panaloDao();
        this.poApis = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(mContext);
    }

    public String getMessage() {
        return message;
    }

    public List<PanaloRewards> GetRewards(String args){
        try{
            JSONObject params = new JSONObject();
            params.put("transtat", args);

            String lsResponse = WebClient.sendRequest(
                    poApis.getUrlGetPanaloRewards(),
                    params.toString(),
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = "Server no response while sending response.";
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (!lsResult.equalsIgnoreCase("success")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            JSONArray laJson = loResponse.getJSONArray("payload");

            List<PanaloRewards> loRewards = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject joReward = laJson.getJSONObject(x);
                PanaloRewards loReward = new PanaloRewards();
                loReward.setPanaloQC(joReward.getString("sPanaloQC"));
                loReward.setTransact(joReward.getString("dTransact"));
                loReward.setUserIDxx(joReward.getString("sUserIDxx"));
                loReward.setPanaloCD(joReward.getString("sPanaloCD"));
                loReward.setPanaloDs(joReward.getString("sPanaloDs"));
                loReward.setAcctNmbr(joReward.getString("sAcctNmbr"));
                loReward.setAmountxx(joReward.getDouble("nAmountxx"));
                loReward.setDeviceID(joReward.getString("sDeviceID"));
                loReward.setExpiryDt(joReward.getString("dExpiryDt"));
                loReward.setItemCode(joReward.getString("sItemCode"));
                loReward.setItemDesc(joReward.getString("sItemDesc"));
                loReward.setItemQtyx(joReward.getInt("nItemQtyx"));
                loReward.setRedeemxx(joReward.getInt("nRedeemxx"));
                loReward.setTranStat(joReward.getString("cTranStat"));
                loReward.setTimeStmp(joReward.getString("dTimeStmp"));
                loReward.setRedeemDt(joReward.getString("dRedeemxx"));
                loReward.setBranchNm(joReward.getString("sBranchNm"));
                loReward.setSourceNm(joReward.getString("sSourceNm"));
                loRewards.add(loReward);
            }

            return loRewards;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public Boolean ClaimReward(String args){
        try{
            JSONObject params = new JSONObject();
            params.put("transtat", args);

            String lsResponse = WebClient.sendRequest(
                    poApis.getClaimPanaloReward(),
                    params.toString(),
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = "Server no response while sending response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (!lsResult.equalsIgnoreCase("success")) {
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

    public Bitmap RedeemReward(PanaloRewards args){
        try{
            Bitmap loBmp = null;
            switch (args.getPanaloCD()) {
                case "0001":
                    loBmp = new CodeGenerator().GeneratePanaloOtherRedemptionQC(args);
                    break;
                case "0002":
                    loBmp = new CodeGenerator().GeneratePanaloRebateRedemptionQC(args);
                    break;
                default:
                    break;
            }
            return loBmp;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public List<String> GetParticipants(){
        try{
            String lsResponse = WebClient.sendRequest(
                    poApis.getUrlGetRaffleParticipants(),
                    "",
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = "Server no response while sending response.";
                return null;
            }
            return new ArrayList<>();
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
