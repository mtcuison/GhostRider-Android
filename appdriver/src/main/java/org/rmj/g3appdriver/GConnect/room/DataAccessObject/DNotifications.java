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

package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.ENotificationMaster;
import org.rmj.g3appdriver.GConnect.room.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.GConnect.room.Entities.ENotificationUser;

import java.util.List;

@Dao
public interface DNotifications {

    @Insert
    void insert(ENotificationMaster notificationMaster);

    @Insert
    void insert(ENotificationRecipient notificationRecipient);

    @Insert
    void insert(ENotificationUser notificationUser);

    @Query("SELECT sUserIDxx FROM Client_Profile_Info")
    String GetUserID();

    @Query("SELECT * FROM Notification_Info_Master WHERE sMesgIDxx=:fsVal")
    ENotificationMaster CheckIfMasterExist(String fsVal);

    @Query("SELECT * FROM Notification_Info_Recepient WHERE sTransNox=:fsVal")
    ENotificationRecipient CheckIfRecipientExist(String fsVal);

    @Query("SELECT * FROM Notification_User WHERE sUserIDxx=:fsVal")
    ENotificationUser CheckIfUserExist(String fsVal);

    @Update
    void update(ENotificationMaster notificationMaster);

    @Update
    void update(ENotificationRecipient notificationRecipient);

    @Update
    void update(ENotificationUser notificationUser);

    @Query("UPDATE Notification_Info_Recepient SET cMesgStat =:status WHERE sTransNox =:MessageID")
    void updateNotificationStatusFromOtherDevice(String MessageID, String status);

    @Query("SELECT COUNT(*) FROM Notification_Info_Master WHERE sMesgIDxx=:TransNox")
    int CheckNotificationIfExist(String TransNox);

    @Query("UPDATE Notification_Info_Recepient SET " +
            "dLastUpdt =:dateTime, " +
            "dReceived =:dateTime, " +
            "cMesgStat =:Status, " +
            "cStatSent = '1' " +
            "WHERE sTransNox =:MessageID")
    void UpdateSentResponseStatus(String MessageID, String Status, String dateTime);

    @Query("UPDATE Notification_Info_Recepient SET " +
            "dLastUpdt =:fsArgs, " +
            "dReceived =:fsArgs, " +
            "cMesgStat = '2', " +
            "cStatSent = '1' " +
            "WHERE sTransNox =:MessageID")
    void updateRecipientReceivedStatus(String MessageID, String fsArgs);

    @Query("UPDATE Notification_Info_Recepient SET " +
            "dLastUpdt =:fsArgs, " +
            "dReadxxxx =:fsArgs, " +
            "cMesgStat = '3', " +
            "cStatSent = '1' " +
            "WHERE sTransNox =:MessageID")
    void updateReadReceivedStatus(String MessageID, String fsArgs);

