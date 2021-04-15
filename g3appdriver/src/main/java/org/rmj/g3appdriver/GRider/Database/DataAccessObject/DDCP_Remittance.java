package org.rmj.g3appdriver.GRider.Database.DataAccessObject;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;

@Dao
public interface DDCP_Remittance {

    @Insert
    void insert(EDCP_Remittance remittance);

    @Query("UPDATE LR_DCP_Remittance SET cSendStat = 1, dDateSent =:DateSend WHERE sTransNox =:TransNox")
    void updateSendStatus(String DateSend, String TransNox);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dTransact =:dTransact)")
    LiveData<String> getTotalRemittedCollection(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dTransact =:dTransact) AND cPaymForm = '0'")
    LiveData<String> getTotalCashRemittedCollection(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dTransact =:dTransact) AND cPaymForm = '1'")
    LiveData<String> getTotalCheckRemittedCollection(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dTransact =:dTransact) AND cRemitTyp = '0'")
    LiveData<String> getTotalBranchRemittedCollection(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dTransact =:dTransact) AND cRemitTyp = '1'")
    LiveData<String> getTotalBankRemittedCollection(String dTransact);

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dTransact =:dTransact) AND cRemitTyp = '2'")
    LiveData<String> getTotalOtherRemittedCollection(String dTransact);}
