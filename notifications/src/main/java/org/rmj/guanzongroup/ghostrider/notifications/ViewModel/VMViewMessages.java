/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 5/18/21 2:22 PM
 * project file last modified : 5/17/21 3:48 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.GRider.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.List;
import java.util.Objects;

public class VMViewMessages extends AndroidViewModel {
    private static final String TAG = VMViewMessages.class.getSimpleName();
    private final RNotificationInfo poNotification;
    private final Application instance;
    public VMViewMessages(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poNotification = new RNotificationInfo(application);
    }

    public LiveData<List<DNotifications.UserNotificationInfo>> getMessagesListFromSender(String SenderID){
        return poNotification.getUserMessageListFromSender(SenderID);
    }

    public void UpdateMessageStatus(String SenderID){
        new UpdateMessagesTask(instance).execute(SenderID);
    }

    private static class UpdateMessagesTask extends AsyncTask<String, Void, String>{
        private final Application instance;
        private final ConnectionUtil loConn;
        private final HttpHeaders poHeaders;
        private final RNotificationInfo poNotif;
        private final WebApi poApi;

        public UpdateMessagesTask(Application application) {
            this.instance = application;
            this.loConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poNotif = new RNotificationInfo(instance);
            AppConfigPreference loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(String... strings) {
            String lsResult = "";
            try {
                List<String> plMsgID = poNotif.getReadMessagesIDFromSender(strings[0]);
                for(int x = 0; x < plMsgID.size(); x++){
//                    poNotif.update(plMsgID.get(x));
                    if (loConn.isDeviceConnected()) {
                        String lsMessageID = plMsgID.get(x);
                        String lsDateReadx = poNotif.getReadMessageTimeStamp(lsMessageID);
                        JSONObject params = new JSONObject();
                        params.put("transno", lsMessageID);
                        params.put("status", "3");
                        params.put("stamp", lsDateReadx);
                        params.put("infox", "");

                        String response = WebClient.httpsPostJSon(poApi.getUrlSendResponse(), params.toString(), poHeaders.getHeaders());
                        JSONObject loJson = new JSONObject(Objects.requireNonNull(response));
                        String result = loJson.getString("result");
                        if (result.equalsIgnoreCase("success")) {
                            Log.e(TAG, "message status updated to READ");
                        }
                    } else {
                        lsResult = AppConstants.NO_INTERNET();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }
    }
}