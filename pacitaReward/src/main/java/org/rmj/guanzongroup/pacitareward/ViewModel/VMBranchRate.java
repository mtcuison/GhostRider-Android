package org.rmj.guanzongroup.pacitareward.ViewModel;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.GCircle.room.Entities.EPacitaRule;
import org.rmj.g3appdriver.GCircle.Apps.GawadPacita.Obj.Pacita;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnDoBackgroundTaskListener;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

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
    /*private class InitializeEvluationTask extends AsyncTask<String, Void, String>{
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
                message = getLocalMessage(e);
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
    }*/
    private class InitializeEvluationTask{
        private final OnInitializeBranchEvaluationListener mListener;
        public InitializeEvluationTask(OnInitializeBranchEvaluationListener mListener) {
            this.mListener = mListener;
        }
        public void execute(String BranchCD){
            TaskExecutor.Execute(BranchCD, new OnTaskExecuteListener() {
                @Override
                public void OnPreExecute() {
                    mListener.onInitialize("Loading Evaluations. Please wait . . .");
                }

                @Override
                public Object DoInBackground(Object args) {
                    String lsResult = poSys.InitializePacitaEvaluation((String) args);
                    try{
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
                public void OnPostExecute(Object object) {
                    if(object == null){
                        mListener.OnError(message);
                    } else {
                        mListener.OnSuccess((String) object, message);
                    }
                }
            });
        }
    }

    public void setEvaluationResult(String TransNox, String EntryNox, String Result){
        new SetEvaluationResultTask().execute(TransNox, EntryNox, Result);
    }
    /*public class SetEvaluationResultTask extends AsyncTask<String, Void, Boolean>{
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
    }*/
    public class SetEvaluationResultTask{
        private String TAG = getClass().getSimpleName();
        public void execute(String TransNox, String EntryNox, String Result){
            String[] argList = {TransNox,EntryNox,Result};

            TaskExecutor.Execute(argList, new OnDoBackgroundTaskListener() {
                @Override
                public Object DoInBackground(Object args) {
                    String[] array = (String[]) args;

                    String sTransNo = array[0];
                    String EntryNox = array[1];
                    String Result = array[2];

                    if(!poSys.UpdateBranchRate(sTransNo, Integer.parseInt(EntryNox), Result)){
                        message = poSys.getMessage();
                        return false;
                    }
                    return true;
                }

                @Override
                public void OnPostExecute(Object object) {
                    Log.d(TAG, object.toString());
                }
            });
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
    /*public class SaveBranchRatings extends AsyncTask<String, Void, Boolean>{
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
                message = getLocalMessage(e);
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
    }*/
    public class SaveBranchRatings{
        private final BranchRatingsCallback callback;
        private String TransNox;
        public SaveBranchRatings(String TransNox, BranchRatingsCallback callback){
            this.TransNox = TransNox;
            this.callback = callback;
        }
        public void execute(String TransNox){
            TaskExecutor.Execute(TransNox, new OnTaskExecuteListener() {
                @Override
                public void OnPreExecute() {
                    callback.onSave("Pacita Reward", "Saving your Evaluation. Please Wait . . . ");
                }

                @Override
                public Object DoInBackground(Object args) {
                    try {
                        if(!poConnection.isDeviceConnected()){
                            message = poConnection.getMessage();
                            return false;
                        }
                        if(!poSys.SaveBranchRatings((String) args)){
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
                public void OnPostExecute(Object object) {
                    Boolean aBoolean = (Boolean) object;
                    if(aBoolean){
                        callback.onSuccess("Successfully Saved Branch Evaluation");
                    } else {
                        callback.onFailed(message);
                    }
                }
            });
        }
    }
}
