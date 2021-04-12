package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CI_Evaluation")
public class ECIEvaluation {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sCredInvx")
    private String CredInvx;
    @ColumnInfo(name = "sLandMark")
    private String LandMark;
    @ColumnInfo(name = "cOwnershp")
    private String Ownershp;
    @ColumnInfo(name = "cOwnOther")
    private String OwnOther;
    @ColumnInfo(name = "cHouseTyp")
    private String HouseTyp;
    @ColumnInfo(name = "cGaragexx")
    private String Garagexx;
    @ColumnInfo(name = "Latitude")
    private String Latitude;
    @ColumnInfo(name = "nLongitud")
    private String Longitud;
    @ColumnInfo(name = "cHasOther")
    private String HasOther;
    @ColumnInfo(name = "cHasRecrd")
    private String HasRecrd;
    @ColumnInfo(name = "sRemRecrd")
    private String sRemRecrd;

    @ColumnInfo(name = "sNeighbr1")
    private String Neighbr1;
    @ColumnInfo(name = "sAddress1")
    private String Address1;
    @ColumnInfo(name = "sReltnCD1")
    private String ReltnCD1;
    @ColumnInfo(name = "sMobileN1")
    private String MobileN1;
    @ColumnInfo(name = "FeedBck1")
    private String FeedBck1;
    @ColumnInfo(name = "FeedBck1")
    private String FBRemrk1;

    @ColumnInfo(name = "sNeighbr2")
    private String Neighbr2;
    @ColumnInfo(name = "sAddress2")
    private String Address2;
    @ColumnInfo(name = "sReltnCD2")
    private String ReltnCD2;
    @ColumnInfo(name = "sMobileN2")
    private String MobileN2;
    @ColumnInfo(name = "FeedBck2")
    private String FeedBck2;
    @ColumnInfo(name = "FeedBck2")
    private String FBRemrk2;

    @ColumnInfo(name = "sNeighbr3")
    private String Neighbr3;
    @ColumnInfo(name = "sAddress3")
    private String Address3;
    @ColumnInfo(name = "sReltnCD3")
    private String ReltnCD3;
    @ColumnInfo(name = "sMobileN3")
    private String MobileN3;
    @ColumnInfo(name = "FeedBck3")
    private String FeedBck3;
    @ColumnInfo(name = "FeedBck3")
    private String FBRemrk3;

    @ColumnInfo(name = "nWaterBil")
    private String WaterBil;
    @ColumnInfo(name = "nElctrcBl")
    private String ElctrcBl;
    @ColumnInfo(name = "nFoodAllw")
    private String FoodAllw;
    @ColumnInfo(name = "nLoanAmtx")
    private String LoanAmtx;
    @ColumnInfo(name = "nEducExpn")
    private String EducExpn;
    @ColumnInfo(name = "nOthrExpn")
    private String OthrExpn;

    @ColumnInfo(name = "cGamblerx")
    private String Gamblerx;
    @ColumnInfo(name = "cWomanizr")
    private String Womanizr;
    @ColumnInfo(name = "cHvyBrwer")
    private String HvyBrwer;
    @ColumnInfo(name = "cWithRepo")
    private String WithRepo;
    @ColumnInfo(name = "cWithMort")
    private String WithMort;
    @ColumnInfo(name = "cArrogant")
    private String Arrogant;
    @ColumnInfo(name = "sOtherBad")
    private String OtherBad;

    @ColumnInfo(name = "sRemarksx")
    private String Remarksx;
    @ColumnInfo(name = "cTranStat")
    private String TranStat;
    @ColumnInfo(name = "dApproved")
    private String Approved;
    @ColumnInfo(name = "dReceived")
    private String Received;
    @ColumnInfo(name = "dTimeStmp")
    private String dTimeStmp;

    public ECIEvaluation() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }
    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }


}
