/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 6/28/21 11:12 AM
 * project file last modified : 6/28/21 11:12 AM
 */

package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
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

import static org.rmj.g3appdriver.utils.WebApi.URL_DOWNLOAD_UPDATE;

public class VMCheckUpdate extends AndroidViewModel {

    private final Application instance;
    private final AppConfigPreference poConfig;

    private final MutableLiveData<String> psVersion = new MutableLiveData<>();

    public VMCheckUpdate(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.psVersion.setValue(poConfig.getVersionInfo());
    }

    public interface CheckUpdateCallback{
        void OnCheck(String title, String message);
        void OnSuccess();
        void OnUpdateAvailable();
        void OnFailed(String message);
    }

    public interface SystemUpateCallback{
        void OnDownloadUpdate(String title, String message);
        void OnProgressUpdate(int progress);
        void OnFinishDownload(Intent intent);
        void OnFailedDownload(String message);
    }

    public LiveData<String> getVersionInfo(){
        return psVersion;
    }

    public void CheckUpdate(CheckUpdateCallback callback){
        new CheckUpdate(instance, callback).execute();
    }

    private static class CheckUpdate extends AsyncTask<String, Void, String>{
        private final CheckUpdateCallback callback;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final WebApi poApi;

        public CheckUpdate(Application application, CheckUpdateCallback callback){
            this.callback = callback;
            this.poConn = new ConnectionUtil(application);
            this.poHeaders = HttpHeaders.getInstance(application);
            this.poApi = new WebApi(application);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnCheck("System Update", "Checking system update. Please wait...");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String lsResult;
            try{
                JSONObject param = new JSONObject();
                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.NO_INTERNET();
                } else {
                    lsResult = WebClient.sendRequest(poApi.URL_CHANGE_PASSWORD(), param.toString(), poHeaders.getHeaders());
                    if(lsResult == null){
                        lsResult = AppConstants.SERVER_NO_RESPONSE();
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
            try {
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccess();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.OnFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailed(e.getMessage());
            }
        }
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
            PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
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
                        URL url = new URL(URL_DOWNLOAD_UPDATE);
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
                    String PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/gRider.apk";
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
