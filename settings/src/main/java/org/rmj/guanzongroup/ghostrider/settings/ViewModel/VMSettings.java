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

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Account.AccountMaster;
import org.rmj.g3appdriver.lib.Account.Model.Auth;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Account.pojo.PasswordUpdate;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

public class VMSettings extends AndroidViewModel {

    private final Application instance;
    private final ConnectionUtil poConn;
    private final iAuth poSys;

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

        this.poConn = new ConnectionUtil(instance);
        this.poSys = new AccountMaster(instance).initGuanzonApp().getInstance(Auth.CHANGE_PASSWORD);
    }

    public void setCameraSummary(String camSummary){
        this.cameraSummarry.setValue(camSummary);
    }
    public LiveData<String> getCameraSummary(){
        return this.cameraSummarry;
    }

    public LiveData<Boolean> isCamPermissionGranted(){
        return camGranted;
    }
    public LiveData<Boolean> isLocPermissionGranted(){
        return locGranted;
    }

    public LiveData<String[]> getCamPermissions(){
        return cameraPermissions;
    }
    public LiveData<String[]> getPhPermissions(){
        return phonePermissions;
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

    public void ChangePassword(String Old, String New, ChangePasswordCallback callback){

        PasswordUpdate loParam = new PasswordUpdate(
                "",
                Old,
                New,
                New
        );

        TaskExecutor.Execute(loParam, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callback.OnLoad("Change Password", "Updating account. Please wait...");
            }

            @Override
            public Object DoInBackground(Object args) {

                if (!poConn.isDeviceConnected()){
                    return false;
                }

                PasswordUpdate loArgs = (PasswordUpdate) args;
                if (poSys.DoAction(loArgs) == 0){
                    return false;
                }
                return true;
            }

            @Override
            public void OnPostExecute(Object object) {

                if (!(Boolean) object){
                    callback.OnFailed(poSys.getMessage());
                    return;
                }
                callback.OnSuccess();
            }
        });

    }

}