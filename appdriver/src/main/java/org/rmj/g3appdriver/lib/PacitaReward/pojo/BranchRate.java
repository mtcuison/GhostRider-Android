package org.rmj.g3appdriver.lib.PacitaReward.pojo;

public class BranchRate {
    private final String sRateIDxx;
    private final String sRateName;
    private final String cPasRatex;
    private final String sCommentx;

    public BranchRate(String sRateIDxx, String sRateName, String cPasRatex, String sCommentx) {
        this.sRateIDxx = sRateIDxx;
        this.sRateName = sRateName;
        this.cPasRatex = cPasRatex;
        this.sCommentx = sCommentx;
    }

    public String getsRateIDxx() {
        return sRateIDxx;
    }

    public String getsRateName() {
        return sRateName;
    }

    public String getcPasRatex() {
        return cPasRatex;
    }

    public String getsCommentx() {
        return sCommentx;
    }
}
