/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLocationSysLog;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.List;

public class VMSelfieLogin extends AndroidViewModel {
    private static final String TAG = VMSelfieLogin.class.getSimpleName();
    private final Application instance;
    private final RImageInfo poImage;
    private final RLogSelfie poLog;
    private final RBranch pobranch;
    private final SessionManager poSession;
    private final REmployee poUser;

    private final MutableLiveData<Boolean> pbGranted = new MutableLiveData<>();
    private final MutableLiveData<String[]> paPermisions = new MutableLiveData<>();
    public interface OnLoginTimekeeperListener{
        void OnLogin();
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public VMSelfieLogin(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poImage = new RImageInfo(instance);
        this.poUser = new REmployee(instance);
        this.poLog = new RLogSelfie(instance);
        this.pobranch = new RBranch(instance);
        this.poSession = new SessionManager(instance);
        paPermisions.setValue(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
        pbGranted.setValue(hasPermissions(application.getApplicationContext(), paPermisions.getValue()));
    }
    public LiveData<Boolean> isPermissionsGranted(){
        return pbGranted;
    }

    public LiveData<String[]> getPermisions(){
        return paPermisions;
    }
    public void setPermissionsGranted(boolean isGranted){
        this.pbGranted.setValue(isGranted);
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
    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }

    public LiveData<EBranchInfo> getBranchInfo(String BranchCD){
        return pobranch.getBranchInfo(BranchCD);
    }

    public LiveData<List<ELog_Selfie>> getAllEmployeeTimeLog(){
        return poLog.getAllEmployeeTimeLog();
    }
    public LiveData<List<ELog_Selfie>> getCurrentTimeLog(){
        return poLog.getCurrentTimeLog(AppConstants.CURRENT_DATE);
    }

    public String getEmployeeLevel(){
        return poSession.getEmployeeLevel();
    }

    public LiveData<List<String>> getLastLogDate(){
        return poLog.getLastLogDate();
    }

    public LiveData<List<EBranchInfo>> getAreaBranchList(){
        return pobranch.getAreaBranchList();
    }

    public void loginTimeKeeper(ELog_Selfie selfieLog, EImageInfo loImage, OnLoginTimekeeperListener callback){
        try {
            loImage.setTransNox(poImage.getImageNextCode());
            poImage.insertImageInfo(loImage);
            selfieLog.setTransNox(poLog.getLogNextCode());
            selfieLog.setReqCCntx("0");
            poLog.insertSelfieLog(selfieLog);

            JSONObject loJson = new JSONObject();
            loJson.put("sEmployID", selfieLog.getEmployID());
            loJson.put("dLogTimex", selfieLog.getLogTimex());
            loJson.put("nLatitude", selfieLog.getLatitude());
            loJson.put("nLongitud", selfieLog.getLongitud());

            new LoginTimekeeperTask(loImage, selfieLog, instance, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    private static class LoginTimekeeperTask extends AsyncTask<JSONObject, Void, String>{
        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final RImageInfo poImage;
        private final RLogSelfie poLog;
        private final EImageInfo poImageInfo;
        private final ELog_Selfie selfieLog;
        private final SessionManager poUser;
        private final Telephony poDevice;
        private final RLocationSysLog poSysLog;
        private final OnLoginTimekeeperListener callback;
        private final Application application;
        private final AppConfigPreference poConfig;

        public LoginTimekeeperTask(EImageInfo foImage, ELog_Selfie logInfo, Application instance, OnLoginTimekeeperListener callback){
            this.poImageInfo = foImage;
            this.selfieLog = logInfo;
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poImage = new RImageInfo(instance);
            this.poLog = new RLogSelfie(instance);
            this.poUser = new SessionManager(instance);
            this.poDevice = new Telephony(instance);
            this.poSysLog = new RLocationSysLog(instance);
            this.callback = callback;
            this.application = instance;
            this.poConfig = AppConfigPreference.getInstance(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLogin();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... loJson) {
            String lsResult = "";
            try{
                EGLocatorSysLog loSysLog = new EGLocatorSysLog();
                loSysLog.setUserIDxx(poUser.getUserID());
                loSysLog.setTransact(new AppConstants().DATE_MODIFIED);
                loSysLog.setLongitud(selfieLog.getLongitud());
                loSysLog.setLatitude(selfieLog.getLatitude());
                loSysLog.setDeviceID(poDevice.getDeviceID());
                loSysLog.setSendStat("0");
                loSysLog.setTimeStmp(new AppConstants().DATE_MODIFIED);
                poSysLog.saveCurrentLocation(loSysLog);

                if(poConn.isDeviceConnected()){
                    String lsClient = WebFileServer.RequestClientToken(poConfig.ProducID(), poUser.getClientId(), poUser.getUserID());
                    String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                    if(lsClient.isEmpty() || lsAccess.isEmpty()){
                        lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Failed to request generated Client or Access token.");
                    } else {

                        org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                                poImageInfo.getFileLoct(),
                                lsAccess,
                                poImageInfo.getFileCode(),
                                poImageInfo.getDtlSrcNo(),
                                poImageInfo.getImageNme(),
                                poUser.getBranchCode(),
                                poImageInfo.getSourceCD(),
                                poImageInfo.getTransNox(),
                                "");

                        if(loUpload != null){
                            String lsImgResult = (String) loUpload.get("result");

                            if(lsImgResult.equalsIgnoreCase("success")){
                                String lsTransnox = (String) loUpload.get("sTransNox");
                                poImage.updateImageInfo(lsTransnox, poImageInfo.getTransNox());
                                Log.e(TAG, "Selfie log image has been uploaded successfully.");

                                Thread.sleep(1000);

                                String lsResponse = WebClient.sendRequest(WebApi.URL_POST_SELFIELOG, loJson[0].toString(), poHeaders.getHeaders());
//                                String lsResponse = AppConstants.APPROVAL_CODE_GENERATED("sample");

                                if(lsResponse == null){
                                    lsResult = AppConstants.SERVER_NO_RESPONSE();
                                } else {
                                    JSONObject loResponse = new JSONObject(lsResponse);
                                    String result = loResponse.getString("result");
                                    if(result.equalsIgnoreCase("success")){
                                        String TransNox = loResponse.getString("sTransNox");
                                        String OldTrans = selfieLog.getTransNox();
                                        poLog.updateEmployeeLogStatus(TransNox, OldTrans);
                                        Log.e(TAG, "Selfie log has been uploaded successfully.");
                                    }
                                    lsResult = lsResponse;
                                }
                            }
                        } else {
                            lsResult = AppConstants.SERVER_NO_RESPONSE();
                        }
                    }
//                    lsResult = AppConstants.APPROVAL_CODE_GENERATED("success");
                } else {
                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Your login will be sent to server automatically if device is connected to internet.");
                }
                    lsResult = AppConstants.APPROVAL_CODE_GENERATED("success");

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
                    callback.OnSuccess("Login successfully");
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
}