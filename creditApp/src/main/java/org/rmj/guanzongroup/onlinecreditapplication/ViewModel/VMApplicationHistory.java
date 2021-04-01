package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.Import_LoanApplications;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;

public class VMApplicationHistory extends AndroidViewModel {
    private static final String TAG = VMApplicationHistory.class.getSimpleName();
    private final RCreditApplication poCreditApp;
    private final Import_LoanApplications poImport;
    private final RImageInfo poImage;

    public VMApplicationHistory(@NonNull Application application) {
        super(application);
        this.poCreditApp = new RCreditApplication(application);
        this.poImport = new Import_LoanApplications(application);
        this.poImage = new RImageInfo(application);
    }

    public void LoadApplications(ViewModelCallBack callBack){
        poImport.ImportData(new ImportDataCallback() {
            @Override
            public void OnSuccessImportData() {
                callBack.onSaveSuccessResult("");
            }

            @Override
            public void OnFailedImportData(String message) {
                callBack.onFailedResult(message);
            }
        });
    }

    public LiveData<List<DCreditApplication.ApplicationLog>> getApplicationHistory(){
        return poCreditApp.getApplicationHistory();
    }

    public void ExportGOCasInfo(String TransNox){

    }

    public void DeleteGOCasInfo(String TransNox){

    }

    public void UpdateGOCasInfo(String TransNox){

    }

    public void saveImageFile(EImageInfo foImage) {
        foImage.setTransNox(poImage.getImageNextCode());
        poImage.insertImageInfo(foImage);
    }
}
