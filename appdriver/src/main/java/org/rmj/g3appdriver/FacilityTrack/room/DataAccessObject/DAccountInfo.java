package org.rmj.g3appdriver.FacilityTrack.room.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;

import org.rmj.g3appdriver.FacilityTrack.room.Entities.EPersonnelInfo;

@Dao
public interface DAccountInfo {

    @Insert
    void Save(EPersonnelInfo foVal);


}
