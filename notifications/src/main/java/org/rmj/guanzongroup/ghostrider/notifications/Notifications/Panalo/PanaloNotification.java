package org.rmj.guanzongroup.ghostrider.notifications.Notifications.Panalo;

import android.app.NotificationManager;
import android.content.Context;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ENotificationMaster;
import org.rmj.guanzongroup.ghostrider.notifications.Etc.iNotificationUI;

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
            PnlNotification loNotif;
            switch (lsModule){
                case "001":
                    loNotif = new RewardNotification(mContext, poMessage);
                    break;
                default:
                    loNotif = new RaffleNotification(mContext, poMessage);
                    break;
            }
            loNotif.CreateNotification();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
