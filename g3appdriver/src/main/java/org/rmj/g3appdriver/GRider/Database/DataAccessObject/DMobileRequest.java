package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;

import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;

import java.util.List;

@Dao
public interface DMobileRequest {

    @Insert
    void insert(List<EMobileUpdate> mobileUpdate);
}
