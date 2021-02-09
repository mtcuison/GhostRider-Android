package org.rmj.guanzongroup.onlinecreditapplication.Model;

public class SelfEmployedInfoModel {

    private String sBussNtrx;
    private String sBussName;
    private String sBussAddx;
    private String sBussProv;
    private String sBussTown;
    private String sBussType;
    private String sBussSize;
    private String sLenghtSv;
    private String sMnthlyIn;
    private String sMnthlyEx;

    private String message;

    public SelfEmployedInfoModel() {
    }

    public String getMessage() {
        return message;
    }

    public String getNatureOfBusiness() {
        return sBussNtrx;
    }

    public void setNatureOfBusiness(String sBussNtrx) {
        this.sBussNtrx = sBussNtrx;
    }

    public String getNameOfBusiness() {
        return sBussName;
    }

    public void setNameOfBusiness(String sBussName) {
        this.sBussName = sBussName;
    }

    public String getBusinessAddress() {
        return sBussAddx;
    }

    public void setBusinessAddress(String sBussAddx) {
        this.sBussAddx = sBussAddx;
    }

    public String getProvince() {
        return sBussProv;
    }

    public void setProvince(String sBussProv) {
        this.sBussProv = sBussProv;
    }

    public String getTown() {
        return sBussTown;
    }

    public void setTown(String sBussTown) {
        this.sBussTown = sBussTown;
    }

    public String getTypeOfBusiness() {
        return sBussType;
    }

    public void setTypeOfBusiness(String sBussType) {
        this.sBussType = sBussType;
    }

    public String getSizeOfBusiness() {
        return sBussSize;
    }

    public void setSizeOfBusiness(String sBussSize) {
        this.sBussSize = sBussSize;
    }

    public double getLenghtOfService() {
        return Double.parseDouble(sLenghtSv);
    }

    public void setLengthOfService(String sLenghtSv) {
        this.sLenghtSv = sLenghtSv;
    }

    public long getMonthlyIncome() {
        return Long.parseLong(sMnthlyIn);
    }

    public void setMonthlyIncome(String sMnthlyIn) {
        this.sMnthlyIn = sMnthlyIn;
    }

    public long getMonthlyExpense() {
        return Long.parseLong(sMnthlyEx);
    }

    public void setMonthlyExpense(String sMnthlyEx) {
        this.sMnthlyEx = sMnthlyEx;
    }

    public boolean isSelfEmployedValid(){
        return isBusinessNatureValid() &&
                isBusinessNameValid() &&
                isBusinessAddressValid() &&
                isTownValid() &&
                isBusinessTypeValid() &&
                isBusinessSizeValid() &&
                isLenghtOfServiceValid() &&
                isMonthlyIncomeValid() &&
                isMonthlyExpenseValid();
    }

    boolean isBusinessNatureValid(){
        if(sBussNtrx.trim().isEmpty()){
            message = "Please select nature of business";
            return false;
        }
        return true;
    }

    boolean isBusinessNameValid(){
        if(sBussName.trim().isEmpty()){
            message = "Please enter name of business";
            return false;
        }
        return true;
    }

    boolean isBusinessAddressValid(){
        if(sBussAddx.trim().isEmpty()){
            message = "Please enter business address";
            return false;
        }
        return true;
    }

    boolean isTownValid(){
        if(sBussTown.trim().isEmpty()){
            message = "Please enter town or municipality";
            return false;
        }
        return true;
    }

    boolean isBusinessTypeValid(){
        if(sBussType.trim().isEmpty()){
            message = "Please select type of business";
            return false;
        }
        return true;
    }

    boolean isBusinessSizeValid(){
        if(sBussSize.trim().isEmpty()){
            message = "Please select size of business";
            return false;
        }
        return true;
    }

    boolean isLenghtOfServiceValid(){
        if(sLenghtSv.trim().isEmpty()){
            message = "Please enter length of service";
            return false;
        }
        return true;
    }

    boolean isMonthlyIncomeValid(){
        if(sMnthlyIn.trim().isEmpty()){
            message = "Please enter monthly income";
            return false;
        }
        return true;
    }

    boolean isMonthlyExpenseValid(){
        if(sMnthlyEx.trim().isEmpty()){
            message = "Please enter monthly expense";
            return false;
        }
        return true;
    }
}
