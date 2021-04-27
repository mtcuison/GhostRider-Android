/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Model;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PurchaseInfoModel {

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

    public PurchaseInfoModel() {
    }

    public String getMessage(){
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
        try {
            String lsSelectedDate = Objects.requireNonNull(dTargetDte);
            @SuppressLint("SimpleDateFormat") Date parseDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsSelectedDate);
            @SuppressLint("SimpleDateFormat") String lsDate = new SimpleDateFormat("yyyy-MM-dd").format(Objects.requireNonNull(parseDate));
            this.dTargetDte = lsDate;
        } catch (Exception e){
            e.printStackTrace();
        }
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
        return isApplicationTypeValid() &&
                isCustomerTypeValid() &&
                isBranchValid() &&
                isBrandValid() &&
                isModelValid() &&
                isDownpaymentValid() &&
                isTermValid();
    }

    private boolean isCustomerTypeValid(){
        if(Integer.parseInt(sCustTypex) < 0){
            message = "Please select customer type";
            return false;
        }
        return true;
    }
    private boolean isApplicationTypeValid(){
        if(Integer.parseInt(sAppTypex) < 0){
            message = "Please select loan unit";
            return false;
        }
        return true;
    }

    private boolean isBranchValid(){
        if(sBranchCde == null || sBranchCde.trim().isEmpty()){
            message = "Please select preferred branch";
            return false;
        }
        return true;
    }

    private boolean isBrandValid(){
        if(sBrandIDxx == null || sBrandIDxx.trim().isEmpty()){
            message = "Please select mc brand";
            return false;
        }
        return true;
    }

    private boolean isModelValid(){
        if(sModelIDxx == null || sModelIDxx.trim().isEmpty()){
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
