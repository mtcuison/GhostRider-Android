package org.rmj.guanzongroup.onlinecreditapplication.Model;

public class DisbursementInfoModel {
    private String transNo;
    private String elctX;
    private String waterX;
    private String foodX;
    private String loans;
    private String bankN;
    private String stypeX;
    private String ccBnk;
    private String limitCC;
    private String yearS;
    private String message;

    public DisbursementInfoModel() {
    }


    public String getMessage() {
        return message;
    }
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }
    public String getTransNo() {
        return transNo;
    }

    public void setElctX(String elctX) {
        this.elctX = elctX;
    }
    public double getElctX() {
        if(elctX != null && !elctX.trim().isEmpty()) {
            return Double.parseDouble(elctX);
        }
        return 0;
    }

    public void setWaterX(String waterX) {
        this.waterX = waterX;
    }
    public double getWaterX() {
        if(waterX != null && !waterX.trim().isEmpty()) {
            return Double.parseDouble(waterX);
        }
        return 0;
    }
    public void setFoodX(String foodX) {
        this.foodX = foodX;
    }
    public double getFoodX() {
        if(foodX != null && !foodX.trim().isEmpty()) {
            return Double.parseDouble(foodX);
        }
        return 0;
    }

    public void setLoans(String loans) {
        this.loans = loans;
    }
    public double getLoans() {
        if(loans != null && !loans.trim().isEmpty()) {
            return Double.parseDouble(loans);
        }
        return 0;
    }

    public void setBankN(String bankN) {
        this.bankN = bankN;
    }
    public String getBankN() {
        return bankN;
    }

    public String getStypeX() {
        return stypeX;
    }

    public void setStypeX(String sCustTypex) {
        this.stypeX = sCustTypex;
    }
    public void setCcBnk(String ccBnk) {
        this.ccBnk = ccBnk;
    }
    public String getCcBnk() {
        return ccBnk;
    }

    public void setLimitCC(String limitCC) {
        this.limitCC = limitCC;
    }
    public double getLimitCC() {
        if(limitCC != null && !limitCC.trim().isEmpty()) {
            return Double.parseDouble(limitCC);
        }
        return 0;
    }
    public void setYearS(String yearS) {
        this.yearS = yearS;
    }
    public int getYearS() {
        if(yearS != null && !yearS.trim().isEmpty()) {
            return Integer.parseInt(yearS);
        }
        return 0;
    }
    public boolean isDataValid(){
        return isBankN() &&
                isCCBank();
    }
    private boolean isBankN(){
        if(!bankN.trim().isEmpty()){
            return  isTypeX();
        }
        return true;
    }
    private boolean isTypeX(){
        if(Integer.parseInt(stypeX) < 0){
            message = "Please select account type";
            return false;
        }
        return true;
    }
    private boolean isCCBank(){
        if(!ccBnk.trim().isEmpty()){
          return isAmount() && isYear();
        }
        return true;
    }
    private boolean isAmount(){
        if(limitCC.trim().isEmpty()){
            message = "Please enter Amount";
            return false;
        }
        return true;
    }
    private boolean isYear(){
        if(yearS.trim().isEmpty()){
            message = "Please enter Length of Use";
            return false;
        }
        return true;
    }


}
