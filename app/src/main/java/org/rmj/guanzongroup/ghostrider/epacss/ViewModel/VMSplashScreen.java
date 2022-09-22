/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETokenInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.AppTokenManager;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBankInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RFileCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RRelation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.ImportData.ImportBarangay;
import org.rmj.g3appdriver.GRider.ImportData.ImportBranch;
import org.rmj.g3appdriver.GRider.ImportData.ImportBrand;
import org.rmj.g3appdriver.GRider.ImportData.ImportBrandModel;
import org.rmj.g3appdriver.GRider.ImportData.ImportCategory;
import org.rmj.g3appdriver.GRider.ImportData.ImportCountry;
import org.rmj.g3appdriver.GRider.ImportData.ImportFileCode;
import org.rmj.g3appdriver.GRider.ImportData.ImportInstance;
import org.rmj.g3appdriver.GRider.ImportData.ImportMcModelPrice;
import org.rmj.g3appdriver.GRider.ImportData.ImportMcTermCategory;
import org.rmj.g3appdriver.GRider.ImportData.ImportProvinces;
import org.rmj.g3appdriver.GRider.ImportData.ImportTown;
import org.rmj.g3appdriver.GRider.ImportData.Import_AreaPerformance;
import org.rmj.g3appdriver.GRider.ImportData.Import_BankList;
import org.rmj.g3appdriver.GRider.ImportData.Import_BranchPerformance;
import org.rmj.g3appdriver.GRider.ImportData.Import_Occupations;
import org.rmj.g3appdriver.GRider.ImportData.Import_Relation;
import org.rmj.g3appdriver.GRider.ImportData.Import_SCARequest;
import org.rmj.g3appdriver.GRider.ImportData.Import_SysConfig;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.epacss.BuildConfig;

import java.io.IOException;
import java.util.Date;

public class VMSplashScreen extends AndroidViewModel {
    private static final String TAG = VMSplashScreen.class.getSimpleName();

    private final Application instance;

    private final MutableLiveData<Boolean> pbGranted = new MutableLiveData<>();
    private final MutableLiveData<String[]> paPermisions = new MutableLiveData<>();
    private final MutableLiveData<Boolean> pbIsLogIn = new MutableLiveData<>();
    private final MutableLiveData<Boolean> pbSession = new MutableLiveData<>();
    private final MutableLiveData<Integer> pnSession = new MutableLiveData<>();
    private final MutableLiveData<String> psVersion = new MutableLiveData<>();
    private final MutableLiveData<String> sEmployLevel = new MutableLiveData<>();
    private final LiveData<DDCPCollectionDetail.Location_Data_Trigger> poLocator;
    private final REmployee poUserDbx;
    private final AppConfigPreference poConfigx;
    private final SessionManager poSession;
    private final ConnectionUtil poConn;
    private final AppTokenManager poToken;
    private final RDailyCollectionPlan poDcp;
    private final RLogSelfie poLogx;

    private final String[] psPermissions = new String[]{
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.REQUEST_INSTALL_PACKAGES};

    private final MutableLiveData<Boolean> pbIsGrantd = new MutableLiveData<>();
    private final MutableLiveData<AppStatus> poAppStat = new MutableLiveData<>();

    @SuppressLint("InlinedApi")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public VMSplashScreen(@NonNull Application application) throws IOException {
        super(application);
        this.instance = application;
        poUserDbx = new REmployee(application);
        poConfigx = AppConfigPreference.getInstance(application);
        poSession = new SessionManager(application);
        poConfigx.setTemp_ProductID("gRider");
        poConfigx.setUpdateLocally(false);
        poLogx = new RLogSelfie(application);
        Date buildDate = new Date(BuildConfig.TIMESTAMP);
        poConfigx.setupAppVersionInfo(BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME, String.valueOf(buildDate.getTime()));
        poConn = new ConnectionUtil(application);
        poToken = new AppTokenManager(application);
        ETokenInfo loToken = new ETokenInfo();
        loToken.setTokenInf("temp_token");
        poDcp = new RDailyCollectionPlan(application);
        poToken.setTokenInfo(loToken);
        this.sEmployLevel.setValue(poSession.getEmployeeLevel());
        paPermisions.setValue(new String[]{
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.REQUEST_INSTALL_PACKAGES});
        pbGranted.setValue(hasPermissions(application.getApplicationContext(), paPermisions.getValue()));
        this.psVersion.setValue(poConfigx.getVersionInfo());
        this.poLocator = poDcp.getDCP_COH_StatusForTracking();
        new CheckConnectionTask(application).execute();
    }

    public void initializeApp(){

    }

    public LiveData<AppStatus> GetAppStatus(){
        return poAppStat;
    }

    public String[] GetPermissions(){
        return psPermissions;
    }

    public LiveData<String> getVersionInfo(){
        return psVersion;
    }

