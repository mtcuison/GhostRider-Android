package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;

import java.util.List;

@Dao
public interface DImageInfo {

    @Insert
    void insert(EImageInfo imageInfo);

    @Query("SELECT * FROM Image_Information WHERE sTransNox =:sTransNox")
    LiveData<EImageInfo> getImageInfo(String sTransNox);

    /**
     *
     * @return returns a LiveData List of all unsent image info...
     */
    @Query("SELECT * FROM Image_Information WHERE cSendStat = 0")
    LiveData<List<EImageInfo>> getUnsentImageInfoList();

    @Query("SELECT * FROM Image_Information")
    LiveData<List<EImageInfo>> getImageInfoList();
}
