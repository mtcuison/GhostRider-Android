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

package org.rmj.g3appdriver.GRider.ImportData;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static org.rmj.g3appdriver.utils.WebApi.IMPORT_BRANCH_PERFORMANCE;

public class Import_BranchPerformance implements ImportInstance {
    public static final String TAG = Import_BranchPerformance.class.getSimpleName();

    private final Application instance;
    private final ArrayList<String> poPeriodc;
    private String sAreaCode;

    public Import_BranchPerformance(Application application) {
        Log.e(TAG, "Initialized.");
        this.instance = application;
        this.poPeriodc = new ArrayList<>();
        setUpPeriodList();
    }

    public void setsAreaCode(String sAreaCode) {
        this.sAreaCode = sAreaCode;
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        JSONObject loJson = new JSONObject();
        try{
//            loJson.put("period", "201911");
            new ImportBranchTask(instance, callback).execute(poPeriodc);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpPeriodList() {
        try {
            Calendar loCalendr = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));
            final int MONTH_MIN = 1;
            int lnMontNow = loCalendr.getInstance().get(Calendar.MONTH) + 1;
            int lnMonthMax;
            int lnRefYear;

            if(lnMontNow == MONTH_MIN) {
                lnRefYear = loCalendr.getInstance().get(Calendar.YEAR) - 1;
                lnMonthMax = 12; // Up to December
            } else {
                lnRefYear = loCalendr.getInstance().get(Calendar.YEAR);
                lnMonthMax = lnMontNow - 1; // Set previous Month
            }

            for (int x = 1; x <= lnMonthMax; x++) {
                String lsMonth = x < 10 ? "0" + x : String.valueOf(x);
                String lsPeriod = lnRefYear + lsMonth;
                Log.e(TAG + " Period", lsPeriod);
                poPeriodc.add(lsPeriod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ImportBranchTask extends AsyncTask<ArrayList<String>, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders loHeaders;
        private final RBranchPerformance loDatabse;
        private final ConnectionUtil loConn;
        private final REmployee poUser;

        public ImportBranchTask(Application application, ImportDataCallback callback) {
            this.callback = callback;
            this.loDatabse = new RBranchPerformance(application);
            this.loConn = new ConnectionUtil(application);
            this.loHeaders = HttpHeaders.getInstance(application);
            this.poUser = new REmployee(application);
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(ArrayList<String>... strings) {
            String response = "";
            try {
                if(loConn.isDeviceConnected()) {

                    if(strings[0].size() > 0) {
                        for(int x = 0; x < strings[0].size(); x++) {
                            JSONObject loJSon = new JSONObject();
                            loJSon.put("period", strings[0].get(x));
                            loJSon.put("areacd", poUser.getUserAreaCode());
                            response = WebClient.httpsPostJSon(IMPORT_BRANCH_PERFORMANCE, loJSon.toString(), loHeaders.getHeaders());
                            if(response == null){
                                response = AppConstants.SERVER_NO_RESPONSE();
                            } else {
                                JSONObject loJson = new JSONObject(response);
                                Log.e(TAG, loJson.getString("result"));
                                String lsResult = loJson.getString("result");
                                if (lsResult.equalsIgnoreCase("success")) {
                                    JSONArray laJson = loJson.getJSONArray("detail");
                                    Log.e(TAG, laJson.toString());
                                    saveDataToLocal(laJson);
                                } else {
                                    JSONObject loError = loJson.getJSONObject("error");
                                    String message = loError.getString("message");
                                    callback.OnFailedImportData(message);
                                }
                            }
                            Thread.sleep(1000);
                        }
                    }
                } else {
                    response = AppConstants.NO_INTERNET();
                }
            } catch (Exception e) {
                Log.e(TAG, Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
            }
            return response;
        }
        void saveDataToLocal(JSONArray laJson) throws Exception{
            List<EBranchPerformance> list = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EBranchPerformance info = new EBranchPerformance();
                int min = loJson.getInt("nSPGoalxx")/2;
                int max = loJson.getInt("nSPGoalxx");
                int random_sp = (int)Math.floor(Math.random()*(max-min+1000)*100);
                int random_jo = (int)Math.floor(Math.random()*(max-min+1000)*100);
                int random_mc = (int)Math.floor(Math.random()*(loJson.getInt("nMCActual")-(loJson.getInt("nMCActual")/2)+1)*(loJson.getInt("nMCActual")/2));
                info.setPeriodxx(loJson.getString("sPeriodxx"));
                info.setBranchCd(loJson.getString("sBranchCd"));
                info.setBranchNm(loJson.getString("sBranchNm"));
//                info.setMCGoalxx(loJson.getInt("nMCGoalxx"));
//                info.setSPGoalxx(loJson.getInt("nSPGoalxx"));
                info.setSPGoalxx(random_sp);
//                info.setJOGoalxx(loJson.getInt("nJOGoalxx"));
                info.setJOGoalxx(random_jo);
                info.setLRGoalxx(loJson.getInt("nLRGoalxx"));
//                info.setMCGoalxx(loJson.getInt("nMCActual"));
                info.setMCActual(loJson.getInt("nMCActual"));
                info.setMCGoalxx(random_mc);
                info.setSPActual(loJson.getInt("nSPActual"));
                info.setLRActual(loJson.getInt("nLRActual"));
                list.add(info);
            }
            loDatabse.insertBulkData(list);
        }
//        void saveDataToLocal(JSONArray laJson) throws Exception{
//            List<EBranchPerformance> branchInfo = new ArrayList<>();
//            for(int x = 0; x < laJson.length(); x++){
//                JSONObject loJson = new JSONObject(laJson.getString(x));
//                EBranchPerformance info = new EBranchPerformance();
//                info.setBranchCd(loJson.getString("sBranchCd"));
//                info.setBranchNm(loJson.getString("sBranchNm"));
//                info.setMCGoalxx(Integer.parseInt(loJson.getString("nMCGoalxx")));
//                info.setSPGoalxx(Float.parseFloat(loJson.getString("nSPGoalxx")));
//                info.setJOGoalxx(Integer.parseInt(loJson.getString("nJOGoalxx")));
//                info.setLRGoalxx(Float.parseFloat(loJson.getString("nLRGoalxx")));
//                info.setMCActual(Integer.parseInt(loJson.getString("nMCActual")));
//                info.setSPActual(Float.parseFloat(loJson.getString("nSPActual")));
//                info.setLRActual(Float.parseFloat(loJson.getString("nLRActual")));
//                branchInfo.add(info);
//            }
//            loDatabse.insertBulkData(branchInfo);
//            Log.e(TAG, "Branch info has been save to local.");
//        }
    }
}
