package org.rmj.g3appdriver.lib.BullsEye;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;

import java.util.List;

public interface ABPM {
    boolean ImportData(String args);
    LiveData<List<EAreaPerformance>> GetPeriodicAreaPerformance();
    LiveData<List<EAreaPerformance>> GetBranchesPerformance(String args);

    String getMessage();
}
