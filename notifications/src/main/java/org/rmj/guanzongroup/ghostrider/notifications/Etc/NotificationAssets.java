package org.rmj.guanzongroup.ghostrider.notifications.Etc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

public class NotificationAssets {

//    public static Bitmap getNotificationLargeIcon(Context context,String MessageType){
//        switch (MessageType){
//            case "00001":
//                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_accoung_warning);
//            case "00002":
//                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_security_warning);
//            case "00003":
//                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_security_warning);
//            case "00004":
//                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_registry_status);
//            case "00005":
//                return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_preorder_ntc);
//        }
//        return null;
//    }

    /**
     * This return a String value of notification title
     * if the value of String MessageType is equal to 00000
     * this indicates that the notification is a regular message
     * in this case the getAppTitle must return a String Value of
     * App or Name of the sender of Notification... else
     * this returns a Title of the notification receive.*/
    public static String getAppTitle(String MessageType, String Title, String AppSrce){

        /**
         * MessageType, Title, AppSrce
         * This String are from JSONObject that is pass by the NotificationBuilder
         * through onNotificationReceive Listener...*/
        if(MessageType.equalsIgnoreCase("00000")){
            return AppSrce;
        } else {
            return Title;
        }
    }

    /**
     * this returns a boolean value for the notification display on tray.
     *
     * if getNotificationstatus returns true. Notification on tray does
     * not require the user to dismiss the notification. This indicates
     * the priority/importance of a notification message...*/
    public static boolean getNotificationStatus(String MessageType){

        /**
         * MessageType is a String value which the NotificationBuilder
         * has pass to the NotificationReceiver to create a Notification
         * Display...*/
        switch (MessageType){
            case "00000":
                return false;
            case "00001":
                return true;
            case "00002":
                return true;
            case "00003":
                return true;
            case "00004":
                return false;
            case "00005":
                return false;
        }
        return true;
    }

    public static int getNotificationPriority(String PRODUCTID, String MessageType){
        switch (PRODUCTID){
            case "GuanzonApp":
                return getGuanzonAppSetPriority(MessageType);
            case "Telecom":
                return getTelecomSetPriority(MessageType);
        }
        return 0;
    }

    private static int getGuanzonAppSetPriority(String MessageType){
        switch (MessageType){
            case "00000":
                return NotificationCompat.PRIORITY_DEFAULT;
            case "00001":
                return NotificationCompat.PRIORITY_HIGH;
            case "00002":
                return NotificationCompat.PRIORITY_HIGH;
            case "00003":
                return NotificationCompat.PRIORITY_DEFAULT;
            case "00004":
                return NotificationCompat.PRIORITY_DEFAULT;
            case "00005":
                return NotificationCompat.PRIORITY_DEFAULT;
        }
        return 0;
    }

    private static int getTelecomSetPriority(String MessageType){
        switch (MessageType){
            case "00000":
                return NotificationCompat.PRIORITY_DEFAULT;
            case "00001":
                return NotificationCompat.PRIORITY_HIGH;
            case "00002":
                return NotificationCompat.PRIORITY_DEFAULT;
            case "00003":
                return NotificationCompat.PRIORITY_HIGH;
            case "00004":
                return NotificationCompat.PRIORITY_DEFAULT;
            case "00005":
                return NotificationCompat.PRIORITY_DEFAULT;
        }
        return 0;
    }
}
