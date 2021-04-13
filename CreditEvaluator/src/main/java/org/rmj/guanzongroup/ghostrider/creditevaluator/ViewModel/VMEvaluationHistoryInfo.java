package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.GRider.Database.Repositories.RCIEvaluation;

public class VMEvaluationHistoryInfo extends AndroidViewModel {
    private static final String TAG = VMEvaluationHistoryInfo.class.getSimpleName();
    private final RCIEvaluation poInvestx;


    public VMEvaluationHistoryInfo(@NonNull Application application) {
        super(application);
        this.poInvestx = new RCIEvaluation(application);
    }

    // TODO: Methods for EvaluationHistoryInfo


    // TODO: AsyncTasks here


    // TODO: Interfaces for callbacks



}
