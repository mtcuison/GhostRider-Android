package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

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

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETokenInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.AppTokenManager;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.SessionManager;

public class VMSettings extends AndroidViewModel {
    private final MutableLiveData<Boolean> pbGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> locGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> camGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> phGranted = new MutableLiveData<>();
    private final MutableLiveData<String[]> paPermisions = new MutableLiveData<>();
    public MutableLiveData<String[]> locationPermissions = new MutableLiveData<>();
    public MutableLiveData<String[]> cameraPermissions = new MutableLiveData<>();
    public MutableLiveData<String[]> phonePermissions = new MutableLiveData<>();

    private final MutableLiveData<String> cameraSummarry = new MutableLiveData<>();

    public VMSettings(@NonNull Application application) {
        super(application);
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
                Manifest.permission.CAMERA});
        locationPermissions.setValue(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
        phonePermissions.setValue(new String[]{
                Manifest.permission.READ_PHONE_STATE});

        locGranted.setValue(hasPermissions(application.getApplicationContext(), locationPermissions.getValue()));
        camGranted.setValue(hasPermissions(application.getApplicationContext(), cameraPermissions.getValue()));
        phGranted.setValue(hasPermissions(application.getApplicationContext(), phonePermissions.getValue()));
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

}