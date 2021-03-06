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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.KnoxErrorCode;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.samsungknox.Model.ActivationModel;

public class VMActivate extends AndroidViewModel {
    private static final String TAG = VMActivate.class.getSimpleName();
    private final ConnectionUtil conn;
    private final HttpHeaders headers;
    private final LoadDialog dialog;

    public VMActivate(@NonNull Application application) {
        super(application);
        conn = new ConnectionUtil(application);
        headers = HttpHeaders.getInstance(application);
        dialog = new LoadDialog(application);
    }

    public void ActivateDevice(ActivationModel model, ViewModelCallBack callBack){
        if(model.isDeviceValid()) {
            new ActivationTask(conn, headers, dialog, callBack).execute(model);
        } else {
            callBack.OnRequestFailed(model.getPsMessage());
        }
    }

    private static class ActivationTask extends AsyncTask<ActivationModel, Void, String>{
        private final ConnectionUtil conn;
        private final HttpHeaders headers;
        private final LoadDialog dialog;
        private final ViewModelCallBack callBack;

        public ActivationTask(ConnectionUtil conn, HttpHeaders headers, LoadDialog dialog, ViewModelCallBack callBack) {
            this.conn = conn;
            this.headers = headers;
            this.dialog = dialog;
            this.callBack = callBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callBack.OnLoadRequest("Samsung Knox", "Activating. Please wait...", false);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(ActivationModel... activationModels) {
            String response = "";
            try {
                if (conn.isDeviceConnected()) {
                    JSONObject loJSon = new JSONObject();
                    JSONObject loParam = new JSONObject();
                    loJSon.put("deviceUid", activationModels[0].getDeviceID());
                    loJSon.put("approveComment", activationModels[0].getRemarksx());
                    loParam.put("request", AppConstants.ACTIVATE_REQUEST);
                    loParam.put("param", loJSon.toString());
                    Log.e(TAG, loParam.toString());
                    response = WebClient.httpsPostJSon(WebApi.URL_KNOX, loParam.toString(), headers.getHeaders());
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
                    callBack.OnRequestSuccess("Device ID has been activated successfully");
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    Log.e(TAG, lsMessage);
                    String lsErrCode = loError.getString("code");
                    callBack.OnRequestFailed(KnoxErrorCode.getMessage(lsErrCode, lsMessage));
                    Log.e(TAG, s);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(false);
        }
    }
}