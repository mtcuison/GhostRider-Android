/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 6/23/21 2:03 PM
 * project file last modified : 6/23/21 1:56 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
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
import java.net.HttpURLConnection;
import java.net.URL;

public class VMSettings extends AndroidViewModel {

    private final Application instance;

    private final MutableLiveData<Boolean> pbGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> locGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> camGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> phGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> storageGranted = new MutableLiveData<>();
    private final MutableLiveData<String[]> paPermisions = new MutableLiveData<>();
    public MutableLiveData<String[]> locationPermissions = new MutableLiveData<>();
    public MutableLiveData<String[]> cameraPermissions = new MutableLiveData<>();
    public MutableLiveData<String[]> phonePermissions = new MutableLiveData<>();
    public MutableLiveData<String[]> storagePermission = new MutableLiveData<>();

    private final MutableLiveData<String> cameraSummarry = new MutableLiveData<>();

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

    public interface ChangePasswordCallback{
        void OnLoad(String Title, String Message);
        void OnSuccess();
        void OnFailed(String message);
    }

    public VMSettings(@NonNull Application application) {
        super(application);
        this.instance = application;
        paPermisions.setValue(new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
        cameraPermissions.setValue(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,});
        locationPermissions.setValue(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
        phonePermissions.setValue(new String[]{
                Manifest.permission.READ_PHONE_STATE});

        storagePermission.setValue(new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE});

        locGranted.setValue(hasPermissions(application.getApplicationContext(), locationPermissions.getValue()));
        camGranted.setValue(hasPermissions(application.getApplicationContext(), cameraPermissions.getValue()));
        phGranted.setValue(hasPermissions(application.getApplicationContext(), phonePermissions.getValue()));
        storageGranted.setValue(hasPermissions(application.getApplicationContext(), storagePermission.getValue()));
    }

    public void setCameraSummary(String camSummary){
        this.cameraSummarry.setValue(camSummary);
    }
    public LiveData<String> getCameraSummary(){
        return this.cameraSummarry;
    }


    public LiveData<Boolean> isPermissionsGranted(){
        return pbGranted;
    }
    public LiveData<Boolean> isCamPermissionGranted(){
        return camGranted;
    }
    public LiveData<Boolean> isLocPermissionGranted(){
        return locGranted;
    }
    public LiveData<Boolean> isStoragePermissionGranted(){
        return storageGranted;
    }
    public LiveData<Boolean> isPhPermissionGranted(){
        return phGranted;
    }

    public LiveData<String[]> getPermisions(){
        return paPermisions;
    }

    public LiveData<String[]> getLocPermissions(){
        return locationPermissions;
    }
    public LiveData<String[]> getCamPermissions(){
        return cameraPermissions;
    }
    public LiveData<String[]> getPhPermissions(){
        return phonePermissions;
    }
    public LiveData<String[]> getStoragePermission(){
        return storagePermission;
    }

    public void setPermissionsGranted(boolean isGranted){
        this.pbGranted.setValue(isGranted);
    }
    public void setLocationPermissionsGranted(boolean isGranted){
        this.locGranted.setValue(isGranted);
    }

    public void setCamPermissionsGranted(boolean isGranted){
        this.camGranted.setValue(isGranted);
    }
    public void setPhonePermissionsGranted(boolean isGranted){
        this.phGranted.setValue(isGranted);
    }

    public void setStoragePermissionGranted(boolean isGranted){
        this.storageGranted.setValue(isGranted);
    }

    private static boolean hasPermissions(Context context, String... permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && permissions!=null ){
            for (String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    public void CheckUpdate(CheckUpdateCallback callback){
        new CheckUpdateTask(instance, callback).execute();
    }

    private static class CheckUpdateTask extends AsyncTask<String, Void, String>{
        private final Application instance;
        private final CheckUpdateCallback callback;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final WebApi poApi;

        public CheckUpdateTask(Application instance, CheckUpdateCallback callback) {
            this.instance = instance;
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
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
                    lsResult = WebClient.sendRequest(poApi.getUrlChangePassword(), param.toString(), poHeaders.getHeaders());
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

    public void ChangePassword(String Old, String New, ChangePasswordCallback callback){
        JSONObject param = new JSONObject();
        try {
            param.put("oldpswd", Old);
            param.put("newpswd", New);
            new ChangePasswordTask(instance, callback).execute(param);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static class ChangePasswordTask extends AsyncTask<JSONObject, Void, String>{
        private final Application instance;
        private final ChangePasswordCallback callback;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final WebApi poApi;

        public ChangePasswordTask(Application application, ChangePasswordCallback callback) {
            this.instance = application;
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String lsResult;
            try{
                JSONObject param = jsonObjects[0];
                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.NO_INTERNET();
                } else {
                    lsResult = WebClient.sendRequest(poApi.getUrlChangePassword(), param.toString(), poHeaders.getHeaders());
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
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad("Change Password", "Updating account. Please wait...");
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
        new DownloadUpdateTask(instance, callback).execute();
    }

    private static class DownloadUpdateTask extends AsyncTask<String, Integer, String>{
        private final Application instance;
        private final SystemUpateCallback callback;
        private final String PATH;
        private final WebApi poApi;
        private final ConnectionUtil poConn;

        public DownloadUpdateTask(Application application, SystemUpateCallback callback){
            this.instance = application;
            this.callback = callback;
            this.poApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
            PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
            this.poConn = new ConnectionUtil(instance);
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
                if(poConn.isDeviceConnected()) {
                    URL url = new URL(poApi.getUrlDownloadUpdate());
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
                    int fileLength = c.getContentLength();
                    InputStream is = c.getInputStream();

                    byte[] buffer = new byte[1024];
                    int len1;
                    while ((len1 = is.read(buffer)) != -1) {
                        if(fileLength > 0){
                            publishProgress(len1 * 100 / fileLength);
                        }
                        fos.write(buffer, 0, len1);
                    }
                    fos.close();
                    is.close();
                    lsResult = "success";
                } else {
                    lsResult = "error";
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResult = "error";
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
            if(s.equalsIgnoreCase("success")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(PATH)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
                callback.OnFinishDownload(intent);
            } else {
                callback.OnFailedDownload("Unknown error occur.");
            }
        }
    }
}