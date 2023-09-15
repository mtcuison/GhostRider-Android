package org.rmj.g3appdriver.GCircle.Apps.PetManager.pojo;


import org.rmj.g3appdriver.etc.FormatUIText;

public class LeaveApplication {
    private String EmploName = "";
    private String BranchNme = "";
    private String leaveType = "";
    private String dateFromx = "";
    private String dateThrux = "";
    private int noOfDaysx = 0;
    private String Remarksxx = "";
    private int noOfHours = 0;

    private String message = "";

    public LeaveApplication() {
    }

    public String getMessage() {
        return message;
    }

    public String getEmploName() {
        return EmploName;
    }

    public void setEmploName(String emploName) {
        EmploName = emploName;
    }

    public String getBranchNme() {
        return BranchNme;
    }

    public void setBranchNme(String branchNme) {
        BranchNme = branchNme;
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
        return Remarksxx.trim();
    }

    public void setRemarksxx(String remarksxx) {
        Remarksxx = remarksxx;
    }

    public double getNoOfHours(){
        noOfHours = noOfDaysx * 8;
        return noOfHours;
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
