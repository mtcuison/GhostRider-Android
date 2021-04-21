package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Collection_Account_Remittance", primaryKeys = {"sBranchCd", "sBnkActID", "sActNumbr"})
public class ERemittanceAccounts {

    @NonNull
    @ColumnInfo(name = "sBranchCd")
    private String BranchCd;

    @NonNull
    @ColumnInfo(name = "sActNumbr")
    private String ActNumbr;

    @NonNull
    @ColumnInfo(name = "sBnkActID")
    private String BnkActID;

    @ColumnInfo(name = "sBranchNm")
    private String BranchNm;
    @ColumnInfo(name = "sActNamex")
    private String ActNamex;

    public ERemittanceAccounts() {
    }

    @NonNull
    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(@NonNull String branchCd) {
        BranchCd = branchCd;
    }

    @NonNull
    public String getActNumbr() {
        return ActNumbr;
    }

    public void setActNumbr(@NonNull String actNumbr) {
        ActNumbr = actNumbr;
    }

    @NonNull
    public String getBnkActID() {
        return BnkActID;
    }

    public void setBnkActID(@NonNull String bnkActID) {
        BnkActID = bnkActID;
    }

    public String getBranchNm() {
        return BranchNm;
    }

    public void setBranchNm(String branchNm) {
        BranchNm = branchNm;
    }

    public String getActNamex() {
        return ActNamex;
    }

    public void setActNamex(String actNamex) {
        ActNamex = actNamex;
    }
}
