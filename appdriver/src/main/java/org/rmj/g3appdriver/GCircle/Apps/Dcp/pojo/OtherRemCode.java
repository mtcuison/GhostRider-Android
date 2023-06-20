package org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo;

public class OtherRemCode {
    private String RemCodex,
                    Remarksx,
                    AccntNox,
                    TransNox,
                    EntryNox,
                    FileName,
                    FilePath,
                    latitude,
                    longtude,
                    message;

    public OtherRemCode() {
    }

    public String getMessage() {
        return message;
    }

    public String getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(String entryNox) {
        EntryNox = entryNox;
    }

    public String getRemCodex() {
        return RemCodex;
    }

    public void setRemCodex(String remCodex) {
        RemCodex = remCodex;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public String getAccountNo() {
        return AccntNox;
    }

    public void setAccountNo(String accntNox) {
        AccntNox = accntNox;
    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtude() {
        return longtude;
    }

    public void setLongtude(String longtude) {
        this.longtude = longtude;
    }

    public boolean isDataValid(){
        if(Remarksx.trim().isEmpty()){
            message = "Please enter remarks.";
            return false;
        }
        return true;
    }
}
