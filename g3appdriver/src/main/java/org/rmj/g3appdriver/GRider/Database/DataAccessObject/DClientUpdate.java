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

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;

import java.util.List;

@Dao
public interface DClientUpdate {

    @Insert
    void insertClientUpdateInfo(EClientUpdate clientUpdate);

    @Query("SELECT * FROM Client_Update_Request " +
            "WHERE sSourceNo = (SELECT sTransNox FROM " +
            "LR_DCP_Collection_Master ORDER BY dTransact DESC LIMIT 1) " +
            "AND sClientID =:ClientID")
    LiveData<EClientUpdate> getClientUpdateInfo(String ClientID);

    @Query("UPDATE Client_Update_Request SET cSendStat = '1', dModified=:dModified " +
            "WHERE sSourceNo = (SELECT sTransNox FROM LR_DCP_Collection_Master ORDER BY dTransact DESC LIMIT 1) " +
            "AND sClientID =:ClientID")
    void updateClientInfoStatus(String ClientID, String dModified);

    @Query("UPDATE Client_Update_Request SET sImageNme =:ImageName " +
            "WHERE sSourceNo = (SELECT sTransNox FROM LR_DCP_Collection_Master ORDER BY dTransact DESC LIMIT 1) " +
            "AND sClientID =:ClientID")
    void updateClientInfoImage(String ClientID, String ImageName);
//
    @Query
    ("SELECT * FROM Client_Update_Request")
    LiveData<List<EClientUpdate>> selectClientUpdate();

    @Update
    void updateClientInfo(EClientUpdate clientUpdate);


    @Query("SELECT * FROM Client_Update_Request " +
            "WHERE sSourceNo =:sSourceNo " +
            "AND sDtlSrcNo =:sDtlSrcNo")
    LiveData<EClientUpdate> selectClient(String sSourceNo, String sDtlSrcNo);

}
