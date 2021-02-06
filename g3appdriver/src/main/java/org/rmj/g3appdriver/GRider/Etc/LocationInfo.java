package org.rmj.g3appdriver.GRider.Etc;

public class LocationInfo {

    private double pnLatitude;
    private double pnLongitude;

    private String psAddress;
    private String psTownCty;
    private String psStatexx;
    private String psCountry;
    private String psPostalx;
    private String psNamexxx;

    public LocationInfo(){

    }

    public double getLatitude() {
        return pnLatitude;
    }

    public void setLatitude(double pnLattitude) {
        this.pnLatitude = pnLattitude;
    }

    public double getLongitude() {
        return pnLongitude;
    }

    public void setLongitude(double pnLongitude) {
        this.pnLongitude = pnLongitude;
    }

    public String getAddress() {
        return psAddress;
    }

    public void setAddress(String psAddress) {
        this.psAddress = psAddress;
    }

    public String getTownCity() {
        return psTownCty;
    }

    public void setTownCity(String psTownCty) {
        this.psTownCty = psTownCty;
    }

    public String getState() {
        return psStatexx;
    }

    public void setState(String psStatexx) {
        this.psStatexx = psStatexx;
    }

    public String getCountry() {
        return psCountry;
    }

    public void setCountry(String psCountry) {
        this.psCountry = psCountry;
    }

    public String getPostal() {
        return psPostalx;
    }

    public void setPostal(String psPostalx) {
        this.psPostalx = psPostalx;
    }

    public String getName() {
        return psNamexxx;
    }

    public void setName(String psNamexxx) {
        this.psNamexxx = psNamexxx;
    }
}
