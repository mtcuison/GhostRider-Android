package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

public class MobileNo {
    private String MobileNo = "";
    private String isPostPd = "";
    private int PostYear = 0;

    public MobileNo() {
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public void setIsPostPd(String isPostPd) {
        this.isPostPd = isPostPd;
    }

    public void setPostYear(int postYear) {
        PostYear = postYear;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getIsPostPd() {
        return isPostPd;
    }

    public int getPostYear() {
        return PostYear;
    }
}
