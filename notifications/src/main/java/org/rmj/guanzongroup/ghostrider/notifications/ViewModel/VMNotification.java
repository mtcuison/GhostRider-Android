/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 9/1/21, 9:49 AM
 * project file last modified : 9/1/21, 9:49 AM
 */

package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.utils.ConnectionUtil;

public class VMNotification extends AndroidViewModel {

    private final Application instance;

    public VMNotification(@NonNull Application application) {
        super(application);
        this.instance = application;
    }

    public void DeleteNotification(String MessageID){
        new UpdateToDeleteTask(instance).execute(MessageID);
    }

    private static class UpdateToDeleteTask extends AsyncTask<String, Void, String> {
        private final Application instance;
        private final ConnectionUtil loConn;
        private final HttpHeaders poHeaders;
        private final RNotificationInfo poNotif;

        public UpdateToDeleteTask(Application application) {
            this.instance = application;
            this.loConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poNotif = new RNotificationInfo(instance);
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(String... strings) {
            String lsResult = "";
            try {
                String lsMsgID = strings[0];
                poNotif.updateNotificationDeleteStatus(lsMsgID);
                if (loConn.isDeviceConnected()) {
                    String lsMessageID = lsMsgID;
                    String lsDLastUpdt = poNotif.getDeleteMessageTimeStamp(lsMessageID);
                    JSONObject params = new JSONObject();
                    params.put("transno", lsMessageID);
                    params.put("status", "5");
                    params.put("stamp", lsDLastUpdt);
                    params.put("infox", "");
//                    lsResult = AppConstants.APPROVAL_CODE_GENERATED("");
//                    String response = WebClient.httpsPostJSon(URL_SEND_RESPONSE, params.toString(), poHeaders.getHeaders());
//                    JSONObject loJson = new JSONObject(Objects.requireNonNull(response));
//                    String result = loJson.getString("result");
//                    if (result.equalsIgnoreCase("success")) {
//                        Log.e(TAG, "message status updated to DELETE");
//                    }
                } else {
                    lsResult = AppConstants.NO_INTERNET();
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }
    }
}
