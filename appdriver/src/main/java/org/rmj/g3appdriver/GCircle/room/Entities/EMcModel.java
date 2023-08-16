/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Mc_Model")
public class EMcModel {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sModelIDx")
    private String ModelIDx;
    @ColumnInfo(name = "sModelCde")
    private String ModelCde;
    @ColumnInfo(name = "sModelNme")
    private String ModelNme;
    @ColumnInfo(name = "sBrandIDx")
    private String BrandIDx;
    @ColumnInfo(name = "cMotorTyp")
    private String MotorTyp;
    @ColumnInfo(name = "cRegisTyp")
    private String RegisTyp;
    @ColumnInfo(name = "cEndOfLfe")
    private String EndOfLfe;
    @ColumnInfo(name = "cEngineTp")
    private String EngineTp;
    @ColumnInfo(name = "cHotItemx")
    private String HotItemx;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;
    @ColumnInfo(name = "dLstUpdte")
    private String LstUpdte;

    public EMcModel() {
    }

    @NonNull
    public String getModelIDx() {
        return ModelIDx;
    }

    public void setModelIDx(@NonNull String modelIDx) {
        ModelIDx = modelIDx;
    }

    public String getModelCde() {
        return ModelCde;
    }

    public void setModelCde(String modelCde) {
        ModelCde = modelCde;
    }

    public String getModelNme() {
        return ModelNme;
    }

    public void setModelNme(String modelNme) {
        ModelNme = modelNme;
    }

    public String getBrandIDx() {
        return BrandIDx;
    }

    public void setBrandIDx(String brandIDx) {
        BrandIDx = brandIDx;
    }

    public String getMotorTyp() {
        return MotorTyp;
    }

    public void setMotorTyp(String motorTyp) {
        MotorTyp = motorTyp;
    }

    public String getRegisTyp() {
        return RegisTyp;
    }

    public void setRegisTyp(String regisTyp) {
        RegisTyp = regisTyp;
    }

    public String getEndOfLfe() {
        return EndOfLfe;
    }

    public void setEndOfLfe(String endOfLfe) {
        EndOfLfe = endOfLfe;
    }

    public String getEngineTp() {
        return EngineTp;
    }

    public void setEngineTp(String engineTp) {
        EngineTp = engineTp;
    }

    public String getHotItemx() {
        return HotItemx;
    }

    public void setHotItemx(String hotItemx) {
        HotItemx = hotItemx;
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
