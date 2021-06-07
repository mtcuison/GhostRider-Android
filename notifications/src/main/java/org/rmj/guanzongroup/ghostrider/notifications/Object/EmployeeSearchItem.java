/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 5/18/21 9:37 AM
 * project file last modified : 5/18/21 9:37 AM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Object;

public class EmployeeSearchItem {

    private String sEmplyeID;
    private String sEmplyeNm;

    public EmployeeSearchItem(String sEmplyeID, String sEmplyeNm) {
        this.sEmplyeID = sEmplyeID;
        this.sEmplyeNm = sEmplyeNm;
    }

    public String getsEmplyeID() {
        return sEmplyeID;
    }

    public String getsEmplyeNm() {
        return sEmplyeNm;
    }
}
