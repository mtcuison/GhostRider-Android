package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.Import_LoanApplications;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;

public class VMApplicationHistory extends AndroidViewModel {
    private static final String TAG = VMApplicationHistory.class.getSimpleName();
    private final RCreditApplication poCreditApp;
    private final Import_LoanApplications poImport;

    public VMApplicationHistory(@NonNull Application application) {
        super(application);
        this.poCreditApp = new RCreditApplication(application);
        this.poImport = new Import_LoanApplications(application);
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

    public LiveData<ArrayAdapter<String>> getFilterList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.APPLICATION_FILTER);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void ExportGOCasInfo(String TransNox){

    }

    public void DeleteGOCasInfo(String TransNox){

    }

    public void UpdateGOCasInfo(String TransNox){

    }
}
