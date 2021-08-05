/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 7/10/21 2:22 PM
 * project file last modified : 7/10/21 2:22 PM
 */

package org.rmj.g3appdriver.etc;

import android.content.Context;
import android.content.SharedPreferences;

public class AppAssistantConfig {
    private static final String TAG = AppAssistantConfig.class.getSimpleName();

    private final SharedPreferences pref;

    private final SharedPreferences.Editor editor;

    private static final String CONFIG_NAME = "GRider_Android_Assistant";

    private static final String HELP_AUTH_NOTICE = "gRider_Auth_Notice";
    private static final String HELP_DCP_NOTICE = "DCP_Instruction_Notice";
    private static final String HELP_DCP_DL_NOTICE = "DCP_Download_Notice";
    private static final String HELP_DCP_IMPORT_NOTICE = "DCP_Import_Notice";
    private static final String ASSIST_DCP_ADD = "Assist_dcp_add";
    private static final String ASSIST_DCP_TRANSACTION = "Assist_Dcp_Transaction";
    private static final String ASSIST_DCP_PAY = "Assist_dcp_pay";
    private static final String ASSIST_DCP_CNA = "Assist_dcp_cna";
    private static final String ASSIST_DCP_OTH = "Assist_dcp_oth";
    private static final String ASSIST_DCP_POST = "Assist_dcp_post";
    private static final String ASSIST_DCP_REMIT = "Assist_dcp_remit";
    private static final String ASSIST_DCP_LOG = "Assist_dcp_log";

    private static final String HELP_SLOGIN_NOTICE = "DCP_Instruction_Notice";
    private static final String HELP_SYSUPDATE_NOTICE = "DCP_Instruction_Notice";
    private static final String HELP_KNOX_NOTICE = "DCP_Instruction_Notice";

    private static AppAssistantConfig mAppConfigPreference;

    private AppAssistantConfig(Context context){
        int priv_Mode = 0;
        pref = context.getSharedPreferences(CONFIG_NAME, priv_Mode);
        editor = pref.edit();
    }

    public static AppAssistantConfig getInstance(Context context){
        if(mAppConfigPreference == null){
            mAppConfigPreference = new AppAssistantConfig(context);
        }
        return mAppConfigPreference;
    }

    public boolean getHELP_AUTH_NOTICE(){
        return pref.getBoolean(HELP_AUTH_NOTICE, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setHELP_AUTH_NOTICE(boolean val){
        editor.putBoolean(HELP_AUTH_NOTICE, val);
        editor.commit();
    }

    public boolean getHELP_DCP_NOTICE() {
        return pref.getBoolean(HELP_DCP_NOTICE, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setHELP_DCP_NOTICE(boolean val) {
        editor.putBoolean(HELP_DCP_NOTICE, val);
        editor.commit();
    }

    public boolean getHELP_DCP_DL_NOTICE(){
        return pref.getBoolean(HELP_DCP_DL_NOTICE, false);
    }

    public void setHELP_DCP_DL_NOTICE(boolean val){
        editor.putBoolean(HELP_DCP_DL_NOTICE, val);
        editor.commit();
    }

    public boolean getASSIST_DCP_ADD() {
        return pref.getBoolean(ASSIST_DCP_ADD, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setASSIST_DCP_ADD(boolean val) {
        editor.putBoolean(ASSIST_DCP_ADD, val);
        editor.commit();
    }

    public boolean getASSIST_DCP_TRANSACTION(){
        return pref.getBoolean(ASSIST_DCP_TRANSACTION, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setASSIST_DCP_TRANSACTION(boolean val){
        editor.putBoolean(ASSIST_DCP_TRANSACTION, val);
        editor.commit();
    }

    public boolean getASSIST_DCP_PAY() {
        return pref.getBoolean(ASSIST_DCP_PAY, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setASSIST_DCP_PAY(boolean val) {
        editor.putBoolean(ASSIST_DCP_PAY, val);
        editor.commit();
    }

    public boolean getASSIST_DCP_CNA() {
        return pref.getBoolean(ASSIST_DCP_CNA, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setASSIST_DCP_CNA(boolean val) {
        editor.putBoolean(ASSIST_DCP_CNA, val);
        editor.commit();
    }

    public boolean getASSIST_DCP_OTH() {
        return pref.getBoolean(ASSIST_DCP_OTH, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setASSIST_DCP_OTH(boolean val) {
        editor.putBoolean(ASSIST_DCP_OTH, val);
        editor.commit();
    }

    public boolean getASSIST_DCP_POST(){
        return pref.getBoolean(ASSIST_DCP_POST, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setASSIST_DCP_POST(boolean val){
        editor.putBoolean(ASSIST_DCP_POST, val);
        editor.commit();
    }

    public boolean getASSIST_DCP_REMIT() {
        return pref.getBoolean(ASSIST_DCP_REMIT, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setASSIST_DCP_REMIT(boolean val) {
        editor.putBoolean(ASSIST_DCP_REMIT, val);
        editor.commit();
    }

    public boolean getASSIST_DCP_LOG(){
        return pref.getBoolean(ASSIST_DCP_LOG, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setASSIST_DCP_LOG(boolean val){
        editor.putBoolean(ASSIST_DCP_LOG, val);
        editor.commit();
    }
    public boolean getHELP_DCP_IMPORT_NOTICE() {
        return pref.getBoolean(HELP_DCP_IMPORT_NOTICE, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setHELP_DCP_IMPORT_NOTICE(boolean val) {
        editor.putBoolean(HELP_DCP_IMPORT_NOTICE, val);
        editor.commit();
    }

    public boolean getHELP_SLOGIN_NOTICE() {
        return pref.getBoolean(HELP_SLOGIN_NOTICE, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setHELP_SLOGIN_NOTICE(boolean val) {
        editor.putBoolean(HELP_SLOGIN_NOTICE, val);
        editor.commit();
    }

    public boolean getHELP_SYSUPDATE_NOTICE() {
        return pref.getBoolean(HELP_SYSUPDATE_NOTICE, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setHELP_SYSUPDATE_NOTICE(boolean val) {
        editor.putBoolean(HELP_SYSUPDATE_NOTICE, val);
        editor.commit();
    }

    public boolean getHELP_KNOX_NOTICE() {
        return pref.getBoolean(HELP_KNOX_NOTICE, false);
    }

    /**
     *
     * @param val set status to true if instructions has been read
     */
    public void setHELP_KNOX_NOTICE(boolean val) {
        editor.putBoolean(HELP_KNOX_NOTICE, val);
        editor.commit();
    }
}
