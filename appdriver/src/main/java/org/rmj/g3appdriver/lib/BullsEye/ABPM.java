package org.rmj.g3appdriver.lib.BullsEye;

import android.content.Context;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;

import java.util.List;

public class ABPM {
    private final Context context;

    public ABPM(Context context) {
        this.context = context;
    }

    public boolean ImportData(){
        return true;
    }

    public String getMessage(){
        return null;
    }

    public boolean SetGoal(){
        return true;
    }

    public LiveData<String> GetCurrentMCSalesPerformance(){
        return null;
    }

    public LiveData<String> GetCurentSPSalesPerformance(){
        return null;
    }

    public LiveData<String> GetJobOrderPerformance(){
        return null;
    }
}
