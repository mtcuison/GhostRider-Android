package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

import android.util.Log;

public class Financier {

    private String TransNox = "";
    private String sFRltnx = "";
    private String sFNamex = "";
    private long sFIncme = 0;
    private String sFCntry = "";
    private String sCountry = "";
    private String sFMoble = "";
    private String sFFcbkx = "";
    private String sFEmlx = "";
    private String cMeanInfo = "";

    private String message;

    public Financier() {
    }

    public String getMessage(){
        return message;
    }

    public String getcMeanInfo() {
        return cMeanInfo;
    }

    public void setcMeanInfo(String cMeanInfo) {
        this.cMeanInfo = cMeanInfo;
    }


    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getFinancierRelation() {
        return sFRltnx;
    }

    public void setFinancierRelation(String sFRltnx) {
        this.sFRltnx = sFRltnx;
    }

    public String getFinancierName() {
        return sFNamex.trim();
    }

    public void setFinancierName(String sFNamex) {
        this.sFNamex = sFNamex;
    }

    public long getRangeOfIncome() {
        if(sFIncme == 0){
            return 0;
        }
        return sFIncme;
    }

    public void setRangeOfIncome(long sFIncme) {
        this.sFIncme = sFIncme;
    }

    public String getCountryName() {
        return sFCntry;
    }

    public void setCountryName(String sFCntry) {
        this.sFCntry = sFCntry;
    }

    public String getMobileNo() {
        return sFMoble;
    }

    public void setMobileNo(String sFMoble) {
        this.sFMoble = sFMoble;
    }

    public String getFacebook() {
        return sFFcbkx;
    }

    public void setFacebook(String sFFcbkx) {
        this.sFFcbkx = sFFcbkx;
    }

    public String getEmail() {
        return sFEmlx;
    }

    public void setEmail(String sFEmlx) {
        this.sFEmlx = sFEmlx;
    }

    public boolean isPrimary(){
        Log.e("means = ", cMeanInfo);
        if (!cMeanInfo.equalsIgnoreCase("2")){
            return false;
        }
        return true;

    }

    public boolean isDataValid(){
        return isFinancierRelationValid() &&
                isFinancierNameValid() &&
                isRangeOfIncomeValid() &&
                isCountryValid() &&
                isMobileNoValid();
    }

    private boolean isFinancierNameValid(){
        if(sFNamex.trim().isEmpty() || sFNamex.trim().equalsIgnoreCase("")){
            message = "Please enter financier name";
            return false;
        }
        return true;
    }
    private boolean isFinancierRelationValid(){
        if(sFRltnx.trim().isEmpty() || sFRltnx.trim().equalsIgnoreCase("") ){
            message = "Please select financier relationship";
            return false;
        }
        return true;
    }

    private boolean isRangeOfIncomeValid(){
        if(sFIncme == 0){
            message = "Please enter atleast estimated range of income";
            return false;
        }
        return true;
    }

    private boolean isCountryValid(){
        if(sCountry == null || sCountry.trim().isEmpty()){
            message = "Please select country";
            return false;
        }
        return true;
    }

    private boolean isMobileNoValid(){
        if(sFMoble.trim().isEmpty() || sFMoble.trim().equalsIgnoreCase("")){
            message = "Please enter financier mobile no";
            return false;
        }
        if(sFMoble.length()!=11){
            message = "Please enter valid mobile no";
            return false;
        }
        if(!sFMoble.substring(0, 2).equalsIgnoreCase("09")){
            message = "Mobile No must start with '09'";
            return false;
        }
        return true;
    }

    public String getCountry() {
        return sCountry;
    }

    public void setCountry(String sCountry) {
        this.sCountry = sCountry;
    }
}
