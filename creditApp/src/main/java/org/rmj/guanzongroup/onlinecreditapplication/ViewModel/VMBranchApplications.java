package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;

public class VMBranchApplications extends AndroidViewModel {
    private static final String TAG = VMBranchApplications.class.getSimpleName();

    private final CreditOnlineApplication poApp;

    private String message;

    public VMBranchApplications(@NonNull Application application) {
        super(application);
        this.poApp = new CreditOnlineApplication(application);
    }

    public void ImportBranchApplications(OnDownloadApplicationsListener listener){

    }

    private class ImportApplicationsTask extends AsyncTask<Void, Void, Boolean>{

        private final OnDownloadApplicationsListener listener;

        public ImportApplicationsTask(OnDownloadApplicationsListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnDownload("Credit Online Application", "Downloading entries from your current branch. Please wait...");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                if(!poApp.DownloadBranchApplications()){
                    message = poApp.getMessage();
                    return false;
                }

                return true;
            } catch (Exception e){
                e.printStackTrace();
                message =e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                listener.OnFailed(message);
            } else {
                listener.OnSuccess("");
            }
        }
    }
}
