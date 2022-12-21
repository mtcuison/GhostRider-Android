/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 6/9/21 4:27 PM
 * project file last modified : 6/9/21 4:27 PM
 */

package org.rmj.g3appdriver.dev.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cash_Count_Master")
public class ECashCount {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;

    @ColumnInfo(name = "sBranchCd")
    private String BranchCd;

    @ColumnInfo(name = "dTransact")
    private String Transact;

    @ColumnInfo(name = "nCn0001cx")
    private Integer Cn0001cx = 0;

    @ColumnInfo(name = "nCn0005cx")
    private Integer Cn0005cx = 0;

    @ColumnInfo(name = "nCn0010cx")
    private Integer Cn0010cx = 0;

    @ColumnInfo(name = "nCn0025cx")
    private Integer Cn0025cx = 0;

    @ColumnInfo(name = "nCn0050cx")
    private Integer Cn0050cx = 0;

    @ColumnInfo(name = "nCn0001px")
    private Integer Cn0001px = 0;

    @ColumnInfo(name = "nCn0005px")
    private Integer Cn0005px = 0;

    @ColumnInfo(name = "nCn0010px")
    private Integer Cn0010px = 0;

    @ColumnInfo(name = "nNte0020p")
    private Integer Nte0020p = 0;

    @ColumnInfo(name = "nNte0050p")
    private Integer Nte0050p = 0;

    @ColumnInfo(name = "nNte0100p")
    private Integer Nte0100p = 0;

    @ColumnInfo(name = "nNte0200p")
    private Integer Nte0200p = 0;

    @ColumnInfo(name = "nNte0500p")
    private Integer Nte0500p = 0;

    @ColumnInfo(name = "nNte1000p")
    private Integer Nte1000p = 0;

    @ColumnInfo(name = "sPettyAmt")
    private Double PettyAmt  = 0.0;

    @ColumnInfo(name = "sORNoxxxx")
    private String ORNoxxxx;

    @ColumnInfo(name = "sSINoxxxx")
    private String SINoxxxx;

    @ColumnInfo(name = "sPRNoxxxx")
    private String PRNoxxxx;

    @ColumnInfo(name = "sCRNoxxxx")
    private String CRNoxxxx;

    @ColumnInfo(name = "sORNoxNPt")
    private String ORNoxNPt;

    @ColumnInfo(name = "sPRNoxNPt")
    private String PRNoxNPt;

    @ColumnInfo(name = "sDRNoxxxx")
    private String DRNoxxxx;

    @ColumnInfo(name = "dEntryDte")
    private String EntryDte;

    @ColumnInfo(name = "sReqstdBy")
    private String ReqstdBy;

    @ColumnInfo(name = "dModified")
    private String Modified;

    public String getSendStat() {
        return SendStat;
    }

    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }

    @ColumnInfo(name = "sSendStat")
    private String SendStat = "0";

    public ECashCount() {
    }

    public String getORNoxxxx() {
        return ORNoxxxx;
    }

    public double getPettyAmt() {
        return PettyAmt;
    }

    public void setPettyAmt(double pettAmnt) {
        PettyAmt = pettAmnt;
    }

    public void setORNoxxxx(String ORNoxxxx) {
        this.ORNoxxxx = ORNoxxxx;
    }

    public String getSINoxxxx() {
        return SINoxxxx;
    }

    public void setSINoxxxx(String SINoxxxx) {
        this.SINoxxxx = SINoxxxx;
    }

    public String getPRNoxxxx() {
        return PRNoxxxx;
    }

    public void setPRNoxxxx(String PRNoxxxx) {
        this.PRNoxxxx = PRNoxxxx;
    }

    public String getCRNoxxxx() {
        return CRNoxxxx;
    }

    public void setCRNoxxxx(String CRNoxxxx) {
        this.CRNoxxxx = CRNoxxxx;
    }

    public String getEntryDte() {
        return EntryDte;
    }

    public void setEntryDte(String entryDte) {
        EntryDte = entryDte;
    }


    public String getReqstdBy() {
        return ReqstdBy;
    }

    public void setReqstdBy(String reqstdBy) {
        ReqstdBy = reqstdBy;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
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

    public int getCn0001cx() {
        return Cn0001cx;
    }

    public void setCn0001cx(int cn0001cx) {
        Cn0001cx = cn0001cx;
    }

    public int getCn0005cx() {
        return Cn0005cx;
    }

    public void setCn0005cx(int cn0005cx) {
        Cn0005cx = cn0005cx;
    }

    public int getCn0010cx() {
        return Cn0010cx;
    }

    public void setCn0010cx(int cn0010cx) {
        Cn0010cx = cn0010cx;
    }

    public int getCn0025cx() {
        return Cn0025cx;
    }

    public void setCn0025cx(int cn0025cx) {
        Cn0025cx = cn0025cx;
    }

    public int getCn0050cx() {
        return Cn0050cx;
    }

    public void setCn0050cx(int cn0050cx) {
        Cn0050cx = cn0050cx;
    }
    public int getCn0001px() {
        return Cn0001px;
    }

    public void setCn0001px(int cn0001px) {
        Cn0001px = cn0001px;
    }

    public int getCn0005px() {
        return Cn0005px;
    }

    public void setCn0005px(int cn0005px) {
        Cn0005px = cn0005px;
    }

    public int getCn0010px() {
        return Cn0010px;
    }

    public void setCn0010px(int cn0010px) {
        Cn0010px = cn0010px;
    }

    public int getNte0020p() {
        return Nte0020p;
    }

    public void setNte0020p(int nte0020p) {
        Nte0020p = nte0020p;
    }

    public int getNte0050p() {
        return Nte0050p;
    }

    public void setNte0050p(int nte0050p) {
        Nte0050p = nte0050p;
    }

    public int getNte0100p() {
        return Nte0100p;
    }

    public void setNte0100p(int nte0100p) {
        Nte0100p = nte0100p;
    }

    public int getNte0200p() {
        return Nte0200p;
    }

    public void setNte0200p(int nte0200p) {
        Nte0200p = nte0200p;
    }

    public int getNte0500p() {
        return Nte0500p;
    }

    public void setNte0500p(int nte0500p) {
        Nte0500p = nte0500p;
    }

    public int getNte1000p() {
        return Nte1000p;
    }

    public void setNte1000p(int nte1000p) {
        Nte1000p = nte1000p;
    }

    public String getORNoxNPt() {
        return ORNoxNPt;
    }

    public void setORNoxNPt(String ORNoxNPt) {
        this.ORNoxNPt = ORNoxNPt;
    }

    public String getPRNoxNPt() {
        return PRNoxNPt;
    }

    public void setPRNoxNPt(String PRNoxNPt) {
        this.PRNoxNPt = PRNoxNPt;
    }

    public String getDRNoxxxx() {
        return DRNoxxxx;
    }

    public void setDRNoxxxx(String DRNoxxxx) {
        this.DRNoxxxx = DRNoxxxx;
    }
}