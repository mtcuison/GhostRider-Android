package org.rmj.guanzongroup.pacitareward.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaRule;
import org.rmj.g3appdriver.lib.GawadPacita.Obj.Pacita;

import java.util.List;

public class VMBranchRecordDetails extends AndroidViewModel {
    private final Pacita posys;
    private String message;

    public VMBranchRecordDetails(@NonNull Application application) {
        super(application);
        posys = new Pacita(application);;
    }

    public LiveData<List<EPacitaRule>> GetCriteria(){
        return posys.GetPacitaRules();
    }

    public LiveData<EPacitaEvaluation> getBranchEvaluation(String sBranhcd){
        return posys.GetEvaluationRecord(posys.InitializePacitaEvaluation(sBranhcd));
    }
    public void onEvaluationRecords(String sBranchcd){
        new EvaluationRecords().execute(sBranchcd);
    }

    public class EvaluationRecords extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... string) {
            if (!posys.ImportPacitaEvaluations(string[0])){
                message = posys.getMessage();
                return false;
            }
            return true;
        }
    }
}
