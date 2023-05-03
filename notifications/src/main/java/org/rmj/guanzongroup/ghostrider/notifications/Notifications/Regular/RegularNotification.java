package org.rmj.guanzongroup.ghostrider.notifications.Notifications.Regular;

import android.app.NotificationManager;
import android.content.Context;

import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationMaster;
import org.rmj.guanzongroup.ghostrider.notifications.Etc.iNotificationUI;

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