    public LiveData<Boolean> isPermissionsGranted(){
        return pbGranted;
    }

    public LiveData<String[]> getPermisions(){
        return paPermisions;
    }

    public LiveData<String> getSessionDate(){
        return poUserDbx.getSessionDate();
    }

    public LiveData<DEmployeeInfo.Session> getSessionTime(){
        return poUserDbx.getSessionTime();
    }

    public LiveData<DDCPCollectionDetail.Location_Data_Trigger> getLocatorDateTrigger(){
        return poLocator;
    }
    public LiveData<String> getEmployeeLevel(){
        return this.sEmployLevel;
    }

    public String getAutoLogStatus(){
        return poSession.getAutoLogStatus();
    }

    public void setSessionTime(int time){
        try {
            this.pnSession.setValue(time);
            if(poSession.getAutoLogStatus().equalsIgnoreCase("1")){
                pbSession.setValue(true);
            } else {
                if(poConn.isDeviceConnected()) {
                    boolean result = pnSession.getValue() <= 0;
                    pbSession.setValue(result);
                } else {
                    pbSession.setValue(true);
                }
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public LiveData<Boolean> isSessionValid(){
        return pbSession;
    }

    public void setPermissionsGranted(boolean isGranted){
        this.pbGranted.setValue(isGranted);
    }

    public LiveData<Boolean> isLoggedIn(){
        pbIsLogIn.setValue(poSession.isLoggedIn());
        return pbIsLogIn;
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

    private static class CheckConnectionTask extends AsyncTask<String, Void, Boolean>{

        private final Application instance;
        private final ConnectionUtil poConn;

        private String message;

        public CheckConnectionTask(Application instance) {
            this.instance = instance;
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!poConn.isDeviceConnected()){
                message = poConn.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                Toast.makeText(instance, "Device connected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(instance, "Offline mode", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void InitializeData(OnInitializecallback onInitializecallback){
        new InitializeDataTask(instance, onInitializecallback).execute();
    }

    public interface OnInitializecallback{
        void OnSuccess();
        void OnFailed(String message);
    }

    private static class InitializeDataTask extends AsyncTask<String, Void, Boolean>{

        private final Application instance;
        private final OnInitializecallback callback;
        private final ConnectionUtil poConn;
        private final AppConfigPreference poConfig;

        private String message;

        public InitializeDataTask(Application instance, OnInitializecallback callback) {
            this.instance = instance;
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poConfig = AppConfigPreference.getInstance(instance);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                if(!poConn.isDeviceConnected()){
                    if(!poConfig.isAppFirstLaunch()){
                        message = "Offline Mode.";
                        return true;
                    }

                    message = "Please connect you device to internet to download important data.";
                    return false;
                }

                if(!new RBarangay(instance).ImportBarangay()){
                    Log.e(TAG, "Unable to import barangay");
                }

                if(!new RTown(instance).ImportTown()){
                    Log.e(TAG, "Unable to import town");
                }

                if(!new RProvince(instance).ImportProvince()){
                    Log.e(TAG, "Unable to import province");
                }

                if(!new RBranch(instance).ImportBranches()){
                    Log.e(TAG, "Unable to import branches");
                }

//                ImportInstance[]  importInstances = {
//                        new Import_BankList(instance),
//                        new ImportFileCode(instance),
//                        new Import_Relation(instance),
//                        new ImportBrand(instance),
//                        new ImportBrandModel(instance),
//                        new ImportCategory(instance),
//                        new ImportProvinces(instance),
//                        new ImportMcModelPrice(instance),
//                        new ImportTown(instance),
//                        new ImportBarangay(instance),
//                        new ImportMcTermCategory(instance),
//                        new ImportCountry(instance),
//                        new Import_Occupations(instance),
//                        new Import_SysConfig(instance),
//                        new Import_SCARequest(instance),
//                        new ImportBranch(instance),
//                        new Import_AreaPerformance(instance),
//                        new Import_BranchPerformance(instance)};

                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                callback.OnSuccess();
            } else {
                callback.OnFailed(message);
            }
        }
    }

    public static class AppStatus{
        private boolean cPrmnGrnt = false;
        private boolean cIsLoginx = false;
        private boolean cCmpltAcc = false;

        public AppStatus(boolean cPrmnGrnt, boolean cIsLoginx, boolean cCmpltAcc) {
            this.cPrmnGrnt = cPrmnGrnt;
            this.cIsLoginx = cIsLoginx;
            this.cCmpltAcc = cCmpltAcc;
        }

        public boolean isPermissionsGranted() {
            return cPrmnGrnt;
        }

        public void setGrantedPermission(boolean cPrmnGrnt) {
            this.cPrmnGrnt = cPrmnGrnt;
        }

        public boolean hasAccountSession() {
            return cIsLoginx;
        }

        public void setAccountSession(boolean cIsLoginx) {
            this.cIsLoginx = cIsLoginx;
        }
    }
}
