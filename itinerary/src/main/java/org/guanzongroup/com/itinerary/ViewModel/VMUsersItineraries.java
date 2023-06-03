package org.guanzongroup.com.itinerary.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.GCircle.room.Entities.EItinerary;
import org.rmj.g3appdriver.GCircle.Apps.Itinerary.Obj.EmployeeItinerary;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMUsersItineraries extends AndroidViewModel {
    private static final String TAG = VMUsersItineraries.class.getSimpleName();

    private final EmployeeItinerary poSys;
    private final ConnectionUtil poConn;

    public interface OnDownloadUserEntriesListener{
        void OnImport(String title, String message);
        void OnSuccess(List<EItinerary> args);
        void OnFailed(String message);
    }

    public VMUsersItineraries(@NonNull Application application) {
        super(application);
        this.poSys = new EmployeeItinerary(application);
        this.poConn = new ConnectionUtil(application);
    }

    public void DownloadItineraryForUser(String args, String args1, String args2, OnDownloadUserEntriesListener listener){
//        new DownloadItineraryTask(listener).execute(args, args1, args2);

    }

    public class DownloadItineraryTask extends AsyncTask<String, Void, List<EItinerary>>{

        private final OnDownloadUserEntriesListener listener;

        private String message;

        public DownloadItineraryTask(OnDownloadUserEntriesListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnImport("Employee Itinerary", "Importing entries. Please wait...");
        }

        @Override
        protected List<EItinerary> doInBackground(String... strings) {
            String args = strings[0];
            String args1 = strings[1];
            String args2 = strings[2];
            List<EItinerary> loList = poSys.GetItineraryListForEmployee(args, args1, args2);
            if(loList == null){
                message = poSys.getMessage();
                return null;
            }

            return loList;
        }

        @Override
        protected void onPostExecute(List<EItinerary> list) {
            super.onPostExecute(list);
            if(list == null){
                listener.OnFailed(message);
            } else {
                listener.OnSuccess(list);
            }
        }
    }

}
