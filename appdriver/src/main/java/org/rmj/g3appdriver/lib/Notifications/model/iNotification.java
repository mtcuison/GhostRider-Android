package org.rmj.g3appdriver.lib.Notifications.model;

import androidx.lifecycle.LiveData;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;
import org.rmj.g3appdriver.lib.Notifications.pojo.NotificationItemList;

import java.util.List;

public interface iNotification {
    String Save(RemoteMessage foVal);

    /**
     *
     * @param status parameters refer to what type of response must be send
     *            Receive, Read
     */
    ENotificationMaster SendResponse(String mesgID, NOTIFICATION_STATUS status);

    boolean CreateNotification(String title, String message);

    LiveData<List<NotificationItemList>> GetNotificationList();

    String getMessage();
}
