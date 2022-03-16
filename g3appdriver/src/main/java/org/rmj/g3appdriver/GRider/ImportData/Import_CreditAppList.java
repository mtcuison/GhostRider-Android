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
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.Arrays;
import java.util.Objects;

public class Import_CreditAppList implements ImportInstance{
    private static final String TAG = Import_CreditAppList.class.getSimpleName();
    private final Application instance;

    private final RCreditApplication db;
    private final ConnectionUtil loConnectx;
    private final HttpHeaders loHeaders;
    private final REmployee poUser;
    private String brnCode;
    public Import_CreditAppList(Application application){
        this.instance = application;
        this.db = new RCreditApplication(application);
        this.loConnectx = new ConnectionUtil(application);
        this.loHeaders = HttpHeaders.getInstance(application);
        this.poUser = new REmployee(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try {
            JSONObject loJson = new JSONObject();
            loJson.put("bycode", true);
            loJson.put("value", poUser.getEmployeeInfo().getValue().getBranchCD());
            new ImportDataTask(instance, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportDataTask extends AsyncTask<JSONObject, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders headers;
        private final ConnectionUtil conn;
        private final RBranchLoanApplication poLoan;
        private final WebApi poApi;

        public ImportDataTask(Application instance, ImportDataCallback callback) {
            this.headers = HttpHeaders.getInstance(instance);
            this.conn = new ConnectionUtil(instance);
            this.poLoan = new RBranchLoanApplication(instance);
            this.callback = callback;
            this.poApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(poApi.getUrlDownloadCreditOnlineApp(), jsonObjects[0].toString(), headers.getHeaders());
                    JSONObject loJson = new JSONObject(Objects.requireNonNull(response));
                    Log.e(TAG, loJson.getString("result"));
                    String lsResult = loJson.getString("result");
                    if(lsResult.equalsIgnoreCase("success")){
                        JSONArray laJson = loJson.getJSONArray("detail");
                        if(!poLoan.insertBranchApplicationInfos(laJson)){
                            response = AppConstants.ERROR_SAVING_TO_LOCAL();
                        }
                    } else {
                        JSONObject loError = loJson.getJSONObject("error");
                        String message = loError.getString("message");
                        callback.OnFailedImportData(message);
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
    }
}
