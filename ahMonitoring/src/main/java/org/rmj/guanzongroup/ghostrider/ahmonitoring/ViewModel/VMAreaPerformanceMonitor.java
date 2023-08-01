package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.GCircle.Apps.BullsEye.obj.AreaPerformance;

public class VMAreaPerformanceMonitor extends AndroidViewModel {
    private static final String TAG = VMAreaPerformanceMonitor.class.getSimpleName();

    private final AreaPerformance poSys;


    public VMAreaPerformanceMonitor(@NonNull Application application) {
        super(application);
        this.poSys = new AreaPerformance(application);
    }
}
