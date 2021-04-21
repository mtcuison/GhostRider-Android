package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.ERemittanceAccounts;

import java.util.List;

@Dao
public interface DRemittanceAccounts {

    @Insert
    void insertBulkData(List<ERemittanceAccounts> remittanceAccounts);

    @Query("SELECT * FROM Collection_Account_Remittance")
    List<ERemittanceAccounts> getRemittanceAccountsIfExist();

    @Query("SELECT * FROM Collection_Account_Remittance " +
            "WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    LiveData<ERemittanceAccounts> getDefaultRemittanceAccount();

    @Query("SELECT * FROM Collection_Account_Remittance " +
            "WHERE sBranchCd = (SELECT sBranchCd FROM USER_INFO_MASTER) " +
            "AND sActNumbr NOT LIKE '____GK%'")
    LiveData<List<ERemittanceAccounts>> getRemittanceBankAccountsList();

    @Query("SELECT * FROM Collection_Account_Remittance " +
            "WHERE sBranchCd = (SELECT sBranchCd FROM USER_INFO_MASTER) " +
            "AND sActNumbr LIKE '____GK%'")
    LiveData<List<ERemittanceAccounts>> getRemittanceOtherAccountsList();
}
