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

    @Query("UPDATE LR_DCP_Collection_Detail " +
            "SET cSendStat='1', dModified=:DateEntry " +
            "WHERE sTransNox =:TransNox " +
            "AND nEntryNox =:EntryNox")
    void updateCollectionDetailStatus(String TransNox, String EntryNox, String DateEntry);

    @Delete
    void delete(EDCPCollectionDetail collectionDetail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EDCPCollectionDetail> collectionDetails);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE cTranStat = 0")
    LiveData<List<EDCPCollectionDetail>> getCollectionDetailList();

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE cTranStat = 1 AND cSendStat == 0")
    LiveData<List<EDCPCollectionDetail>> getCollectionDetailLog();

    @Query("SELECT * FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = :TransNox " +
            "AND nEntryNox = :EntryNox")
    LiveData<EDCPCollectionDetail> getCollectionDetail(String TransNox, String EntryNox);

    @Query("SELECT * FROM LR_DCP_Collection_Detail ORDER BY nEntryNox DESC LIMIT 1")
    LiveData<EDCPCollectionDetail> getCollectionLastEntry();

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox =:TransNox AND sAcctNmbr =:AccountNo")
     LiveData<EDCPCollectionDetail> getDuplicateAccountEntry(String TransNox, String AccountNo);

    @Query("UPDATE LR_DCP_Collection_Detail " +
            "SET sImageNme=:ImageID " +
            "WHERE sAcctNmbr =:AccountNo " +
            "AND sTransNox = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master " +
            "ORDER BY dTransact DESC LIMIT 1)")
    void updateCustomerDetailImage(String ImageID, String AccountNo);
}
