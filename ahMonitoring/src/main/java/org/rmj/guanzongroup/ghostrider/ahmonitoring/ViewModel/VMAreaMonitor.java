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
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Repositories.RAreaPerformance;

import java.util.List;

public class VMAreaMonitor extends AndroidViewModel {
    public static final String TAG = VMAreaMonitor.class.getSimpleName();

    private final RAreaPerformance poDatabse;

    public VMAreaMonitor(@NonNull Application application) {
        super(application);
        poDatabse = new RAreaPerformance(application);
    }

    public LiveData<List<EAreaPerformance>> getAreaPerformanceInfoList(){
        return poDatabse.getAreaPerformanceInfoList();
    }
}