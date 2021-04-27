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

public class McModelInfo {

    private String sApplType;
    private String sCustmrTp;
    private String sBranchCd;
    private String sBrandCde;
    private String sModelCde;
    private String sDownPymt;
    private String sInsTermx;
    private String sMnthAmrt;

    private String message;

    public McModelInfo() {
    }

    public String getMessage() {
        return message;
    }

    public String getsApplType() {
        return sApplType;
    }

    public void setsApplType(String sApplType) {
        this.sApplType = sApplType;
    }

    public String getsCustmrTp() {
        return sCustmrTp;
    }

    public void setsCustmrTp(String sCustmrTp) {
        this.sCustmrTp = sCustmrTp;
    }

    public String getsBranchCd() {
        return sBranchCd;
    }

    public void setsBranchCd(String sBranchCd) {
        this.sBranchCd = sBranchCd;
    }

    public String getsBrandCde() {
        return sBrandCde;
    }

    public void setsBrandCde(String sBrandCde) {
        this.sBrandCde = sBrandCde;
    }

    public String getsModelCde() {
        return sModelCde;
    }

    public void setsModelCde(String sModelCde) {
        this.sModelCde = sModelCde;
    }

    public String getsDownPymt() {
        return sDownPymt;
    }

    public void setsDownPymt(String sDownPymt) {
        this.sDownPymt = sDownPymt;
    }

    public String getsInsTermx() {
        return sInsTermx;
    }

    public void setsInsTermx(String sInsTermx) {
        this.sInsTermx = sInsTermx;
    }

    public String getsMnthAmrt() {
        return sMnthAmrt;
    }

    public void setsMnthAmrt(String sMnthAmrt) {
        this.sMnthAmrt = sMnthAmrt;
    }

    private boolean isBranchValid(){
        if(sBranchCd.trim().isEmpty()){
            message = "Please select Branch";
            return false;
        }
        return true;
    }

    private boolean isBrandValid(){
        if(sBranchCd.trim().isEmpty()){
            message = "Please select motorcycle brand";
            return false;
        }
        return true;
    }

    private boolean isModelValid(){
        if(sModelCde.trim().isEmpty()){
            message = "Please select motorcycle brand";
            return false;
        }
        return true;
    }

    private boolean isDownpaymentValid(){
        if(sDownPymt.trim().isEmpty()){
            message = "Please enter downpayment";
            return false;
        }
        return true;
    }

    private boolean isTermValid(){
        if(sInsTermx.trim().isEmpty()){
            message = "Please select installment term";
            return false;
        }
        return true;
    }
}
