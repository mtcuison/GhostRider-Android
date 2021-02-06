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
