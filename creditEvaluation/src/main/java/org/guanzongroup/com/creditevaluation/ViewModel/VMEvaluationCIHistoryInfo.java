/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 4/8/22, 11:35 AM
 * project file last modified : 4/8/22, 11:35 AM
 */

package org.guanzongroup.com.creditevaluation.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.guanzongroup.com.creditevaluation.Core.EvaluatorManager;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;

import java.util.List;

public class VMEvaluationCIHistoryInfo extends AndroidViewModel {

    private final EvaluatorManager poHistory;

    public VMEvaluationCIHistoryInfo(@NonNull Application application) {
        super(application);
        this.poHistory = new EvaluatorManager(application);
    }

    public LiveData<List<ECreditOnlineApplicationCI>> getForPreviewResultList() {
        return poHistory.getForPreviewResultList();
    }

    public LiveData<ECreditOnlineApplicationCI> RetrieveApplicationData(String TransNox) {
        return poHistory.RetrieveApplicationData(TransNox);
    }

}
