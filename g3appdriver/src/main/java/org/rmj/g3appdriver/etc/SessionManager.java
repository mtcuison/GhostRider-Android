package org.rmj.g3appdriver.etc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    //LOG CAT TAG
    private static final String TAG = SessionManager.class.getSimpleName();

    //SHARED PREFERENCES
    private final SharedPreferences pref;

    private final Editor editor;

    //shared preference  file name
    private static final String PREF_NAME = "AndroidGhostRider";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    private static final String KEY_IS_FIRST_LAUNCHED = "isFirstLaunch";

    private static final String KEY_CLIENT_ID = "sClientID";

    private static final String KEY_LOG_NUMBER = "sLogNoxxx";

    private static final String KEY_BRANCH_CODE = "sBranchCd";

    private static final String KEY_USER_ID = "sUserIDxx";

    private static final String KEY_DEPT_ID = "sDeptIDxx";

    private static final String KEY_EMPLOYEE_ID = "sEmplIDxx";

    private static final String KEY_POSITION_ID = "sPositionID";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context){
        //Shared pref mode
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void initUserSession(String UserID, String Client, String LogNo, String Branch, String DeptID, String EmpID, String Position){
        editor.putString(KEY_USER_ID, UserID);
        if(editor.commit()){
            Log.e(TAG, "User ID for this session has been set.");
        }

        editor.putString(KEY_CLIENT_ID, Client);
        if(editor.commit()){
            Log.e(TAG, "Client ID for this session has been set.");
        }

        editor.putString(KEY_LOG_NUMBER, LogNo);
        if(editor.commit()){
            Log.e(TAG, "Log number for this session has been set.");
        }

        editor.putString(KEY_BRANCH_CODE, Branch);
        if(editor.commit()){
            Log.e(TAG, "Branch code for this session has been set.");
        }

        editor.putString(KEY_DEPT_ID, DeptID);
        if(editor.commit()){
            Log.e(TAG, "User Department ID for this session has been set.");
        }

        editor.putString(KEY_EMPLOYEE_ID, EmpID);
        if(editor.commit()){
            Log.e(TAG, "User Employee ID for this session has been set.");
        }

        editor.putString(KEY_POSITION_ID, Position);
        if(editor.commit()){
            Log.e(TAG, "User position ID for this session has been set.");
        }

        editor.putBoolean(KEY_IS_LOGGEDIN, true);
        if(editor.commit()){
            Log.d(TAG, "User login session has been initialized.");
        }
    }

    public void initUserLogout(){
        editor.putString(KEY_USER_ID, "");
        if(editor.commit()){
            Log.e(TAG, "User ID for this session has been set.");
        }

        editor.putString(KEY_CLIENT_ID, "");
        if(editor.commit()){
            Log.e(TAG, "Client ID for this session has been set.");
        }

        editor.putString(KEY_LOG_NUMBER, "");
        if(editor.commit()){
            Log.e(TAG, "Log number for this session has been set.");
        }

        editor.putBoolean(KEY_IS_LOGGEDIN, false);
        if(editor.commit()){
            Log.d(TAG, "User login session has been initialized.");
        }
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setIsFirstLaunched(boolean isFirstLaunched){
        editor.putBoolean(KEY_IS_FIRST_LAUNCHED, isFirstLaunched);

        editor.commit();

        Log.e(TAG, "Is app first launched.");
    }

    public boolean isFirstLaunch(){
        return pref.getBoolean(KEY_IS_FIRST_LAUNCHED, true);
    }

    public String getUserID(){
        return pref.getString(KEY_USER_ID, "");
    }

    public String getClientId(){
        return pref.getString(KEY_CLIENT_ID, "");
    }

    public String getLogNumber(){
        return pref.getString(KEY_LOG_NUMBER, "");
    }

    public String getBranchCode() {
        return pref.getString(KEY_BRANCH_CODE, "");
    }

    public String getDeptID(){
        return pref.getString(KEY_DEPT_ID, "");
    }

    public String getEmployeeID(){
        return pref.getString(KEY_EMPLOYEE_ID, "");
    }

    public String getPositionID(){
        return pref.getString(KEY_POSITION_ID, "");
    }
}

