package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Data.ImportCollection;

import java.util.List;

public class VMCollectionList extends AndroidViewModel {
    private static final String TAG = VMCollectionList.class.getSimpleName();
    private final RDailyCollectionPlan poDCPRepo;
    private final ImportCollection dataImport;
    private final HttpHeaders poheaders;
    private final ConnectionUtil poConn;
    private final LiveData<List<EDCPCollectionDetail>> collectionList;

    public VMCollectionList(@NonNull Application application) {
        super(application);
        poDCPRepo = new RDailyCollectionPlan(application);
        poheaders = HttpHeaders.getInstance(application);
        poConn = new ConnectionUtil(application);
        dataImport = new ImportCollection(application);
        collectionList = poDCPRepo.getCollectionDetail();
    }

    public void DownloadDcp(){
        dataImport.ImportData(new ImportDataCallback() {
            @Override
            public void OnSuccessImportData() {
                Log.e(TAG, "DCP Downloaded.");
            }

            @Override
            public void OnFailedImportData(String message) {
                Log.e(TAG, message);
            }
        });
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionList(){
        return collectionList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean ImportData(){
        try{
            JSONObject param = new JSONObject();
            param.put("sEmployID", "M00110006088");
            param.put("dTransact", "2021-02-04");
            param.put("cDCPTypex", "1");
            String response = WebClient.httpsPostJSon(WebApi.URL_DOWNLOAD_DCP, param.toString(), poheaders.getHeaders());
            if(response == null){
                return false;
            } else {
                JSONObject loResponse = new JSONObject(response);
                Log.e(TAG, loResponse.toString());
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
