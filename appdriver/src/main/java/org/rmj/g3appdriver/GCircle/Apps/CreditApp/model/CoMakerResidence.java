package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

public class CoMakerResidence {

    private String sTransNox = "";
    private String sLandMark = "";
    private String sHouseNox = "";
    private String sAddress1 = "";
    private String sAddress2 = "";
    private String sProvncNm = "";
    private String sProvncID = "";
    private String sMuncplNm = "";
    private String sMuncplID = "";
    private String sBrgyName = "";
    private String sBrgyIDxx = "";
    private String sHouseOwn = "";
    private String sHouseHld = "";
    private String sHouseTpe = "";
    private String sHasGarge = "";
    private String sRelation = "";
    private double sLenghtSt = 0;
    private int cIsYearxx = 0;
    private double sExpenses = 0;

    private String message;

    public CoMakerResidence() {

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

    public String getLandMark() {
        return sLandMark.trim();
    }

    public void setLandMark(String sLandMark) {
        this.sLandMark = sLandMark;
    }

    public String getHouseNox() {
        return sHouseNox.trim();
    }

    public void setHouseNox(String sHouseNox) {
        this.sHouseNox = sHouseNox;
    }

    public String getAddress1() {
        return sAddress1.trim();
    }

    public void setAddress1(String sAddress1) {
        this.sAddress1 = sAddress1;
    }

    public String getAddress2() {
        return sAddress2.trim();
    }

    public void setAddress2(String sAddress2) {
        this.sAddress2 = sAddress2;
    }

    public String getProvinceNm() {
        return sProvncNm;
    }

    public void setProvinceNm(String sProvncNm) {
        this.sProvncNm = sProvncNm;
    }

    public String getProvinceID() {
        return sProvncID;
    }

    public void setProvinceID(String sProvncID) {
        this.sProvncID = sProvncID;
    }

    public String getMunicipalNm() {
        return sMuncplNm;
    }

    public void setMunicipalNm(String sMuncplNm) {
        this.sMuncplNm = sMuncplNm;
    }

    public String getMunicipalID() {
        return sMuncplID;
    }

    public void setMunicipalID(String sMuncplID) {
        this.sMuncplID = sMuncplID;
    }

    public String getBarangayName() {
        return sBrgyName;
    }

    public void setBarangayName(String sBrgyName) {
        this.sBrgyName = sBrgyName;
    }

    public String getBarangayID() {
        return sBrgyIDxx;
    }

    public void setBarangayID(String sBrgyIDxx) {
        this.sBrgyIDxx = sBrgyIDxx;
    }

    public String getHouseOwn() {
        return sHouseOwn;
    }

    public void setHouseOwn(String houseOwnx) {
        sHouseOwn = houseOwnx;
    }

    public String getHouseHold() {
        return sHouseHld;
    }

    public void setHouseHold(String sHouseHld) {
        this.sHouseHld = sHouseHld;
    }

    public String getHouseType() {
        return sHouseTpe;
    }

    public void setHouseType(String sHouseTpe) {
        this.sHouseTpe = sHouseTpe;
    }

    public String getHasGarage() {
        return sHasGarge;
    }

    public void setHasGarage(String sHasGarge) {
        this.sHasGarge = sHasGarge;
    }

    public String getOwnerRelation() {
        if(sHouseOwn.equalsIgnoreCase("1") || sHouseOwn.equalsIgnoreCase("2")) {
            return sRelation;
        }
        return "";
    }

    public void setOwnerRelation(String sRelation) {
        this.sRelation = sRelation;
    }

    public double getLenghtofStay() {
        try{
            if(cIsYearxx == 0) {
                double ldValue = sLenghtSt;
                return ldValue / 12;
            } else {
                return sLenghtSt;
            }
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public void setLenghtOfStay(double sLenghtSt) {
        this.sLenghtSt = sLenghtSt;
    }

    public void setIsYear(int cIsYearxx) {
        this.cIsYearxx = cIsYearxx;
    }

    public double getMonthlyExpenses() {
        if(sHouseOwn.equalsIgnoreCase("1") || sHouseOwn.equalsIgnoreCase("2")) {
            if (sExpenses == 0) {
                return 0;
            }
            return sExpenses;
        }
        return 0;
    }

    public void setMonthlyExpenses(double sExpenses) {
        this.sExpenses = sExpenses;
    }

    public boolean isDataValid(){
        return isLandMarkValid() &&
//                isProvinceValid() &&
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

//    private boolean isProvinceValid(){
//        if(sProvncID == null || sProvncID.trim().isEmpty()){
//            message = "Please enter province";
//            return false;
//        }
//        return true;
//    }

    private boolean isTownValid(){
        if(sMuncplID == null || sMuncplID.trim().isEmpty()){
            message = "Please enter Municipality address";
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
        if(sHouseOwn.trim().equalsIgnoreCase("1") || sHouseOwn.trim().equalsIgnoreCase("2")) {
            if (sLenghtSt == 0){
                message = "Please enter length of stay";
                return false;
            }
        }
        return true;
    }

    private boolean isMonthylyExpenseValid(){
        if(sHouseOwn.trim().equalsIgnoreCase("1") || sHouseOwn.trim().equalsIgnoreCase("2")) {
            if (sExpenses == 0) {
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
