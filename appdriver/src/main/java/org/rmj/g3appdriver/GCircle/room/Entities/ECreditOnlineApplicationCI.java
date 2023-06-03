package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Credit_Online_Application_CI")
public class ECreditOnlineApplicationCI {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sCredInvx")
    private String CredInvx;
    @ColumnInfo(name = "sManagerx")
    private String Managerx;
    @ColumnInfo(name = "sAddressx")
    private String Addressx;
    @ColumnInfo(name = "sAddrFndg")
    private String AddrFndg;
    @ColumnInfo(name = "sAssetsxx")
    private String Assetsxx;
    @ColumnInfo(name = "sAsstFndg")
    private String AsstFndg;
    @ColumnInfo(name = "sIncomexx")
    private String Incomexx;
    @ColumnInfo(name = "sIncmFndg")
    private String IncmFndg;
    @ColumnInfo(name = "cHasRecrd")
    private String HasRecrd;
    @ColumnInfo(name = "sRecrdRem")
    private String RecrdRem;
    @ColumnInfo(name = "sPrsnBrgy")
    private String PrsnBrgy;
    @ColumnInfo(name = "sPrsnPstn")
    private String PrsnPstn;
    @ColumnInfo(name = "sPrsnNmbr")
    private String PrsnNmbr;
    @ColumnInfo(name = "sNeighBr1")
    private String NeighBr1;
    @ColumnInfo(name = "sNeighBr2")
    private String NeighBr2;
    @ColumnInfo(name = "sNeighBr3")
    private String NeighBr3;
    @ColumnInfo(name = "dRcmdRcd1")
    private String RcmdRcd1;
    @ColumnInfo(name = "dRcmdtnx1")
    private String Rcmdtnx1;
    @ColumnInfo(name = "cRcmdtnx1")
    private String Rcmdtnc1;
    @ColumnInfo(name = "sRcmdtnx1")
    private String Rcmdtns1;
    @ColumnInfo(name = "dRcmdRcd2")
    private String RcmdRcd2;
    @ColumnInfo(name = "dRcmdtnx2")
    private String Rcmdtnx2;
    @ColumnInfo(name = "cRcmdtnx2")
    private String Rcmdtnc2;
    @ColumnInfo(name = "sRcmdtnx2")
    private String Rcmdtns2;
    @ColumnInfo(name = "cTranStat")
    private String TranStat;
    @ColumnInfo(name = "cUploaded")
    private String Uploaded = "0";
    @ColumnInfo(name = "cSendStat")
    private String SendStat = "0";
    @ColumnInfo(name = "cTransfer")
    private String Transfer = "0";
    @ColumnInfo(name = "sApproved")
    private String Approved;
    @ColumnInfo(name = "dApproved")
    private String Dapprovd;
    @ColumnInfo(name = "dTimeStmp", defaultValue = "CURRENT_TIMESTAMP")
    private String TimeStmp;

