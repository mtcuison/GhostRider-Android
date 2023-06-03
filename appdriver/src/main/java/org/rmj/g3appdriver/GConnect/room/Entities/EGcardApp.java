package org.rmj.g3appdriver.GConnect.room.Entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GCard_App_Master")
public class EGcardApp {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sGCardNox")
    private String GCardNox;

    @ColumnInfo(name = "sCardNmbr")
    private String CardNmbr;


    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx;

    @ColumnInfo(name = "sNmOnCard")
    private String NmOnCard;

    @ColumnInfo(name = "dMemberxx")
    private String Memberxx;

    @ColumnInfo(name = "cCardType")
    private String CardType;

    @ColumnInfo(name = "sTotPoint")
    private String TotPoint;

    @ColumnInfo(name = "sAvlPoint")
    private String AvlPoint;

    @ColumnInfo(name = "cTranStat")
    private String TranStat;

    @ColumnInfo(name = "cActvStat")
    private String ActvStat;

    @ColumnInfo(name = "dModified")
    private String Modified;

    @ColumnInfo(name = "cNotified")
    private String Notified;

    public EGcardApp() {
    }

    @NonNull
    public String getCardNmbr() {
        return CardNmbr;
    }

    public void setCardNmbr(@NonNull String cardNmbr) {
        CardNmbr = cardNmbr;
    }

    public String getGCardNox() {
        return GCardNox;
    }

    public void setGCardNox(String GCardNox) {
        this.GCardNox = GCardNox;
    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getNmOnCard() {
        return NmOnCard;
    }

    public void setNmOnCard(String nmOnCard) {
        NmOnCard = nmOnCard;
    }

    public String getMemberxx() {
        return Memberxx;
    }

    public void setMemberxx(String memberxx) {
        Memberxx = memberxx;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getTotPoint() {
        return TotPoint;
    }

    public void setTotPoint(String totPoint) {
        TotPoint = totPoint;
    }

    public String getAvlPoint() {
        return AvlPoint;
    }

    public void setAvlPoint(String avlPoint) {
        AvlPoint = avlPoint;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getActvStat() {
        return ActvStat;
    }

    public void setActvStat(String actvStat) {
        ActvStat = actvStat;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getNotified() {
        return Notified;
    }

    public void setNotified(String notified) {
        Notified = notified;
    }

}
