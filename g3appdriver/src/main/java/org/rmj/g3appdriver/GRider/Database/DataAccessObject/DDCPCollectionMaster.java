package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;

import java.util.List;

@Dao
public interface DDCPCollectionMaster {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EDCPCollectionMaster collectionMaster);

    @Update
    void update(EDCPCollectionMaster collectionMaster);

    @Delete
    void delete(EDCPCollectionMaster collectionMaster);

    @Query("SELECT * FROM LR_DCP_Collection_Master")
    LiveData<List<EDCPCollectionMaster>> getCollectionMasterList();

    @Query("SELECT * FROM LR_DCP_Collection_Master")
    LiveData<EDCPCollectionMaster> getCollectionMaster();
}
