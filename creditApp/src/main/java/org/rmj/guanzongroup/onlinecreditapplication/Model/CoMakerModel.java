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

    //for save instance state
    private String message;

    public CoMakerModel() {
    }

    public CoMakerModel(String coLastName,
                        String coFrstName,
                        String coMiddName,
                        String coSuffix,
                        String coNickName,
                        String coBrthDate,
                        String coBrthPlce,
                        String coFbAccntx,
                        String coIncomeSource,
                        String coBorrowerRel) {
        this.coLastName = coLastName;
        this.coFrstName = coFrstName;
        this.coMiddName = coMiddName;
        this.coSuffix = coSuffix;
        this.coNickName = coNickName;
        this.coBrthDate = coBrthDate;
        this.coBrthPlce = coBrthPlce;
        this.coFbAccntx = coFbAccntx;
        this.coIncomeSource = coIncomeSource;
        this.coBorrowerRel = coBorrowerRel;
    }


    public boolean isCoMakerInfoValid(){
        if (!isBorrowerRel()){
            return false;
        }
        if (!isLastNameValid()){
            return false;
        }
        if (!isFrstNameValid()){
            return false;
        }
        if (!isMiddNameValid()){
            return false;
        }
        if (!isBirthdateValid()){
            return false;
        }
        if (!isBirthPlaceValid()){
            return false;
        }
        if (!isIncomeSource()){
            return false;
        }
        if (!isContactValid()){
            return false;
        }
        return true;
//        return isBorrowerRel() &&
//                isLastNameValid() &&
//                isFrstNameValid() &&
//                isMiddNameValid() &&
//                isBirthdateValid() &&
//                isBirthPlaceValid() &&
//                isIncomeSource() &&
//                isContactValid();
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
        if(coLastName == null || coLastName.trim().isEmpty()){
            message = "Please enter last name";
            return false;
        }
        return true;
    }

    private boolean isFrstNameValid(){
        if(coFrstName == null || coFrstName.trim().isEmpty()){
            message = "Please enter first name";
            return false;
        }
        return true;
    }

    private boolean isMiddNameValid(){
        if(coMiddName == null || coMiddName.trim().isEmpty()){
            message = "Please enter middle name";
            return false;
        }
        return true;
    }

    private boolean isBirthdateValid(){
        if(coBrthDate == null || coBrthDate.trim().isEmpty()){
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
    private boolean isBorrowerRel(){
        if(Integer.parseInt(coBorrowerRel) < 0){
            message = "Please enter Borrower Relationship!";
            return false;
        }
        return true;
    }
    private boolean isIncomeSource(){
        if(Integer.parseInt(coIncomeSource) < 0){
            message = "Please select source of income!";
            return false;
        }
        return true;
    }

    private boolean isContactValid(){
        if(mobileNoList.size() == 0){
            message = "Please enter primary contact.";
            return false;
        }else{
            return isPrimaryContactValid() &&
                    isSecondaryContactValid() &&
                    isTertiaryContactValid();
        }
    }

    private boolean isPrimaryContactValid(){
        if(mobileNoList.get(0).getMobileNo().trim().isEmpty()){
            message = "Please enter primary contact number";
            return false;
        }

        if(Integer.parseInt(mobileNoList.get(0).getIsPostPd()) < 0){
            message = "Please select sim card type";
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
            if(Integer.parseInt(mobileNoList.get(1).getIsPostPd()) < 0){
                message = "Please select sim card type";
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
                    message = "Please enter primary contact info";
                    return false;
                }
                if(mobileNoList.get(0).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())
                        || mobileNoList.get(1).getMobileNo().equalsIgnoreCase(mobileNoList.get(2).getMobileNo())){
                    message = "Contact numbers are duplicated";
                    return false;
                }
            }
            if(Integer.parseInt(mobileNoList.get(2).getIsPostPd()) < 0){
                message = "Please select sim card type";
                return false;
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
