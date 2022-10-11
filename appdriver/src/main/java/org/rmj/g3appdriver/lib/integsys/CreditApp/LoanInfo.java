package org.rmj.g3appdriver.lib.integsys.CreditApp;

public class LoanInfo {

    private String sAppTypex;
    private String sCustTypex;
    private String dTargetDte;
    private String sBranchCde;
    private String sBrandIDxx;
    private String sModelIDxx;
    private double sDownPaymt;
    private int sAccTermxx;
    private double sMonthlyAm;

    private String message;

    public LoanInfo() {

    }

    public String getMessage() {
        return message;
    }

    public String getsAppTypex() {
        return sAppTypex;
    }

    public void setsAppTypex(String sAppTypex) {
        this.sAppTypex = sAppTypex;
    }

    public String getsCustTypex() {
        return sCustTypex;
    }

    public void setsCustTypex(String sCustTypex) {
        this.sCustTypex = sCustTypex;
    }

    public String getdTargetDte() {
        return dTargetDte;
    }

    public void setdTargetDte(String dTargetDte) {
        this.dTargetDte = dTargetDte;
    }

    public String getsBranchCde() {
        return sBranchCde;
    }

    public void setsBranchCde(String sBranchCde) {
        this.sBranchCde = sBranchCde;
    }

    public String getsBrandIDxx() {
        return sBrandIDxx;
    }

    public void setsBrandIDxx(String sBrandIDxx) {
        this.sBrandIDxx = sBrandIDxx;
    }

    public String getsModelIDxx() {
        return sModelIDxx;
    }

    public void setsModelIDxx(String sModelIDxx) {
        this.sModelIDxx = sModelIDxx;
    }

    public double getsDownPaymt() {
        return sDownPaymt;
    }

    public void setsDownPaymt(double sDownPaymt) {
        this.sDownPaymt = sDownPaymt;
    }

    public int getsAccTermxx() {
        return sAccTermxx;
    }

    public void setsAccTermxx(int sAccTermxx) {
        this.sAccTermxx = sAccTermxx;
    }

    public double getsMonthlyAm() {
        return sMonthlyAm;
    }

    public void setsMonthlyAm(double sMonthlyAm) {
        this.sMonthlyAm = sMonthlyAm;
    }

    public boolean isDataValid(){
        if(Integer.parseInt(sCustTypex) < 0){
            message = "Please select customer type";
            return false;
        }
        if(Integer.parseInt(sAppTypex) < 0){
            message = "Please select loan unit";
            return false;
        }
        if(sBranchCde == null || sBranchCde.trim().isEmpty()){
            message = "Please select preferred branch";
            return false;
        }
        if(sBrandIDxx == null || sBrandIDxx.trim().isEmpty()){
            message = "Please select mc brand";
            return false;
        }
        if(sModelIDxx == null || sModelIDxx.trim().isEmpty()){
            message = "Please select mc model";
            return false;
        }
        if(sDownPaymt == 0){
            message = "Please enter preferred downpayment";
            return false;
        }
        if(sAccTermxx == 0){
            message = "Please select installment term";
            return false;
        }
        return true;
    }
}
