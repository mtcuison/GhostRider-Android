package org.guanzongroup.com.itinerary.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EItinerary;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.Itinerary.EmployeeItinerary;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMItinerary extends AndroidViewModel {
    private static final String TAG = VMItinerary.class.getSimpleName();

    private final Application instance;
    private final EmployeeItinerary poSystem;
    private final RBranch poBranch;
    private final EmployeeMaster poUser;

    public interface OnActionCallback {
        void OnLoad(String title, String message);
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public VMItinerary(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSystem = new EmployeeItinerary(instance);
        this.poBranch = new RBranch(instance);
        this.poUser = new EmployeeMaster(instance);
    }

    public LiveData<List<EItinerary>> GetItineraryListForCurrentDay(){
        return poSystem.GetItineraryListForCurrentDay();
    }

    public LiveData<List<EItinerary>> GetItineraryForFilteredDate(String fsArgs1, String fsArgs2){
        return poSystem.GetItineraryListForFilteredDate(fsArgs1, fsArgs2);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public LiveData<String[]> GetAllBranchNames(){
        return poBranch.getAllBranchNames();
    }

    public void SaveItinerary(EmployeeItinerary.ItineraryEntry foVal, OnActionCallback callback){
        new SaveItineraryTask(instance, callback).execute(foVal);
    }

    private static class SaveItineraryTask extends AsyncTask<EmployeeItinerary.ItineraryEntry , Void, Boolean>{

        private final OnActionCallback callback;
        private final EmployeeItinerary poSystem;
        private final ConnectionUtil poConn;

        private String message;

        public SaveItineraryTask(Application instance, OnActionCallback callback) {
            this.callback = callback;
            this.poSystem = new EmployeeItinerary(instance);
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad("Employee Itinerary", "Saving entry. Please wait...");
        }

        @Override
        protected Boolean doInBackground(EmployeeItinerary.ItineraryEntry ... itineraries) {
            try{
                EmployeeItinerary.ItineraryEntry loVal = itineraries[0];
                String lsResult = poSystem.SaveItinerary(loVal);
                if(lsResult == null){
                    Log.e(TAG, "Unable to save itinerary. Message: " + poSystem.getMessage());
                    message = poSystem.getMessage();
                    return false;
                }

                Log.d(TAG, "Data for upload transaction no. : " + lsResult);
                if(!poConn.isDeviceConnected()){
                    Log.e(TAG, "Unable to connect to upload entry.");
                    message = "Your entry has been save locally and will be uploaded later for if device has reconnected.";
                    return true;
                }

                if(!poSystem.UploadItinerary(lsResult)){
                    message = poSystem.getMessage();
                    return false;
                } else {
                    message = "Your entry has been save. You may now download the itinerary to other device.";
                    return true;
                }
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                callback.OnSuccess(message);
            } else {
                callback.OnFailed(message);
            }
        }
    }

    public void DownloadItinerary(String args1, String args2, OnActionCallback callback){
        new DownloadItineraryTask(instance, callback).execute(args1, args2);
    }

    private static class DownloadItineraryTask extends AsyncTask<String, Void, Boolean>{

        private final OnActionCallback callback;
        private final EmployeeItinerary poSystem;
        private final ConnectionUtil poConn;

        private String message;

        public DownloadItineraryTask(Application instance, OnActionCallback callback) {
            this.callback = callback;
            this.poSystem = new EmployeeItinerary(instance);
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                if(!poConn.isDeviceConnected()){
                    Log.e(TAG, "Unable to connect to download entries.");
                    message = "Unable to connect. Please check internet connectivity";
                    return true;
                } else {
                    Log.d(TAG, "Argument 1: " + strings[0]);
                    Log.d(TAG, "Argument 2: " + strings[1]);
                    boolean isDownloaded = poSystem.DownloadItinerary(
                            strings[0]
                            ,strings[1]);
                    if(!isDownloaded){
                        message = poSystem.getMessage();
                        return false;
                    } else {
                        message = "Itinerary entries downloaded successfully";
                        return true;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad("Employee Itinerary", "Saving entry. Please wait...");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                callback.OnSuccess(message);
            } else {
                callback.OnFailed(message);
            }
        }
    }
}
