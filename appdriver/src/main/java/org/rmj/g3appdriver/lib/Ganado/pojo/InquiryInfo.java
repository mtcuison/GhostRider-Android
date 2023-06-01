package org.rmj.g3appdriver.lib.Ganado.pojo;

public class InquiryInfo {
    private String cGanadoTp = "";
    private String cPaymForm = "";
    private String sInvTypCd = "";
    private String sBrandIDx = "";
    private String sModelIDx = "";
    private String sColorIDx = "";
    private String sTermIDxx = "";
    private String dTargetxx = "";
    private String nDownPaym = "";


    private String nMonthAmr = "";
    private String sRelatnID = "";

    private String message;

    public InquiryInfo() {
    }

    public String getMessage() {
        return message;
    }

    public String getGanadoTp() {
        return cGanadoTp;
    }

    /**
     *
     * @param cGanadoTp
     *          0 = Automobile
     *          1 = Motorcycle
     *          2 = Mobile Phone
     */
    public void setGanadoTp(String cGanadoTp) {
        this.cGanadoTp = cGanadoTp;
    }

    public String getPaymForm() {
        return cPaymForm;
    }

    /**
     *
     * @param cPaymForm
     *          0 = Cash
     *          1 = Installment
     */
    public void setPaymForm(String cPaymForm) {
        this.cPaymForm = cPaymForm;
    }

    public String getInvTypCd() {
        return sInvTypCd;
    }

    public void setInvTypCd(String sInvTypCd) {
        this.sInvTypCd = sInvTypCd;
    }

    public String getBrandIDx() {
        return sBrandIDx;
    }

    public void setBrandIDx(String sBrandIDx) {
        this.sBrandIDx = sBrandIDx;
    }

    public String getModelIDx() {
        return sModelIDx;
    }

    public void setModelIDx(String sModelIDx) {
        this.sModelIDx = sModelIDx;
    }

    public String getColorIDx() {
        return sColorIDx;
    }

    public void setColorIDx(String sColorIDx) {
        this.sColorIDx = sColorIDx;
    }

    public String getTermIDxx() {
        return sTermIDxx;
    }

    public void setTermIDxx(String sTermIDxx) {
        this.sTermIDxx = sTermIDxx;
    }

    public String getTargetxx() {
        return dTargetxx;
    }

    public void setTargetxx(String dTargetxx) {
        this.dTargetxx = dTargetxx;
    }

    public String getDownPaym() {
        return nDownPaym;
    }

    public void setDownPaym(String nDownPaym) {
        this.nDownPaym = nDownPaym;
    }

    public String getnMonthAmr() {
        return nMonthAmr;
    }

    public void setMonthAmr(String nMonthAmr) {
        this.nMonthAmr = nMonthAmr;
    }
    public String getRelatnID() {
        return sRelatnID;
    }

    public void setRelatnID(String sRelatnID) {
        this.sRelatnID = sRelatnID;
    }

    public boolean isDataValid(){
        if(cGanadoTp.isEmpty()) {
            message = "Please select type of unit whether motorcycle, automobile or mobile phone";
            return false;
        }

        if(sBrandIDx.isEmpty()) {
            message = "Please select which motorcycle brand you prefer.";
            return false;
        }

        if(sModelIDx.isEmpty()) {
            message = "Please select which motorcycle unit you prefer.";
            return false;
        }

        if(sColorIDx.isEmpty()) {
            message = "Please select which color you prefer.";
            return false;
        }

        if(dTargetxx.isEmpty()){
            message = "Please select the target date for getting the unit.";
            return false;
        }

        if(cPaymForm.isEmpty()) {
            message = "Please select which type of payment you prefer. Cash or Installment";
            return false;
        }

        if(cPaymForm.equalsIgnoreCase("1")) {
            if (sTermIDxx.isEmpty()) {
                message = "Please select the desired installment term.";
                return false;
            }
            if (nDownPaym.isEmpty()) {
                message = "Please enter your downpayment.";
                return false;
            }
        }

        return true;
    }
}
