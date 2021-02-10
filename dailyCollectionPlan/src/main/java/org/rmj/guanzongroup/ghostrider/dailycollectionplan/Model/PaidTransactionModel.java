package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

public class PaidTransactionModel {

    private String Payment;
    private String Remarks;
    private String Amountx;
    private String Dscount;
    private String Othersx;
    private String TotAmnt;

    private String message;

    public PaidTransactionModel() {
    }

    public String getMessage(){
        return message;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getAmountx() {
        return Amountx;
    }

    public void setAmountx(String amountx) {
        Amountx = amountx;
    }

    public String getDscount() {
        return Dscount;
    }

    public void setDscount(String dscount) {
        Dscount = dscount;
    }

    public String getOthersx() {
        return Othersx;
    }

    public void setOthersx(String othersx) {
        Othersx = othersx;
    }

    public String getTotAmnt() {
        return TotAmnt;
    }

    public void setTotAmnt(String totAmnt) {
        TotAmnt = totAmnt;
    }

    public boolean isDataValid(){
        return isAmountValid();
    }

    private boolean isAmountValid(){
        if(Amountx.trim().isEmpty()){
            message = "Please enter amount";
            return false;
        }
        return true;
    }
}
