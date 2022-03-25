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

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;

import java.util.List;

@Dao
public interface DDCP_Remittance {

    @Insert
    void insert(EDCP_Remittance remittance);

    @Query("SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact")
    String getMasterTransNox(String dTransact);

    @Query("SELECT COUNT(nEntryNox) + 1 FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact)")
    String getRemittanceEntry(String dTransact);

    @Query("UPDATE LR_DCP_Remittance SET cSendStat = 1, dDateSent =:DateSend WHERE sTransNox =:TransNox AND nEntryNox =:EntryNox")
    void updateSendStatus(String DateSend, String TransNox, String EntryNox);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dReferDte =:dTransact)")
    LiveData<String> getTotalRemittedCollection(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dReferDte =:dTransact) AND cPaymForm = '0'")
    LiveData<String> getTotalCashRemittedCollection(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dReferDte =:dTransact) AND cPaymForm = '1'")
    LiveData<String> getTotalCheckRemittedCollection(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dReferDte =:dTransact) AND cRemitTyp = '0'")
    LiveData<String> getTotalBranchRemittedCollection(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dReferDte =:dTransact) AND cRemitTyp = '1'")
    LiveData<String> getTotalBankRemittedCollection(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dReferDte =:dTransact) AND cRemitTyp = '2'")
    LiveData<String> getTotalOtherRemittedCollection(String dTransact);

    @Query("SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact) " +
            "AND sCheckNox == '' AND sCheckDte == '' AND sCheckAct == ''")
    String getCollectedCash(String dTransact);

    @Query("SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact) " +
            "AND sCheckNox == '' AND sCheckDte == '' AND sCheckAct == ''")
    LiveData<String> getTotalCollectedCash(String dTransact);

    @Query("SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact) " +
            "AND sCheckNox <> '' AND sCheckDte <> '' AND sCheckAct <> ''")
    String getCollectedCheck(String dTransact);

    @Query("SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact) " +
            "AND sCheckNox <> '' AND sCheckDte <> '' AND sCheckAct <> ''")
    LiveData<String> getTotalCollectedCheck(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact) " +
            "AND cPaymForm ='1'")
    String getRemittedCheck(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact) " +
            "AND cPaymForm ='0'")
    String getRemittedCash(String dTransact);

    @Query("SELECT * FROM LR_DCP_Remittance WHERE sTransNox = (" +
            "SELECT sTransNox FROM LR_DCP_Collection_Master WHERE cSendStat != '1')")
    EDCP_Remittance getDCPRemittance();

    @Query("INSERT INTO LR_DCP_Remittance(" +
            "sTransNox, " +
            "nEntryNox, " +
            "dTransact, " +
            "cSendStat, " +
            "nAmountxx)" +
            "VALUES (" +
            "(SELECT sTransNox FROM LR_DCP_Collection_Master ORDER BY dReferDte DESC LIMIT 1)," +
            "(SELECT COUNT(nEntryNox) + 1 FROM LR_DCP_Remittance WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master ORDER BY dReferDte DESC LIMIT 1)), " +
            ":dTransact, " +
            "'0', " +
            "'0')")
    void initializeCurrentDayRemittanceField(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance WHERE sTransNox = (" +
            "SELECT sTransNox FROM LR_DCP_Collection_Master WHERE cSendStat != '1')")
    double getRemittedCollection();

    @Query("SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail WHERE sTransNox = (" +
            "SELECT sTransNox FROM LR_DCP_Collection_Master WHERE cSendStat != '1') " +
            "AND sRemCodex = 'PAY'")
    double getCollectedPayments();

    @Query("SELECT sTransNox FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact)")
    String checkRemittanceExist(String dTransact);

    @Query("SELECT (SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail WHERE sTransNox = (" +
            "SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact) " +
            "AND sCheckNox <> '' AND sCheckAct <> '' AND sCheckDte <> '') - " +
            "(SELECT SUM(nAmountxx) FROM LR_DCP_Remittance WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dReferDte =:dTransact)" +
            "AND cPaymForm = '1') AS CHECK_ON_HAND")
    LiveData<String> getCheckOnHand(String dTransact);

    @Query("SELECT (SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail WHERE sTransNox = (" +
            "SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact)" +
            "AND sCheckNox == '' AND sCheckAct == '' AND sCheckDte == '') - " +
            "(SELECT SUM(nAmountxx) FROM LR_DCP_Remittance WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dReferDte =:dTransact)" +
            "AND cPaymForm = '0') AS CASH_ON_HAND")
    LiveData<String> getCashOnHand(String dTransact);

    @Query("SELECT * FROM LR_DCP_Remittance WHERE cSendStat != '1'")
    List<EDCP_Remittance> getUnsentRemittanceData();
}
