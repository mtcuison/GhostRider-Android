package org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EClientInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EUserInfo;

import java.util.List;

@Dao
public interface DUserInfo {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EUserInfo eUserInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EUserInfo> eUserInfoList);

    @Update
    void update(EUserInfo eUserInfo);

    @Query("SELECT * FROM Client_Profile_Info")
    EClientInfo getUserInfo();
}
