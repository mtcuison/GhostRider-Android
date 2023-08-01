package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

public class SpouseResidence {

    private String sTransNox = "";
    private String sLandMark = "";
    private String sHouseNox = "";
    private String sAddress1 = "";
    private String sAddress2 = "";
    private String sMuncplNm = "";
    private String sMuncplID = "";
    private String sBrgyName = "";
    private String sBrgyIDxx = "";

    private String message;

    public SpouseResidence() {
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

    public boolean isDataValid() {
        if(isLandmarkValid() || !sHouseNox.trim().isEmpty() || !sAddress1.trim().isEmpty() || !sAddress2.trim().isEmpty() || isTownValid() || isBarangayValid()) {
            return isLandmarkValid() &&
//                    isProvinceValid() &&
                    isTownValid() &&
                    isBarangayValid();
        } else {
            return true;
        }
    }

    private boolean isLandmarkValid(){
        if(sLandMark == null || sLandMark.trim().isEmpty()){
            message = "Please provide Landmark.";
            return false;
        }
        return true;
    }

//    private boolean isProvinceValid(){
//        if(sProvncID == null || sProvncID.trim().isEmpty()){
//            message = "Please provide Province.";
//            return false;
//        }
//        return true;
//    }

    private boolean isTownValid(){
        if(sMuncplID == null || sMuncplID.trim().isEmpty()){
            message = "Please provide Town.";
            return false;
        }
        return true;
    }

    private boolean isBarangayValid(){
        if(sBrgyIDxx == null || sBrgyIDxx.trim().isEmpty()){
            message = "Please provide Barangay.";
            return false;
        }
        return true;
    }
}
