/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/22/21, 4:22 PM
 * project file last modified : 9/22/21, 4:20 PM
 */

package org.rmj.guanzongroup.petmanager.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Apps.PetManager.Obj.EmployeeOB;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeBusinessTrip;

import java.util.List;

public class VMBusinessTripList extends AndroidViewModel {

    private final EmployeeOB poSys;

    public VMBusinessTripList(@NonNull Application application) {
        super(application);
        this.poSys = new EmployeeOB(application);
    }

    public LiveData<List<EEmployeeBusinessTrip>> getForApprovalList(){
        return poSys.GetOBApplicationsForApproval();
    }
    public LiveData<List<EEmployeeBusinessTrip>> getForPreviewList(){
        return poSys.GetOBApplicationList();
    }

}