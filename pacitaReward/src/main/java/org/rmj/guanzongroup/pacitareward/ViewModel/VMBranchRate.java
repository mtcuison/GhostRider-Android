package org.rmj.guanzongroup.pacitareward.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaRule;
import org.rmj.g3appdriver.lib.GawadPacita.Obj.Pacita;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMBranchRate extends AndroidViewModel {
    private final Pacita poSys;
    private ConnectionUtil poConnection;
    private String message;

    public VMBranchRate(@NonNull Application application) {
        super(application);

        poSys = new Pacita(application);
        poConnection = new ConnectionUtil(application);
    }

    public interface OnInitializeBranchEvaluationListener {
        void onInitialize(String message);
        void OnSuccess(String transactNo, String message);
        void OnError(String message);
    }

    public void InitializeEvaluation(String BranchCD, OnInitializeBranchEvaluationListener listener){
        new InitializeEvluationTask(listener).execute(BranchCD);
    }
    public LiveData<EPacitaEvaluation> getBranchEvaluation(String lsTransNo){
        return poSys.GetEvaluationRecord(lsTransNo);
    }
    public LiveData<List<EPacitaRule>> GetCriteria(){
        return poSys.GetPacitaRules();
    }
    private class InitializeEvluationTask extends AsyncTask<String, Void, String>{
        private final OnInitializeBranchEvaluationListener mListener;
        public InitializeEvluationTask(OnInitializeBranchEvaluationListener mListener) {
            this.mListener = mListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            message = "Loading Evaluations. Please wait . . .";
            mListener.onInitialize(message);
        }

        @Override
        protected String doInBackground(String... branchcd) {
            String lsResult = poSys.InitializePacitaEvaluation(branchcd[0]);
            try{
                message = poSys.getMessage();

                if(lsResult == null){
                    message = poSys.getMessage();
                    return null;
                }
            }catch (Exception e){
                message = e.getMessage();
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result == null){
                mListener.OnError(message);
            } else {
                mListener.OnSuccess(result, message);
            }
        }
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
                message = poSys.getMessage();
                return false;
            }
            return true;
        }
    }

    public interface BranchRatingsCallback{
        void onSave(String title, String message);
        void onSuccess(String message);
        void onFailed(String message);
    }
    public void saveBranchRatings(String TransNox, BranchRatingsCallback callback){
        new SaveBranchRatings(TransNox, callback).execute(TransNox);
    }
    public class SaveBranchRatings extends AsyncTask<String, Void, Boolean>{
        private final BranchRatingsCallback callback;
        private String TransNox;
        public SaveBranchRatings(String TransNox, BranchRatingsCallback callback){
            this.TransNox = TransNox;
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onSave("Pacita Reward", "Saving your Evaluation. Please Wait . . . ");
        }
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if(!poConnection.isDeviceConnected()){
                    message = poConnection.getMessage();
                    return false;
                }
                if(!poSys.SaveBranchRatings(strings[0])){
                    message = poSys.getMessage();
                    return  false;
                }
            }catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                callback.onSuccess("Successfully Saved Branch Evaluation");
            } else {
                callback.onFailed(message);
            }
        }
    }
}
