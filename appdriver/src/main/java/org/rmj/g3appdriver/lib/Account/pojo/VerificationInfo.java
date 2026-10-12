package org.rmj.g3appdriver.lib.Account.pojo;

public class VerificationInfo {

    public String sUserIDxx;
    public String sOTPxx;

    public VerificationInfo(String fsUserIDxx, String fsOTPxx){
        this.sUserIDxx = fsUserIDxx;
        this.sOTPxx = fsOTPxx;
    }

    public String getsUserIDxx() {
        return sUserIDxx;
    }

    public String getsOTPxx() {
        return sOTPxx;
    }
}
