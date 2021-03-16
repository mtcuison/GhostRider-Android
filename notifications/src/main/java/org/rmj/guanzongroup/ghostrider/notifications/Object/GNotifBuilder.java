package org.rmj.guanzongroup.ghostrider.notifications.Object;

import android.content.Context;

import androidx.core.app.NotificationCompat;

import org.rmj.guanzongroup.ghostrider.notifications.R;

public class GNotifBuilder {

    private final Context context;

    private static String SELFIE_LOG = "grider_selfielog";

    public GNotifBuilder(Context context) {
        this.context = context;
    }

//    public static void viewNotification(String textTitle, String textContent) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, SELFIE_LOG)
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle(textTitle)
//                .setContentText(textContent)
//                .setPriority(NotificationCompat.PRIORITY_HIGH);
//    }
}
