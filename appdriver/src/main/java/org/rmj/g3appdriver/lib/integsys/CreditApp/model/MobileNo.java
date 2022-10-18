package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

public class MobileNo {
    private final String MobileNo;
    private final String isPostPd;
    private final int PostYear;

    public MobileNo(String mobileNo, String isPostPd, int postYear) {
        MobileNo = mobileNo;
        this.isPostPd = isPostPd;
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
