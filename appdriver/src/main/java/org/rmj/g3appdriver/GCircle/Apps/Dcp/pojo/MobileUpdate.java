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

public class MobileUpdate {
    private String cReqstCde;
    private String sMobileNo;
    private String cPrimaryx = "0";
    private String sRemarksx;
    private String sClientID;

    private String message;

    public MobileUpdate() {
    }

    public void setReqstCde(String cReqstCde) {
        this.cReqstCde = cReqstCde;
    }

    public void setMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }

    public void setPrimaryx(String cPrimaryx) {
        this.cPrimaryx = cPrimaryx;
    }

    public void setRemarksx(String sRemarksx) {
        this.sRemarksx = sRemarksx;
    }

    public void setClientID(String sClientID) {
        this.sClientID = sClientID;
    }

    public String getMessage(){
        return message;
    }

    public String getReqstCode() {
        return cReqstCde;
    }

    public String getMobileNo() {
        return sMobileNo;
    }

    public String getPrimaryx() {
        return cPrimaryx;
    }

    public String getsRemarksx() {
        return sRemarksx;
    }

    public String getClientID() {
        return sClientID;
    }

    public boolean isDataValid(){
        return isRequestCodeValid() &&
                isMobileNoValid() &&
                isRemarksValid();
    }

    private boolean isRequestCodeValid(){
        if(cReqstCde == null){
            message = "Please select request code";
            return false;
        }
        return true;
    }

    private boolean isMobileNoValid(){
        if(sMobileNo.trim().isEmpty()){
            message = "Please enter mobile number";
            return false;
        }
        if(!sMobileNo.substring(0, 2).equalsIgnoreCase("09")){
            message = "Contact number must start with '09'";
            return false;
        }
        if(sMobileNo.length() != 11){
            message = "Please enter exact count of mobile no.";
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
