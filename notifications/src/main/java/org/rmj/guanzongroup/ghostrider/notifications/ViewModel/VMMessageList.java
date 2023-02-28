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
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.dev.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.dev.Api.WebApi;
import org.rmj.guanzongroup.ghostrider.notifications.Obj.EmployeeSearchItem;
import org.rmj.guanzongroup.ghostrider.notifications.Obj.MessageItemList;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.notifications.Notifications.EmployeeSearchItem;
import org.rmj.guanzongroup.ghostrider.notifications.Notifications.MessageItemList;

import java.util.List;

public class VMMessageList extends AndroidViewModel {
    private final MutableLiveData<List<MessageItemList>> plMessage = new MutableLiveData<>();
    private final Application instance;
    private final RNotificationInfo poNotification;
    private final LiveData<List<DNotifications.UserNotificationInfo>> userMessagesList;

    public interface OnEmloyeeSearchListener{
        void OnSearch(List<EmployeeSearchItem> employeeSearchItems);
        void OnError(String message);
    }

    public VMMessageList(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poNotification = new RNotificationInfo(application);
        this.userMessagesList = poNotification.getUserMessageListGroupByUser();
    }

    public LiveData<List<DNotifications.UserNotificationInfo>> getUserMessagesList() {
        return userMessagesList;
    }

    public LiveData<List<MessageItemList>> getMessageList(){
        return plMessage;
    }

    public void SearchEmployee(String EmployeeName, OnEmloyeeSearchListener listener){
        new SearchEmployeeTask(instance, listener).execute(EmployeeName);
    }

    private static class SearchEmployeeTask extends AsyncTask<String, Void, String>{
        private final Application instance;
        private final OnEmloyeeSearchListener mListener;

        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final WebApi poApi;
        private final AppConfigPreference loConfig;

        public SearchEmployeeTask(Application application, OnEmloyeeSearchListener listener) {
            this.instance = application;
            this.mListener = listener;
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(String... strings) {
            String lsResult = "";
            try{
                if(poConn.isDeviceConnected()){
                    JSONObject loJson = new JSONObject();
                    loJson.put("reqstdnm", strings[0]);
                    loJson.put("bsearch", true);

                    lsResult = WebClient.sendRequest(poApi.getUrlKwiksearch(loConfig.isBackUpServer()), loJson.toString(), poHeaders.getHeaders());

                } else {
                    lsResult = AppConstants.NO_INTERNET();
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loResult = new JSONObject(s);
                String result = loResult.getString("result");
                if(result.equalsIgnoreCase("success")){

                } else {

                }
            } catch (Exception e){
                e.printStackTrace();
                mListener.OnError(e.getMessage());
            }
        }
    }
}