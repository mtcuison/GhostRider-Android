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
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.GCircle.room.Entities.EMCColor;
import org.rmj.g3appdriver.GCircle.room.Entities.EMCModelCashPrice;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DMcModel;
import org.rmj.g3appdriver.GCircle.room.Entities.EMcModel;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RMcModel {
    private static final String TAG = RMcModel.class.getSimpleName();

    private final DMcModel poDao;

    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public RMcModel(Application instance){
        this.poDao = GGC_GCircleDB.getInstance(instance).McModelDao();
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<List<EMcModel>> getMcModelFromBrand(String BrandID){
        return poDao.getAllModeFromBrand(BrandID);
    }

    public String getLatestDataTime(){
        return poDao.getLatestDataTime();
    }

    public EMcModel getModelInfo(String TransNox){
        return poDao.getModelInfo(TransNox);
    }

    public LiveData<DMcModel.McAmortInfo> GetMonthlyPayment(String fsModel, int fnTerm){
        return poDao.GetMonthlyPayment(fsModel, fnTerm);
    }

    public LiveData<DMcModel.McDPInfo> getDownpayment(String ModelID){
        return poDao.getDownpayment(ModelID);
    }

    public boolean ImportMCModel(){
        try{
            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("descript", "All");

            EMcModel loObj = poDao.GetLatestMCModel();
            if(loObj != null) {
                params.put("timestamp", loObj.getTimeStmp());
            }

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlImportMcModel(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(Objects.requireNonNull(lsResponse));
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                Log.d(TAG, "Json value: " + loJson.toString());
                EMcModel loDetail = poDao.GetMCModel(loJson.getString("sModelIDx"));

                if(loDetail == null) {
                    if(loJson.getString("cRecdStat").equalsIgnoreCase("1")) {
                        EMcModel loModel = new EMcModel();
                        loModel.setModelIDx(loJson.getString("sModelIDx"));
                        loModel.setModelCde(loJson.getString("sModelCde"));
                        loModel.setModelNme(loJson.getString("sModelNme"));
                        loModel.setBrandIDx(loJson.getString("sBrandIDx"));
                        loModel.setMotorTyp(loJson.getString("cMotorTyp"));
                        loModel.setRegisTyp(loJson.getString("cRegisTyp"));
                        loModel.setEndOfLfe(loJson.getString("cEndOfLfe"));
                        loModel.setEngineTp(loJson.getString("cEngineTp"));
                        loModel.setHotItemx(loJson.getString("cHotItemx"));
                        loModel.setRecdStat(loJson.getString("cRecdStat"));
                        loModel.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.insert(loModel);
                        Log.d(TAG, "Mc model info has been saved.");
                    }
                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setModelIDx(loJson.getString("sModelIDx"));
                        loDetail.setModelCde(loJson.getString("sModelCde"));
                        loDetail.setModelNme(loJson.getString("sModelNme"));
                        loDetail.setBrandIDx(loJson.getString("sBrandIDx"));
                        loDetail.setMotorTyp(loJson.getString("cMotorTyp"));
                        loDetail.setRegisTyp(loJson.getString("cRegisTyp"));
                        loDetail.setEndOfLfe(loJson.getString("cEndOfLfe"));
                        loDetail.setEngineTp(loJson.getString("cEngineTp"));
                        loDetail.setHotItemx(loJson.getString("cHotItemx"));
                        loDetail.setRecdStat(loJson.getString("cRecdStat"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.update(loDetail);
                        Log.d(TAG, "Mc model info has been updated.");
                    }
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean ImportModelColor(){
        try{
            JSONObject params = new JSONObject();
            params.put("bsearch", true);
            params.put("descript", "All");

            EMCColor loObj = poDao.GetLatestMcColorTimeStamp();
            if(loObj != null) {
                params.put("timestamp", loObj.getTimeStmp());
            }

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlImportMcModelColor(),
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
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                String lsModelID = loJson.getString("sModelIDx");
                String lsColorID = loJson.getString("sColorIDx");
                EMCColor loDetail = poDao.GetModelColor(lsModelID, lsColorID);

                if(loDetail == null) {
                    EMCColor loModel = new EMCColor();
                    loModel.setModelIDx(loJson.getString("sModelIDx"));
                    loModel.setColorIDx(loJson.getString("sColorIDx"));
                    loModel.setColorNme(loJson.getString("sColorNme"));
                    loModel.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.insert(loModel);
                    Log.d(TAG, "Mc model info has been saved.");
                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setModelIDx(loJson.getString("sModelIDx"));
                        loDetail.setColorIDx(loJson.getString("sColorIDx"));
                        loDetail.setColorNme(loJson.getString("sColorNme"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.update(loDetail);
                        Log.d(TAG, "Mc model info has been updated.");
                    }
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean ImportCashPrices(){
        try{
            JSONObject params = new JSONObject();
            params.put("sModelIDxx", "");
            params.put("dPricedxxx", "");

            EMCModelCashPrice loInfo = poDao.GetLatestMcCashPrice();

            if(loInfo != null){
                params.put("dPricedxxx", loInfo.getPricexxx());
            }

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlImportMcModelPrices(),
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
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                String lsModelID = loJson.getString("sModelIDx");

                EMCModelCashPrice loDetail = poDao.GetModelCashPriceInfo(lsModelID);

                if(loDetail == null) {
                    EMCModelCashPrice loModel = new EMCModelCashPrice();
                    loModel.setModelIDx(loJson.getString("sModelIDx"));
                    loModel.setMCCatNme(loJson.getString("sMCCatNme"));
                    loModel.setModelNme(loJson.getString("sModelNme"));
                    loModel.setBrandNme(loJson.getString("sBrandNme"));
                    loModel.setSelPrice(loJson.getDouble("nSelPrice"));
                    loModel.setLastPrce(loJson.getDouble("nLastPrce"));
                    loModel.setDealrPrc(loJson.getDouble("nDealrPrc"));
                    loModel.setPricexxx(loJson.getString("dPricexxx"));
                    loModel.setBrandIDx(loJson.getString("sBrandIDx"));
                    loModel.setMCCatIDx(loJson.getString("sMCCatIDx"));
                    poDao.insert(loModel);
                    Log.d(TAG, "Mc model price info has been saved.");
                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getPricexxx(), SQLUtil.FORMAT_SHORT_DATE);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dPricexxx"), SQLUtil.FORMAT_SHORT_DATE);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setModelIDx(loJson.getString("sModelIDx"));
                        loDetail.setMCCatNme(loJson.getString("sMCCatNme"));
                        loDetail.setModelNme(loJson.getString("sModelNme"));
                        loDetail.setBrandNme(loJson.getString("sBrandNme"));
                        loDetail.setSelPrice(loJson.getDouble("nSelPrice"));
                        loDetail.setLastPrce(loJson.getDouble("nLastPrce"));
                        loDetail.setDealrPrc(loJson.getDouble("nDealrPrc"));
                        loDetail.setPricexxx(loJson.getString("dPricexxx"));
                        loDetail.setBrandIDx(loJson.getString("sBrandIDx"));
                        loDetail.setMCCatIDx(loJson.getString("sMCCatIDx"));
                        poDao.update(loDetail);
                        Log.d(TAG, "Mc model price info has been updated.");
                    }
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

}
