package org.rmj.g3appdriver.lib.Notifications;

import com.google.firebase.messaging.RemoteMessage;

public interface iNotification {
    void Save(RemoteMessage foVal);
    void CreateNotification(String title, String message);
}
