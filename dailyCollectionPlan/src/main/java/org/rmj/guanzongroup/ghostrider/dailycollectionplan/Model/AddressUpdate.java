package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

public class AddressUpdate {
    private String cReqstCDe;
    private String cAddrssTp;
    private String sHouseNox;
    private String sAddressx;
    private String sProvIDxx;
    private String sProvName;
    private String sTownIDxx;
    private String sTownName;
    private String sBrgyIDxx;
    private String sBrgyName;
    private String cPrimaryx;
    private String nLatitude;
    private String nLongitud;
    private String sRemarksx;

    private String message;

    public AddressUpdate(){

    }

    public String getMessage() {
        return message;
    }

    public String getRequestCode() {
        return cReqstCDe;
    }

    public void setRequestCode(String cReqstCDe) {
        this.cReqstCDe = cReqstCDe;
    }

    public String getcAddrssTp() {
        return cAddrssTp;
    }

    public void setcAddrssTp(String cAddrssTp) {
        this.cAddrssTp = cAddrssTp;
    }

    public String getHouseNumber() {
        return sHouseNox;
    }

    public void setHouseNumber(String sHouseNox) {
        this.sHouseNox = sHouseNox;
    }

    public String getAddress() {
        return sAddressx;
    }

    public void setAddress(String sAddressx) {
        this.sAddressx = sAddressx;
    }

    public String getsProvIDxx() {
        return sProvIDxx;
    }

    public void setsProvIDxx(String sProvIDxx) {
        this.sProvIDxx = sProvIDxx;
    }

    public String getTownID() {
        return sTownIDxx;
    }

    public void setTownID(String sTownIDxx) {
        this.sTownIDxx = sTownIDxx;
    }

    public String getBarangayID() {
        return sBrgyIDxx;
    }

    public void setBarangayID(String sBrgyIDxx) {
        this.sBrgyIDxx = sBrgyIDxx;
    }

    public String getPrimaryStatus() {
        return cPrimaryx;
    }

    public void setPrimaryStatus(String cPrimaryx) {
        this.cPrimaryx = cPrimaryx;
    }

    public String getLatitude() {
        return nLatitude;
    }

    public void setLatitude(String nLatitude) {
        this.nLatitude = nLatitude;
    }

    public String getLongitude() {
        return nLongitud;
    }

    public void setLongitude(String nLongitud) {
        this.nLongitud = nLongitud;
    }

    public String getRemarks() {
        return sRemarksx;
    }

    public void setsRemarksx(String sRemarksx) {
        this.sRemarksx = sRemarksx;
    }

    public String getsProvName() {
        return sProvName;
    }

    public void setsProvName(String sProvName) {
        this.sProvName = sProvName;
    }

    public String getsTownName() {
        return sTownName;
    }

    public void setsTownName(String sTownName) {
        this.sTownName = sTownName;
    }

    public String getsBrgyName() {
        return sBrgyName;
    }

    public void setsBrgyName(String sBrgyName) {
        this.sBrgyName = sBrgyName;
    }

    public boolean isDataValid() {
        return isRequestCodeValid() &&
                isAddressTPValid() &&
                isHouseNoxValid() &&
                isAddressValid() &&
                isTownValid() &&
                isBrgyValid() &&
                isRemarksValid();
    }

    private boolean isRequestCodeValid(){
        if(cReqstCDe.trim().isEmpty()){
            message = "Please select request code";
            return false;
        }
        return true;
    }

    private boolean isAddressTPValid() {
        if(cAddrssTp == null || cAddrssTp.equalsIgnoreCase("")) {
            message = "Please select Address Type";
            return false;
        }
        return true;
    }

    private boolean isHouseNoxValid() {
        if(sHouseNox.trim().isEmpty()) {
            message = "Please enter House No.";
            return false;
        }
        return true;
    }

    private boolean isAddressValid() {
        if(sAddressx.trim().isEmpty()) {
            message = "Please enter Street/Sitio/Lot No.";
            return false;
        }
        return true;
    }


    private boolean isTownValid(){
        if(sTownIDxx == null || sTownIDxx.equalsIgnoreCase("")){
            message = "Please enter town";
            return false;
        }
        return true;
    }

    private boolean isBrgyValid() {
        if(sBrgyIDxx == null || sBrgyIDxx.equalsIgnoreCase("")) {
            message = "Please enter barangay";
            return false;
        }
        return true;
    }

    private boolean isRemarksValid() {
        if(sRemarksx.trim().isEmpty()) {
            message = "Please enter remarks";
            return false;
        }
        return true;
    }
}
