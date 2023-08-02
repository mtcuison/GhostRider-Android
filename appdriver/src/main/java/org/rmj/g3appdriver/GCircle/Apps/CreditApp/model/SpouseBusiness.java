package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

public class SpouseBusiness {

    private String sTransNox = "";
    private String sBussNtrx = "";
    private String sBussName = "";
    private String sBussAddx = "";
    private String sBussProv = "";
    private String sBussTown = "";
    private String sBussType = "";
    private String sBussSize = "";
    private double sLenghtSv = 0;
    private String cIsYearxx = "";
    private long sMnthlyIn = 0;
    private long sMnthlyEx = 0;

    private String message;

    public SpouseBusiness() {

    }

    public String getMessage() {
        return message;
    }

    public String getTransNox() {
        return sTransNox;
    }

    public void setTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
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
        if(cIsYearxx.equalsIgnoreCase("0")){
            double ldValue = sLenghtSv;
            return ldValue / 12;
        } else {
            return sLenghtSv;
        }
    }

    public void setLengthOfService(double sLenghtSv) {
        this.sLenghtSv = sLenghtSv;
    }

    public void setIsYear(String sLenghtSv) {
        this.cIsYearxx = sLenghtSv;
    }

    public long getMonthlyIncome() {
        return sMnthlyIn;
    }

    public void setMonthlyIncome(long sMnthlyIn) {
        this.sMnthlyIn = sMnthlyIn;
    }

    public long getMonthlyExpense() {
        return sMnthlyEx;
    }

    public void setMonthlyExpense(long sMnthlyEx) {
        this.sMnthlyEx = sMnthlyEx;
    }

    public boolean isDataValid(){
        if(!isBusinessNatureValid() &&
                !isBusinessNameValid() &&
                !isBusinessAddressValid()){
            return true;
        }
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
        if(!sBussNtrx.trim().isEmpty() || !sBussNtrx.equalsIgnoreCase("")) {
            return true;
        } else {
            message = "Please select business nature";
            return false;
        }
    }

    boolean isBusinessNameValid(){
        if(sBussName.trim().isEmpty() || sBussName.equalsIgnoreCase("")){
            message = "Please enter name of business";
            return false;
        }
        return true;
    }

    boolean isBusinessAddressValid(){
        if(sBussAddx.trim().isEmpty() || sBussAddx.equalsIgnoreCase("")){
            message = "Please enter business address";
            return false;
        }
        return true;
    }

    boolean isTownValid(){
        if(sBussTown.trim().isEmpty() || sBussTown.equalsIgnoreCase("")){
            message = "Please enter municipality address";
            return false;
        }
        return true;
    }

    boolean isBusinessTypeValid(){
        if(sBussType.trim().isEmpty() || sBussType.trim().equalsIgnoreCase("")){
            message = "Please select type of business";
            return false;
        }
        return true;
    }

    boolean isBusinessSizeValid(){
        if(sBussSize.trim().isEmpty() || sBussSize.trim().equalsIgnoreCase("")){
            message = "Please select size of business";
            return false;
        }
        return true;
    }

    boolean isLenghtOfServiceValid(){
        if(sLenghtSv == 0){
            message = "Please enter length of service";
            return false;
        }else {
            return isLenghtOfServiceSpinnerValid();
        }
    }
    boolean isLenghtOfServiceSpinnerValid(){
        if(cIsYearxx.trim().isEmpty() || cIsYearxx.trim().equalsIgnoreCase("")){
            message = "Please enter length of service in Month/Year";
            return false;
        }
        return true;
    }

    boolean isMonthlyIncomeValid(){
        if(sMnthlyIn == 0){
            message = "Please enter monthly income";
            return false;
        }
        return true;
    }

    boolean isMonthlyExpenseValid(){
        if(sMnthlyEx == 0){
            message = "Please enter monthly expense";
            return false;
        }
        return true;
    }
}
