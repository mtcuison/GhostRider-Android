package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

public class PaidTransactionModel {

    private String TransNo;
    private String EntryNo;
    private String RemCode;
    private String PrNoxxx;
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

    public String getTransNo() {
        return TransNo;
    }

    public void setTransNo(String transNo) {
        TransNo = transNo;
    }

    public String getEntryNo() {
        return EntryNo;
    }

    public void setEntryNo(String entryNo) {
        EntryNo = entryNo;
    }

    public void setRemarksCode(String Remarks){
        this.RemCode = Remarks;
    }

    public String getPrNoxxx() {
        return PrNoxxx;
    }

    public void setPrNoxxx(String prNoxxx) {
        PrNoxxx = prNoxxx;
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
        if(Dscount == null || Dscount.trim().isEmpty()){
            return "0";
        }
        return Dscount;
    }

    public void setDscount(String dscount) {
        Dscount = dscount;
    }

    public String getOthersx() {
        if(Othersx == null || Othersx.trim().isEmpty()){
            return "0";
        }
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
        return isPRNumberValid() && isAmountValid();
    }

    private boolean isPRNumberValid(){
        if(PrNoxxx.trim().isEmpty()){
            message = "Please enter P.R number";
            return false;
        }
        return true;
    }

    private boolean isAmountValid(){
        if(Amountx.trim().isEmpty()){
            message = "Please enter amount";
            return false;
        }
        return true;
    }

    public String getRemarksCode(){
        switch (RemCode){
            case "Paid":
                return "PAY";
            case "Promise to Pay":
                return "PTP";
            case "Customer Not Around":
                return "CNA";
            case "For Legal Action":
                return "FLA";
            case "Carnap":
                return "Car";
            case "Uncooperative":
                return "UNC";
            case "Missing Customer":
                return "MCs";
            case "Missing Unit":
                return "MUn";
            case "Missing Client and Unit":
                return "MCU";
            case "Loan Unit":
                return "LUn";
            case "Transferred/Assumed":
                return "TA";
            case "False Ownership":
                return "FO";
            case "Did Not Pay":
                return "DNP";
            case "Not Visited":
                return "NV";
            case "Others":
                return "OTH";
        }
        return "";
    }
}
