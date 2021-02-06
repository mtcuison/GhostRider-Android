package org.rmj.guanzongroup.onlinecreditapplication.Model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class CoMakerModel {

    private String coLastName;
    private String coFrstName;
    private String coMiddName;
    private String coSuffix;
    private String coNickName;
    private String coBrthDate;
    private String coBrthPlce;
    private final List<CoMakerMobileNo> mobileNoList = new ArrayList<>();
    private String coFbAccntx;
    private String coIncomeSource;
    private String coBorrowerRel;
    private String coContactCount;

    //for save instance state
    private String ProvNme;
    private String TownNme;

    private String message;

    public CoMakerModel() {
    }

    public boolean isCoMakerInfoValid(){
        return isLastNameValid() &&
                isFrstNameValid() &&
                isMiddNameValid() &&
                isBirthdateValid() &&
                isBirthPlaceValid() &&
                isPrimaryContactValid() &&
                isSecondaryContactValid() &&
                isTertiaryContactValid();
    }


    public String getMessage(){
        return message;
    }

    public String getCoLastName() {
        return coLastName;
    }

    public void setCoLastName(String coLastName) {
        this.coLastName = coLastName;
    }

    public String getCoFrstName() {
        return coFrstName;
    }

    public void setCoFrstName(String coFrstName) {
        this.coFrstName = coFrstName;
    }

    public String getCoMiddName() {
        return coMiddName;
    }

    public void setCoMiddName(String coMiddName) {
        this.coMiddName = coMiddName;
    }

    public String getCoSuffix() {
        return coSuffix;
    }

    public void setCoSuffix(String coSuffix) {
        this.coSuffix = coSuffix;
    }

    public String getCoNickName() {
        return coNickName;
    }

    public void setCoNickName(String coNickName) {
        this.coNickName = coNickName;
    }

    public String getCoBrthDate() {
        return coBrthDate;
    }

    public void setcoBrthDate(String coBrthDate) {
        this.coBrthDate = coBrthDate;
    }

    public void setcoBrthPlce(String coBrthPlce){
        this.coBrthPlce = coBrthPlce;
    }

    public String getCoBrthPlce() {
        return coBrthPlce;
    }

    public void setCoIncomeSource(String coIncomeSource){
        this.coIncomeSource = coIncomeSource;
    }

    public String getCoIncomeSource() {
        return coIncomeSource;
    }

    public void setCoBorrowerRel(String coBorrowerRel){
        this.coBorrowerRel = coBorrowerRel;
    }

    public String getCoBorrowerRel() {
        return coBorrowerRel;
    }

    public int getCoMobileNoQty(){
        return mobileNoList.size();
    }

    public String getCoMobileNo(int position){
        return mobileNoList.get(position).getMobileNo();
    }

    public String getCoPostPaid(int position){
        return mobileNoList.get(position).getIsPostPd();
    }

    public int getCoPostYear(int position){
        return mobileNoList.get(position).getPostYear();
    }

    public void setCoMobileNo(String MobileNo, String Postpaid, int PostYear){
        CoMakerMobileNo mobileNo = new CoMakerMobileNo(MobileNo, Postpaid, PostYear);
        this.mobileNoList.add(mobileNo);
    }
   
    public String getCoFbAccntx() {
        return coFbAccntx;
    }

    public void setCoFbAccntx(String coFbAccntx) {
        this.coFbAccntx = coFbAccntx;
    }



    private boolean isLastNameValid(){
        if(coLastName.trim().isEmpty()){
            message = "Please enter last name";
            return false;
        }
        return true;
    }

    private boolean isFrstNameValid(){
        if(coFrstName.trim().isEmpty()){
            message = "Please enter first name";
            return false;
        }
        return true;
    }

    private boolean isMiddNameValid(){
        if(coMiddName.trim().isEmpty()){
            message = "Please enter middle name";
            return false;
        }
        return true;
    }

    private boolean isBirthdateValid(){
        if(coBrthDate.trim().isEmpty()){
            message = "Please enter birth date";
            return false;
        }
        return true;
    }

    private boolean isBirthPlaceValid(){
        if(coBrthPlce == null || coBrthPlce.equalsIgnoreCase("")){
            message = "Please enter birth place";
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
                    message = "Please enter primary contact info";
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


    public static class CoMakerMobileNo{
        String MobileNo;
        String isPostPd;
        int PostYear;

        public CoMakerMobileNo(String mobileNo, String isPostPd, int postYear) {
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
