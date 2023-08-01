/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationMaster;
import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationUser;

import java.util.List;

@Dao
public interface DNotificationReceiver {

    @Insert
    void insert(ENotificationMaster notificationMaster);

    @Insert
    void insert(ENotificationRecipient notificationRecipient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ENotificationUser notificationUser);

    @Update
    void update(ENotificationMaster notificationMaster);

    @Update
    void update(ENotificationRecipient notificationRecipient);

    @Update
    void update(ENotificationUser notificationUser);

    @Query("UPDATE Notification_Info_Recepient SET cMesgStat =:status WHERE sTransNox =:MessageID")
    void updateNotificationStatusFromOtherDevice(String MessageID, String status);

    @Query("SELECT COUNT(*) FROM Notification_Info_Recepient a LEFT JOIN Notification_Info_Master b ON a.sTransNox = b.sMesgIDxx WHERE b.sMesgIDxx =:MessageID")
    int getNotificationIfExist(String MessageID);

    @Query("SELECT COUNT(*) FROM Notification_Info_Master WHERE sMesgIDxx=:TransNox")
    int CheckNotificationIfExist(String TransNox);

    @Query("SELECT * FROM Notification_Info_Recepient WHERE sTransNox =:MessageID")
    ENotificationRecipient GetNotification(String MessageID);
    @Query("SELECT * FROM Notification_User WHERE sUserIDxx=:fsVal")
    ENotificationUser CheckIfUserExist(String fsVal);

    @Query("SELECT COUNT(*) FROM Notification_Info_Master")
    int GetNotificationCountForID();

    @Query("SELECT sUserIDxx FROM User_Info_Master")
    String GetUserID();

    @Query("UPDATE Notification_Info_Recepient SET " +
            "dLastUpdt =:DateTime, " +
            "cMesgStat = '2', " +
            "cStatSent = '0' " +
            "WHERE sTransNox =:MessageID")
    void updateRecipientRecievedStatus(String MessageID, String DateTime);

    @Query("SELECT a.sMesgIDxx AS MesgIDxx," +
            "a.sAppSrcex AS AppSrcex," +
            "b.dReceived AS Received," +
            "a.sMessagex AS Messagex," +
            "a.sCreatrID AS CreatrID," +
            "a.sCreatrNm AS CreatrNm," +
            "b.cMesgStat AS MesgStat," +
            "a.sMsgTitle AS MsgTitle," +
            "a.sMsgTypex AS MsgTypex " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM Client_Info_Master)")
    LiveData<List<ClientNotificationInfo>> getClientNotificationList();

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrID AS CreatrID, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received, " +
            "b.cMesgStat AS Status " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sMsgTypex == '00000' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM User_Info_Master)")
    LiveData<List<UserNotificationInfo>> getUserMessageList();
