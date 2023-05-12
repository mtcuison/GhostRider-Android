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

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DRemittanceAccounts;
import org.rmj.g3appdriver.GCircle.room.Entities.ERemittanceAccounts;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;

import java.util.List;

public class RRemittanceAccount {

    private final DRemittanceAccounts poDao;

    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RRemittanceAccount(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).RemitanceAccDao();
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean ImportRemittanceAccounts(){
        try{
            JSONObject param = new JSONObject();
            param.put("bsearch", true);
            param.put("descript", "all");

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlBranchRemittanceAcc(),
                    param.toString(),
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
                return false;
            }

            JSONArray loDetail = loResponse.getJSONArray("detail");

            for(int x = 0; x < loDetail.length(); x++){
                JSONObject loJson = loDetail.getJSONObject(x);
                String args = loJson.getString("sBranchCd");
                String args1 = loJson.getString("sBnkActID");
                String args2 = loJson.getString("sActNamex");
                ERemittanceAccounts loRecord = poDao.GetAccount(args, args1, args2);

                if(loRecord == null) {
                    ERemittanceAccounts loAcc = new ERemittanceAccounts();
                    loAcc.setBranchCd(loJson.getString("sBranchCd"));
                    loAcc.setBranchNm(loJson.getString("sBranchNm"));
                    loAcc.setActNamex(loJson.getString("sActNamex"));
                    loAcc.setActNumbr(loJson.getString("sActNumbr"));
                    loAcc.setBnkActID(loJson.getString("sBnkActID"));
                    poDao.SaveAccountRemittance(loAcc);
                }
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean RefreshRecords(){
        try{
            JSONObject param = new JSONObject();
            param.put("bsearch", true);
            param.put("descript", "all");

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlBranchRemittanceAcc(),
                    param.toString(),
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
                return false;
            }

            JSONArray loDetail = loResponse.getJSONArray("detail");

            poDao.ClearRecords();
            for(int x = 0; x < loDetail.length(); x++){
                JSONObject loJson = loDetail.getJSONObject(x);
                ERemittanceAccounts loAcc = new ERemittanceAccounts();
                loAcc.setBranchCd(loJson.getString("sBranchCd"));
                loAcc.setBranchNm(loJson.getString("sBranchNm"));
                loAcc.setActNamex(loJson.getString("sActNamex"));
                loAcc.setActNumbr(loJson.getString("sActNumbr"));
                loAcc.setBnkActID(loJson.getString("sBnkActID"));
                poDao.SaveAccountRemittance(loAcc);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public LiveData<List<ERemittanceAccounts>> getRemittanceBankAccount(){
        return poDao.getRemittanceBankAccountsList();
    }

    public LiveData<List<ERemittanceAccounts>> getRemittanceOtherAccount(){
        return poDao.getRemittanceOtherAccountsList();
    }
}
