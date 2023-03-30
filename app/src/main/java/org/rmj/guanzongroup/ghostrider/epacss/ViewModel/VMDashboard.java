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

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ERaffleStatus;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.Notifications.Obj.Notification;
import org.rmj.g3appdriver.lib.Panalo.Obj.ILOVEMYJOB;

public class VMDashboard extends AndroidViewModel {

    private final EmployeeMaster poEmploye;
    private final ILOVEMYJOB poPanalo;
    private final Notification poNotification;

    public VMDashboard(@NonNull Application application) {
        super(application);
        this.poEmploye = new EmployeeMaster(application);
        this.poPanalo = new ILOVEMYJOB(application);
        this.poNotification = new Notification(application);
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmploye.GetEmployeeInfo();
    }

    public LiveData<ERaffleStatus> GetRaffleStatus(){
        return poPanalo.GetRaffleStatus();
    }

    public LiveData<Integer> GetAllUnreadNotificationCount(){
        return poNotification.GetAllUnreadNotificationCount();
    }
}