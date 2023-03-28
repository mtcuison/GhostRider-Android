package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.lib.Notifications.Obj.Notification;

import java.util.List;

@Dao
public interface DNotification {

    @Query("SELECT " +
            "a.sMesgIDxx, " +
            "a.sCreatrNm, " +
            "a.sMsgTitle, " +
            "a.sMessagex, " +
            "b.dReceived, " +
            "b.cMesgStat " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.sRecpntID = (SELECT sUserIDxx FROM user_info_master) " +
            "AND a.sCreatrID = 'SYSTEM'" +
            "AND a.sMsgTitle <> 'Branch Opening'" +
            "ORDER BY b.dReceived DESC")
    LiveData<List<NotificationListDetail>> GetNotificationList();

    @Query("SELECT * FROM Notification_Info_Master WHERE sMesgIDxx =:args")
    LiveData<ENotificationMaster> GetNotificationMaster(String args);

    @Query("SELECT * FROM Notification_Info_Recepient WHERE sTransNox =:args")
    LiveData<ENotificationRecipient> GetNotificationDetail(String args);

    class NotificationListDetail{
        public String sMesgIDxx;
        public String sCreatrNm;
        public String sMsgTitle;
        public String sMessagex;
        public String dReceived;
        public String cMesgStat;
    }
}
