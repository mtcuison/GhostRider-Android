package org.rmj.guanzongroup.ghostrider.notifications.Object;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import org.rmj.guanzongroup.ghostrider.notifications.R;

public class GNotifBuilder {

    private final Context context;

    private static int CHANNEL_ID = 0;

    public static int SELFIE_LOG = 132;
    public static int DCP_DATA = 133;

    public static int SENDING_SELFIE_IMAGES = 124;
    public static int SENDING_SELFIELOG = 241;
    public static int SENDING_DCP_DATA = 412;
    public static int SENDING_DCP_IMAGES = 421;

    private static final String CHANNEL_DESC = "DRider Background Sending Data";
    private static final String CHANNEL_NAME = "GRider NTF_Channel";
    private static final String NotificationID = "org.rmj.guanzongroup.ghostrider";

    private static String Title;
    private static String Message;

    @SuppressLint("StaticFieldLeak")
    private static GNotifBuilder instance;

    private GNotifBuilder(Context context){
        this.context = context;
    }

    public static GNotifBuilder createNotification(Context context, String fsTitle, String fsMessage, int fnChannelID) {
        if(instance == null) {
            instance = new GNotifBuilder(context);
        }
        Title = fsTitle;
        Message = fsMessage;
        CHANNEL_ID = fnChannelID;
        return instance;
    }

    private void initChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NotificationID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private NotificationCompat.Builder initNotification(){
        return new NotificationCompat.Builder(context)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setChannelId(NotificationID)
                .setSmallIcon(R.drawable.ic_ghostrider_logo_day)
                .setContentTitle(Title)
                .setContentText(Message);
    }

    public void show(){
        initChannel();
        initNotification();
        NotificationManager loManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        loManager.notify(CHANNEL_ID, initNotification().build());
    }
}
