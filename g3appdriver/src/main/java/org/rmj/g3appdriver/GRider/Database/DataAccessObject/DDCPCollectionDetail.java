package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;

import java.util.List;

@Dao
public interface DDCPCollectionDetail {

    @Insert
    void insert(EDCPCollectionDetail collectionDetail);

    @Update
    void update(EDCPCollectionDetail collectionDetail);

    @Delete
    void delete(EDCPCollectionDetail collectionDetail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EDCPCollectionDetail> collectionDetails);

    @Query("SELECT * FROM LR_DCP_Collection_Detail")
    LiveData<List<EDCPCollectionDetail>> getCollectionDetailList();
}
