/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/19/21 1:23 PM
 * project file last modified : 6/19/21 1:23 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;

public class LeaveApplication {
    private String leaveType = "";
    private String dateFromx = "";
    private String dateThrux = "";
    private int noOfDaysx = 0;
    private String Remarksxx = "";

    private String message = "";

    public LeaveApplication() {
    }

    public String getMessage() {
        return message;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getDateFromx() {
        return FormatUIText.toSqlValue(dateFromx);
    }

    public void setDateFromx(String dateFromx) {
        this.dateFromx = dateFromx;
    }

    public String getDateThrux() {
        return FormatUIText.toSqlValue(dateThrux);
    }

    public void setDateThrux(String dateThrux) {
        this.dateThrux = dateThrux;
    }

    public int getNoOfDaysx() {
        return noOfDaysx;
    }

    public void setNoOfDaysx(int noOfDaysx) {
        this.noOfDaysx = noOfDaysx;
    }

    public String getRemarksxx() {
        return Remarksxx;
    }

    public void setRemarksxx(String remarksxx) {
        Remarksxx = remarksxx;
    }

    public boolean isDataValid(){
        return isLeaveTypeValid() && isDateFromValid() && isDateThruValid() && isRemarksValid();
    }

    private boolean isDateFromValid(){
        if(dateFromx.isEmpty()){
            message = "Please select starting date of leave";
            return false;
        }
        return true;
    }

    private boolean isDateThruValid(){
        if(dateThrux.isEmpty()){
            message = "Please select ending date of leave";
            return false;
        }
        return true;
    }

    private boolean isLeaveTypeValid(){
        if(leaveType.isEmpty()){
            message = "Please select type of leave";
            return false;
        }
        return true;
    }

    private boolean isRemarksValid(){
        if(Remarksxx.isEmpty()){
            message = "Please provide your purpose on remarks area";
            return false;
        }
        return true;
    }
}
