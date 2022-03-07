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

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ECashCount;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;

import java.util.List;

@Dao
public interface DCashCount {
    @Insert
    void insertCashCount(ECashCount cashCount);

    @Update
    void updateCashCount(ECashCount cashCount);

    @Delete
    void delete(ECashCount cashCount);

    @Query("SELECT * FROM Cash_Count_Master WHERE sTransNox =:TransNox")
    LiveData<ECashCount> getCashCounDetetail(String TransNox);

    @Query("SELECT * FROM Cash_Count_Master WHERE sTransNox =:TransNox")
    List<ECashCount> getDuplicateTransNox(String TransNox);

    @Query("UPDATE Cash_Count_Master SET " +
            "sSendStat = 1 " +
            "WHERE sTransNox =:TransNox ")
    void UpdateByTransNox(String TransNox);

    @Query("SELECT * FROM Cash_Count_Master WHERE sSendStat <> '1'")
    List<ECashCount> getAllUnsentCashCountEntries();

    @Query("SELECT a.*, " +
            "b.sBranchNm FROM " +
            "Cash_Count_Master a LEFT JOIN " +
            "Branch_Info b ON " +
            "a.sBranchCd = b.sBranchCd")
    LiveData<List<CashCountLog>> getCashCountLog();

    class CashCountLog{
        public String sTransNox;
        public String sBranchCd;
        public String dTransact;
        public String nCn0001cx;
        public String nCn0005cx;
        public String nCn0010cx;
        public String nCn0025cx;
        public String nCn0050cx;
        public String nCn0001px;
        public String nCn0005px;
        public String nCn0010px;
        public String nNte0020p;
        public String nNte0050p;
        public String nNte0100p;
        public String nNte0200p;
        public String nNte0500p;
        public String nNte1000p;
        public String sPettyAmt;
        public String sORNoxxxx;
        public String sSINoxxxx;
        public String sPRNoxxxx;
        public String sCRNoxxxx;
        public String sORNoxNPt;
        public String sPRNoxNPt;
        public String sDRNoxxxx;
        public String dEntryDte;
        public String sReqstdBy;
        public String dModified;
        public String sSendStat;
        public String sBranchNm;
    }
}
