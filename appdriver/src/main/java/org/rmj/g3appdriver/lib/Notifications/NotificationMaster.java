package org.rmj.g3appdriver.lib.Notifications;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotificationMaster;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;

public class NotificationMaster {
    private static final String TAG = NotificationMaster.class.getSimpleName();

    private final Application instance;

    private final DNotificationMaster poDao;

    private String message;

    public NotificationMaster(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).nMasterDao();
    }

    public String getMessage() {
        return message;
    }

    public boolean SaveNotification(RemoteMessage remoteMessage){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
