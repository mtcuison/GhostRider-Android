package org.rmj.g3appdriver.lib.Panalo.Obj;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.CodeGenerator;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPanalo;
import org.rmj.g3appdriver.dev.Database.Entities.EPanaloReward;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

public class GPanalo {
    private static final String TAG = GPanalo.class.getSimpleName();

    private final Context mContext;

    private final DPanalo poDao;

    private final WebApi poApis;
    private final AppConfigPreference poConfig;
    private final HttpHeaders poHeaders;

    private String message;

    public GPanalo(Context context) {
        this.mContext = context;
        this.poDao = GGC_GriderDB.getInstance(mContext).panaloDao();
        this.poConfig = AppConfigPreference.getInstance(mContext);
        this.poApis = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(mContext);
    }

    public String getMessage() {
        return message;
    }

    public List<PanaloRewards> GetRewards(String args){
        try{
            JSONObject params = new JSONObject();
            params.put("transtat", args);

            String lsResponse = WebClient.httpsPostJSon(
                    poApis.getUrlGetPanaloRewards(poConfig.isBackUpServer()),
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
                message = loError.getString("message");
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
            message = e.getMessage();
            return null;
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
            message = e.getMessage();
            return null;
        }
    }
}
