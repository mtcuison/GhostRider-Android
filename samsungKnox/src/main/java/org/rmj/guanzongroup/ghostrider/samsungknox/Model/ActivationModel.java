/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.samsungKnox
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.samsungknox.Model;

import org.rmj.g3appdriver.utils.ImeiValidator;

public class ActivationModel {

    private String DeviceID;
    private String Remarksx;

    private String psMessage;

    public ActivationModel(String deviceID, String remarksx) {
        DeviceID = deviceID;
        Remarksx = remarksx;
    }

    public String getPsMessage() {
        return psMessage;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public boolean isDeviceValid(){
        if(DeviceID.isEmpty()){
            psMessage = "Please enter device imei";
            return false;
        }
        if (ImeiValidator.isValidImei(Long.parseLong(DeviceID))){
            psMessage = "Device imei is invalid please retry";
            return false;
        }
        return true;
    }
}
