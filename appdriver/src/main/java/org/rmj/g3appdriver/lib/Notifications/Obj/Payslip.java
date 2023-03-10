package org.rmj.g3appdriver.lib.Notifications.Obj;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPayslip;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;

import java.util.List;
import java.util.Objects;

public class Payslip {
    private static final String TAG = Payslip.class.getSimpleName();

    private final Application instance;

    private final DPayslip poDao;
    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public Payslip(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).payslipDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public LiveData<List<DPayslip.Payslip>> GetPaySliplist(){
        return poDao.GetPaySlipList();
    }

    public boolean NotificationRead(String MessageID){
        try{
            ENotificationRecipient loDetail = poDao.GetPaySlipNotification(MessageID);

            if(loDetail == null){
                message = "Unable to find notification for update.";
                return false;
            }

            loDetail.setMesgStat("3");
            loDetail.setReadxxxx(new AppConstants().DATE_MODIFIED);
            poDao.Update(loDetail);

            JSONObject params = new JSONObject();
            params.put("transno", loDetail.getTransNox());
            params.put("status", "3");
            params.put("stamp", loDetail.getReadxxxx());
            params.put("infox", "");

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlSendResponse(poConfig.isBackUpServer()),
                    params.toString(), poHeaders.getHeaders());
            JSONObject loResponse = new JSONObject(Objects.requireNonNull(lsResponse));
            String result = loResponse.getString("result");
            if (result.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                Log.e(TAG, loError.getString("message"));
                return false;
            }

            loDetail.setStatSent("1");
            poDao.Update(loDetail);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
