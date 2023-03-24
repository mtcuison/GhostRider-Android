package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;

import java.util.List;

@Dao
public interface DMessages {

    @Query("SELECT " +
            "a.*, " +
            "b.sMessagex, " +
            "c.dReceived " +
            "FROM Notification_User a " +
            "LEFT JOIN Notification_Info_Master b " +
            "ON a.sUserIDxx = b.sCreatrID " +
            "LEFT JOIN Notification_Info_Recepient c " +
            "ON b.sMesgIDxx = c.sTransNox " +
            "WHERE b.sCreatrID <> 'SYSTEM' " +
            "GROUP BY a.sUserIDxx " +
            "ORDER BY c.dReceived DESC")
    LiveData<List<MessageUsers>> GetUsersMessages();

    @Query("SELECT sUserName FROM Notification_User WHERE sUserIDxx =:args")
    LiveData<String> GetSenderName(String args);

    @Query("SELECT " +
            "a.sMessagex," +
            "b.dReadxxxx AS dMsgReadx," +
            "b.dReceived " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE a.sCreatrID =:args " +
            "ORDER BY b.dReceived DESC")
    LiveData<List<UserMessages>> GetUserMessages(String args);

    /**
     *
     * @param args pass the user id of the sender
     * @return returns message id of unread messages or unsent read response
     */
    @Query("SELECT a.sMesgIDxx " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE a.sCreatrID =:args " +
            "AND b.cMesgStat == '2' " +
            "OR a.sCreatrID =:args " +
            "AND b.cMesgStat == '3' " +
            "AND b.cStatSent == 0")
    List<String> GetUnreadMessagesID(String args);

    /**
     *
     * @param args pass the message id
     * @return returns the whole entity of ENotificationRecipient
     */
    @Query("SELECT * FROM Notification_Info_Recepient WHERE sTransNox=:args")
    ENotificationRecipient GetNotificationInfo(String args);

    class MessageUsers {
        public String sUserIDxx;
        public String sUserName;
        public String sMessagex;
        public String dReceived;
    }

    class UserMessages{
        public String sMessagex;
        public String dMsgReadx;
        public String dReceived;
    }
}
