package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

import android.content.Intent;

public class ReimburseInfo {

    private String sDetlInfo;
    private String sAmountxx;

    private String message;

    public ReimburseInfo(String sDetlInfo, String sAmountxx) {
        this.sDetlInfo = sDetlInfo;
        this.sAmountxx = sAmountxx;
    }

    public String getMessage() {
        return message;
    }

    public String getsDetlInfo() {
        return sDetlInfo;
    }

    public int getsAmountxx() {
        return Integer.parseInt(sAmountxx);
    }

    public boolean isDataValid(){
        if(sDetlInfo == null || sDetlInfo.trim().isEmpty()){
            message = "Please enter report detail or receipt no.";
            return false;
        } else if(sAmountxx == null || sAmountxx.trim().isEmpty()){
            message = "Please enter amount";
            return false;
        } else {
            return true;
        }
    }
}
