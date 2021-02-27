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

    private final MutableLiveData<Boolean> cameraGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean> locationGranted = new MutableLiveData<>();
    private final MutableLiveData<Boolean>  phoneGranted = new MutableLiveData<>();
    private final MutableLiveData<String[]> cameraPermisions = new MutableLiveData<>();
    private final MutableLiveData<String[]> locationPermisions = new MutableLiveData<>();
    private final MutableLiveData<String[]> phonePermisions = new MutableLiveData<>();
    private final REmployee poUserDbx;
    private final AppTokenManager poTokenInf;
    private final AppConfigPreference poConfigx;

    public VMSettings(@NonNull Application application) {
        super(application);
        poUserDbx = new REmployee(application);
        poTokenInf = new AppTokenManager(application);
        poConfigx = AppConfigPreference.getInstance(application);
        poConfigx.setTemp_ProductID("IntegSys");
        cameraPermisions.setValue(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA});
        locationPermisions.setValue(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
        phonePermisions.setValue(new String[]{
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.GET_ACCOUNTS,});
        cameraGranted.setValue(hasCameraPermissions(application.getApplicationContext(), cameraPermisions.getValue()));
        locationGranted.setValue(hasLocationPermissions(application.getApplicationContext(), locationPermisions.getValue()));
        phoneGranted.setValue(hasPermissions(application.getApplicationContext(), phonePermisions.getValue()));
    }

    public void setupTokenInfo(String tokenInfo){
        ETokenInfo loToken = new ETokenInfo();
        loToken.setTokenInf(tokenInfo);
        poTokenInf.setTokenInfo(loToken);
    }

    public LiveData<Boolean> isCameraPermissionsGranted(){
        return cameraGranted;
    }

    public LiveData<String[]> getCameraPermisions(){
        return cameraPermisions;
    }


    public void setCameraPermissionsGranted(boolean isGranted){
        this.cameraGranted.setValue(isGranted);
    }

    public LiveData<Boolean> isLocationPermissionsGranted(){
        return locationGranted;
    }

    public LiveData<String[]> getLocationPermisions(){
        return locationPermisions;
    }


    public void setLocationPermissionsGranted(boolean isGranted){
        this.locationGranted.setValue(isGranted);
    }

    public LiveData<Boolean> isPhonePermissionsGranted(){
        return phoneGranted;
    }

    public LiveData<String[]> getPhonePermisions(){
        return phonePermisions;
    }


    public void setPhonePermissionsGranted(boolean isGranted){
        this.phoneGranted.setValue(isGranted);
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

    private static boolean hasCameraPermissions(Context context, String... permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && permissions!=null ){
            for (String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean hasLocationPermissions(Context context, String... permissions){
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