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
