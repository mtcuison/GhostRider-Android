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

    private static final String isAppFirstLaunch = "AppFirstLaunch";
    private static final String isLocalHostChange = "ChangeLocalHost";
    private static final String isTestingPhase = "TestingPhase";
    private static final String AppServer = "ApplicationServer";
    private static final String CONFIG_NAME = "GGC_AndroidLocalConfig";
    private static final String APP_PRODUCT_ID = "ProductID";
    private static final String APP_DATE_LOGIN = "DateLogin";
    private static final String temp_PIN = "ConfirmationPIN";
    private static final String APP_FIREBASE_TOKEN = "Firebase_Token";
    private static final String DCP_CustomerRebate = "DCP_CustomerRebate";
    private static final String DCP_PRNox = "DCP_PR_Noxxx";
    private static final String Application_Agreement = "TermAndConditions";
    private static final String MobileNo = "Mobile_Number";
    private static boolean isAgreedOnTerms = false;
    private static final String APP_CODE_VERSION = "gRider_VersionCode";
    private static final String APP_NAME_VERSION = "gRider_VersionName";
    private static final String APP_DATE_RELEASE = "gRider_DateRelease";

    private static final String HELP_LOGIN_NOTICE = "Login_Instruction_Notice";
    private static final String HELP_DCP_DOWNLOAD_NOTICE = "DCP_Download_Instruction_Notice";

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

    public void setDCP_CustomerRebate(String fnRebate){
        editor.putString(DCP_CustomerRebate, fnRebate);
        editor.commit();
    }

    public String getDCP_CustomerRebate(){
        return pref.getString(DCP_CustomerRebate, "100.00");
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

    public int getVersionCode(){
        return pref.getInt(APP_CODE_VERSION, 1);
    }

    public String getVersionName(){
        return pref.getString(APP_NAME_VERSION, "");
    }

    public String getDateRelease(){
        return pref.getString(APP_DATE_RELEASE, "");
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
}
