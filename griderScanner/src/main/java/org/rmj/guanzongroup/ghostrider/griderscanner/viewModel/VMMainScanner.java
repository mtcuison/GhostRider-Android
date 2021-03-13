package org.rmj.guanzongroup.ghostrider.griderscanner.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RFileCode;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.Import_LoanApplications;

import java.util.List;

public class VMMainScanner extends AndroidViewModel {
    private final Application instance;
    private final RFileCode peFileCode;
    private final LiveData<List<EFileCode>> collectionList;
    private final RCreditApplication poCreditApp;
    private final Import_LoanApplications poImport;
    public VMMainScanner(@NonNull Application application) {
        super(application);
        this.instance = application;
        peFileCode = new RFileCode(application);
        this.collectionList = peFileCode.getAllFileCode();
        this.poCreditApp = new RCreditApplication(application);
        this.poImport = new Import_LoanApplications(application);
    }
    public LiveData<List<EFileCode>> getFileCode(){
        return this.collectionList;
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
}
