package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;

import java.util.List;

@Dao
public interface DImageInfo {

    @Insert
    void insert(EImageInfo imageInfo);

    @Update
    void update(EImageInfo imageInfo);

    @Query("UPDATE Image_Information " +
            "SET sTransNox =:TransNox, " +
            "cSendStat = '1', " +
            "dSendDate =:DateModifield " +
            "WHERE sTransNox =:oldTransNox")
    void updateImageInfo(String TransNox, String DateModifield, String oldTransNox);

    @Query("SELECT * FROM Image_Information " +
            "WHERE sSourceNo = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master " +
            "ORDER BY dTransact DESC LIMIT 1)")
    LiveData<EImageInfo> getImageInfo();

    /**
     *
     * @return returns a LiveData List of all unsent image info...
     */
    @Query("SELECT * FROM Image_Information " +
            "WHERE cSendStat = 0 " +
            "AND sSourceNo = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master " +
            "ORDER BY dTransact DESC LIMIT 1)")
    LiveData<List<EImageInfo>> getUnsentImageInfoList();

    @Query("SELECT * FROM Image_Information")
    LiveData<List<EImageInfo>> getImageInfoList();

    @Update
    void updateImageInfo(EImageInfo imageInfo);


}
