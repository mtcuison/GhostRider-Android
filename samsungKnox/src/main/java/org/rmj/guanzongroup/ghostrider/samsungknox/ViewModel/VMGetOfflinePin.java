package org.rmj.guanzongroup.ghostrider.samsungknox.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.KnoxErrorCode;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.samsungknox.Model.PinModel;

public class VMGetOfflinePin extends AndroidViewModel {
    public static final String TAG = VMGetOfflinePin.class.getSimpleName();
    private final ConnectionUtil conn;
    private final HttpHeaders header;
    private final LoadDialog dialog;

    public VMGetOfflinePin(@NonNull Application application) {
        super(application);
        conn = new ConnectionUtil(application);
        header = HttpHeaders.getInstance(application);
        dialog = new LoadDialog(application);
    }

    public void UnlockWithPasskey(PinModel model, ViewModelCallBack callBack){
        if(model.isParameterValid()){
            new GetOfflinePINTask(conn, header, dialog, callBack).execute(model);
        } else {
            callBack.OnRequestFailed(model.getPsMessage());
        }
    }


    private static class GetOfflinePINTask extends AsyncTask<PinModel, Void, String>{
        private final ConnectionUtil conn;
        private final HttpHeaders header;
        private final LoadDialog dialog;
        private final ViewModelCallBack callBack;

        public GetOfflinePINTask(ConnectionUtil conn, HttpHeaders header, LoadDialog dialog, ViewModelCallBack callBack) {
            this.conn = conn;
            this.header = header;
            this.dialog = dialog;
            this.callBack = callBack;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callBack.OnLoadRequest("Samsung Knox", "Getting unlock PIN. Please wait...", false);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(PinModel... pinModels) {
            String response = "";
            try {
                if (conn.isDeviceConnected()) {
                    JSONObject loJSon = new JSONObject();
                    JSONObject loParam = new JSONObject();
                    loJSon.put("deviceUid", pinModels[0].getDeviceID());
                    loJSon.put("challenge", pinModels[0].getDeviceID());
                    loParam.put("request", AppConstants.OFFLINE_PIN_REQUEST);
                    loParam.put("param", loJSon.toString());
                    Log.e(TAG, loParam.toString());
                    response = WebClient.httpsPostJSon(WebApi.URL_KNOX, loParam.toString(), header.getHeaders());
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
                    JSONArray jsonArray = loResponse.getJSONArray("pinNumber");
                    callBack.OnRequestSuccess(jsonArray.getString(0));
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
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