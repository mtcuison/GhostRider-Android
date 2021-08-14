/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/22/21 3:11 PM
 * project file last modified : 6/22/21 3:11 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

public class OBApplication {

    private String ObTypexx;
    private String DateFrom;
    private String DateThru;
    private String NoOfDays;
    private String Remarks;
    private String TimeINam;
    private String TimeOUTam;
    private String TimeINpm;
    private String TimeOUTpm;
    private String xEmployee;
    private String sBranchNm;
    private String sDeptName;
    private String sPositnNm;
    private String message;
    private String Destination;
    private String EmpID;

    public OBApplication(){

    }
    public String getObTypexx() {
        return ObTypexx;
    }

    public void setObTypexx(String obTypexx) {
        ObTypexx = obTypexx;
    }
    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String empID) {
        EmpID = empID;
    }
    public String getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(String dateFrom) {
        DateFrom = dateFrom;
    }

    public String getDateThru() {
        return DateThru;
    }

    public void setDateThru(String dateThru) {
        DateThru = dateThru;
    }

    public String getNoOfDays() {
        return NoOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        NoOfDays = noOfDays;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getTimeINam() {
        return TimeINam;
    }

    public void setTimeINam(String timeINam) {
        TimeINam = timeINam;
    }

    public String getTimeOUTam() {
        return TimeOUTam;
    }

    public void setTimeOUTam(String timeOUTam) {
        TimeOUTam = timeOUTam;
    }

    public String getTimeINpm() {
        return TimeINpm;
    }

    public void setTimeINpm(String timeINpm) {
        TimeINpm = timeINpm;
    }

    public String getTimeOUTpm() {
        return TimeOUTpm;
    }

    public void setTimeOUTpm(String timeOUTpm) {
        TimeOUTpm = timeOUTpm;
    }

    public String getxEmployee() {
        return xEmployee;
    }

    public void setxEmployee(String xEmployee) {
        this.xEmployee = xEmployee;
    }

    public String getsBranchNm() {
        return sBranchNm;
    }

    public void setsBranchNm(String sBranchNm) {
        this.sBranchNm = sBranchNm;
    }

    public String getsDeptName() {
        return sDeptName;
    }

    public void setsDeptName(String sDeptName) {
        this.sDeptName = sDeptName;
    }

    public String getsPositnNm() {
        return sPositnNm;
    }

    public void setsPositnNm(String sPositnNm) {
        this.sPositnNm = sPositnNm;
    }
    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }


    public String getMessage() {
        return message;
    }

    public boolean isOBLeaveValid(){
        return isObTypexx() &&
                isDateFrom() &&
                isDateFrom();
    }
    private boolean isObTypexx(){
        if(ObTypexx.trim().isEmpty()){
            message = "Please select leave type";
            return false;
        }
        return true;
    }
    private boolean isDateFrom(){
        if(DateFrom.trim().isEmpty()){
            message = "Please enter Date From";
            return false;
        }
        return true;
    }
    private boolean isDateThru(){
        if(DateThru.trim().isEmpty()){
            message = "Please enter Date Thru";
            return false;
        }
        return true;
    }

}
