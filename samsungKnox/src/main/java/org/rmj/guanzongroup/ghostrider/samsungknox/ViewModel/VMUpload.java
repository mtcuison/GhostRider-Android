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

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.ViewModelCallBack;

public class VMUpload extends AndroidViewModel {
    public static final String TAG = VMUpload.class.getSimpleName();
    private final ConnectionUtil conn;
    private final HttpHeaders headers;
    private final Application instance;

    public VMUpload(@NonNull Application application) {
        super(application);
        this.instance = application;
        conn = new ConnectionUtil(application);
        headers = HttpHeaders.getInstance(application);
    }

    public void UploadDevice(String DeviceID, ViewModelCallBack callBack){
        new UploadTask(instance, callBack).execute(DeviceID);
    }

    private static class UploadTask extends AsyncTask<String, Void, String>{
        private final ConnectionUtil conn;
        private final HttpHeaders headers;
        private final GCircleApi poApi;
        private final AppConfigPreference loConfig;
        private final ViewModelCallBack callBack;

        public UploadTask(Application instance, ViewModelCallBack callBack) {
            this.conn = new ConnectionUtil(instance);
            this.headers = HttpHeaders.getInstance(instance);
            this.loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new GCircleApi(instance);
            this.callBack = callBack;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                if (conn.isDeviceConnected()) {
                    JSONObject loJSon = new JSONObject();
                    response = WebClient.sendRequest(poApi.getUrlKnox(), loJSon.toString(), headers.getHeaders());
                } else {
                    response = AppConstants.NO_INTERNET();
                }
            } catch (Exception e){
                e.printStackTrace();
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
                    callBack.OnRequestSuccess("Device ID has been uploaded successfully");
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = getErrorMessage(loError);
                    callBack.OnRequestFailed(lsMessage);
                    Log.e(TAG, s);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(false);
        }
    }
}