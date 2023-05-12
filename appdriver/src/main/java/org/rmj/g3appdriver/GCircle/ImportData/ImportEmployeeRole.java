package org.rmj.g3appdriver.GCircle.ImportData;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.os.AsyncTask;

import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.utils.ConnectionUtil;

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

    private static class ImportEmployeeRoleTask extends AsyncTask<String, Void, Boolean>{

        private final EmployeeMaster poUser;
        private final ConnectionUtil poConn;
        private final OnImportEmployeeRoleCallback callback;

        private String message;

        public ImportEmployeeRoleTask(Application instance, OnImportEmployeeRoleCallback callback) {
            this.poUser = new EmployeeMaster(instance);
            this.poConn = new ConnectionUtil(instance);
            this.callback =  callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnRequest();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage();
                    return false;
                }

                if(!poUser.GetUserAuthorizeAccess()){
                    message = poUser.getMessage();
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e){
                e.printStackTrace();
                message = getLocalMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            try{
                if(isSuccess){
                    callback.OnSuccess();
                } else {
                    callback.OnFailed(message);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
