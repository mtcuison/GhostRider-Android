package org.rmj.guanzongroup.ghostrider.notifications.Notifications.Panalo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.guanzongroup.ghostrider.notifications.Etc.iNotificationUI;
import org.rmj.guanzongroup.ghostrider.notifications.R;

public class PanaloNotification implements iNotificationUI {
    private static final String TAG = PanaloNotification.class.getSimpleName();

    private final Context mContext;
    private final ENotificationMaster poMessage;
    private NotificationManager loManager;

    public static final String NotificationID = "org.rmj.guanconnect.guanzonpanalo";
    public static final String CHANNEL_NAME = "Guanzon Panalo";
    public static final String CHANNEL_DESC = "Guanzon connect rewards notification for panalo participants.";

    public PanaloNotification(Context context, ENotificationMaster message) {
        this.mContext = context;
        this.poMessage = message;
    }

    @Override
    public void CreateNotification() {
        try {
            JSONObject loJson = new JSONObject(poMessage.getDataSndx());
            String lsModule = loJson.getString("module");
            PnlNotification loNotif = null;
            switch (lsModule){
                case "001":
                    loNotif = new RaffleNotification(mContext, poMessage);
                    break;
                default:
                    loNotif = new RewardNotification(mContext, poMessage);
                    break;
            }
            loNotif.CreateNotification();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
