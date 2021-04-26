/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Model;

public class FinanceInfoModel {

    private String sFRltnx;
    private String sFNamex;
    private String sFIncme;
    private String sFCntry;
    private String sFMoble;
    private String sFFcbkx;
    private String sFEmlx;

    private String message;

    public FinanceInfoModel() {
    }

    public String getMessage(){
        return message;
    }

    public String getFinancierRelation() {
        return sFRltnx;
    }

    public void setFinancierRelation(String sFRltnx) {
        this.sFRltnx = sFRltnx;
    }

    public String getFinancierName() {
        return sFNamex;
    }

    public void setFinancierName(String sFNamex) {
        this.sFNamex = sFNamex;
    }

    public long getRangeOfIncome() {
        if(sFIncme == null || sFIncme.isEmpty()){
            return 0;
        }
        return Long.parseLong(sFIncme.replace(",", ""));
    }

    public void setRangeOfIncome(String sFIncme) {
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

    public boolean isFinancierInfoValid(){
        return isFinancierRelationValid() &&
                isFinancierNameValid() &&
                isRangeOfIncomeValid() &&
                isCountryValid() &&
                isMobileNoValid();
    }

    private boolean isFinancierNameValid(){
        if(sFNamex.trim().isEmpty()){
            message = "Please enter financier name";
            return false;
        }
        return true;
    }
    private boolean isFinancierRelationValid(){
        if(Integer.parseInt(sFRltnx) < 0){
            message = "Please select financier relationship";
            return false;
        }
        return true;
    }

    private boolean isRangeOfIncomeValid(){
        if(sFIncme.trim().isEmpty()){
            message = "Please enter atleast estimated range of income";
            return false;
        }
        return true;
    }

    private boolean isCountryValid(){
        if(sFCntry == null || sFCntry.trim().isEmpty()){
            message = "Please select country";
            return false;
        }
        return true;
    }

    private boolean isMobileNoValid(){
        if(sFMoble.trim().isEmpty()){
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
}
