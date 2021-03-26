package org.rmj.guanzongroup.onlinecreditapplication.Data;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

public class UploadCreditApp {
    private static final String TAG = UploadCreditApp.class.getSimpleName();

    private final Application application;

    public interface OnUploadLoanApplication{
        void OnUpload();
        void OnSuccess(String ClientName);
        void OnFailed(String message);
    }

    public UploadCreditApp(Application application) {
        this.application = application;
    }

    public void UploadLoanApplication(String TransNox, OnUploadLoanApplication listener){
        new UploadTask(application, listener).execute(TransNox);
    }

    private static class UploadTask extends AsyncTask<String, Void, String>{
        private final Application instance;
        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final RCreditApplication poCreditApp;
        private final OnUploadLoanApplication mListener;
        private ECreditApplication poInfo;

        public UploadTask(Application application, OnUploadLoanApplication listener) {
            this.instance = application;
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poCreditApp = new RCreditApplication(instance);
            this.mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mListener.OnUpload();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String TransNox = strings[0];
            String lsResult;
            try {
                if (poConn.isDeviceConnected()) {
                    poInfo = poCreditApp.getLoanInfoOfTransNox(TransNox);
                    JSONObject params = new JSONObject(poInfo.getDetlInfo());
                    params.put("dCreatedx", poInfo.getClientNm());

                    String lsResponse = WebClient.httpsPostJSon(WebApi.URL_SUBMIT_ONLINE_APPLICATION, params.toString(), poHeaders.getHeaders());
                    if(lsResponse != null) {
                        JSONObject loResponse = new JSONObject(lsResponse);

                        String result = loResponse.getString("result");
                        if(result.equalsIgnoreCase("success")){
                            String lsTransNox = loResponse.getString("sTransNox");
                            poCreditApp.updateSentLoanAppl(poInfo.getTransNox(), lsTransNox);
                        }
                        lsResult = lsResponse;
                    } else {
                        lsResult = AppConstants.SERVER_NO_RESPONSE();
                    }
                } else {
                    lsResult = AppConstants.NO_INTERNET();
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    mListener.OnSuccess(poInfo.getClientNm());
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    mListener.OnFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mListener.OnFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                mListener.OnFailed(e.getMessage());
            }
        }
    }
}
