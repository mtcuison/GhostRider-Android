/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 3/28/22, 8:50 AM
 * project file last modified : 3/28/22, 8:50 AM
 */

package org.guanzongroup.com.creditevaluation.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.guanzongroup.com.creditevaluation.Core.EvaluatorManager;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VMCIHistoryPreview extends AndroidViewModel {
    private static final String TAG = VMEvaluation.class.getSimpleName();
    private final Application app;
    private final REmployee poUser;
    private final EvaluatorManager foManager;
    private MutableLiveData<HashMap<oParentFndg, List<oChildFndg>>> foEvaluate = new MutableLiveData<>();

    public VMCIHistoryPreview(@NonNull Application application) {
        super(application);
        this.app = application;
        this.poUser = new REmployee(application);
        this.foManager = new EvaluatorManager(application);
    }
    public LiveData<ECreditOnlineApplicationCI> getCIEvaluation(String transNox){
        return foManager.getApplications(transNox);
    }

    public void parseToEvaluationPreviewData(ECreditOnlineApplicationCI eCI) throws Exception {
        foEvaluate.setValue(foManager.parseToEvaluationPreviewData(eCI));
    }
    public LiveData<HashMap<oParentFndg, List<oChildFndg>>> getParsedEvaluationPreviewData(){
        return foEvaluate;
    }
}
