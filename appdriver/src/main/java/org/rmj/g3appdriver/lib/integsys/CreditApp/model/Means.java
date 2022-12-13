package org.rmj.g3appdriver.lib.integsys.CreditApp.model;

public class Means {
    private String TransNox = "";
    private String IncmeSrc = "";
    private String message;

    public Means() {
    }
    public String getMessage(){
        return message;
    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getIncmeSrc() {
        return IncmeSrc;
    }

    public void setIncmeSrc(String incmeSrc) {
        IncmeSrc = incmeSrc;
    }
}
