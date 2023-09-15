package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EAddressInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EMobileInfo;

import java.util.List;

@Dao
public interface DMobileAddressInfo {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void SaveAddress(EAddressInfo foVal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void SaveMobile(EMobileInfo foVal);

    @Update
    void UpdateAddress(EAddressInfo foVal);

    @Update
    void UpdateMobile(EMobileInfo foVal);

    @Query("SELECT * FROM Address_Update_Request")
    LiveData<List<EAddressInfo>> GetAddressInfoList();

    @Query("SELECT * FROM App_User_Mobile")
    LiveData<List<EMobileInfo>> GetMobileInfoList();

    @Query("SELECT * FROM Address_Update_Request WHERE sTransNox =:fsTransNo")
    LiveData<EAddressInfo> GetAddressInfo(String fsTransNo);

    @Query("SELECT * FROM App_User_Mobile WHERE sUserIDxx =:fsTransNo")
    LiveData<EMobileInfo> GetMobileInfo(String fsTransNo);

    @Query("UPDATE Address_Update_Request SET sTransNox =:fsNewTran WHERE sTransNox =:fsOldTran")
    void UpdateNewAddressID(String fsOldTran, String fsNewTran);

    @Query("UPDATE App_User_Mobile SET sUserIDxx =:fsNewTran WHERE sUserIDxx =:fsOldTran")
    void UpdateNewContactID(String fsOldTran, String fsNewTran);
}
