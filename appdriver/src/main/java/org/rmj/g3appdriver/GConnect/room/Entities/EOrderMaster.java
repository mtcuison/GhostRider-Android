package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MarketPlace_Order_Master")
public class EOrderMaster {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sBranchCd")
    private String BranchCd;
    @ColumnInfo(name = "dTransact")
    private String Transact;
    @ColumnInfo(name = "dExpected")
    private String Expected;
    @ColumnInfo(name = "sClientID")
    private String ClientID;
    @ColumnInfo(name = "sAppUsrID")
    private String AppUsrID;
    @ColumnInfo(name = "sReferNox")
    private String ReferNox;
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx;
    @ColumnInfo(name = "nTranTotl")
    private Double TranTotl = 0.00;
    @ColumnInfo(name = "nVATRatex")
    private Double VATRatex = 0.00;
    @ColumnInfo(name = "nDiscount")
    private Double Discount = 0.00;
    @ColumnInfo(name = "nAddDiscx")
    private Double AddDiscx = 0.00;
    @ColumnInfo(name = "nFreightx")
    private Double Freightx = 0.00;
    @ColumnInfo(name = "nProcPaym")
    private Double ProcPaym = 0.00;
    @ColumnInfo(name = "nAmtPaidx")
    private Double AmtPaidx = 0.00;
    @ColumnInfo(name = "dDueDatex")
    private String DueDatex;
    @ColumnInfo(name = "sTermCode")
    private String TermCode;
    @ColumnInfo(name = "sSourceNo")
    private String SourceNo;
    @ColumnInfo(name = "sSourceCd")
    private String SourceCd;
    @ColumnInfo(name = "cTranStat")
    private String TranStat;
    @ColumnInfo(name = "dWaybillx")
    private String Waybillx;
    @ColumnInfo(name = "sWaybilNo")
    private String WaybilNo;
    @ColumnInfo(name = "sBatchNox")
    private String BatchNox;
    @ColumnInfo(name = "dPickedUp")
    private String PickedUp;
    @ColumnInfo(name = "cPaymPstd")
    private String PaymPstd;
    @ColumnInfo(name = "cPaymType")
    private String PaymType;
    @ColumnInfo(name = "dModified")
    private String Modified;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EOrderMaster() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(String branchCd) {
        BranchCd = branchCd;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getExpected() {
        return Expected;
    }

    public void setExpected(String expected) {
        Expected = expected;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getAppUsrID() {
        return AppUsrID;
    }

    public void setAppUsrID(String appUsrID) {
        AppUsrID = appUsrID;
    }

    public String getReferNox() {
        return ReferNox;
    }

    public void setReferNox(String referNox) {
        ReferNox = referNox;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public Double getTranTotl() {
        return TranTotl;
    }

    public void setTranTotl(Double tranTotl) {
        TranTotl = tranTotl;
    }

    public Double getVATRatex() {
        return VATRatex;
    }

    public void setVATRatex(Double VATRatex) {
        this.VATRatex = VATRatex;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

    public Double getAddDiscx() {
        return AddDiscx;
    }

    public void setAddDiscx(Double addDiscx) {
        AddDiscx = addDiscx;
    }

    public Double getFreightx() {
        return Freightx;
    }

    public void setFreightx(Double freightx) {
        Freightx = freightx;
    }

    public Double getProcPaym() {
        return ProcPaym;
    }

    public void setProcPaym(Double procPaym) {
        ProcPaym = procPaym;
    }

    public Double getAmtPaidx() {
        return AmtPaidx;
    }

    public void setAmtPaidx(Double amtPaidx) {
        AmtPaidx = amtPaidx;
    }

    public String getDueDatex() {
        return DueDatex;
    }

    public void setDueDatex(String dueDatex) {
        DueDatex = dueDatex;
    }

    public String getTermCode() {
        return TermCode;
    }

    public void setTermCode(String termCode) {
        TermCode = termCode;
    }

    public String getSourceNo() {
        return SourceNo;
    }

    public void setSourceNo(String sourceNo) {
        SourceNo = sourceNo;
    }

    public String getSourceCd() {
        return SourceCd;
    }

    public void setSourceCd(String sourceCd) {
        SourceCd = sourceCd;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getWaybillx() {
        return Waybillx;
    }

    public void setWaybillx(String waybillx) {
        Waybillx = waybillx;
    }

    public String getWaybilNo() {
        return WaybilNo;
    }

    public void setWaybilNo(String waybilNo) {
        WaybilNo = waybilNo;
    }

    public String getBatchNox() {
        return BatchNox;
    }

    public void setBatchNox(String batchNox) {
        BatchNox = batchNox;
    }

    public String getPickedUp() {
        return PickedUp;
    }

    public void setPickedUp(String pickedUp) {
        PickedUp = pickedUp;
    }

    public String getPaymPstd() {
        return PaymPstd;
    }

    public void setPaymPstd(String paymPstd) {
        PaymPstd = paymPstd;
    }

    public String getPaymType() {
        return PaymType;
    }

    public void setPaymType(String paymType) {
        PaymType = paymType;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
