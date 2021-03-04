package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

import android.location.Address;

public class LoanUnitModel {
    private String luRemarks;
    private String luLastName;
    private String luFirstName;
    private String luMiddleName;
    private String luSuffix;
    private String luImgPath;

    private String luHouseNo;
    private String luStreet;
    private String luTown;
    private String luBrgy;

    private String luGender;
    private String luCivilStats;

    private String luBDate;
    private String luBPlace;

    private String luPhone;
    private String luMobile;
    private String luEmail;

    private String message;

    public LoanUnitModel() {
    }

    public String getMessage() { return message; }

    public String getLuRemarks() { return this.luRemarks; }
    public void setLuRemarks(String luRemarks) {
        this.luRemarks = luRemarks;
    }

    public String getLuImgPath() { return this.luImgPath; }
    public void setLuImgPath(String luImgPath) {
        this.luImgPath = luImgPath;
    }

    public String getLuLastName() { return this.luLastName; }
    public void setLuLastName(String luLastName) {
        this.luLastName = luLastName;
    }

    public String getLuFirstName() { return this.luFirstName; }
    public void setLuFirstName(String luFirstName) {
        this.luFirstName = luFirstName;
    }

    public String getLuMiddleName() { return this.luMiddleName; }
    public void setLuMiddleName(String luMiddleName) {
        this.luMiddleName = luMiddleName;
    }

    public String getLuSuffix() { return this.luSuffix; }
    public void setLuSuffix(String luSuffix) {
        this.luSuffix = luSuffix;
    }

    public String getLuGender() { return this.luGender; }
    public void setLuGender(String luGender) {
        this.luGender = luGender;
    }

    public String getLuHouseNo() { return this.luHouseNo; }
    public void setLuHouseNo(String luHouseNo) {
        this.luHouseNo = luHouseNo;
    }

    public String getLuStreet() { return this.luStreet; }
    public void setLuStreet(String luStreet) {
        this.luStreet = luStreet;
    }

    public String getLuTown() { return this.luTown; }
    public void setLuTown(String luTown) {
        this.luTown = luTown;
    }

    public String getLuBrgy() { return this.luBrgy; }
    public void setLuBrgy(String luBrgy) {
        this.luBrgy = luBrgy;
    }

    public String getLuCivilStats() { return this.luCivilStats; }
    public void setLuCivilStats(String luCivilStats) {
        this.luCivilStats = luCivilStats;
    }

    public String getLuBDate() { return this.luBDate; }
    public void setLuBDate(String luBDate) {
        this.luBDate = luBDate;
    }

    public String getLuBPlace() { return this.luBPlace; }
    public void setLuBPlace(String luBPlace) {
        this.luBPlace = luBPlace;
    }

    public String getLuPhone() { return this.luPhone; }
    public void setLuPhone(String luPhone) {
        this.luPhone = luPhone;
    }

    public String getLuMobile() { return this.luMobile; }
    public void setLuMobile(String luMobile) {
        this.luMobile = luMobile;
    }

    public String getLuEmail() { return this.luEmail; }
    public void setLuEmail(String luEmail) {
        this.luEmail = luEmail;
    }

    public boolean isValidData(){
        return isValidFullName() &&
                isGender() &&
                isValidAddress() &&
                isCivilStats() &&
                isBDate() &&
                isBPlace() &&
                isContactValid() &&
                isValidImgPth();
    }

    public boolean isValidFullName(){
        return isLuLastName() &&
                isLuFirstName() &&
                isLuMiddleName();
    }

    public boolean isValidAddress(){
        return isHouseNo() &&
                isStreet() &&
                isLuTown() &&
                isBrgy();
    }

    private boolean isLuLastName(){
        if (luLastName.trim().isEmpty()){
            message = "Please enter lastname.";
            return false;
        }
        return true;
    }
    private boolean isLuFirstName(){
        if (luFirstName.trim().isEmpty()){
            message = "Please enter lastname.";
            return false;
        }
        return true;
    }
    private boolean isLuMiddleName(){
        if (luMiddleName.trim().isEmpty()){
            message = "Please enter lastname.";
            return false;
        }
        return true;
    }
    private boolean isSuffix(){
        if (luSuffix.trim().isEmpty()){
            message = "Please enter lastname.";
            return false;
        }
        return true;
    }

//    Address
    private boolean isHouseNo(){
        if (luHouseNo.trim().isEmpty()){
            message = "Please enter house no.";
            return false;
        }
        return true;
    }
    private boolean isStreet(){
        if (luStreet.trim().isEmpty()){
            message = "Please enter Street/ Sitio /Lot No.";
            return false;
        }
        return true;
    }
    private boolean isLuTown(){
        if (luTown == null || luTown.trim().isEmpty()){
            message = "Please enter municipality.";
            return false;
        }
        return true;
    }
    private boolean isBrgy(){
        if (luBrgy == null ||luBrgy.trim().isEmpty()){
            message = "Please enter barangay.";
            return false;
        }
        return true;
    }
    private boolean isGender(){
        if (luGender == null ||Integer.parseInt(luGender) < 0){
            message = "Please select gender.";
            return false;
        }
        return true;
    }

    private boolean isCivilStats(){
        if (luCivilStats == null || Integer.parseInt(luCivilStats) < 0){
            message = "Please select civil status.";
            return false;
        }
        return true;
    }
    private boolean isValidImgPth(){
        if (luImgPath == null || luImgPath.trim().isEmpty()){
            message = "empty";
            return false;
        }
        return true;
    }

    private boolean isBDate(){
        if (luBDate.trim().isEmpty()){
            message = "Please enter birth date.";
            return false;
        }
        return true;
    }

    private boolean isBPlace(){
        if (luBPlace == null || luBPlace.trim().isEmpty()){
            message = "Please enter birth date.";
            return false;
        }
        return true;
    }

    private boolean isLUPhone(){
        if (luPhone.trim().isEmpty()){
            message = "Please enter phone number.";
            return false;
        }
        return true;
    }

    private boolean isContactValid(){
        if(luMobile.trim().isEmpty()){
            message = "Please enter Contact Number!";
            return false;
        } else if (!luMobile.trim().substring(0,2).equalsIgnoreCase("09")){
            message = "Contact number must start with " + luMobile.trim().substring(0,2);
            return false;
        } else if(luMobile.length() != 11){
            message = "Please provide valid contact no!";
            return false;
        }
        return true;
    }
    private boolean isLuEmail(){
        if (luEmail.trim().isEmpty()){
            message = "Please enter email address.";
            return false;
        }
        return true;
    }
    public String getRemarksCode(){
        switch (luRemarks){
            case "Paid":
                return "PAY";
            case "Promise to Pay":
                return "PTP";
            case "Customer Not Around":
                return "CNA";
            case "For Legal Action":
                return "FLA";
            case "Carnap":
                return "Car";
            case "Uncooperative":
                return "UNC";
            case "Missing Customer":
                return "MCs";
            case "Missing Unit":
                return "MUn";
            case "Missing Client and Unit":
                return "MCU";
            case "Loan Unit":
                return "LUn";
            case "Transferred/Assumed":
                return "TA";
            case "False Ownership":
                return "FO";
            case "Did Not Pay":
                return "DNP";
            case "Not Visited":
                return "NV";
            case "Others":
                return "OTH";
        }
        return "";
    }
}
