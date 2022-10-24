package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

import org.rmj.g3appdriver.etc.FormatUIText;

import java.util.ArrayList;
import java.util.List;

public class ClientInfo {

    private String TransNox;
    private String LastName;
    private String FrstName;
    private String MiddName;
    private String Suffix;
    private String NickName;
    private String BrthDate;
    private String BrthPlce;
    private String BirthPlc; //This holds the data for preview of Town, Province names.
    private String Gender;
    private String CvlStats;
    private String Citizenx;
    private String CtznShip; //This holds the data for preview of citizenship
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

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
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

    /**
     *
     * @return Use this get method to retrieve the UI preview of birthdate.
     */
    public String getBirthDte() {
        return FormatUIText.formatGOCasBirthdate(BrthDate);
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

    public String getCvlStats() {
        return CvlStats;
    }

    public void setCvlStats(String cvlStats) {
        CvlStats = cvlStats;
    }

    public String getCitizenx() {
        return Citizenx;
    }

    /**
     *
     * @return use this get method to
     * retrieve the UI preview of client citizenship.
     */
    public String getCtznShip() {
        return CtznShip;
    }

    /**
     *
     * @param ctznShip use this method to set the UI preview of citizenship.
     */
    public void setCtznShip(String ctznShip) {
        CtznShip = ctznShip;
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

    public boolean isDataValid(){
        if(LastName.trim().isEmpty()){
            message = "Please enter last name";
            return false;
        }

        if(FrstName.trim().isEmpty()){
            message = "Please enter first name";
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

        if(Integer.parseInt(Gender) < 0){
            message = "Please select gender";
            return false;
        }

        if(Gender.equalsIgnoreCase("1") && CvlStats.equalsIgnoreCase("1")) {
            if (MotherNm.trim().isEmpty()) {
                message = "Please enter mother's maiden name";
                return false;
            }
        }

        if(CvlStats == null || Integer.parseInt(CvlStats) < 0){
            message = "Please select civil status";
            return false;
        }

        if(Citizenx == null || Citizenx.trim().isEmpty()){
            message = "Please enter citizenship";
            return false;
        }

        if (mobileNoList.size()> 0) {
            return isPrimaryContactValid() &&
                    isSecondaryContactValid() &&
                    isTertiaryContactValid();
        }else {
            message = "Please enter primary contact number";
            return false;
        }
    }

    private boolean isPrimaryContactValid(){
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

    private boolean isSecondaryContactValid(){
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

    private boolean isTertiaryContactValid(){
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

    public boolean isEqual(ClientInfo val){
        if(!val.getLastName().equalsIgnoreCase(LastName)){
            return false;
        } else if(!val.getFrstName().equalsIgnoreCase(FrstName)){
            return false;
        } else if(!val.getMiddName().equalsIgnoreCase(MiddName)){
            return false;
        } else if(!val.getSuffix().equalsIgnoreCase(Suffix)){
            return false;
        } else if(!val.getNickName().equalsIgnoreCase(NickName)){
            return false;
        } else if(!val.getBirthDte().equalsIgnoreCase(BrthDate)){
            return false;
        } else if(!val.getBirthPlc().equalsIgnoreCase(BrthPlce)){
            return false;
        } else if(!val.getBirthPlc().equalsIgnoreCase(BirthPlc)){
            return false;
        } else if(!val.getGender().equalsIgnoreCase(Gender)){
            return false;
        } else if(!val.getCvlStats().equalsIgnoreCase(CvlStats)){
            return false;
        } else if(!val.getCitizenx().equalsIgnoreCase(Citizenx)){
            return false;
        } else if(!val.getCtznShip().equalsIgnoreCase(CtznShip)){
            return false;
        } else if(!val.getMotherNm().equalsIgnoreCase(MotherNm)){
            return false;
        } else if(!val.getPhoneNox().equalsIgnoreCase(PhoneNox)){
            return false;
        } else if(!val.getEmailAdd().equalsIgnoreCase(EmailAdd)){
            return false;
        } else if(!val.getFbAccntx().equalsIgnoreCase(FbAccntx)){
            return false;
        } else if(!val.getVbrAccnt().equalsIgnoreCase(VbrAccnt)){
            return false;
        } else if(val.getMobileNoQty() != mobileNoList.size()){
            return false;
        } else if(val.getMobileNoQty() != mobileNoList.size()){
            for(int x = 0; x < val.getMobileNoQty(); x++){
                if(!val.getMobileNo(x).equalsIgnoreCase(mobileNoList.get(x).getMobileNo())){
                    return false;
                } else if(!val.getPostPaid(x).equalsIgnoreCase(mobileNoList.get(x).getIsPostPd())){
                    return false;
                } else if(val.getPostYear(x) != (mobileNoList.get(x).getPostYear())){
                    return false;
                }
            }
        }

        return true;
    }
}
