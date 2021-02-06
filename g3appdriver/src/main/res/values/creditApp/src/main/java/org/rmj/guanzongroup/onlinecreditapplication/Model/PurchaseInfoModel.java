package org.rmj.guanzongroup.onlinecreditapplication.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PurchaseInfoModel {

    private String sCustTypex;
    private String sBranchCde;
    private String sBrandIDxx;
    private String sModelIDxx;
    private double sDownPaymt;
    private int sAccTermxx;
    private double sMonthlyAm;

    private String message;

    public PurchaseInfoModel() {
    }

    public String getMessage(){
        return message;
    }

    public String getsCustTypex() {
        return sCustTypex;
    }

    public void setsCustTypex(String sCustTypex) {
        this.sCustTypex = sCustTypex;
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

    public String getDateApplied(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
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

    public boolean isPurchaseInfoValid(){
        return isCustomerTypeValid() &&
                isBranchValid() &&
                isBrandValid() &&
                isModelValid() &&
                isDownpaymentValid() &&
                isTermValid();
    }

    private boolean isCustomerTypeValid(){
        if(sCustTypex.trim().isEmpty()){
            message = "Please select customer type";
            return false;
        }
        return true;
    }

    private boolean isBranchValid(){
        if(sBranchCde.trim().isEmpty()){
            message = "Please select preferred branch";
            return false;
        }
        return true;
    }

    private boolean isBrandValid(){
        if(sBrandIDxx.trim().isEmpty()){
            message = "Please select mc brand";
            return false;
        }
        return true;
    }

    private boolean isModelValid(){
        if(sModelIDxx.trim().isEmpty()){
            message = "Please select mc model";
            return false;
        }
        return true;
    }

    private boolean isDownpaymentValid(){
        if(sDownPaymt == 0){
            message = "Please enter preferred downpayment";
            return false;
        }
        return true;
    }

    private boolean isTermValid(){
        if(sAccTermxx == 0){
            message = "Please select installment term";
            return false;
        }
        return true;
    }
}
