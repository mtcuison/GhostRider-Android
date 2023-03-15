package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.lib.BullsEye.obj.BranchPerformance;

public class VMBranchPerformanceMonitor extends AndroidViewModel {
    private static final String TAG = VMBranchPerformanceMonitor.class.getSimpleName();

    private final BranchPerformance poSys;

    public VMBranchPerformanceMonitor(@NonNull Application application) {
        super(application);
        this.poSys = new BranchPerformance(application);
    }


}
