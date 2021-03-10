package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EBankInfo;

import java.util.List;

@Dao
public interface DBankInfo {

    @Query("SELECT * FROM Bank_Info WHERE cRecdStat = 1")
    LiveData<List<EBankInfo>> getBankInfoList();

    @Query("SELECT sBankName FROM Bank_Info WHERE cRecdStat = 1")
    LiveData<String[]> getBankNameList();
}
