/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;

import java.util.List;

public class VMHomeContainer extends AndroidViewModel {
    private static final String TAG = VMHomeContainer.class.getSimpleName();

    private final EmployeeMaster poUser;
    private final RNotificationInfo poNotification;
    private final SessionManager poSession;

    public VMHomeContainer(@NonNull Application application) {
        super(application);
        this.poUser = new EmployeeMaster(application);
        this.poNotification = new RNotificationInfo(application);
        this.poSession = new SessionManager(application);
    }

    public LiveData<Integer> getUnreadMessagesCount(){
        return poNotification.getUnreadMessagesCount();
    }

    public LiveData<Integer> getUnreadNotificationsCount(){
        return poNotification.getUnreadNotificationsCount();
    }

    public LiveData<List<DNotifications.UserNotificationInfoWithRcpt>> getUserNotificationList(){
        return poNotification.getUserNotificationList();
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poUser.GetEmployeeInfo();
    }

    public String getEmployeeLevel(){
        return poSession.getEmployeeLevel();
    }
}