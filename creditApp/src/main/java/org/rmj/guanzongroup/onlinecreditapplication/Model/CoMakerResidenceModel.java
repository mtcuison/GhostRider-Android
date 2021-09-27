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

import android.util.Log;

public class CoMakerResidenceModel {
    private static final String TAG = CoMakerResidenceModel.class.getSimpleName();
    private String sLandMark;
    private String sHouseNox;
    private String sAddress1;
    private String sAddress2;
    private String sProvncNm;
    private String sProvncID;
    private String sMuncplNm;
    private String sMuncplID;
    private String sBrgyName;
    private String sBrgyIDxx;
    private String sHouseOwn;
    private String sHouseHld;
    private String sHouseTpe;
    private String sHasGarge;
    private String sRelation;
    private String sLenghtSt;
    private String cIsYearxx;
    private String sExpenses;

    private String message;

    public CoMakerResidenceModel() {
    }

    public String getMessage() {
        return message;
    }

    public String getsLandMark() {
        return sLandMark;
    }

    public void setsLandMark(String sLandMark) {
        this.sLandMark = sLandMark;
    }

    public String getsHouseNox() {
        return sHouseNox;
    }

    public void setsHouseNox(String sHouseNox) {
        this.sHouseNox = sHouseNox;
    }

    public String getsAddress1() {
        return sAddress1;
    }

    public void setsAddress1(String sAddress1) {
        this.sAddress1 = sAddress1;
    }

    public String getsAddress2() {
        return sAddress2;
    }

    public void setsAddress2(String sAddress2) {
        this.sAddress2 = sAddress2;
    }

    public String getsProvncNm() {
        return sProvncNm;
    }

    public void setsProvncNm(String sProvncNm) {
        this.sProvncNm = sProvncNm;
    }

    public String getsProvncID() {
        return sProvncID;
    }

    public void setsProvncID(String sProvncID) {
        this.sProvncID = sProvncID;
    }

    public String getsMuncplNm() {
        return sMuncplNm;
    }

    public void setsMuncplNm(String sMuncplNm) {
        this.sMuncplNm = sMuncplNm;
    }

    public String getsMuncplID() {
        Log.e(TAG + "townIdGetter", sMuncplID);
        return sMuncplID;
    }

    public void setsMuncplID(String sMuncplID) {
        this.sMuncplID = sMuncplID;
        Log.e(TAG + "townIdSetter", this.sMuncplID);
    }

    public String getsBrgyName() {
        return sBrgyName;
    }

    public void setsBrgyName(String sBrgyName) {
        this.sBrgyName = sBrgyName;
    }

    public String getsBrgyIDxx() {
        return sBrgyIDxx;
    }

    public void setsBrgyIDxx(String sBrgyIDxx) {
        this.sBrgyIDxx = sBrgyIDxx;
    }

    public String getsHouseOwn() {
        return sHouseOwn;
    }

    public void setsHouseOwn(String sHouseOwn) {
        this.sHouseOwn = sHouseOwn;
    }

    public String getsHouseHld() {
        return sHouseHld;
    }

    public void setsHouseHld(String sHouseHld) {
        this.sHouseHld = sHouseHld;
    }

    public String getsHouseTpe() {
        return sHouseTpe;
    }

    public void setsHouseTpe(String sHouseTpe) {
        this.sHouseTpe = sHouseTpe;
    }

    public String getsHasGarge() {
        return sHasGarge;
    }

    public void setsHasGarge(String sHasGarge) {
        this.sHasGarge = sHasGarge;
    }

    public String getsRelation() {
        return sRelation;
    }

    public void setsRelation(String sRelation) {
        this.sRelation = sRelation;
    }

    public double getsLenghtSt() {
        try{
            if(Integer.parseInt(cIsYearxx) == 0) {
                double ldValue = Double.parseDouble(sLenghtSt);
                return ldValue / 12;
            } else {
                return Double.parseDouble(sLenghtSt);
            }
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public void setsLenghtSt(String sLenghtSt) {
        this.sLenghtSt = sLenghtSt;
    }

    public void setcIsYearxx(String cIsYearxx) {
        this.cIsYearxx = cIsYearxx;
    }

    public double getsExpenses() {
        if(sHouseOwn.equalsIgnoreCase("1") || sHouseOwn.equalsIgnoreCase("2")) {
            if (sExpenses == null || sExpenses.trim().isEmpty()) {
                return 0;
            }
            return Double.parseDouble(sExpenses);
        }
        return 0;
    }

    public void setsExpenses(String sExpenses) {
        this.sExpenses = sExpenses;
    }

    public boolean isDataValid(){
        return isLandMarkValid() &&
                isTownValid() &&
                isBarangayValid() &&
                isOwnershipValid() &&
                isGarageValid() &&
                isRelationValid() &&
                isLengthOfStayValid() &&
                isMonthylyExpenseValid() &&
                isHouseHoldValid() &&
                isHouseTypeValid();
    }

    private boolean isLandMarkValid(){
        if(sLandMark == null || sLandMark.trim().isEmpty()){
            message = "Please provide landmark";
            return false;
        }
        return true;
    }

    private boolean isProvinceValid(){
        if(sProvncID == null || sProvncID.trim().isEmpty()){
            message = "Please enter province";
            return false;
        }
        return true;
    }

    private boolean isTownValid(){
        if(sMuncplID == null || sMuncplID.trim().isEmpty()){
            message = "Please enter town";
            return false;
        }
        return true;
    }

    private boolean isBarangayValid(){
        if(sBrgyIDxx == null || sBrgyIDxx.trim().isEmpty()){
            message = "Please enter barangay";
            return false;
        }
        return true;
    }

    private boolean isOwnershipValid() {
        if (sHouseOwn == null || sHouseOwn.trim().isEmpty()) {
            message = "Please select house ownership";
            return false;
        }
        return true;
    }

    private boolean isGarageValid(){
        if(sHasGarge == null || sHasGarge.trim().isEmpty()){
            message = "Please select if customer has garage";
            return false;
        }
        return true;
    }

    private boolean isRelationValid(){
        if(sHouseOwn.trim().equalsIgnoreCase("2")){
            if(sRelation.trim().isEmpty()){
                message = "Please enter house owner relation";
                return false;
            }
        }
        return true;
    }

    private boolean isLengthOfStayValid(){
        if(sHouseOwn.trim().equalsIgnoreCase("1") || sHouseOwn.trim().equalsIgnoreCase("1")) {
            if (sLenghtSt.trim().isEmpty()){
                message = "Please enter length of stay";
                return false;
            }
        }
        return true;
    }

    private boolean isMonthylyExpenseValid(){
        if(sHouseOwn.trim().equalsIgnoreCase("1") || sHouseOwn.trim().equalsIgnoreCase("1")) {
            if (sExpenses.trim().isEmpty()) {
                message = "Please enter monthly rent expense";
                return false;
            }
        }
        return true;
    }

    private boolean isHouseHoldValid(){
        if(sHouseHld == null || sHouseHld.trim().isEmpty()){
            message = "Please select customer household.";
            return false;
        }
        return true;
    }

    private boolean isHouseTypeValid(){
        if(sHouseTpe == null || sHouseTpe.trim().isEmpty()){
            message = "Please select customer house type.";
            return false;
        }
        return true;
    }
}
