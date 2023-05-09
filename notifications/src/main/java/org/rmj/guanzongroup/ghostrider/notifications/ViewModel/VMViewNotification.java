/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotification;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;
import org.rmj.g3appdriver.lib.Notifications.Obj.Notification;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMViewNotification extends AndroidViewModel {
    private static final String TAG = VMViewNotification.class.getSimpleName();

    private final Notification poSys;
    private final ConnectionUtil poConn;

    public VMViewNotification(@NonNull Application application) {
        super(application);
        this.poSys = new Notification(application);
        this.poConn = new ConnectionUtil(application);
    }

    public LiveData<List<DNotification.NotificationListDetail>> GetOtherNotificationList(){
        return poSys.GetOtherNotificationList();
    }

    public LiveData<ENotificationMaster> GetNotificationMaster(String args){
        return poSys.GetNotificationMaster(args);
    }

    public LiveData<ENotificationRecipient> GetNotificationDetail(String args){
        return poSys.GetNotificationDetail(args);
    }

    public void SendResponse(String args){
        new SendResponseTask().execute(args);
    }

    private class SendResponseTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... voids) {
            try{
                if(!poConn.isDeviceConnected()){
                    Log.e(TAG, poConn.getMessage());
                    return false;
                }

                String lsMessageID = voids[0];
                if(poSys.SendResponse(lsMessageID, NOTIFICATION_STATUS.READ) == null){
                    Log.e(TAG, poSys.getMessage());
                    return false;
                }

                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }
}