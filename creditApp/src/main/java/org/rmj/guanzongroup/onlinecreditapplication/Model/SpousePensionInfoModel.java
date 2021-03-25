package org.rmj.guanzongroup.onlinecreditapplication.Model;

import org.rmj.g3appdriver.GRider.Etc.InputChecker;

public class SpousePensionInfoModel extends InputChecker {
    // TODO: Test first
    private String sMgs;
    private String sPensionSector;
    private String sPensionAmt;
    private String sRetirementYr;
    private String sOtherSrc;
    private String sOtherSrcIncx;

    public String getsMgs() {
        return sMgs;
    }

    public String getsPensionSector() {
        return sPensionSector;
    }

    public void setsPensionSector(String sPensionSector) {
        this.sPensionSector = sPensionSector;
    }

    public long getsPensionAmt() {
        return Long.parseLong(sPensionAmt.replace(",", ""));
    }

    public void setsPensionAmt(String sPensionAmt) {
        this.sPensionAmt = sPensionAmt;
    }

    public int getsRetirementYr() {
        return Integer.parseInt(sRetirementYr);
    }

    public void setsRetirementYr(String sRetirementYr) {
        this.sRetirementYr = sRetirementYr;
    }

    public String getsOtherSrc() {
        return sOtherSrc;
    }

    public void setsOtherSrc(String sOtherSrc) {
        this.sOtherSrc = sOtherSrc;
    }

    public long getsOtherSrcIncx() {
        if(!sOtherSrc.isEmpty()) {
            return Long.parseLong(sOtherSrcIncx.replace(",", ""));
        } else {
            return 0;
        }
    }

    public void setsOtherSrcIncx(String sOtherSrcIncx) {
        this.sOtherSrcIncx = sOtherSrcIncx;
    }

    public boolean isPensionDataValid(){
        return isPensionSectorValid() &&
                isPensionAmtValid() &&
                isRetirementYrValid() &&
                isOtherSrcIncxValid();
    }

    private boolean isPensionSectorValid() {
        if(sPensionSector.isEmpty()) {
            sMgs = "Please select pension sector.";
            return false;
        }
        return true;
    }

    private boolean isPensionAmtValid() {
        if(isStringAllZero(sPensionAmt) || sPensionAmt.trim().isEmpty()) {
            sMgs = "Please enter an estimated amount of pension.";
            return false;
        }
        return true;
    }

    private boolean isRetirementYrValid() {
        if(sRetirementYr.trim().isEmpty()) {
            sMgs = "Please enter retirement year.";
            return false;
        }
        return true;
    }

    private boolean isOtherSrcIncxValid() {
        if(!sOtherSrc.trim().isEmpty()) {
            if(isStringAllZero(sOtherSrcIncx) || sOtherSrcIncx.trim().isEmpty()) {
                sMgs = "Please enter an estimated amount in source of income.";
                return false;
            }
        }
        return true;
    }

}
