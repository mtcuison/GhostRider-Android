package org.rmj.g3appdriver.FacilityTrack.pojo;

import org.rmj.g3appdriver.etc.AppConstants;

public class Facility {

    private String sWHouseID;
    private String sWHouseNm;
    private String dLastChck;
    private int nVisitedx;

    public Facility(String sWHouseID, String sWHouseNm, String dLastChck) {
        this.sWHouseID = sWHouseID;
        this.sWHouseNm = sWHouseNm;
        this.dLastChck = dLastChck;
        this.nVisitedx = nVisitedx;
    }

    public String getWarehouseID() {
        return sWHouseID;
    }

    public String getWarehouseName() {
        return sWHouseNm;
    }

    public String getLastCheck() {
        return dLastChck;
    }

    public void WarehouseVisited(){
        nVisitedx++;
        dLastChck = AppConstants.DATE_MODIFIED();
    }

    public int getNumberOfVisits() {
        return nVisitedx;
    }
}
