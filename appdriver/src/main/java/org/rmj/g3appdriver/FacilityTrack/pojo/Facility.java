package org.rmj.g3appdriver.FacilityTrack.pojo;

public class Facility {

    private String sWHouseID;
    private String sWHouseNm;
    private String dLastChck;

    public Facility(String sWHouseID, String sWHouseNm, String dLastChck) {
        this.sWHouseID = sWHouseID;
        this.sWHouseNm = sWHouseNm;
        this.dLastChck = dLastChck;
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
}
