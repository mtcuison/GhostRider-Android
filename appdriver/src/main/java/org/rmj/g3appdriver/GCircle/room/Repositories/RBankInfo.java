/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.room.Repositories;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBankInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBankInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;

import java.util.List;

public class RBankInfo {
    private static final String TAG = RBankInfo.class.getSimpleName();
    private final DBankInfo poDao;

    private final AppConfigPreference poConfig;
    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RBankInfo(Application instance){
        this.poDao = GGC_GCircleDB.getInstance(instance).BankInfoDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<List<EBankInfo>> getBankInfoList(){
        return poDao.getBankInfoList();
    }

    public LiveData<String[]> getBankNameList(){
        return poDao.getBankNameList();
    }

    public LiveData<String> getBankNameFromId(String fsBankId) {
        return poDao.getBankNameFromId(fsBankId);
    }

    public boolean ImportBankInfo(){
        try{
            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("descript", "All");

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlDownloadBankInfo(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");

            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                Log.e(TAG, message);
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EBankInfo loBank = poDao.GetBankInfo(loJson.getString("sBankIDxx"));
                if(loBank == null){

                    if(loJson.getString("cRecdStat").equalsIgnoreCase("1")){
                        EBankInfo loDetail = new EBankInfo();
                        loDetail.setBankIDxx(loJson.getString("sBankIDxx"));
                        loDetail.setBankName(loJson.getString("sBankName"));
                        loDetail.setRecdStat(loJson.getString("cRecdStat"));
//                        if(loJson.getString("dModified").equalsIgnoreCase("null")){
//                            loDetail.setTimeStmp(new AppConstants().DATE_MODIFIED);
//                        } else {
//                            loDetail.setTimeStmp(loJson.getString("dModified"));
//                        }
                        poDao.SaveBankInfo(loDetail);
                        Log.d(TAG, "Bank info has been saved.");
                    }
                }

//                else {
//                    //Date Modified from server has been save as Date TimeStamp
//                    Log.d(TAG, "Local Time Stamp: " + loBank.getTimeStmp());
//                    Log.d(TAG, "Server Time Stamp: " + loJson.toString());
//                    Date ldDate1 = SQLUtil.toDate(loBank.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
//                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dModified"), SQLUtil.FORMAT_TIMESTAMP);
//                    if (!ldDate1.equals(ldDate2)) {
//                        loBank.setBankIDxx(loJson.getString("sBankIDxx"));
//                        loBank.setBankName(loJson.getString("sBankName"));
//                        loBank.setRecdStat(loJson.getString("cRecdStat"));
//                        loBank.setTimeStmp(loJson.getString("dModified"));
//                        poDao.UpdateBankInfo(loBank);
//                        Log.d(TAG, "Bank record has been updated.");
//                    }
//                }

            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
