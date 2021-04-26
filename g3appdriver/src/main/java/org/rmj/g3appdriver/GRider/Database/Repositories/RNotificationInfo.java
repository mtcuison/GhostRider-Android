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

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationUser;

import java.util.List;

public class RNotificationInfo {
    private final DNotifications notificationDao;
    private final LiveData<List<DNotifications.ClientNotificationInfo>> clientNotificationList;
    private final LiveData<List<DNotifications.UserNotificationInfo>> userNotificationList;

    public RNotificationInfo(Application application){
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        notificationDao = GGCGriderDB.NotificationDao();
        clientNotificationList = notificationDao.getClientNotificationList();
        userNotificationList = notificationDao.getUserNotificationList();
    }

    public LiveData<List<DNotifications.ClientNotificationInfo>> getClientNotificationList() {
        return clientNotificationList;
    }

    public LiveData<List<DNotifications.UserNotificationInfo>> getUserNotificationList() {
        return userNotificationList;
    }

    public void insertNotificationInfo(ENotificationMaster notificationMaster,
                                       ENotificationRecipient notificationRecipient,
                                       ENotificationUser notificationUser){
        new InsertNotificationTask(notificationDao, notificationMaster, notificationRecipient, notificationUser).execute();
    }

    private static class InsertNotificationTask extends AsyncTask<Void, Void, Void>{
        private final DNotifications notificationDao;
        private final ENotificationMaster notificationMaster;
        private final ENotificationRecipient notificationRecipient;
        private final ENotificationUser notificationUser;

        public InsertNotificationTask(DNotifications notificationDao,
                                      ENotificationMaster notificationMaster,
                                      ENotificationRecipient notificationRecipient,
                                      ENotificationUser notificationUser) {
            this.notificationDao = notificationDao;
            this.notificationMaster = notificationMaster;
            this.notificationRecipient = notificationRecipient;
            this.notificationUser = notificationUser;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notificationDao.insert(notificationMaster);
            notificationDao.insert(notificationRecipient);
            notificationDao.insert(notificationUser);
            return null;
        }
    }
}