    @Query("SELECT a.sMesgIDxx AS MesgIDxx," +
            "a.sAppSrcex AS AppSrcex," +
            "b.dReceived AS Received," +
            "a.sMessagex AS Messagex," +
            "a.sCreatrID AS CreatrID," +
            "a.sCreatrNm AS CreatrNm," +
            "b.cMesgStat AS MesgStat," +
            "a.sMsgTitle AS MsgTitle," +
            "a.sMsgTypex AS MsgTypex, " +
            "a.sDataSndx AS DataInfo " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM Client_Profile_Info) " +
            "ORDER BY b.dReceived DESC")
    LiveData<List<ClientNotificationInfo>> getClientNotificationList();

    @Query("SELECT a.sMesgIDxx AS MesgIDxx," +
            "a.sAppSrcex AS AppSrcex," +
            "b.dReceived AS Received," +
            "a.sMessagex AS Messagex," +
            "a.sCreatrID AS CreatrID," +
            "a.sCreatrNm AS CreatrNm," +
            "b.cMesgStat AS MesgStat," +
            "a.sMsgTitle AS MsgTitle," +
            "a.sMsgTypex AS MsgTypex, " +
            "a.sDataSndx AS DataInfo " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE a.sMesgIDxx =:fsMesgID")
    LiveData<ClientNotificationInfo> GetNotificationInfo(String fsMesgID);

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrID AS CreatrID, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sMsgTypex == '00000' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM Client_Profile_Info)")
    LiveData<List<UserNotificationInfo>> getUserMessageList();

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrID AS CreatrID, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sMsgTypex == '00000' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM Client_Profile_Info) " +
            "AND a.sCreatrID=:SenderID")
    LiveData<List<UserNotificationInfo>> getUserMessageListFromSender(String SenderID);

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrID AS CreatrID, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5'" +
            "AND a.sMsgTypex == '00000' " +
            "AND a.sMsgTypex == '00000' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM Client_Profile_Info) " +
            "GROUP BY a.sCreatrID")
    LiveData<List<UserNotificationInfo>> getUserMessageListGroupByUser();

    @Query("SELECT COUNT(*) FROM Notification_Info_Recepient a " +
            "LEFT JOIN Notification_Info_Master b " +
            "ON a.sTransNox = b.sMesgIDxx " +
            "WHERE a.cMesgStat = '2' AND a.sRecpntID = (" +
            "SELECT sUserIDxx FROM Client_Profile_Info)")
    LiveData<Integer> getUnreadMessagesCount();

    @Query("SELECT COUNT(*) FROM Notification_Info_Recepient a " +
            "LEFT JOIN Notification_Info_Master b " +
            "ON a.sTransNox = b.sMesgIDxx " +
            "WHERE a.cMesgStat = '2' AND a.sRecpntID = (" +
            "SELECT sUserIDxx FROM Client_Profile_Info) " +
            "AND b.sMsgTypex <> '00000'")
    LiveData<Integer> getUnreadNotificationsCount();

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrID AS CreatrID, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sMsgTypex <> '00000' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM Client_Profile_Info)")
    LiveData<List<UserNotificationInfo>> getUserNotificationList();

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrID AS CreatrID, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sMsgTypex == '00003' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM Client_Profile_Info)")
    LiveData<List<UserNotificationInfo>> getPromotionsNotifications();
    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrID AS CreatrID, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sMsgTypex == '00008' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM Client_Profile_Info)")
    LiveData<List<UserNotificationInfo>> getPanaloNotifications();

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
    void updateRecipientReadStatus(String MessageID, String DateTime);

    @Query("SELECT dReadxxxx FROM Notification_Info_Recepient WHERE sTransNox =:MessageID")
    String getReadMessageTimeStamp(String MessageID);

    @Query("UPDATE Notification_Info_Recepient SET " +
            "dReadxxxx =:DateTime, " +
            "cMesgStat = '3', " +
            "cStatSent = '0' " +
            "WHERE sTransNox =(SELECT sMesgIDxx FROM Notification_Info_Master WHERE sCreatrID=:SenderID) " +
            "AND cMesgStat == '2'")
    void updateMessageReadStatus(String SenderID, String DateTime);

    @Query("SELECT COUNT(*) FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.sRecpntID = (SELECT sUserIDxx FROM Client_Profile_Info) " +
            "AND cMesgStat = '2'")
    int GetNotificationCount();

    @Query("SELECT COUNT(*) FROM Notification_Info_Master")
    int GetNotificationCountForID();

    @Query("SELECT a.sMesgIDxx, " +
            "b.dReceived, " +
            "a.sMessagex, " +
            "a.sCreatrID, " +
            "a.sCreatrNm, " +
            "b.sRecpntID, " +
            "b.cMesgStat, " +
            "a.sDataSndx " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.sRecpntID = (SELECT sUserIDxx FROM Client_Profile_Info) " +
            "AND a.sMsgTypex = '00000' " +
            "AND a.sCreatrID IS NULL " +
            "OR a.sCreatrID = ''")
    LiveData<List<RegularMessage>> GetRegularMessagesSystemNotif();

    class ClientNotificationInfo{
        public String MesgIDxx;
        public String AppSrcex;
        public String Received;
        public String Messagex;
        public String CreatrID;
        public String CreatrNm;
        public String MesgStat;
        public String DataInfo;
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
    }

    class RegularMessage{
        public String sMesgIDxx;
        public String dReceived;
        public String sMessagex;
        public String sRecpntID;
        public String sCreatrID;
        public String sCreatrNm;
        public String cMesgStat;
        public String sDataSndx;
    }
}


