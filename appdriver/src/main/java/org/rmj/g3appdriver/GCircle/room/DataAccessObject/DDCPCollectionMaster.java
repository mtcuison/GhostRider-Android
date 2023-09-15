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

package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionMaster;

import java.util.List;

@Dao
public interface DDCPCollectionMaster {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EDCPCollectionMaster collectionMaster);

    @Update
    void update(EDCPCollectionMaster collectionMaster);

    @Query("SELECT * FROM LR_DCP_Collection_Master")
    LiveData<List<EDCPCollectionMaster>> getCollectionMasterList();

    @Query("SELECT * FROM LR_DCP_Collection_Master ORDER BY dTransact DESC LIMIT 1")
    LiveData<EDCPCollectionMaster> getCollectionMaster();

    @Query("UPDATE LR_DCP_Collection_Master SET nEntryNox =:nEntryNox")
    void updateEntryMaster(int nEntryNox);

    @Query("UPDATE LR_DCP_Collection_Master " +
            "SET cSendStat = '1', " +
            "cTranStat = '2', " +
            "dSendDate =:dModified, " +
            "dModified =:dModified " +
            "WHERE sTransNox =:Transnox")
    void updateSentPostedDCPMaster(String Transnox, String dModified);

    @Query("SELECT * FROM LR_DCP_Collection_Master WHERE sTransNox =:TransNox")
    List<EDCPCollectionMaster> getCollectionMasterIfExist(String TransNox);

    @Query("SELECT * FROM LR_DCP_Collection_Master WHERE cSendStat IS NULL")
    EDCPCollectionMaster CheckIfHasCollection();

    @Query("SELECT * FROM LR_DCP_Collection_Master ORDER BY dReferDte DESC LIMIT 1")
    EDCPCollectionMaster getLastCollectionMaster();
}
