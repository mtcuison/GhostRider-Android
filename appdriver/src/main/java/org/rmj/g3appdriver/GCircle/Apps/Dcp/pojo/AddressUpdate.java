/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo;

public class AddressUpdate {
    private String cReqstCDe;
    private String sClientID;
    private String cAddrssTp = "0";
    private String sHouseNox;
    private String sAddressx;
    private String sProvIDxx;
    private String sTownIDxx;
    private String sBrgyIDxx;
    private String cPrimaryx = "0";
    private String nLatitude;
    private String nLongitud;
    private String sRemarksx;

    private String message;

    public AddressUpdate(){

    }

    public String getMessage() {
        return message;
    }

    public String getClientID() {
        return sClientID;
    }

    public void setsClientID(String sClientID) {
        this.sClientID = sClientID;
    }

    public String getRequestCode() {
        return cReqstCDe;
    }

    public void setRequestCode(String cReqstCDe) {
        this.cReqstCDe = cReqstCDe;
    }

    public String getAddrssTp() {
        return cAddrssTp;
    }

    public void setcAddrssTp(String cAddrssTp) {
        this.cAddrssTp = cAddrssTp.trim();
    }

    public String getHouseNumber() {
        return sHouseNox.trim();
    }

    public void setHouseNumber(String sHouseNox) {
        this.sHouseNox = sHouseNox;
    }

    public String getAddress() {
        return sAddressx.trim();
    }

    public void setAddress(String sAddressx) {
        this.sAddressx = sAddressx;
    }

    public String getsProvIDxx() {
        return sProvIDxx;
    }

    public void setsProvIDxx(String sProvIDxx) {
        this.sProvIDxx = sProvIDxx;
    }

    public String getTownID() {
        return sTownIDxx;
    }

    public void setTownID(String sTownIDxx) {
        this.sTownIDxx = sTownIDxx;
    }

    public String getBarangayID() {
        return sBrgyIDxx;
    }

    public void setBarangayID(String sBrgyIDxx) {
        this.sBrgyIDxx = sBrgyIDxx;
    }

    public String getPrimaryStatus() {
        return cPrimaryx;
    }

    public void setPrimaryStatus(String cPrimaryx) {
        this.cPrimaryx = cPrimaryx;
    }

    public String getLatitude() {
        return nLatitude;
    }

    public void setLatitude(String nLatitude) {
        this.nLatitude = nLatitude;
    }

    public String getLongitude() {
        return nLongitud;
    }

    public void setLongitude(String nLongitud) {
        this.nLongitud = nLongitud;
    }

    public String getRemarks() {
        return sRemarksx.trim();
    }

    public void setsRemarksx(String sRemarksx) {
        this.sRemarksx = sRemarksx;
    }

    public boolean isDataValid() {
        return isRequestCodeValid() &&
                isAddressTPValid() &&
                isHouseNoxValid() &&
                isAddressValid() &&
                isTownValid() &&
                isBrgyValid() &&
                isRemarksValid();
    }

    private boolean isRequestCodeValid(){
        if(cReqstCDe == null){
            message = "Please select request code";
            return false;
        }
        return true;
    }

    private boolean isAddressTPValid() {
        if(cAddrssTp == null || cAddrssTp.equalsIgnoreCase("")) {
            message = "Please select Address Type";
            return false;
        }
        return true;
    }

    private boolean isHouseNoxValid() {
        if(sHouseNox.trim().isEmpty()) {
            message = "Please enter House No.";
            return false;
        }
        return true;
    }

    private boolean isAddressValid() {
        if(sAddressx.trim().isEmpty()) {
            message = "Please enter Street/Sitio/Lot No.";
            return false;
        }
        return true;
    }


    private boolean isTownValid(){
        if(sTownIDxx == null || sTownIDxx.equalsIgnoreCase("")){
            message = "Please enter town";
            return false;
        }
        return true;
    }

    private boolean isBrgyValid() {
        if(sBrgyIDxx == null || sBrgyIDxx.equalsIgnoreCase("")) {
            message = "Please enter barangay";
            return false;
        }
        return true;
    }

    private boolean isRemarksValid() {
        if(sRemarksx.trim().isEmpty()) {
            message = "Please enter remarks";
            return false;
        }
        return true;
    }
}
