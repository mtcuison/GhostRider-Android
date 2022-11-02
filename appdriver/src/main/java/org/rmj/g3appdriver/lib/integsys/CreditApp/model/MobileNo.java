package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

public class MobileNo {
    private String MobileNo = "";
    private int isPostPd = 0;
    private int PostYear = 0;

    private String message;

    public MobileNo() {
    }

    public String getMessage() {
        return message;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public void setIsPostPd(int isPostPd) {
        this.isPostPd = isPostPd;
    }

    public void setPostYear(int postYear) {
        PostYear = postYear;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getIsPostPd() {
        return String.valueOf(isPostPd);
    }

    public int getPostYear() {
        return PostYear;
    }

    public boolean isDataValid(){
        if(MobileNo.trim().isEmpty()){
            message = "Please enter mobile no.";
            return false;
        }

        if(!MobileNo.substring(0, 2).equalsIgnoreCase("09")){
            message = "Contact number must start with '09'";
            return false;
        }
        if(MobileNo.length() != 11){
            message = "Please complete primary contact info";
            return false;
        }

        if(isPostPd == 1 && PostYear == 0){
            message = "Please enter postpaid plan year.";
            return false;
        }

        return true;
    }
}
