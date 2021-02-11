package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;

import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;

import java.util.List;

@Dao
public interface DAddressRequest {

    @Insert
    void insert(List<EAddressUpdate> addressUpdate);
}
