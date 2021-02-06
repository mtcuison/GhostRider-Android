package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RAreaPerformance;

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