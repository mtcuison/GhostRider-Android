package org.rmj.g3appdriver.lib.Notifications;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotificationDetail;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConstants;

public class NotificationDetail {
    private static final String TAG = NotificationDetail.class.getSimpleName();

    private final Application instance;

    private final DNotificationDetail poDao;

    private String message;

    public NotificationDetail(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).nDetailDao();
    }

    public String getMessage() {
        return message;
    }

    public boolean SaveDetail(RemoteMessage remoteMessage){
        try{
            RemoteMessageParser loParser = new RemoteMessageParser(remoteMessage);

            ENotificationRecipient loRecpnt = new ENotificationRecipient();
            loRecpnt.setTransNox(loParser.getValueOf("transno"));
            loRecpnt.setAppRcptx(loParser.getValueOf("apprcpt"));
            loRecpnt.setRecpntID(loParser.getValueOf("rcptid"));
            loRecpnt.setRecpntNm(loParser.getValueOf("rcptnm"));
            loRecpnt.setMesgStat(loParser.getValueOf("status"));
            loRecpnt.setReceived(new AppConstants().DATE_MODIFIED);
            loRecpnt.setTimeStmp(new AppConstants().DATE_MODIFIED);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
