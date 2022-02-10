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

public class SelfEmployedInfoModel {

    private String sBussNtrx = "";
    private String sBussName = "";
    private String sBussAddx = "";
    private String sBussProv = "";
    private String sBussTown = "";
    private String sBussType = "";
    private String sBussSize = "";
    private String sLenghtSv = "";
    private String sLenghtSpinner = "";
    private String sMnthlyIn = "";
    private String sMnthlyEx = "";

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
        if(sLenghtSpinner.equalsIgnoreCase("0")){
            double ldValue = Double.parseDouble(sLenghtSv);
            return ldValue / 12;
        } else {
            return Double.parseDouble(sLenghtSv);
        }
    }

    public void setLengthOfService(String sLenghtSv) {
        this.sLenghtSv = sLenghtSv;
    }

    public void setLengthOfServiceSpinner(String sLenghtSv) {
        this.sLenghtSpinner = sLenghtSv;
    }
    public String getLengthOfServiceSpinner() {
        return this.sLenghtSpinner;
    }

    public long getMonthlyIncome() {
        return Long.parseLong(sMnthlyIn.replace(",", ""));
    }

    public void setMonthlyIncome(String sMnthlyIn) {
        this.sMnthlyIn = sMnthlyIn;
    }

    public long getMonthlyExpense() {
        return Long.parseLong(sMnthlyEx.replace(",", ""));
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
        if(!sBussNtrx.isEmpty()) {
            return true;
        } else {
            message = "Please select business nature";
            return false;
        }
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
        if(Integer.parseInt(sBussType) < 0){
            message = "Please select type of business";
            return false;
        }
        return true;
    }

    boolean isBusinessSizeValid(){
        if(Integer.parseInt(sBussSize) < 0){
            message = "Please select size of business";
            return false;
        }
        return true;
    }

    boolean isLenghtOfServiceValid(){
        if(sLenghtSv.trim().isEmpty()){
            message = "Please enter length of service";
            return false;
        }else {
            return isLenghtOfServiceSpinnerValid();
        }
    }
    boolean isLenghtOfServiceSpinnerValid(){
        if(Integer.parseInt(sLenghtSpinner) < 0){
            message = "Please enter length of service in Month/Year";
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