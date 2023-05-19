package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.EGanadoOnline;

@Dao
public interface DGanadoOnline {

    @Insert
    void Save(EGanadoOnline foVal);

    @Update
    void Update(EGanadoOnline foVal);
}
