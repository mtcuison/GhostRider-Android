package org.rmj.g3appdriver.GConnect.room.Entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Client_Profile_Info")
public class EClientInfo {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx;
    @ColumnInfo(name = "sClientID")
    private String ClientID;
    @ColumnInfo(name = "sEmailAdd")
    private String EmailAdd = "";
    @ColumnInfo(name = "sUserName")
    private String UserName = "";
    @ColumnInfo(name = "sLastName")
    private String LastName = "";
    @ColumnInfo(name = "sFrstName")
    private String FrstName = "";
    @ColumnInfo(name = "sMiddName")
    private String MiddName = "";
    @ColumnInfo(name = "sSuffixNm")
    private String SuffixNm = "";
    @ColumnInfo(name = "sMaidenNm")
    private String MaidenNm = "";
    @ColumnInfo(name = "cGenderCd")
    private String GenderCd = "";
    @ColumnInfo(name = "cCvilStat")
    private String CvilStat = "";
    @ColumnInfo(name = "sCitizenx")
    private String Citizenx;
    @ColumnInfo(name = "dBirthDte")
    private String BirthDte;
    @ColumnInfo(name = "sBirthPlc")
    private String BirthPlc;
    @ColumnInfo(name = "sHouseNo1")
    private String HouseNo1 = "";
    @ColumnInfo(name = "sAddress1")
    private String Address1 = "";
    @ColumnInfo(name = "sBrgyIDx1")
    private String BrgyIDx1 = "";
    @ColumnInfo(name = "sTownIDx1")
    private String TownIDx1 = "";
    @ColumnInfo(name = "sHouseNo2")
    private String HouseNo2 = "";
    @ColumnInfo(name = "sAddress2")
    private String Address2 = "";
    @ColumnInfo(name = "sBrgyIDx2")
    private String BrgyIDx2 = "";
    @ColumnInfo(name = "sTownIDx2")
    private String TownIDx2 = "";
    @ColumnInfo(name = "sTaxIDNox")
    private String TaxIDNox = "";
    @ColumnInfo(name = "cRecdStat")
    private int RecdStat;
    @ColumnInfo(name = "sMobileNo")
    private String MobileNo = "";
    @ColumnInfo(name = "cVerified")
    private int Verified;
    @ColumnInfo(name = "dVerified")
    private String DateVrfd;
    @ColumnInfo(name = "cAgreeTnC")
    private int AgreeTnC;
    @ColumnInfo(name = "dDateMmbr")
    private String DateMmbr;
    @ColumnInfo(name = "sImagePth")
    private String ImagePth = "";
    @ColumnInfo(name = "dImgeDate")
    private String ImgeDate = "";
    @ColumnInfo(name = "cImgeStat")
    private String ImgeStat = "";
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EClientInfo() {
    }

    @NonNull
    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(@NonNull String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getEmailAdd() {
        return EmailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        EmailAdd = emailAdd;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFrstName() {
        return FrstName;
    }

    public void setFrstName(String frstName) {
        FrstName = frstName;
    }

    public String getMiddName() {
        return MiddName;
    }

    public void setMiddName(String middName) {
        MiddName = middName;
    }

    public String getSuffixNm() {
        return SuffixNm;
    }

    public void setSuffixNm(String suffixNm) {
        SuffixNm = suffixNm;
    }

    public String getGenderCd() {
        return GenderCd;
    }

    public void setGenderCd(String genderCd) {
        GenderCd = genderCd;
    }

    public String getCvilStat() {
        return CvilStat;
    }

    public void setCvilStat(String cvilStat) {
        CvilStat = cvilStat;
    }

    public String getBirthDte() {
        return BirthDte;
    }

    public void setBirthDte(String birthDte) {
        BirthDte = birthDte;
    }

    public String getBirthPlc() {
        return BirthPlc;
    }

    public void setBirthPlc(String birthPlc) {
        BirthPlc = birthPlc;
    }

    public String getTaxIDNox() {
        return TaxIDNox;
    }

    public void setTaxIDNox(String taxIDNox) {
        TaxIDNox = taxIDNox;
    }

    public int getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(int recdStat) {
        RecdStat = recdStat;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getDateMmbr() {
        return DateMmbr;
    }

    public void setDateMmbr(String dateMmbr) {
        DateMmbr = dateMmbr;
    }

    public String getCitizenx() {
        return Citizenx;
    }

    public void setCitizenx(String citizenx) {
        Citizenx = citizenx;
    }

    public int getAgreeTnC() {
        return AgreeTnC;
    }

    public void setAgreeTnC(int agreeTnC) {
        AgreeTnC = agreeTnC;
    }

    public String getMaidenNm() {
        return MaidenNm;
    }

    public void setMaidenNm(String maidenNm) {
        MaidenNm = maidenNm;
    }

    public String getHouseNo1() {
        return HouseNo1;
    }

    public void setHouseNo1(String houseNo1) {
        HouseNo1 = houseNo1;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getTownIDx1() {
        return TownIDx1;
    }

    public void setTownIDx1(String townIDx1) {
        TownIDx1 = townIDx1;
    }

    public String getHouseNo2() {
        return HouseNo2;
    }

    public void setHouseNo2(String houseNo2) {
        HouseNo2 = houseNo2;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getTownIDx2() {
        return TownIDx2;
    }

    public void setTownIDx2(String townIDx2) {
        TownIDx2 = townIDx2;
    }

    public int getVerified() {
        return Verified;
    }

    public void setVerified(int verified) {
        Verified = verified;
    }

    public String getDateVrfd() {
        return DateVrfd;
    }

    public void setDateVrfd(String dateVrfd) {
        DateVrfd = dateVrfd;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }

    public String getBrgyIDx1() {
        return BrgyIDx1;
    }

    public void setBrgyIDx1(String brgyIDx1) {
        BrgyIDx1 = brgyIDx1;
    }

    public String getBrgyIDx2() {
        return BrgyIDx2;
    }

    public void setBrgyIDx2(String brgyIDx2) {
        BrgyIDx2 = brgyIDx2;
    }

    public String getImagePth() {
        return ImagePth;
    }

    public void setImagePth(String imagePth) {
        ImagePth = imagePth;
    }

    public String getImgeDate() {
        return ImgeDate;
    }

    public void setImgeDate(String imgeDate) {
        ImgeDate = imgeDate;
    }

    public String getImgeStat() {
        return ImgeStat;
    }

    public void setImgeStat(String imgeStat) {
        ImgeStat = imgeStat;
    }
}
