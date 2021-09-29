/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/21/21, 4:20 PM
 * project file last modified : 9/21/21, 4:20 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

public class OBApprovalInfo {

    private String TransNox;
    private String AppldFrx;
    private String AppldTox;
    private String Approved;
    private String DateAppv;
    private String TranStat;

    public OBApprovalInfo() {
    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getAppldFrx() {
        return AppldFrx;
    }

    public void setAppldFrx(String appldFrx) {
        AppldFrx = appldFrx;
    }

    public String getAppldTox() {
        return AppldTox;
    }

    public void setAppldTox(String appldTox) {
        AppldTox = appldTox;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String approved) {
        Approved = approved;
    }

    public String getDateAppv() {
        return DateAppv;
    }

    public void setDateAppv(String dateAppv) {
        DateAppv = dateAppv;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }
}
