/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

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
        if(!sPensionAmt.equalsIgnoreCase("")) {
            return Long.parseLong(sPensionAmt.replace(",", ""));
        } else {
            return 0;
        }
    }

    public void setsPensionAmt(String sPensionAmt) {
        this.sPensionAmt = sPensionAmt;
    }

    public int getsRetirementYr() {
        if(!sRetirementYr.equalsIgnoreCase("")) {
            return Integer.parseInt(sRetirementYr);
        } else {
            return 0;
        }
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
            if(!sOtherSrcIncx.equalsIgnoreCase("")) {
                return Long.parseLong(sOtherSrcIncx.replace(",", ""));
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public void setsOtherSrcIncx(String sOtherSrcIncx) {
        this.sOtherSrcIncx = sOtherSrcIncx;
    }

    public boolean isPensionDataValid(){
        if(isPensionSectorValid() ||
                isPensionAmtValid() ||
                isRetirementYrValid()) {

            return isPensionSectorValid() &&
                    isPensionAmtValid() &&
                    isRetirementYrValid() &&
                    isOtherSrcIncxValid();
        } else {
            return true;
        }
    }

    private boolean isPensionSectorValid() {
        if(sPensionSector == null) {
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
        if(isStringAllZero(sRetirementYr) || sRetirementYr.trim().isEmpty()) {
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
