package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;

@Dao
public interface DImageInfo {

    @Insert
    void insert(EImageInfo imageInfo);

    @Query("SELECT * FROM Image_Information WHERE sTransNox =:sTransNox")
    LiveData<EImageInfo> getImageInfo(String sTransNox);
}
