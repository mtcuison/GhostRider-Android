package org.rmj.guanzongroup.ghostrider.notifications.Notifications.Regular;

import static org.rmj.guanzongroup.ghostrider.notifications.Notifications.Panalo.PanaloNotification.NotificationID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.guanzongroup.ghostrider.notifications.Etc.iNotificationUI;
import org.rmj.guanzongroup.ghostrider.notifications.R;

import java.util.Date;

public class RegularNotification implements iNotificationUI {
    private static final String TAG = RegularNotification.class.getSimpleName();

    private final Context mContext;
    private final ENotificationMaster poMessage;
    private NotificationManager loManager;

    public static final String NotificationID = "org.rmj.guanconnect.regularmessage";
    private static final String CHANNEL_NAME = "Regular Message";
    private static final String CHANNEL_DESC = "Guanzon connect notice";

    public RegularNotification(Context mContext, ENotificationMaster message) {
        this.mContext = mContext;
        this.poMessage = message;
    }

    @Override
    public void CreateNotification() {
        try{
            String lsCrtr = poMessage.getCreatrID();

            RglNotification loNotif;
            switch (lsCrtr){
                case "SYSTEM":
                    loNotif = new RegularSysNotification(mContext, poMessage);
                    break;

                default:
                    loNotif = new MessageNotification(mContext, poMessage);
                    break;
            }

            loNotif.CreateNotification();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
