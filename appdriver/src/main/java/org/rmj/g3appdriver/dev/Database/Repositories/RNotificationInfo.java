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

package org.rmj.g3appdriver.dev.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationUser;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConstants;

import java.util.List;

public class RNotificationInfo {
    private final Application instance;
    private final DNotifications notificationDao;
    private final LiveData<List<DNotifications.ClientNotificationInfo>> clientNotificationList;
    private final LiveData<List<DNotifications.UserNotificationInfoWithRcpt>> userNotificationList;
    private final LiveData<List<DNotifications.UserNotificationInfo>> userMessageList;

    public RNotificationInfo(Application application){
        this.instance = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        notificationDao = GGCGriderDB.NotificationDao();
        clientNotificationList = notificationDao.getClientNotificationList();
        userNotificationList = notificationDao.getUserNotificationList();
        userMessageList = notificationDao.getUserMessageList();
    }

    public LiveData<List<DNotifications.ClientNotificationInfo>> getClientNotificationList() {
        return clientNotificationList;
    }

    public LiveData<List<DNotifications.UserNotificationInfo>> getUserMessageList(){
        return userMessageList;
    }

    public List<String> getReadMessagesIDFromSender(String SenderID){
        return notificationDao.getReadMessagesIDFromSender(SenderID);
    }

    public void updateNotificationReadStatus(String SenderID){
        notificationDao.updateNotificationReadStatus(SenderID, new AppConstants().DATE_MODIFIED());
    }

    public String getNotificationStatus(String MessageID){
        return notificationDao.getNotificationStatus(MessageID);
    }

    public void updateNotificationDeleteStatus(String MessageID){
        notificationDao.updateNotificationDeleteStatus(MessageID, new AppConstants().DATE_MODIFIED());
    }

    public String getReadMessageTimeStamp(String MessageID){
        return notificationDao.getReadMessageTimeStamp(MessageID);
    }

    public String getDeleteMessageTimeStamp(String MessageID){
        return notificationDao.getDeleteMessageTimeStamp(MessageID);
    }

    public LiveData<List<DNotifications.UserNotificationInfo>> getUserMessageListFromSender(String SenderID){
        return notificationDao.getUserMessageListFromSender(SenderID);
    }

    public LiveData<List<DNotifications.UserNotificationInfo>> getUserMessageListGroupByUser(){
        return notificationDao.getUserMessageListGroupByUser();
    }

    public LiveData<Integer> getUnreadMessagesCount(){
        return notificationDao.getUnreadMessagesCount();
    }

    public LiveData<Integer> getUnreadNotificationsCount(){
        return notificationDao.getUnreadNotificationsCount();
    }

    public LiveData<DNotifications.UserNotificationInfoWithRcpt>getNotificationForViewing(String MessageID){
        return notificationDao.getNotificationForViewing(MessageID);
    }

    public LiveData<List<DNotifications.UserNotificationInfoWithRcpt>> getUserNotificationList() {
        return userNotificationList;
    }

    public void insertNotificationInfo(ENotificationMaster notificationMaster,
                                       ENotificationRecipient notificationRecipient,
                                       ENotificationUser notificationUser){
        notificationDao.insert(notificationMaster);
        notificationDao.insert(notificationRecipient);
        notificationDao.insert(notificationUser);
    }

    public void updateNotificationStatusFromOtherDevice(String MessageID, String status){
        notificationDao.updateNotificationStatusFromOtherDevice(MessageID, status);
    }

    public int getNotificationIfExist(String MessageID){
        return notificationDao.getNotificationIfExist(MessageID);
    }

    public void updateRecipientRecievedStat(String messageID){
        notificationDao.updateRecipientRecievedStatus(messageID, new AppConstants().DATE_MODIFIED());
    }

    public void updateMessageReadStatus(String SenderID){
        notificationDao.updateMessageReadStatus(SenderID, new AppConstants().DATE_MODIFIED());
    }
}
