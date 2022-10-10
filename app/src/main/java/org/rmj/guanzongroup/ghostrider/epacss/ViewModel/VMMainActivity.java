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
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.dev.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.PetManager.SelfieLog;
import org.rmj.guanzongroup.ghostrider.epacss.Service.DataSyncService;

import java.util.List;

public class VMMainActivity extends AndroidViewModel {
    private static final String TAG = "GRider Main Activity";
    private final Application app;
    private final DataSyncService poNetRecvr;
    private final EmployeeMaster poUser;
    private final SelfieLog poLog;

    public VMMainActivity(@NonNull Application application) {
        super(application);
        this.app = application;
        this.poNetRecvr = new DataSyncService(app);
        this.poUser = new EmployeeMaster(app);
        this.poLog = new SelfieLog(app);
    }

    public DataSyncService getInternetReceiver(){
        return poNetRecvr;
    }

    public LiveData<List<EEmployeeRole>> getEmployeeRole(){
        return poUser.getEmployeeRoles();
    }

    public LiveData<List<EEmployeeRole>> getChildRoles(){
        return poUser.getChildRoles();
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poUser.GetEmployeeInfo();
    }

    public LiveData<ESelfieLog> getLastSelfieLog(){
        return poLog.getLastSelfieLog();
    }
}
