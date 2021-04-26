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
