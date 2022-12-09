package org.rmj.g3appdriver.lib.Notifications;

import androidx.lifecycle.LiveData;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.lib.Notifications.model.NotificationItemList;

import java.util.List;

public interface iNotification {
    String Save(RemoteMessage foVal);

    /**
     *
     * @param val parameters refer to what type of response must be send
     *            Receive, Read
     */
    boolean SendResponse(String mesgID, NOTIFICATION_STATUS status);

    boolean CreateNotification(String title, String message);

    LiveData<List<NotificationItemList>> GetNotificationList();

    String getMessage();
}
