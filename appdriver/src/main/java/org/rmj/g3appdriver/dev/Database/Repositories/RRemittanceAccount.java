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

package org.rmj.g3appdriver.dev.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DRemittanceAccounts;
import org.rmj.g3appdriver.dev.Database.Entities.ERemittanceAccounts;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.dev.Api.WebApi;

import java.util.List;

public class RRemittanceAccount {

    private final DRemittanceAccounts poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RRemittanceAccount(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).RemitanceAccDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
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
                    poApi.getUrlBranchRemittanceAcc(poConfig.isBackUpServer()),
                    param.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
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
            message = e.getMessage();
            return false;
        }
    }

    public boolean RefreshRecords(){
        try{
            JSONObject param = new JSONObject();
            param.put("bsearch", true);
            param.put("descript", "all");

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlBranchRemittanceAcc(poConfig.isBackUpServer()),
                    param.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
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
            message = e.getMessage();
            return false;
        }
    }

    public LiveData<List<ERemittanceAccounts>> getRemittanceBankAccount(){
        return poDao.getRemittanceBankAccountsList();
    }

    public LiveData<List<ERemittanceAccounts>> getRemittanceOtherAccount(){
        return poDao.getRemittanceOtherAccountsList();
    }

    public LiveData<ERemittanceAccounts> getDefaultRemittanceAccount(){
        return poDao.getDefaultRemittanceAccount();
    }

    public List<ERemittanceAccounts> getRemittanceAccountIfExist(){
        return poDao.getRemittanceAccountsIfExist();
    }
}
