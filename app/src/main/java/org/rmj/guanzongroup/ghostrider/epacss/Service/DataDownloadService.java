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
import android.util.Log;

import org.rmj.g3appdriver.lib.ImportData.Obj.ImportBarangay;
import org.rmj.g3appdriver.lib.ImportData.Obj.ImportBrand;
import org.rmj.g3appdriver.lib.ImportData.Obj.ImportBrandModel;
import org.rmj.g3appdriver.lib.ImportData.Obj.ImportCategory;
import org.rmj.g3appdriver.lib.ImportData.Obj.ImportCountry;
import org.rmj.g3appdriver.lib.ImportData.model.ImportDataCallback;
import org.rmj.g3appdriver.lib.ImportData.Obj.ImportFileCode;
import org.rmj.g3appdriver.lib.ImportData.model.ImportInstance;
import org.rmj.g3appdriver.lib.ImportData.Obj.ImportMcModelPrice;
import org.rmj.g3appdriver.lib.ImportData.Obj.ImportMcTermCategory;
import org.rmj.g3appdriver.lib.ImportData.Obj.ImportProvinces;
import org.rmj.g3appdriver.lib.ImportData.Obj.ImportTown;
import org.rmj.g3appdriver.lib.ImportData.Obj.Import_AreaPerformance;
import org.rmj.g3appdriver.lib.ImportData.Obj.Import_BankList;
import org.rmj.g3appdriver.lib.ImportData.Obj.Import_BranchAccounts;
import org.rmj.g3appdriver.lib.ImportData.Obj.Import_BranchPerformance;
import org.rmj.g3appdriver.lib.ImportData.Obj.Import_Occupations;
import org.rmj.g3appdriver.lib.ImportData.Obj.Import_Relation;
import org.rmj.g3appdriver.lib.ImportData.Obj.Import_SCARequest;
import org.rmj.g3appdriver.lib.ImportData.Obj.Import_SysConfig;
import org.rmj.g3appdriver.utils.ConnectionUtil;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class DataDownloadService extends JobService {
    public static final String TAG = DataDownloadService.class.getSimpleName();

    private Application instance;

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
        instance = getApplication();
        ConnectionUtil loConn = new ConnectionUtil(instance);
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

//            String message;
//            try {
//                if (!loConn.isDeviceConnected()) {
//                    Log.d(TAG, loConn.getMessage());
//                    return;
//                }
//
//                SelfieLog loSelfie = new SelfieLog(instance);
//                if(loSelfie.UploadSelfieLogs()){
//                    Log.d(TAG, "Selfie log/s uploaded successfully");
//                } else {
//                    message = loSelfie.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                iPM loLeave = new PetManager(instance).GetInstance(PetManager.ePetManager.LEAVE_APPLICATION);
//                if(loLeave.UploadApplications()){
//                    Log.d(TAG, "Leave application/s uploaded successfully");
//                } else {
//                    message = loSelfie.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                iPM loBustrp = new PetManager(instance).GetInstance(PetManager.ePetManager.BUSINESS_TRIP_APPLICATION);
//                if(loBustrp.UploadApplications()){
//                    Log.d(TAG, "Business trip application/s uploaded successfully");
//                } else {
//                    message = loBustrp.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                ApprovalCode loCode = new ApprovalCode(instance);
//                if(loCode.UploadApprovalCode()){
//                    Log.d(TAG, "Approval code/s uploaded successfully");
//                } else {
//                    message = loCode.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                CashCount loCash = new CashCount(instance);
//                if(loCash.UploadCashCountEntries()){
//                    Log.d(TAG, "Cash count/s uploaded successfully");
//                } else {
//                    message = loCash.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                EmployeeItinerary loItnry = new EmployeeItinerary(instance);
//                if(loItnry.UploadUnsentItinerary()){
//                    Log.d(TAG, "Itinerary entries uploaded successfully");
//                } else {
//                    message = loItnry.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                RLocationSysLog loLoct = new RLocationSysLog(instance);
//                if(loLoct.uploadUnsentLocationTracks()){
//                    Log.d(TAG, "Location tracking uploaded successfully");
//                } else {
//                    message = loLoct.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                CreditOnlineApplication loApp = new CreditOnlineApplication(instance);
//                if(loApp.UploadApplications()){
//                    Log.d(TAG, "Credit online application uploaded successfully");
//                } else {
//                    message = loApp.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                message = "Local data and server is updated.";
//                Log.d(TAG, message);
//            } catch (Exception e){
//                e.printStackTrace();
//                message = e.getMessage();
//                Log.e(TAG, message);
//            }
            jobFinished(params, false);
        }).start();
    }
}
