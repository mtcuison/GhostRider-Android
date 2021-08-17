package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

public class RequestLeaveObInfoModel {

    public String sTransNox;
    public String dTransact;
    public String EmployName;
    public String dDateFrom;
    public String dDateThru;
    private String sApproved;
    private String dApprove;
    private String dAppldFrx;
    private String dAppldTox;
    private String sRemarks;
    private String nWithPayx;
    private String nWithOPay;
    private String sTranStat;

    public RequestLeaveObInfoModel(){

    }


    public String getsTransNox() {
        return sTransNox;
    }

    public String getdTransact() {
        return dTransact;
    }

    public String getEmployName() {
        return EmployName;
    }

    public String getdDateFrom() {
        return dDateFrom;
    }

    public String getdDateThru() {
        return dDateThru;
    }

    public String getsApproved() {
        return sApproved;
    }

    public String getdApprove() {
        return dApprove;
    }

    public String getdAppldFrx() {
        return dAppldFrx;
    }

    public String getdAppldTox() {
        return dAppldTox;
    }

    public String getsRemarks() {
        return sRemarks;
    }
    public void setsTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public void setdTransact(String dTransact) {
        this.dTransact = dTransact;
    }

    public void setEmployName(String employName) {
        EmployName = employName;
    }

    public void setdDateFrom(String dDateFrom) {
        this.dDateFrom = dDateFrom;
    }

    public void setdDateThru(String dDateThru) {
        this.dDateThru = dDateThru;
    }

    public void setsApproved(String sApproved) {
        this.sApproved = sApproved;
    }

    public void setdApprove(String dApprove) {
        this.dApprove = dApprove;
    }

    public void setdAppldFrx(String dAppldFrx) {
        this.dAppldFrx = dAppldFrx;
    }

    public void setdAppldTox(String dAppldTox) {
        this.dAppldTox = dAppldTox;
    }

    public void setsRemarks(String sRemarks) {
        this.sRemarks = sRemarks;
    }

    public String getnWithPayx() {
        return nWithPayx;
    }

    public void setnWithPayx(String nWithPayx) {
        this.nWithPayx = nWithPayx;
    }

    public String getnWithOPay() {
        return nWithOPay;
    }

    public void setnWithOPay(String nWithOPay) {
        this.nWithOPay = nWithOPay;
    }

    public String getsTranStat() {
        return sTranStat;
    }

    public void setsTranStat(String sTranStat) {
        this.sTranStat = sTranStat;
    }


}
