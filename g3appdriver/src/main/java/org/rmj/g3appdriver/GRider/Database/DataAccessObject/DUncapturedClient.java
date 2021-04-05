package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import org.rmj.g3appdriver.GRider.Database.Entities.EUncapturedClient;

import java.util.List;

@Dao
public interface DUncapturedClient {

    @Insert
    void insert(EUncapturedClient uncapturedClient);

    @Delete
    void delete(EUncapturedClient uncapturedClient);

    LiveData<List<EUncapturedClient>> getUncapturedClientList();
}
