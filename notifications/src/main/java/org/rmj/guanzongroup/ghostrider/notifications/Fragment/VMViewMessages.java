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

package org.rmj.guanzongroup.ghostrider.notifications.Fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.GRider.Database.Repositories.RNotificationInfo;

import java.util.List;

public class VMViewMessages extends AndroidViewModel {

    private final RNotificationInfo poNotification;


    public VMViewMessages(@NonNull Application application) {
        super(application);
        this.poNotification = new RNotificationInfo(application);
    }

    public LiveData<List<DNotifications.UserNotificationInfo>> getMessagesListFromSender(String SenderID){
        return poNotification.getUserMessageListFromSender(SenderID);
    }
}