package org.rmj.guanzongroup.promotions.Model;

public class PromoDetail {

    private String sBranchCd;
    private String dTransact;
    private String sClientNm;
    private String sDocTypex;
    private String sDocNoxxx;
    private String sMobileNo;
    private char cSendStat;
    private String sTimeStmp;

    public PromoDetail() {
    }

    public String getsBranchCd() {
        return sBranchCd;
    }

    public void setsBranchCd(String sBranchCd) {
        this.sBranchCd = sBranchCd;
    }

    public String getdTransact() {
        return dTransact;
    }

    public void setdTransact(String dTransact) {
        this.dTransact = dTransact;
    }

    public String getsClientNm() {
        return sClientNm;
    }

    public void setsClientNm(String sClientNm) {
        this.sClientNm = sClientNm;
    }

    public String getsDocTypex() {
        return sDocTypex;
    }

    public void setsDocTypex(String sDocTypex) {
        this.sDocTypex = sDocTypex;
    }

    public String getsDocNoxxx() {
        return sDocNoxxx;
    }

    public void setsDocNoxxx(String sDocNoxxx) {
        this.sDocNoxxx = sDocNoxxx;
    }

    public String getsMobileNo() {
        return sMobileNo;
    }

    public void setsMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }

    public char getcSendStat() {
        return cSendStat;
    }

    public void setcSendStat(char cSendStat) {
        this.cSendStat = cSendStat;
    }

    public String getsTimeStmp() {
        return sTimeStmp;
    }

    public void setsTimeStmp(String sTimeStmp) {
        this.sTimeStmp = sTimeStmp;
    }

    public String getEntryLink(){
        return "https://restgk.guanzongroup.com.ph/promo/fblike/encoded.php?brc=" +
                "" + sBranchCd + "&typ=" + sDocTypex + "&nox=" + sDocNoxxx +
                "&mob=" + sMobileNo + "&nme=" + sClientNm + "";
    }
}
