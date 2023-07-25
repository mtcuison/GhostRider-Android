package org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo;

public class PromiseToPay {

    private String Remarksx = "",
                    Transact = "",
                    Paymntxx = "0",
                    CollctNm = "",
                    BranchCd = "",
                    AccntNox = "",
                    TransNox = "",
                    EntryNox = "",
                    FileName = "",
                    FilePath = "";

    private double latitude, longtude;

    private String message;

    public PromiseToPay() {

    }

    public String getMessage() {
        return message;
    }

    public String getRemarks() {
        return Remarksx;
    }

    public void setRemarks(String remarksx) {
        Remarksx = remarksx;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getTransact() {
        return Transact;
    }

    public String getPaymntxx() {
        return Paymntxx;
    }

    public void setPaymntxx(String paymntxx) {
        Paymntxx = paymntxx;
    }

    public String getCollctNm() {
        return CollctNm;
    }

    public void setCollctNm(String collctNm) {
        CollctNm = collctNm;
    }

    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(String branchCd) {
        BranchCd = branchCd;
    }

    public String getAccntNox() {
        return AccntNox;
    }

    public void setAccntNox(String accntNox) {
        AccntNox = accntNox;
    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(String entryNox) {
        EntryNox = entryNox;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtude() {
        return longtude;
    }

    public void setLongtude(double longtude) {
        this.longtude = longtude;
    }

    public boolean isDataValid(){
        if (Transact.trim().isEmpty()){
            message = "Please select Payment Date.";
            return false;
        }
        if (Paymntxx.trim().isEmpty()){
            message = "Please select appointment unit.";
            return false;
        }
        if (Remarksx.trim().isEmpty()){
            message = "Please enter remarks.";
            return false;
        }
        if (Paymntxx.equalsIgnoreCase("1") &&
                BranchCd.trim().isEmpty()){
            message = "Please enter branch.";
            return false;
        }
        return true;
    }
}
