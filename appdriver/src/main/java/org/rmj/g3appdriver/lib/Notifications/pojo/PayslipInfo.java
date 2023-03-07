package org.rmj.g3appdriver.lib.Notifications.pojo;

public class PayslipInfo {

    private final String sPeriodxx;
    private final String sMessagex;
    private final String sLinkxxxx;
    private final String sDateRcvd;

    public PayslipInfo(String sPeriodxx, String sMessagex, String sLinkxxxx, String sDateRcvd) {
        this.sPeriodxx = sPeriodxx;
        this.sMessagex = sMessagex;
        this.sLinkxxxx = sLinkxxxx;
        this.sDateRcvd = sDateRcvd;
    }

    public String getsPeriodxx() {
        return sPeriodxx;
    }

    public String getsMessagex() {
        return sMessagex;
    }

    public String getsLinkxxxx() {
        return sLinkxxxx;
    }

    public String getsDateRcvd() {
        return sDateRcvd;
    }
}
