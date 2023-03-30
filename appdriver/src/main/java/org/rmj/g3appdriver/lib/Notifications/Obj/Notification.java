package org.rmj.g3appdriver.lib.Notifications.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotification;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotificationReceiver;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_Regular;

import java.util.List;

public class Notification extends NMM_Regular {
    private static final String TAG = Notification.class.getSimpleName();

    private final DNotification poDao;

    public Notification(Application instance) {
        super(instance);
        this.poDao = GGC_GriderDB.getInstance(instance).notificationDao();
    }

    public LiveData<List<DNotification.NotificationListDetail>> GetOtherNotificationList(){
        return poDao.GetNotificationList();
    }

    public LiveData<Integer> GetUnreadNotificationCout(){
        return poDao.GetUnreadNotificationCout();
    }

    public LiveData<ENotificationMaster> GetNotificationMaster(String args){
        return poDao.GetNotificationMaster(args);
    }

    public LiveData<ENotificationRecipient> GetNotificationDetail(String args){
        return poDao.GetNotificationDetail(args);
    }

    @Override
    public ENotificationMaster SendResponse(String mesgID, NOTIFICATION_STATUS status) {
        return super.SendResponse(mesgID, status);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
