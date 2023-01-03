package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppDocuments;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.CreditAppDocs;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMApplicantDocuments extends AndroidViewModel {
    private static final String TAG = VMApplicantDocuments.class.getSimpleName();

    private final CreditAppDocuments poApp;

    private final ConnectionUtil poConn;

    public interface OnInitializeCreditAppDocuments{
        void OnSuccess();
        void OnFailed(String message);
    }

    public interface OnSaveCreditAppDocument{
        void OnSave(String title, String message);
        void OnSuccess(String message);
        void OnFailed(String message);
    }

    private String message;

    public VMApplicantDocuments(@NonNull Application application) {
        super(application);
        this.poApp = new CreditAppDocuments(application);
        this.poConn = new ConnectionUtil(application);
    }

    public void InitializeDocuments(String TransNox, OnInitializeCreditAppDocuments listener){
        new InitializeDocumentsTask(listener).execute(TransNox);
    }

    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> GetCreditAppDocuments(String args){
        return poApp.GetApplicantDocumentsList(args);
    }

    public void SaveDocumentScan(CreditAppDocs args, OnSaveCreditAppDocument listener){
        new SaveDocumentTask(listener).execute(args);
    }

    private class InitializeDocumentsTask extends AsyncTask<String, Void, Boolean>{

        private final OnInitializeCreditAppDocuments listener;

        public InitializeDocumentsTask(OnInitializeCreditAppDocuments listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(String... transnox) {
            try{
                if(!poApp.InitializeApplicantDocuments(transnox[0])){
                    message = poApp.getMessage();
                    return false;
                }

                return true;
            } catch (NullPointerException e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                listener.OnFailed(message);
            } else {
                listener.OnSuccess();
            }
        }
    }

    private class SaveDocumentTask extends AsyncTask<CreditAppDocs, Void, Boolean>{

        private final OnSaveCreditAppDocument listener;

        public SaveDocumentTask(OnSaveCreditAppDocument listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(CreditAppDocs... info) {
            try{
                String lsResult = poApp.SaveDocumentInfo(info[0]);
                if(lsResult == null){
                    message = poApp.getMessage();
                    return false;
                }

                if(!poConn.isDeviceConnected()){
                    return true;
                }

                if(!poApp.UploadDocument(lsResult)){
                    message = poApp.getMessage();
                    return false;
                }
                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
        }
    }
}
