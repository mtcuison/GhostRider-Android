package org.rmj.g3appdriver.GConnect.GCard.DigitalGcard.pojo;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.encryp.CodeGenerator;

public class GcardCredentials {
    private String sGcrdNmbr = "";
    private String sBirthDte = "";
    private String sConfirmx = "0";

    private String message = "";

    CodeGenerator poCode;

    public GcardCredentials(String sGcrdNmbr, String sBirthDte) {
        this.sGcrdNmbr = sGcrdNmbr;
        this.sBirthDte = sBirthDte;
        this.poCode = new CodeGenerator();
    }

    public void setsConfirmx(String sConfirmx) {
        this.sConfirmx = sConfirmx;
    }

    public String getMessage() {
        return message;
    }

    public boolean isDataValid(){
        if (sGcrdNmbr.isEmpty()){
            message = "Please enter gcard number.";
            return false;
        } else if(sBirthDte.isEmpty()){
            message = "Please enter your birth date.";
            return false;
        } else {
            return true;
        }
    }

    public String getJSONParameters() throws Exception{
        JSONObject params = new JSONObject();
        params.put("secureno", poCode.generateSecureNo(sGcrdNmbr));
        params.put("bday", sBirthDte);
        params.put("newdevce", sConfirmx);
        return params.toString();
    }
}
