/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

public class CIBarangayRecordInfoModel {

    private String HasOther;
    private String HasRecrd;
    private String RemRecrd;

    private String Neighbr1;
    private String Address1;
    private String ReltnCD1;
    private String MobileN1;
    private String FeedBck1;
    private String FBRemrk1;

    private String Neighbr2;
    private String Address2;
    private String ReltnCD2;
    private String MobileN2;
    private String FeedBck2;
    private String FBRemrk2;

    private String Neighbr3;
    private String Address3;
    private String ReltnCD3;
    private String MobileN3;
    private String FeedBck3;
    private String FBRemrk3;

    private String message;
    public CIBarangayRecordInfoModel() {
    }

    public String getMessage() { return message; }
    //    NEIGHBOR 1
    public boolean isValidNeigbor1(){
        return isNeighbr1() &&
                isReltnCD1() &&
                isAddress1() &&
                isMobileN1() &&
                isFeedBck1() &&
                isFBRemrk1();
    }
    private boolean isNeighbr1(){
        if (this.Neighbr1 == null || this.Neighbr1.trim().isEmpty()){
            message = "Please enter neighbor 1 fullname.";
            return false;
        }
        return true;
    }
    private boolean isReltnCD1(){
        if (this.ReltnCD1 == null || this.ReltnCD1.trim().isEmpty()){
            message = "Please enter neighbor 1 relationship.";
            return false;
        }
        return true;
    }
    private boolean isMobileN1(){
        if (!MobileN1.trim().isEmpty()) {
            if(!MobileN1.substring(0, 2).equalsIgnoreCase("09")){
                message = "Contact number must start with '09'";
                return false;
            }
            if(MobileN1.length() != 11){
                message = "Please enter primary contact info";
                return false;
            }
            if(MobileN1.equalsIgnoreCase(MobileN2)
                    || MobileN1.equalsIgnoreCase(MobileN3)){
                message = "Contact numbers are duplicated";
                return false;
            }
        }else {
            message = "Please enter Neighbor 1 contact no.";
            return false;
        }
        return true;
    }
    private boolean isFeedBck1(){
        if (this.FeedBck1 == null || this.FeedBck1.trim().isEmpty()){
            message = "Please select neighbor 1 feedback.";
            return false;
        }
        return true;
    }
    private boolean isFBRemrk1(){
        if (this.FBRemrk1 == null || this.FBRemrk1.trim().isEmpty()){
            message = "Please enter neighbor 1 remarks.";
            return false;
        }
        return true;
    }

    private boolean isAddress1(){
        if (this.Address1 == null || this.Address1.trim().isEmpty()){
            message = "Please enter neighbor 1 address.";
            return false;
        }
        return true;
    }

    //    NEIGHBOR 2
    public boolean isValidNeigbor2(){
        return isNeighbr2() &&
                isReltnCD2() &&
                isAddress2() &&
                isMobileN2() &&
                isFeedBck2()&&
                isFBRemrk2();
    }
    private boolean isNeighbr2(){
        if (this.Neighbr2 == null || this.Neighbr2.trim().isEmpty()){
            message = "Please enter neighbor 2 fullname.";
            return false;
        }
        return true;
    }
    private boolean isReltnCD2(){
        if (this.ReltnCD2 == null || this.ReltnCD2.trim().isEmpty()){
            message = "Please enter neighbor 2 relationship.";
            return false;
        }
        return true;
    }
    private boolean isMobileN2(){
        if (!MobileN2.trim().isEmpty()) {
            if(!MobileN2.substring(0, 2).equalsIgnoreCase("09")){
                message = "Contact number must start with '09'";
                return false;
            }
            if(MobileN2.length() != 11){
                message = "Please enter primary contact info";
                return false;
            }
            if(MobileN2.equalsIgnoreCase(MobileN1)
                    || MobileN2.equalsIgnoreCase(MobileN3)){
                message = "Contact numbers are duplicated";
                return false;
            }
        }else {
            message = "Please enter Neighbor 2 contact no.";
            return false;
        }
        return true;
    }
    private boolean isFeedBck2(){
        if (this.FeedBck2 == null || this.FeedBck2.trim().isEmpty()){
            message = "Please select neighbor 2 feedback.";
            return false;
        }
        return true;
    }
    private boolean isFBRemrk2(){
        if (this.FBRemrk2 == null || this.FBRemrk2.trim().isEmpty()){
            message = "Please enter neighbor 2 remarks.";
            return false;
        }
        return true;
    }

    private boolean isAddress2(){
        if (this.Address2 == null || this.Address2.trim().isEmpty()){
            message = "Please enter neighbor 2 address.";
            return false;
        }
        return true;
    }



