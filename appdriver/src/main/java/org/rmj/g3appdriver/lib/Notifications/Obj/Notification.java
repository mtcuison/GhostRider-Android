package org.rmj.g3appdriver.lib.Notifications.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_Regular;

import java.util.List;

public class Notification extends NMM_Regular {
    private static final String TAG = Notification.class.getSimpleName();

    private final DNotifications poDao;

    public Notification(Application instance) {
        super(instance);
        this.poDao = GGC_GriderDB.getInstance(instance).NotificationDao();
    }


}
