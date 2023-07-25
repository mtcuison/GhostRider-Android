package org.rmj.g3appdriver.GCircle.Apps.GawadPacita.pojo;

public class BranchRate {
    private final Integer sRateIDxx;
    private final String sRateName;
    private final String cPasRatex;

    public BranchRate(Integer sRateIDxx, String sRateName, String cPasRatex) {
        this.sRateIDxx = sRateIDxx;
        this.sRateName = sRateName;
        this.cPasRatex = cPasRatex;
    }

    public Integer getsRateIDxx() {
        return sRateIDxx;
    }

    public String getsRateName() {
        return sRateName;
    }

    public String getcPasRatex() {
        return cPasRatex;
    }
}
