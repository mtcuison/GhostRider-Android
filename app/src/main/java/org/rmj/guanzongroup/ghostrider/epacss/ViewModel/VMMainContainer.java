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
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;

public class VMMainContainer extends AndroidViewModel {

    private final EmployeeMaster poUser;

    public VMMainContainer(@NonNull Application application) {
        super(application);
        this.poUser = new EmployeeMaster(application);
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poUser.GetEmployeeInfo();
    }
}