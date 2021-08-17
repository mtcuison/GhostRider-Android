/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.List;
import java.util.Objects;

import static org.rmj.g3appdriver.utils.WebApi.URL_SEND_RESPONSE;

public class VMViewNotification extends AndroidViewModel {
    private static final String TAG = VMViewNotification.class.getSimpleName();
    private final Application instance;

    public VMViewNotification(@NonNull Application application) {
        super(application);
        Log.e(TAG, "Initialized.");
        this.instance = application;
    }

    public void sendReply(String fsTransNo, String fsMessage) {
        Log.e(TAG, "Reply notification currently not available");
    }

    public void deleteNotification(String fsTransNo) {
        Log.e(TAG, "Delete notification currently not available.");
        return;
    }

    public void UpdateMessageStatus(String MessageID){
        new UpdateMessagesTask(instance).execute(MessageID);
    }

    private static class UpdateMessagesTask extends AsyncTask<String, Void, String> {
        private final Application instance;
        private final ConnectionUtil loConn;
        private final HttpHeaders poHeaders;
        private final RNotificationInfo poNotif;

        public UpdateMessagesTask(Application application) {
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
                poNotif.updateRecipientReadStatus(lsMsgID);
                if (loConn.isDeviceConnected()) {
                    String lsMessageID = lsMsgID;
                    String lsDateReadx = poNotif.getReadMessageTimeStamp(lsMessageID);
                    JSONObject params = new JSONObject();
                    params.put("transno", lsMessageID);
                    params.put("status", "3");
                    params.put("stamp", lsDateReadx);
                    params.put("infox", "");

                    String response = WebClient.httpsPostJSon(URL_SEND_RESPONSE, params.toString(), poHeaders.getHeaders());
                    JSONObject loJson = new JSONObject(Objects.requireNonNull(response));
                    String result = loJson.getString("result");
                    if (result.equalsIgnoreCase("success")) {
                        Log.e(TAG, "message status updated to READ");
                    }
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