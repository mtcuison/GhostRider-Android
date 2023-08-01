package org.rmj.g3appdriver.lib.Account.pojo;

import org.rmj.g3appdriver.dev.Api.WebFileServer;

public class PhotoDetail {
    private String sSourceCD;
    private String sSourceNo;
    private String sDtlSrcNo;
    private String sFileCode;
    private String sImageNme;
    private String sMD5Hashx;
    private String sFileLoct;
    private String dCaptured;

    public PhotoDetail() {

    }

    public String getSourceCD() {
        return sSourceCD;
    }

    public void setSourceCD(String sSourceCD) {
        this.sSourceCD = sSourceCD;
    }

    public String getSourceNo() {
        return sSourceNo;
    }

    public void setSourceNo(String sSourceNo) {
        this.sSourceNo = sSourceNo;
    }

    public String getDtlSrcNo() {
        return sDtlSrcNo;
    }

    public void setDtlSrcNo(String sDtlSrcNo) {
        this.sDtlSrcNo = sDtlSrcNo;
    }

    public String getFileCode() {
        return sFileCode;
    }

    public void setFileCode(String sFileCode) {
        this.sFileCode = sFileCode;
    }

    public String getImageNme() {
        return sImageNme;
    }

    public void setImageNme(String sImageNme) {
        this.sImageNme = sImageNme;
    }

    public String getMD5Hashx() {
        return WebFileServer.createMD5Hash(sFileLoct);
    }

    public String getFileLoct() {
        return sFileLoct;
    }

    public void setFileLoct(String sFileLoct) {
        this.sFileLoct = sFileLoct;
    }

    public String getCaptured() {
        return dCaptured;
    }

    public void setCaptured(String dCaptured) {
        this.dCaptured = dCaptured;
    }

    public boolean isDataValid() {
        return sFileLoct != null;
    }
}