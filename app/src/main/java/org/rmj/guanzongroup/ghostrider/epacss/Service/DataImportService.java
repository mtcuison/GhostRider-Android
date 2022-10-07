/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.Service;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import org.rmj.g3appdriver.lib.ImportData.ImportBarangay;
import org.rmj.g3appdriver.lib.ImportData.ImportBrand;
import org.rmj.g3appdriver.lib.ImportData.ImportBrandModel;
import org.rmj.g3appdriver.lib.ImportData.ImportCategory;
import org.rmj.g3appdriver.lib.ImportData.ImportCountry;
import org.rmj.g3appdriver.lib.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.lib.ImportData.ImportFileCode;
import org.rmj.g3appdriver.lib.ImportData.ImportInstance;
import org.rmj.g3appdriver.lib.ImportData.ImportMcModelPrice;
import org.rmj.g3appdriver.lib.ImportData.ImportMcTermCategory;
import org.rmj.g3appdriver.lib.ImportData.ImportProvinces;
import org.rmj.g3appdriver.lib.ImportData.ImportTown;
import org.rmj.g3appdriver.lib.ImportData.Import_AreaPerformance;
import org.rmj.g3appdriver.lib.ImportData.Import_BankList;
import org.rmj.g3appdriver.lib.ImportData.Import_BranchAccounts;
import org.rmj.g3appdriver.lib.ImportData.Import_BranchPerformance;
import org.rmj.g3appdriver.lib.ImportData.Import_Occupations;
import org.rmj.g3appdriver.lib.ImportData.Import_Relation;
import org.rmj.g3appdriver.lib.ImportData.Import_SCARequest;
import org.rmj.g3appdriver.lib.ImportData.Import_SysConfig;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class DataImportService extends JobService {
    public static final String TAG = DataImportService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
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
                new Import_BranchAccounts(getApplication()),
                new Import_BankList(getApplication()),
                new ImportFileCode(getApplication()),
                new Import_Relation(getApplication()),
                new ImportBrand(getApplication()),
                new ImportBrandModel(getApplication()),
                new ImportCategory(getApplication()),
                new ImportProvinces(getApplication()),
                new ImportMcModelPrice(getApplication()),
                new ImportTown(getApplication()),
                new ImportBarangay(getApplication()),
                new ImportMcTermCategory(getApplication()),
                new ImportCountry(getApplication()),
                new Import_Occupations(getApplication()),
                new Import_SysConfig(getApplication()),
                new Import_SCARequest(getApplication()),
                new Import_AreaPerformance(getApplication()),
                new Import_BranchPerformance(getApplication())};
        new Thread(() -> {
            for (ImportInstance importInstance : importInstances) {
                importInstance.ImportData(new ImportDataCallback() {
                    @Override
                    public void OnSuccessImportData() {
                        Log.e(TAG, importInstance.getClass().getSimpleName() + " import success.");
                    }

                    @Override
                    public void OnFailedImportData(String message) {
                        Log.e(TAG, importInstance.getClass().getSimpleName() + " import failed. " + message);
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
