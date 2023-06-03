package org.rmj.g3appdriver.GConnect.room.Entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Promo_Link_Info")
public class EPromo {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sTransNox")
    private String TransNox;

    @ColumnInfo(name = "dTransact")
    private String Transact;

    @ColumnInfo(name = "dDateFrom")
    private String DateFrom;

    @ColumnInfo(name = "dDateThru")
    private String DateThru;

    @ColumnInfo(name = "sCaptionx")
    private String Captionx;

    @ColumnInfo(name = "sImageUrl")
    private String ImageUrl;

    @ColumnInfo(name = "sImageSld")
    private String ImageSld;

    @ColumnInfo(name = "sImgeByte")
    private String ImgeByte;

    @ColumnInfo(name = "sPromoUrl")
    private String PromoUrl;

    @ColumnInfo(name = "cNotified")
    private String Notified;

    @ColumnInfo(name = "cDivision")
    private int Division;

    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;

    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EPromo() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getImgeByte() {
        return ImgeByte;
    }

    public void setImgeByte(String imgeByte) {
        ImgeByte = imgeByte;
    }

    public String getPromoUrl() {
        return PromoUrl;
    }

    public void setPromoUrl(String promoUrl) {
        PromoUrl = promoUrl;
    }

    public String getCaptionx() {
        return Captionx;
    }

    public void setCaptionx(String captionx) {
        Captionx = captionx;
    }

    public String getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(String dateFrom) {
        DateFrom = dateFrom;
    }

    public String getDateThru() {
        return DateThru;
    }

    public void setDateThru(String dateThru) {
        DateThru = dateThru;
    }

    public String getNotified() {
        return Notified;
    }

    public void setNotified(String notified) {
        Notified = notified;
    }

    public int getDivision() {
        return Division;
    }

    public void setDivision(int division) {
        Division = division;
    }

    public String getImageSld() {
        return ImageSld;
    }

    public void setImageSld(String imageSld) {
        ImageSld = imageSld;
    }

    public String getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(String recdStat) {
        RecdStat = recdStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
