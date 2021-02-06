package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;

import java.util.List;

@Dao
public interface DCreditApplication {

    @Insert
    void insert(ECreditApplication creditApplication);

    @Update
    void update(ECreditApplication creditApplication);

    @Delete
    void delete(ECreditApplication creditApplication);

    @Query("SELECT * FROM Credit_Online_Application")
    LiveData<List<ECreditApplication>> getAllCreditApplication();
}
