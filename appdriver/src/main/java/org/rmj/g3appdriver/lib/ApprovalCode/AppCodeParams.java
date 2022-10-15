package org.rmj.g3appdriver.lib.ApprovalCode;

public class AppCodeParams {
    private String sBranchCd;
    private String dReqDatex;
    private String sSystemCd;
    private String sMiscInfo;
    private String sRemarksx;

    private String message;

    public AppCodeParams() {
    }

    public String getMessage() {
        return message;
    }

    public String getsBranchCd() {
        return sBranchCd;
    }

    public void setsBranchCd(String sBranchCd) {
        this.sBranchCd = sBranchCd;
    }

    public String getdReqDatex() {
        return dReqDatex;
    }

    public void setdReqDatex(String dReqDatex) {
        this.dReqDatex = dReqDatex;
    }

    public String getsSystemCd() {
        return sSystemCd;
    }

    public void setsSystemCd(String sSystemCd) {
        this.sSystemCd = sSystemCd;
    }

    public String getsMiscInfo() {
        return sMiscInfo;
    }

    public void setsMiscInfo(String sMiscInfo) {
        this.sMiscInfo = sMiscInfo;
    }

    public String getsRemarksx() {
        return sRemarksx;
    }

    public void setsRemarksx(String sRemarksx) {
        this.sRemarksx = sRemarksx;
    }

    public boolean isDataValid(){
        if(sBranchCd == null){
            message = "Please select branch";
            return false;
        } else if(dReqDatex == null){
            message = "Please enter date requested";
            return false;
        } else if(sSystemCd == null){
            message = "Please select system code";
            return false;
        } else if(sMiscInfo == null){
            message = "Please enter misc info ";
            return false;
        }

        return true;
    }
}
