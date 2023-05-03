/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 3/18/22, 2:47 PM
 * project file last modified : 3/18/22, 2:47 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.room.Repositories.RBranch;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.DcpManager;

public class VMImageLog extends AndroidViewModel {
    private final DcpManager poDcpMngr;
    private final RBranch poBranchx;

    public VMImageLog(@NonNull Application application) {
        super(application);
        this.poDcpMngr = new DcpManager(application);
        this.poBranchx = new RBranch(application);
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranchx.getUserBranchInfo();
    }

//    public LiveData<List<EImageInfo>> getDCPImageInfoList() {
//        return poDcpMngr.getDCPImageInfoList();
//    }

}
