package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EAddressUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EImageInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EMobileUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Device.Telephony;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Account.SessionManager;
import org.rmj.g3appdriver.dev.Api.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.iDCPTransaction;

public class Paid implements iDCPTransaction {

    private final Application instance;
    private final RDailyCollectionPlan poDcp;
    private final WebApi poApi;

    public Paid(Application application) {
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(instance);
        AppConfigPreference loConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(loConfig.getTestStatus());
    }

    @Override
    public LiveData<EDCPCollectionDetail> getAccountInfo(String TransNox, int EntryNo) {
        return poDcp.getCollectionDetail(TransNox, EntryNo);
    }

    @Override
    public void SaveAddress(EAddressUpdate foAddress) {
        throw new NullPointerException();
    }

    @Override
    public void SaveMobileUpdate(EMobileUpdate foAddress) {
        throw new NullPointerException();
    }

    @Override
    public void SaveImageInfo(EImageInfo foImage) {
        throw new NullPointerException();
    }

    @Override
    public void OnSaveTransaction(EDCPCollectionDetail foDetail, TransactionCallback callback) {
        try{
            HttpHeaders loHeaders = HttpHeaders.getInstance(instance);
            AppConfigPreference loConfig = AppConfigPreference.getInstance(instance);
            SessionManager loUser = new SessionManager(instance);
            Telephony loTlphny = new Telephony(instance);

            foDetail.setTranStat("2");
            foDetail.setModified(new AppConstants().DATE_MODIFIED);
            poDcp.updateCollectionDetailInfo(foDetail);
            loConfig.setDCP_PRNox(foDetail.getPRNoxxxx());

            JSONObject loData = new JSONObject();
            loData.put("sPRNoxxxx", foDetail.getPRNoxxxx());
            loData.put("nTranAmtx", foDetail.getTranAmtx());
            loData.put("nDiscount", foDetail.getDiscount());
            loData.put("nOthersxx", foDetail.getOthersxx());
            loData.put("cTranType", foDetail.getTranType());
            loData.put("nTranTotl", foDetail.getTranTotl());
            if(foDetail.getBankIDxx() == null) {
                loData.put("sBankIDxx", "");
                loData.put("sCheckDte", "");
                loData.put("sCheckNox", "");
                loData.put("sCheckAct", "");
            } else {
                loData.put("sBankIDxx", foDetail.getBankIDxx());
                loData.put("sCheckDte", foDetail.getCheckDte());
                loData.put("sCheckNox", foDetail.getCheckNox());
                loData.put("sCheckAct", foDetail.getCheckAct());
            }
            JSONObject loJson = new JSONObject();
            loJson.put("sTransNox", foDetail.getTransNox());
            loJson.put("nEntryNox", foDetail.getEntryNox());
            loJson.put("sAcctNmbr", foDetail.getAcctNmbr());
            loJson.put("sRemCodex", foDetail.getRemCodex());
            loJson.put("dModified", foDetail.getModified());
            loJson.put("sJsonData", loData);
            loJson.put("dReceived", "");
            loData.put("sRemarksx", foDetail.getRemarksx());
            loJson.put("sUserIDxx", loUser.getUserID());
            loJson.put("sDeviceID", loTlphny.getDeviceID());

            String lsResponse = WebClient.sendRequest(poApi.getUrlDcpSubmit(loConfig.isBackUpServer()), loJson.toString(), loHeaders.getHeaders());

            if(lsResponse == null){
                callback.OnFailed("Server no response");
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    foDetail.setSendStat("1");
                    foDetail.setSendDate(new AppConstants().DATE_MODIFIED);
                    foDetail.setModified(new AppConstants().DATE_MODIFIED);
                    poDcp.updateCollectionDetailInfo(foDetail);
                    callback.OnSuccess();
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailed(lsMessage);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("OnSaveTransaction" + e.getMessage());
        }
    }
}
