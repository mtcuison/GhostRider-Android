package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationRecipient;

@Dao
public interface DNotificationDetail {

    @Insert
    void Insert(ENotificationRecipient foVal);

    @Update
    void Update(ENotificationRecipient foVal);

//    @Query("SELECT * FROM Notification_Info_Recepient")
//    ENotificationMaster GetNotificationDetail();

}
