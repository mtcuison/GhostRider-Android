package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

public class LeaveApprovalInfo {

    private String TransNox = "";
    private String AppldFrx = "";
    private String AppldTox = "";
    private String WithPayx = "";
    private String WithOPay = "";
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

    public String getWithPayx() {
        return WithPayx;
    }

    public void setWithPayx(String withPayx) {
        WithPayx = withPayx;
    }

    public String getWithOPay() {
        return WithOPay;
    }

    public void setWithOPay(String withOPay) {
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
        } else if(WithPayx == null || WithPayx.isEmpty()){
            message = "With pay value is invalid.";
            return false;
        } else if(WithOPay == null || WithOPay.isEmpty()){
            message = "Without pay value is invalid.";
            return false;
        } else if (Integer.parseInt(WithPayx) < 0){
            message = "Negative numbers are not allowed";
            return false;
        } else if(Integer.parseInt(WithOPay) < 0){
            message = "Negative numbers are not allowed";
            return false;
        }
        return true;
    }
}
