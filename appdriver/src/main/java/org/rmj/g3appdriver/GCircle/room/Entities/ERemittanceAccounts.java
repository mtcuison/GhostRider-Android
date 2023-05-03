/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

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
