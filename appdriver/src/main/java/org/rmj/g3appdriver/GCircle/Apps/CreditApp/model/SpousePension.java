package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

public class SpousePension {

    private String TransNox = "";
    private String sSectorx = "";
    private long sRangexx = 0;
    private String sRtrYear = "";
    private String sNtrIncm = "";
    private long sRngIncm = 0;

    private String message;

    public SpousePension() {
    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getPensionSector() {
        return sSectorx;
    }

    public String getMessage() {
        return message;
    }

    public void setPensionSector(String sSectorx) {
        this.sSectorx = sSectorx;
    }

    public long getPensionIncomeRange() {
        return sRangexx;
    }

    public void setPensionIncomeRange(long sRangexx) {
        this.sRangexx = sRangexx;
    }

    public int getRetirementYear() {
        if (!sRtrYear.trim().isEmpty()) {
            return Integer.parseInt(sRtrYear);
        }
        return 0;
    }

    public void setRetirementYear(String sRtrYear) {
        this.sRtrYear = sRtrYear;
    }

    public String getNatureOfIncome() {
        return sNtrIncm;
    }

    public void setNatureOfIncome(String sNtrIncm) {
        this.sNtrIncm = sNtrIncm;
    }

    public long getRangeOfIncome() {
        return sRngIncm;
    }

    public void setRangeOfIncom(long sRngIncm) {
        this.sRngIncm = sRngIncm;
    }

    public boolean isDataValid() {
        if(!isSectorValid() && !isPensionIncomeValid()){
            return true;
        }
        return isSectorValid() &&
                isPensionIncomeValid() &&
                isRetirementYearValid() &&
                isOtherIncomeValid();
    }

    private boolean isSectorValid() {
        if (sSectorx.trim().isEmpty() || sSectorx.equalsIgnoreCase("")) {
            message = "Please select sector";
            return false;
        }
        return true;
    }

    private boolean isPensionIncomeValid() {
        if (sRangexx == 0 ) {
            message = "Please enter at least estimated income";
            return false;
        }
        return true;
    }

    private boolean isRetirementYearValid() {
        if (sRtrYear.trim().isEmpty() || sRtrYear.equalsIgnoreCase("") ) {
            message = "Please enter retirement year";
            return false;
        }
        return true;
    }

    private boolean isOtherIncomeValid() {
        if (!sNtrIncm.trim().isEmpty() || !sNtrIncm.equalsIgnoreCase("")) {
            if (sRngIncm == 0 ) {
                message = "Please enter at least estimated amount in other source of income";
                return false;
            }
        }
        return true;
    }

}
