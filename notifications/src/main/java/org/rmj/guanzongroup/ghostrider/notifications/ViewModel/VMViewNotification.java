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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class VMViewNotification extends AndroidViewModel {
    private static final String TAG = VMViewNotification.class.getSimpleName();

    public VMViewNotification(@NonNull Application application) {
        super(application);
        Log.e(TAG, "Initialized.");
    }

    public void sendReply(String fsTransNo, String fsMessage) {
        Log.e(TAG, "Reply notification currently not available");
    }

    public void deleteNotification(String fsTransNo) {
        Log.e(TAG, "Delete notification currently not available.");
        return;
    }

}