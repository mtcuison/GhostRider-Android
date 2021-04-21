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
                isMobileN1() &&
                isFeedBck1();
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
        if (this.MobileN1 == null || this.MobileN1.trim().isEmpty()){
            message = "Please enter neighbor 1 mobile no.";
            return false;
        }
        return true;
    }
    private boolean isFeedBck1(){
        if (FeedBck1 == null || FeedBck1.trim().isEmpty()){
            message = "Please select neighbor 1 feedback.";
            return false;
        }else {
            if(Integer.parseInt(FeedBck1) == 1){
                return isFBRemrk1();
            }
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

    //    NEIGHBOR 2
    public boolean isValidNeigbor2(){
        return isNeighbr2() &&
                isReltnCD2() &&
                isMobileN2() &&
                isFeedBck2();
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
        if (this.MobileN2 == null || this.MobileN2.trim().isEmpty()){
            message = "Please enter neighbor 2 mobile no.";
            return false;
        }
        return true;
    }
    private boolean isFeedBck2(){
        if (FeedBck2 == null || FeedBck2.trim().isEmpty()){
            message = "Please select neighbor 2 feedback.";
            return false;
        }
        if(this.FeedBck2.equalsIgnoreCase("1")){
            return isFBRemrk2();
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



    //    NEIGHBOR 3
    public boolean isValidNeigbor3(){
        return isNeighbr3() &&
                isReltnCD3() &&
                isMobileN3() &&
                isFeedBck3();
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
        if (this.MobileN3 == null || this.MobileN3.trim().isEmpty()){
            message = "Please enter neighbor 3 mobile no.";
            return false;
        }
        return true;
    }
    private boolean isFeedBck3(){
        if (this.FeedBck3 == null || this.FeedBck3.trim().isEmpty()){
            message = "Please select neighbor 3 feedback.";
            return false;
        }else {
            if(Integer.parseInt(this.FeedBck3) == 1){
                return isFBRemrk3();
            }
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
