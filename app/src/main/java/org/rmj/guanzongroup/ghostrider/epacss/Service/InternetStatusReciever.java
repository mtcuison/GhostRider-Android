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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.dev.Database.Repositories.RApprovalCode;
import org.rmj.g3appdriver.dev.Database.Repositories.RItinerary;
import org.rmj.g3appdriver.dev.Database.Repositories.RLocationSysLog;
import org.rmj.g3appdriver.lib.PetManager.EmployeeLeave;
import org.rmj.g3appdriver.lib.PetManager.EmployeeOB;
import org.rmj.g3appdriver.lib.PetManager.SelfieLog;
import org.rmj.g3appdriver.lib.integsys.CashCount.CashCount;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;

import java.util.ArrayList;
import java.util.List;

public class InternetStatusReciever extends BroadcastReceiver {
    private static final String TAG = InternetStatusReciever.class.getSimpleName();

    private final Application instance;

    private ConnectionUtil poConn;

    public InternetStatusReciever(Application instance) {
        this.instance = instance;
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        poConn = new ConnectionUtil(context);

        SendDataTask poSendTask = new SendDataTask(instance);
        poSendTask.execute();
    }

    private class SendDataTask extends AsyncTask<Void, String, Boolean>{
        private final Application instance;

        private final List<ECreditApplication> sentAppl = new ArrayList<>();

        public SendDataTask(Application instance) {
            this.instance = instance;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String message;
            try {
                if (!poConn.isDeviceConnected()) {
                    message = poConn.getMessage();
                    publishProgress(message);
                    return false;
                }

                SelfieLog loSelfie = new SelfieLog(instance);
                if(loSelfie.UploadSelfieLogs()){
                    publishProgress("Selfie log/s uploaded successfully");
                } else {
                    message = loSelfie.getMessage();
                    Log.e(TAG, message);
                }
                Thread.sleep(1000);

                EmployeeLeave loLeave = new EmployeeLeave(instance);
                if(loLeave.UploadLeaveApplications()){
                    publishProgress("Leave application/s uploaded successfully");
                } else {
                    message = loSelfie.getMessage();
                    Log.e(TAG, message);
                }
                Thread.sleep(1000);

                EmployeeOB loBustrp = new EmployeeOB(instance);
                if(loBustrp.UploadApplications()){
                    publishProgress("Business trip application/s uploaded successfully");
                } else {
                    message = loBustrp.getMessage();
                    Log.e(TAG, message);
                }
                Thread.sleep(1000);

                RApprovalCode loCode = new RApprovalCode(instance);
                if(loCode.UploadApprovalCode()){
                    publishProgress("Approval code/s uploaded successfully");
                } else {
                    message = loCode.getMessage();
                    Log.e(TAG, message);
                }
                Thread.sleep(1000);

                CashCount loCash = new CashCount(instance);
                if(loCash.UploadCashCountEntries()){
                    publishProgress("Cash count/s uploaded successfully");
                } else {
                    message = loCash.getMessage();
                    Log.e(TAG, message);
                }
                Thread.sleep(1000);

                RItinerary loItnry = new RItinerary(instance);
                if(loItnry.UploadUnsentItinerary()){
                    publishProgress("Itinerary entries uploaded successfully");
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

                message = "Local data and server is updated.";
                Log.d(TAG, message);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                Log.e(TAG, message);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            GNotifBuilder.createNotification(instance, GNotifBuilder.BROADCAST_RECEIVER, values[0], GNotifBuilder.SYNC_PROGRESS).show();
        }
    }
}
