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
import android.os.Build;

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
import org.rmj.g3appdriver.GRider.Database.Repositories.RCashCount;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.epacss.BuildConfig;

import java.io.IOException;
import java.util.Date;

public class VMSplashScreen extends AndroidViewModel {

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

    @SuppressLint("InlinedApi")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public VMSplashScreen(@NonNull Application application) throws IOException {
        super(application);
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
                Manifest.permission.REQUEST_INSTALL_PACKAGES});
        pbGranted.setValue(hasPermissions(application.getApplicationContext(), paPermisions.getValue()));
        this.psVersion.setValue(poConfigx.getVersionInfo());
        this.poLocator = poDcp.getDCP_COH_StatusForTracking();
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

    public LiveData<String> getCashCountRequireStatus(){
        return poLogx.getCashCountRequireStatus();
    }

    public LiveData<String> getInventoryRequireStatus(){
        return poLogx.getInventoryRequireStatus();
    }
}
