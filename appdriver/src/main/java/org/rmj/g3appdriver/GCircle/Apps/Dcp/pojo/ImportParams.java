package org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo;

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
