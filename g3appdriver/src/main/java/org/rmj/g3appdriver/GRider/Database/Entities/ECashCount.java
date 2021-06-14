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

package org.rmj.g3appdriver.GRider.Database.Entities;

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

    @ColumnInfo(name = "dTransact")
    private String Transact;

    @ColumnInfo(name = "nCn0005cx")
    private String Cn0005cx;

    @ColumnInfo(name = "nCn0010cx")
    private String Cn0010cx;

    @ColumnInfo(name = "nCn0025cx")
    private String Cn0025cx;

    @ColumnInfo(name = "nCn0050cx")
    private String Cn0050cx;

    @ColumnInfo(name = "nCn0001px")
    private String Cn0001px;

    @ColumnInfo(name = "nCn0005px")
    private String Cn0005px;

    @ColumnInfo(name = "nCn0010px")
    private String Cn0010px;

    @ColumnInfo(name = "nNte0020p")
    private String Nte0020p;

    @ColumnInfo(name = "nNte0050p")
    private String Nte0050p;

    @ColumnInfo(name = "nNte0100p")
    private String Nte0100p;

    @ColumnInfo(name = "nNte0200p")
    private String Nte0200p;

    @ColumnInfo(name = "nNte0500p")
    private String Nte0500p;

    @ColumnInfo(name = "nNte1000p")
    private String Nte1000p;

    @ColumnInfo(name = "sORNoxxxx")
    private String ORNoxxxx;

    @ColumnInfo(name = "sSINoxxxx")
    private String SINoxxxx;

    @ColumnInfo(name = "sPRNoxxxx")
    private String PRNoxxxx;

    @ColumnInfo(name = "sCRNoxxxx")
    private String CRNoxxxx;

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
    private String SendStat;


    public ECashCount() {
    }
    public String getORNoxxxx() {
        return ORNoxxxx;
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

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getCn0005cx() {
        return Cn0005cx;
    }

    public void setCn0005cx(String cn0005cx) {
        Cn0005cx = cn0005cx;
    }

    public String getCn0010cx() {
        return Cn0010cx;
    }

    public void setCn0010cx(String cn0010cx) {
        Cn0010cx = cn0010cx;
    }

    public String getCn0025cx() {
        return Cn0025cx;
    }

    public void setCn0025cx(String cn0025cx) {
        Cn0025cx = cn0025cx;
    }

    public String getCn0050cx() {
        return Cn0050cx;
    }

    public void setCn0050cx(String cn0050cx) {
        Cn0050cx = cn0050cx;
    }
    public String getCn0001px() {
        return Cn0001px;
    }

    public void setCn0001px(String cn0001px) {
        Cn0001px = cn0001px;
    }

    public String getCn0005px() {
        return Cn0005px;
    }

    public void setCn0005px(String cn0005px) {
        Cn0005px = cn0005px;
    }

    public String getCn0010px() {
        return Cn0010px;
    }

    public void setCn0010px(String cn0010px) {
        Cn0010px = cn0010px;
    }

    public String getNte0020p() {
        return Nte0020p;
    }

    public void setNte0020p(String nte0020p) {
        Nte0020p = nte0020p;
    }

    public String getNte0050p() {
        return Nte0050p;
    }

    public void setNte0050p(String nte0050p) {
        Nte0050p = nte0050p;
    }

    public String getNte0100p() {
        return Nte0100p;
    }

    public void setNte0100p(String nte0100p) {
        Nte0100p = nte0100p;
    }

    public String getNte0200p() {
        return Nte0200p;
    }

    public void setNte0200p(String nte0200p) {
        Nte0200p = nte0200p;
    }

    public String getNte0500p() {
        return Nte0500p;
    }

    public void setNte0500p(String nte0500p) {
        Nte0500p = nte0500p;
    }

    public String getNte1000p() {
        return Nte1000p;
    }

    public void setNte1000p(String nte1000p) {
        Nte1000p = nte1000p;
    }

}
