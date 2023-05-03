/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/22/21, 4:22 PM
 * project file last modified : 9/22/21, 4:19 PM
 */

package org.rmj.guanzongroup.petmanager.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GCircle.room.Repositories.RBranch;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.Obj.EmployeeLeave;

import java.util.List;

public class VMLeaveList extends AndroidViewModel {
   private static final String TAG = VMLeaveList.class.getSimpleName();

    private final Application instance;
    private final EmployeeLeave poLeave;
    private final RBranch poBranch;

    public VMLeaveList(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poLeave = new EmployeeLeave(instance);
        this.poBranch = new RBranch(instance);
    }

    public LiveData<List<EEmployeeLeave>> getForApprovalList(){
        return poLeave.GetLeaveApplicationsForApproval();
    }

    public LiveData<List<EEmployeeLeave>> getForPreviewList(){
        return poLeave.GetLeaveApplicationList();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }
}