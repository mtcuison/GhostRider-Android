package org.rmj.g3appdriver.GCircle.Apps.BullsEye;

import android.content.Context;

import androidx.lifecycle.LiveData;

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
