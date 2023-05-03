package org.rmj.guanzongroup.pacitareward.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.Apps.GawadPacita.Obj.Pacita;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMBranchList extends AndroidViewModel {

    private final Pacita poSys;
    private final ConnectionUtil poConn;


    public VMBranchList(@NonNull Application application) {
        super(application);

        poSys = new Pacita(application);
        poConn = new ConnectionUtil(application);
    }

    public LiveData<List<EBranchInfo>> getBranchlist(){
        return poSys.GetBranchesList();
    }
    public void importCriteria(){
        new ImportCriteriaTask().execute();
    }


    private class ImportCriteriaTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (!poConn.isDeviceConnected()){
                Log.e("Error", poConn.getMessage());
                return false;
            }
            if (!poSys.ImportPacitaRules()){
                Log.e("Error", poSys.getMessage());
                return false;
            }
            return true;
        }
    }
}
