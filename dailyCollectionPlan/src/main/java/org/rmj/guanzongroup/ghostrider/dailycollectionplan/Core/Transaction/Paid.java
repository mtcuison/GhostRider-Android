package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction;

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GCircle.room.Entities.EImageInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GCircle.room.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Device.Telephony;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.iDCPTransaction;

public class Paid implements iDCPTransaction {

    private final Application instance;
    private final RDailyCollectionPlan poDcp;
    private final GCircleApi poApi;

    public Paid(Application application) {
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(instance);
        AppConfigPreference loConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new GCircleApi(application);
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
            EmployeeSession loUser = new EmployeeSession(instance);
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

            String lsResponse = WebClient.sendRequest(poApi.getUrlDcpSubmit(), loJson.toString(), loHeaders.getHeaders());

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
                    String lsMessage = getErrorMessage(loError);
                    callback.OnFailed(lsMessage);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("OnSaveTransaction" + e.getMessage());
        }
    }
}
