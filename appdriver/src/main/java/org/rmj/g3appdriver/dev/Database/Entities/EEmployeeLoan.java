package org.rmj.g3appdriver.dev.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Employee_Loan")
public class EEmployeeLoan {

    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx = "";
    @ColumnInfo(name = "dTransact")
    private String Transact = "";
    @ColumnInfo(name = "sLoanIDxx")
    private String LoanIDxx = "";
    @ColumnInfo(name = "cLoanType")
    private String LoanType = "";
    @ColumnInfo(name = "nAmountxx")
    private Double Amountxx = 0.0;
    @ColumnInfo(name = "nInterest")
    private Double Interest = 0.0;
    @ColumnInfo(name = "nLoanTerm")
    private Integer LoanTerm = 0;
    @ColumnInfo(name = "cSendStat")
    private Integer SendStat = 0;

    public EEmployeeLoan() {
    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getLoanIDxx() {
        return LoanIDxx;
    }

    public void setLoanIDxx(String loanIDxx) {
        LoanIDxx = loanIDxx;
    }

    public String getLoanType() {
        return LoanType;
    }

    public void setLoanType(String loanType) {
        LoanType = loanType;
    }

    public Double getAmountxx() {
        return Amountxx;
    }

    public void setAmountxx(Double amountxx) {
        Amountxx = amountxx;
    }

    public Double getInterest() {
        return Interest;
    }

    public void setInterest(Double interest) {
        Interest = interest;
    }

    public Integer getLoanTerm() {
        return LoanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        LoanTerm = loanTerm;
    }

    public Integer getSendStat() {
        return SendStat;
    }

    public void setSendStat(Integer sendStat) {
        SendStat = sendStat;
    }
}
