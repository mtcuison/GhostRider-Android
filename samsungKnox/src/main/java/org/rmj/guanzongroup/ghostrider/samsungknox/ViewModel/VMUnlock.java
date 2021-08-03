/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.samsungKnox
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.samsungknox.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.KnoxErrorCode;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.ViewModelCallBack;

public class VMUnlock extends AndroidViewModel {
    public static final String TAG = VMUnlock.class.getSimpleName();
    private final ConnectionUtil conn;
    private final HttpHeaders headers;

    public VMUnlock(@NonNull Application application) {
        super(application);
        this.conn = new ConnectionUtil(application);
        this.headers = HttpHeaders.getInstance(application);
    }

    public void UnlockDevice(String DeviceID, ViewModelCallBack callBack){
        if(!DeviceID.trim().isEmpty()) {
            new UnlockRequestTask(conn, headers, callBack).execute(DeviceID);
        } else {
            callBack.OnRequestFailed("Please enter device id");
        }
    }

    private static class UnlockRequestTask extends AsyncTask<String, Void, String> {
        private final ConnectionUtil conn;
        private final HttpHeaders headers;
        private final ViewModelCallBack callBack;

        public UnlockRequestTask(ConnectionUtil conn, HttpHeaders headers, ViewModelCallBack callBack) {
            this.conn = conn;
            this.headers = headers;
            this.callBack = callBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callBack.OnLoadRequest("Samsung Knox", "Getting unlock PIN. Please wait...", false);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... string) {
            String response;
            try {
                if (conn.isDeviceConnected()) {
                    JSONObject loJSon = new JSONObject();
                    JSONObject loParam = new JSONObject();
                    loJSon.put("deviceUid", string[0]);
                    loParam.put("request", AppConstants.UNLOCK_REQUEST);
                    loParam.put("param", loJSon.toString());
                    response = WebClient.sendRequest(WebApi.URL_KNOX, loParam.toString(), headers.getHeaders());
                } else {
                    response = AppConstants.NO_INTERNET();
                }
            } catch (Exception e){
                e.printStackTrace();
                response = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loResponse = new JSONObject(s);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callBack.OnRequestSuccess("Device unlock successfully");
//                    JSONArray jsonArray = loResponse.getJSONArray("pinNumber");
//                    callBack.OnRequestSuccess(jsonArray.getString(0));
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    String lsErrCode = loError.getString("code");
                    callBack.OnRequestFailed(KnoxErrorCode.getMessage(lsErrCode, lsMessage));
                }
            } catch (Exception e){
                e.printStackTrace();
                callBack.OnRequestFailed(e.getMessage());
            }
            this.cancel(false);
        }
    }
}