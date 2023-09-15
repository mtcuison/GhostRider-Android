/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo;

public class LoanUnit {
    private String TransNox;
    private String EntryNox;
    private String AccntNox;

    private String Remarksx;
    private String LastName;
    private String FrstName;
    private String MiddName;
    private String Suffixxx;
    private String FilePath;
    private String FileName;
    private String Latitude;
    private String Longtude;

    private String HouseNox;
    private String Streetxx;
    private String TownIDxx;
    private String BrgyIDxx;

    private String Genderxx;
    private String CvilStat;

    private String BirthDte;
    private String BirthPlc;

    private String PhoneNox;
    private String MobileNo;
    private String EmailAdd;
    private String Remarks1;

    private String message;

    public LoanUnit() {
    }

    public String getMessage() { return message; }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(String entryNox) {
        EntryNox = entryNox;
    }

    public String getAccntNox() {
        return AccntNox;
    }

    public void setAccntNox(String accntNox) {
        AccntNox = accntNox;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setFrstName(String frstName) {
        FrstName = frstName;
    }

    public void setMiddName(String middName) {
        MiddName = middName;
    }

    public String getSuffixxx() {
        return Suffixxx;
    }

    public void setSuffixxx(String suffixxx) {
        Suffixxx = suffixxx;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongtude() {
        return Longtude;
    }

    public void setLongtude(String longtude) {
        Longtude = longtude;
    }

    public String getHouseNox() {
        return HouseNox;
    }

    public void setHouseNox(String houseNox) {
        HouseNox = houseNox;
    }

    public String getStreetxx() {
        return Streetxx;
    }

    public void setStreetxx(String streetxx) {
        Streetxx = streetxx;
    }

    public void setTownIDxx(String townIDxx) {
        TownIDxx = townIDxx;
    }

    public String getBrgyIDxx() {
        return BrgyIDxx;
    }

    public void setBrgyIDxx(String brgyIDxx) {
        BrgyIDxx = brgyIDxx;
    }

    public String getGenderxx() {
        return Genderxx;
    }

    public void setGenderxx(String genderxx) {
        Genderxx = genderxx;
    }

    public String getCvilStat() {
        return CvilStat;
    }

    public void setCvilStat(String cvilStat) {
        CvilStat = cvilStat;
    }

    public String getBirthDte() {
        return BirthDte;
    }

    public void setBirthDte(String birthDte) {
        BirthDte = birthDte;
    }

    public String getBirthPlc() {
        return BirthPlc;
    }

    public void setBirthPlc(String birthPlc) {
        BirthPlc = birthPlc;
    }

    public String getPhoneNox() {
        return PhoneNox;
    }

    public void setPhoneNox(String phoneNox) {
        PhoneNox = phoneNox;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public void setEmailAdd(String emailAdd) {
        EmailAdd = emailAdd;
    }

    public String getRemarks1() {
        return Remarks1;
    }

    public void setRemarks1(String remarks1) {
        Remarks1 = remarks1;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFrstName() {
        return FrstName;
    }

    public String getMiddName() {
        return MiddName;
    }

    public String getTownIDxx() {
        return TownIDxx;
    }

    public String getEmailAdd() {
        return EmailAdd;
    }

    public boolean isDataValid(){
        if (LastName.trim().isEmpty()){
            message = "Please enter lastname.";
            return false;
        } else if (FrstName.trim().isEmpty()){
            message = "Please enter lastname.";
            return false;
        } else if (MiddName.trim().isEmpty()){
            message = "Please enter lastname.";
            return false;
        } else if (Suffixxx.trim().isEmpty()){
            message = "Please enter lastname.";
            return false;
        } else if (HouseNox.trim().isEmpty()){
            message = "Please enter house no.";
            return false;
        } else if (Streetxx.trim().isEmpty()){
            message = "Please enter Street/ Sitio /Lot No.";
            return false;
        } else if (TownIDxx == null || TownIDxx.trim().isEmpty()){
            message = "Please enter municipality.";
            return false;
        } else if (BrgyIDxx == null || BrgyIDxx.trim().isEmpty()){
            message = "Please enter barangay.";
            return false;
        } else if (Genderxx == null ||Integer.parseInt(Genderxx) < 0){
            message = "Please select gender.";
            return false;
        } else if (CvilStat == null || Integer.parseInt(CvilStat) < 0){
            message = "Please select civil status.";
            return false;
        } else if (Latitude == null || Latitude.trim().isEmpty()){
            message = "empty";
            return false;
        } else if (BirthDte.trim().isEmpty()){
            message = "Please enter birth date.";
            return false;
        } else if (BirthPlc == null || BirthPlc.trim().isEmpty()){
            message = "Please enter birth date.";
            return false;
        } else if (PhoneNox.trim().isEmpty()){
            message = "Please enter phone number.";
            return false;
        } else if (Remarks1.trim().isEmpty()){
            message = "Required field!" +
                    "\nPlease enter remarks.";
            return false;
        } else if(MobileNo.trim().isEmpty()){
            message = "Please enter Contact Number!";
            return false;
        } else if (!MobileNo.trim().substring(0,2).equalsIgnoreCase("09")){
            message = "Contact number must start with " + MobileNo.trim().substring(0,2);
            return false;
        } else if(MobileNo.length() != 11){
            message = "Please provide valid contact no!";
            return false;
        } else if (EmailAdd.trim().isEmpty()){
            message = "Please enter email address.";
            return false;
        }
        return true;
    }
}
