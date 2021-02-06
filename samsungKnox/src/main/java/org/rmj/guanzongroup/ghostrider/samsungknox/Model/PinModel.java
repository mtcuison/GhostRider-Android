package org.rmj.guanzongroup.ghostrider.samsungknox.Model;

import org.rmj.g3appdriver.utils.ImeiValidator;

public class PinModel {

    private final String psDevceID;
    private final String psPassKey;

    private String psMessage;

    public PinModel(String psDevceID, String psPassKey) {
        this.psDevceID = psDevceID;
        this.psPassKey = psPassKey;
    }

    public String getPsMessage() {
        return psMessage;
    }

    public String getDeviceID() {
        return psDevceID;
    }

    public String getPassKey() {
        return psPassKey;
    }

    public boolean isParameterValid(){
        return isDeviceValid() && isPasskeyValid();
    }

    private boolean isDeviceValid(){
        if(psDevceID.trim().isEmpty()){
            psMessage = "Please enter device id";
            return false;
        }
        if(ImeiValidator.isValidImei(Long.parseLong(psDevceID))){
            psMessage = "Device imei is invalid please retry";
            return false;
        }
        return true;
    }

    private boolean isPasskeyValid(){
        if(psPassKey.trim().isEmpty()){
            psMessage = "Please enter";
            return false;
        }
        if(psPassKey.length() < 4){
            psMessage = "Device passkey is invalid please retry";
            return false;
        }
        return true;
    }
}
