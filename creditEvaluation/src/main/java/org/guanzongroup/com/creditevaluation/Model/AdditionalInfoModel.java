/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 3/12/22, 2:15 PM
 * project file last modified : 3/12/22, 2:15 PM
 */

package org.guanzongroup.com.creditevaluation.Model;

public class AdditionalInfoModel {

    public String HasRecrd;
    public String RemRecrd;
    public String Neighbr1;
    public String Neighbr2;
    public String Neighbr3;


    public String AsstPersonnel;
    public String AsstPosition;
    public String MobileNo;

    private String cTranstat;
    private String sRemarks;
    private String cRcmdtnx1;
    private String sRcmdtnx1;
    public String message;
    public AdditionalInfoModel() {
    }

    public String getMessage() { return message; }

    public boolean isValidEvaluation(){
        return isPersonnel() &&
                isPosition() &&
                isHasRecord() &&
                isMobileNo() &&
                isNeighbr1() &&
                isNeighbr2() &&
                isNeighbr3();
    }
    public boolean isPersonnel(){
        if (this.AsstPersonnel == null || this.AsstPersonnel.trim().isEmpty()){
            message = "Please enter brgy. personnel's fullname.";
            return false;
        }else{
            message = "Brgy. personnel's fullname successfully saved.";
        }
        return true;
    }

    public boolean isPosition(){
        if (this.AsstPosition == null || this.AsstPosition.trim().isEmpty()){
            message = "Please enter personnel position.";
            return false;
        }else{
            message = "Personnel position successfully saved.";
        }
        return true;
    }

    //    NEIGHBOR 1
    public boolean isNeighbr1(){
        if (this.Neighbr1 == null || this.Neighbr1.trim().isEmpty()){
            message = "Please enter neighbor 1 fullname.";
            return false;
        }else{
            message = "Neighbor 1 fullname successfully saved.";
        }
        return true;
    }

    public boolean isNeighbr2(){
        if (this.Neighbr2 == null || this.Neighbr2.trim().isEmpty()){
            message = "Please enter neighbor 2 fullname.";
            return false;
        }else{
            message = "Neighbor 2 fullname successfully saved.";
        }
        return true;
    }


    public boolean isNeighbr3(){
        if (this.Neighbr3 == null || this.Neighbr3.trim().isEmpty()){
            message = "Please enter neighbor 3 fullname.";
            return false;
        }else{
            message = "Neighbor 3 fullname successfully saved.";
        }
        return true;
    }

    public boolean isMobileNo(){
        if (MobileNo != null) {
            if(!MobileNo.substring(0, 2).equalsIgnoreCase("09")){
                message = "Contact number must start with '09'";
                return false;
            }else if(MobileNo.length() != 11){
                message = "Please enter primary contact info";
                return false;
            }else{
                message = "Contact number successfully saved.";
            }
        }else {
            message = "Please enter contact number.";
            return false;
        }
        return true;
    }

    public boolean isHasRecord(){
        if (this.HasRecrd == null || this.HasRecrd.trim().isEmpty()){
            message = "Please select applicant brgy. record.";
            return false;
        }else{
                message = "Barangay record successfully saved.";
        }
        if (this.HasRecrd.equalsIgnoreCase("1")){
            return isRemRecrd();
        }
        return true;
    }
    public boolean isRemRecrd(){
        if (this.RemRecrd == null || this.RemRecrd.trim().isEmpty()){
            message = "Please enter record remarks.";
            return false;
        }else{
            message = "Barangay record successfully saved.";
        }
        return true;
    }
    public boolean isValidApproval(){
        return isApproved() && isReason();
    }
    public boolean isReason(){
        if (this.sRemarks == null || this.sRemarks.trim().isEmpty()){
            message = "Please enter reason for approval/disapproval evaluation.";
            return false;
        }
        return true;
    }
    public boolean isApproved(){
        if (this.cTranstat == null || this.cTranstat.trim().isEmpty()){
            message = "Please select approval/disapproval status.";
            return false;
        }
        else if (this.sRemarks == null || this.sRemarks.trim().isEmpty()){
            message = "Please enter reason for approval/disapproval evaluation.";
            return false;
        }
        else{
            message = "Approval successfully saved.";
            return true;
        }
    }
    public String getHasRecrd() {
        return HasRecrd;
    }

    public void setHasRecrd(String hasRecrd) {
        HasRecrd = hasRecrd;
    }

    public String getRemRecrd() {
        return RemRecrd;
    }

    public void setRemRecrd(String remRecrd) {
        RemRecrd = remRecrd;
    }

    public String getNeighbr1() {
        return Neighbr1;
    }

    public void setNeighbr1(String neighbr1) {
        Neighbr1 = neighbr1;
    }


    public String getNeighbr2() {
        return Neighbr2;
    }

    public void setNeighbr2(String neighbr2) {
        Neighbr2 = neighbr2;
    }


    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getNeighbr3() {
        return Neighbr3;
    }

    public void setNeighbr3(String neighbr3) {
        Neighbr3 = neighbr3;
    }

    public String getAsstPersonnel() {
        return AsstPersonnel;
    }

    public void setAsstPersonnel(String asstPersonnel) {
        AsstPersonnel = asstPersonnel;
    }

    public String getAsstPosition() {
        return AsstPosition;
    }

    public void setAsstPosition(String asstPosition) {
        AsstPosition = asstPosition;
    }

    public String getTranstat() {
        return cTranstat;
    }

    public void setTranstat(String cTranstat) {
        this.cTranstat = cTranstat;
    }

    public String getsRemarks() {
        return sRemarks;
    }

    public void setsRemarks(String sRemarks) {
        this.sRemarks = sRemarks;
    }
    public String getcRcmdtnx1() {
        return cRcmdtnx1;
    }

    public void setcRcmdtnx1(String cRcmdtnx1) {
        this.cRcmdtnx1 = cRcmdtnx1;
    }

    public String getsRcmdtnx1() {
        return sRcmdtnx1;
    }

    public void setsRcmdtnx1(String sRcmdtnx1) {
        this.sRcmdtnx1 = sRcmdtnx1;
    }
}
