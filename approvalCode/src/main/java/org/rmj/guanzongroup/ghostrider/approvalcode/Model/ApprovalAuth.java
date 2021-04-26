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

package org.rmj.guanzongroup.ghostrider.approvalcode.Model;

class ApprovalAuth {

    private final String sCACodexx;
    private final String sCATitlex;
    private final String sCATypexx;

    public ApprovalAuth(String Code, String Title, String Type){
        this.sCACodexx = Code;
        this.sCATitlex = Title;
        this.sCATypexx = Type;
    }

    public String getsCACodexx() {
        return sCACodexx;
    }

    public String getsCATitlex() {
        return sCATitlex;
    }

    public String getsCATypexx() {
        return sCATypexx;
    }
}
