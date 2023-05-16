package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EServiceInfo;

import java.util.List;

@Dao
public interface DServiceInfo {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EServiceInfo eServiceInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EServiceInfo> eServiceInforInfoList);

    @Update
    void update(EServiceInfo eServiceInforInfo);

    @Query("SELECT sCardNmbr FROM Gcard_App_Master WHERE cActvStat = '1'")
    String GetGCardNumber();

    @Query("SELECT S.* FROM MC_Service as S " +
            "LEFT JOIN GCard_App_Master as G " +
            "ON S.sGCardNox = G.sGCardNox " +
            "WHERE G.cActvStat = '1'")
    LiveData<EServiceInfo> getActiveServiceInfo();
}
