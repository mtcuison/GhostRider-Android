package org.rmj.guanzongroup.ganado.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.Apps.GawadPacita.Obj.Pacita;
import org.rmj.g3appdriver.GCircle.room.Entities.EMcBrand;
import org.rmj.g3appdriver.lib.Ganado.Obj.ProductInquiry;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnDoBackgroundTaskListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.util.List;

public class VMBrandList extends AndroidViewModel {

    private final ProductInquiry poSys;
    private final ConnectionUtil poConn;


    public VMBrandList(@NonNull Application application) {
        super(application);

        poSys = new ProductInquiry(application);
        poConn = new ConnectionUtil(application);
    }

    public LiveData<List<EMcBrand>> getBrandList(){
        return poSys.GetMotorcycleBrands();
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
