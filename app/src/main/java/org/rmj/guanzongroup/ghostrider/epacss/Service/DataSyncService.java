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

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.rmj.g3appdriver.GCircle.room.Repositories.RLocationSysLog;
import org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.ApprovalCode;
import org.rmj.g3appdriver.GCircle.Apps.Itinerary.Obj.EmployeeItinerary;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.PetManager;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.model.iPM;
import org.rmj.g3appdriver.GCircle.Apps.SelfieLog.SelfieLog;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CashCount.CashCount;
import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnDoBackgroundTaskListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;
import org.rmj.guanzongroup.ghostrider.notifications.Notifications.GNotifBuilder;

public class DataSyncService extends BroadcastReceiver {
    private static final String TAG = DataSyncService.class.getSimpleName();

    private final Application instance;

    private ConnectionUtil poConn;

    private String message;

    public DataSyncService(Application instance) {
        this.instance = instance;
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        poConn = new ConnectionUtil(context);

        TaskExecutor.Execute(instance, new OnDoBackgroundTaskListener() {
            @Override
            public Object DoInBackground(Object args) {
                try {
                    if (!poConn.isDeviceConnected()) {
                        message = poConn.getMessage();
                        TaskExecutor.ShowProgress(() -> GNotifBuilder.createNotification(instance, GNotifBuilder.BROADCAST_RECEIVER, message, GNotifBuilder.SYNC_PROGRESS).show());
                        return false;
                    }

                    SelfieLog loSelfie = new SelfieLog(instance);
                    if(loSelfie.UploadSelfieLogs()){
                        message = "Selfie log/s uploaded successfully";
                        TaskExecutor.ShowProgress(() -> GNotifBuilder.createNotification(instance, GNotifBuilder.BROADCAST_RECEIVER, message, GNotifBuilder.SYNC_PROGRESS).show());
                    } else {
                        message = loSelfie.getMessage();
                        Log.e(TAG, message);
                    }
                    Thread.sleep(1000);

                    if(loSelfie.UploadImages()){
                        publishProgress("Selfie log image/s uploaded successfully");
                    } else {
                        message = loSelfie.getMessage();
                        Log.e(TAG, message);
                    }
                    Thread.sleep(1000);

                    iPM loLeave = new PetManager(instance).GetInstance(PetManager.ePetManager.LEAVE_APPLICATION);
                    if(loLeave.UploadApplications()){
                        message = "Leave application/s uploaded successfully";
                        TaskExecutor.ShowProgress(() -> GNotifBuilder.createNotification(instance, GNotifBuilder.BROADCAST_RECEIVER, message, GNotifBuilder.SYNC_PROGRESS).show());
                    } else {
                        message = loSelfie.getMessage();
                        Log.e(TAG, message);
                    }
                    Thread.sleep(1000);

                    iPM loBustrp = new PetManager(instance).GetInstance(PetManager.ePetManager.BUSINESS_TRIP_APPLICATION);
                    if(loBustrp.UploadApplications()){
                        message = "Business trip application/s uploaded successfully";
                        TaskExecutor.ShowProgress(() -> GNotifBuilder.createNotification(instance, GNotifBuilder.BROADCAST_RECEIVER, message, GNotifBuilder.SYNC_PROGRESS).show());
                    } else {
                        message = loBustrp.getMessage();
                        Log.e(TAG, message);
                    }
                    Thread.sleep(1000);

                    ApprovalCode loCode = new ApprovalCode(instance);
                    if(loCode.UploadApprovalCode()){
                        message = "Approval code/s uploaded successfully";
                        TaskExecutor.ShowProgress(() -> GNotifBuilder.createNotification(instance, GNotifBuilder.BROADCAST_RECEIVER, message, GNotifBuilder.SYNC_PROGRESS).show());
                    } else {
                        message = loCode.getMessage();
                        Log.e(TAG, message);
                    }
                    Thread.sleep(1000);

                    CashCount loCash = new CashCount(instance);
                    if(loCash.UploadCashCountEntries()){
                        message = "Cash count/s uploaded successfully";
                        TaskExecutor.ShowProgress(() -> GNotifBuilder.createNotification(instance, GNotifBuilder.BROADCAST_RECEIVER, message, GNotifBuilder.SYNC_PROGRESS).show());
                    } else {
                        message = loCash.getMessage();
                        Log.e(TAG, message);
                    }
                    Thread.sleep(1000);

                    EmployeeItinerary loItnry = new EmployeeItinerary(instance);
                    if(loItnry.UploadUnsentItinerary()){
                        message = "Itinerary entries uploaded successfully";
                        TaskExecutor.ShowProgress(() -> GNotifBuilder.createNotification(instance, GNotifBuilder.BROADCAST_RECEIVER, message, GNotifBuilder.SYNC_PROGRESS).show());
                    } else {
                        message = loItnry.getMessage();
                        Log.e(TAG, message);
                    }
                    Thread.sleep(1000);

                    RLocationSysLog loLoct = new RLocationSysLog(instance);
                    if(loLoct.uploadUnsentLocationTracks()){
                        Log.d(TAG, "Location tracking uploaded successfully");
                    } else {
                        message = loLoct.getMessage();
                        Log.e(TAG, message);
                    }
                    Thread.sleep(1000);

                    CreditOnlineApplication loApp = new CreditOnlineApplication(instance);
                    if(loApp.UploadApplications()){
                        Log.d(TAG, "Credit online application uploaded successfully");
                    } else {
                        message = loApp.getMessage();
                        Log.e(TAG, message);
                    }
                    Thread.sleep(1000);

                    message = "Local data and server is updated.";
                    Log.d(TAG, message);
                    return true;
                } catch (Exception e){
                    e.printStackTrace();
                    message = getLocalMessage(e);
                    Log.e(TAG, message);
                    return false;
                }
            }

            @Override
            public void OnPostExecute(Object object) {

            }
        });
    }

//    private class SendDataTask extends AsyncTask<Void, String, Boolean>{
//
//        private final Application instance;
//
//        public SendDataTask(Application instance) {
//            this.instance = instance;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            String message;
//            try {
//                if (!poConn.isDeviceConnected()) {
//                    message = poConn.getMessage();
//                    publishProgress(message);
//                    return false;
//                }
//
//                SelfieLog loSelfie = new SelfieLog(instance);
//                if(loSelfie.UploadSelfieLogs()){
//                    publishProgress("Selfie log/s uploaded successfully");
//                } else {
//                    message = loSelfie.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                iPM loLeave = new PetManager(instance).GetInstance(PetManager.ePetManager.LEAVE_APPLICATION);
//                if(loLeave.UploadApplications()){
//                    publishProgress("Leave application/s uploaded successfully");
//                } else {
//                    message = loSelfie.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                iPM loBustrp = new PetManager(instance).GetInstance(PetManager.ePetManager.BUSINESS_TRIP_APPLICATION);
//                if(loBustrp.UploadApplications()){
//                    publishProgress("Business trip application/s uploaded successfully");
//                } else {
//                    message = loBustrp.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                ApprovalCode loCode = new ApprovalCode(instance);
//                if(loCode.UploadApprovalCode()){
//                    publishProgress("Approval code/s uploaded successfully");
//                } else {
//                    message = loCode.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                CashCount loCash = new CashCount(instance);
//                if(loCash.UploadCashCountEntries()){
//                    publishProgress("Cash count/s uploaded successfully");
//                } else {
//                    message = loCash.getMessage();
//                    Log.e(TAG, message);
//                }
//                Thread.sleep(1000);
//
//                EmployeeItinerary loItnry = new EmployeeItinerary(instance);
//                if(loItnry.UploadUnsentItinerary()){
//                    publishProgress("Itinerary entries uploaded successfully");
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
//                return true;
//            } catch (Exception e){
//                e.printStackTrace();
//                message = e.getMessage();
//                Log.e(TAG, message);
//                return false;
//            }
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//            GNotifBuilder.createNotification(instance, GNotifBuilder.BROADCAST_RECEIVER, values[0], GNotifBuilder.SYNC_PROGRESS).show();
//        }
//    }
}
