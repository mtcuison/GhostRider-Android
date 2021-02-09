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

public class VMSplashScreen extends AndroidViewModel {

    private final MutableLiveData<Boolean> pbGranted = new MutableLiveData<>();
    private final MutableLiveData<String[]> paPermisions = new MutableLiveData<>();
    private final MutableLiveData<Boolean> pbIsLogIn = new MutableLiveData<>();
    private final MutableLiveData<Boolean> pbSession = new MutableLiveData<>();
    private final MutableLiveData<Integer> pnSession = new MutableLiveData<>();
    private final REmployee poUserDbx;
    private final AppTokenManager poTokenInf;
    private final AppConfigPreference poConfigx;
    private final SessionManager poSession;

    public VMSplashScreen(@NonNull Application application) {
        super(application);
        poUserDbx = new REmployee(application);
        poTokenInf = new AppTokenManager(application);
        poConfigx = AppConfigPreference.getInstance(application);
        poSession = new SessionManager(application);
        poConfigx.setTemp_ProductID("IntegSys");
        paPermisions.setValue(new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
        pbGranted.setValue(hasPermissions(application.getApplicationContext(), paPermisions.getValue()));
    }

    public void setupTokenInfo(String tokenInfo){
        ETokenInfo loToken = new ETokenInfo();
        loToken.setTokenInf(tokenInfo);
        poTokenInf.setTokenInfo(loToken);
    }

    public LiveData<Boolean> isPermissionsGranted(){
        return pbGranted;
    }

    public LiveData<String[]> getPermisions(){
        return paPermisions;
    }

    public LiveData<DEmployeeInfo.Session> getSessionTime(){
        return poUserDbx.getSessionTime();
    }

    public void setSessionTime(int time){
        try {
            this.pnSession.setValue(time);
            pbSession.setValue(pnSession.getValue() <= 0);
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
}
