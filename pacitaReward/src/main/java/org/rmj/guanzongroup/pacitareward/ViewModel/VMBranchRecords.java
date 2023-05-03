package org.rmj.guanzongroup.pacitareward.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DPacita.BranchRecords;
import org.rmj.g3appdriver.GCircle.Apps.GawadPacita.Obj.Pacita;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMBranchRecords extends AndroidViewModel {
    private final Pacita posys;
    private final ConnectionUtil poConn;
    private String message;

    public VMBranchRecords(@NonNull Application application) {
        super(application);

        this.posys = new Pacita(application);
        this.poConn = new ConnectionUtil(application);
    }

    public interface BranchRecordsCallBack{
        void onInitialize(String message);
        void onSuccess(String message);
        void onError(String message);
    }

    public LiveData<List<BranchRecords>> getBranchRecords(String sBranchcd){
        return posys.GetBranchRecords(sBranchcd);
    }
    public void initializeRecords(String sBranchcd, BranchRecordsCallBack mListener){
        new OnInitializeRecords(mListener).execute(sBranchcd);
    }
    public class OnInitializeRecords extends AsyncTask<String, Void, Boolean>{
        private BranchRecordsCallBack mListener;
        private OnInitializeRecords(BranchRecordsCallBack mListener){
            this.mListener = mListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mListener.onInitialize("Loading Branch Records. Please wait . . .");
        }

        @Override
        protected Boolean doInBackground(String... branchCd) {
            Boolean importResult = posys.ImportPacitaEvaluations(branchCd[0]);

            if (!poConn.isDeviceConnected()){
                message = poConn.getMessage();
                return false;
            }
            if (!importResult){
                message = posys.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean == false){
                mListener.onError(message);
            }else {
                mListener.onSuccess(message);
            }
        }
    }
}
