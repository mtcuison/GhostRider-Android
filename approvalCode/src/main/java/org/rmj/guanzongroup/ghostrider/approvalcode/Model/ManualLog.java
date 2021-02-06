package org.rmj.guanzongroup.ghostrider.approvalcode.Model;

public class ManualLog {
    private String sBranchCd;
    private String sReqDatex;
    private String psTmeInAM;
    private String psTmeInPM;
    private String psTmOutAM;
    private String psTmOutPM;
    private String psTmeInOT;
    private String psTmOutOT;
    private String psRemarks;
    private String psSysCode;

    private String message;

    public ManualLog() {
    }

    public String getMessage(){
        return message;
    }

    public String getBranchCd() {
        return sBranchCd;
    }

    public void setBranchCd(String sBranchCd) {
        this.sBranchCd = sBranchCd;
    }

    public String getReqDatex() {
        return sReqDatex;
    }

    public void setReqDatex(String sReqDatex) {
        this.sReqDatex = sReqDatex;
    }

    public void setTimeInAM(String psTmeInAM) {
        this.psTmeInAM = psTmeInAM;
    }

    public void setTimeInPM(String psTmeInPM) {
        this.psTmeInPM = psTmeInPM;
    }

    public void setTimeOutAM(String psTmOutAM) {
        this.psTmOutAM = psTmOutAM;
    }

    public void setTimeOutPM(String psTmOutPM) {
        this.psTmOutPM = psTmOutPM;
    }

    public void setTimeInOT(String psTmeInOT) {
        this.psTmeInOT = psTmeInOT;
    }

    public void setTimeOutOT(String psTmOutOT) {
        this.psTmOutOT = psTmOutOT;
    }

    public String getRemarks() {
        return psRemarks;
    }

    public void setRemarks(String psRemarks) {
        this.psRemarks = psRemarks;
    }

    public String getSysCode() {
        return psSysCode;
    }

    public void setSysCode(String psSysCode) {
        this.psSysCode = psSysCode;
    }

    public String getMiscInfo(){
        return psTmeInAM + psTmOutAM + psTmeInPM + psTmOutPM + psTmeInOT + psTmOutOT;
    }

    public boolean isDataValid(){
        return isBranchValid() && isDateValid() && isRemarksValid() && isMiscValid();
    }

    private boolean isBranchValid(){
        if(sBranchCd == null || psRemarks.isEmpty()){
            message = "Please select branch";
            return false;
        }
        return true;
    }

    private boolean isDateValid(){
        if(sReqDatex == null || psRemarks.isEmpty()){
            message = "Please provide requested date";
            return false;
        }
        return true;
    }

    private boolean isRemarksValid(){
        if(psRemarks == null || psRemarks.isEmpty()){
            message = "Please provide some remarks for approval";
            return false;
        }
        return true;
    }

    private boolean isMiscValid(){
        String[] laString = {
                psTmeInAM,
                psTmOutAM,
                psTmeInPM,
                psTmOutPM,
                psTmeInOT,
                psTmOutOT};
        for (String s : laString) {
            if (!s.isEmpty() || s.equalsIgnoreCase("1")) {
                return true;
            }
        }
        message = "Please select which log should request approval.";
        return false;
    }
}
