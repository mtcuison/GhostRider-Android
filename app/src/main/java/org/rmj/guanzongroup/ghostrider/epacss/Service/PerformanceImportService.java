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
import android.app.Application;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranchPerformance;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.BullsEye.PerformancePeriod;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.ImportData.model.ImportDataCallback;
import org.rmj.g3appdriver.lib.ImportData.model.ImportInstance;
import org.rmj.g3appdriver.lib.ImportData.Obj.Import_AreaPerformance;
import org.rmj.g3appdriver.lib.ImportData.Obj.Import_BranchPerformance;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.dev.Api.WebApi;

import java.util.ArrayList;
import java.util.Arrays;


@SuppressLint("SpecifyJobSchedulerIdRange")
public class PerformanceImportService extends JobService {
    public static final String TAG = PerformanceImportService.class.getSimpleName();

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
            AppConfigPreference.getInstance(PerformanceImportService.this).setLastSyncDate(AppConstants.CURRENT_DATE());
            jobFinished(params, false);
        }).start();
    }
}
