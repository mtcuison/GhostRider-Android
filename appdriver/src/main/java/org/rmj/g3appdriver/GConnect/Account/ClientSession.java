package org.rmj.g3appdriver.GConnect.Account;

import android.content.Context;
import android.content.SharedPreferences;

public class ClientSession {
    private static final String TAG = ClientSession.class.getSimpleName();

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    private static final String ACCOUNT_CREDENTIALS = "GApp_AccountCredentials";
    private static final String USERID = "sUserIDxx";
    private static final String CLIENTID = "sClientID";
    private static final String FULLNAME = "sUserName";
    private static final String LASTNAME = "sLastName";
    private static final String FIRSTNAME = "sFrstName";
    private static final String MIDDLENAME = "sMiddName";
    private static final String SUFFIX = "sSuffixNm";
    private static final String GENDER = "sGenderCd";
    private static final String CIVILSTATUS = "sCvilStat";
    private static final String CITIZENSHIP = "sCitizenx";
    private static final String BIRTHDATE = "sBirthDte";
    private static final String BIRTHPLACE = "sBirthPlc";
    private static final String HOUSENO = "sHouseNox";
    private static final String MOBILENO = "sMobileNo";
    private static final String ADDRESS = "sAddressx";
    private static final String BARANGAY = "sBrgyName";
    private static final String TOWN = "sTownName";
    private static final String PROVINCE = "sProvName";

    private static final String VERIFIED_STATUS = "cVerified";

    private static final String SESSION_ACTIVE = "cLoggedin";

    private static ClientSession instance;

    private ClientSession(Context context) {
        pref = context.getSharedPreferences(ACCOUNT_CREDENTIALS, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public static ClientSession getInstance(Context context){
        if(instance == null){
            instance = new ClientSession(context);
        }
        return instance;
    }

    public void setUserID(String userID){
        editor.putString(USERID, userID);
        editor.commit();
    }

    public void setClientID(String clientId){
        editor.putString(CLIENTID, clientId);
        editor.commit();
    }

    public void setFullName(String fullName){
        editor.putString(FULLNAME, fullName);
        editor.commit();
    }

    public void setLastname(String lastName){
        editor.putString(LASTNAME, lastName);
        editor.commit();
    }

    public void setCivilStatus(String civilStatus){
        editor.putString(CIVILSTATUS, civilStatus);
        editor.commit();
    }

    public void setMiddlename(String middleName){
        editor.putString(MIDDLENAME, middleName);
        editor.commit();
    }

    public void setSuffix(String suffix){
        editor.putString(SUFFIX, suffix);
        editor.commit();
    }

    public void setFirstName(String firstName){
        editor.putString(FIRSTNAME, firstName);
        editor.commit();
    }

    public void setGender(String gender){
        editor.putString(GENDER, gender);
        editor.commit();
    }

    public void setBirthdate(String birthdate){
        editor.putString(BIRTHDATE, birthdate);
        editor.commit();
    }

    public void setBirthplace(String birthplace){
        editor.putString(BIRTHPLACE, birthplace);
        editor.commit();
    }

    public void setCitizenship(String citizenship){
        editor.putString(CITIZENSHIP, citizenship);
        editor.commit();
    }

    public void setHouseNo(String houseNo){
        editor.putString(HOUSENO, houseNo);
        editor.commit();
    }

    public void setAddress(String address){
        editor.putString(ADDRESS, address);
        editor.commit();
    }

    public void setBarangay(String barangay){
        editor.putString(BARANGAY, barangay);
        editor.commit();
    }

    public void setTownName(String townName){
        editor.putString(TOWN, townName);
        editor.commit();
    }

    public void setProvince(String province){
        editor.putString(PROVINCE, province);
        editor.commit();
    }

    public void setLoginStatus(boolean status){
        editor.putBoolean(SESSION_ACTIVE, status);
        editor.commit();
    }

    /**
     *
     * @param fnVal set 0 for not verified or not completed account,
     *              2 for completed account not verified,
     *              1 for verified account.
     */
    public void setVerifiedStatus(int fnVal){
        editor.putInt(VERIFIED_STATUS, fnVal);
        editor.commit();
    }

    public void setMobileNo(String fsVal){
        editor.putString(MOBILENO, fsVal);
        editor.commit();
    }

    public String getUserID(){
        return pref.getString(USERID, "");
    }

    public String getClientID(){
        return pref.getString(CLIENTID, "");
    }

    public String getLogNumber(){
        return "";
    }

    public String getFullName(){
        return pref.getString(FULLNAME, "");
    }

    public String getLastName(){
        return pref.getString(LASTNAME, "");
    }

    public String getFirstName(){
        return pref.getString(FIRSTNAME, "");
    }

    public String getMiddleName(){
        return pref.getString(MIDDLENAME, "");
    }

    public String getSuffix(){
        return pref.getString(SUFFIX, "");
    }

    public String getGender() {
        return pref.getString(GENDER, "");
    }

    public String getBirthdate() {
        return pref.getString(BIRTHDATE, "");
    }

    public String getBirthplace() {
        return pref.getString(BIRTHPLACE, "");
    }

    public String getCivilStatus() {
        return pref.getString(CIVILSTATUS, "");
    }

    public String getCitizenship() {
        return pref.getString(CITIZENSHIP, "");
    }

    public String getHouseNo(){
        return pref.getString(HOUSENO, "");
    }

    public String getAddress() {
         return pref.getString(ADDRESS, "");
    }

    public String getBarangay(){
        return pref.getString(BARANGAY, "");
    }

    public String getTown(){
        return pref.getString(TOWN, "");
    }

    public String getProvince(){
        return pref.getString(PROVINCE, "");
    }

    public boolean getLoginStatus() {
        return  pref.getBoolean(SESSION_ACTIVE,false);
    }

    public int getVerificationStatus(){
        return pref.getInt(VERIFIED_STATUS, 0);
    }

    public String getMobileNo(){
        return pref.getString(MOBILENO, "");
    }

    public void LogoutUser(){
        editor.putString(USERID, "");
        editor.commit();
        editor.putString(CLIENTID, "");
        editor.commit();
        editor.putString(FULLNAME, "");
        editor.commit();
        editor.putString(LASTNAME, "");
        editor.commit();
        editor.putString(FIRSTNAME, "");
        editor.commit();
        editor.putString(MIDDLENAME, "");
        editor.commit();
        editor.putString(SUFFIX, "");
        editor.commit();
        editor.putString(GENDER, "");
        editor.commit();
        editor.putString(CIVILSTATUS, "");
        editor.commit();
        editor.putString(CITIZENSHIP, "");
        editor.commit();
        editor.putString(BIRTHDATE, "");
        editor.commit();
        editor.putString(BIRTHPLACE, "");
        editor.commit();
        editor.putString(HOUSENO, "");
        editor.commit();
        editor.putString(ADDRESS, "");
        editor.commit();
        editor.putString(BARANGAY, "");
        editor.commit();
        editor.putString(TOWN, "");
        editor.commit();
        editor.putString(PROVINCE, "");
        editor.commit();
        editor.putInt(VERIFIED_STATUS, 0);
        editor.commit();
        editor.putBoolean(SESSION_ACTIVE, false);
        editor.commit();
    }
}