//
//    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
//            "a.sMsgTitle AS MsgTitle, " +
//            "a.sCreatrID AS CreatrID, " +
//            "a.sCreatrNm AS CreatrNm, " +
//            "a.sMessagex AS Messagex, " +
//            "b.dReceived AS Received " +
//            "FROM Notification_Info_Master a " +
//            "LEFT JOIN Notification_Info_Recepient b " +
//            "ON a.sMesgIDxx = b.sTransNox " +
//            "WHERE b.cMesgStat <> '5' " +
//            "AND a.sMsgTypex == '00000' " +
//            "AND b.sRecpntID = (SELECT sUserIDxx FROM User_Info_Master) " +
//            "AND a.sCreatrID=:SenderID")
//    LiveData<List<UserNotificationInfo>> getUserMessageListFromSender(String SenderID);
//
//    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
//            "a.sMsgTitle AS MsgTitle, " +
//            "a.sCreatrID AS CreatrID, " +
//            "a.sCreatrNm AS CreatrNm, " +
//            "a.sMessagex AS Messagex, " +
//            "b.dReceived AS Received " +
//            "FROM Notification_Info_Master a " +
//            "LEFT JOIN Notification_Info_Recepient b " +
//            "ON a.sMesgIDxx = b.sTransNox " +
//            "WHERE b.cMesgStat <> '5'" +
//            "AND a.sMsgTypex == '00000' " +
//            "AND a.sMsgTypex == '00000' " +
//            "AND b.sRecpntID = (SELECT sUserIDxx FROM User_Info_Master) " +
//            "GROUP BY a.sCreatrID")
//    LiveData<List<UserNotificationInfo>> getUserMessageListGroupByUser();

    @Query("SELECT COUNT(*) FROM Notification_Info_Recepient a " +
            "LEFT JOIN Notification_Info_Master b " +
            "ON a.sTransNox = b.sMesgIDxx " +
            "WHERE a.cMesgStat = '2' AND a.sRecpntID = (" +
            "SELECT sUserIDxx FROM User_Info_Master) " +
            "AND b.sMsgTypex == '00000' AND b.sMsgTypex <> '00006'")
    LiveData<Integer> getUnreadMessagesCount();

    @Query("SELECT COUNT(*) FROM Notification_Info_Recepient a " +
            "LEFT JOIN Notification_Info_Master b " +
            "ON a.sTransNox = b.sMesgIDxx " +
            "WHERE a.cMesgStat = '2' " +
            "AND b.sDataSndx == '' " +
            "AND a.sRecpntID = (" +
            "SELECT sUserIDxx FROM User_Info_Master)")
    LiveData<Integer> getUnreadNotificationsCount();

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrID AS CreatrID, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMsgTypex AS MsgType, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received, " +
            "c.sEmailAdd AS Receipt, " +
            "b.cMesgStat AS Status " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "LEFT JOIN User_Info_Master c " +
            "ON b.sRecpntID = c.sUserIDxx " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sDataSndx == '' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM User_Info_Master) " +
            "ORDER BY b.dReceived DESC ")
    LiveData<List<UserNotificationInfoWithRcpt>> getUserNotificationList();

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrID AS CreatrID, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMsgTypex AS MsgType, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received, " +
            "c.sEmailAdd AS Receipt, " +
            "b.cMesgStat AS Status " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "LEFT JOIN User_Info_Master c " +
            "ON b.sRecpntID = c.sUserIDxx " +
            "WHERE a.sMesgIDxx =:MessageID")
    LiveData<UserNotificationInfoWithRcpt> getNotificationForViewing(String MessageID);

    @Query("SELECT a.sMesgIDxx FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox WHERE a.sCreatrID =:SenderID " +
            "AND b.cMesgStat == 2 AND a.sMsgTypex == '00000'")
    List<String> getReadMessagesIDFromSender(String SenderID);

    @Query("UPDATE Notification_Info_Recepient SET " +
            "dReadxxxx =:DateTime, " +
            "cMesgStat = '3', " +
            "cStatSent = '0' " +
            "WHERE sTransNox =:MessageID")
    void updateNotificationReadStatus(String MessageID, String DateTime);

    @Query("SELECT cMesgStat FROM Notification_Info_Recepient WHERE sTransNox =:MessageID")
    String getNotificationStatus(String MessageID);

    @Query("UPDATE Notification_Info_Recepient SET " +
            "dReadxxxx =:DateTime, " +
            "cMesgStat = '5', " +
            "cStatSent = '0' " +
            "WHERE sTransNox =:MessageID")
    void updateNotificationDeleteStatus(String MessageID, String DateTime);

    @Query("SELECT dReadxxxx FROM Notification_Info_Recepient WHERE sTransNox =:MessageID")
    String getReadMessageTimeStamp(String MessageID);

    @Query("SELECT dTimeStmp FROM Notification_Info_Recepient WHERE sTransNox =:MessageID")
    String getDeleteMessageTimeStamp(String MessageID);

    @Query("UPDATE Notification_Info_Recepient SET " +
            "dReadxxxx =:DateTime, " +
            "cMesgStat = '3', " +
            "cStatSent = '0' " +
            "WHERE sTransNox =(SELECT sMesgIDxx FROM Notification_Info_Master WHERE sCreatrID=:SenderID) " +
            "AND cMesgStat == '2'")
    void updateMessageReadStatus(String SenderID, String DateTime);

    @Query("UPDATE Notification_Info_Recepient SET " +
            "dLastUpdt =:dateTime, " +
            "dReceived =:dateTime, " +
            "cMesgStat =:Status, " +
            "cStatSent = '1' " +
            "WHERE sTransNox =:MessageID")
    void UpdateSentResponseStatus(String MessageID, String Status, String dateTime);

    @Query("SELECT * FROM Notification_Info_Master WHERE sMesgIDxx=:fsVal")
    ENotificationMaster CheckIfMasterExist(String fsVal);

    @Insert
    void SaveBranchOpening(EBranchOpenMonitor foVal);

    @RawQuery
    String ExecuteTableUpdateQuery(SupportSQLiteQuery query);

    class ClientNotificationInfo{
        public String MesgIDxx;
        public String AppSrcex;
        public String Received;
        public String Messagex;
        public String CreatrID;
        public String CreatrNm;
        public String MesgStat;
        public String MsgTitle;
        public String MsgTypex;
    }

    class UserNotificationInfo{
        public String MesgIDxx;
        public String MsgTitle;
        public String CreatrID;
        public String CreatrNm;
        public String Messagex;
        public String Received;
        public String Status;
    }

    class UserNotificationInfoWithRcpt{
        public String MesgIDxx;
        public String MsgTitle;
        public String CreatrID;
        public String CreatrNm;
        public String MsgType;
        public String Receipt;
        public String Messagex;
        public String Received;
        public String Status;
    }
}


