package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Pacita_Evaluation")
public class EPacitaEvaluation {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sTransNox")
    private String TransNox = "";
    @ColumnInfo(name = "dTransact")
    private String Transact = "";
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx = "";
    @ColumnInfo(name = "sBranchCD")
    private String BranchCD = "";
    @ColumnInfo(name = "sPayloadx")
    private String Payloadx = "";
    @ColumnInfo(name = "nRatingxx")
    private Double Ratingxx = 0.0;
    @ColumnInfo(name = "sEvalType")
    private String EvalType = "";
    @ColumnInfo(name = "cTranStat")
    private String TranStat = "0";
    @ColumnInfo(name = "dModified")
    private String Modified = "";
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";

    public EPacitaEvaluation() {
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

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getBranchCD() {
        return BranchCD;
    }

    public void setBranchCD(String branchCD) {
        BranchCD = branchCD;
    }

    public String getPayloadx() {
        return Payloadx;
    }

    public void setPayloadx(String payloadx) {
        Payloadx = payloadx;
    }

    public Double getRatingxx() {
        return Ratingxx;
    }

    public void setRatingxx(Double ratingxx) {
        Ratingxx = ratingxx;
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

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getEvalType() {
        return EvalType;
    }

    public void setEvalType(String evalType) {
        EvalType = evalType;
    }
}
