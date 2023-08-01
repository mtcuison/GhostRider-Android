package org.rmj.g3appdriver.GCircle.Apps.PetManager.pojo;

public class LeaveApprovalInfo {
    private String TransNox = "";
    private String AppldFrx = "";
    private String AppldTox = "";
    private int WithPayx = 0;
    private int WithOPay = 0;
    private String Approved = "";
    private String Approvex = "";
    private String TranStat = "";

    private String message;

    public LeaveApprovalInfo(){

    }

    public String getMessage(){
        return message;
    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getAppldFrx() {
        return AppldFrx;
    }

    public void setAppldFrx(String appldFrx) {
        AppldFrx = appldFrx;
    }

    public String getAppldTox() {
        return AppldTox;
    }

    public void setAppldTox(String appldTox) {
        AppldTox = appldTox;
    }

    public int getWithPayx() {
        return WithPayx;
    }

    public void setWithPayx(int withPayx) {
        WithPayx = withPayx;
    }

    public int getWithOPay() {
        return WithOPay;
    }

    public void setWithOPay(int withOPay) {
        WithOPay = withOPay;
    }

    public String getApprovex() {
        return Approvex;
    }

    public void setApprovex(String approvex) {
        Approvex = approvex;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String approved) {


        Approved = approved;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public boolean isDataValid(){
        if(TransNox.isEmpty()){
            message = "No leave to approve or cancel";
            return false;
        } else if (WithPayx < 0){
            message = "Negative numbers are not allowed";
            return false;
        } else if(WithOPay < 0){
            message = "Negative numbers are not allowed";
            return false;
        }
        return true;
    }
}
