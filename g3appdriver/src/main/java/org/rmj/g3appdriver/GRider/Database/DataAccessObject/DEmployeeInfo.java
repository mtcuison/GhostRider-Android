package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;

@Dao
public interface DEmployeeInfo {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EEmployeeInfo employee);

    @Update
    void update(EEmployeeInfo employee);

    @Delete
    void delete(EEmployeeInfo employee);

    @Query("SELECT * FROM User_Info_Master")
    LiveData<EEmployeeInfo> getEmployeeInfo();

    @Query("DELETE FROM User_Info_Master")
    void deleteAllEmployeeInfo();

    @Query("SELECT sUserIDxx FROM User_Info_Master")
    String getUserID();

    @Query("SELECT sLogNoxxx FROM User_Info_Master")
    String getLogNumber();

    @Query("SELECT sClientID FROM User_Info_Master")
    String getClientID();

    @Query("SELECT * FROM User_Info_Master")
    Cursor getUserInfo();

    @Query("SELECT strftime('%H:%M:%S', 'now', 'localtime') - strftime('%H:%M:%S', dSessionx) AS Session FROM User_Info_Master")
    LiveData<Session> getSessionTime();

    class Session{
        public int Session;
    }
}
