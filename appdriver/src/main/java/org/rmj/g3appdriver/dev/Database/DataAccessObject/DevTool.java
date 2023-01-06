package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;

@Dao
public interface DevTool {

    @Query("SELECT * FROM User_Info_Master")
    LiveData<EEmployeeInfo> GetUserInfo();

    @Query("SELECT * FROM User_Info_Master")
    EEmployeeInfo GetUser();

    @Update
    void Update(EEmployeeInfo args);
}
