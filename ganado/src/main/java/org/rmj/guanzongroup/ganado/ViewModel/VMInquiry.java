package org.rmj.guanzongroup.ganado.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.EGanadoOnline;
import org.rmj.g3appdriver.GCircle.room.Entities.EMcBrand;
import org.rmj.g3appdriver.lib.Ganado.Obj.Ganado;
import org.rmj.g3appdriver.lib.Ganado.Obj.ProductInquiry;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnDoBackgroundTaskListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.util.List;

public class VMInquiry extends AndroidViewModel {

    private final Ganado poSys;
    private final ConnectionUtil poConn;


    public VMInquiry(@NonNull Application application) {
        super(application);

        poSys = new Ganado(application);
        poConn = new ConnectionUtil(application);
    }

    public LiveData<List<EGanadoOnline>> GetInquiries(){
        return poSys.GetInquiries();
    }
    public void importCriteria(){
        new ImportCriteriaTask().execute();
    }


    private class ImportCriteriaTask{
        private String TAG = getClass().getSimpleName();
        public void execute(){
            TaskExecutor.Execute(null, new OnDoBackgroundTaskListener() {
                @Override
                public Object DoInBackground(Object args) {
                    if (!poConn.isDeviceConnected()){
                        return poConn.getMessage();
                    }

                    return "";
                }

                @Override
                public void OnPostExecute(Object object) {
                    Log.d(TAG, object.toString());
                }
            });
        }
    }
}
