package org.rmj.g3appdriver.lib.Notifications;

import android.app.Application;

public class Notifications {
    private static final String TAG = Notifications.class.getSimpleName();

    private final Application instance;

    public Notifications(Application instance) {
        this.instance = instance;
    }
}
