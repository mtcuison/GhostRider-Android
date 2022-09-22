/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/26/21 4:19 PM
 * project file last modified : 4/26/21 4:19 PM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DSysConfig;
import org.rmj.g3appdriver.GRider.Database.Entities.ESysConfig;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.Date;
import java.util.List;

public class RSysConfig {
    private static final String TAG = RSysConfig.class.getSimpleName();
    private final DSysConfig poDao;

    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public interface OnGetDataCallback{
        void OnResult(String result);
    }

    public RSysConfig(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).sysConfigDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public void insertSysConfig(List<ESysConfig> sysConfig){
        poDao.insertSysConfig(sysConfig);
    }

    public void getLocationInterval(OnGetDataCallback callback){
        new GetLocationTask(callback).execute(poDao);
    }

    private static class GetLocationTask extends AsyncTask<DSysConfig, Void, String>{
        private final OnGetDataCallback callback;

        public GetLocationTask(OnGetDataCallback callback){
            this.callback = callback;
        }

        @Override
        protected String doInBackground(DSysConfig... dSysConfigs) {
            return dSysConfigs[0].getLocationInterval();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.OnResult(s);
        }
    }

    public boolean ImportSysConfig(){
        try{
            JSONObject params = new JSONObject();
            params.put("descript", "all");
            params.put("bsearch", true);

            ESysConfig loObj = poDao.GetLatestSysConfig();
            if(loObj != null){
                params.put("dTimeStmp", loObj.getTimeStmp());
            }

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlImportSysConfig(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            Log.e(TAG, loResponse.getString("result"));
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = new JSONObject(laJson.getString(x));
                ESysConfig loDetail = poDao.GetSysConfig(loJson.getString("sConfigCd"));

                if(loDetail == null) {
                    ESysConfig info = new ESysConfig();
                    info.setConfigCd(loJson.getString("sConfigCd"));
                    info.setConfigDs(loJson.getString("sConfigDs"));
                    info.setConfigVl(loJson.getString("sConfigVl"));
                    info.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.SaveSysConfig(info);
                    Log.d(TAG, "System config has been saved.");
                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    if (!ldDate1.equals(ldDate2)) {
                        loDetail.setConfigCd(loJson.getString("sConfigCd"));
                        loDetail.setConfigDs(loJson.getString("sConfigDs"));
                        loDetail.setConfigVl(loJson.getString("sConfigVl"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                        poDao.UpdateSysConfig(loDetail);
                        Log.d(TAG, "System config has been updated.");
                    }
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
