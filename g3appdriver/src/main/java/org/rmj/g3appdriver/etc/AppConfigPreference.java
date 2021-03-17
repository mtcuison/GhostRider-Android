package org.rmj.g3appdriver.etc;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppConfigPreference {
    private static final String TAG = AppConfigPreference.class.getSimpleName();

    private final SharedPreferences pref;

    private final SharedPreferences.Editor editor;

    private static final String isAppFirstLaunch = "AppFirstLaunch";
    private static final String isLocalHostChange = "ChangeLocalHost";
    private static final String isTestingPhase = "TestingPhase";
    private static final String AppServer = "ApplicationServer";
    private static final String CONFIG_NAME = "GGC_AndroidLocalConfig";
    private static final String APP_PRODUCT_ID = "ProductID";
    private static final String APP_DATE_LOGIN = "DateLogin";
    private static final String temp_PIN = "ConfirmationPIN";
    private static final String APP_FIREBASE_TOKEN = "Firebase_Token";

    private static AppConfigPreference mAppConfigPreference;

    private AppConfigPreference(Context context){
        int priv_Mode = 0;
        pref = context.getSharedPreferences(CONFIG_NAME, priv_Mode);
        editor = pref.edit();
    }

    public static AppConfigPreference getInstance(Context context){
        if(mAppConfigPreference == null){
            mAppConfigPreference = new AppConfigPreference(context);
        }
        return mAppConfigPreference;
    }

    public void setIsAppFirstLaunch(boolean isFirstLaunch){
        editor.putBoolean(isAppFirstLaunch, isFirstLaunch);
        editor.commit();
    }

    public boolean isAppFirstLaunch(){
        return pref.getBoolean(isAppFirstLaunch, true);
    }

    public void setIsLocalHostChange(boolean isChange){
        editor.putBoolean(isLocalHostChange, isChange);
        editor.commit();
    }

    public boolean getIsLocalHostChange() {
        return pref.getBoolean(isLocalHostChange, true);
    }

    public void setIsTesting(boolean isTesting){
        editor.putBoolean(isTestingPhase, isTesting);
        editor.commit();
    }

    public boolean isTesting_Phase(){
        return pref.getBoolean(isTestingPhase, false);
    }

    public void setAppServer(String LocalDataServer){
        editor.putString(AppServer, LocalDataServer);
        if(editor.commit()){
            Log.e(TAG, "Server has been set to localhost");
        }
    }

    /**
     *
     * @return Default return value https://restgk.guanzongroup.com.ph/
     * live data address
     */
    public String getAppServer(){
        return pref.getString(AppServer, "https://restgk.guanzongroup.com.ph/");
    }

    public void setTemp_ProductID(String ProductID){
        editor.putString(APP_PRODUCT_ID, ProductID);
        editor.commit();

        Log.d(TAG, "ProductID has been set");
    }
    public String ProducID(){
        return  pref.getString(APP_PRODUCT_ID,"");
    }

    public void setTemp_DateLogin(String DateLogin){
        editor.putString(APP_DATE_LOGIN, DateLogin);
        editor.commit();
    }
    public String DateLogin(){
        return  pref.getString(APP_DATE_LOGIN, "");
    }

    public void setTemp_PIN(String PIN){
        editor.putString(temp_PIN, PIN);
        editor.commit();
    }

    public String getPIN(){
        return pref.getString(temp_PIN, "");
    }

    public void setAppToken(String AppToken){
        editor.putString(APP_FIREBASE_TOKEN, AppToken);
        editor.commit();
    }

    public String getAppToken(){
        return pref.getString(APP_FIREBASE_TOKEN, "");
    }
}
