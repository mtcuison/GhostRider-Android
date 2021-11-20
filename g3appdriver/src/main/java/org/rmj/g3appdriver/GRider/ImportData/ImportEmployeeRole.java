package org.rmj.g3appdriver.GRider.ImportData;

import static org.rmj.g3appdriver.utils.WebApi.REQUEST_USER_ACCESS;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeRole;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebClient;

public class ImportEmployeeRole {
    private static final String TAG = ImportEmployeeRole.class.getSimpleName();

    private final Application instance;

    public interface OnImportEmployeeRoleCallback{
        void OnRequest();
        void OnSuccess();
        void OnFailed(String message);
    }

    public ImportEmployeeRole(Application application) {
        this.instance = application;
    }

    public void RefreshEmployeeRole(OnImportEmployeeRoleCallback callback){
        new ImportEmployeeRoleTask(instance, callback).execute();
    }

    private static class ImportEmployeeRoleTask extends AsyncTask<String, Void, String>{

        private final REmployeeRole poRole;
        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final OnImportEmployeeRoleCallback callback;

        public ImportEmployeeRoleTask(Application instance, OnImportEmployeeRoleCallback callback) {
            this.poRole = new REmployeeRole(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.callback =  callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnRequest();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String lsResult;
            try{
                lsResult = WebClient.httpsPostJSon(REQUEST_USER_ACCESS, new JSONObject().toString(), poHeaders.getHeaders());
                if (lsResult == null) {
                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Server no response while downloading authorize features.");
                } else {
                    JSONObject loResponse = new JSONObject(lsResult);
                    if (loResponse.getString("result").equalsIgnoreCase("success")) {
                        JSONArray loArr = loResponse.getJSONArray("payload");
                        poRole.clearEmployeeRole();
                        if(!poRole.SaveEmployeeRole(loArr)){
                            lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Unable to same employee authorize features");
                        } else {
                            lsResult = AppConstants.APPROVAL_CODE_GENERATED("Employee authorize features has been successfully imported.");
                        }
                    }
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
            try{
                JSONObject loJSon = new JSONObject(s);
                String result = loJSon.getString("result");
                if(result.equalsIgnoreCase("success")){
                    callback.OnSuccess();
                } else {
                    JSONObject loError = loJSon.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailed(message);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
