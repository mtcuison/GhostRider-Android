package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

import java.util.ArrayList;
import java.util.List;

public class ClientInfo {


    private String LastName;
    private String FrstName;
    private String MiddName;
    private String Suffix;
    private String NickName;
    private String BrthDate;
    private String BrthPlce;
    private String Gender;
    private String CvlStats;
    private String Citizenx;
    private String MotherNm;
    private final List<MobileNo> mobileNoList = new ArrayList<>();
    private String PhoneNox;
    private String EmailAdd;
    private String FbAccntx;
    private String VbrAccnt;
    private String ImgPath;

    //for save instance state
    private String ProvNme;
    private String TownNme;

    private String message;

    public ClientInfo() {
    }


    public String getMessage(){
        return message;
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

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getBrthDate() {
        return BrthDate;
    }

    public void setBrthDate(String brthDate) {
        BrthDate = brthDate;
    }

    public void setBrthPlce(String brthPlce){
        BrthPlce = brthPlce;
    }

    public String getBrthPlce() {
        return BrthPlce;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCvlStats() {
        return CvlStats;
    }

    public void setCvlStats(String cvlStats) {
        CvlStats = cvlStats;
    }

    public String getCitizenx() {
        return Citizenx;
    }

    public void setCitizenx(String citizenx) {
        Citizenx = citizenx;
    }

    public String getMotherNm() {
        return MotherNm;
    }

    public void setMotherNm(String motherNm) {
        MotherNm = motherNm;
    }

    public int getMobileNoQty(){
        return mobileNoList.size();
    }

    public String getMobileNo(int position){
        return mobileNoList.get(position).getMobileNo();
    }

    public String getPostPaid(int position){
        return mobileNoList.get(position).getIsPostPd();
    }

    public int getPostYear(int position){
        return mobileNoList.get(position).getPostYear();
    }

    public void setMobileNo(String MobileNo, String Postpaid, int PostYear){
        MobileNo mobileNo = new MobileNo(MobileNo, Postpaid, PostYear);
        mobileNoList.add(mobileNo);
    }

    /**
     * Clear List of Mobile No if Any Error Occurs to prevent mobile no duplicate.
     */
    public void clearMobileNo(){
        mobileNoList.clear();
    }

    public String getPhoneNox() {
        return PhoneNox;
    }

    public void setPhoneNox(String phoneNox) {
        PhoneNox = phoneNox;
    }

    public String getEmailAdd() {
        return EmailAdd.trim();
    }

    public void setEmailAdd(String emailAdd) {
        EmailAdd = emailAdd;
    }

    public String getFbAccntx() {
        return FbAccntx.trim();
    }

    public void setFbAccntx(String fbAccntx) {
        FbAccntx = fbAccntx;
    }

    public String getVbrAccnt() {
        return VbrAccnt;
    }

    public void setVbrAccnt(String vbrAccnt) {
        VbrAccnt = vbrAccnt;
    }

    private boolean isLastNameValid(){
        if(LastName.trim().isEmpty()){
            message = "Please enter last name";
            return false;
        }
        return true;
    }

    private boolean isFrstNameValid(){
        if(FrstName.trim().isEmpty()){
            message = "Please enter first name";
            return false;
        }
        return true;
    }

    private boolean isMiddNameValid(){
        if(MiddName.trim().isEmpty()) {
            message = "Please enter middle name";
            return false;
        }else if(MiddName.length() <= 1){
            message = "Please provide valid Middle name.";
            return false;
        }
        return true;
    }

    private boolean isBirthdateValid(){
        if(BrthDate.trim().isEmpty()){
            message = "Please enter birth date";
            return false;
        }
        return true;
    }

    private boolean isBirthPlaceValid(){
        if(BrthPlce == null || BrthPlce.equalsIgnoreCase("")){
            message = "Please enter birth place";
            return false;
        }
        return true;
    }

    private boolean isGenderValid(){
        if(Integer.parseInt(Gender) < 0){
            message = "Please select gender";
            return false;
        }
        return true;
    }

    private boolean isMotherNameValid(){
        if(Gender.equalsIgnoreCase("1") && CvlStats.equalsIgnoreCase("1")) {
            if (MotherNm.trim().isEmpty()) {
                message = "Please enter mother's maiden name";
                return false;
            }
        }
        return true;
    }

    private boolean isCivilStatusValid(){
        if(CvlStats == null || Integer.parseInt(CvlStats) < 0){
            message = "Please select civil status";
            return false;
        }
        return true;
    }

    private boolean isCitizenshipValid(){
        if(Citizenx == null || Citizenx.trim().isEmpty()){
            message = "Please enter citizenship";
            return false;
        }
        return true;
    }
    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    private boolean isValidContact(){
        if (mobileNoList.size()> 0) {
            return isPrimaryContactValid() &&
                    isSecondaryContactValid() &&
                    isTertiaryContactValid();
        }else {
            message = "Please enter primary contact number";
            return false;
        }
    }
    public boolean isPrimaryContactValid(){
        if(mobileNoList.get(0).getMobileNo().trim().isEmpty()){
            message = "Please enter primary contact number";
            return false;
        }
        if(Integer.parseInt(mobileNoList.get(0).getIsPostPd()) < 0){
            message = "Please select sim 1 card type";
            return false;
        }
        if(!mobileNoList.get(0).getMobileNo().substring(0, 2).equalsIgnoreCase("09")){
            message = "Contact number must start with '09'";
            return false;
        }
        if(mobileNoList.get(0).getMobileNo().length() != 11){
            message = "Please complete primary contact info";
            return false;
        }
        return true;
    }

    public boolean isSecondaryContactValid(){
        if(mobileNoList.size() >= 2) {
            if (mobileNoList.get(1).getMobileNo().trim().isEmpty()) {
                if(!mobileNoList.get(1).getMobileNo().substring(0, 2).equalsIgnoreCase("09")){
                    message = "Contact number must start with '09'";
                    return false;
                }
                if(mobileNoList.get(1).getMobileNo().length() != 11){
                    message = "Please complete 2nd contact info";
                    return false;
                }
                if(mobileNoList.get(1).getMobileNo().equalsIgnoreCase(mobileNoList.get(0).getMobileNo())
                        || mobileNoList.get(1).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())){
                    message = "Contact numbers are duplicated";
                    return false;
                }
            }
            if(Integer.parseInt(mobileNoList.get(1).getIsPostPd()) < 0){
                message = "Please select sim 2 card type";
                return false;
            }
        }
        return true;
    }

    public boolean isTertiaryContactValid(){
        if(mobileNoList.size() == 3) {
            if (!mobileNoList.get(2).getMobileNo().trim().isEmpty()) {
                if(!mobileNoList.get(2).getMobileNo().substring(0, 2).equalsIgnoreCase("09")){
                    message = "Contact number must start with '09'";
                    return false;
                }
                if(mobileNoList.get(2).getMobileNo().length() != 11){
                    message = "Please complete 3rd contact info";
                    return false;
                }
                if(mobileNoList.get(0).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())
                        || mobileNoList.get(1).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())){
                    message = "Contact numbers are duplicated";
                    return false;
                }
            }
            if(Integer.parseInt(mobileNoList.get(2).getIsPostPd()) < 0){
                message = "Please select sim 3 card type";
                return false;
            }
        }
        return true;
    }
}
