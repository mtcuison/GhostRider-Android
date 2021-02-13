package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

public class MobileUpdate {
    private String cReqstCde;
    private String sMobileNo;
    private String cPrimaryx;
    private String sRemarksx;

    private String message;

    public MobileUpdate(String cReqstCde, String sMobileNo, String cPrimaryx, String sRemarksx) {
        this.cReqstCde = cReqstCde;
        this.sMobileNo = sMobileNo;
        this.cPrimaryx = cPrimaryx;
        this.sRemarksx = sRemarksx;
    }

    public String getMessage(){
        return message;
    }

    public String getcReqstCde() {
        return cReqstCde;
    }

    public String getsMobileNo() {
        return sMobileNo;
    }

    public String getcPrimaryx() {
        return cPrimaryx;
    }

    public String getsRemarksx() {
        return sRemarksx;
    }

    public boolean isDataValid(){
        return isRequestCodeValid() && isMobileNoValid();
    }

    private boolean isRequestCodeValid(){
        if(cReqstCde.trim().isEmpty()){
            message = "Please select request code";
            return false;
        }
        return true;
    }

    private boolean isMobileNoValid(){
        if(sMobileNo.trim().isEmpty()){
            message = "Please enter mobile no";
            return false;
        }
        return true;
    }
}
