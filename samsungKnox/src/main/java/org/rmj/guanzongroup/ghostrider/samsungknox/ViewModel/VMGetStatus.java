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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.KnoxErrorCode;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.ViewModelCallBack;

public class VMGetStatus extends AndroidViewModel {
    public static final String TAG = VMGetStatus.class.getSimpleName();
    private final ConnectionUtil conn;
    private final HttpHeaders headers;
    private final Application instance;
    private final GCircleApi poApi;

    public VMGetStatus(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poApi = new GCircleApi(instance);
        conn = new ConnectionUtil(application);
        headers = HttpHeaders.getInstance(application);
    }

    public void GetDeviceStatus(String DeviceID, ViewModelCallBack callBack) {
        if (!DeviceID.trim().isEmpty()) {
//            new GetDeviceStatusTask(instance, callBack).execute(DeviceID);
            TaskExecutor.Execute(DeviceID, new OnTaskExecuteListener() {
                @Override
                public void OnPreExecute() {
                    callBack.OnLoadRequest("Status", "Checking device status", false);
                }

                @Override
                public Object DoInBackground(Object args) {
                    String lsString = (String) args;
                    String response = "";
                    try {
                        if (conn.isDeviceConnected()) {
                            JSONObject loJSon = new JSONObject();
                            JSONObject loParam = new JSONObject();
                            loJSon.put("deviceUid", lsString);
                            loJSon.put("pageNum", 0);
                            loJSon.put("pageSize", 30);
                            loParam.put("request", AppConstants.GET_DEVICE_LOG_REQUEST);
                            loParam.put("param", loJSon.toString());
                            Log.e(TAG, loParam.toString());
                            response = WebClient.sendRequest(poApi.getUrlKnox(), loParam.toString(), headers.getHeaders());
                        } else {
                            response = AppConstants.NO_INTERNET();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return response;
                }

                @Override
                public void OnPostExecute(Object object) {
                    String lsString = (String) object;
                    try {
                        JSONObject loResponse = new JSONObject(lsString);
                        String lsResult = loResponse.getString("result");
                        if (lsResult.equalsIgnoreCase("success")) {
                            JSONArray jsonArray = loResponse.getJSONArray("deviceLogs");
                            long maxValue = (long) jsonArray.getJSONObject(jsonArray.length() - 1).get("time");
                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject loJson = new JSONObject(jsonArray.getString(x));

                                long CurrMax = (long) loJson.get("time");
                                if (CurrMax > maxValue) {
                                    maxValue = CurrMax;
                                    callBack.OnRequestSuccess(loJson.toString());
                                }
                            }
                        } else {
                            JSONObject loError = loResponse.getJSONObject("error");
                            String lsMessage = getErrorMessage(loError);
                            String lsErrCode = loError.getString("code");
                            callBack.OnRequestFailed(KnoxErrorCode.getMessage(lsErrCode, lsMessage));
                            Log.e(TAG, lsString);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            callBack.OnRequestFailed("Please enter device id");
        }
    }
}
//    private static class GetDeviceStatusTask extends AsyncTask<String, Void, String> {
//        private final ConnectionUtil conn;
//        private final HttpHeaders headers;
//        private final GCircleApi poApi;
//        private final AppConfigPreference loConfig;
//        private final ViewModelCallBack callBack;
//
//        public GetDeviceStatusTask(Application instance, ViewModelCallBack callBack) {
//            this.conn = new ConnectionUtil(instance);
//            this.headers = HttpHeaders.getInstance(instance);
//            this.loConfig = AppConfigPreference.getInstance(instance);
//            this.poApi = new GCircleApi(instance);
//            this.callBack = callBack;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            callBack.OnLoadRequest("Status", "Checking device status", false);
//            super.onPreExecute();
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        protected String doInBackground(String... string) {
//            String response = "";
//            try {
//                if (conn.isDeviceConnected()) {
//                    JSONObject loJSon = new JSONObject();
//                    JSONObject loParam = new JSONObject();
//                    loJSon.put("deviceUid", string[0]);
//                    loJSon.put("pageNum", 0);
//                    loJSon.put("pageSize", 30);
//                    loParam.put("request", AppConstants.GET_DEVICE_LOG_REQUEST);
//                    loParam.put("param", loJSon.toString());
//                    Log.e(TAG, loParam.toString());
//                    response = WebClient.sendRequest(poApi.getUrlKnox(), loParam.toString(), headers.getHeaders());
//                } else {
//                    response = AppConstants.NO_INTERNET();
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            try {
//                JSONObject loResponse = new JSONObject(s);
//                String lsResult = loResponse.getString("result");
//                if(lsResult.equalsIgnoreCase("success")){
//                    JSONArray jsonArray = loResponse.getJSONArray("deviceLogs");
//                    long maxValue = (long)jsonArray.getJSONObject(jsonArray.length()-1).get("time");
//                    for(int x = 0; x < jsonArray.length(); x++){
//                        JSONObject loJson = new JSONObject(jsonArray.getString(x));
//
//                        long CurrMax = (long)loJson.get("time");
//                        if(CurrMax > maxValue){
//                            maxValue = CurrMax;
//                            callBack.OnRequestSuccess(loJson.toString());
//                        }
//                    }
//                } else {
//                    JSONObject loError = loResponse.getJSONObject("error");
//                    String lsMessage = getErrorMessage(loError);
//                    String lsErrCode = loError.getString("code");
//                    callBack.OnRequestFailed(KnoxErrorCode.getMessage(lsErrCode, lsMessage));
//                    Log.e(TAG, s);
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//            this.cancel(false);
//        }
//    }
//}