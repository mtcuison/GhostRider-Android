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

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationUser;

import java.util.List;

@Dao
public interface DNotifications {

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

    @Query("UPDATE Notification_Info_Recepient SET " +
            "dReadxxxx =:DateTime, " +
            "dLastUpdt =:DateTime, " +
            "cMesgStat =:Status, " +
            "cStatSent = '0' " +
            "WHERE sTransNox =:MessageID")
    void updateRecipientStatus(String MessageID, String DateTime, String Status);

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
            "AND a.sMsgTypex <> '00000' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM Client_Info_Master)")
    LiveData<List<ClientNotificationInfo>> getClientNotificationList();

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sMsgTypex == '00000' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM User_Info_Master)")
    LiveData<List<UserNotificationInfo>> getUserMessageList();

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sMsgTypex == '00000' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM User_Info_Master) " +
            "AND a.sCreatrID=:SenderID")
    LiveData<List<UserNotificationInfo>> getUserMessageListFromSender(String SenderID);

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sMsgTypex == '00000' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM User_Info_Master) " +
            "GROUP BY a.sCreatrID")
    LiveData<List<UserNotificationInfo>> getUserMessageListGroupByUser();

    @Query("SELECT a.sMesgIDxx AS MesgIDxx, " +
            "a.sMsgTitle AS MsgTitle, " +
            "a.sCreatrNm AS CreatrNm, " +
            "a.sMessagex AS Messagex, " +
            "b.dReceived AS Received " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b " +
            "ON a.sMesgIDxx = b.sTransNox " +
            "WHERE b.cMesgStat <> '5' " +
            "AND a.sMsgTypex <> '00000' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM User_Info_Master)")
    LiveData<List<UserNotificationInfo>> getUserNotificationList();

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
        public String CreatrNm;
        public String Messagex;
        public String Received;
    }
}


