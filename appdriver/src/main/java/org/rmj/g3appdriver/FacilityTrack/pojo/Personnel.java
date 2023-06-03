package org.rmj.g3appdriver.FacilityTrack.pojo;

public class Personnel {

    private final String sPrsnIDxx;
    private final String sPrsnName;
    private final String cPrsnStat;
    private final String sWrHousex;

    public Personnel(String sPrsnIDxx, String sPrsnName, String cPrsnStat, String sWrHousex) {
        this.sPrsnIDxx = sPrsnIDxx;
        this.sPrsnName = sPrsnName;
        this.cPrsnStat = cPrsnStat;
        this.sWrHousex = sWrHousex;
    }

    public String getPersonnelID() {
        return sPrsnIDxx;
    }

    public String getPersonnelName() {
        return sPrsnName;
    }

    public String getPersonnelStatus() {
        return cPrsnStat;
    }

    public String getWarehouseDuty() {
        return sWrHousex;
    }
}
