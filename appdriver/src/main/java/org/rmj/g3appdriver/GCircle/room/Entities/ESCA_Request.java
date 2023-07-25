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
import androidx.room.PrimaryKey;

@Entity(tableName = "xxxSCA_Request")
public class ESCA_Request {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sSCACodex")
    private String SCACodex = "";
    @ColumnInfo(name = "sSCATitle")
    private String SCATitle;
    @ColumnInfo(name = "sSCADescx")
    private String SCADescx;
    @ColumnInfo(name = "cSCATypex")
    private String SCATypex;
    @ColumnInfo(name = "cAreaHead")
    private String AreaHead;
    @ColumnInfo(name = "cHCMDeptx")
    private String HCMDeptx;
    @ColumnInfo(name = "cCSSDeptx")
    private String CSSDeptx;
    @ColumnInfo(name = "cComplnce")
    private String Complnce;
    @ColumnInfo(name = "cMktgDept")
    private String MktgDept;
    @ColumnInfo(name = "cASMDeptx")
    private String ASMDeptx;
    @ColumnInfo(name = "cTLMDeptx")
    private String TLMDeptx;
    @ColumnInfo(name = "cSCMDeptx")
    private String SCMDeptx;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;
    @ColumnInfo(name = "dLstUpdte")
    private String LstUpdte;

    public ESCA_Request() {
    }

    @NonNull
    public String getSCACodex() {
        return SCACodex;
    }

    public void setSCACodex(@NonNull String SCACodex) {
        this.SCACodex = SCACodex;
    }

    public String getSCATitle() {
        return SCATitle;
    }

    public void setSCATitle(String SCATitle) {
        this.SCATitle = SCATitle;
    }

    public String getSCADescx() {
        return SCADescx;
    }

    public void setSCADescx(String SCADescx) {
        this.SCADescx = SCADescx;
    }

    public String getSCATypex() {
        return SCATypex;
    }

    public void setSCATypex(String SCATypex) {
        this.SCATypex = SCATypex;
    }

    public String getAreaHead() {
        return AreaHead;
    }

    public void setAreaHead(String areaHead) {
        AreaHead = areaHead;
    }

    public String getHCMDeptx() {
        return HCMDeptx;
    }

    public void setHCMDeptx(String HCMDeptx) {
        this.HCMDeptx = HCMDeptx;
    }

    public String getCSSDeptx() {
        return CSSDeptx;
    }

    public void setCSSDeptx(String CSSDeptx) {
        this.CSSDeptx = CSSDeptx;
    }

    public String getComplnce() {
        return Complnce;
    }

    public void setComplnce(String complnce) {
        Complnce = complnce;
    }

    public String getMktgDept() {
        return MktgDept;
    }

    public void setMktgDept(String mktgDept) {
        MktgDept = mktgDept;
    }

    public String getASMDeptx() {
        return ASMDeptx;
    }

    public void setASMDeptx(String ASMDeptx) {
        this.ASMDeptx = ASMDeptx;
    }

    public String getTLMDeptx() {
        return TLMDeptx;
    }

    public void setTLMDeptx(String TLMDeptx) {
        this.TLMDeptx = TLMDeptx;
    }

    public String getSCMDeptx() {
        return SCMDeptx;
    }

    public void setSCMDeptx(String SCMDeptx) {
        this.SCMDeptx = SCMDeptx;
    }

    public String getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(String recdStat) {
        RecdStat = recdStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }

    public String getLstUpdte() {
        return LstUpdte;
    }

    public void setLstUpdte(String lstUpdte) {
        LstUpdte = lstUpdte;
    }
}
