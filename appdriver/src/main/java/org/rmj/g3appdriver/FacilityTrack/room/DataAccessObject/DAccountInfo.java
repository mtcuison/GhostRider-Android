package org.rmj.g3appdriver.FacilityTrack.room.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;

import org.rmj.guanzongroup.appdriver.Data.Entity.EAccountInfo;

@Dao
public interface DAccountInfo {

    @Insert
    void Save(EAccountInfo foVal);
}