    //    NEIGHBOR 3
    public boolean isValidNeigbor3(){
        return isNeighbr3() &&
                isReltnCD3() &&
                isAddress3() &&
                isMobileN3() &&
                isFeedBck3() &&
                isFBRemrk3();
    }
    private boolean isNeighbr3(){
        if (this.Neighbr3 == null || this.Neighbr3.trim().isEmpty()){
            message = "Please enter neighbor 3 fullname.";
            return false;
        }
        return true;
    }
    private boolean isReltnCD3(){
        if (this.ReltnCD3 == null || this.ReltnCD3.trim().isEmpty()){
            message = "Please enter neighbor 3 relationship.";
            return false;
        }
        return true;
    }
    private boolean isMobileN3(){
        if (!MobileN3.trim().isEmpty()) {
            if(!MobileN3.substring(0, 2).equalsIgnoreCase("09")){
                message = "Contact number must start with '09'";
                return false;
            }
            if(MobileN3.length() != 11){
                message = "Please enter primary contact info";
                return false;
            }
            if(MobileN3.equalsIgnoreCase(MobileN1)
                    || MobileN3.equalsIgnoreCase(MobileN2)){
                message = "Contact numbers are duplicated";
                return false;
            }
        }else {
            message = "Please enter Neighbor 3 contact no.";
            return false;
        }
        return true;
    }
    private boolean isFeedBck3(){
        if (this.FeedBck3 == null || this.FeedBck3.trim().isEmpty()){
            message = "Please select neighbor 3 feedback.";
            return false;
        }
        return true;
    }
    private boolean isFBRemrk3(){
        if (this.FBRemrk3 == null || this.FBRemrk3.trim().isEmpty()){
            message = "Please enter neighbor 3 remarks.";
            return false;
        }
        return true;
    }
    private boolean isAddress3(){
        if (this.Address3 == null || this.Address3.trim().isEmpty()){
            message = "Please enter neighbor 3 address.";
            return false;
        }
        return true;
    }


    public boolean isValidNeighbor(){
        return isValidNeigbor1() &&
                isValidNeigbor2() &&
                isValidNeigbor3() &&
                isHasRecord();
    }
    public boolean isHasRecord(){
        if (this.HasRecrd == null || this.HasRecrd.trim().isEmpty()){
            message = "Please select applicant brgy. record.";
            return false;
        }
        if (this.HasRecrd.equalsIgnoreCase("1")){
            return isRemRecrd();
        }
        return true;
    }
    private boolean isRemRecrd(){
        if (this.RemRecrd == null || this.RemRecrd.trim().isEmpty()){
            message = "Please enter record remarks.";
            return false;
        }
        return true;
    }
    public String getHasOther() {
        return HasOther;
    }

    public void setHasOther(String hasOther) {
        HasOther = hasOther;
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

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getReltnCD1() {
        return ReltnCD1;
    }

    public void setReltnCD1(String reltnCD1) {
        ReltnCD1 = reltnCD1;
    }

    public String getMobileN1() {
        return MobileN1;
    }

    public void setMobileN1(String mobileN1) {
        MobileN1 = mobileN1;
    }

    public String getFeedBck1() {
        return FeedBck1;
    }

    public void setFeedBck1(String feedBck1) {
        FeedBck1 = feedBck1;
    }

    public String getFBRemrk1() {
        return FBRemrk1;
    }

    public void setFBRemrk1(String FBRemrk1) {
        this.FBRemrk1 = FBRemrk1;
    }

    public String getNeighbr2() {
        return Neighbr2;
    }

    public void setNeighbr2(String neighbr2) {
        Neighbr2 = neighbr2;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getReltnCD2() {
        return ReltnCD2;
    }

    public void setReltnCD2(String reltnCD2) {
        ReltnCD2 = reltnCD2;
    }

    public String getMobileN2() {
        return MobileN2;
    }

    public void setMobileN2(String mobileN2) {
        MobileN2 = mobileN2;
    }

    public String getFeedBck2() {
        return FeedBck2;
    }

    public void setFeedBck2(String feedBck2) {
        FeedBck2 = feedBck2;
    }

    public String getFBRemrk2() {
        return FBRemrk2;
    }

    public void setFBRemrk2(String FBRemrk2) {
        this.FBRemrk2 = FBRemrk2;
    }

    public String getNeighbr3() {
        return Neighbr3;
    }

    public void setNeighbr3(String neighbr3) {
        Neighbr3 = neighbr3;
    }

    public String getAddress3() {
        return Address3;
    }

    public void setAddress3(String address3) {
        Address3 = address3;
    }

    public String getReltnCD3() {
        return ReltnCD3;
    }

    public void setReltnCD3(String reltnCD3) {
        ReltnCD3 = reltnCD3;
    }

    public String getMobileN3() {
        return MobileN3;
    }

    public void setMobileN3(String mobileN3) {
        MobileN3 = mobileN3;
    }

    public String getFeedBck3() {
        return FeedBck3;
    }

    public void setFeedBck3(String feedBck3) {
        FeedBck3 = feedBck3;
    }

    public String getFBRemrk3() {
        return FBRemrk3;
    }

    public void setFBRemrk3(String FBRemrk3) {
        this.FBRemrk3 = FBRemrk3;
    }

}
