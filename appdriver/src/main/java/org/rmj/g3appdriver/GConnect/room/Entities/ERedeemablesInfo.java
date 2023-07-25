package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Redeemables")
public class ERedeemablesInfo {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sTransNox")
    private String TransNox;

    @ColumnInfo(name = "sPromoCde")
    private String PromoCde;

    @ColumnInfo(name = "sPromoDsc")
    private String PromoDsc;

    @ColumnInfo(name = "nPointsxx")
    private double Pointsxx;

    @ColumnInfo(name = "sImageUrl")
    private String ImageUrl;

    @ColumnInfo(name = "cPreOrder")
    private String PreOrder;

    @ColumnInfo(name = "dDateFrom")
    private String DateFrom;

    @ColumnInfo(name = "dDateThru")
    private String DateThru;

    @ColumnInfo(name = "cNotified")
    private String Notified;

    public ERedeemablesInfo(){

    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getPromoCde() {
        return PromoCde;
    }

    public void setPromoCde(String promoCde) {
        PromoCde = promoCde;
    }

    public String getPromoDsc() {
        return PromoDsc;
    }

    public void setPromoDsc(String promoDsc) {
        PromoDsc = promoDsc;
    }

    public double getPointsxx() {
        return Pointsxx;
    }

    public void setPointsxx(double pointsxx) {
        Pointsxx = pointsxx;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getPreOrder() {
        return PreOrder;
    }

    public void setPreOrder(String preOrder) {
        PreOrder = preOrder;
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
}
