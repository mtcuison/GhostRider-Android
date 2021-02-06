package org.rmj.guanzongroup.onlinecreditapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PersonalInfoModel implements Parcelable {

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
    private final List<PersonalMobileNo> mobileNoList = new ArrayList<>();
    private String PhoneNox;
    private String EmailAdd;
    private String FbAccntx;
    private String VbrAccnt;

    //for save instance state
    private String ProvNme;
    private String TownNme;

    private String message;

    public PersonalInfoModel() {
    }

    protected PersonalInfoModel(Parcel in) {
        LastName = in.readString();
        FrstName = in.readString();
        MiddName = in.readString();
        Suffix = in.readString();
        NickName = in.readString();
        BrthDate = in.readString();
        BrthPlce = in.readString();
        Gender = in.readString();
        CvlStats = in.readString();
        Citizenx = in.readString();
        MotherNm = in.readString();
        PhoneNox = in.readString();
        EmailAdd = in.readString();
        FbAccntx = in.readString();
        VbrAccnt = in.readString();
        ProvNme = in.readString();
        TownNme = in.readString();
        message = in.readString();
    }

    public static final Creator<PersonalInfoModel> CREATOR = new Creator<PersonalInfoModel>() {
        @Override
        public PersonalInfoModel createFromParcel(Parcel in) {
            return new PersonalInfoModel(in);
        }

        @Override
        public PersonalInfoModel[] newArray(int size) {
            return new PersonalInfoModel[size];
        }
    };

    public boolean isPersonalInfoValid(){
        return isLastNameValid() &&
                isFrstNameValid() &&
                isMiddNameValid() &&
                isBirthdateValid() &&
                isBirthPlaceValid() &&
                isGenderValid() &&
                isCivilStatusValid() &&
                isCitizenshipValid() &&
                isMotherNameValid() &&
                isPrimaryContactValid() &&
                isSecondaryContactValid() &&
                isTertiaryContactValid();
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
        PersonalMobileNo mobileNo = new PersonalMobileNo(MobileNo, Postpaid, PostYear);
        mobileNoList.add(mobileNo);
    }

    public String getPhoneNox() {
        return PhoneNox;
    }

    public void setPhoneNox(String phoneNox) {
        PhoneNox = phoneNox;
    }

    public String getEmailAdd() {
        return EmailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        EmailAdd = emailAdd;
    }

    public String getFbAccntx() {
        return FbAccntx;
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
        if(MiddName.trim().isEmpty()){
            message = "Please enter middle name";
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
        if(Gender.trim().isEmpty()){
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
        if(CvlStats == null || CvlStats.isEmpty()){
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

    private boolean isPrimaryContactValid(){
        if(mobileNoList.get(0).getMobileNo().trim().isEmpty()){
            message = "Please enter primary contact number";
            return false;
        }
        if(!mobileNoList.get(0).getMobileNo().substring(0, 2).equalsIgnoreCase("09")){
            message = "Contact number must start with '09'";
            return false;
        }
        if(mobileNoList.get(0).getMobileNo().length() != 11){
            message = "Please enter primary contact info";
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
                    message = "Please enter valid contact info";
                    return false;
                }
                if(mobileNoList.get(1).getMobileNo().equalsIgnoreCase(mobileNoList.get(0).getMobileNo())
                        || mobileNoList.get(1).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())){
                    message = "Contact numbers are duplicated";
                    return false;
                }
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
                    message = "Please enter primary contact info";
                    return false;
                }
                if(mobileNoList.get(0).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())
                        || mobileNoList.get(1).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())){
                    message = "Contact numbers are duplicated";
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(LastName);
        parcel.writeString(FrstName);
        parcel.writeString(MiddName);
        parcel.writeString(Suffix);
        parcel.writeString(NickName);
        parcel.writeString(BrthDate);
        parcel.writeString(BrthPlce);
        parcel.writeString(Gender);
        parcel.writeString(CvlStats);
        parcel.writeString(Citizenx);
        parcel.writeString(MotherNm);
        parcel.writeString(PhoneNox);
        parcel.writeString(EmailAdd);
        parcel.writeString(FbAccntx);
        parcel.writeString(VbrAccnt);
        parcel.writeString(ProvNme);
        parcel.writeString(TownNme);
        parcel.writeString(message);
    }

    public static class PersonalMobileNo{
        String MobileNo;
        String isPostPd;
        int PostYear;

        public PersonalMobileNo(String mobileNo, String isPostPd, int postYear) {
            MobileNo = mobileNo;
            this.isPostPd = isPostPd;
            PostYear = postYear;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public String getIsPostPd() {
            return isPostPd;
        }

        public int getPostYear() {
            return PostYear;
        }
    }
}
