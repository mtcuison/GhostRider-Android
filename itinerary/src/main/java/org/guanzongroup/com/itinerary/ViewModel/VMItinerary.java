package org.guanzongroup.com.itinerary.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EItinerary;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RItinerary;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMItinerary extends AndroidViewModel {
    private static final String TAG = VMItinerary.class.getSimpleName();

    private final Application instance;
    private final RItinerary poSystem;
    private final RBranch poBranch;
    private final REmployee poUser;

    public interface OnActionCallback {
        void OnLoad(String title, String message);
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public VMItinerary(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSystem = new RItinerary(instance);
        this.poBranch = new RBranch(instance);
        this.poUser = new REmployee(instance);
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

    public void SaveItinerary(RItinerary.Itinerary foVal, OnActionCallback callback){
        new SaveItineraryTask(instance, callback).execute(foVal);
    }

    private static class SaveItineraryTask extends AsyncTask<RItinerary.Itinerary, Void, Boolean>{

        private final OnActionCallback callback;
        private final RItinerary poSystem;
        private final ConnectionUtil poConn;

        private String message;

        public SaveItineraryTask(Application instance, OnActionCallback callback) {
            this.callback = callback;
            this.poSystem = new RItinerary(instance);
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad("Employee Itinerary", "Saving entry. Please wait...");
        }

        @Override
        protected Boolean doInBackground(RItinerary.Itinerary... itineraries) {
            try{
                RItinerary.Itinerary loVal = itineraries[0];
                boolean isSave = poSystem.SaveItinerary(loVal);
                if(!isSave){
                    Log.e(TAG, "Unable to save itinerary. Message: " + poSystem.getMessage());
                    message = poSystem.getMessage();
                    return false;
                } else {
                    Log.d(TAG, "Data for upload transaction no. : " + poSystem.getTransNox());
                    if(!poConn.isDeviceConnected()){
                        Log.e(TAG, "Unable to connect to upload entry.");
                        message = "Your entry has been save locally and will be uploaded later for if device has reconnected.";
                        return true;
                    } else {
                        boolean isUploaded = poSystem.UploadItinerary(poSystem.getTransNox());
                        if(!isUploaded){
                            message = poSystem.getMessage();
                            return false;
                        } else {
                            message = "Your entry has been save. You may now download the itinerary to other device.";
                            return true;
                        }
                    }
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
        private final RItinerary poSystem;
        private final ConnectionUtil poConn;

        private String message;

        public DownloadItineraryTask(Application instance, OnActionCallback callback) {
            this.callback = callback;
            this.poSystem = new RItinerary(instance);
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
