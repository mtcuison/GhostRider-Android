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
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMBranchRecordDetails extends AndroidViewModel {
    private final Pacita posys;
    private final ConnectionUtil poConn;
    private String message;

    public VMBranchRecordDetails(@NonNull Application application) {
        super(application);
        posys = new Pacita(application);
        poConn = new ConnectionUtil(application);
    }

    public interface BranchRecordDetailsCallBack{
        void onInitialize(String message);
        void onSuccess(String message, String transactNo);
        void onError(String message);
    }
    public LiveData<List<EPacitaRule>> GetCriteria(){
        return posys.GetPacitaRules();
    }
    public LiveData<EPacitaEvaluation> getBranchEvaluation(String transactNo){
        return posys.GetEvaluationRecord(transactNo);
    }
    public void onEvaluationRecords(String sBranchcd, BranchRecordDetailsCallBack mListener){
        new EvaluationRecordDetails(mListener).execute(sBranchcd);
    }

    public class EvaluationRecordDetails extends AsyncTask<String, Void, String>{
        private BranchRecordDetailsCallBack mListener;
        private EvaluationRecordDetails(BranchRecordDetailsCallBack mListener){
            this.mListener = mListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mListener.onInitialize("Loading Branch Record Details. Please wait . . .");
        }
        @Override
        protected String doInBackground(String... branchCD) {
            Log.d("BRANCHCODE", branchCD[0]);
            String lsResult = posys.InitializePacitaEvaluation(branchCD[0]);

            if(!poConn.isDeviceConnected()){
                message = poConn.getMessage();
                return message;
            }
            /*if (!posys.ImportPacitaEvaluations(branchCD[0])){
                message = posys.getMessage();
                return message;
            }*/
            if (lsResult == null){
                message = posys.getMessage();
                return message;
            }
            return lsResult;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null){
                mListener.onError(message);
            }else {
                mListener.onSuccess(message, result);
            }
        }
    }
}
