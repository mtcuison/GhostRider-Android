/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

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
