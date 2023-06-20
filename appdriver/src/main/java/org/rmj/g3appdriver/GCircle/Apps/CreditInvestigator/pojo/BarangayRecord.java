package org.rmj.g3appdriver.GCircle.Apps.CreditInvestigator.pojo;

public class BarangayRecord {
    private String sTransNox;
    private String cHasRecrd = "";
    private String sRecrdRem = "";
    private String sBrgyPrsn = "";
    private String sBrgyPstn = "";
    private String sPrsnNmbr = "";

    private String message;

    public BarangayRecord() {
    }

    public String getMessage() {
        return message;
    }

    public String getsTransNox() {
        return sTransNox;
    }

    public void setsTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getcHasRecrd() {
        return cHasRecrd;
    }

    public void setcHasRecrd(String cHasRecrd) {
        this.cHasRecrd = cHasRecrd;
    }

    public String getsRecrdRem() {
        return sRecrdRem;
    }

    public void setsRecrdRem(String sRecrdRem) {
        this.sRecrdRem = sRecrdRem;
    }

    public String getsBrgyPrsn() {
        return sBrgyPrsn;
    }

    public void setsBrgyPrsn(String sBrgyPrsn) {
        this.sBrgyPrsn = sBrgyPrsn;
    }

    public String getsBrgyPstn() {
        return sBrgyPstn;
    }

    public void setsBrgyPstn(String sBrgyPstn) {
        this.sBrgyPstn = sBrgyPstn;
    }

    public String getsPrsnNmbr() {
        return sPrsnNmbr;
    }

    public void setsPrsnNmbr(String sPrsnNmbr) {
        this.sPrsnNmbr = sPrsnNmbr;
    }

    public boolean isDataValid(){
        if(cHasRecrd == null){
            message = "Please check if applicant has barangay record.";
            return false;
        } else if(cHasRecrd.equalsIgnoreCase("1")){
            if(sRecrdRem == null){
                message = "Please enter remarks about barangay record of applicant.";
                return false;
            }

            if(sRecrdRem.isEmpty()){
                message = "Please enter remarks about barangay record of applicant.";
                return false;
            }
        }

        if(sBrgyPrsn == null){
            message = "Please enter the name of barangay personnel interviewed.";
            return false;
        }

        if(sBrgyPrsn.isEmpty()){
            message = "Please enter the name of barangay personnel interviewed.";
            return false;
        }

        if(sBrgyPstn == null){
            message = "Please enter the position of barangay personnel interviewed.";
            return false;
        }

        if(sBrgyPstn.isEmpty()){
            message = "Please enter the position of barangay personnel interviewed.";
            return false;
        }

        if(sPrsnNmbr.isEmpty()){
            message = "Please enter the mobile no. of barangay personnel interviewed.";
            return false;
        }

        if(sPrsnNmbr.isEmpty()){
            message = "Please enter the mobile no. of barangay personnel interviewed.";
            return false;
        }

        return true;
    }
}
