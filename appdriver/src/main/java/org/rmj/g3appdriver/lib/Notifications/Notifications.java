package org.rmj.g3appdriver.lib.Notifications;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;

public class Notifications {
    private static final String TAG = Notifications.class.getSimpleName();

    private final Application instance;

    private final

    private String message;

    public Notifications(Application instance) {
        this.instance = instance;
    }

    public String getMessage() {
        return message;
    }

    public boolean SaveNotification(RemoteMessage foVal){
        try{
            ENotificationMaster loMaster = null;

        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
