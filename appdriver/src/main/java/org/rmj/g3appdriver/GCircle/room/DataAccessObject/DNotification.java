package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationMaster;
import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationRecipient;

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
            "AND a.sMsgTypex == '00000'" +
            "AND a.sMsgTitle NOT LIKE '%PAYSLIP%'" +
            "AND a.sCreatrID = 'SYSTEM'" +
            "AND a.sMsgTitle <> 'Branch Opening'" +
            "ORDER BY b.dReceived DESC")
    LiveData<List<NotificationListDetail>> GetNotificationList();

    @Query("SELECT COUNT(a.sMesgIDxx) " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.sRecpntID = (SELECT sUserIDxx FROM user_info_master) " +
            "AND a.sMsgTypex == '00000'" +
            "AND a.sCreatrID = 'SYSTEM'" +
            "AND a.sMsgTitle NOT LIKE '%PAYSLIP%'" +
            "AND a.sMsgTitle <> 'Branch Opening'" +
            "AND b.cMesgStat == '2'")
    LiveData<Integer> GetUnreadNotificationCount();

    @Query("SELECT * FROM Notification_Info_Master WHERE sMesgIDxx =:args")
    LiveData<ENotificationMaster> GetNotificationMaster(String args);

    @Query("SELECT * FROM Notification_Info_Recepient WHERE sTransNox =:args")
    LiveData<ENotificationRecipient> GetNotificationDetail(String args);

    @Query("SELECT COUNT(a.sTransNox) FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "AND a.sMsgTypex == '00000'" +
            "AND a.sMsgTitle <> 'Branch Opening'" +
            "WHERE b.sRecpntID = (SELECT sUserIDxx FROM User_Info_Master) " +
            "AND b.cMesgStat == '2'")
    LiveData<Integer> GetAllUnreadNotificationCount();

    class NotificationListDetail{
        public String sMesgIDxx;
        public String sCreatrNm;
        public String sMsgTitle;
        public String sMessagex;
        public String dReceived;
        public String cMesgStat;
    }
}
