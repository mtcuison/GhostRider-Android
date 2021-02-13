package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;

import java.util.List;

@Dao
public interface DAddressRequest {

    @Insert
    void insert(EAddressUpdate addressUpdate);

    @Query("DELETE FROM Address_Update_Request WHERE sTransNox = :sTransNox")
    void deleteAddressInfo(String sTransNox);

    @Query("SELECT * FROM Address_Update_Request")
    LiveData<List<EAddressUpdate>> getAddressRequestList();
}
