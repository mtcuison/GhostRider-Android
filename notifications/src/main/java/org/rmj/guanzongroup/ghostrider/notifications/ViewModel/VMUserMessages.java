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

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMessages;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.dev.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Notifications.Obj.Message;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.dev.Api.WebApi;

import java.util.List;
import java.util.Objects;

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