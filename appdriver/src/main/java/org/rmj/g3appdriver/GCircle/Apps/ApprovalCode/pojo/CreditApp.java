/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.approvalCode
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.pojo;

public class CreditApp {

    private final String sSystemCD;
    private final String sTransNox;
    private final String sBranchCD;
    private String message;

    public CreditApp(String fsSystemCD, String fsTransNox, String fsBranchCD) {
        this.sSystemCD = fsSystemCD;
        this.sTransNox = fsTransNox;
        this.sBranchCD = fsBranchCD;
    }

    public String getSystemCD() {
        return sSystemCD;
    }

    public String getTransNox() {
        return sTransNox;
    }

    public String getBranchCD() {
        return sBranchCD;
    }

    public String getMessage() {
        return message;
    }

    public boolean isDataValid(){
        if(sTransNox == null || sTransNox.trim().isEmpty()){
            message = "Please enter application transaction no.";
            return false;
        }
        if(sBranchCD == null || sBranchCD.trim().isEmpty()){
            message = "Please enter or select branch";
            return false;
        }
        return true;
    }

    public static class Application{
        private String sTransNox;
        private String sReasonxx;
        private String sApproved;
        private String message;

        public Application(String sTransNox, String sReasonxx, String sApproved) {
            this.sTransNox = sTransNox;
            this.sReasonxx = sReasonxx;
            this.sApproved = sApproved;
        }

        public String getMessage() {
            return message;
        }

        public String getTransNox() {
            return sTransNox;
        }

        public String getReasonxx() {
            return sReasonxx;
        }

        public String getApproved() {
            return sApproved;
        }

        public boolean isDataValid(){
            if(sTransNox == null || sTransNox.trim().isEmpty()){
                message = "Please enter application transaction no.";
                return false;
            }
            if(sReasonxx == null || sReasonxx.trim().isEmpty()){
                message = "Please provide approval reason";
                return false;
            }
            return true;
        }
    }
}
