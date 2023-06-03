package org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.pojo;

public class CreditAppInfo {
    private final String rqstinfo,
                            reqstdxx,
                            miscinfo,
                            remarks1;

    public CreditAppInfo(String rqstinfo, String reqstdxx, String miscinfo, String remarks1) {
        this.rqstinfo = rqstinfo;
        this.reqstdxx = reqstdxx;
        this.miscinfo = miscinfo;
        this.remarks1 = remarks1;
    }

    public String getRqstinfo() {
        return rqstinfo;
    }

    public String getReqstdxx() {
        return reqstdxx;
    }

    public String getMiscinfo() {
        return miscinfo;
    }

    public String getRemarks1() {
        return remarks1;
    }
}
