package org.rmj.g3appdriver.lib.Notifications.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.lib.Notifications.iNotification;
import org.rmj.g3appdriver.lib.Notifications.model.NotificationItemList;

import java.util.List;

public class NMM_MPReview implements iNotification {
    private static final String TAG = NMM_MPReview.class.getSimpleName();

    private final Application instance;

    private String message;

    public NMM_MPReview(Application instance) {
        this.instance = instance;
    }

    @Override
    public boolean Save(RemoteMessage foVal) {
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    @Override
    public boolean SendResponse(String val) {
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    @Override
    public boolean CreateNotification(String title, String message) {
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            this.message = e.getMessage();
            return false;
        }
    }

    @Override
    public LiveData<List<NotificationItemList>> GetNotificationList() {
        return null;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
