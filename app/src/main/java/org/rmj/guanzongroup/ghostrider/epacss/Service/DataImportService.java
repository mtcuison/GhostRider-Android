package org.rmj.guanzongroup.ghostrider.epacss.Service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.rmj.g3appdriver.GRider.ImportData.ImportBarangay;
import org.rmj.g3appdriver.GRider.ImportData.ImportBranch;
import org.rmj.g3appdriver.GRider.ImportData.ImportBrand;
import org.rmj.g3appdriver.GRider.ImportData.ImportBrandModel;
import org.rmj.g3appdriver.GRider.ImportData.ImportCategory;
import org.rmj.g3appdriver.GRider.ImportData.ImportCountry;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.ImportInstance;
import org.rmj.g3appdriver.GRider.ImportData.ImportMcModelPrice;
import org.rmj.g3appdriver.GRider.ImportData.ImportMcTermCategory;
import org.rmj.g3appdriver.GRider.ImportData.ImportProvinces;
import org.rmj.g3appdriver.GRider.ImportData.ImportTown;
import org.rmj.g3appdriver.GRider.ImportData.Import_AreaPerformance;
import org.rmj.g3appdriver.GRider.ImportData.Import_BranchPerformance;
import org.rmj.g3appdriver.GRider.ImportData.Import_Occupations;
import org.rmj.g3appdriver.GRider.ImportData.Import_SCARequest;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public
class DataImportService extends JobService {
    public static final String TAG = DataImportService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e(TAG, "Data import service has started.");
        try{
            doBackgroundTask(jobParameters);
        } catch (Exception e){
            e.printStackTrace();
            jobFinished(jobParameters, false);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e(TAG, "Data import service has stop.");
        return true;
    }

    private void doBackgroundTask(JobParameters params) {
        ImportInstance[]  importInstances = {
                new ImportBrand(getApplication()),
                new ImportBrandModel(getApplication()),
                new ImportCategory(getApplication()),
                new ImportProvinces(getApplication()),
                new ImportMcModelPrice(getApplication()),
                new ImportTown(getApplication()),
                new ImportBarangay(getApplication()),
                new ImportMcTermCategory(getApplication()),
                new ImportCountry(getApplication()),
                new Import_SCARequest(getApplication()),
                new Import_AreaPerformance(getApplication()),
                new Import_Occupations(getApplication()),
                new Import_BranchPerformance(getApplication()),
                new ImportBranch(getApplication())};

        new Thread(() -> {
            for (ImportInstance importInstance : importInstances) {
                importInstance.ImportData(new ImportDataCallback() {
                    @Override
                    public void OnSuccessImportData() {

                    }

                    @Override
                    public void OnFailedImportData(String message) {

                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            jobFinished(params, false);
        }).start();
    }
}
