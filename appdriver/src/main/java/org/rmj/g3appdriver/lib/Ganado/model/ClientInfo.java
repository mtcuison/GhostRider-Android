package org.rmj.g3appdriver.lib.Ganado.model;

import android.text.TextUtils;
import android.util.Patterns;

public class ClientInfo {

    private String LastName = "";
    private String FrstName = "";
    private String MiddName = "";
    private String Suffix = "";
    private String BrthDate = "";
    private String BrthPlce = "";
    private String BirthPlc = ""; //This holds the data for preview of Town, Province names.
    private String Gender = "";
    private String HouseNox = "";
    private String Address = "";
    private String sMuncplNm = "";
    private String sMuncplID = "";
    private String MobileNo;
    private String EmailAdd = "";

    private String message;

    public ClientInfo() {
    }

    public String getMessage() {
        return message;
    }

    public String getFullName(){
        return LastName + ", " +
                "" + FrstName + " " +
                "" + MiddName + " " +
                "" + Suffix;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFrstName() {
        return FrstName;
    }

    public void setFrstName(String frstName) {
        FrstName = frstName;
    }

    public String getMiddName() {
        return MiddName;
    }
    public void setMiddName(String middName) {
        MiddName = middName;
    }

    public String getSuffix() {
        return Suffix;
    }

    public void setSuffix(String suffix) {
        Suffix = suffix;
    }

    public String getEmail() {
        return EmailAdd;
    }

    public void setEmail(String email) {
        EmailAdd = email;
    }

    public String getBrthDate() {
        return BrthDate;
    }

    /**
     *
     * @return Use this get method to retrieve the UI preview of birthdate.
     */
    public String getBirthDte() {
//        if(!BrthDate.isEmpty()){
//            return  FormatUIText.formatGOCasBirthdate(BrthDate);
//        }else {
//            return "";
//        }
        return BrthDate;
    }
    public void setBrthDate(String brthDate) {
        BrthDate = brthDate;
    }

    /***
     *
     * @param brthPlce set the actual ID of Town and Province using this method...
     */
    public void setBrthPlce(String brthPlce){
        BrthPlce = brthPlce;
    }

    /**
     *
     * @return get the actual ID of town which
     * will be the birthdate of applicant using this method...
     */
    public String getBrthPlce() {
        return BrthPlce;
    }

    /**
     *
     * @return get the town and
     * province name of the applicant birthplace using this method...
     */
    public String getBirthPlc() {
        return BirthPlc;
    }

    /**
     *
     * @param birthPlc set the town and
     *                 province name of applicant birthplace using this method...
     */
    public void setBirthPlc(String birthPlc) {
        BirthPlc = birthPlc;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getHouseNox() {
        return HouseNox.trim();
    }

    public void setHouseNox(String sHouseNox) {
        this.HouseNox = sHouseNox;
    }

    public String getAddress() {
        return Address.trim();
    }

    public void setAddress(String sAddress) {
        this.Address = sAddress;
    }

    public boolean isAccountInfoValid(){
        if(!isLastNameValid()){
            return false;
        }
        if(!isFirstNameValid()){
            return false;
        }
        if(!isMiddNameValid()){
            return false;
        }

        if(BrthDate.trim().isEmpty()){
            message = "Please enter birth date";
            return false;
        }

        if(BrthPlce == null || BrthPlce.equalsIgnoreCase("")){
            message = "Please enter birth place";
            return false;
        }

        if (Gender == null || Gender.trim().isEmpty()){
            message = "Please select gender";
            return false;
        }

        return true;
    }

    private boolean isLastNameValid(){
        if(LastName.trim().isEmpty()){
            message = "Please enter your last name";
            return false;
        }
        return true;
    }

    private boolean isFirstNameValid(){
        if(FrstName.trim().isEmpty()){
            message = "Please enter your first name";
            return false;
        }
        return true;
    }

    private boolean isMiddNameValid(){
        if(MiddName.trim().isEmpty()){
            message = "Please enter your middle name";
            return false;
        }
        return true;
    }
    private boolean isEmailValid(){
        if(EmailAdd.trim().isEmpty()){
            message = "Please enter your email";
            return false;
        }
        if(TextUtils.isEmpty(EmailAdd) &&
                Patterns.EMAIL_ADDRESS.matcher(EmailAdd).matches()){
            message = "Please enter valid email";
            return false;
        }
        return true;
    }

    private boolean isMobileValid(){
        if(MobileNo.trim().isEmpty()){
            message = "Please enter mobile number";
            return false;
        } else if(MobileNo.length()!=11){
            message = "Mobile number must be 11 characters";
            return false;
        } else if(!MobileNo.substring(0, 2).equalsIgnoreCase("09")){
            message = "Mobile number must start with '09'";
            return false;
        }
        return true;
    }
    public boolean isContactDataValid() {


        if(Address == null || Address.equalsIgnoreCase("")){
            message = "Please enter Address.";
            return false;
        }

        if(!isTownMunicipalityValid()){
            return false;
        }
        if(!isMobileValid()){
            return false;
        }
        if(!isEmailValid()){
            return false;
        }
        return true;
    }

    private boolean isTownMunicipalityValid(){
        if(sMuncplID == null || sMuncplID.trim().isEmpty()){
            message = "Please provide Town.";
            return false;
        }
        return true;
    }
}
