package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Image_Information")
public class EImageInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sSourceCD")
    private String SourceCD;
    @ColumnInfo(name = "sSourceNo")
    private String SourceNo;
    @ColumnInfo(name = "sDtlSrcNo")
    private String DtlSrcNo;
    @ColumnInfo(name = "sFileCode")
    private String FileCode;
    @ColumnInfo(name = "sImageNme")
    private String ImageNme;
    @ColumnInfo(name = "sMD5Hashx")
    private String MD5Hashx;
    @ColumnInfo(name = "nLatitude")
    private String Latitude;
    @ColumnInfo(name = "nLongitud")
    private String Longitud;
    @ColumnInfo(name = "cSendStat")
    private char SendStat;
    @ColumnInfo(name = "dSendDate")
    private String SendDate;

    public EImageInfo() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getSourceCD() {
        return SourceCD;
    }

    public void setSourceCD(String sourceCD) {
        SourceCD = sourceCD;
    }

    public String getSourceNo() {
        return SourceNo;
    }

    public void setSourceNo(String sourceNo) {
        SourceNo = sourceNo;
    }

    public String getDtlSrcNo() {
        return DtlSrcNo;
    }

    public void setDtlSrcNo(String dtlSrcNo) {
        DtlSrcNo = dtlSrcNo;
    }

    public String getFileCode() {
        return FileCode;
    }

    public void setFileCode(String fileCode) {
        FileCode = fileCode;
    }

    public String getImageNme() {
        return ImageNme;
    }

    public void setImageNme(String imageNme) {
        ImageNme = imageNme;
    }

    public String getMD5Hashx() {
        return MD5Hashx;
    }

    public void setMD5Hashx(String MD5Hashx) {
        this.MD5Hashx = MD5Hashx;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public char getSendStat() {
        return SendStat;
    }

    public void setSendStat(char sendStat) {
        SendStat = sendStat;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
    }
}