    public ECreditOnlineApplicationCI() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String sTransNox) {
        this.TransNox = sTransNox;
    }

    /**
     *
     * @return Credit Investigator ID
     */
    public String getCredInvx() {
        return CredInvx;
    }

    /**
     *
     * @param credInvx Credit Investigator ID
     */
    public void setCredInvx(String credInvx) {
        CredInvx = credInvx;
    }

    public String getManagerx() {
        return Managerx;
    }

    public void setManagerx(String managerx) {
        Managerx = managerx;
    }

    /**
     *
     * @return Address Info
     */
    public String getAddressx() {
        return Addressx;
    }

    /**
     *
     * @return Address Info
     */
    public void setAddressx(String addressx) {
        Addressx = addressx;
    }

    /**
     *
     * @return Findings Information of Address
     */
    public String getAddrFndg() {
        return AddrFndg;
    }

    /**
     *
     * @param addrFndg Findings Information of Address
     */
    public void setAddrFndg(String addrFndg) {
        AddrFndg = addrFndg;
    }

    public String getAssetsxx() {
        return Assetsxx;
    }

    public void setAssetsxx(String assetsxx) {
        Assetsxx = assetsxx;
    }

    public String getAsstFndg() {
        return AsstFndg;
    }

    public void setAsstFndg(String asstFndg) {
        AsstFndg = asstFndg;
    }

    public String getIncomexx() {
        return Incomexx;
    }

    public void setIncomexx(String incomexx) {
        Incomexx = incomexx;
    }

    public String getIncmFndg() {
        return IncmFndg;
    }

    public void setIncmFndg(String incmFndg) {
        IncmFndg = incmFndg;
    }

    /**
     *
     * @return has barangay record
     */
    public String getHasRecrd() {
        return HasRecrd;
    }

    /**
     *
     * @param hasRecrd has barangay record
     */
    public void setHasRecrd(String hasRecrd) {
        HasRecrd = hasRecrd;
    }

    /**
     *
     * @return has record remarks
     */
    public String getRecrdRem() {
        return RecrdRem;
    }

    /**
     *
     * @param recrdRem record remarks
     */
    public void setRecrdRem(String recrdRem) {
        RecrdRem = recrdRem;
    }

    /**
     *
     * @return position in barangay if applicant is barangay official
     */
    public String getPrsnBrgy() {
        return PrsnBrgy;
    }

    /**
     *
     * @param prsnBrgy position in barangay if applicant is barangay official
     */
    public void setPrsnBrgy(String prsnBrgy) {
        PrsnBrgy = prsnBrgy;
    }

    /**
     *
     * @return present position
     */
    public String getPrsnPstn() {
        return PrsnPstn;
    }

    /**
     *
     * @param prsnPstn present position
     */
    public void setPrsnPstn(String prsnPstn) {
        PrsnPstn = prsnPstn;
    }

    /**
     *
     * @return present contact number
     */
    public String getPrsnNmbr() {
        return PrsnNmbr;
    }

    /**
     *
     * @param prsnNmbr present contact number
     */
    public void setPrsnNmbr(String prsnNmbr) {
        PrsnNmbr = prsnNmbr;
    }

    public String getNeighBr1() {
        return NeighBr1;
    }

    public void setNeighBr1(String neighBr1) {
        NeighBr1 = neighBr1;
    }

    public String getNeighBr2() {
        return NeighBr2;
    }

    public void setNeighBr2(String neighBr2) {
        NeighBr2 = neighBr2;
    }

    public String getNeighBr3() {
        return NeighBr3;
    }

    public void setNeighBr3(String neighBr3) {
        NeighBr3 = neighBr3;
    }

    /**
     *
     * @return when the application was received by CI
     */
    public String getRcmdRcd1() {
        return RcmdRcd1;
    }

    /**
     *
     * @param rcmdRcd1 when the application was received by CI
     */
    public void setRcmdRcd1(String rcmdRcd1) {
        RcmdRcd1 = rcmdRcd1;
    }

    /**
     *
     * @return when the recommendation was given to CI
     */
    public String getRcmdtnx1() {
        return Rcmdtnx1;
    }

    /**
     *
     * @param rcmdtnx1 when the recommendation was given to CI
     */
    public void setRcmdtnx1(String rcmdtnx1) {
        Rcmdtnx1 = rcmdtnx1;
    }

    /**
     *
     * @return Recommendation (Approved/Disapproved)
     */
    public String getRcmdtnc1() {
        return Rcmdtnc1;
    }

    /**
     *
     * @param rcmdtnc1 Recommendation (Approved/Disapproved)
     */
    public void setRcmdtnc1(String rcmdtnc1) {
        Rcmdtnc1 = rcmdtnc1;
    }

    /**
     *
     * @return Remarks of Credit Investigator
     */
    public String getRcmdtns1() {
        return Rcmdtns1;
    }

    /**
     *
     * @param rcmdtns1 Remarks of Credit Investigator
     */
    public void setRcmdtns1(String rcmdtns1) {
        Rcmdtns1 = rcmdtns1;
    }

    /**
     *
     * @return when the application was received by Branch Manager
     */
    public String getRcmdRcd2() {
        return RcmdRcd2;
    }

    /**
     *
     * @param rcmdRcd2 when the application was received by Branch Manager
     */
    public void setRcmdRcd2(String rcmdRcd2) {
        RcmdRcd2 = rcmdRcd2;
    }

    /**
     *
     * @return when recommendation was given to Branch Manager
     */
    public String getRcmdtnx2() {
        return Rcmdtnx2;
    }

    /**
     *
     * @param rcmdtnx2 when recommendation was given to Branch Manager
     */
    public void setRcmdtnx2(String rcmdtnx2) {
        Rcmdtnx2 = rcmdtnx2;
    }

    /**
     *
      * @return Recommendation (Approved/Disapproved)
     */
    public String getRcmdtnc2() {
        return Rcmdtnc2;
    }

    /**
     *
     * @param rcmdtnc2 Recommendation (Approved/Disapproved)
     */
    public void setRcmdtnc2(String rcmdtnc2) {
        Rcmdtnc2 = rcmdtnc2;
    }

    /**
     *
     * @return Remarks of Branch Manager
     */
    public String getRcmdtns2() {
        return Rcmdtns2;
    }

    /**
     *
     * @param rcmdtns2 remarks of Branch Manager
     */
    public void setRcmdtns2(String rcmdtns2) {
        Rcmdtns2 = rcmdtns2;
    }

    /**
     *
     * @return Approve/Disapproved by Collection Dept.
     */
    public String getTranStat() {
        return TranStat;
    }

    /**
     *
     * @param tranStat Approve/Disapproved by Collection Dept.
     */
    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getSendStat() {
        return SendStat;
    }

    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }

    /**
     *
     * @return Who approved the application
     */
    public String getApproved() {
        return Approved;
    }

    /**
     *
     * @param approved Who approved the application
     */
    public void setApproved(String approved) {
        Approved = approved;
    }

    /**
     *
     * @return date of approval
     */
    public String getDapprovd() {
        return Dapprovd;
    }

    /**
     *
     * @param dapprovd date of approval
     */
    public void setDapprovd(String dapprovd) {
        Dapprovd = dapprovd;
    }

    public String getUploaded() {
        return Uploaded;
    }

    public void setUploaded(String uploaded) {
        Uploaded = uploaded;
    }

    public String getTransfer() {
        return Transfer;
    }

    public void setTransfer(String transfer) {
        Transfer = transfer;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
