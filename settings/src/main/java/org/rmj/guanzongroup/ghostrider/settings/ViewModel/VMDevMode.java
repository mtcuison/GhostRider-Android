/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 8/14/21 11:33 AM
 * project file last modified : 8/14/21 11:33 AM
 */

package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.rmj.g3appdriver.utils.WebApi.URL_DOWNLOAD_TEST_UPDATE;

public class VMDevMode extends AndroidViewModel {

    private final Application instance;
    private final SessionManager poSession;
    private final AppConfigPreference poConfig;

    public interface OnRestoreCallback{
        void OnRestore();
    }

    public VMDevMode(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSession = new SessionManager(application);
        this.poConfig = AppConfigPreference.getInstance(application);
    }

    public void setEmployeeLevel(String val){
        poSession.setEmployeeLevel(val);
    }

    public void setDepartmentID(String val){
        poSession.setDepartment(val);
    }

    public void setDebugMode(boolean val){
        poConfig.setIsTesting(val);
    }

    public boolean getIsDebugMode(){
        return poConfig.isTesting_Phase();
    }

    public String getDepartment(){
        return poSession.getDeptID();
    }

    public String getEmployeeLevel(){
        return poSession.getEmployeeLevel();
    }

    public void RestoreDefault(OnRestoreCallback callback){
        new RestoreSessionInfoTask(instance, callback).execute();
    }

    private static class RestoreSessionInfoTask extends AsyncTask<Void, Void, Void>{

        private final SessionManager poSession;
        private final AppConfigPreference poConfig;
        private final REmployee poEmployee;
        private final OnRestoreCallback callback;

        public RestoreSessionInfoTask(Application application, OnRestoreCallback callback){
            poSession = new SessionManager(application);
            poConfig = AppConfigPreference.getInstance(application);
            poEmployee = new REmployee(application);
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            EEmployeeInfo loEmployee = poEmployee.getUserNonLiveData();
            poSession.setEmployeeLevel(loEmployee.getEmpLevID());
            poSession.setDepartment(loEmployee.getDeptIDxx());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            callback.OnRestore();
            super.onPostExecute(aVoid);
        }
    }

    public interface SystemUpateCallback{
        void OnDownloadUpdate(String title, String message);
        void OnProgressUpdate(int progress);
        void OnFinishDownload(Intent intent);
        void OnFailedDownload(String message);
    }

    public void DownloadUpdate(SystemUpateCallback callback){
        new DownloadUpdate(instance, callback).execute();
    }

    public static class DownloadUpdate extends AsyncTask<String, Integer, String>{
        private static final String TAG = DownloadUpdate.class.getSimpleName();
        private final Application instance;
        private final SystemUpateCallback callback;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final String PATH;
        private final WebApi poApi;

        public DownloadUpdate(Application application, SystemUpateCallback callback) {
            this.instance = application;
            this.callback = callback;
            this.poConn = new ConnectionUtil(application);
            this.poHeaders = HttpHeaders.getInstance(application);
            PATH = application.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/";
            this.poApi = new WebApi(application);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownloadUpdate("System Update", "Downloading updates. Please wait...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String lsResult;
            try {
                if(poConn.isWifiConnected()) {
                    if (poConn.isDeviceConnected()) {
                        URL url = new URL(URL_DOWNLOAD_TEST_UPDATE);
                        HttpURLConnection c = (HttpURLConnection) url.openConnection();
                        c.setRequestMethod("GET");
                        c.setDoOutput(true);
                        c.connect();

                        File file = new File(PATH);
                        file.mkdirs();
                        File outputFile = new File(file, "gRider.apk");
                        if (outputFile.exists()) {
                            outputFile.delete();
                        }
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        BigDecimal fileLength = BigDecimal.valueOf(c.getContentLength());
                        InputStream is = c.getInputStream();

                        byte[] buffer = new byte[4096];
                        BigDecimal total = BigDecimal.valueOf(4096);
                        int len1;
                        while ((len1 = is.read(buffer)) != -1) {
                            total = total.add(BigDecimal.valueOf(len1));
                            if (fileLength.intValue() > 0) {
                                BigDecimal percentage = percentage(total, fileLength).multiply(ONE_HUNDRED);
                                publishProgress(percentage.intValue());
                            }
                            fos.write(buffer, 0, len1);
                        }
                        fos.close();
                        is.close();
                        lsResult = AppConstants.APPROVAL_CODE_GENERATED("Download Complete. Ready to install.");
                    } else {
                        lsResult = AppConstants.NO_INTERNET();
                    }
                } else {
                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("You need wifi connection in order to download updates.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            callback.OnProgressUpdate(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if (lsResult.equalsIgnoreCase("success")) {
                    String PATH = instance.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/gRider.apk";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    Uri loUriFile = FileProvider.getUriForFile(instance, "org.rmj.guanzongroup.ghostrider.epacss" + ".provider", new File(PATH));
                    intent.setDataAndType(loUriFile, "application/vnd.android" + ".package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    callback.OnFinishDownload(intent);
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    callback.OnFailedDownload(loError.getString("message"));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

        public static BigDecimal percentage(BigDecimal base, BigDecimal pct){
            return base.divide(pct, 2, RoundingMode.HALF_UP);
        }
    }
}
