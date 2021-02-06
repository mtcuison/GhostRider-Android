package org.rmj.guanzongroup.ghostrider.approvalcode.Model;

public class ApprovalEntry {

    private String sSysTypex;
    private String sSystemCd;
    private String sSCATypex;
    private String sBranchCd;
    private String sReqDatex;
    private String sReferNox;
    private String sLastName;
    private String sFrstName;
    private String sMiddName;
    private String sSuffixxx;
    private String sRemarksx;

    private String message;

    public ApprovalEntry(){

    }

    public String getMessage() {
        return message;
    }

    public void setSysTypex(String sSysTypex) {
        this.sSysTypex = sSysTypex;
    }

    public String getSystemCd() {
        return sSystemCd;
    }

    public void setSystemCd(String sSystemCd) {
        this.sSystemCd = sSystemCd;
    }

    public String getSCAType() {
        return sSCATypex;
    }

    public void setSCAType(String sSCATypex) {
        this.sSCATypex = sSCATypex;
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

    public void setReferNox(String sReferNox) {
        this.sReferNox = sReferNox;
    }

    public void setLastName(String sLastName) {
        this.sLastName = sLastName;
    }

    public void setFrstName(String sFrstName) {
        this.sFrstName = sFrstName;
    }

    public void setMiddName(String sMiddName) {
        this.sMiddName = sMiddName;
    }

    public void setSuffix(String sSuffixxx) {
        this.sSuffixxx = sSuffixxx;
    }

    public String getRemarks() {
        return sRemarksx;
    }

    public void setRemarks(String sRemarksx) {
        this.sRemarksx = sRemarksx;
    }

    public String getMiscInfo(){
        if(sSysTypex.equalsIgnoreCase("1")){
            return sReferNox;
        }
        return getFullName();
    }

    private String getFullName(){
        String lsFullName = sLastName + ", " + sFrstName;
        if(!sSuffixxx.trim().isEmpty()){
            lsFullName = lsFullName + " " + sSuffixxx;
        }
        if(!sMiddName.trim().isEmpty()){
            lsFullName = lsFullName + " " + sMiddName;
        }
        return lsFullName;
    }

    public boolean isDataValid(){
        return isBranchValid() && isDateValid() && isReferNoxValid() && isNameValid();
    }

    private boolean isBranchValid(){
        if(sBranchCd == null || sBranchCd.trim().isEmpty()){
            message = "Please select branch";
            return false;
        }
        return true;
    }

    private boolean isDateValid(){
        if(sReqDatex.trim().isEmpty()){
            message = "Please enter requested date";
            return false;
        }
        return true;
    }

    private boolean isReferNoxValid(){
        if(sSysTypex.equalsIgnoreCase("1")){
            if(sReferNox.trim().isEmpty()){
                message = "Please enter reference no.";
                return false;
            }
        }
        return true;
    }

    private boolean isNameValid(){
        if(sSysTypex.equalsIgnoreCase("2")){
            if(sLastName.trim().isEmpty()){
                message = "Please enter last name";
                return false;
            }
            if(sFrstName.trim().isEmpty()){
                message = "Please enter first name";
                return false;
            }
        }
        return true;
    }
}
