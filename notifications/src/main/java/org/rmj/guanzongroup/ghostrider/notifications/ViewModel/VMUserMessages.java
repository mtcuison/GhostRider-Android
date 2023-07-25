/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 5/18/21 2:22 PM
 * project file last modified : 5/17/21 3:48 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DMessages;
import org.rmj.g3appdriver.lib.Notifications.Obj.Message;

import java.util.List;

public class VMUserMessages extends AndroidViewModel {
    private static final String TAG = VMUserMessages.class.getSimpleName();

    private final Message poSys;
    private final Application instance;

    public VMUserMessages(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new Message(application);
    }

    public LiveData<String> GetSenderName(String args){
        return poSys.GetSenderName(args);
    }

    public LiveData<List<DMessages.UserMessages>> GetUserMessages(String args){
        return poSys.GetUserMessages(args);
    }
}