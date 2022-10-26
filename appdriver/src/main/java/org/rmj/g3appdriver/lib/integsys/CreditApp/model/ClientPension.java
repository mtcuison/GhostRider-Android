package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

public class ClientPension {

    private String TransNox = "";
    private String sSectorx = "";
    private String sRangexx = "";
    private String sRtrYear = "";
    private String sNtrIncm = "";
    private String sRngIncm = "";

    private String message;

    public ClientPension() {
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
        if(!sRangexx.trim().isEmpty()) {
            return Long.parseLong(sRangexx);
        }
        return 0;
    }

    public void setPensionIncomeRange(String sRangexx) {
        this.sRangexx = sRangexx;
    }

    public int getRetirementYear() {
        if(!sRtrYear.trim().isEmpty()){
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
        if(!sRngIncm.trim().isEmpty()) {
            return Long.parseLong(sRngIncm);
        }
        return 0;
    }

    public void setRangeOfIncom(String sRngIncm) {
        this.sRngIncm = sRngIncm;
    }

    public boolean isDataValid(){
        return isSectorValid() &&
                isPensionIncomeValid() &&
                isRetirementYearValid() &&
                isOtherIncomeValid();
    }

    private boolean isSectorValid(){
        if(sSectorx.equalsIgnoreCase("-1")){
            message = "Please select sector";
            return false;
        }
        return true;
    }

    private boolean isPensionIncomeValid(){
        if(sRangexx == null || sRangexx.trim().isEmpty()){
            message = "Please enter at least estimated income";
            return false;
        }
        return true;
    }

    private boolean isRetirementYearValid(){
        if(sRtrYear.trim().isEmpty()){
            message = "Please enter retirement year";
            return false;
        }
        return true;
    }

    private boolean isOtherIncomeValid(){
        if(!sNtrIncm.trim().isEmpty()){
            if(sRngIncm.trim().isEmpty()){
                message = "Please enter at least estimated amount in other source of income";
                return false;
            }
        }
        return true;
    }

}
