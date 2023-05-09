/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.lib.Notifications;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import org.rmj.g3appdriver.R;

public class GNotifBuilder {

    private final Context context;

    private NotificationManager loManager;

    private static int CHANNEL_ID = 0;

    public static int SELFIE_LOG = 132;
    public static int DCP_DATA = 133;
    public static int SYNC_PROGRESS = 235;

    public static int APP_DATA_DOWNLOAD = 413;
    public static int APP_SYNC_DATA = 314;

    public static int SENDING_SELFIE_IMAGES = 124;
    public static int SENDING_SELFIELOG = 241;
    public static int SENDING_DCP_DATA = 412;
    public static int SENDING_DCP_IMAGES = 421;

    public static String JOB_SERVICE = "GRider System Service";
    public static String SELFIE_LOGIN_SERVICE = "Selfie Login Service";
    public static String BROADCAST_RECEIVER = "Internet Sync Service";

    private static final String CHANNEL_DESC = "DRider Background Sending Data";
    private static final String CHANNEL_NAME = "GRider NTF_Channel";
    private static final String NotificationID = "org.rmj.guanzongroup.ghostrider";

    private boolean PRIORITY = false;

    private static String Title;
    private static String Message;
    private static String MessageID;

    @SuppressLint("StaticFieldLeak")
    private static GNotifBuilder instance;

    private GNotifBuilder(Context context){
        this.context = context;
    }

    public static GNotifBuilder createFirebaseNotification(Context context, String msgID,String fsTitle, String fsMessage, int fnChannelID) {
        if(instance == null) {
            instance = new GNotifBuilder(context);
        }
        Title = fsTitle;
        Message = fsMessage;
        MessageID = msgID;
        CHANNEL_ID = fnChannelID;
        return instance;
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

    private NotificationCompat.Builder initNotification(Class<?> activity){
        Intent notifyIntent = new Intent(context, activity);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if(MessageID != null) {
            notifyIntent.putExtra("id", MessageID);
            notifyIntent.putExtra("title", Title);
            notifyIntent.putExtra("type", "notification");
        }
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            notifyPendingIntent = PendingIntent.getActivity(
                    context, 0, notifyIntent, PendingIntent.FLAG_MUTABLE);
        } else {
            notifyPendingIntent = PendingIntent.getActivity(
                    context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return new NotificationCompat.Builder(context)
                .setContentIntent(notifyPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setChannelId(NotificationID)
                .setSmallIcon(R.drawable.ic_guanzon_circle)
                .setContentTitle(Title)
                .setContentText(Message);
    }

    public void show(Class<?> activity){
        initChannel();
        initNotification(activity);
        loManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        loManager.notify(CHANNEL_ID, initNotification(activity).build());
    }

    public void dismiss(){
        if (loManager != null) {
            loManager.cancel(CHANNEL_ID);
        }
    }

    /**
     *
     * @param priority set the priority type of notification base on what method is going to use the notification
     *                 Default value : NotificationCompat.PRIORITY_HIGH
     */
    public void setPriority(boolean priority){
        this.PRIORITY = priority;
    }
}
