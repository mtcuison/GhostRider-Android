/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchPerformance;

public class VMBranchMonitor extends AndroidViewModel {
    private static final String TAG = VMBranchMonitor.class.getSimpleName();

    private RBranchPerformance poPerfrm;

    public VMBranchMonitor(@NonNull Application application) {
        super(application);
        this.poPerfrm = new RBranchPerformance(application);
    }



}