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

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeRole;
import org.rmj.guanzongroup.ghostrider.epacss.Service.InternetStatusReciever;

import java.util.List;

public class VMMainActivity extends AndroidViewModel {
    private static final String TAG = "GRider Main Activity";
    private final Application app;
    private final InternetStatusReciever poNetRecvr;
    private final REmployeeRole poRole;
    private final REmployee poUser;

    public VMMainActivity(@NonNull Application application) {
        super(application);
        this.app = application;
        this.poNetRecvr = new InternetStatusReciever(app);
        this.poRole = new REmployeeRole(app);
        this.poUser = new REmployee(app);
    }

    public InternetStatusReciever getInternetReceiver(){
        return poNetRecvr;
    }

    public LiveData<List<EEmployeeRole>> getEmployeeRole(){
        return poRole.getEmployeeRoles();
    }

    public LiveData<List<EEmployeeRole>> getChildRoles(){
        return poRole.getChildRoles();
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poUser.getEmployeeInfo();
    }
}
