package org.rmj.guanzongroup.onlinecreditapplication.Model;

public class SpouseSelfEmployedInfoModel {

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

    public String getsBizYrs() {
        return sBizYrs;
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

    public String getsGrossMonthly() {
        return sGrossMonthly;
    }

    public void setsGrossMonthly(String sGrossMonthly) {
        this.sGrossMonthly = sGrossMonthly;
    }

    public String getsMonthlyExps() {
        return sMonthlyExps;
    }

    public void setsMonthlyExps(String sMonthlyExps) {
        this.sMonthlyExps = sMonthlyExps;
    }

    public void isDataValid() {

    }

    private boolean isBizIndustryValid() {
        if(sBizIndustry.isEmpty()) {
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
        if(sProvId.isEmpty()) {
            sMsg = "Please enter Province.";
            return false;
        }
        return true;
    }

    private boolean isTownIdValid() {
        if(sTownId.isEmpty()) {
            sMsg = "Please enter Town";
            return false;
        }
        return true;
    }

    private boolean isBizTypeValid() {
        if(sBizType.isEmpty()) {
            sMsg = "Please select type of business";
            return false;
        }
        return true;
    }

    private boolean isBizSizeValid() {
        if(sBizSize.isEmpty()) {
            sMsg = "Please select size of business";
            return false;
        }
        return true;
    }

    private String isMonthOrYear() {
        if(sMonthOrYear == "0") {
            return "Month";
        }
        return "Year";
    }

    private boolean isBizYrsValid() {
        if(isStringAllZero(sBizYrs) || sBizYrs.trim().isEmpty()) {
            sMsg = "Please enter " + isMonthOrYear() + ".";
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


    private boolean isStringAllZero(String checkStr) {
        char[] charSplit = checkStr.trim().toCharArray();
        for(char i : charSplit) {
            if(i != '0') {
                return false;
            }
        }
        return true;
    }



}
