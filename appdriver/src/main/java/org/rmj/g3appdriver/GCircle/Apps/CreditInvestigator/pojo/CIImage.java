package org.rmj.g3appdriver.GCircle.Apps.CreditInvestigator.pojo;

public class CIImage {
    private String sTransNox;
    private String sParentxx;
    private String sKeyNamex;
    private String cResultxx;
    private String nLatitude;
    private String nLongtude;
    private String sFileName;
    private String sFilePath;

    public CIImage() {

    }

    public String getTransNox() {
        return sTransNox;
    }

    public void setTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getLatitude() {
        return nLatitude;
    }

    public void setLatitude(String nLatitude) {
        this.nLatitude = nLatitude;
    }

    public String getLongtude() {
        return nLongtude;
    }

    public void setLongtude(String nLongtude) {
        this.nLongtude = nLongtude;
    }

    public String getFileName() {
        return sFileName;
    }

    public void setFileName(String sFileName) {
        this.sFileName = sFileName;
    }

    public String getFilePath() {
        return sFilePath;
    }

    public void setFilePath(String sFilePath) {
        this.sFilePath = sFilePath;
    }

    public boolean isPrimaryAddress(){
        return sParentxx.equalsIgnoreCase("primary_address");
    }

    public String getsParentxx() {
        return sParentxx;
    }

    public void setsParentxx(String sParentxx) {
        this.sParentxx = sParentxx;
    }

    public String getsKeyNamex() {
        return sKeyNamex;
    }

    public void setsKeyNamex(String sKeyNamex) {
        this.sKeyNamex = sKeyNamex;
    }

    public String getcResultxx() {
        return cResultxx;
    }

    public void setcResultxx(String cResultxx) {
        this.cResultxx = cResultxx;
    }
}
