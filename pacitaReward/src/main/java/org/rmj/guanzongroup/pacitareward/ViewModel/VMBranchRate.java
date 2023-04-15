package org.rmj.guanzongroup.pacitareward.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaRule;
import org.rmj.g3appdriver.lib.GawadPacita.Obj.Pacita;

import java.util.List;

public class VMBranchRate extends AndroidViewModel {
    private final Pacita poSys;
    private OnInitializeBranchEvaluationListener listener;

    public VMBranchRate(@NonNull Application application) {
        super(application);

        poSys = new Pacita(application);
    }

    public interface OnInitializeBranchEvaluationListener {
        void OnInitialize(String transactNo);
        void OnError(String message);
    }

    public void InitializeEvaluation(String BranchCD, OnInitializeBranchEvaluationListener listener){
        new InitializeEvluationTask(listener).execute(BranchCD);
    }

    private class InitializeEvluationTask extends AsyncTask<String, Void, String>{

        private final OnInitializeBranchEvaluationListener mListener;

        private String message;

        public InitializeEvluationTask(OnInitializeBranchEvaluationListener mListener) {
            this.mListener = mListener;
        }

        @Override
        protected String doInBackground(String... branchcd) {
            String lsResult = poSys.InitializePacitaEvaluation(branchcd[0]);
            if(lsResult == null){
                message = poSys.getMessage();
                return null;
            }

            return lsResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result == null){
                mListener.OnError(message);
            } else {
                mListener.OnInitialize(result);
            }
        }
    }

    public LiveData<EPacitaEvaluation> getBranchEvaluation(String lsTransNo){
        return poSys.GetEvaluationRecord(lsTransNo);
    }
    public LiveData<List<EPacitaRule>> GetCriteria(){
        return poSys.GetPacitaRules();
    }

    public void setEvaluationResult(String TransNox, String EntryNox, String Result){
        new SetEvaluationResultTask().execute(TransNox, EntryNox, Result);
    }

    public class SetEvaluationResultTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            String sTransNo = strings[0];
            String EntryNox = strings[1];
            String Result = strings[2];

            if(!poSys.UpdateBranchRate(sTransNo, Integer.parseInt(EntryNox), Result)){
                return false;
            }
            return true;
        }
    }
}
