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

public class SpouseSelfEmployedInfoModel extends InputChecker {

    private String sMsg;

    private String sBizIndustry;
    private String sBizName;
    private String sBizAddress;
    private String sProvId;
    private String sTownId;
    private String sBizType;
    private String sBizSize;
    private String sBizYrs;
    private String sMonthOrYear;
    private String sGrossMonthly;
    private String sMonthlyExps;

    public String getsMsg() {
        return sMsg;
    }

    public String getsBizIndustry() {
        return sBizIndustry;
    }

    public void setsBizIndustry(String sBizIndustry) {
        this.sBizIndustry = sBizIndustry;
    }

    public String getsBizName() {
        return sBizName;
    }

    public void setsBizName(String sBizName) {
        this.sBizName = sBizName;
    }

    public String getsBizAddress() {
        return sBizAddress;
    }

    public void setsBizAddress(String sBizAddress) {
        this.sBizAddress = sBizAddress;
    }

    public String getsProvId() {
        return sProvId;
    }

    public void setsProvId(String sProvId) {
        this.sProvId = sProvId;
    }

    public String getsTownId() {
        return sTownId;
    }

    public void setsTownId(String sTownId) {
        this.sTownId = sTownId;
    }

    public String getsBizType() {
        return sBizType;
    }

    public void setsBizType(String sBizType) {
        this.sBizType = sBizType;
    }

    public String getsBizSize() {
        return sBizSize;
    }

    public void setsBizSize(String sBizSize) {
        this.sBizSize = sBizSize;
    }

    public double getsBizYrs() {
        try{
            if(sMonthOrYear == null) {
                return 0.0;
            }

            if(Integer.parseInt(sMonthOrYear) == 0) {
                double ldValue = Double.parseDouble(sBizYrs);
                return ldValue / 12;
            } else {
                return Double.parseDouble(sBizYrs);
            }
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public void setsBizYrs(String sBizYrs) {
        this.sBizYrs = sBizYrs;
    }

    public String getsMonthOrYear() {
        return sMonthOrYear;
    }

    public void setsMonthOrYear(String sMonthOrYear) {
        this.sMonthOrYear = sMonthOrYear;
    }

    public long getsGrossMonthly() {
        if(!sGrossMonthly.equalsIgnoreCase("")){
            return Long.parseLong(sGrossMonthly.replace(",", ""));
        }
        return 0;
    }

    public void setsGrossMonthly(String sGrossMonthly) {
        this.sGrossMonthly = sGrossMonthly;
    }

    public long getsMonthlyExps() {
        if(!sMonthlyExps.equalsIgnoreCase("")) {
            return Long.parseLong(sMonthlyExps.replace(",", ""));
        }
        return 0;
    }

    public void setsMonthlyExps(String sMonthlyExps) {
        this.sMonthlyExps = sMonthlyExps;
    }

    public boolean isSpouseInfoValid() {
        if(isBizIndustryValid() ||
                isBizNameValid() ||
                isProvIdValid() ||
                isTownIdValid() ||
                isBizTypeValid() ||
                isBizSizeValid() ||
                isMonthOrYearValid() ||
                isBizYrsValid() ||
                isGrossMonthlyValid() ||
                isMonthlyExpsValid()) {

            return isBizIndustryValid() &&
                    isBizNameValid() &&
                    isProvIdValid() &&
                    isTownIdValid() &&
                    isBizTypeValid() &&
                    isBizSizeValid() &&
                    isMonthOrYearValid() &&
                    isBizYrsValid() &&
                    isGrossMonthlyValid() &&
                    isMonthlyExpsValid();

        } else {
            return true;
        }
    }

    private boolean isBizIndustryValid() {
        if(sBizIndustry == null || sBizIndustry.isEmpty()) {
            sMsg = "Please select business industry.";
            return false;
        }
        return true;
    }

    private boolean isBizNameValid() {
        if(sBizName.trim().isEmpty()) {
            sMsg = "Please enter business name.";
            return false;
        }
        return true;
    }

    private boolean isProvIdValid() {
        if(sProvId == null || sProvId.equalsIgnoreCase("")) {
            sMsg = "Please enter Province.";
            return false;
        }
        return true;
    }

    private boolean isTownIdValid() {
        if(sTownId == null || sTownId.equalsIgnoreCase("")) {
            sMsg = "Please enter Town";
            return false;
        }
        return true;
    }

    private boolean isBizTypeValid() {
        if(sBizType == null || sBizType.isEmpty()) {
            sMsg = "Please select type of business";
            return false;
        }
        return true;
    }

    private boolean isBizSizeValid() {
        if(sBizSize == null || sBizSize.isEmpty()) {
            sMsg = "Please select size of business";
            return false;
        }
        return true;
    }

    private boolean isMonthOrYearValid() {
        if(sMonthOrYear == null || sMonthOrYear.equalsIgnoreCase("")) {
            sMsg = "Please select duration of business";
            return false;
        }
        return true;
    }

    private boolean isBizYrsValid() {
        if(sBizYrs == null || isStringAllZero(sBizYrs) || sBizYrs.trim().isEmpty()) {
            if(sMonthOrYear == null) { return false; }
            String lsMosYr = (sMonthOrYear.equalsIgnoreCase("0")) ? "Month" : "Year";
            sMsg = "Please enter " + lsMosYr + ".";
            return false;
        }
        return true;
    }

    private boolean isGrossMonthlyValid() {
        if(isStringAllZero(sGrossMonthly)|| sGrossMonthly.trim().isEmpty()) {
            sMsg = "Please enter estimated monthly earnings.";
            return false;
        }
        return true;
    }

    private boolean isMonthlyExpsValid() {
        if(isStringAllZero(sMonthlyExps) || sMonthlyExps.trim().isEmpty()) {
            sMsg = "Please enter estimated monthly expenses.";
            return false;
        }
        return true;
    }

}
