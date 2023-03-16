/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.etc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppConfigPreference {
    private static final String TAG = AppConfigPreference.class.getSimpleName();

    private final SharedPreferences pref;

    private final SharedPreferences.Editor editor;

    private static final String PACKAGE_NAME = "gRider_PackageName";
    private static final String isAppFirstLaunch = "AppFirstLaunch";
    private static final String isTestingPhase = "gRider_TestingPhase";
    private static final String isServerBackUp = "gRider_SecondaryLive";
    private static final String AppServer = "ApplicationServer";
    private static final String CONFIG_NAME = "GGC_AndroidLocalConfig";
    private static final String APP_PRODUCT_ID = "gRider_ProductID";
    private static final String APP_DATE_LOGIN = "gRider_DateLogin";
    private static final String temp_PIN = "ConfirmationPIN";
    private static final String APP_FIREBASE_TOKEN = "Firebase_Token";
    private static final String DCP_CustomerRebate = "DCP_CustomerRebate";
    private static final String DCP_PRNox = "DCP_PR_Noxxx";
    private static final String Application_Agreement = "gRider_TermAndConditions";
    private static final String MobileNo = "Mobile_Number";
    private static boolean isAgreedOnTerms = false;
    private static final String APP_CODE_VERSION = "gRider_VersionCode";
    private static final String APP_NAME_VERSION = "gRider_VersionName";
    private static final String APP_DATE_RELEASE = "gRider_DateRelease";
    private static final String LAST_SYNC_DATE = "gRider_last_date_sync";
    private static final String UPDATE_LOCALLY = "gRider_local_update";
    private static final String EXPORTED_DCP = "gRider_dcp_export_file";

    private static final String INVENTORY_COUNT = "gRider_inventory_count";

    private static final String HELP_LOGIN_NOTICE = "Login_Instruction_Notice";
    private static final String HELP_DCP_DOWNLOAD_NOTICE = "DCP_Download_Instruction_Notice";

    private static final String LOGIN_ATTEMPT = "gRider_Log_Attempt";

    private static final String MAIN_ACTIVITY = "cMainActv";

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

    public void setIsMainActive(boolean isActive){
        editor.putBoolean(MAIN_ACTIVITY, isActive);
        editor.commit();
    }

    public boolean isMainActive(){
        return pref.getBoolean(MAIN_ACTIVITY, false);
    }

    public void setPackageName(String fsVal){
        editor.putString(PACKAGE_NAME, fsVal);
        editor.commit();
    }

    public String getPackageName(){
        return pref.getString(PACKAGE_NAME, "");
    }

    public void setIsAppFirstLaunch(boolean isFirstLaunch){
        editor.putBoolean(isAppFirstLaunch, isFirstLaunch);
        editor.commit();
    }

    public boolean isAppFirstLaunch(){
        return pref.getBoolean(isAppFirstLaunch, true);
    }

    public boolean getTestStatus() {
        return pref.getBoolean(isTestingPhase, false);
    }

    public void setTestCase(boolean isTesting){
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
//        return pref.getString(AppServer, "http://192.168.10.141/");
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

    public void setDCP_CustomerRebate(String fnRebate){
        editor.putString(DCP_CustomerRebate, fnRebate);
        editor.commit();
    }

    public String getDCP_CustomerRebate(){
        return pref.getString(DCP_CustomerRebate, "100");
    }

    public void setIfBackUpServer(boolean fbval){
        if(fbval){
            Log.e(TAG, "Backup server set as active");
        } else {
            Log.e(TAG, "Live server set as active");
        }
        editor.putBoolean(isServerBackUp, fbval);
        editor.commit();
    }

    public boolean isBackUpServer(){
        return pref.getBoolean(isServerBackUp, false);
    }


    public void setDCP_PRNox(String fnPrNox){
        editor.putInt(DCP_PRNox, Integer.parseInt(fnPrNox));
        editor.commit();
    }

    public String getDCP_PRNox(){
        int lnPrNox = pref.getInt(DCP_PRNox, 0) + 1;
        @SuppressLint("DefaultLocale") String lsPrNox = String.format("%08d", lnPrNox);
        return lsPrNox;
    }

    public void setAgreement(boolean isAgree){
        editor.putBoolean(Application_Agreement, isAgree);
        editor.commit();
    }

    public boolean isAgreedOnTermsAndConditions(){
        return pref.getBoolean(Application_Agreement, false);
    }

    public boolean isAgreedOnTerms() {
        return isAgreedOnTerms;
    }

    public String getMobileNo(){
        return pref.getString(MobileNo, "");
    }

    public void setMobileNo(String mobileNo){
        editor.putString(MobileNo, mobileNo);
        editor.commit();
    }

    public void setIsAgreedOnTerms(boolean isAgreed) {
        this.isAgreedOnTerms = isAgreed;
    }

    public void setupAppVersionInfo(int VersionCode, String VersionName, String DateRelease){
        editor.putInt(APP_CODE_VERSION, VersionCode);
        editor.commit();

        editor.putString(APP_NAME_VERSION, VersionName);
        editor.commit();

        editor.putString(APP_DATE_RELEASE, DateRelease);
        editor.commit();
    }

    public String getVersionInfo(){
        return pref.getString(APP_NAME_VERSION, "") + " - " + pref.getString(APP_DATE_RELEASE, "");
    }

    public int getVersionCode(){
        return pref.getInt(APP_CODE_VERSION, 0);
    }

    public String getVersionName(){
        return pref.getString(APP_NAME_VERSION, "");
    }

    //is first open help login
    public void setIsHelpLoginNotice(boolean loginNotice){
        editor.putBoolean(HELP_LOGIN_NOTICE, loginNotice);

        editor.commit();

        Log.e(TAG, "HELP_LOGIN_NOTICE first launched.");
    }

    public boolean isHelpLoginNotice(){
        return pref.getBoolean(HELP_LOGIN_NOTICE, true);
    }

    //is first open help login
    public void setIsHelpDownloadDCPNotice(boolean downloadNotice){
        editor.putBoolean(HELP_DCP_DOWNLOAD_NOTICE, downloadNotice);

        editor.commit();

        Log.e(TAG, "HELP_LOGIN_NOTICE first launched.");
    }

    public boolean isHelpDownloadDCPNotice(){
        return pref.getBoolean(HELP_DCP_DOWNLOAD_NOTICE, true);
    }

    public void setLastSyncDate(String dReferDte){
        editor.putString(LAST_SYNC_DATE, dReferDte);

        editor.commit();

        Log.e(TAG, "LAST_SYNC_DATE has been set.");
    }

    public String getLastSyncDate(){
        return pref.getString(LAST_SYNC_DATE, "");
    }

    public void setLoginAttempt(int val){
        editor.putInt(LOGIN_ATTEMPT, val);

        editor.commit();

        Log.e(TAG, "LOGIN_ATTEMPT has been set.");
    }

    public int getLoginAttempt(){
        return pref.getInt(LOGIN_ATTEMPT, 2);
    }

    public void setUpdateLocally(boolean val){
        editor.putBoolean(UPDATE_LOCALLY, val);
        editor.commit();
    }

    public boolean getUpdateStatus(){
        return pref.getBoolean(UPDATE_LOCALLY, false);
    }

    public void setExportedDcp(boolean val){
        editor.putBoolean(EXPORTED_DCP, val);
        editor.commit();
    }

    public boolean isExportedDcp(){
        return pref.getBoolean(EXPORTED_DCP, true);
    }

    public void setInventoryCount(boolean hasInventory){
        editor.putBoolean(INVENTORY_COUNT, hasInventory);
        editor.commit();
    }

    public boolean hasInventory(){
        return pref.getBoolean(INVENTORY_COUNT, true);
    }



}
