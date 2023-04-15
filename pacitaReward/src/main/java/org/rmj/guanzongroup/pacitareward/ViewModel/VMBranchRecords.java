package org.rmj.guanzongroup.pacitareward.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPacita.BranchRecords;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.lib.GawadPacita.Obj.Pacita;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMBranchRecords extends AndroidViewModel {
    private final Pacita posys;
    private final ConnectionUtil poConn;

    public VMBranchRecords(@NonNull Application application) {
        super(application);

        this.posys = new Pacita(application);
        this.poConn = new ConnectionUtil(application);
    }

    public LiveData<List<BranchRecords>> getBranchRecords(String sBranchcd){
        return posys.GetBranchRecords(sBranchcd);
    }
    public void initializeRecords(String sBranchcd){
        new OnInitializeRecords().execute(sBranchcd);
    }
    public class OnInitializeRecords extends AsyncTask<String, Void, Boolean>{
        String message;

        @Override
        protected Boolean doInBackground(String... string) {
            if (!poConn.isDeviceConnected()){
                message = poConn.getMessage();
                return false;
            }
            if (!posys.ImportPacitaEvaluations(string[0])){
                message = posys.getMessage();
                return false;
            }
            return true;
        }
    }
}
