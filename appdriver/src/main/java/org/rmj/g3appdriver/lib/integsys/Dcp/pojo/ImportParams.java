package org.rmj.g3appdriver.lib.integsys.Dcp.pojo;

public class ImportParams {
    private final String sEmployID;
    private final String dTransact;

    public ImportParams(String sEmployID, String dTransact, String cDCPTypex) {
        this.sEmployID = sEmployID;
        this.dTransact = dTransact;
    }

    public String getEmployID() {
        return sEmployID;
    }

    public String getTransact() {
        return dTransact;
    }
}
